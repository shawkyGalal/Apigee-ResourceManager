package com.smartvalue.apigee.rest.schema.proxyRevision;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Flow;
import com.smartvalue.apigee.rest.schema.sharedFlow.auto.RevisionedObject;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

import com.smartvalue.apigee.rest.schema.proxyEndPoint.ProxyEndpoint;

public class ProxyRevision extends com.smartvalue.apigee.rest.schema.proxyRevision.auto.ProxyRevision{

	private static final String OAS_FLOW_NAME = "GetOAS";
	private static final String SERVICE_NOT_AVAILABLE = "ServiceNotAvailable";
	private RevisionedObject parentProxy ; 


	public void setManagementServer(ManagementServer managmentServer) {
		this.setManagmentServer(managmentServer) ; 
	}
	

	public HashMap<String , ProxyEndpoint >  getProxyEndPointDetails(  ) throws UnirestException, IOException
	{
		HashMap<String , ProxyEndpoint > result = new HashMap<>() ; 
		for (String proxyEndpointName :   this.getProxyEndpoints())
		{
			String path = getResourcePath() + "/proxies/"+ proxyEndpointName  ; 
			ProxyEndpoint  proxyEndPoint = this.getManagmentServer().executeGetMgmntAPI(path, ProxyEndpoint.class) ; 
			proxyEndPoint.setParentProxyRevision(this);
			proxyEndPoint.setManagmentServer(this.getManagmentServer());     
			result.put( proxyEndpointName, proxyEndPoint) ; 
		}
		return result ; 
	}
	
	
	public HashMap<String , Object >  getTargetEndPointDetails(  ) throws UnirestException, IOException
	{
		HashMap<String , Object > result = new HashMap<>() ; 
		for (Object targetEndpoint :   this.getTargetEndpoints())
		{
			String path = getResourcePath() + "/targets/"+(String) targetEndpoint  ; 
			HashMap<String , Object > xx =  this.getManagmentServer().executeGetMgmntAPI(path, HashMap.class) ; 	
			result.put((String) targetEndpoint, xx) ; 
		}
		return result ; 
	}
	
	public ArrayList<String> getTargetEndPontsUsesTargetServer(String m_targetServerName) throws UnirestException, IOException
	{
		ArrayList<String> result = new ArrayList<String>() ; 
		HashMap<String , Object > tepds = 	this.getTargetEndPointDetails() ; 
		for (String name  : tepds.keySet() )
		{
			@SuppressWarnings("unchecked")
			HashMap<String, Object> tepd = (HashMap<String, Object>) tepds.get(name) ;
			Map connection = (Map) tepd.get("connection") ;
			if (connection != null)
			{
				Map loadBalancer = (Map) connection.get("loadBalancer") ;
				if (loadBalancer != null )
				{
					ArrayList<Object> servers = (ArrayList <Object>)loadBalancer.get("server") ;
					if (servers != null )
					{
						String serverName = (String) ((Map)servers.get(0)).get("name") ;   
						if( serverName.equalsIgnoreCase(m_targetServerName) )
						{
							result.add(name) ; 
						}
						
						
					}
				}
			
			}
		}
		return result ; 
	}
	/**
	 * Checks if this proxy revision is using a policy with the given names - useful to check validity of the proxy to conform to standards and uses a shared flow like ELK-Logger 
	 * @param policynames
	 * @return
	 */
	public boolean isUsingPolicy(String[] policynames )
	{
		boolean result = false ; 
		for (String policyname : policynames )
		{
			List<String> polices = this.getPolicies() ; 
			if ( polices.contains(policyname) )
			{
				result = true ; 
				break ; 
			}
		}
		return result;
		
	}

	public String getResourcePath() {
		return "/v1/organizations/"+this.getOrgName()+"/apis/"+this.getName()+"/revisions/" + this.getRevision();
	}

	/**
	 * 
	 * @return the expected minimum (operations without input or output parameters ) openapi specification for this proxy revision based on the defined flows 
	 * @throws UnirestException
	 * @throws IOException
	 */
	public String generateOpenApi() throws UnirestException, IOException 
	{
		StringBuffer result = new StringBuffer() ; 
		String delimiter = "__" ; 
		for ( Entry<String, ProxyEndpoint> entry : this.getProxyEndPointDetails().entrySet()) 
		{
			String proxyEnpointName = entry.getKey();
		    ProxyEndpoint proxyEndPoint = entry.getValue();
		    for (Flow flow : proxyEndPoint.getFlows())
		    {
		    	String operationId = this.getName()+ delimiter +proxyEnpointName+delimiter+flow.getName() ; 
		    	result.append("\n"+operationId) ;
		    	//TODO 
		    }
			System.out.println(proxyEndPoint) ; 
		}
		return result.toString() ; 
	}

	/**
	 * Returns a Specific ProxyEndpoint  
	 * @param proxyEndpointname
	 * @return
	 * @throws UnirestException
	 * @throws IOException
	 */
	public ProxyEndpoint getProxyEndpointsByName(String proxyEndpointname) throws UnirestException, IOException {
		ProxyEndpoint result ; 
		String path = getResourcePath() + "/proxies/"+ proxyEndpointname  ;
		result = this.getManagmentServer().executeGetMgmntAPI(path, ProxyEndpoint.class)  ;
		result.setParentProxyRevision(this);
		return  result ;
	}

/**
 * This method check the OAS consistancy of this ProxyVersion assuming that there is a one OAS flow service that will return all proxyEndpoints flows operation  
 * @param virtualHostUrl
 * @return
 * @throws UnirestException
 * @throws IOException
 */
	public HashMap<Flow, OasOperation> checkFlowsConsistancy (String virtualHostUrl, boolean fixOperationId) throws Exception  
	{
		HashMap<Flow, OasOperation> result = new HashMap<Flow, OasOperation>() ; 
		Flow oasFlow = getOASFlow(OAS_FLOW_NAME) ;
		HttpResponse<String> oasResponse =  oasFlow.call(virtualHostUrl, "") ;
		
		String oasJsonString  = oasResponse.getBody() ;
		SwaggerParseResult swaggerParse = this.parseOpenAPI(oasJsonString) ; 
		OpenAPI openapi = swaggerParse.getOpenAPI() ; 
		Paths paths =  openapi.getPaths(); 
		String oasServerURL = swaggerParse.getOpenAPI().getServers().get(0).getUrl() ; 
		String  serverPath = new URL(oasServerURL).getPath() ; 
		String pathStr ;
		PathItem pathItem ; 
		ArrayList<String> execuldedFlowNames = new ArrayList<String>() ; 
		execuldedFlowNames.add(OAS_FLOW_NAME);
		execuldedFlowNames.add(SERVICE_NOT_AVAILABLE);
		List<Flow> allApigeeFlows = this.getAllFlows(execuldedFlowNames) ;
		  
		for ( Flow flow : allApigeeFlows )
		{
			boolean matched ; 
			for (  Entry<String, PathItem> entry  :  paths.entrySet()) 
			{
				pathStr = entry.getKey() ; 
				pathItem = entry.getValue() ; 
				String completeOasPath = serverPath + pathStr ;  

				OasOperation getOper = new OasOperation( completeOasPath , "GET", pathItem.getGet()) ; 
				if (flow.match(getOper)) { 	
					result.put(flow, getOper) ;
					if (fixOperationId) getOper.setOperationId(flow);
					continue ;	
				}
				
				OasOperation postOper = new OasOperation( completeOasPath , "POST" , pathItem.getPost()) ; 
				if (flow.match(postOper)) { 
					result.put(flow, postOper) ; 
					if (fixOperationId) postOper.setOperationId(flow);
					continue ; 
				}
				
				OasOperation putOper = new OasOperation( completeOasPath , "PUT" , pathItem.getPut()) ; 
				if (flow.match(putOper)) { 
					result.put(flow, putOper) ;  
					if (fixOperationId) putOper.setOperationId(flow);
					continue ; 
				}
				
				OasOperation deleteOper = new OasOperation( completeOasPath , "DELETE" , pathItem.getDelete()) ; 
				if (flow.match(deleteOper)) { 
					result.put(flow, deleteOper) ; 
					if (fixOperationId) deleteOper.setOperationId(flow);
					continue ; } 
				
				OasOperation patchOper = new OasOperation( completeOasPath , "PATCH" , pathItem.getPatch()) ;
				if (flow.match(patchOper)) { 
					result.put(flow, patchOper) ; 
					if (fixOperationId) patchOper.setOperationId(flow);
					continue ; } 
			}
		}
		return result; 
	}
	
	
	/**
	 * 
	 * @param virtualHostUrl
	 * @return an updated version of the 
	 * @throws Exception
	 */
	public HashMap<OasOperation , Flow>  checkOpenApiConsistancy (String virtualHostUrl , boolean fixOperationId) throws Exception  
	{
		HashMap<OasOperation , Flow> result = new HashMap<OasOperation , Flow>(); 
		Flow oasFlow = getOASFlow(OAS_FLOW_NAME) ;
		HttpResponse<String> oasResponse =  oasFlow.call(virtualHostUrl, "") ;
		
		String oasJsonString  = oasResponse.getBody() ;
		SwaggerParseResult swaggerParse = this.parseOpenAPI(oasJsonString) ; 
		OpenAPI openapi = swaggerParse.getOpenAPI() ;  
		Paths 	paths =  openapi.getPaths(); 
		String 	oasServerURL = swaggerParse.getOpenAPI().getServers().get(0).getUrl() ; 
		String 	serverPath = new URL(oasServerURL).getPath() ; 
		String 	pathStr ;
		PathItem pathItem ; 
		ArrayList<String> execuldedFlowNames = new ArrayList<String>() ; 
		execuldedFlowNames.add(OAS_FLOW_NAME);
		execuldedFlowNames.add(SERVICE_NOT_AVAILABLE);
		List<Flow> allApigeeFlows = this.getAllFlows(execuldedFlowNames) ;
		  
		for (  Entry<String, PathItem> entry  :  paths.entrySet()) 
		{
			pathStr = entry.getKey() ; 
			pathItem = entry.getValue() ;
			String completeOasPath = serverPath + pathStr ;
			OasOperation getOper = new OasOperation(completeOasPath , "GET" , pathItem.getGet());
			OasOperation postOper = new OasOperation(completeOasPath , "POST" , pathItem.getPost());
			OasOperation putOper = new OasOperation(completeOasPath , "PUT" , pathItem.getPut());
			OasOperation deleteOper = new OasOperation(completeOasPath , "DELETE", pathItem.getDelete());
			OasOperation patchOper = new OasOperation(completeOasPath ,"PATCH" , pathItem.getPatch());
			
			
			boolean getOperMachedFlowFound = false ;
			boolean postOperMachedFlowFound = false ; 
			boolean putOperMachedFlowFound = false ;
			boolean deleteOperMachedFlowFound = false ;
			boolean patchOperMachedFlowFound = false ;
			for ( Flow flow : allApigeeFlows )
			{
				 
				if (getOper != null)
				{
					if ( flow.match( getOper ) ) 
						{ 
							if (fixOperationId) getOper.getOperation().setOperationId(flow.getUniqueIdentifier());	
							result.put(getOper , flow );
							getOperMachedFlowFound = true ; 
						} 
				}
				
				if (postOper != null)
				{	
					if ( flow.match(postOper ) ) {
						if (fixOperationId) postOper.getOperation().setOperationId(flow.getUniqueIdentifier()); 
						result.put(postOper , flow );
						postOperMachedFlowFound= true ; 
						} 
				}

				if (putOper != null)
				{
					if ( flow.match(putOper ) ) {
						result.put(putOper , flow ); 
						if (fixOperationId) putOper.getOperation().setOperationId(flow.getUniqueIdentifier());
						putOperMachedFlowFound = true ; 
						} 
				}
				
				if (deleteOper != null)
				{
					if ( flow.match(deleteOper ) ) {
						result.put(deleteOper , flow );
						if (fixOperationId) deleteOper.getOperation().setOperationId(flow.getUniqueIdentifier()); 
						deleteOperMachedFlowFound = true ; 
						} 
				}
				
				if (patchOper != null)
				{
					if ( flow.match(patchOper ) ) {
						result.put(patchOper , flow );
						if (fixOperationId) patchOper.getOperation().setOperationId(flow.getUniqueIdentifier()); 
						patchOperMachedFlowFound = true ; 
						} 
				}

			}
			if ( getOper  	 != null && !getOperMachedFlowFound) 	{result.put(getOper , null );}
			if ( postOper 	 != null && !postOperMachedFlowFound)	{result.put(postOper , null );}
			if ( putOper  	 != null && !putOperMachedFlowFound) 	{result.put(putOper , null );}
			if ( deleteOper  != null && !deleteOperMachedFlowFound) {result.put(deleteOper , null );}
			if ( patchOper   != null && !patchOperMachedFlowFound)  {result.put(patchOper , null );}
		
		}
		return result; 
	}
	
	private List<Flow> getAllFlows(ArrayList<String> execludeFlowNames) throws UnirestException, IOException {
		List<Flow> result = new ArrayList<Flow>() ; 
		
		for ( String  pepStr :  this.getProxyEndpoints())
		{
			
			result.addAll(this.getProxyEndpointsByName(pepStr).getFlows(execludeFlowNames ) ); 
		}
		return result;
	}


	private SwaggerParseResult parseOpenAPI(String oasJsonString )
	{
		OpenAPIParser parser = new OpenAPIParser();
		return parser.readContents(oasJsonString , null , null);
	}
	
	
	private Flow getOASFlow(String oasFlowName) throws UnirestException, IOException
	{
		Flow oasFlow = null ; 
		for ( Entry<String, ProxyEndpoint> entry : this.getProxyEndPointDetails().entrySet()) 
		{
		    ProxyEndpoint proxyEndPoint = entry.getValue();
		    oasFlow = proxyEndPoint.getFlowByName(oasFlowName) ; 
		    if (oasFlow != null) break ; 
		}
		return oasFlow;
	}


	public RevisionedObject getParentProxy() {
		return parentProxy;
	}


	public void setParentProxy(RevisionedObject revisionedObject) {
		this.parentProxy = revisionedObject;
	}


	
	
	
}
