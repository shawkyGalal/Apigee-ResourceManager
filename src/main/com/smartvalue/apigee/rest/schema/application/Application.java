package com.smartvalue.apigee.rest.schema.application;

import java.io.IOException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.AppConfig;
import com.smartvalue.apigee.rest.schema.application.auto.Credential;

public class Application extends com.smartvalue.apigee.rest.schema.application.auto.Application
{
	public Credential getCredentialByKey( String m_key)
	{
		Credential result = null ; 
		for (Credential  credential : this.getCredentials())
		{
			if (credential.getConsumerKey().equalsIgnoreCase(m_key))
			{
				result = credential ; 
				break; 
			}
		}
		return result ; 
	}
	

	public  HttpResponse<String>  approveApiProduct(String consumerkey , String apiProductId) throws UnirestException, IOException
	{
		return updateApiProductStatus(consumerkey , apiProductId , "approve" ) ; 
	}
	
	public  HttpResponse<String>  revokeApiProduct(String consumerkey , String apiProductId) throws UnirestException, IOException
	{
		return updateApiProductStatus(consumerkey , apiProductId , "revoke" ) ; 
	}

	private  HttpResponse<String>  updateApiProductStatus(String consumerkey , String apiProductId , String m_action) throws UnirestException, IOException
	{
		String bath = AppConfig.BASE_BATH+this.getOrgName()+"/developers/"+this.getDeveloperId()+"/apps/"+this.getName()+"/keys/"+consumerkey+"/apiproducts/"+apiProductId+"?action="+m_action ;
		return  this.getManagmentServer().getPostHttpResponse(bath) ;
	}
	
}
