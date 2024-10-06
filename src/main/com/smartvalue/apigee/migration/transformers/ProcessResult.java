package com.smartvalue.apigee.migration.transformers;

public class ProcessResult {

	private String error ;
	private boolean failed ; 
	private String exceptionClassName ; 

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
}
