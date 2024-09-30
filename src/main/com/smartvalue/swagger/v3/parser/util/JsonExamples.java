package com.smartvalue.swagger.v3.parser.util;

import java.util.Map;
import java.util.Map.Entry ; 


import io.swagger.v3.oas.models.examples.Example;



public class JsonExamples  extends JsonLinkedHashMap<String, Example>
{
	private JsonExample jsonExample ; 
	
	public JsonExamples(Map<String, Example> examples) {
		for (Entry<String, Example> entry : examples.entrySet())
			{   
				String key = entry.getKey(); 
				Example example =  entry.getValue() ; 
				this.put( key, new JsonExample (example)) ; 
			}
	}


}
