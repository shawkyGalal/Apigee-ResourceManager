package com.smartvalue.moj.clients.environments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ClientEnvironmentsFactory {

	public static Environments create(InputStream inputStream) throws FileNotFoundException, IOException
	{
		//Type envsType = (Type) Environments.class ; 
		JsonParser envParser = new JsonParser(  ) ;
		Environments clientEnvs = (Environments) envParser.getObject(inputStream , Environments.class) ;
		return clientEnvs;
	}
	
	public static Environments create(File file) throws FileNotFoundException, IOException
	{
		//Type envsType = (Type) Environments.class ; 
		JsonParser envParser = new JsonParser(  ) ;
		Environments clientEnvs = (Environments) envParser.getObject(file , Environments.class) ;
		return clientEnvs;
	}
	
	public static Environments create(String filePath) throws FileNotFoundException, IOException
	{
		//Type envsType = (Type) Environments.class ; 
		JsonParser envParser = new JsonParser() ;
		Environments clientEnvs = (Environments) envParser.getObject(filePath , Environments.class) ;
		return clientEnvs;
	} 
	
	
}
