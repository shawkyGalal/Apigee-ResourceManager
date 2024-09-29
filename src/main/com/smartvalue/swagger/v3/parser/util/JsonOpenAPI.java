package com.smartvalue.swagger.v3.parser.util;


import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.models.SpecVersion;


public class JsonOpenAPI extends io.swagger.v3.oas.models.OpenAPI implements Jsonable {

	JsonInfo jsonInfo ; 
    public JsonInfo getJsonInfo() {
        return (this.jsonInfo == null)? new JsonInfo(this.getInfo()) : jsonInfo;
    }
    

    private List<JsonServer> servers = null;
    private List<JsonServer> getJsonServers() 
    {
    if (this.servers == null && this.getServers().size()>0 )
	   	{
	    	this.servers = new ArrayList<JsonServer>() ; 
	   		for (io.swagger.v3.oas.models.servers.Server server : this.getServers() )
	   		{
	   			servers.add(new JsonServer(server)) ; 
	   		}
	   	}
    	return servers ; 
	}

    private List<JsonSecurityRequirement> SecurityRequirements ;
    private List<JsonSecurityRequirement> getJsonSecurity() 
    {
    if (this.SecurityRequirements == null && this.getSecurity().size()>0 )
	   	{
	    	this.SecurityRequirements = new ArrayList<JsonSecurityRequirement>() ; 
	   		for (io.swagger.v3.oas.models.security.SecurityRequirement securityRequirement : this.getSecurity() )
	   		{
	   			SecurityRequirements.add(new JsonSecurityRequirement(securityRequirement)) ; 
	   		}
	   	}
    	return SecurityRequirements ; 
	}
    
    private JsonPaths paths ; 
    private JsonPaths getJsonPaths() 
    {
    if (this.paths == null )
	   	{
	    	this.paths = new JsonPaths(this.getPaths()) ; 
	   	}
    	return paths ; 
	}
    
    private JsonComponents components ; 
    private JsonComponents getJsonComponents() 
    {
    	if (this.components == null )
	   	{
	    	this.components = new JsonComponents(this.getComponents()) ; 
	   	}
    return components ; 
	}
    
	public String toJsonString()
	{
		boolean needComma = false ; 
		StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        if (getOpenapi() != null) 				{sb.append("    \"openapi\": \"").append(toIndentedString(getOpenapi())).append("\""); needComma = true ; } 
        if (getJsonInfo() != null ) 			{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"info\": ").append(toIndentedString(getJsonInfo().toJsonString()));  needComma = true ;  } 
        if (getExternalDocs() != null) 			{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"externalDocs\": ").append(toIndentedString(getExternalDocs()));  needComma = true ; }
        if (getJsonServers() != null ) 			{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"servers\": ").append(toIndentedString(getJsonServers()));  needComma = true ; }
        if (getTags() != null ) 				{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"tags\": ").append(toIndentedString(getTags()));  needComma = true ; }
        if (getJsonPaths() != null ) 			{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"paths\": ").append(toIndentedString(getJsonPaths().toJsonString()));  needComma = true ; }
        if (getJsonComponents() != null)		{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"components\": ").append(toIndentedString(getJsonComponents().toJsonString()));  needComma = true ; } 
        if (getJsonSecurity() != null ) 		{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"security\": ").append(toIndentedString(getJsonSecurity() ));  needComma = true ; } 
        if (getSpecVersion() == SpecVersion.V31){sb.append( Jsonable.appendCommaEnter(needComma)).append("\"webhooks\": ").append(toIndentedString(getWebhooks()));   needComma = true ; }
        if (getSpecVersion() == SpecVersion.V31){sb.append( Jsonable.appendCommaEnter(needComma)).append("\"jsonSchemaDialect\": ").append(toIndentedString(getJsonSchemaDialect()));  needComma = true ; }
        sb.append("}");
        return sb.toString(); 
	}
	
	

	/**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    

}
