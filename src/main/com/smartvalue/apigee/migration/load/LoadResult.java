package com.smartvalue.apigee.migration.load;

import com.smartvalue.apigee.migration.ProcessResult;

public class LoadResult extends ProcessResult {
	
	private String source ;
	private com.mashape.unirest.http.HttpResponse<String> httpResponse ; 

	public String getSource() {
		return source;
	}

	public LoadResult withSource(String source) {
		this.source = source;
		return this ; 
	}

	public void setSource(String source) {
		this.source = source;

	}
	
		
	public String toString()
	{
		return super.toString() ;
	}

	public com.mashape.unirest.http.HttpResponse<String> getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(com.mashape.unirest.http.HttpResponse<String> httpResponse) {
		this.httpResponse = httpResponse;
	}

	



}
