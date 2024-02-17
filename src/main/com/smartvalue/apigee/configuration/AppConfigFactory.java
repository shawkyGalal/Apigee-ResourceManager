package com.smartvalue.apigee.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import com.smartvalue.moj.clients.environments.JsonParser;

public class AppConfigFactory {

	public static AppConfig create(InputStream inputStream ) throws FileNotFoundException, IOException
	{
		JsonParser apigeeConfigParser = new JsonParser( ) ;
		AppConfig ac = (AppConfig) apigeeConfigParser.getObject(inputStream , AppConfig.class) ;
		return ac;
	}
	
	public static AppConfig create(File file , Type typeofT ) throws FileNotFoundException, IOException
	{
		//Type apigeeConfigType = (Type) ApigeeConfig.class ;
		JsonParser apigeeConfigParser = new JsonParser() ;
		AppConfig ac = (AppConfig) apigeeConfigParser.getObject(file , AppConfig.class) ;
		return ac;
	}
	
	public static AppConfig create(String filePath ,  Type typeofT) throws FileNotFoundException, IOException
	{
		JsonParser apigeeConfigParser = new JsonParser( ) ;
		AppConfig ac = (AppConfig) apigeeConfigParser.getObject(filePath , AppConfig.class) ;
		return ac;
	} 
}
