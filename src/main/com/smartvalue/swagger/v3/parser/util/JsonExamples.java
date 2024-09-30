package com.smartvalue.swagger.v3.parser.util;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry ; 


import io.swagger.v3.oas.models.examples.Example;



public class JsonExamples  extends JsonLinkedHashMap<String, Example>
{
	private JsonExample jsonExample ; 
	
	public JsonExamples(Map<String, Example> examples) {
		for (Entry<String, Example> entry : examples.entrySet())
			{ this.put(entry.getKey(), new JsonExample (entry.getValue())) ; }
	}


}
