package com.smartvalue.swagger.v3.parser.util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.node.ArrayNode ; 

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
		if (mediaType.getExample() != null)  { 
			this.setExample(mediaType.getExample());
			if ( mediaType.getExample() instanceof Example)
			{
			this.setJsonExample(new JsonExample((Example) mediaType.getExample()));
			}
			else if ( mediaType.getExample() instanceof ArrayNode)
			{
				this.setJsonExample(new JsonExample((ArrayNode) mediaType.getExample()));
			}
			
		} 
		if (mediaType.getExamples() != null) this.setJsonExamples(new JsonExamples(mediaType.getExamples()));
		this.setExampleSetFlag(mediaType.getExampleSetFlag());
		this.setExtensions(mediaType.getExtensions());
		if (mediaType.getSchema()!= null) this.setJsonSchema(new JsonSchema (mediaType.getSchema()));
	}

	public String toJsonString() throws JsonMappingException, JsonProcessingException {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
 
        FifoMap<String , Object > elements = new FifoMap<String , Object >() ;
        elements.put("schema", getJsonSchema()) ; 
        elements.put("examples", getJsonExamples()) ;
        elements.put("example", getExample()) ;
        elements.put("encoding", getEncoding()) ;

        sb = Jsonable.appendElements(sb , elements);
       
        /*
        if (getJsonSchema() != null ) sb.append("    \"schema\": ").append(toIndentedString(getJsonSchema().toJsonString())).append("\n");
        if (getJsonExamples() != null ) sb.append("    \"examples\": ").append(toIndentedString(getJsonExamples().toJsonString())).append("\n");
        if (getExample() != null ) sb.append("    \"example\": ").append(toIndentedString(getExample())).append("\n");
        if (getEncoding() != null ) sb.append("    \"encoding\": ").append(toIndentedString(getEncoding())).append("\n");
        */
        sb.append("}");
        return sb.toString();
    }
	
}
