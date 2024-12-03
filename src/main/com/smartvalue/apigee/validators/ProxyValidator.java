package com.smartvalue.apigee.validators;

import java.io.File;
import java.util.UUID;

import com.smartvalue.apigee.migration.ProcessResult;
import com.smartvalue.apigee.migration.ProcessResults;
import com.smartvalue.apigee.rest.schema.proxy.Proxy;
import com.smartvalue.apigee.rest.schema.proxyRevision.ProxyRevision;

public abstract class ProxyValidator extends ApigeeValidator {
	
	private static final String TEMP_FOLDER = "c:\\temp\\Apigee\\" ;  
	public  ProcessResults validate(Proxy proxy , int revision ,  UUID uuid ) 
	{
		String className = this.getClass().getName() ; 
		ProcessResults result = new ProcessResults(className , uuid) ;
		try {
		String bundleFileName = proxy.export(revision , TEMP_FOLDER );
		result.addAll ( validate(new File(bundleFileName) , uuid ))  ; 
		}
		catch (Exception e) {
			result.add(new ProcessResult(e)) ; 
		}
		return result; 
	}
	
	public  ProcessResults validate(ProxyRevision proxyRevision ,  UUID uuid ) 
	{
		String className = this.getClass().getName() ; 
		ProcessResults result = new ProcessResults(className, uuid) ;
		try {
		String bundleFileName = proxyRevision.export(TEMP_FOLDER );
		result.addAll ( validate(new File(bundleFileName) , uuid ))  ; 
		}
		catch (Exception e) {
			result.add(new ProcessResult(e)) ; 
		}
		return result; 
	}
	

}
