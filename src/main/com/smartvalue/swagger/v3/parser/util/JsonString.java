package com.smartvalue.swagger.v3.parser.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class JsonString  implements Jsonable{

	String stringValue ; 
	public JsonString(String value) {
		stringValue = value ; 
	}

	@Override
	public String toJsonString() throws JsonMappingException, JsonProcessingException {
		return "\""+stringValue + "\"";
	}

}
