package com.smartvalue.apigee.resourceManager;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.google.api.client.util.ArrayMap;

//import com.google.gson.internal.LinkedTreeMap;

public class Renderer {
	
	
	public static String hashMapWithArraylisttoHtmlTable(HashMap<String , List<Object>> m_object)
	{
		StringBuffer result = new StringBuffer() ;
		result.append("<table border = 1>") ;
		
		for (String key : m_object.keySet() )
		{
			result.append("<tr>") ; 
				result.append("<td>") ; 
				result.append(key) ;
				result.append("</td>") ;
				
				result.append("<td>") ;
				ArrayList<Object> obj = (ArrayList<Object>) m_object.get(key) ; 
				result.append(arrayListToHtmlTable(obj)) ; 
				result.append("</td>") ;
 			result.append("</tr>") ;
		}
		result.append("</table>") ;
		return result.toString();
	}
	
	public static String hashMaptoHtmlTable(AbstractMap<Object , ? extends Object > m_object)
	{
		StringBuffer result = new StringBuffer() ;
		result.append("<table border = 1>") ;
		
		for (Object key : m_object.keySet() )
		{
			result.append("<tr>") ; 
				result.append("<td>") ; 
				result.append(key) ;
				result.append("</td>") ;
				
				result.append("<td>") ;
				Object obj = m_object.get(key) ; 
				if (obj instanceof String || obj instanceof Double || obj instanceof Integer )
				{result.append(obj) ;}
				else if (obj instanceof AbstractMap ) 
				{
					result.append ( hashMaptoHtmlTable((HashMap<Object, ? extends Object>) obj) ) ; 
				}
				else 
				{
					result.append ( objectToHtmlTable(obj) ) ; 
				}
				
				result.append("</td>") ;
 			result.append("</tr>") ;
		}
		
		
		result.append("</table>") ;
		return result.toString();
		
	}
	
	 public static String objectToHtmlTable(Object obj) {
	        StringBuilder htmlTable = new StringBuilder("<table border = 1>");
	        Class<?> clazz = obj.getClass();

	        // Get all fields including inherited ones
	        Field[] fields = getAllFields(clazz);
	         
	        for (Field field : fields) {
	        	if (isModifierAllowed(field))
	        	{
	        		Object value;
	        		String name = field.getName();
	        		try {
	        			
		            	field.setAccessible(true);
		                value = field.get(obj);
		            } catch (Exception e) {
		                value = "N/A";
		            }
	
		            htmlTable.append("<tr><td>").append(name).append("</td><td>");
	
		            if (value != null) {
		            	Class<?> fieldClass = field.getType() ; 
		                if (fieldClass.isPrimitive() || value instanceof String || value instanceof Number) {
		                    htmlTable.append(value);
		                } else if ( value instanceof List  ) {
		                    htmlTable.append(arrayListToHtmlTable((ArrayList) value));
		                } 
		                else if (fieldClass.getName().equalsIgnoreCase("java.util.List") || fieldClass.getName().equalsIgnoreCase("java.util.ArrayList") )
		                {
		                	htmlTable.append ( arrayListToHtmlTable((List<Object>) value) ) ; 
		                	//htmlTable.append("List");
		                }
		                 
		                else if (fieldClass.getName().equalsIgnoreCase("java.util.Map") )
		                {
		                	htmlTable.append(hashMaptoHtmlTable((AbstractMap<Object, Object>) value));
		                }
		                else if (fieldClass.getName().equalsIgnoreCase("java.lang.Boolean") )
		                {
		                	htmlTable.append(value);
		                }
		                else if (fieldClass.getName().equalsIgnoreCase("com.smartvalue.apigee.resourceManager.ManagementServer") )
		                {
		                	htmlTable.append("******");
		                }
		                else {
		                    // Recursively generate HTML table for nested objects
		                    htmlTable.append(objectToHtmlTable(value));
		                }
		            } else {
		                htmlTable.append("null");
		            }
	
		            htmlTable.append("</td></tr>");
		        }
	        }

	        htmlTable.append("</table>");
	        return htmlTable.toString();
	    }
	 
	 private  static boolean isModifierAllowed(Field field) {
	        int modifiers = field.getModifiers();
	        return ! ( Modifier.isFinal(modifiers) || Modifier.isTransient(modifiers) );
	    }
	 
	 public static String generateArrayHtmlTable(ArrayList<Object> array ) {
		 return generateArrayHtmlTable( array , null) ; 
	 }

	    public static String generateArrayHtmlTable(ArrayList<Object> array , HashMap<String , String> extraLinks) {
	    	StringBuilder htmlTable = new StringBuilder("<table border = 1 ><tr><th>Index</th><th>Value</th>"+((extraLinks!=null)? "<th>Extra</th>":"") +"</tr>");
	        int length = array.size() ; // Array.getLength(array);

	        for (int i = 0; i < length; i++) {
	            Object element = array.get(i); // Array.get(array, i);
	            htmlTable.append("<tr><td>").append(i).append("</td><td>");

	            if (element != null) {
	                if (element instanceof List) {
	                    htmlTable.append(generateArrayHtmlTable((ArrayList)element));
	                } else {
	                	htmlTable.append(element);
	                	if (extraLinks != null)
	            		{	htmlTable.append("<td>") ; 
		            		for (String extrLink : extraLinks.keySet() )
		            		{
		            			htmlTable.append("<a href = " +extrLink + element + ">"+extraLinks.get(extrLink)+"</a><br>") ; 
		            		}
		            		htmlTable.append("</td>") ; 
	            		}
	                    
	                }
	            } else {
	                htmlTable.append("null");
	            }

	            htmlTable.append("</td></tr>");
	        }

	        htmlTable.append("</table>");
	        return htmlTable.toString();
	    }
	    
	    public static String arrayListToHtmlTable(List<? extends Object> array ) {
	    	return arrayListToHtmlTable( array , null) ;  
	    }
	    public static String arrayListToHtmlTable(List<? extends Object> array , HashMap<String , String> extraLinks) {
	        StringBuilder htmlTable = new StringBuilder("<table border = 1 ><tr><th>Index</th><th>Value</th>"+((extraLinks!=null)? "<th>Extra</th>":"") +"</tr>");
	        //int length = Array.getLength(array);
	        int i =0 ; 
	        for ( Object element : array )
	        	
	        //for (int i = 0; i < length; i++) 
	        {
	        	i++ ; 
	            //Object element = Array.get(array, i);
	            htmlTable.append("<tr><td>").append(i).append("</td><td>");

	            if (element != null) {
	            	if (element instanceof String) 
	            	{
	            		htmlTable.append(element) ; 
	            		if (extraLinks != null)
	            		{	htmlTable.append("<td>") ; 
		            		for (String extrLink : extraLinks.keySet() )
		            		{
		            			htmlTable.append("<a href = " +extrLink + element + ">"+extraLinks.get(extrLink)+"</a><br>") ; 
		            		}
		            		htmlTable.append("</td>") ; 
	            		}
	            	}
	            	else if (element.getClass().getName().equalsIgnoreCase("java.util.List")) {
	                    htmlTable.append(arrayListToHtmlTable((List<Object>) element, extraLinks) );
	                } else {
	                    htmlTable.append(objectToHtmlTable(element));
	                }
	            } else {
	                htmlTable.append("null");
	            }

	            htmlTable.append("</td></tr>");
	        }

	        htmlTable.append("</table>");
	        return htmlTable.toString();
	    }
	    
	    
	    public static Field[] getAllFields(Class<?> clazz) {
	        List<Field> fields = new ArrayList<>();

	        while (clazz != null) {
	            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
	            clazz = clazz.getSuperclass();
	        }

	        return fields.toArray(new Field[0]);
	    }

}
