package com.smartvalue.apigee.rest.schema.targetServer;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.rest.schema.EnvironmentScopeService;

public class TargetServerServices  extends EnvironmentScopeService {
	
	public TargetServerServices(ManagementServer ms, String m_orgName) {
		super(ms, m_orgName);
	}


	@Override
	public String getApigeeObjectType() {
		return "targetservers";
	}

}
