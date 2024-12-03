package com.smartvalue.apigee.resourceManager.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartvalue.apigee.rest.schema.ApigeeComman;
import com.smartvalue.apigee.rest.schema.product.Product;
import com.smartvalue.apigee.rest.schema.proxy.Proxy;
import com.smartvalue.apigee.rest.schema.server.auto.Property;
import com.smartvalue.apigee.rest.schema.sharedFlow.SharedFlow;

public class Helper {

	public static final String WILD_CARD_OAUTHS_COPE = "Wild_Card_Scope";
	public static final String TEMP_FOLDER = "C:\\temp\\Apigee\\downloads";

	public static boolean isConsideredSuccess(int stausCode)
	{
		return (stausCode == 200 || stausCode == 201 || stausCode == 302 ) ; 
	}
	
	
	
	public static String getPropertyValueFromList( List<Property> m_list , String keyName , String m_searchFor )
	{
		String result = null ; 
		for (int i =0 ; i <= m_list.size() ; i++ )
		{
			String keyname = m_list.get(i).getName() ; 
			if ( keyname.equalsIgnoreCase(m_searchFor))
			{
				result = m_list.get(i).getValue();
				break; 
			}
		}
		return result ;
		
	}
	
	public static Object deSerializeObject(String sourceFile) throws IOException, ClassNotFoundException 
	{
		FileInputStream fis = new FileInputStream(sourceFile);
        try (ObjectInputStream ois = new ObjectInputStream(fis)) {
        	Object result  = ois.readObject(); 
			return result ;
		} 
	}
	
	public static void serialize(String destFile, Object serializableObj ) throws IOException {
		File file = new File(destFile);
        file.getParentFile().mkdirs();
		FileOutputStream fos = new FileOutputStream(destFile);
	    try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(serializableObj);
	    }  
	}
	
	public static String mapObjectToJsonStr(Object obj) throws JsonProcessingException
	{
		ObjectMapper mapper = new ObjectMapper(); 
		return mapper.writeValueAsString(obj);  
	}
	
	public static Class<? extends ApigeeComman> mapObjectTypeToClass(String bundleType) 
	{
		Class<? extends ApigeeComman> clazz = null ; 
		if (bundleType.equalsIgnoreCase("proxies") || bundleType.equalsIgnoreCase("apis")  ) clazz =  Proxy.class ;
		else if (bundleType.equalsIgnoreCase("sharedFlows")) clazz =  SharedFlow.class ;
		else if (bundleType.equalsIgnoreCase("products") || bundleType.equalsIgnoreCase("apiproducts")) clazz =  Product.class ;
		
		return clazz ; 
	}
	
	public static String  mapClassToObjectType(Class<? extends ApigeeComman> clazz) 
	{
		String result = null ; 
		if (clazz ==  Proxy.class)  
		{return "apis" ;	}
		else if (clazz ==  SharedFlow.class)
		{return "sharedflows" ;	}
		else if (clazz ==  Product.class)
		{return "apiproducts" ;	}
		
		return result ; 
	}
	
}
