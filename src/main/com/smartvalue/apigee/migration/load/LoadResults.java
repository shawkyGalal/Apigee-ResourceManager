package com.smartvalue.apigee.migration.load;

import java.util.Map.Entry;

import com.mashape.unirest.http.HttpResponse;

public class LoadResults {
	private java.util.HashMap<String, HttpResponse<String>> allResponses ; 
	private java.util.HashMap<String, HttpResponse<String>> unMatchedResponses = new  java.util.HashMap<String, HttpResponse<String>> (); 
	
	public LoadResults( java.util.HashMap<String, HttpResponse<String>> m_responses)
	{
		this.allResponses = m_responses ; 
	}
	public java.util.HashMap<String, HttpResponse<String>> getResponsees() {
		return allResponses;
	}
	public void setResponsees(java.util.HashMap<String, HttpResponse<String>> responsees) {
		this.allResponses = responsees;
	}
	
	public java.util.HashMap<String, HttpResponse<String>> filterSuccess( )
	{
		java.util.HashMap<String, HttpResponse<String>> result = new java.util.HashMap<String, HttpResponse<String>> () ; 
		for ( Entry<String, HttpResponse<String>> entry : this.getResponsees().entrySet() )
		{
			HttpResponse<String> response = entry.getValue() ;
			if (response != null )
			{
				int status = response.getStatus() ;  
				boolean success = (status ==  200 || status ==  201) ;  
				if (success )
				{
					result.put(entry.getKey(), response) ; 
				}
				else {
					this.getUnMatchedResponses().put(entry.getKey(), response) ; 
				}
			}
			else
			{
				this.getUnMatchedResponses().put(entry.getKey(), null) ;
			}
		}
		return result  ; 
	}
	public java.util.HashMap<String, HttpResponse<String>> getUnMatchedResponses() {
		return unMatchedResponses;
	}
	public void setUnMatchedResponses(java.util.HashMap<String, HttpResponse<String>> unMatchedResponses) {
		this.unMatchedResponses = unMatchedResponses;
	}
}
