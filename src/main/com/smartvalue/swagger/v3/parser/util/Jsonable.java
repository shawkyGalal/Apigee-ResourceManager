package com.smartvalue.swagger.v3.parser.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public interface Jsonable {

	public String toJsonString(); 
	
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

	public static StringBuilder appendElements(StringBuilder sb , FifoMap<String, Object> elements) 
	{
		boolean needComma = false;  
		for (Entry<String, Object> entry : elements.entrySet())
		{
			String objectName = entry.getKey() ; 
			Object value = entry.getValue() ; 
			
			if(value != null ) 
			{ 	
	 			if ( value instanceof Jsonable )
				{ 
	 				sb.append( Jsonable.appendCommaEnter(needComma)).append("\""+objectName+"\": ").append(toIndentedString(((Jsonable)value).toJsonString())).append("\n"); 
	 			} 
				else if ( value instanceof String )
		 		{
					sb.append( Jsonable.appendCommaEnter(needComma)).append("\""+objectName+"\": \"").append(toIndentedString(value)).append("\"");
		 		}
				else 
				{
					sb.append(value) ; 
				}
				needComma = true ; 
			}
		}
 		return sb;
	}
}
