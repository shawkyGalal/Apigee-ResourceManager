package com.smartvalue.apigee.rest.schema.sharedFlow;

import com.smartvalue.apigee.configuration.AppConfig;

public class SharedFlow extends com.smartvalue.apigee.rest.schema.sharedFlow.auto.SharedFlow {

	public String getResourcePath() {
		return AppConfig.BASE_BATH+this.getOrgName()+"/sharedflows/"+this.getName();
	}

	@Override
	protected String getUniqueId() {
		// TODO Auto-generated method stub
		return this.getName();
	}

}
