package com.smartvalue.apigee.resourceManager;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
	
	public static String hashMaptoHtmlTable(HashMap<String , ? extends Object > m_object)
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
				Object obj = m_object.get(key) ; 
				if (obj instanceof String)
				{result.append(obj) ;}
				else if (obj instanceof HashMap) 
				{
					result.append ( hashMaptoHtmlTable((HashMap<String, ? extends Object>) obj) ) ; 
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
	        	if (!isPrivateStaticFinal(field))
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
		                } else if (fieldClass.isArray()  ) {
		                    htmlTable.append(generateArrayHtmlTable(value));
		                } 
		                else if (fieldClass.getName().equalsIgnoreCase("java.util.List") || fieldClass.getName().equalsIgnoreCase("java.util.ArrayList") )
		                {
		                	htmlTable.append ( arrayListToHtmlTable((List<Object>) value) ) ; 
		                	//htmlTable.append("List");
		                }
		                else if (fieldClass.getName().equalsIgnoreCase("java.util.Map") )
		                {
		                	htmlTable.append(hashMaptoHtmlTable((HashMap<String, Object>) value));
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
	 
	 private  static boolean isPrivateStaticFinal(Field field) {
	        int modifiers = field.getModifiers();
	        return Modifier.isPrivate(modifiers) 
	        		//&& Modifier.isStatic(modifiers) 
	        		&& Modifier.isFinal(modifiers);
	    }

	    public static String generateArrayHtmlTable(Object array) {
	        StringBuilder htmlTable = new StringBuilder("<table border = 1 ><tr><th>Index</th><th>Value</th></tr>");
	        int length = Array.getLength(array);

	        for (int i = 0; i < length; i++) {
	            Object element = Array.get(array, i);
	            htmlTable.append("<tr><td>").append(i).append("</td><td>");

	            if (element != null) {
	                if (element.getClass().isArray()) {
	                    htmlTable.append(generateArrayHtmlTable(element));
	                } else {
	                    htmlTable.append(element);
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
