package com.smartvalue.swagger.v3.parser.util;

public class ExternalDocumentation extends io.swagger.v3.oas.models.ExternalDocumentation {

	 public ExternalDocumentation(io.swagger.v3.oas.models.ExternalDocumentation externalDocs) {
		// TODO Auto-generated constructor stub
	}

	public String toJsonString() {
	        StringBuilder sb = new StringBuilder();
	        sb.append("{\n");

	        sb.append("    \"description\": ").append(toIndentedString(getDescription())).append("\n");
	        sb.append("    \"url\": ").append(toIndentedString(getUrl())).append("\n");
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
