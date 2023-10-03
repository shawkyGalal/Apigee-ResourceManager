package com.smartvalue.apigee.rest.schema.environment;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.filteredList.FilteredList;
import com.smartvalue.apigee.environmentsMonitor.CondActionPair;
import com.smartvalue.apigee.environmentsMonitor.MonitoringEnvThread;
import com.smartvalue.apigee.resourceManager.ManagementServer;
import com.smartvalue.apigee.rest.schema.TargetServer;
import com.smartvalue.apigee.rest.schema.keyValueMap.KeyValueMap;
import com.smartvalue.apigee.rest.schema.organization.Organization;
import com.smartvalue.apigee.rest.schema.server.MPServer;
import com.smartvalue.apigee.rest.schema.virtualHost.VirtualHost;

public class Environment extends com.smartvalue.apigee.rest.schema.environment.auto.Environment{

private String orgName ;
private transient  ManagementServer ms ;  

public String getOrgName() {
	return orgName;
}

public void setOrgName(String orgName) {
	this.orgName = orgName;
}

public ManagementServer getMs() {
	return ms;
}

public void setMs(ManagementServer ms) {
	this.ms = ms;
}






public HashMap<String , TargetServer>  getTargetServers() throws UnirestException, IOException
{
	String[] targetServersNames = null; 
	String apiPath = "/v1/o/"+this.orgName+"/e/"+this.getName()+"/targetservers" ; 
	targetServersNames = this.ms.executeGetMgmntAPI(apiPath , String[].class ) ; 
	HashMap<String , TargetServer> tss = new HashMap<>() ; 
	for ( String  tsName : targetServersNames )
	{
		String targetServerApiPath = apiPath + "/"+ tsName ; 
		@SuppressWarnings("deprecation")
		TargetServer ts = this.ms.executeGetMgmntAPI(targetServerApiPath , TargetServer.class ) ; 
		tss.put (tsName , ts) ;
	}
	return tss ; 
}

/**
 * 
 * @param m_region  use null value to return all MP's 
 * @return
 * @throws UnirestException
 * @throws IOException
 */
public List<MPServer> getMessageProcesors(String m_region) throws UnirestException, IOException
{
	String apiPath = "/v1/o/"+this.orgName+"/e/"+this.getName()+"/servers?expand=true" ; 
	// === Thanks To ChatGPT 
	Type listType = new TypeToken<List<MPServer>>() {}.getType();
	List<MPServer> serversArray = this.ms.executeMgmntAPI(apiPath , listType ) ;
	List<MPServer> filteredMpServers = new ArrayList<MPServer>() ; 
	for (MPServer server : serversArray )
	{
		String mpRegion = server.getRegion() ; 
		if ( m_region == null || mpRegion.equalsIgnoreCase(m_region))
		{ 
			server.setManagmentServer(ms);
			filteredMpServers.add(server) ; 
		}
		
	}
	return filteredMpServers ; 
	
}

public TargetServer  getTargetServer(String m_targetServerName) throws UnirestException, IOException
{
	return this.getTargetServers().get(m_targetServerName) ; 
}

@SuppressWarnings("unchecked")
public VirtualHost  getVirtualHostByName(String virtualHostName ) throws UnirestException, IOException
{
	String apiPath = "/v1/o/"+this.orgName +"/e/" +this.getName()+"/virtualhosts/" + virtualHostName ; 
	VirtualHost virtualHost  = this.ms.executeGetMgmntAPI(apiPath , VirtualHost.class ) ;
	virtualHost.setOrgName(this.getName()) ; 
	virtualHost.setManagmentServer(this.ms) ; 
	return virtualHost ; 
}

public List<String>  getAllKvmNames() throws UnirestException, IOException
{
	String apiPath = "/v1/o/"+this.orgName +"/e/" +this.getName()+"/keyvaluemaps/"  ; 
	List<String> virtualHosts  = this.ms.executeGetMgmntAPI(apiPath , List.class ) ;

	return virtualHosts ; 
}  

public List<String>  getAllTargetServersNames() throws UnirestException, IOException
{
	String apiPath = "/v1/o/"+this.orgName +"/e/" +this.getName()+"/targetservers/"  ; 
	List<String> targetServers  = this.ms.executeGetMgmntAPI(apiPath , List.class ) ;

	return targetServers ; 
}  

@SuppressWarnings("unchecked")
public KeyValueMap  getKvm(String kvmName ) throws UnirestException, IOException
{
	String apiPath = "/v1/o/"+this.orgName +"/e/" +this.getName()+"/keyvaluemaps/" + kvmName ; 
	KeyValueMap keyValueMap  = this.ms.executeGetMgmntAPI(apiPath , KeyValueMap.class ) ;
	keyValueMap.setOrgName(this.getName()) ; 
	keyValueMap.setManagmentServer(this.ms) ; 
	
	return keyValueMap ; 
}


@SuppressWarnings("unchecked")
public String[]  getAllVirtualHosts() throws UnirestException, IOException
{
	String apiPath =  "/v1/o/"+this.orgName +"/e/" +this.getName()+"/virtualhosts/"  ; 
	String[] virtualHosts  = this.ms.executeGetMgmntAPI(apiPath , String[].class ) ;
	
	return virtualHosts ; 
}

public ArrayList<String> addMessageProcessor(MPServer mpServer ) throws UnirestException, IOException
{
	//Organization org = (Organization) this.ms.getOrgByName(this.orgName) ;
	ArrayList<String> result = mpServer.addToEnvironmnt(this); 
	return result;
}

public ArrayList<String> removeMessageProcessor(MPServer mpServer ) throws UnirestException, IOException
{
	//Organization org = (Organization) this.ms.getOrgByName(this.orgName) ;
	ArrayList<String> result = mpServer.removeFromEnvironmnt(this); 
	return result;
}

public void monitor(ArrayList<CondActionPair> condActionPairs ) throws Exception
{
	for (CondActionPair condActionPair : condActionPairs )
	{
		boolean conditionMet = condActionPair.getCondition().evaluate() ;
		if (conditionMet)
		{
			condActionPair.getAction().run() ; 
		}
	}
}

private transient MonitoringEnvThread met ;
public void startMonitoring(int expectedMps) 
{	if (met==null)
	{
		met = new MonitoringEnvThread() ;
		met.setEnv(this) ;
		met.setExpectedMPCount(expectedMps);
	}
	met.start() ; 
}

public void stopMonitoring() 
{	if (met!=null )
	{
		met.stopThread() ;
	}
}




}
