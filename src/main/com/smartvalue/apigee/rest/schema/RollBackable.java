package com.smartvalue.apigee.rest.schema;

import com.smartvalue.apigee.migration.deploy.DeployResults;

public interface RollBackable {
	public abstract DeployResults rollBackObjectToLastSerializedDeployStatus(String revisionedObjectName , String serlizeFileName) ;
	

}
