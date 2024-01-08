package com.smartvalue.apigee.rest.schema.sharedFlow;


public class SharedFlow extends com.smartvalue.apigee.rest.schema.sharedFlow.auto.SharedFlow {

	public String getResourcePath() {
		return "/v1/organizations/"+this.getOrgName()+"/sharedflows/"+this.getName();
	}

}
