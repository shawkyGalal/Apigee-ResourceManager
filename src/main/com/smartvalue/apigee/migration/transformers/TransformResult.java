package com.smartvalue.apigee.migration.transformers;

import com.smartvalue.apigee.migration.ProcessResult;

public class TransformResult extends ProcessResult {
	
	private String source ;
	private String destination ;
	private Class transformerClass ;

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
	
	public void setTransformerClass(Class transformerClass) {
		this.transformerClass = transformerClass;
	}
	
	public TransformResult withTransformerClass(Class transformerClass) {
		this.transformerClass = transformerClass;
		return this ; 
	}
	
	public String toString()
	{
		return super.toString() ;
	}

	public Class getTransformerClass() {
		return transformerClass;
	}



}
