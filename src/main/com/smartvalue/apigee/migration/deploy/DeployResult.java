package com.smartvalue.apigee.migration.deploy;

import com.mashape.unirest.http.HttpResponse;
import com.smartvalue.apigee.migration.ProcessResult;
import com.smartvalue.apigee.migration.load.LoadResults;

public class DeployResult extends ProcessResult {

	private String error ;
	private boolean failed ; 
	private String exceptionClassName ; 
	private LoadResults notMatchedResult ; 
	private String source ;
	private String destination ;
	private String envName ;  
	public String getSource() {
		return source;
	}

	public DeployResult withSource(String source) {
		this.source = source;
		return this ; 
	}

	public String getDestination() {
		return destination;
	}

	public void  setDestination(String destination) {
		this.destination = destination;
	}
	public DeployResult withDestination(String destination) {
		this.destination = destination;
		return this; 
	}

	public boolean isFailed() {
		return failed;
	}

	public void setFailed(boolean failed) {
		this.failed = failed;
	} 
	
	
	public DeployResult withFailed(boolean b) {
		this.setFailed(b) ;
		return this ;  
	}


	
	public String getExceptionClassName() {
		return exceptionClassName;
	}

	public void setExceptionClassName(String exceptionClassName) {
		this.exceptionClassName = exceptionClassName;
	}
	public DeployResult withExceptionClassName(String name) {
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
	
	public DeployResult withError(String error) {
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
	
	private HttpResponse<String> httpResponse ; 
	public HttpResponse<String> getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(HttpResponse<String> httpResponse) {
		this.httpResponse = httpResponse;
	}

	public String getEnvName() {
		return envName;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}
}
