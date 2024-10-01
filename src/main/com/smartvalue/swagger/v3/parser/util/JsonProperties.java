package com.smartvalue.swagger.v3.parser.util;

import java.util.Map;

import io.swagger.v3.oas.models.media.Schema;

public class JsonProperties extends JsonLinkedHashMap<String, JsonSchema>{

	public JsonProperties(Map<String, Schema> properties) {
		
		for (java.util.Map.Entry<String, Schema> entry : properties.entrySet() ) 
		{
			this.put(entry.getKey(), new JsonSchema(entry.getValue())) ; 
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
