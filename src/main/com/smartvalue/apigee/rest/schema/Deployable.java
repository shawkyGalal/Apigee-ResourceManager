package com.smartvalue.apigee.rest.schema;

import java.io.IOException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.migration.deploy.DeployResult;
import com.smartvalue.apigee.migration.deploy.DeployResults;

public interface Deployable {

	public DeployResults deployRevision(String m_proxyName , String m_envName , int revision , boolean force ) throws UnirestException, IOException ; 
 
	public DeployResults UnDeployFromEnv(String m_objectName , String m_envName ) throws UnirestException, IOException ; 
}
