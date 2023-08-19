package com.smartvalue.apigee.resourceManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.smartvalue.apigee.configuration.ApigeeConfig;
import com.smartvalue.apigee.configuration.infra.Infra;
import com.smartvalue.apigee.rest.schema.environment.Environment;
import com.smartvalue.apigee.rest.schema.organization.Organization;
import com.smartvalue.apigee.rest.schema.product.ProductsServices;
import com.smartvalue.apigee.rest.schema.proxy.Proxy;
import com.smartvalue.apigee.rest.schema.server.Server;
import com.smartvalue.apigee.rest.schema.sharedFlow.SharedFlow;
import com.smartvalue.apigee.rest.schema.TargetServer;
import com.smartvalue.apigee.rest.schema.virtualHost.VirtualHost; 

public class Tester {

	public static void main (String[] args) throws Exception
	{
   
		ApigeeConfig ac = new ApigeeConfig("config.json" ) ; 
		
		//Infra infra = ac.getInfra("MasterWorks" , "MOJ" , "Stage") ;
		Infra infra = ac.getInfra("SmartValue" , "Demo" , "Prod") ; 
				 
		ManagementServer ms = new ManagementServer(infra) ; 
				
		String orgName =  "smart-value"  ; // "stg" ; 
		String envName = "prod"  ; // "iam-protected"
		Organization org = ms.getOrgs().get(orgName) ;  
		Environment env = org.getEnvs().get(envName);
		
		ProductsServices   productServices = ms.getProductServices() ; 
		ArrayList<String>  productsWithoutProxies  =productServices.getProductsWithoutProxies(org) ;  
		System.out.println(productsWithoutProxies); 
		
		Proxy proxy = org.getProxy("DZIT"); 
		HashMap<String, ArrayList<String>> xx= proxy.getRevisionsUsesTargetServer("Yesser_Server") ; 
		System.out.println(xx);
		
		HashMap<String, Proxy> allProxies = org.getAllProxies();
		HashMap<String, Object> proxies = org.getAllProxiesUsesTargetServer("Yesser_Server");
		allProxies.entrySet().stream().filter(entry -> entry.getValue().getName().equalsIgnoreCase("ghfgh") ) ; 

		
		System.out.println(proxies);
		
		HashMap<String , TargetServer> allTargetServers = env.getTargetServers();  
		System.out.println(allTargetServers);
		
		ArrayList<String> proxiesNotDeployed = org.getUndeployedProxies() ; 
		System.out.println(proxiesNotDeployed.toString());
		
		
		List<Server> mpServers = env.getMessageProcesors() ;
		mpServers.get(0).healthCheck() ; 
		System.out.println(mpServers);
		
		List<Server>  servers = ms.getServers() ; 
		System.out.println(servers);
		
		
		String[] allVirtuslHosts = env.getAllVirtualHosts() ; 
		System.out.println(allVirtuslHosts.toString());
		
		VirtualHost vh = env.getVirtualHost(allVirtuslHosts[0]) ; 
		System.out.println(vh.toString());
		
		String[] allShardFlows = org.getAllShardFlow() ;
		SharedFlow shardFlow = org.getShardFlow(allShardFlows[1]) ; 
		System.out.println(shardFlow.toString());
		
		
		
		String enviro = "{\r\n"
				+ "    \"createdAt\": 1543337768969,\r\n"
				+ "    \"createdBy\": \"sfoda@master-works.net\",\r\n"
				+ "    \"lastModifiedAt\": 1543337768969,\r\n"
				+ "    \"lastModifiedBy\": \"sfoda@master-works.net\",\r\n"
				+ "    \"name\": \"prod\",\r\n"
				+ "    \"properties\": {\r\n"
				+ "        \"property\": [\r\n"
				+ "            {\r\n"
				+ "                \"name\": \"useSampling\",\r\n"
				+ "                \"value\": \"100\"\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"name\": \"samplingThreshold\",\r\n"
				+ "                \"value\": \"100000\"\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"name\": \"samplingTables\",\r\n"
				+ "                \"value\": \"10=ten;1=one;\"\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"name\": \"samplingAlgo\",\r\n"
				+ "                \"value\": \"reservoir_sampler\"\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"name\": \"samplingInterval\",\r\n"
				+ "                \"value\": \"300000\"\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"name\": \"aggregationinterval\",\r\n"
				+ "                \"value\": \"300000\"\r\n"
				+ "            }\r\n"
				+ "        ]\r\n"
				+ "    }\r\n"
				+ "}" ; 
		//ac.generateJavaClassFromJson(enviro, "Environment" , "com.smartvalue.apigee.rest.schema.environment.auto");
		
		
		
		
	}

	public static void executeShellScript(ArrayList<String> m_commands) throws IOException, InterruptedException {
	  
				ArrayList<String> commands = new  ArrayList<String>()  ; 
				String os = System.getProperty("os.name").toLowerCase();
				
				 if (os.contains("win")) 
				 {
					 commands.add("cmd") ;  commands.add( "-c" ) ; 
				 }
				 else 
				 {
					 commands.add("/bin/bash") ; commands.add( "-c" ) ;
				 }
				 
				 commands.addAll(m_commands) ; 
				
	 	        // Create a ProcessBuilder instance
	            ProcessBuilder processBuilder = new ProcessBuilder(m_commands);

	            // Start the process
	            Process process = processBuilder.start();

	            // Wait for the process to complete
	            int exitCode = process.waitFor();

	            // Print the exit code
	            System.out.println("Shell script exited with code: " + exitCode);

	       
	    }
	
}
