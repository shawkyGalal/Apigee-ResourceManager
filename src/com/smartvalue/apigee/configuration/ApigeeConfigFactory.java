package com.smartvalue.apigee.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import com.smartvalue.moj.clients.environments.JsonParser;

public class ApigeeConfigFactory {

	public static ApigeeConfig create(InputStream inputStream ) throws FileNotFoundException, IOException
	{
		JsonParser apigeeConfigParser = new JsonParser( ) ;
		ApigeeConfig ac = (ApigeeConfig) apigeeConfigParser.getObject(inputStream , ApigeeConfig.class) ;
		return ac;
	}
	
	public static ApigeeConfig create(File file , Type typeofT ) throws FileNotFoundException, IOException
	{
		//Type apigeeConfigType = (Type) ApigeeConfig.class ;
		JsonParser apigeeConfigParser = new JsonParser() ;
		ApigeeConfig ac = (ApigeeConfig) apigeeConfigParser.getObject(file , ApigeeConfig.class) ;
		return ac;
	}
	
	public static ApigeeConfig create(String filePath ,  Type typeofT) throws FileNotFoundException, IOException
	{
		JsonParser apigeeConfigParser = new JsonParser( ) ;
		ApigeeConfig ac = (ApigeeConfig) apigeeConfigParser.getObject(filePath , ApigeeConfig.class) ;
		return ac;
	} 
}
