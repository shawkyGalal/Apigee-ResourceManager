package com.smartvalue.apigee.rest.schema.virtualHost;

import java.util.HashMap;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;

public class VirtualHost extends com.smartvalue.apigee.rest.schema.virtualHost.auto.VirtualHost {

	public HttpResponse<String> executeVerbRequest ( String m_path,	HashMap<String , String> m_headers,  
																   	HashMap<String , String> m_queryParams , 
																   String m_body , 
																   String m_verb ) throws UnirestException
	{
		String serviceURL = buildUrl (  m_path,  m_queryParams  ) ; 
		HttpRequestWithBody request = null ; 
		switch (m_verb) {  
			case  "POST" : 
			{	request =  Unirest.post(serviceURL.toString()) ;}
			case  "DELETE" : 
			{	request =  Unirest.delete(serviceURL.toString()) ;	}
			case  "PUT" : 
			{	request =  Unirest.put(serviceURL.toString()) ;	}
		}
		request.body(m_body) ;
		request = (HttpRequestWithBody) appendHeaders (request ,  m_headers  );  
		HttpResponse<String> response  = request.asString();
		return response ; 
	}
	
	public HttpResponse<String> executeGetRequest ( String m_path, HashMap<String , String> m_headers,  
																   HashMap<String , String> m_queryParams  
																    ) throws UnirestException
	{
		String serviceURL = buildUrl (  m_path,  m_queryParams  ) ; 
		HttpRequest request = null ; 
		request =  Unirest.get(serviceURL.toString()) ; 
		request = appendHeaders (request ,  m_headers  );  
		HttpResponse<String> response  = request.asString();
		
		return response ; 
	}
	
	private HttpRequest appendHeaders ( HttpRequest request , HashMap<String , String> m_headers  )
	{
		if (m_headers != null)
		{
			for ( String headerName : m_headers.keySet() )
			{	request.header(headerName , m_headers.get(headerName)) ;	}
		}
		return request ; 
	}
	
	private String   buildUrl ( String m_path, HashMap<String , String> m_queryParams  )
	{
		String hostAlias = this.getHostAliases().get(0) ;
		String port = this.getPort() ; 
		boolean secure = this.getsSLInfo().getEnabled().equalsIgnoreCase("true") ; 
		
		StringBuffer serviceURL = new StringBuffer( ( (secure)? "https:" : "http:" )  +"//"  + hostAlias + ":" + port + "/" + m_path ) ;
		int counter = 0 ;  
		if (m_queryParams != null)
		{
			for ( String queryName : m_queryParams.keySet() )
			{
				serviceURL.append((counter ==0 )? "?": "&" + queryName + "=" + m_queryParams.get(queryName)) ;
				counter++; 
			}
		}
		return serviceURL.toString() ; 
	}
	
	private HttpResponse<String>  executeRequest(String m_url , HashMap<String , String> m_headers , String m_verb , String m_body) throws UnirestException
	{
		HttpRequest request = null ; 
		HttpResponse<String> response = null ; 
		if (m_verb.equalsIgnoreCase("GET"))
		{
			request =  Unirest.get(m_url) ; 
			request = appendHeaders (request ,  m_headers  );  
		}
		else 
		{
			HttpRequestWithBody requestWithBody = null ; 
			switch (m_verb) {  
				case  "POST" : 
				{	request =  Unirest.post(m_url) ;}
				case  "DELETE" : 
				{	request =  Unirest.delete(m_url) ;	}
				case  "PUT" : 
				{	request =  Unirest.put(m_url) ;	}
			}
			requestWithBody = (HttpRequestWithBody) appendHeaders (requestWithBody ,  m_headers  );  
			requestWithBody.body(m_body); 

		}
		response = request.asString();
		return response ; 
	}
}
