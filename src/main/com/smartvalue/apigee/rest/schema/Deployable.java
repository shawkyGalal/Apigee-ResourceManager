package com.smartvalue.apigee.rest.schema;

import java.io.IOException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

public interface Deployable {

	public HttpResponse<String> deployRevision(String m_proxyName , String m_envName , int revision ) throws UnirestException, IOException ; 
 
}
