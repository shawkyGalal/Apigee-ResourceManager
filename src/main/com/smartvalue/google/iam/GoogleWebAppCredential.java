package com.smartvalue.google.iam ;


import java.nio.charset.Charset;

import org.springframework.security.crypto.codec.Base64;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.google.iam.auto.GoogleAccessToken;


public class GoogleWebAppCredential extends com.smartvalue.google.iam.auto.GoogleWebAppCredential 
{
	
	public HttpResponse<String>  getAccessTokenResByAuthCode( String authCode , String redirectUri) throws UnirestException
	{
		String clientId = this.getClient_id() ; 
		String clientISectet= this.getClientSecret() ;
		String tokenUrl = this.getTokenUri() ; 
		HttpResponse<String> accessTokenResponse = Unirest.post(tokenUrl)
		  .header("Content-Type", "application/x-www-form-urlencoded")
		  .header("Authorization", "Basic "+ new String(Base64.encode((clientId + ":" + clientISectet).getBytes()), Charset.forName("UTF-8")))
		  .field("grant_type", "authorization_code")
		  .field("code", authCode)
		  .field("redirect_uri", redirectUri )  //"https://apigeeadmin.moj.gov.sa:8443/ResourceManagerWeb/loginWithGoogle/authCodeHandler.jsp")
		  .asString();
	
		return accessTokenResponse ; 
	
	}
	
	public GoogleAccessToken  getAccessTokenByAuthCode( String authCode , String redirectUri) throws UnirestException
	{
		Gson json = new Gson(); 
		HttpResponse<String> response = getAccessTokenResByAuthCode(authCode, redirectUri) ; 
		String body = response.getBody() ; 
		int status  = response.getStatus() ;
		if (status == 200 )
		{
			GoogleAccessToken googleAccessToken =  json.fromJson(  body , GoogleAccessToken.class );
			googleAccessToken.setSourceCredentials(this); 
			
			return googleAccessToken; 
		}
		else 
		{
			throw new IllegalArgumentException("Unable to Generate Google AccessToken Due to " + body ) ; 
		}
	}
	
}