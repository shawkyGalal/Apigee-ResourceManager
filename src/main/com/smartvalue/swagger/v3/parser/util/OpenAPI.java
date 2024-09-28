package com.smartvalue.swagger.v3.parser.util;


import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.models.SpecVersion;
//import io.swagger.v3.oas.models.servers.Server;


public class OpenAPI extends io.swagger.v3.oas.models.OpenAPI {

	Info info ; 
    public Info getMyInfo() {
        return (this.info == null)? new Info(this.getInfo()) : info;
    }
    

    private List<Server> servers = null;
    private List<Server> getMyServers() 
    {
    if (this.servers == null && this.getServers().size()>0 )
	   	{
	    	this.servers = new ArrayList<Server>() ; 
	   		for (io.swagger.v3.oas.models.servers.Server server : this.getServers() )
	   		{
	   			servers.add(new Server(server)) ; 
	   		}
	   	}
    	return servers ; 
	}

    private List<SecurityRequirement> SecurityRequirements ;
    private List<SecurityRequirement> getMySecurity() 
    {
    if (this.SecurityRequirements == null && this.getSecurity().size()>0 )
	   	{
	    	this.SecurityRequirements = new ArrayList<SecurityRequirement>() ; 
	   		for (io.swagger.v3.oas.models.security.SecurityRequirement securityRequirement : this.getSecurity() )
	   		{
	   			SecurityRequirements.add(new SecurityRequirement(securityRequirement)) ; 
	   		}
	   	}
    	return SecurityRequirements ; 
	}
    
    private Paths paths ; 
    private Paths getMyPaths() 
    {
    if (this.paths == null )
	   	{
	    	this.paths = new Paths(this.getPaths()) ; 
	   	}
    	return paths ; 
	}

    
	public String toJsonString()
	{
		StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        sb.append("    \"openapi\": ").append(toIndentedString(getOpenapi())).append("\n");
        sb.append("    ,\"info\": ").append(toIndentedString(getMyInfo().toJsonString())).append("\n");
        if (getExternalDocs() != null) sb.append("    ,\"externalDocs\": ").append(toIndentedString(getExternalDocs())).append("\n");
        sb.append("    ,\"servers\": ").append(toIndentedString(getMyServers())).append("\n");
        sb.append("    ,\"tags\": ").append(toIndentedString(getTags())).append("\n");
        sb.append("    ,\"paths\": ").append(toIndentedString(getMyPaths().toJsonString())).append("\n");
        sb.append("    ,\"components\": ").append(toIndentedString(getComponents())).append("\n");
        sb.append("    ,\"security\": ").append(toIndentedString(getMySecurity() )).append("\n");
        if (getSpecVersion() == SpecVersion.V31) sb.append("    webhooks: ").append(toIndentedString(getWebhooks())).append("\n");
        if (getSpecVersion() == SpecVersion.V31) sb.append("    jsonSchemaDialect: ").append(toIndentedString(getJsonSchemaDialect())).append("\n");
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
