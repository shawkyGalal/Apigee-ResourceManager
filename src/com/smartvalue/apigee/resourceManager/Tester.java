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
		//HashMap<String, ArrayList<String>> xx= proxy.getRevisionsUsesTargetServer("Yesser_Server") ; 
		//System.out.println(xx);
		
		//HashMap<String, Proxy> allProxies = org.getAllProxies();
		HashMap<String, Object> proxies = org.getAllProxiesUsesTargetServer("Yesser_Server" , true);
		System.out.println(proxies);
		
		//HashMap<String , TargetServer> allTargetServers = env.getTargetServers();  
		//System.out.println(allTargetServers);
		
		//ArrayList<String> proxiesNotDeployed = org.getUndeployedProxies() ; 
		//System.out.println(proxiesNotDeployed.toString());
		
		
		//List<MPServer> envMpServers = env.getMessageProcesors("dc-1") ;
		//envMpServers.get(0).healthCheck() ;
		//ArrayList<String> result = envMpServers.get(0).removeFromEnvironmnt(org , env ) ; 
		//result = envMpServers.get(0).addToEnvironmnt(org , env ) ; 
		//System.out.println(envMpServers);
		
		//result = env.removeMessageProcessor(envMpServers.get(0)) ; 
		//result = env.addMessageProcessor(envMpServers.get(0)) ;
		
		//ServerServices ss = ms.getServerServices() ; 
		//List<Server>  gatewayServers = ss.getServers("gateway") ;
		//List<Server>  analyticsServers = ss.getServers("analytics") ;
		//List<Server>  centralServers = ss.getServers("central") ;
		//List<MPServer> allMpServers = ss.getAllMessageProcessorServers(); 
		//List<Router> routerServers =  ss.getAllRouterServers();
		//List<Postgres> potgresServers = ss.getAllPostgres(); 
		
		//System.out.println(gatewayServers);
	
		//String[] allVirtuslHosts = env.getAllVirtualHosts() ; 
		//System.out.println(allVirtuslHosts.toString());
		
		//VirtualHost vh = env.getVirtualHost(allVirtuslHosts[0]) ; 
		//System.out.println(vh.toString());
		
		//String[] allShardFlows = org.getAllShardFlow() ;
		//SharedFlow shardFlow = org.getShardFlow(allShardFlows[1]) ; 
		//System.out.println(shardFlow.toString());
		
		String proxyDeployment = "{\r\n" + 
				"    \"environment\": [\r\n" + 
				"        {\r\n" + 
				"            \"name\": \"iam-protected\",\r\n" + 
				"            \"revision\": [\r\n" + 
				"                {\r\n" + 
				"                    \"configuration\": {\r\n" + 
				"                        \"basePath\": \"/\",\r\n" + 
				"                        \"configVersion\": \"SHA-512:fe4d685b79cc9a75ead8f0ec6b1b4bb4724b14f0eba7727059bfffea3cede679323174c0c037a931a7ae0bcfd42fb6ced33fc662a02fbd1bb4201283023b6e15:dc-1\",\r\n" + 
				"                        \"steps\": []\r\n" + 
				"                    },\r\n" + 
				"                    \"name\": \"45\",\r\n" + 
				"                    \"server\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"pod\": {\r\n" + 
				"                                \"name\": \"gateway-2\",\r\n" + 
				"                                \"region\": \"dc-2\"\r\n" + 
				"                            },\r\n" + 
				"                            \"status\": \"deployed\",\r\n" + 
				"                            \"type\": [\r\n" + 
				"                                \"message-processor\"\r\n" + 
				"                            ],\r\n" + 
				"                            \"uUID\": \"b633d588-c343-49d0-89e4-2b92230d6b88\"\r\n" + 
				"                        },\r\n" + 
				"                        {\r\n" + 
				"                            \"pod\": {\r\n" + 
				"                                \"name\": \"gateway\",\r\n" + 
				"                                \"region\": \"dc-1\"\r\n" + 
				"                            },\r\n" + 
				"                            \"status\": \"deployed\",\r\n" + 
				"                            \"type\": [\r\n" + 
				"                                \"message-processor\"\r\n" + 
				"                            ],\r\n" + 
				"                            \"uUID\": \"bf9d3f83-0b44-4801-bfc6-d049d377e37b\"\r\n" + 
				"                        },\r\n" + 
				"                        {\r\n" + 
				"                            \"pod\": {\r\n" + 
				"                                \"name\": \"gateway\",\r\n" + 
				"                                \"region\": \"dc-1\"\r\n" + 
				"                            },\r\n" + 
				"                            \"status\": \"deployed\",\r\n" + 
				"                            \"type\": [\r\n" + 
				"                                \"message-processor\"\r\n" + 
				"                            ],\r\n" + 
				"                            \"uUID\": \"a3c6d03a-9fd6-4cc9-a88f-5e943f645388\"\r\n" + 
				"                        },\r\n" + 
				"                        {\r\n" + 
				"                            \"pod\": {\r\n" + 
				"                                \"name\": \"gateway-2\",\r\n" + 
				"                                \"region\": \"dc-2\"\r\n" + 
				"                            },\r\n" + 
				"                            \"status\": \"deployed\",\r\n" + 
				"                            \"type\": [\r\n" + 
				"                                \"message-processor\"\r\n" + 
				"                            ],\r\n" + 
				"                            \"uUID\": \"05cfbb50-df64-411b-b0c3-3a334b827fc4\"\r\n" + 
				"                        },\r\n" + 
				"                        {\r\n" + 
				"                            \"pod\": {\r\n" + 
				"                                \"name\": \"gateway\",\r\n" + 
				"                                \"region\": \"dc-1\"\r\n" + 
				"                            },\r\n" + 
				"                            \"status\": \"deployed\",\r\n" + 
				"                            \"type\": [\r\n" + 
				"                                \"router\"\r\n" + 
				"                            ],\r\n" + 
				"                            \"uUID\": \"8b7f25ce-7725-4a3b-a53b-1d435129b10e\"\r\n" + 
				"                        },\r\n" + 
				"                        {\r\n" + 
				"                            \"pod\": {\r\n" + 
				"                                \"name\": \"gateway-2\",\r\n" + 
				"                                \"region\": \"dc-2\"\r\n" + 
				"                            },\r\n" + 
				"                            \"status\": \"deployed\",\r\n" + 
				"                            \"type\": [\r\n" + 
				"                                \"router\"\r\n" + 
				"                            ],\r\n" + 
				"                            \"uUID\": \"7cce8a50-ecd6-4bcd-a146-8ec9a85ed1c5\"\r\n" + 
				"                        },\r\n" + 
				"                        {\r\n" + 
				"                            \"pod\": {\r\n" + 
				"                                \"name\": \"gateway-2\",\r\n" + 
				"                                \"region\": \"dc-2\"\r\n" + 
				"                            },\r\n" + 
				"                            \"status\": \"deployed\",\r\n" + 
				"                            \"type\": [\r\n" + 
				"                                \"router\"\r\n" + 
				"                            ],\r\n" + 
				"                            \"uUID\": \"49d74066-c784-4b29-9b96-a8c504ab981d\"\r\n" + 
				"                        },\r\n" + 
				"                        {\r\n" + 
				"                            \"pod\": {\r\n" + 
				"                                \"name\": \"gateway\",\r\n" + 
				"                                \"region\": \"dc-1\"\r\n" + 
				"                            },\r\n" + 
				"                            \"status\": \"deployed\",\r\n" + 
				"                            \"type\": [\r\n" + 
				"                                \"router\"\r\n" + 
				"                            ],\r\n" + 
				"                            \"uUID\": \"6a1098ac-ab2f-44c5-9a3b-3530241897fa\"\r\n" + 
				"                        },\r\n" + 
				"                        {\r\n" + 
				"                            \"pod\": {\r\n" + 
				"                                \"name\": \"gateway\",\r\n" + 
				"                                \"region\": \"dc-1\"\r\n" + 
				"                            },\r\n" + 
				"                            \"status\": \"deployed\",\r\n" + 
				"                            \"type\": [\r\n" + 
				"                                \"router\"\r\n" + 
				"                            ],\r\n" + 
				"                            \"uUID\": \"6dcd178d-94ba-464b-af07-b674de15712f\"\r\n" + 
				"                        },\r\n" + 
				"                        {\r\n" + 
				"                            \"pod\": {\r\n" + 
				"                                \"name\": \"gateway-2\",\r\n" + 
				"                                \"region\": \"dc-2\"\r\n" + 
				"                            },\r\n" + 
				"                            \"status\": \"deployed\",\r\n" + 
				"                            \"type\": [\r\n" + 
				"                                \"router\"\r\n" + 
				"                            ],\r\n" + 
				"                            \"uUID\": \"bdab8124-64c0-4a89-8923-73ec96a2bcf3\"\r\n" + 
				"                        }\r\n" + 
				"                    ],\r\n" + 
				"                    \"state\": \"deployed\"\r\n" + 
				"                }\r\n" + 
				"            ]\r\n" + 
				"        }\r\n" + 
				"    ],\r\n" + 
				"    \"name\": \"oidc-core\",\r\n" + 
				"    \"organization\": \"stg\"\r\n" + 
				"}" ; 
		//ac.generateJavaClassFromJson(proxyDeployment, "ProxyDeployment" , "com.smartvalue.apigee.rest.schema.proxyDeployment.auto");
		
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
