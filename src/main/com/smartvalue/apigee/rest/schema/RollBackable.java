package com.smartvalue.apigee.rest.schema;

import java.util.UUID;

import com.smartvalue.apigee.migration.deploy.DeployResults;

public interface RollBackable {
	public abstract DeployResults rollBackObjectToSerializedDeployStatus(String revisionedObjectName , UUID uuid) ;
	

}
