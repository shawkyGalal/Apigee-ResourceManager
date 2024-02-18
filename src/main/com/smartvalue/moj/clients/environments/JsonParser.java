package com.smartvalue.moj.clients.environments;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import com.google.gson.Gson;
import com.smartvalue.apigee.configuration.infra.Infra;

public class JsonParser {
 
 private String fileContent ;  
 //private Type typeofT ; 
	
	private HashMap<String , HashMap<String , HashMap<String , Infra> > > partnersMap = new HashMap<> () ; 
	
	public <T> T getObject (File configFile , Class<T> classOfT   ) throws FileNotFoundException , IOException
	{
		return parseConfigFile(configFile , classOfT ) ; 
	}
	
	public <T> T getObject (InputStream inputStream , Class<T> classOfT  ) throws FileNotFoundException , IOException
	{
		return parseConfigFileInputStream(inputStream , classOfT) ; 
	}
	
	public <T> T getObject (String  m_apigeeConfigFilePath , Class<T> classOfT ) throws FileNotFoundException , IOException
	{
		File configFile = new File(m_apigeeConfigFilePath) ;
		return parseConfigFile(configFile , classOfT ) ; 
	}
	
	public <T> T getObject (URL  url  , Class<T> classOfT ) throws FileNotFoundException , IOException
	{
        // Open a connection to the URL
        URLConnection connection = url.openConnection();
        // Get the InputStream
        InputStream inputStream = connection.getInputStream();
        return getObject(inputStream , classOfT ) ; 
	}
	
	public String readURL(URL  url) throws IOException
	{
		URLConnection connection = url.openConnection();
        // Get the InputStream
        InputStream inputStream = connection.getInputStream();
        return readFromInputStream(inputStream) ; 
	}
	private <T> T  parseConfigFile(File configFile , Class<T> classOfT ) throws IOException {
		FileInputStream inputStream = new FileInputStream(configFile);
		return parseConfigFileInputStream(inputStream , classOfT  ) ; 
	}
	
	private <T> T  parseConfigFileInputStream(InputStream inputStream , Class<T> classOfT) throws IOException {
		fileContent = readFromInputStream(inputStream) ; 
		Gson gson = new Gson();
		return gson.fromJson(fileContent, classOfT );  
	
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
