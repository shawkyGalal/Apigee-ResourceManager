package com.smartvalue.swagger.v3.parser.util;


public class JsonComponents  extends io.swagger.v3.oas.models.Components implements Jsonable{

	
	 public JsonComponents(io.swagger.v3.oas.models.Components components) {
		this.setCallbacks(components.getCallbacks());
		this.setExamples(components.getExamples());
		this.setExtensions(components.getExtensions());
		this.setHeaders(components.getHeaders());
		this.setLinks(components.getLinks());
		this.setParameters(components.getParameters());
		this.setPathItems(components.getPathItems());
		this.setRequestBodies(components.getRequestBodies());
		this.setResponses(components.getResponses());
		this.setSchemas(components.getSchemas());
		this.setSecuritySchemes(components.getSecuritySchemes());
	}

	public String toJsonString() {
	        StringBuilder sb = new StringBuilder();
	        sb.append("{\n");

	        sb.append("    \"schemas\": ").append(toIndentedString(getSchemas())).append("\n");
	        sb.append("    \"responses\": ").append(toIndentedString(getResponses())).append("\n");
	        sb.append("    \"parameters\": ").append(toIndentedString(getParameters())).append("\n");
	        sb.append("    \"examples\": ").append(toIndentedString(getExamples())).append("\n");
	        sb.append("    \"requestBodies\": ").append(toIndentedString(getRequestBodies())).append("\n");
	        sb.append("    \"headers\": ").append(toIndentedString(getHeaders())).append("\n");
	        sb.append("    \"securitySchemes\": ").append(toIndentedString(getSecuritySchemes())).append("\n");
	        sb.append("    \"links\": ").append(toIndentedString(getLinks())).append("\n");
	        sb.append("    \"callbacks\": ").append(toIndentedString(getCallbacks())).append("\n");
	        sb.append("    \"pathItems\": ").append(toIndentedString(getPathItems())).append("\n");
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
