package com.smartvalue.apigee;

import java.io.File;

import com.smartvalue.apigee.migration.ProcessResult;
import com.smartvalue.apigee.migration.ProcessResults;
import com.smartvalue.apigee.rest.schema.proxy.Proxy;

public abstract class ProxyValidator {
	
	public  ProcessResults validate(Proxy proxy , int revision ) 
	{
		String className = this.getClass().getName() ; 
		ProcessResults result = new ProcessResults(className) ;
		try {
		String bundleFileName = proxy.export(revision , "/temp/apigee/validators/" + File.separator + "className" );
		result.addAll ( validate(new File(bundleFileName)) )  ; 
		}
		catch (Exception e) {
			result.add(new ProcessResult(e)) ; 
		}
		return result; 
	}
	public abstract ProcessResults validate(File proxyBundle ) ; 

}
