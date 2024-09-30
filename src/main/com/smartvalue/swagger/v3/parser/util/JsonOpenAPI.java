package com.smartvalue.swagger.v3.parser.util;


import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import io.swagger.v3.oas.models.tags.Tag ;
import io.swagger.v3.oas.models.SpecVersion;


public class JsonOpenAPI extends io.swagger.v3.oas.models.OpenAPI implements Jsonable {

	JsonInfo jsonInfo ; 
    public JsonInfo getJsonInfo() {
        return (this.jsonInfo == null)? new JsonInfo(this.getInfo()) : jsonInfo;
    }
    

    private JsonArrayList<JsonServer> servers = null;
    private JsonArrayList<JsonServer> getJsonServers() 
    {
    if (this.servers == null && this.getServers() !=null && this.getServers().size()>0 )
	   	{
	    	this.servers = new JsonArrayList<JsonServer>() ; 
	   		for (io.swagger.v3.oas.models.servers.Server server : this.getServers() )
	   		{
	   			servers.add(new JsonServer(server)) ; 
	   		}
	   	}
    	return servers ; 
	}

    private JsonArrayList<JsonSecurityRequirement> jsonSecurityRequirements ;
    private JsonArrayList<JsonSecurityRequirement> getJsonSecurity() 
    {
    if (this.jsonSecurityRequirements == null && this.getSecurity().size()>0 )
	   	{
	    	this.jsonSecurityRequirements = new JsonArrayList<JsonSecurityRequirement>() ; 
	   		for (io.swagger.v3.oas.models.security.SecurityRequirement securityRequirement : this.getSecurity() )
	   		{
	   			jsonSecurityRequirements.add(new JsonSecurityRequirement(securityRequirement)) ; 
	   		}
	   	}
    	return jsonSecurityRequirements ; 
	}
    
    private JsonPaths paths ; 
    private JsonPaths getJsonPaths() 
    {
    	if (this.paths == null )
	   	{ this.paths = new JsonPaths(this.getPaths()) ; }
    	return paths ; 
	}
    
    private JsonComponents components ; 
    private JsonComponents getJsonComponents() 
    {
    	if (this.components == null )
	   	{ this.components = new JsonComponents(this.getComponents()) ; }
    	return components ; 
	}
    
    private JsonArrayList<Tag> jsonTags ; 
    public JsonArrayList<Tag> getJsonTags() {
		if (this.jsonTags == null && this.getTags() !=null && this.getTags().size()>0)
		{
			this.jsonTags = new JsonArrayList<Tag>() ; 
			for (int i = 0 ; i< this.getTags().size() ; i++)
			{jsonTags.add(this.getTags().get(i)) ; }
		}
    	return jsonTags;
	}

    
	public String toJsonString()
	{
		boolean needComma = false ; 
		StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        /*
        TreeMap<String , Object > elements = new TreeMap<String , Object >() ;
        elements.put("openapi", getOpenapi()) ; 
        elements.put("info", getJsonInfo()) ;
        elements.put("externalDocs", getExternalDocs()) ;
        elements.put("servers", getJsonServers()) ;
        elements.put("tags", getJsonTags()) ;
        elements.put("paths", getJsonPaths()) ;
        elements.put("components", getJsonComponents()) ;
        elements.put("security", getJsonSecurity()) ;
        
        sb = Jsonable.appendElements(sb, elements);
        */
       
        if (getOpenapi() != null) 				{sb.append("    \"openapi\": \"").append(toIndentedString(getOpenapi())).append("\""); needComma = true ; } 
        if (getJsonInfo() != null ) 			{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"info\": ").append(toIndentedString(getJsonInfo().toJsonString()));  needComma = true ;  } 
        if (getExternalDocs() != null) 			{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"externalDocs\": ").append(toIndentedString(getExternalDocs()));  needComma = true ; }
        if (getJsonServers() != null ) 			{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"servers\": ").append(toIndentedString(getJsonServers().toJsonString()));  needComma = true ; }
        if (getJsonTags() != null ) 			{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"tags\": ").append(toIndentedString(getJsonTags().toJsonString()));  needComma = true ; }
        if (getJsonPaths() != null ) 			{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"paths\": ").append(toIndentedString(getJsonPaths().toJsonString()));  needComma = true ; }
        if (getJsonComponents() != null)		{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"components\": ").append(toIndentedString(getJsonComponents().toJsonString()));  needComma = true ; } 
        if (getJsonSecurity() != null ) 		{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"security\": ").append(toIndentedString(getJsonSecurity().toJsonString() ));  needComma = true ; } 
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
