package com.smartvalue.apigee.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import com.smartvalue.moj.clients.environments.JsonParser;

public class ApigeeConfigFactory {

	public static ApigeeConfig create(InputStream inputStream) throws FileNotFoundException, IOException
	{
		Type apigeeConfigType = (Type) ApigeeConfig.class ;
		JsonParser apigeeConfigParser = new JsonParser( apigeeConfigType ) ;
		ApigeeConfig ac = (ApigeeConfig) apigeeConfigParser.getObject(inputStream) ;
		return ac;
	}
	
	public static ApigeeConfig create(File file) throws FileNotFoundException, IOException
	{
		Type apigeeConfigType = (Type) ApigeeConfig.class ;
		JsonParser apigeeConfigParser = new JsonParser( apigeeConfigType ) ;
		ApigeeConfig ac = (ApigeeConfig) apigeeConfigParser.getObject(file) ;
		return ac;
	}
	
	public static ApigeeConfig create(String filePath) throws FileNotFoundException, IOException
	{
		Type apigeeConfigType = (Type) ApigeeConfig.class ;
		JsonParser apigeeConfigParser = new JsonParser( apigeeConfigType ) ;
		ApigeeConfig ac = (ApigeeConfig) apigeeConfigParser.getObject(filePath) ;
		return ac;
	} 
}
