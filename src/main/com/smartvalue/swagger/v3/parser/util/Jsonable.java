package com.smartvalue.swagger.v3.parser.util;

import static org.hamcrest.CoreMatchers.equalToObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.TreeMap;
import java.util.UUID;

public interface Jsonable {

	public String toJsonString() throws JsonMappingException, JsonProcessingException; 
	
	public static String appendCommaEnter(boolean needed )
	{
		return ( (needed)? ",\n    ":"" )  ; 
	}
	
		
	private static String toIndentedString(java.lang.Object o) {
	    if (o == null) {
	      return "null";
	    }
	  return o.toString().replace("\n", "\n    ");
	 }

	public static StringBuilder appendElements(StringBuilder sb , FifoMap<String, Object> elements) throws JsonMappingException, JsonProcessingException 
	{
		boolean needComma = false;  
		for (Entry<String, Object> entry : elements.entrySet())
		{
			String objectName = entry.getKey() ; 
			Object value = entry.getValue() ; 
			
			if(value != null ) 
			{ 	
			
				sb.append( Jsonable.appendCommaEnter(needComma)).append("\""+objectName+"\": ") ;
	 			if ( value instanceof Jsonable )
				{ 
	 				boolean doNotJsonFormat = false ; 
	 				Object firstElement ; 
	 				if (value instanceof JsonArrayList  )
	 				{	JsonArrayList xx = (JsonArrayList) value ;  
	 					firstElement = (xx.size()>0 )? xx.get(0) : null ;
	 					doNotJsonFormat = firstElement != null && firstElement instanceof JsonServer ; 
	 				}
	 				
	 				String strJson = ((Jsonable)value).toJsonString() ;
	 				doNotJsonFormat = doNotJsonFormat || strJson.equals("") ; 
 
	 				try {
	 	            	sb.append(toIndentedString((doNotJsonFormat) ? strJson : formatJson(strJson))).append("\n");
	 				}
	 				catch (Exception e) {
	 					System.out.print(strJson);
	 					throw e ; 
					}
	 			} 
				else if ( value instanceof String || value instanceof UUID )
		 		{
					String escapHandledValue = toIndentedString(value)
							.replace("\"", "\\\"")
							.replace("\n", "\\n")
							.replace("\\s", "\\\\s")
							.replace("\\-", "\\\\-") ; 
					sb.append("\"").append(escapHandledValue).append("\"");
		 		}
				else if ( value instanceof Boolean )
				{
					sb.append(toIndentedString(value));
				}
				else if ( value instanceof ArrayList ) 
				{
					ArrayList<?> arrayValue = (ArrayList<?>) value ;
					StringBuilder xx = new StringBuilder();
					xx.append("[ ") ; 
					for (int i = 0 ; i< arrayValue.size() ; i++)
					{
						xx.append( (i>0 )? ",    ":"").append("\"" + arrayValue.get(i) +"\""); 
					}
					xx.append(" ]") ;
					try {
					sb.append(formatJson(xx.toString())) ;
					}
					catch(Exception e)
					{
						System.out.println(xx.toString());
						throw e ; 
					}
				}
				else if (value instanceof ObjectNode )
				{
					sb.append(toIndentedString(value));
				}
				else 
				{
					sb.append(toIndentedString(value)); 
				}
				needComma = true ; 
			}
		}
 		return sb;
	}
	
	private static String formatJson(String m_input) throws JsonMappingException, JsonProcessingException
	{
		try {
		ObjectMapper mapper = new ObjectMapper();
     	ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
     	return writer.writeValueAsString(mapper.readValue( m_input ,  Object.class));
		}
     	catch (Exception e) {
     	e.printStackTrace();
     	throw e ; 
     	}
		
	}
}
