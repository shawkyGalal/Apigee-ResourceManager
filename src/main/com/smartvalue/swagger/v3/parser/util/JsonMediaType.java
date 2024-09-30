package com.smartvalue.swagger.v3.parser.util;

import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.MediaType;

public class JsonMediaType extends io.swagger.v3.oas.models.media.MediaType implements Jsonable 

{
	private JsonSchema  jsonSchema ;  
	public JsonSchema getJsonSchema() {
		return jsonSchema;
	}

	public void setJsonSchema(JsonSchema jsonSchema) {
		this.jsonSchema = jsonSchema;
	}
	
	private JsonExamples jsonExamples ; 
	public JsonExamples getJsonExamples() {
		return jsonExamples;
	}

	public void setJsonExamples(JsonExamples jsonExample) {
		this.jsonExamples = jsonExample;
	}
	
	private JsonExample jsonExample ;
	public JsonExample getJsonExample() {
		return jsonExample;
	}

	public void setJsonExample(JsonExample jsonExample) {
		this.jsonExample = jsonExample;
	}

	
	
	public JsonMediaType(MediaType mediaType) {
		// TODO Auto-generated constructor stub
		this.setEncoding(mediaType.getEncoding());
		this.setJsonExample(new JsonExample((Example) mediaType.getExample()));
		this.setJsonExamples(new JsonExamples(mediaType.getExamples()));
		this.setExampleSetFlag(mediaType.getExampleSetFlag());
		this.setExtensions(mediaType.getExtensions());
		this.setJsonSchema(new JsonSchema (mediaType.getSchema()) );
	}

	public String toJsonString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        if (getJsonSchema() != null ) sb.append("    \"schema\": ").append(toIndentedString(getJsonSchema().toJsonString())).append("\n");
        if (getJsonExamples() != null ) sb.append("    \"examples\": ").append(toIndentedString(getJsonExamples().toJsonString())).append("\n");
        if (getExample() != null ) sb.append("    \"example\": ").append(toIndentedString(getExample())).append("\n");
        if (getEncoding() != null ) sb.append("    \"encoding\": ").append(toIndentedString(getEncoding())).append("\n");
        sb.append("}");
        return sb.toString();
    }

	 
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

	

	
}
