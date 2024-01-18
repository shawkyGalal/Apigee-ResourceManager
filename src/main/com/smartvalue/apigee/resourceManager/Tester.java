package com.smartvalue.apigee.resourceManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.auth.oauth2.GoogleCredentials;
import com.mashape.unirest.http.HttpResponse;
import com.smartvalue.apigee.configuration.ApigeeConfig;
import com.smartvalue.apigee.configuration.filteredList.FilteredList;
import com.smartvalue.apigee.configuration.infra.Infra;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.environmentsMonitor.CondActionPair;
import com.smartvalue.apigee.environmentsMonitor.EnvironmentAction;
import com.smartvalue.apigee.environmentsMonitor.EnvironmentCondition;
import com.smartvalue.apigee.rest.schema.environment.Environment;
import com.smartvalue.apigee.rest.schema.organization.Organization;
import com.smartvalue.apigee.rest.schema.product.ProductsServices;
import com.smartvalue.apigee.rest.schema.proxy.transformers.NullTransformer;
import com.smartvalue.apigee.rest.schema.proxy.Proxy;
import com.smartvalue.apigee.rest.schema.proxy.ProxyServices;
import com.smartvalue.apigee.rest.schema.proxy.transformers.TargetServerTransformer;
import com.smartvalue.apigee.rest.schema.proxy.google.auto.GoogleProxiesList;
import com.smartvalue.apigee.rest.schema.server.MPServer;
import com.smartvalue.apigee.rest.schema.server.Postgres;
import com.smartvalue.apigee.rest.schema.server.QupidServer;
import com.smartvalue.apigee.rest.schema.server.Router;
import com.smartvalue.apigee.rest.schema.server.Server;
import com.smartvalue.apigee.rest.schema.server.ServerServices;
import com.smartvalue.apigee.rest.schema.sharedFlow.SharedFlow;
import com.smartvalue.apigee.rest.schema.sharedFlow.SharedFlowServices;
import com.smartvalue.apigee.rest.schema.ApigeeObjectTransformer;
import com.smartvalue.apigee.rest.schema.TargetServer;
import com.smartvalue.apigee.rest.schema.virtualHost.VirtualHost;
import com.smartvalue.moj.clients.environments.ClientEnvironmentsFactory;
import com.smartvalue.moj.clients.environments.Environments;
import com.smartvalue.moj.clients.environments.JsonParser;
import com.smartvalue.openapi.SDKGeneratoer;
import com.smartvalue.zip.ZipUtility;

public class Tester {

	public static void main (String[] args) throws Exception
	{
		
		/*
		String specsUrl = "https://raw.githubusercontent.com/openapitools/openapi-generator/master/modules/openapi-generator/src/test/resources/3_0/petstore.json" ; //"https://api.moj.gov.local/v1/najiz-services/portal/openapi.json" ;
		String lang = "java"; 
		String outputDirectory = "C:\\Users\\Shawky Foda\\Downloads\\MOJ_SDK_"+lang ;
		TestSDKGenerator(specsUrl , lang , outputDirectory); 
		File outFile = new File(outputDirectory) ; 
		List<File> fileList = new ArrayList<File>() ; 
		fileList.add(outFile); 
		ZipUtility.zip(fileList, outputDirectory+".zip");
		
		Environments clientEnvs = ClientEnvironmentsFactory.create("moj-enviropnments.json") ; 
		com.smartvalue.moj.clients.environments.Environment e  =clientEnvs.getEnvByName("testing") ;
		String authURL = e.getUrlBuilder()
				.withForthAuth(false)
				.withResponseType("code")
				.withScope("openid")
				.buildAuthorizationURL();
		

		*/

		//e.executeRequest("/test01", null, "GET", "") ; 
		JsonParser apigeeConfigParser = new JsonParser( ) ;
		ApigeeConfig ac = apigeeConfigParser.getObject("config.json" , ApigeeConfig.class) ; 
		//ApigeeConfig ac  = ApigeeConfigFactory.create("config.json" , ApigeeConfig.class) ; 


		
		//String orgName = "moj-apigee" ; 

		Infra mojStageinfra = ac.getInfra("MasterWorks" , "MOJ" , "Stage") ;

		//String envName = "iam-protected" ; 
		//String proxyName = "oidc-core" ;
		//String region ; 
		
		 //Infra infra = ac.getInfra("SmartValue" , "Demo" , "Prod") ; 
		 //String orgName =  "smart-value"  ; // "stg" ; 
		 //String envName = "prod"  ; // "iam-protected"
		 //String proxyName = "DZIT" ;
		 //String region = "dc-1" ; 
		
		ManagementServer sourceMngServer = mojStageinfra.getManagementServer(mojStageinfra.getRegions().get(0).getName()) ;

		//region = ms.getRegions().get(0); 
		//String pundleFileName = "//E://MasterWorks//Apigee//Customers//MOJ/10.162.3.3.etc.apigee//apigee-migrate-tool//data_history//MOJ//Prod//moj-prod//moj-internal-clients//2023-11-19-03-06//proxies//AccessCaseFile.zip" ;
		String exportFolderName = "C:\\temp\\MOJ\\Stage" ;
		//==================Export All ===========================
		String sourceOrgName = "stg" ; 
		//HashMap<String, HashMap<String, Exception>> targetServerFaults =  sourceMngServer.getTargetServersServices(sourceOrgName).exportAll(exportFolderName +"targetservers") ;
		//HashMap<String, HashMap<String, Exception>> productsFaults = sourceMngServer.getProductServices(sourceOrgName).exportAll(exportFolderName +"\\products") ; 
		//HashMap<String, HashMap<String, Exception>> appsFaults = sourceMngServer.getApplicationServices(sourceOrgName).exportAll(exportFolderName +"\\apps") ;
		//HashMap<String, HashMap<String, Exception>> proxiesFaults =  sourceMngServer.getProxyServices(sourceOrgName).exportAll(exportFolderName +"\\proxies") ;
		//HashMap<String, HashMap<String, Exception>> sharedflowsFaults =  sourceMngServer.getSharedFlowServices(sourceOrgName).exportAll(exportFolderName +"\\sharedflows") ;
		//HashMap<String, HashMap<String, Exception>> devsFaults =  sourceMngServer.getDevelopersServices(sourceOrgName).exportAll(exportFolderName +"\\developers") ;
		//HashMap<String, HashMap<String, Exception>> kvmsFaults =  sourceMngServer.getKeyValueMapServices(sourceOrgName).exportAll(exportFolderName +"\\kvms") ;
		
		
		String transFolder = "C:\\temp\\MOJ\\Stage_tranformed" ;
		//==================Transform All  ===========================
		ProxyServices ps = (ProxyServices) sourceMngServer.getProxyServices(sourceOrgName); 
		//ps.getTransformers().add(new NullTransformer()) ;
		//ps.getTransformers().add(new TargetServerTransformer()) ;
		//ps.getTransformers().add(new NullTransformer()) ;
		//ps.transformAll(exportFolderName+"\\proxies", transFolder+"\\proxies");
		
		SharedFlowServices sfs = (SharedFlowServices) sourceMngServer.getSharedFlowServices(sourceOrgName); 
		//sfs.getTransformers().add(new TargetServerTransformer()) ; 
		//sfs.getTransformers().add(new NullTransformer()) ;
		sfs.transformAll(exportFolderName + "\\sharedflows", transFolder + "\\sharedflows");
		
		//==================Import All Sequence is Important ===========================
		 
		Infra destInfra = ac.getInfra("MasterWorks" , "MOJ" , "Gcloud(shawky.foda@gmail.com)") ;
		ManagementServer destMngServer = destInfra.getManagementServer(destInfra.getRegions().get(0).getName()) ;
		String destOrgName = "apigee-moj-stage" ;
		ArrayList<HttpResponse<String>> importTargetServersFaults =  destMngServer.getTargetServersServices(destOrgName).importAll(transFolder +"\\targetservers") ;
		ArrayList<HttpResponse<String>> importSharedflowsFaults =  destMngServer.getSharedFlowServices(destOrgName).importAll(transFolder +"\\sharedflows") ;
		ArrayList<HttpResponse<String>> importKvmsFaults =  destMngServer.getKeyValueMapServices(destOrgName).importAll(transFolder +"\\kvms") ;
		ArrayList<HttpResponse<String>> importProxiesFaults =  destMngServer.getProxyServices(destOrgName).importAll(transFolder +"\\proxies") ;
		ArrayList<HttpResponse<String>> importProductsFaults = destMngServer.getProductServices(destOrgName).importAll(transFolder +"\\products") ; 
		ArrayList<HttpResponse<String>> importDevsFaults =  destMngServer.getDevelopersServices(destOrgName).importAll(transFolder +"\\developers") ;
		ArrayList<HttpResponse<String>> importAppsFaults = destMngServer.getApplicationServices(destOrgName).importAll(transFolder +"\\apps") ;
	
		

		/* 
		ms.getProxyServices(orgName).exportAllProxies("C:\\tmp"); 

		Organization org = (Organization) ms.getOrgs().get(orgName) ;  
		Environment env = (Environment) org.getEnvs().get(envName);
		//Environment env02 = (Environment) org.getEnvs().get("cert-protected");
		env.startMonitoring(2);
		//env02.startMonitoring(2);
		
		
		
		ProductsServices   productServices = ms.getProductServices(orgName) ; 
		ArrayList<Object>  productsWithoutProxies  =productServices.getProductsWithoutProxies() ;  
		System.out.println(productsWithoutProxies); 
		
		Proxy proxy = org.getProxy(proxyName); 
		HashMap<String, ArrayList<String>> xx= proxy.getRevisionsUsesTargetServer("Yesser_Server" , true) ; 
		System.out.println(xx);
		
		HashMap<String, Proxy> allProxies = org.getAllProxies();
		Renderer.hashMaptoHtmlTable(allProxies); 
		
		HashMap<String, Object> proxies = org.getAllProxiesUsesTargetServer("Yesser_Server" , true);
		System.out.println(proxies);
		String[] policesNames = {"FC-ELK-Logger" ,  "ELK-Logger" ,  "FC-Elk-Logger" } ; 
		ms.getProxyServices(orgName).getProxiesWithoutPolices(policesNames, true) ; 
		HashMap<String , TargetServer> allTargetServers = env.getTargetServers();  
		System.out.println(allTargetServers);
		
		ArrayList<String> proxiesNotDeployed = org.getUndeployedProxies() ; 
		System.out.println(proxiesNotDeployed.toString());
 		
		
		ms.getFreeMps("dc-2") ; 
		List<MPServer> envMpServers = env.getMessageProcesors(region) ;
		Renderer.arrayListToHtmlTable(envMpServers) ; 

		MPServer mp = ((MPServer)envMpServers.get(0)) ;
		String[] commands = {"pwd" , "ls -ltr"} ; 
		String privateKeyPath = "C:\\Users\\sfoda\\.ssh\\id_rsa"; //"G:\\My Drive\\SmartValue\\.ssh\\ShawkyFoda\\id_rsa";
		mp.executeShell(privateKeyPath , commands) ; 
		HashMap<String , ArrayList<String>> aa = mp.getAssociatedEnvs(region) ; 
		
		MPServer mp1 = ((MPServer)envMpServers.get(1)) ;
		HashMap<String , ArrayList<String>> bb = mp1.getAssociatedEnvs(region) ; 

		
		
		boolean healthy = mp.healthCheck() ;
		ArrayList<String> result = mp.removeFromEnvironmnt(env ) ; 
		Renderer.arrayListToHtmlTable(result) ;
		
		result = mp.addToEnvironmnt( env ) ; 
		Renderer.arrayListToHtmlTable(result) ;
		
		result = env.removeMessageProcessor(mp) ; 
		result = env.addMessageProcessor(mp) ;
		
		org.getAllApps() ; 
		ServerServices ss = ms.getServerServices() ;
		ss.getServers("gateway", region) ; 
		List<Server>  gatewayServers = ss.getServers("gateway" , region ) ;
		List<Server>  analyticsServers = ss.getServers("analytics" , region ) ;
		List<Server>  centralServers = ss.getServers("central" , region ) ;
		
		FilteredList<MPServer> regionAllMpServers = ss.getMPServers(region); 
		FilteredList<Router> routerServers =  ss.getRouterServers(region);
		FilteredList<Postgres> potgresServers = ss.getPostgresServers(region); 
		FilteredList<QupidServer> qupidServers =  ss.getQupidServers(region) ;
		FilteredList<ManagementServer> mgmServer = ss.getManagementServers(region)  ; 
		List<MPServer> dcMPs= env.getMessageProcesors(region);
		List<MPServer> drMPs= env.getMessageProcesors("dc-2");
		
		ss.getAllEnvsMessageProcessors(orgName); 
		
		ss.getOnlyUpMpServers(region); 
		
		System.out.println(gatewayServers);
	
		String[] allVirtuslHosts = env.getAllVirtualHosts() ; 
			
		System.out.println(allVirtuslHosts.toString());
		
		VirtualHost vh = env.getVirtualHostByName(allVirtuslHosts[0]) ;
		HttpResponse<String> resultxx = vh.executeGetRequest("/test01" , null,  null) ; 
		System.out.println(resultxx.toString());
		
		String[] allShardFlows = org.getAllShardFlow() ;
		SharedFlow shardFlow = org.getShardFlow(allShardFlows[1]) ; 
		System.out.println(shardFlow.toString());
		
		//-- Testing Environment Monitoring Framework -- 
		ArrayList<CondActionPair> condActionPairs = new ArrayList<>() ;
		EnvironmentCondition ec = new EnvironmentCondition(env) 
			{ 	@Override
				public boolean evaluate() throws Exception  {
					boolean result  = true ; 
					ManagementServer ms = this.getEnv().getMs(); 
					ArrayList<String> regions = ms.getRegions(); 
					for (String region : regions)
					{	List<MPServer> mpservers = this.getEnv().getMessageProcesors(region);
						for (MPServer mpserver : mpservers )
						{
							result = result  && mpserver.healthCheck(); 
							if (! result) 
							{
								break ; 
							}
						}
					}
					return result ; 
				}
			};
		
		EnvironmentAction ea = new EnvironmentAction(env) 
			{	@Override
				public void run() throws Exception {
					// TODO Auto-generated method stub
					// Add one of the free mp's to this env. 
					Environment env = this.getEnv() ;
					ArrayList<MPServer> freeMps = env.getMs().getFreeMps(region) ;
					env.addMessageProcessor(freeMps.get(0)) ; 
				}
			} ; 
		 
		CondActionPair cp = new CondActionPair() ; 
		cp.setAction(ea); cp.setCondition(ec); 
		condActionPairs.add(cp);
		env.monitor(condActionPairs);
		
		*/
		
	
	} 

	public static void TestSDKGenerator(String specsUrl , String lang , String outputDirectory ) {
		SDKGeneratoer sdkg = new SDKGeneratoer() ;
		sdkg.setLang(lang);
				
		// "https://raw.githubusercontent.com/openapitools/openapi-generator/master/modules/openapi-generator/src/test/resources/3_0/petstore.json" ;  
		sdkg.setOutputDir(outputDirectory ) ; 
		sdkg.setPackageName("org.moj.najiz.sdk");
		sdkg.setValidateSpecs(false); 
		// "https://api.moj.gov.local/realestateidentityid/openapi.json" ; //
		  
		sdkg.generateSDK(specsUrl);
	}
	
	public static com.google.auth.oauth2.AccessToken getGoogleAccessToken(String serviceAccountString) throws Exception {
		GoogleCredentials credentials;
		try {
			byte[] bytes = serviceAccountString.getBytes(StandardCharsets.UTF_8);

	        // Create an InputStream from the byte array
	        InputStream xx  = new java.io.ByteArrayInputStream(bytes);
	        
			credentials = GoogleCredentials.fromStream(xx) //new FileInputStream(serviceAccountJSON)
					.createScoped("https://www.googleapis.com/auth/cloud-platform");
			credentials.refreshIfExpired();
			com.google.auth.oauth2.AccessToken token = credentials.getAccessToken();
			return token;
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}
	
		
}
