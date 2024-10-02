package com.smartvalue.swagger.v3.parser.util;

import java.util.LinkedHashMap;
import java.util.Map.Entry ;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException; 

public class JsonLinkedHashMap<k, v> extends LinkedHashMap<k, v > implements Jsonable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String toJsonString() throws JsonMappingException, JsonProcessingException {
        StringBuilder sb = new StringBuilder();
        sb.append(toIndentedString("{\n"));
        int counter = 0 ; 
        int size = this.size() ; 
        for ( Entry<k, v> entry : this.entrySet() )
        {
        	counter ++; 
        	sb.append("\"").append(toIndentedString(entry.getKey())).append("\" :") ;
        	Jsonable jsonableObject  = (Jsonable) entry.getValue() ; 
        	String jsonStr = jsonableObject.toJsonString() ; 
        	sb.append("    \n").append(toIndentedString(jsonStr)) ;
        	if (counter < size ) sb.append(",").append(" \n") ; 
        }

        sb.append( toIndentedString("\n}"));
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
