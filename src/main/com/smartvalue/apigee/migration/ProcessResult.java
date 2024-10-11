package com.smartvalue.apigee.migration;

import com.smartvalue.apigee.migration.load.LoadResults;
import com.smartvalue.apigee.migration.transformers.TransformResult;

public class ProcessResult {

	private String error ;
	private boolean failed ; 
	private String exceptionClassName ; 
	private LoadResults notMatchedResult ; 
	private String source ;
	private String destination ;
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
}
