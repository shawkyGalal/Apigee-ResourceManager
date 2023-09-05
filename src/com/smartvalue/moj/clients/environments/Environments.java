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

public class Environments {
 Environment[] Environments ; 
 private String fileContent ;  
	
	private HashMap<String , HashMap<String , HashMap<String , Infra> > > partnersMap = new HashMap<> () ; 

	public Environments (File   configFile) throws FileNotFoundException , IOException
	{
		parseConfigFile(configFile) ; 
	}
	
	public Environments (InputStream inputStream) throws FileNotFoundException , IOException
	{
		parseConfigFileInputStream(inputStream) ; 
	}
	
	public Environments (String  m_apigeeConfigFilePath) throws FileNotFoundException , IOException
	{
		File configFile = new File(m_apigeeConfigFilePath) ;
		parseConfigFile(configFile) ; 
	}
	
	private void parseConfigFile(File configFile) throws IOException {
		FileInputStream inputStream = new FileInputStream(configFile);
		parseConfigFileInputStream(inputStream) ; 
	}
	
	private void parseConfigFileInputStream(InputStream inputStream) throws IOException {
		//FileInputStream inputStream = new FileInputStream(configFile);
		fileContent = readFromInputStream(inputStream) ; 
		Gson gson = new Gson();
		Environments result = null; 
		result = gson.fromJson(fileContent, (Type) Environments.class);
		this.setEnvs(result.getEnvs()); 
	
	}

	private void setEnvs(Environment[] m_envs) {
		this.Environments = m_envs; 
		
	}

	private Environment[] getEnvs() {

		return this.Environments;
	}
	
	public Environment getEnvName(String m_name)
	{
		Environment result = null ; 
		for ( Environment x : this.getEnvs() ) 
		{
			if (x.getName().equalsIgnoreCase(m_name) )
			{	result = x ;break ;  }
		}
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
