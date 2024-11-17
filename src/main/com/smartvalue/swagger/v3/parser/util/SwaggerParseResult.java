package com.smartvalue.swagger.v3.parser.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Map.Entry;

import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Flow;
import com.smartvalue.apigee.rest.schema.proxyRevision.OasOperation;
import com.smartvalue.apigee.rest.schema.proxyRevision.ProxyRevision;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;


public class SwaggerParseResult {

	private List<String> messages = null;
    private JsonOpenAPI openAPI;
    private boolean openapi31;

    public SwaggerParseResult messages(List<String> messages) {
        this.messages = messages;
        return this;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public SwaggerParseResult message(String message) {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        messages.add(message);
        return this;
    }

    public SwaggerParseResult addMessages(List<String> messages) {
        if (this.messages == null) {
            this.messages = new ArrayList<>();
        }
        this.messages.addAll(messages);
        return this;
    }

    public JsonOpenAPI getOpenAPI() {
        return openAPI;
    }

    public void setOpenAPI(JsonOpenAPI openAPI) {
        this.openAPI = openAPI;
    }

    public static SwaggerParseResult ofError(String message){
        final SwaggerParseResult result = new SwaggerParseResult();
        result.setMessages(Collections.singletonList(message));
        return result;
    }

    public void setOpenapi31(boolean openapi31) {
        this.openapi31 = openapi31;
    }

    public SwaggerParseResult openapi31(boolean openapi31) {
        this.openapi31 = openapi31;
        return this;
    }

    public boolean isOpenapi31() {
        return this.openapi31;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SwaggerParseResult result = (SwaggerParseResult) o;
        return openapi31 == result.openapi31 && Objects.equals(messages, result.messages) && Objects.equals(openAPI, result.openAPI);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messages, openAPI, openapi31);
    }

	public static SwaggerParseResult CopyFrom(SwaggerParseResult result) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public HashMap<OasOperation , Flow>  checkConsistancyWithProxyFlows (List<Flow> allApigeeFlows , String oasProxyEndPointBasePath ,  boolean fixOperationId) throws Exception
	{
		HashMap<OasOperation , Flow> result = new HashMap<OasOperation , Flow>();
		OpenAPI openapi = this.getOpenAPI() ;
		if (openapi == null) {throw new IllegalArgumentException("Unable to Extract Documentation from Flow : " + ProxyRevision.getOasFlowName()) ; }
		Paths 	paths =  openapi.getPaths(); 
		//String 	oasServerBasePath =  swaggerParse.getOpenAPI().getServers().get(0).getUrl() ; 
		//String 	serverPath = new URL(oasServerBasePath).getPath() ; 
		String 	pathStr ;
		PathItem pathItem ; 
		  
		for (  Entry<String, PathItem> entry  :  paths.entrySet()) 
		{
			pathStr = entry.getKey() ; 
			pathItem = entry.getValue() ;
			String completeOasPath = oasProxyEndPointBasePath + pathStr ;
			OasOperation getOper = 	( pathItem.getGet() != null ) ? new OasOperation(completeOasPath , "GET" , pathItem.getGet()) : null;
			OasOperation postOper =	(pathItem.getPost() != null) ?  new OasOperation(completeOasPath , "POST" , pathItem.getPost()): null;
			OasOperation putOper = 	( pathItem.getPut() != null) ? new OasOperation(completeOasPath , "PUT" , pathItem.getPut()): null;
			OasOperation deleteOper=(pathItem.getDelete() != null) ? new OasOperation(completeOasPath , "DELETE", pathItem.getDelete()): null;
			OasOperation patchOper =(pathItem.getPatch() != null) ? new OasOperation(completeOasPath ,"PATCH" , pathItem.getPatch()):null;
			
			
			boolean getOperMachedFlowFound = false ;
			boolean postOperMachedFlowFound = false ; 
			boolean putOperMachedFlowFound = false ;
			boolean deleteOperMachedFlowFound = false ;
			boolean patchOperMachedFlowFound = false ;
			for ( Flow flow : allApigeeFlows )
			{
				if (flow.extractPathSuffixFromCondition() != null && flow.extractVerbFromCondition() != null)
				{ 
					if (getOper != null)
					{
						if ( flow.match( getOper ) ) 
							{ 
								if (fixOperationId) getOper.updateOperationIdFromFlowIfNeeded(flow);	
								result.put(getOper , flow );
								getOperMachedFlowFound = true ; 
							} 
					}
					
					if (postOper != null)
					{	
						if ( flow.match(postOper ) ) {
							if (fixOperationId) postOper.updateOperationIdFromFlowIfNeeded(flow); 
							result.put(postOper , flow );
							postOperMachedFlowFound= true ; 
							} 
					}
	
					if (putOper != null)
					{
						if ( flow.match(putOper ) ) {
							result.put(putOper , flow ); 
							if (fixOperationId) putOper.updateOperationIdFromFlowIfNeeded(flow); 
							putOperMachedFlowFound = true ; 
							} 
					}
					
					if (deleteOper != null)
					{
						if ( flow.match(deleteOper ) ) {
							result.put(deleteOper , flow );
							if (fixOperationId) deleteOper.updateOperationIdFromFlowIfNeeded(flow); 
							deleteOperMachedFlowFound = true ; 
							} 
					}
					
					if (patchOper != null)
					{
						if ( flow.match(patchOper ) ) {
							result.put(patchOper , flow );
							if (fixOperationId) patchOper.updateOperationIdFromFlowIfNeeded(flow); 
							patchOperMachedFlowFound = true ; 
							} 
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
	
	public  HashMap<Flow, OasOperation> checkFlowsConsistancy ( List<Flow> allApigeeFlows, String oasProxyEndPointBasePath , boolean fixOperationId, boolean execludeKnownFlows ) throws Exception  
	{
		HashMap<Flow, OasOperation> result = new HashMap<Flow, OasOperation>() ;
		OpenAPI openapi = this.getOpenAPI() ; 
		Paths paths =  openapi.getPaths(); 
		//String oasServerURL = swaggerParse.getOpenAPI().getServers().get(0).getUrl() ; 
		//String  oasServerBasePath = new URL(oasServerURL).getPath() ; 
		String pathStr ;
		PathItem pathItem ; 
		  
		for ( Flow flow : allApigeeFlows )
		{
			boolean matchedOperationFound = false ; 
			if (flow.extractPathSuffixFromCondition() != null && flow.extractVerbFromCondition() != null)
			{
			for (  Entry<String, PathItem> entry  :  paths.entrySet()) 
			{
				pathStr = entry.getKey() ; 
				pathItem = entry.getValue() ; 
				String completeOasPath = oasProxyEndPointBasePath + pathStr ;  

				Operation oper = pathItem.getGet() ; 
				if (oper != null)
				{
					OasOperation getOper = new OasOperation( completeOasPath , "GET", oper) ; 
					if ( flow.match(getOper)) { 	
					matchedOperationFound= true; 
					result.put(flow, getOper) ;
					if (fixOperationId) getOper.updateOperationIdFromFlowIfNeeded(flow);
					break ;	
					}
				}
				oper = pathItem.getPost() ; 
				if (oper != null)
				{
					OasOperation postOper = new OasOperation( completeOasPath , "POST" , oper ) ; 
					if (flow.match(postOper)) { 
					matchedOperationFound= true;
					result.put(flow, postOper) ; 
					if (fixOperationId) postOper.updateOperationIdFromFlowIfNeeded(flow);
					break ; 
					}
				}
				oper = pathItem.getPut() ;
				if (oper != null)
				{
					OasOperation putOper = new OasOperation( completeOasPath , "PUT" , oper) ; 
					if (flow.match(putOper)) { 
					matchedOperationFound= true;
					result.put(flow, putOper) ;  
					if (fixOperationId) putOper.updateOperationIdFromFlowIfNeeded(flow);
					break ;	
					}
				}
				oper = pathItem.getDelete() ;
				if (oper != null)
				{
				OasOperation deleteOper = new OasOperation( completeOasPath , "DELETE" , oper) ; 
				if (flow.match(deleteOper)) { 
					matchedOperationFound= true;
					result.put(flow, deleteOper) ; 
					if (fixOperationId) deleteOper.updateOperationIdFromFlowIfNeeded(flow);
					break ; 
					} 
				}
				oper = pathItem.getPatch() ;
				if (oper != null)
				{
				OasOperation patchOper = new OasOperation( completeOasPath , "PATCH" , oper) ;
				if (flow.match(patchOper)) { 
					matchedOperationFound= true;
					result.put(flow, patchOper) ; 
					if (fixOperationId) patchOper.updateOperationIdFromFlowIfNeeded(flow);
					break ; } 
				}
			}
			}
			if (! matchedOperationFound)
			{
				result.put(flow, null) ;
			}
		}
		return result; 
		
	}
}
