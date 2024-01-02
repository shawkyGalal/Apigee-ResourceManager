package com.smartvalue.apigee.rest.schema.proxy.transformers;

public class TransformResult {
	private String status ;
	private String source ;
	private String destination ;
	private String error ;

	public String getStatus() {
		return status;
	}

	public TransformResult withStatus(String status) {
		this.status = status;
		return this ; 
	}
	

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

}
