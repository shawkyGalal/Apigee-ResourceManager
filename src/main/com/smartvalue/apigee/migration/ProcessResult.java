package com.smartvalue.apigee.migration;

import java.io.Serializable;

import com.mashape.unirest.http.HttpResponse;
import com.smartvalue.apigee.migration.load.LoadResults;
import com.smartvalue.apigee.resourceManager.helpers.Helper;

public class ProcessResult implements Serializable {

	private String description; 
	private String error ;
	private boolean failed ; 
	private String exceptionClassName ; 
	private LoadResults notMatchedResult ; 
	private String source ;
	private String destination ;
	private String responseBody ;

	
	public ProcessResult(Exception e) {
		this.setFailed(true);
		this.setError(e.getMessage());
		this.setExceptionClassName(e.getClass().getName());
	}

	public ProcessResult() {
		// TODO Auto-generated constructor stub
	}

	public String getSource() {
		return source;
	}

	public ProcessResult withSource(String source) {
		this.source = source;
		return this ; 
	}

	public String getDestination() {
		return destination;
	}

	public void  setDestination(String destination) {
		this.destination = destination;
	}
	public ProcessResult withDestination(String destination) {
		this.destination = destination;
		return this; 
	}

	public boolean isFailed() {
		return failed;
	}

	public void setFailed(boolean failed) {
		this.failed = failed;
	} 
	
	
	public ProcessResult withFailed(boolean b) {
		this.setFailed(b) ;
		return this ;  
	}


	
	public String getExceptionClassName() {
		return exceptionClassName;
	}

	public void setExceptionClassName(String exceptionClassName) {
		this.exceptionClassName = exceptionClassName;
	}
	public ProcessResult withExceptionClassName(String name) {
		// TODO Auto-generated method stub
		this.setExceptionClassName(name);
		return this;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	public ProcessResult withError(String error) {
		this.error = error;
		return this ; 
	}

	public LoadResults getNotMatchedResult() {
		return notMatchedResult;
	}

	public void setNotMatchedResult(LoadResults notMatchedResult) {
		this.notMatchedResult = notMatchedResult;
	}

	public void setSource(String processSource) {
		this.source = processSource ; 
		
	}
	
	//public HttpResponse<String> getHttpResponse() {
	//	return httpResponse;
	//}

	public void setHttpResponse(HttpResponse<String> httpResponse) {
		//this.httpResponse = httpResponse;
		if (httpResponse != null)
		{
			boolean success = Helper.isConsideredSuccess(httpResponse.getStatus()) ;
			this.setFailed(!success);
			this.responseBody = httpResponse.getBody();
		}
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setDescription(String string) {
		this.description = string ;
		
	}
	public ProcessResult withDescription(String string) {
		this.description = string ;
		return this; 
		
	}
	public String  getDescription() {
		return this.description ;
		
	}
}
