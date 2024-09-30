package com.smartvalue.swagger.v3.parser.util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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

	

	public String toJsonString() {
		
		
	        
		boolean needComma = false ; 
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        TreeMap<String , Object > elements = new TreeMap<String , Object >() ;
        elements.put("summary", getSummary()) ; 
        elements.put("description", getDescription()) ;
        elements.put("value", getJsonValue()) ;
        elements.put("$ref", get$ref()) ;
        sb = Jsonable.appendElements(sb, elements);
        /*
        if (getSummary() != null) 		{ sb.append( Jsonable.appendCommaEnter(needComma)).append("\"summary\": \"").append(toIndentedString(getSummary())).append("\"\n"); needComma = true ; } 
        if (getDescription() != null) 	{ sb.append( Jsonable.appendCommaEnter(needComma)).append("\"description\": \"").append(toIndentedString(getDescription())).append("\"\n"); needComma = true ;}
        if (getJsonValue() != null)     { sb.append( Jsonable.appendCommaEnter(needComma)).append("\"value\": ").append(toIndentedString(getJsonValue().toJsonString())).append("\n"); needComma = true ; } 
        if (getExternalValue() != null) { sb.append( Jsonable.appendCommaEnter(needComma)).append("\"externalValue\": ").append(toIndentedString(getExternalValue())).append("\n"); needComma = true ; }
        if (get$ref() != null) 			{ sb.append( Jsonable.appendCommaEnter(needComma)).append("\"$ref\": \"").append(toIndentedString(get$ref())).append("\"\n"); needComma = true ;}
        */
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
}
