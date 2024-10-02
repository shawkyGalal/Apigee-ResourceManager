package com.smartvalue.swagger.v3.parser.util;

import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.swagger.v3.oas.models.annotations.OpenAPI31;
import io.swagger.v3.oas.models.responses.ApiResponse;


public class JsonApiResponses extends JsonLinkedHashMap<String, JsonApiResponse> implements Jsonable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JsonApiResponses(io.swagger.v3.oas.models.responses.ApiResponses responses) {
		for (java.util.Map.Entry<String, ApiResponse> entry  :  responses.entrySet() )
		{
			this.put(entry.getKey() , new JsonApiResponse(entry.getValue())) ; 
		}
		if (responses.getDefault() != null ) this.setDefault( new JsonApiResponse (responses.getDefault()));
		if (responses.getExtensions() != null ) this.setExtensions(responses.getExtensions());
	}
	
	public static final String DEFAULT = "default";

    private java.util.Map<String, Object> extensions = null;

    public JsonApiResponses addApiResponse(String name, JsonApiResponse item) {
        this.put(name, item);
        return this;
    }

    /**
     * returns the default property from a ApiResponses instance.
     *
     * @return ApiResponse _default
     **/

    public JsonApiResponse getDefault() {
        return this.get(DEFAULT);
    }

    public void setDefault(JsonApiResponse _default) {
        addApiResponse(DEFAULT, _default);
    }

    public JsonApiResponses _default(JsonApiResponse _default) {
        setDefault(_default);
        return this;
    }

    public java.util.Map<String, Object> getExtensions() {
        return extensions;
    }

    public void addExtension(String name, Object value) {
        if (name == null || name.isEmpty() || !name.startsWith("x-")) {
            return;
        }
        if (this.extensions == null) {
            this.extensions = new java.util.LinkedHashMap<>();
        }
        this.extensions.put(name, value);
    }

    @OpenAPI31
    public void addExtension31(String name, Object value) {
        if (name != null && (name.startsWith("x-oas-") || name.startsWith("x-oai-"))) {
            return;
        }
        addExtension(name, value);
    }

    public void setExtensions(java.util.Map<String, Object> extensions) {
        this.extensions = extensions;
    }

    public JsonApiResponses extensions(java.util.Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        JsonApiResponses apiResponses = (JsonApiResponses) o;
        return Objects.equals(this.extensions, apiResponses.extensions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), extensions);
    }

   

    

	@Override
	 public String toJsonString() throws JsonMappingException, JsonProcessingException {
        StringBuilder sb = new StringBuilder();
        //sb.append("{\n");
        if (this.size() > 0 ) sb.append("    ").append(toIndentedString(super.toJsonString())).append("\n");
        if (getExtensions() != null ) sb.append("    \"extensions\": ").append(toIndentedString(getExtensions())).append("\n");
        //sb.append("}");
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
