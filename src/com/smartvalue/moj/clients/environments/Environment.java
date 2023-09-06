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
import com.smartvalue.apigee.resourceManager.helpers.Helper;
import com.smartvalue.apigee.rest.schema.ApigeeAccessToken;

public class Environment extends com.smartvalue.moj.clients.environments.auto.Environment {

	private ApigeeAccessToken accessToken = null ; 
	//private ApigeeAccessToken accessToken = null ;
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
		
		if (! Helper.isConsideredSuccess(response.getStatus()) )
		{
			if (response.getBody().contains("Access Token expired"))
			{
				this.reNewAccessToken() ; 
				response = executeRequest( m_url ,  m_headers , m_verb , m_body) ; 
			}
		}
		
		return response ; 
	}
	
	private void reNewAccessToken() throws JsonSyntaxException, UnirestException {
		this.accessToken = generateCientAccessToken() ; 
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
	
	private  ApigeeAccessToken  generateCientAccessToken() throws UnirestException
	{
		ApigeeAccessToken at ; 
		String authorization =  getClientBasicAuth() ; 
		HashMap<String, String> headers = new HashMap<String, String>() ; 
		headers.put("Authorization" , authorization) ; 
		headers.put("Content-Type" , "application/x-www-form-urlencoded") ;
		HttpResponse<String> response = executeRequest ( this.getTokenUrl(), headers , "POST" , "") ;
		handleReponseError(response) ; 
		Gson gson = new Gson(); 
		at =  gson.fromJson( response.getBody() , ApigeeAccessToken.class ) ;
		return at ; 
	}
	
	private String getClientBasicAuth()
	{
		return "Basic "+new String(Base64.encode((this.getCredential().getClientId() + ":" + this.getCredential().getClientSecret()).getBytes()), Charset.forName("UTF-8")) ;
	}
	
	private void handleReponseError(HttpResponse<String> response) throws UnirestException
	{
		int statusCode = response.getStatus() ; 
		if (! Helper.isConsideredSuccess(statusCode) )
		{
			throw new UnirestException ( "API Failure response with Error Code : " + statusCode +", Response : "+ response.getBody() ) ;
		}
	}

	public ApigeeAccessToken getAccessToken(boolean renew) throws JsonSyntaxException, UnirestException {
		if (this.accessToken == null || renew )
		{
			ApigeeAccessToken at = generateCientAccessToken() ;
			accessToken = at;
		}
		return accessToken ;  
		
	}
	public ApigeeAccessToken getAccessToken(String m_authCode) throws JsonSyntaxException, UnirestException 
	{
		Unirest.setTimeouts(0, 0);
		HttpResponse<String> response = Unirest.post(this.getNafath().getTokenUrl())
		  .header("Content-Type", "application/x-www-form-urlencoded")
		  .header("Authorization", getClientBasicAuth () )
		  .field("grant_type", "authorization_code")
		  .field("code", m_authCode)
		  .field("redirect_uri", this.getNafath().getRedirectUri())
		  .asString();
		
		handleReponseError(response) ; 
		Gson gson = new Gson(); 
		accessToken =  gson.fromJson( response.getBody() , ApigeeAccessToken.class ) ;
		return accessToken ; 
	}
	
	public ApigeeAccessToken getAccessToken(String m_authCode , String codeVerifier) throws JsonSyntaxException, UnirestException 
	{
	Unirest.setTimeouts(0, 0);
	String clientId = this.getCredential().getClientId() ; 
	HttpResponse<String> response = Unirest.post( this.getNafath().getTokenWithPkceUrl() + "?client_id=" + clientId)
	  .header("Content-Type", "application/x-www-form-urlencoded")
	  .field("grant_type", "authorization_code")
	  .field("code", m_authCode)
	  .field("redirect_uri", this.getNafath().getRedirectUri())
	  .field("code_verifier", codeVerifier)
	  .field("client_id", clientId)
	  .asString();
		handleReponseError(response) ; 
		Gson gson = new Gson(); 
		accessToken =  gson.fromJson( response.getBody() , ApigeeAccessToken.class ) ;
		return accessToken ; 
	}
	
	public void refreshAccessToken() throws JsonSyntaxException, UnirestException 
	{
		Unirest.setTimeouts(0, 0);
		HttpResponse<String> response = Unirest.post(this.getNafath().getRefreshTokenUrl())
		  .header("Content-Type", "application/x-www-form-urlencoded")
		  .header("Authorization", getClientBasicAuth () )
		  .field("grant_type", "refresh_token")
		  .field("refresh_token", this.accessToken.getRefresh_token())
		  .asString();
		handleReponseError(response) ; 
		Gson gson = new Gson(); 
		accessToken =  gson.fromJson( response.getBody() , ApigeeAccessToken.class ) ;
	}
	
	public void refreshAccessToken(String m_codeVerifier) throws JsonSyntaxException, UnirestException 
	{
		Unirest.setTimeouts(0, 0);
		HttpResponse<String> response = Unirest.post(this.getNafath().getRefreshTokenWithPkceUrl() + "?client_id=MtQPBZK57lXLJlB93gHYdA6f6o9bNzp8")
		  .header("Content-Type", "application/x-www-form-urlencoded")
		  .field("grant_type", "refresh_token")
		  .field("refresh_token", this.accessToken.getRefresh_token())
		  .field("client_id", this.getCredential().getClientId())
		  .field("code_verifier", m_codeVerifier)
		  .asString();
		handleReponseError(response) ; 
		Gson gson = new Gson(); 
		accessToken =  gson.fromJson( response.getBody() , ApigeeAccessToken.class ) ; 
		
	}
		
	private UrlBuilder urlBuilder = new UrlBuilder(this) ; 

	public UrlBuilder getUrlBuilder() {
		return urlBuilder;
	}
		
}
