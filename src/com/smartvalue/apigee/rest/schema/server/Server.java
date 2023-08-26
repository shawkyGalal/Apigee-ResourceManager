package com.smartvalue.apigee.rest.schema.server;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.resourceManager.helpers.Helper;

public  class Server extends com.smartvalue.apigee.rest.schema.server.auto.Server{

	public boolean healthCheck()
	{
		boolean result = false ; 
		HttpResponse<String> response ; 
		try {

			String propValue = Helper.getPropertyValueFromList(this.getTags().getProperty(), "name" , "http.management.port") ; 
			String healthCheckURL = "http://" + this.getExternalIP()+":" + propValue + "/v1/servers/self/up" ; 
			com.mashape.unirest.request.HttpRequest httpRequest = Unirest.get(healthCheckURL);
			response = httpRequest.header("Authorization", "No Need " ).asString();
		
			result =  Helper.isConsideredSuccess(response.getStatus()) ; 
		} 
		catch (UnirestException e) {
			e.printStackTrace();
		} 
		return result;
		
	}

	public  String getSimpleName() 
	{
		return "general purpose Server " ; 
	}

	  
}
