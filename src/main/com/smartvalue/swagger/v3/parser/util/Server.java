package com.smartvalue.swagger.v3.parser.util;

public class Server extends io.swagger.v3.oas.models.servers.Server  {

	public Server(io.swagger.v3.oas.models.servers.Server server) {
		
		this.setDescription( server.getDescription()) ; 
		this.setExtensions(server.getExtensions() );
		this.setUrl(server.getUrl()) ; 
		this.setVariables(server.getVariables()); 
	}

	 public String toString() {
	        StringBuilder sb = new StringBuilder();
	        sb.append("{\n");
	        sb.append("    \"url\": ").append(toIndentedString(getUrl())).append("\n");
	        if(getDescription()!= null) sb.append("    \"description\": ").append(toIndentedString(getDescription())).append("\n");
	        if(getVariables()!= null) sb.append("    \"variables\": ").append(toIndentedString(getVariables())).append("\n");
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
