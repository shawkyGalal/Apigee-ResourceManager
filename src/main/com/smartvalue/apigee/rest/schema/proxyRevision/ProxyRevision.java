package com.smartvalue.apigee.rest.schema.proxyRevision;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.AppConfig;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.migration.ProcessResults;
import com.smartvalue.apigee.proxyBundle.ProxyBundleParser;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Flow;
import com.smartvalue.apigee.rest.schema.sharedFlow.auto.RevisionedObject;
import com.smartvalue.apigee.validators.ProxyValidator;
import com.smartvalue.swagger.v3.parser.util.OpenAPIDeserializer;
import com.smartvalue.swagger.v3.parser.util.SwaggerParseResult;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.parser.ObjectMapperFactory;

import com.smartvalue.apigee.rest.schema.proxyDeployment.ProxyDeployment;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.ProxyEndpoint;

public class ProxyRevision extends com.smartvalue.apigee.rest.schema.proxyRevision.auto.ProxyRevision{

	private static String OAS_FLOW_NAME = "GetOAS";
	
	public static String getOasFlowName() {
		return OAS_FLOW_NAME;
	}

	public static void setOasFlowName(String oasFlowName) {
		ProxyRevision.OAS_FLOW_NAME = oasFlowName;
	}
	public static final String SERVICE_NOT_AVAILABLE = "ServiceNotAvailable";
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
		return AppConfig.BASE_BATH+this.getManagmentServer().getOrgName()+"/apis/"+this.getName()+"/revisions/" + this.getRevision();
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

	public String  getOasString(String virtualHostUrl ) throws Exception
	{
		Flow oasFlow = getOASFlow() ;
		HttpResponse<String> oasResponse =  oasFlow.call(virtualHostUrl, "") ;
		String result  = oasResponse.getBody() ;
		return result; 
	}
	
	
/**
 * This method check the OAS consistancy of this ProxyVersion assuming that there is a one OAS flow service that will return all proxyEndpoints flows operation  
 * @param virtualHostUrl
 * @return
 * @throws UnirestException
 * @throws IOException
 */
	public HashMap<Flow, OasOperation> checkFlowsConsistancy (String virtualHostUrl, boolean fixOperationId, boolean execludeKnownFlows ) throws Exception  
	{
		String oasJsonString  = getOasString(virtualHostUrl ) ;  
		SwaggerParseResult swaggerParse = this.parseOpenAPI(oasJsonString) ; 
		ArrayList<String> execuldedFlowNames = new ArrayList<String>() ; 
		if (execludeKnownFlows)
		{execuldedFlowNames.add(getOasFlowName()); 
		execuldedFlowNames.add(SERVICE_NOT_AVAILABLE);}
		List<Flow> allApigeeFlows = this.getAllFlows(execuldedFlowNames) ;
		return swaggerParse.checkFlowsConsistancy( allApigeeFlows , this.getOASProxyEndpoint().getConnection().getBasePath(),  fixOperationId , execludeKnownFlows ) ; 
	}
	

	
	
	/**
	 * 
	 * @param virtualHostUrl
	 * @return an updated version of the 
	 * @throws Exception
	 */
	public HashMap<OasOperation , Flow>  checkOpenApiConsistancy (String virtualHostUrl , boolean fixOperationId) throws Exception  
	{
		 
		Flow oasFlow = getOASFlow() ;
		HttpResponse<String> oasResponse =  oasFlow.call(virtualHostUrl, "") ;
		
		String oasJsonString  = oasResponse.getBody() ;
		SwaggerParseResult swaggerParse = this.parseOpenAPI(oasJsonString) ;
		ArrayList<String> execuldedFlowNames = new ArrayList<String>() ; 
		execuldedFlowNames.add(getOasFlowName());
		execuldedFlowNames.add(SERVICE_NOT_AVAILABLE);
		List<Flow> allApigeeFlows = this.getAllFlows(execuldedFlowNames) ;
		ProxyEndpoint oasProxyEndPoint = this.getOASProxyEndpoint() ; 
		return swaggerParse.checkConsistancyWithProxyFlows( allApigeeFlows , oasProxyEndPoint.getConnection().getBasePath() ,  fixOperationId ) ; 
		
	}
	
	private List<Flow> getAllFlows(ArrayList<String> execludeFlowNames) throws UnirestException, IOException {
		List<Flow> result = new ArrayList<Flow>() ; 
		
		for ( String  pepStr :  this.getProxyEndpoints())
		{
			
			result.addAll(this.getProxyEndpointsByName(pepStr).getFlows(execludeFlowNames ) ); 
		}
		return result;
	}


	private SwaggerParseResult parseOpenAPI(String oasJsonString ) throws JsonMappingException, JsonProcessingException
	{
		OpenAPIDeserializer openAPIDeserializer = new OpenAPIDeserializer() ;
		SwaggerParseResult swaggerParseResult = openAPIDeserializer.mydeserialize(ObjectMapperFactory.createJson().readTree(oasJsonString)) ;
		return swaggerParseResult ; 
	}
	
	
	private Flow getOASFlow() throws UnirestException, IOException
	{
		Flow oasFlow = null ; 
		for ( Entry<String, ProxyEndpoint> entry : this.getProxyEndPointDetails().entrySet()) 
		{
		    ProxyEndpoint proxyEndPoint = entry.getValue();
		    oasFlow = proxyEndPoint.getFlowByName(getOasFlowName()) ; 
		    if (oasFlow != null) break ; 
		}
		return oasFlow;
	}
	
	private ProxyEndpoint getOASProxyEndpoint() throws UnirestException, IOException
	{
		ProxyEndpoint result = null ; 
		for ( Entry<String, ProxyEndpoint> entry : this.getProxyEndPointDetails().entrySet()) 
		{
		    ProxyEndpoint proxyEndPoint = entry.getValue();
		    Flow flow  = proxyEndPoint.getFlowByName(getOasFlowName()) ; 
		    if (flow != null) { result = proxyEndPoint ;  break ; } 
		}
		return result ;
	}


	public RevisionedObject getParentProxy() {
		return parentProxy;
	}


	public void setParentProxy(RevisionedObject revisionedObject) {
		this.parentProxy = revisionedObject;
	}


	public ProxyDeployment deploy(String envName) throws UnirestException, IOException {
		ProxyDeployment result ; 
		String path = AppConfig.BASE_BATH +this.getManagmentServer().getOrgName()+"/environments/"+envName+"/apis/"+this.getParentProxy().getName()+"/revisions/"+this.getRevision()+"/deployments" ; 
		result =  this.getManagmentServer().executePostMgmntAPI(path ,  ProxyDeployment.class , null , null) ;
		
		return result ; 
	}

	
	public String export( String folderDest) throws UnirestException, IOException
	{
		String bundleFile ; 
		HttpResponse<InputStream> result = null; 
		String apiPath = getResourcePath()+"?format=bundle" ; 
		ManagementServer ms = this.getManagmentServer() ; 
		result = ms.getGetHttpBinResponse(apiPath ) ;
		bundleFile = folderDest 
				//+ this.getRevision()
				//+ File.separator 
				+ this.getName()+".zip" 
				;
		try{Files.createDirectories(Paths.get(bundleFile).getParent());}catch (Exception e) {	}
		 
		Files.copy(result.getBody(), Paths.get(bundleFile) , StandardCopyOption.REPLACE_EXISTING );
		
		return bundleFile ; 
	}
	
	public ProcessResults validate(UUID uuid) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, FileNotFoundException, IOException
	{
		ProcessResults results = new ProcessResults("Validating Proxy Revision " + this.getName() +"."+ this.getRevision()  , uuid) ; 
		ArrayList<ProxyValidator> validators = this.getManagmentServer().getInfra().buildProxyValidators(); 
		for (ProxyValidator pv : validators )
		{
			results.addAll(pv.validate(this, uuid)) ; 
		}
		
		return results ; 
	}
	


	
	
	
}
