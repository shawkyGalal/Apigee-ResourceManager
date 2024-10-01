package com.smartvalue.swagger.v3.parser.util;

import java.util.Map;

import io.swagger.v3.oas.models.media.Schema;

public class JsonSchemas extends JsonLinkedHashMap<String , JsonSchema>{

	public JsonSchemas(Map<String, Schema> schemas) {
		for (java.util.Map.Entry<String, Schema> entry : schemas.entrySet() ) 
		{
			this.put(entry.getKey(), new JsonSchema(entry.getValue())) ; 
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
