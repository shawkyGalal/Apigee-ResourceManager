package com.smartvalue.apigee.rest.schema.proxy.transformers;

public class TransformResult {
	
	private String source ;
	private String destination ;
	private String error ;
	private boolean failed ; 

	public String getSource() {
		return source;
	}

	public TransformResult withSource(String source) {
		this.source = source;
		return this ; 
	}
	

	public String getDestination() {
		return destination;
	}
	public TransformResult withDestination(String destination) {
		this.destination = destination;
		return this; 
	}

	
	public String getError() {
		return error;
	}
	public TransformResult withError(String error) {
		this.error = error;
		return this ; 
	}

	public boolean isFailed() {
		return failed;
	}

	public void setFailed(boolean failed) {
		this.failed = failed;
	} 
	public TransformResult withFailed(boolean failed) {
		this.failed = failed;
		return this; 
	}

}
