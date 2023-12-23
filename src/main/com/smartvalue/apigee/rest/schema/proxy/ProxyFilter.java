package com.smartvalue.apigee.rest.schema.proxy;

import java.io.File;

public interface  ProxyFilter {
	
	public boolean filter(File proxyBundle)  ;

}
