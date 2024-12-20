package com.smartvalue.swagger.v3.parser.util;

import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.node.ArrayNode;

import io.swagger.v3.oas.models.examples.Example;

public class JsonExample extends Example implements Jsonable {

	private JsonValue jsonValue ; 
	public JsonExample(Example m_example) {
		this.set$ref(m_example.get$ref());
		this.setDescription(m_example.getDescription());
		this.setExtensions(m_example.getExtensions());
		this.setExternalValue(m_example.getExternalValue());
		this.setSummary(m_example.getSummary());
		this.setJsonValue( new JsonValue(m_example.getValue()));
		this.setValueSetFlag(m_example.getValueSetFlag());
	}

	
	private  ArrayNode arrayNode ; 
	public JsonExample(ArrayNode m_arrayNode) {
		this.setArrayNode(arrayNode);
	}


	public String toJsonString() throws JsonMappingException, JsonProcessingException {
		if (this.getArrayNode() != null)
		{
			return this.getArrayNode().toString() ; 
		}
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        FifoMap<String , Object > elements = new FifoMap<String , Object >() ;
        elements.put("summary", getSummary()) ; 
        elements.put("description", getDescription()) ;
        elements.put("value", getJsonValue()) ;
        elements.put("$ref", get$ref()) ;
        sb = Jsonable.appendElements(sb, elements);

        sb.append("}");
        return sb.toString();
    }
	

	/**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private static String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }



	public JsonValue getJsonValue() {
		return jsonValue;
	}



	public void setJsonValue(JsonValue jsonValue) {
		this.jsonValue = jsonValue;
	}



	public ArrayNode getArrayNode() {
		return arrayNode;
	}



	public void setArrayNode(ArrayNode arrayNode) {
		this.arrayNode = arrayNode;
	}
}
