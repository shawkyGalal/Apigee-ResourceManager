package com.smartvalue.apigee.rest.cloud.OrganizationList;

import com.smartvalue.apigee.rest.cloud.OrganizationList.auto.Organization;

public class OrganizationList extends com.smartvalue.apigee.rest.cloud.OrganizationList.auto.OrganizationList {

	public String[] toArray() {
		String[] result = new String[this.getOrganizations().size()] ;
		int i =0 ; 
		for (Organization org : this.getOrganizations() )
		{
			result[i] = org.getProjectId();
			i++ ; 
		}
		return result;
	}

}
