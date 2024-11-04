package com.smartvalue.apigee.resourceManager.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import com.smartvalue.apigee.rest.schema.server.auto.Property;

public class Helper {

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
	
}
