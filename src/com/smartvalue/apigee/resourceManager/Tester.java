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
import com.smartvalue.apigee.rest.schema.server.MPServer;
import com.smartvalue.apigee.rest.schema.server.Postgres;
import com.smartvalue.apigee.rest.schema.server.Router;
import com.smartvalue.apigee.rest.schema.server.Server;
import com.smartvalue.apigee.rest.schema.server.ServerServices;
import com.smartvalue.apigee.rest.schema.sharedFlow.SharedFlow;
import com.smartvalue.apigee.rest.schema.TargetServer;
import com.smartvalue.apigee.rest.schema.virtualHost.VirtualHost; 

public class Tester {

	public static void main (String[] args) throws Exception
	{
   
		ApigeeConfig ac = new ApigeeConfig("config.json" ) ; 
		
		Infra infra = ac.getInfra("MasterWorks" , "MOJ" , "Stage") ;
		String orgName = "stg" ; 
		String envName = "cert-protected" ; 
		String proxyName = "oidc-core" ; 
		
		 //Infra infra = ac.getInfra("SmartValue" , "Demo" , "Prod") ; 
		 //String orgName =  "smart-value"  ; // "stg" ; 
		 //String envName = "prod"  ; // "iam-protected"
		 //String proxyName = "DZIT" ;

		ManagementServer ms = new ManagementServer(infra) ; 
		Organization org = (Organization) ms.getOrgs().get(orgName) ;  
		Environment env = (Environment) org.getEnvs().get(envName);

		
		//ProductsServices   productServices = ms.getProductServices(orgName) ; 
		//ArrayList<String>  productsWithoutProxies  =productServices.getProductsWithoutProxies() ;  
		//System.out.println(productsWithoutProxies); 
		
		//Proxy proxy = org.getProxy(proxyName); 
		//HashMap<String, ArrayList<String>> xx= proxy.getRevisionsUsesTargetServer("Yesser_Server" , true) ; 
		//System.out.println(xx);
		
		//HashMap<String, Proxy> allProxies = org.getAllProxies();
		//HashMap<String, Object> proxies = org.getAllProxiesUsesTargetServer("Yesser_Server" , true);
		//System.out.println(proxies);
		String[] aa = {"FC-ELK-Logger" ,  "ELK-Logger" ,  "FC-Elk-Logger" } ; 
		ms.getProxyServices(orgName).getProxiesWithoutPolices(aa, true) ; 
		HashMap<String , TargetServer> allTargetServers = env.getTargetServers();  
		System.out.println(allTargetServers);
		
		ArrayList<Object> proxiesNotDeployed = org.getUndeployedProxies() ; 
		System.out.println(proxiesNotDeployed.toString());
 		
		List<MPServer> envMpServers = env.getMessageProcesors("dc-1") ;
		boolean healthy = envMpServers.get(0).healthCheck() ;
		ArrayList<String> result = envMpServers.get(0).removeFromEnvironmnt(org , env ) ; 
		result = envMpServers.get(0).addToEnvironmnt(org , env ) ; 
		System.out.println(envMpServers);
		
		result = env.removeMessageProcessor(envMpServers.get(0)) ; 
		result = env.addMessageProcessor(envMpServers.get(0)) ;
		
		ServerServices ss = ms.getServerServices() ; 
		List<Server>  gatewayServers = ss.getServers("gateway") ;
		List<Server>  analyticsServers = ss.getServers("analytics") ;
		List<Server>  centralServers = ss.getServers("central") ;
		List<MPServer> allMpServers = ss.getAllMessageProcessorServers(); 
		List<Router> routerServers =  ss.getAllRouterServers();
		List<Postgres> potgresServers = ss.getAllPostgres(); 
		
		System.out.println(gatewayServers);
	
		String[] allVirtuslHosts = env.getAllVirtualHosts() ; 
		System.out.println(allVirtuslHosts.toString());
		
		VirtualHost vh = env.getVirtualHost(allVirtuslHosts[0]) ; 
		System.out.println(vh.toString());
		
		String[] allShardFlows = org.getAllShardFlow() ;
		SharedFlow shardFlow = org.getShardFlow(allShardFlows[1]) ; 
		System.out.println(shardFlow.toString());
		
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
