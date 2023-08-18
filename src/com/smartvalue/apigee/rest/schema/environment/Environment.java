package com.smartvalue.apigee.rest.schema.environment;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.resourceManager.ManagementServer;
import com.smartvalue.apigee.rest.schema.TargetServer;
import com.smartvalue.apigee.rest.schema.server.Server;
import com.smartvalue.apigee.rest.schema.virtualHost.VirtualHost;

public class Environment {
private String name ;
private String orgName ;
private ManagementServer ms ; 

public Environment(ManagementServer m_ms, String orgName , String envName) {
	this.ms = m_ms ; 
	this.name = envName ; 
	this.orgName = orgName ; 
}

public String getName() {
	return name;
}

public HashMap<String , TargetServer>  getTargetServers() throws UnirestException, IOException
{
	String[] targetServersNames = null; 
	String apiPath = "/v1/o/"+this.orgName+"/e/"+this.name+"/targetservers" ; 
	targetServersNames = this.ms.executeMgmntAPI(apiPath , String[].class , "GET") ; 
	HashMap<String , TargetServer> tss = new HashMap<String , TargetServer>() ; 
	for ( String  tsName : targetServersNames )
	{
		String targetServerApiPath = apiPath + "/"+ tsName ; 
		@SuppressWarnings("deprecation")
		TargetServer ts = this.ms.executeMgmntAPIUsingGson(targetServerApiPath , TargetServer.class , "GET") ; 
		tss.put (tsName , ts) ;
	}
	return tss ; 
}

public List<Server> getMessageProcesors() throws UnirestException, IOException
{
	String apiPath = "/v1/o/"+this.orgName+"/e/"+this.name+"/servers?expand=true" ; 
	// === Thanks To ChatGPT 
	Type listType = new TypeToken<List<Server>>() {}.getType();
	@SuppressWarnings("deprecation")
	List<Server> serversArray = this.ms.executeMgmntAPIUsingGson(apiPath , listType , "GET") ; 
	for (Server server : serversArray )
	{
		server.setManagmentServer(ms);
	}
	return serversArray ; 
	
}

public TargetServer  getTargetServer(String m_targetServerName) throws UnirestException, IOException
{
	return this.getTargetServers().get(m_targetServerName) ; 
}

@SuppressWarnings("unchecked")
public VirtualHost  getVirtualHost(String virtualHostName ) throws UnirestException, IOException
{
	String apiPath = "/v1/o/"+this.orgName +"/e/" +this.getName()+"/virtualhosts/" + virtualHostName ; 
	VirtualHost virtualHost  = this.ms.executeMgmntAPI(apiPath , VirtualHost.class , "GET") ;
	virtualHost.setOrgName(this.getName()) ; 
	virtualHost.setManagmentServer(this.ms) ; 
	
	return virtualHost ; 
}

@SuppressWarnings("unchecked")
public String[]  getAllVirtualHosts() throws UnirestException, IOException
{
	String apiPath =  "/v1/o/"+this.orgName +"/e/" +this.getName()+"/virtualhosts/"  ; 
	String[] virtualHosts  = this.ms.executeMgmntAPI(apiPath , String[].class , "GET") ;
	
	return virtualHosts ; 
}

}
