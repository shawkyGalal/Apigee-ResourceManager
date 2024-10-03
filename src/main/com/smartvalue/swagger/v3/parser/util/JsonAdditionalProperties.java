package com.smartvalue.swagger.v3.parser.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

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
	public String toJsonString() throws JsonMappingException, JsonProcessingException {
		StringBuilder sb  = new StringBuilder(); 
		sb.append("{ \"value\" : ")  ;
		if (value != null )
		{
			if (value instanceof Jsonable ) 
				sb.append (((Jsonable) value).toJsonString() );
			else 
			{ 
				throw new JsonMappingException("JsonAdditionalProperties Object Conatins " + value.getClass() +" Wchis is Not Jsonable Implementation ") ; 
			}
		}
		else 
			sb.append ("\"\"");
		
		sb.append("}") ; 
		return sb.toString();
	}

}
