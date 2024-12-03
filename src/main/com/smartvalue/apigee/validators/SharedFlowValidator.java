package com.smartvalue.apigee.validators;

import java.io.File;
import java.util.UUID;

import com.smartvalue.apigee.migration.ProcessResult;
import com.smartvalue.apigee.migration.ProcessResults;
import com.smartvalue.apigee.rest.schema.proxy.Proxy;
import com.smartvalue.apigee.rest.schema.proxyRevision.ProxyRevision;

public abstract class SharedFlowValidator extends ProxyValidator {
			
	public abstract ProcessResults validate(File proxyBundle ,  UUID uuid) ; 

}
