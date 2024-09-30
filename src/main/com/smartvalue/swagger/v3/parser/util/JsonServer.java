package com.smartvalue.swagger.v3.parser.util;

public class JsonServer extends io.swagger.v3.oas.models.servers.Server  implements Jsonable {

	public JsonServer(io.swagger.v3.oas.models.servers.Server server) {
		
		this.setDescription( server.getDescription()) ; 
		this.setExtensions(server.getExtensions() );
		this.setUrl(server.getUrl()) ; 
		this.setVariables(server.getVariables()); 
	}

	 public String toJsonString() {
	        StringBuilder sb = new StringBuilder();
	        sb.append("{\"url\":").append(toIndentedString("\""+getUrl()+"\""));
	        if(getDescription()!= null) sb.append("    \"description\": ").append(toIndentedString(getDescription()));
	        if(getVariables()!= null) 	sb.append("    \"variables\": ").append(toIndentedString(getVariables()));
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
