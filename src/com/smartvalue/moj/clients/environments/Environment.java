package com.smartvalue.moj.clients.environments;

import java.nio.charset.Charset;
import java.util.HashMap;

import org.springframework.security.crypto.codec.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.smartvalue.apigee.rest.schema.ApigeeAccessToken;

public class Environment extends com.smartvalue.moj.clients.environments.auto.Environment {

	private ApigeeAccessToken accessToken = null ; 
	public HttpResponse<String>  executeRequest(HashMap<String , String> m_headers , String m_verb , String m_body) throws UnirestException
	{
		String m_url = this.getUrl() ;
		return executeRequest( m_url , m_headers ,  m_verb , m_body) ;  
	}
	
	public HttpResponse<String>  executeRequest( String m_url , HashMap<String , String> m_headers , String m_verb , String m_body) throws UnirestException
	{
		HttpRequest request = null ; 
		HttpResponse<String> response = null ; 
		if (m_verb.equalsIgnoreCase("GET"))
		{
			request =  Unirest.get(m_url) ; 
			request = appendHeaders (request ,  m_headers  );
			if (! m_headers.containsKey("Authorization"))
			{
				request.header("Authorization" , "Bearer" +this.accessToken.getAccess_token()) ; 
			}
			response = request.asString();
		}
		else 
		{
			HttpRequestWithBody requestWithBody = null ; 
			switch (m_verb) {  
				case  "POST": 
				{	requestWithBody =  Unirest.post(m_url) ; 
				break; 
				}
				case  "DELETE": 
				{	requestWithBody =  Unirest.delete(m_url) ; break;	}
				case  "PUT": 
				{	requestWithBody =  Unirest.put(m_url) ;	break; }
			}
			requestWithBody = (HttpRequestWithBody) appendHeaders (requestWithBody ,  m_headers  );
			if (! m_headers.containsKey("Authorization"))
			{
				requestWithBody.header("Authorization" , "Bearer" +this.accessToken.getAccess_token()) ; 
			}
			requestWithBody.body(m_body);
			response = requestWithBody.asString();
		}
		
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
	
	private  HttpResponse<String>  generateAccessToken() throws UnirestException
	{
		String authorization = "Basic "+new String(Base64.encode((this.getCredential().getClientId() + ":" + this.getCredential().getClientSecret()).getBytes()), Charset.forName("UTF-8")) ;  
		HashMap<String, String> headers = new HashMap<String, String>() ; 
		headers.put("Authorization" , authorization) ; 
		headers.put("Content-Type" , "application/x-www-form-urlencoded") ;
		HttpResponse<String> result = executeRequest ( this.getTokenUrl(), headers , "POST" , "") ; 
		return result ; 
	}

	public ApigeeAccessToken getAccessToken() throws JsonSyntaxException, UnirestException {
		if (this.accessToken == null )
		{
			Gson gson = new Gson(); 
			this.accessToken =  gson.fromJson( generateAccessToken().getBody() , ApigeeAccessToken.class ) ;  
		}
		return accessToken;
	}
}
