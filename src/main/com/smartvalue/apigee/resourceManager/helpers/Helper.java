package com.smartvalue.apigee.resourceManager.helpers;

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
	
}
