package com.smartvalue.moj.clients.environments;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.Gson;
import com.smartvalue.apigee.configuration.infra.Infra;

public class JsonParser {
 
 private String fileContent ;  
 private Type typeofT ; 
	
	private HashMap<String , HashMap<String , HashMap<String , Infra> > > partnersMap = new HashMap<> () ; 

	public JsonParser(Type m_typeofT )
	{
		this.typeofT = m_typeofT ; 
	}
	public Object getObject (File   configFile  ) throws FileNotFoundException , IOException
	{
		return parseConfigFile(configFile ) ; 
	}
	
	public Object getObject (InputStream inputStream   ) throws FileNotFoundException , IOException
	{
		return parseConfigFileInputStream(inputStream ) ; 
	}
	
	public Object getObject (String  m_apigeeConfigFilePath ) throws FileNotFoundException , IOException
	{
		File configFile = new File(m_apigeeConfigFilePath) ;
		return parseConfigFile(configFile  ) ; 
	}
	
	private Object parseConfigFile(File configFile  ) throws IOException {
		FileInputStream inputStream = new FileInputStream(configFile);
		return parseConfigFileInputStream(inputStream  ) ; 
	}
	
	private Object parseConfigFileInputStream(InputStream inputStream ) throws IOException {
		fileContent = readFromInputStream(inputStream) ; 
		Gson gson = new Gson();
		Object  result = null;
		result = gson.fromJson(fileContent, typeofT );
		return result ; 
	
	}

	private static String readFromInputStream(InputStream inputStream)
			  throws IOException {
			    StringBuilder resultStringBuilder = new StringBuilder();
			    try (BufferedReader br
			      = new BufferedReader(new InputStreamReader(inputStream))) {
			        String line;
			        while ((line = br.readLine()) != null) {
			            resultStringBuilder.append(line).append("\n");
			        }
			    }
			  return resultStringBuilder.toString();
			}

	public HashMap<String , HashMap<String , HashMap<String , Infra> > > getPartnersMap() {
		return partnersMap;
	}

	public String getFileContent() {
		return fileContent;
	}
	

}
