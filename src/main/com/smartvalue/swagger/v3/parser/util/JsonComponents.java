package com.smartvalue.swagger.v3.parser.util;


public class JsonComponents  extends io.swagger.v3.oas.models.Components implements Jsonable{

	private JsonSchemas jsonSchemas ;
	
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
		this.setJsonSchemas(new JsonSchemas(components.getSchemas()));
		this.setSecuritySchemes(components.getSecuritySchemes());
	}

	public String toJsonString() {
	        StringBuilder sb = new StringBuilder();
	        sb.append("{\n");

	        if (getJsonSchemas() != null ) sb.append("    \"schemas\": ").append(toIndentedString(getJsonSchemas().toJsonString())).append("\n");
	        if (getResponses() != null ) sb.append("    \"responses\": ").append(toIndentedString(getResponses())).append("\n");
	        if (getParameters() != null ) sb.append("    \"parameters\": ").append(toIndentedString(getParameters())).append("\n");
	        if (getExamples() != null ) sb.append("    \"examples\": ").append(toIndentedString(getExamples())).append("\n");
	        if (getRequestBodies() != null ) sb.append("    \"requestBodies\": ").append(toIndentedString(getRequestBodies())).append("\n");
	        if (getHeaders() != null ) sb.append("    \"headers\": ").append(toIndentedString(getHeaders())).append("\n");
	        if (getSecuritySchemes() != null ) sb.append("    \"securitySchemes\": ").append(toIndentedString(getSecuritySchemes())).append("\n");
	        if (getLinks() != null ) sb.append("    \"links\": ").append(toIndentedString(getLinks())).append("\n");
	        if (getCallbacks() != null ) sb.append("    \"callbacks\": ").append(toIndentedString(getCallbacks())).append("\n");
	        if (getPathItems() != null ) sb.append("    \"pathItems\": ").append(toIndentedString(getPathItems())).append("\n");
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

	public JsonSchemas getJsonSchemas() {
		return jsonSchemas;
	}

	public void setJsonSchemas(JsonSchemas jsonSchemas) {
		this.jsonSchemas = jsonSchemas;
	}

	
	 
}
