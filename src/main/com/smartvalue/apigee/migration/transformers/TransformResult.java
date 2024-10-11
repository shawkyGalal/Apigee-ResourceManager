package com.smartvalue.apigee.migration.transformers;

import com.smartvalue.apigee.migration.ProcessResult;

public class TransformResult extends ProcessResult {
	

	private Class transformerClass ;

	
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
