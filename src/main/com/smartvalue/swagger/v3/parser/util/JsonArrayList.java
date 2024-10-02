package com.smartvalue.swagger.v3.parser.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class JsonArrayList<T> extends ArrayList<T> implements Jsonable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JsonArrayList(List<T> values) {
		 for (int i = 0 ; i< values.size() ; i++) 
		 { this.add(values.get(i)) ; }
	}

	public JsonArrayList() {
		super(); 
	}

	@Override
	public String toJsonString() throws JsonMappingException, JsonProcessingException {
		String elementsClassName = null ;
		String result = null ; 
		if(this.size() > 0 )
		{
			elementsClassName = this.get(0).getClass().getName() ; 
		}
		//---Specific Implementation for elements of type JsonServer in a single line 
		if (elementsClassName != null && elementsClassName.equalsIgnoreCase(JsonServer.class.getName()) )
		{
			result = "[" ; 
			for ( int i = 0 ; i< this.size() ; i++)
			{
				result = result + ( (i> 0 )? ",":"") +((Jsonable) this.get(i)).toJsonString(); 
			}
			result = result + "]";
		}
		//------Generic Implementation 
		else {
			result = "[\n" ; 
			for ( int i = 0 ; i< this.size() ; i++)
			{
				boolean isJsonable = this.get(i) instanceof Jsonable ;  
				String  objStrValue ; 
				if (isJsonable)
				{	objStrValue = ((Jsonable) this.get(i)).toJsonString() ; }
				else
				{ objStrValue = "\""+this.get(i).toString()+"\"" ;  }
			
				result = result + ( (i> 0 )? ",\n":"") + objStrValue; 
			}
			result = result + "\n]";
			
		}
		
		return result ; 
	}

	

}
