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
import com.smartvalue.moj.najiz.mapping.appointments.AppointmentServices;
import com.smartvalue.moj.najiz.mapping.attornies.AttornyServices;
import com.smartvalue.moj.najiz.mapping.xyz.XyzServices;

public class Environment extends com.smartvalue.moj.clients.environments.auto.Environment {
	
	public void initialize() {
		Unirest.setTimeouts(this.getConnectionTimeout(), this.getSocketTimeout());
	}

	private ApigeeAccessToken accessToken = null ; 
	//private ApigeeAccessToken accessToken = null ;
	public HttpResponse<String>  executeRequest(HashMap<String , String> m_headers , String m_verb , String m_body) throws UnirestException , AccessTokenNotFound
	{
		String m_url = this.getMojServicesBaseUrl() ;
		return executeRequest( m_url , m_headers ,  m_verb , m_body) ;  
	}
	private boolean accessTokenMandatory = false ; 
	public HttpResponse<String>  executeRequest( String m_url , HashMap<String , String> m_headers , String m_verb , String m_body) throws UnirestException , AccessTokenNotFound
	{
		HttpRequest request = null ; 
		HttpResponse<String> response = null ; 
		if (m_verb.equalsIgnoreCase("GET"))
		{
			request =  Unirest.get(m_url) ; 
			request = appendHeaders (request ,  m_headers  );
			if (m_headers == null || ! m_headers.containsKey("Authorization"))
			{
				if (this.accessToken == null && accessTokenMandatory ) throw new AccessTokenNotFound() ; 
				request.header("Authorization" , "Bearer " +this.accessToken.getAccess_token()) ; 
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
			if (m_headers == null ||  ! m_headers.containsKey("Authorization"))
			{
				if (this.accessToken == null && accessTokenMandatory ) throw new AccessTokenNotFound() ; 
				requestWithBody.header("Authorization" , "Bearer " +this.accessToken.getAccess_token()) ; 
			}
		
			requestWithBody.body(m_body);
			response = requestWithBody.asString();
		}
		
		if (! Helper.isConsideredSuccess(response.getStatus()) )
		{
			if (response.getBody() != null && response.getBody().contains("Access Token expired"))
			{
				this.reNewAccessToken() ; 
				response = executeRequest( m_url ,  m_headers , m_verb , m_body) ; 
			}
			else if (response.getStatus() == 401 &&  response.getBody() != null && response.getBody().contains("steps.jwt.TokenExpired"))
			{ 
				throw new UnirestException(response.getBody()) ; 
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
		HttpResponse<String> response = null;
		try {
			response = executeRequest ( this.getTokenUrl(), headers , "POST" , "");
		} catch (AccessTokenNotFound e) {	}
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
	public void setAccessToken(String m_authCode) throws JsonSyntaxException, UnirestException 
	{	
		//check if the provided m_authCode is the same used to generate the current accesstoken  
		String previousAuthCode = (accessToken == null)? null : accessToken.getAuthoirizationCode() ; 
		if ( previousAuthCode == null || ! previousAuthCode.equalsIgnoreCase(m_authCode))
		{
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
			// Associate the generated accessToken with the provided m_authCode 
			accessToken.setAuthoirizationCode(m_authCode) ;
		}
	}
	
	public ApigeeAccessToken getAccessTokenWithPkce(String m_authCode , String codeVerifier) throws JsonSyntaxException, UnirestException 
	{
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
	
	public ApigeeAccessToken getAccessToken()
	{
		return this.accessToken ; 
	}
	
	public void refreshAccessToken() throws JsonSyntaxException, UnirestException 
	{
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
	
	public void refreshAccessTokenWithPkce(String m_codeVerifier) throws JsonSyntaxException, UnirestException 
	{
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

	public boolean isAccessTokenMandatory() {
		return accessTokenMandatory;
	}

	public void setAccessTokenMandatory(boolean accessTokenMandatory) {
		this.accessTokenMandatory = accessTokenMandatory;
	}
	
	
	private AppointmentServices appointmentServices = null;  
	public AppointmentServices getAppointmentService()
	{
		if (appointmentServices == null)
		{
			this.appointmentServices = new AppointmentServices(this) ; 
		}
		return appointmentServices ; 
	}
	
	private AttornyServices attornyServices = null;  
	public AttornyServices getAttornyServices()
	{
		if (attornyServices == null)
		{
			this.attornyServices = new AttornyServices(this) ; 
		}
		return attornyServices ; 
	}
	
	private XyzServices xyzServices = null;  
	public XyzServices getXyzServices()
	{
		if (xyzServices == null)
		{
			this.xyzServices = new XyzServices(this) ; 
		}
		return xyzServices ; 
	}
		
}
