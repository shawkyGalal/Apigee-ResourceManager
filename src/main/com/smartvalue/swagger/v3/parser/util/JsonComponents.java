package com.smartvalue.swagger.v3.parser.util;

import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.swagger.v3.oas.models.parameters.RequestBody;

public class JsonComponents  extends io.swagger.v3.oas.models.Components implements Jsonable{

	private JsonSchemas jsonSchemas ;
	private JsonLinkedHashMap<String, JsonRequestBody> jsonRequestBodies ; 
	
	public JsonComponents(io.swagger.v3.oas.models.Components components) {
		this.setCallbacks(components.getCallbacks());
		this.setExamples(components.getExamples());
		this.setExtensions(components.getExtensions());
		this.setHeaders(components.getHeaders());
		this.setLinks(components.getLinks());
		this.setParameters(components.getParameters());
		this.setPathItems(components.getPathItems());
		if( components.getRequestBodies() != null  )  
		{
			this.setRequestBodies(components.getRequestBodies()); 
			if ( components.getRequestBodies().size() > 0 )
			{
				jsonRequestBodies = new JsonLinkedHashMap<String, JsonRequestBody>() ; 
				for ( Entry<String, RequestBody> entry : components.getRequestBodies().entrySet() )
				{
					jsonRequestBodies.put(entry.getKey(), new JsonRequestBody(entry.getValue())) ; 
				}
			}
		}
		this.setResponses(components.getResponses());
		this.setJsonSchemas(new JsonSchemas(components.getSchemas()));
		this.setSecuritySchemes(components.getSecuritySchemes());
	}

	public String toJsonString() throws JsonMappingException, JsonProcessingException {
	        StringBuilder sb = new StringBuilder();
	        sb.append("{\n");
	        
	        FifoMap<String , Object > elements = new FifoMap<String , Object >() ;
	        elements.put("schemas", getJsonSchemas()) ; 
	        elements.put("responses", getResponses()) ;
	        elements.put("parameters", getParameters()) ;
	        elements.put("examples", getExamples()) ;
	        elements.put("requestBodies", getJsonRequestBodies()) ;
	        elements.put("headers", getHeaders()) ;
	        elements.put("securitySchemes", getSecuritySchemes()) ;
	        elements.put("links", getLinks()) ;
	        elements.put("callbacks", getCallbacks()) ;
	        elements.put("pathItems", getPathItems()) ;

	        sb = Jsonable.appendElements(sb , elements);
	        
	        /*
	        if (getJsonSchemas() != null ) 	sb.append("    \"schemas\": ").append(toIndentedString(getJsonSchemas().toJsonString())).append("\n");
	        if (getResponses() != null ) 	sb.append("    \"responses\": ").append(toIndentedString(getResponses())).append("\n");
	        if (getParameters() != null ) 	sb.append("    \"parameters\": ").append(toIndentedString(getParameters())).append("\n");
	        if (getExamples() != null ) 	sb.append("    \"examples\": ").append(toIndentedString(getExamples())).append("\n");
	        if (getJsonRequestBodies() != null ) sb.append("    \"requestBodies\": ").append(toIndentedString(getJsonRequestBodies().toJsonString())).append("\n");
	        if (getHeaders() != null ) sb.append("    \"headers\": ").append(toIndentedString(getHeaders())).append("\n");
	        if (getSecuritySchemes() != null ) sb.append("    \"securitySchemes\": ").append(toIndentedString(getSecuritySchemes())).append("\n");
	        if (getLinks() != null ) sb.append("    \"links\": ").append(toIndentedString(getLinks())).append("\n");
	        if (getCallbacks() != null ) sb.append("    \"callbacks\": ").append(toIndentedString(getCallbacks())).append("\n");
	        if (getPathItems() != null ) sb.append("    \"pathItems\": ").append(toIndentedString(getPathItems())).append("\n");
	        */
	        
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

	public JsonLinkedHashMap<String, JsonRequestBody> getJsonRequestBodies() {
		return jsonRequestBodies;
	}

	public void setJsonRequestBodies(JsonLinkedHashMap<String, JsonRequestBody> jsonRequestBodies) {
		this.jsonRequestBodies = jsonRequestBodies;
	}

	
	 
}
