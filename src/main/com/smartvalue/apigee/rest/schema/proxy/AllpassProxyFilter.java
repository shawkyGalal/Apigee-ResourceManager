package com.smartvalue.apigee.rest.schema.proxy;

import java.io.File;

public class AllpassProxyFilter implements ProxyFilter {

	@Override
	public boolean filter(File proxyBundle) {
		return true;
	}

}
