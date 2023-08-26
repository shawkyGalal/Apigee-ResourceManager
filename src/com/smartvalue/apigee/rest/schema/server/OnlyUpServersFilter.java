package com.smartvalue.apigee.rest.schema.server;

import com.smartvalue.apigee.configuration.filteredList.ListFilter;

public class OnlyUpServersFilter implements ListFilter<MPServer> {

	@Override
	public boolean apply(MPServer entry) throws Exception {
		return entry.getIsUp();
	}

}
