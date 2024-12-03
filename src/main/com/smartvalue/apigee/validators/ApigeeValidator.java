package com.smartvalue.apigee.validators;

import java.io.File;
import java.util.UUID;

import com.smartvalue.apigee.migration.ProcessResults;

public abstract class ApigeeValidator {
	
	public abstract ProcessResults validate(File proxyBundle ,  UUID uuid) ; 
	public abstract boolean filter (String  proxyBundleFileName ) ; 
	

}
