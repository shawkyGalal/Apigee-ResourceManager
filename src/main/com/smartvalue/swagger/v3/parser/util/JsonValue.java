package com.smartvalue.swagger.v3.parser.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonValue implements Jsonable {

	Object value ; 
	public JsonValue(Object m_value) {
		this.value = m_value ; 
	}

	@Override
	public String toJsonString() {
		String result = "\"\"" ; 
		if (value != null)
		{
			try {
	            ObjectMapper mapper = new ObjectMapper();
	            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
	            result =  writer.writeValueAsString(mapper.readValue(this.value.toString(),  Object.class));
	        } catch (Exception e) {
	            e.printStackTrace();
	            result = (this.value != null)? this.value.toString() : null; 
	        }
		}
		else
		{
			// System.out.println("xxx");
		}
		return result;
	}

}
