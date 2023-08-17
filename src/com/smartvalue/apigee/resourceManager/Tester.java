package com.smartvalue.apigee.resourceManager;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.smartvalue.apigee.configuration.ApigeeConfig;
import com.smartvalue.apigee.configuration.Infra;
import com.smartvalue.apigee.rest.schema.Environment;
import com.smartvalue.apigee.rest.schema.Organization;
import com.smartvalue.apigee.rest.schema.Proxy;
import com.smartvalue.apigee.rest.schema.ProxyRevision;
import com.smartvalue.apigee.rest.schema.Server;
import com.smartvalue.apigee.rest.schema.SharedFlow;
import com.smartvalue.apigee.rest.schema.TargetServer;
import com.smartvalue.apigee.rest.schema.VirtualHost; 

public class Tester {

	public static void main (String[] args) throws Exception
	{
   
		ApigeeConfig ac = new ApigeeConfig("config.json" ) ; 
		//Infra infra = ac.getInfra("MasterWorks" , "MOJ" , "Stage") ;
		Infra infra = ac.getInfra("SmartValue" , "Demo" , "Prod") ; 
				 
		ManagementServer ms = new ManagementServer(infra) ; 
				
		String orgName =  "smart-value"  ; // "stg" ; 
		String envName = "Prod"  ; // "iam-protected"
		Organization org = ms.getOrgs().get(orgName) ;  
		Environment env = org.getEnvs().get(envName);
		
		//Proxy proxy = org.getProxy("ADFClientFinancialStatus"); 
		//HashMap<Integer, ArrayList<String>> xx= proxy.getRevisionsUsesTargetServer("Yesser_Server") ; 
		//System.out.println(xx);
		
		//HashMap<String, Proxy> allProxies = org.getAllProxies();
		//HashMap<String, Object> proxies = org.getAllProxiesUsesTargetServer("Yesser_Server"); 
		//System.out.println(proxies);
		
		//HashMap<String , TargetServer> allTargetServers = env.getTargetServers();  
		//System.out.println(allTargetServers);
		
		ArrayList<String> proxiesNotDeployed = org.getUndeployedProxies() ; 
		System.out.println(proxiesNotDeployed.toString());
		
		
		List<Server> mpServers = env.getMessageProcesors() ;
		mpServers.get(1).healthCheck() ; 
		//System.out.println(mpServers);
		/*
		List<Server>  servers = ms.getServers() ; 
		System.out.println(servers);
		*/
		
		//String[] allVirtuslHosts = env.getAllVirtualHosts() ; 
		//System.out.println(allVirtuslHosts.toString());
		
		//VirtualHost vh = env.getVirtualHost(allVirtuslHosts[0]) ; 
		//System.out.println(vh.toString());
		
		//String[] allShardFlows = org.getAllShardFlow() ;
		//SharedFlow shardFlow = org.getShardFlow(allShardFlows[1]) ; 
		//System.out.println(shardFlow.toString());
		
		String sharedFlow = "{\r\n" + 
				"    \"metaData\": {\r\n" + 
				"        \"createdAt\": 1624394356562,\r\n" + 
				"        \"createdBy\": \"defaultUser\",\r\n" + 
				"        \"lastModifiedAt\": 1634623116253,\r\n" + 
				"        \"lastModifiedBy\": \"defaultUser\"\r\n" + 
				"    },\r\n" + 
				"    \"name\": \"AsyncFlow\",\r\n" + 
				"    \"revision\": [\r\n" + 
				"        \"1\",\r\n" + 
				"        \"2\"\r\n" + 
				"    ]\r\n" + 
				"}" ; 
		//ac.generateJavaClassFromJson(sharedFlow, "SharedFlow" , "com.smartvalue.apigee.rest.schema.auto.sharedFlow");
		
	}

}
