package com.smartvalue.swagger.v3.parser.util;

import io.swagger.v3.oas.models.media.Schema;

public class JsonAdditionalProperties implements Jsonable {

	Object value ; 
	public JsonAdditionalProperties(Object additionalProperties) {
		if (additionalProperties instanceof Schema )
		{
			value = new JsonSchema((Schema<?>) additionalProperties) ; 
		}
		else 
		{
			
		}
	}

	@Override
	public String toJsonString() {
		String result = "" ; 
		if (value instanceof Jsonable ) result = ((Jsonable) value).toJsonString() ; 
		return result;
	}

}
