package com.smartvalue.apigee.rest.schema;

import java.io.IOException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.migration.deploy.DeployResult;

public interface Deployable {

	public DeployResult deployRevision(String m_proxyName , String m_envName , int revision ) throws UnirestException, IOException ; 
 
}
