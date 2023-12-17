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
import com.smartvalue.apigee.rest.schema.proxy.Proxy;
import com.smartvalue.apigee.rest.schema.server.MPServer;
import com.smartvalue.apigee.rest.schema.server.Postgres;
import com.smartvalue.apigee.rest.schema.server.QupidServer;
import com.smartvalue.apigee.rest.schema.server.Router;
import com.smartvalue.apigee.rest.schema.server.Server;
import com.smartvalue.apigee.rest.schema.server.ServerServices;
import com.smartvalue.apigee.rest.schema.sharedFlow.SharedFlow;
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
		File f = new File("googleServiceAccout.json"); 
		String serviceAccountCred = "{\r\n"
				+ "  \"type\": \"service_account\",\r\n"
				+ "  \"project_id\": \"moj-apigee\",\r\n"
				+ "  \"private_key_id\": \"23273ebd6bd286d95b1b97ebd4b9932e6f796751\",\r\n"
				+ "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCu13O9mYa9UQ1v\\nJm5q2k5MewiqrXG0wCwoG0KKbcdxtlBhDS27hJH+dy3OIum/6NgrcEAS6bfEoT0g\\nknuFVYyje4eIc4z+2uIvU9aMX2Tc57tI1ovQfFk9j12M2Ma9bq4qtUVnfITgA2T3\\nhkmODEFwr7OUNBxgZIto6sOCE9SSh6acX0fKTOmtKzCUafxSBOT/DuNtgYAUyB9X\\n4sWhbr1L0CFKumoEiYCWti8a79NV9nfe5Ha7Rq5TkATQYk6r04p8TpAmS3NA53jw\\neqGtvbPa5Gprl13iPdNdZ3m21mCdD7wavxQh5y6t6TBnEK/pfxAxrsvyebuiuzRI\\ntT82kLlFAgMBAAECggEARcdH8m3w1+KevXL1k2CP6jcaJN/25t/hX5pNiqi4dfoA\\nh1M2osWMbKXM2p2Vc5lumJuCfunXBTsihbrgYDzd1asp0rvnFBlwNto7YKd7viwf\\n0ziI8UqHZSmv+NPVMWoYSYQx8FTCRRMluyHVfkMG5P3Cak4H2zKVbuhu4Z2fc2DW\\nEUc7OFOVunctvvW+UahEVOYManLMX+Qzprsb7SSi7eAfA+4nT77ZAU/4hHgaGEKa\\n01vC0fAjGCs07nFefav70hk1nCdiB616B+dBHe7teDXrcNyvbxbZ7DMYsEbYKPhO\\n94Zv8PYqWT4v1xy/gisIjj6pILuH19UxzvpRdHh7ewKBgQDbl5nKKAuBL9XeDbOT\\nH7KyrY3jgvFUocxLwcYZo0oAWGu03rd0uoOUTZT1bRwe66/y80vEFpOn8ijPgLSJ\\nxL+WgH/o5cBNZgUW2x0kYehFphdCkwOSQH6lUPjPiyoaY89OwP+rKOGBL55fkvN2\\nMYVsgBdwPNW6hPdt2IVpO5XdlwKBgQDL1HOzV8ELBHFE0nvFRQd+IHHUp6/q5yHx\\np5NQ19WLuAtpPs5IovdvU1pqgA8UqHqMoh/4gWLR6nYKV5bRXeUcDucPxUD//vTd\\ncV7ylZP8jlWufxK/aghrpXxnGimeut/Qb+hlYbWgbEDZ45PmLWHRDn0AXh5EAFP/\\nyr7bqgbzgwKBgHkW6RRD7swnP6z6KzsG8Lh84dBChaegrRJdYUsD8yurloEfi1SQ\\nATzHZ0vLBgW6+8RiCzavKG8lJwH9vBB4cRmh9GbOQrOrfUT0QOauIrI9e3gXr5F3\\nhR7OPtXbrLTuswN/g7yzh16AjrmvRBe6CRQiskoPEeCPsA1fp3m3BNYzAoGAHdHz\\nK2XHREleGOeCXBYBRp6QiMAPqJowCR6vGsC46Tv/z2H05AOTl6HmNHaPvkH3y+Nc\\nMgsb3gIjStgNpIvMHRLBEzmUjN9MC6mubmcbw3M/bcwBfieRG86WKK9XL3W/kMsO\\ngOaOn+22fyogVSiGiwNHnmomm2RA5cdPrOtvIOkCgYB8NhxHjBmF79JEzsKmLaXW\\n4cHBhCGKr1t5gxdLYz5fM2rj55U2p3vT706OP+Y7CrtWZfD9dupFRyom8Rs8E7VB\\nhHlC8wdA5A1T80++NMwQtRpV3GT82++c3pHKgKAufdBlx7eL1Tw9ypsv76IvWQq0\\npOgtIiP6ZOD5BNAqZXWioA==\\n-----END PRIVATE KEY-----\\n\",\r\n"
				+ "  \"client_email\": \"postman@moj-apigee.iam.gserviceaccount.com\",\r\n"
				+ "  \"client_id\": \"109918179197281334425\",\r\n"
				+ "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\r\n"
				+ "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\r\n"
				+ "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\r\n"
				+ "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/postman%40moj-apigee.iam.gserviceaccount.com\",\r\n"
				+ "  \"universe_domain\": \"googleapis.com\"\r\n"
				+ "}" ; 
		Tester.getGoogleAccessToken(serviceAccountCred);
		
		String specsUrl = "https://api.moj.gov.local/v1/najiz-services/portal/openapi.json" ;   // "https://raw.githubusercontent.com/openapitools/openapi-generator/master/modules/openapi-generator/src/test/resources/3_0/petstore.json" ;
		String lang = "php"; 
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
		
		
		//e.executeRequest("/test01", null, "GET", "") ; 
		JsonParser apigeeConfigParser = new JsonParser( ) ;
		ApigeeConfig ac = apigeeConfigParser.getObject("config.json" , ApigeeConfig.class) ; 
		//ApigeeConfig ac  = ApigeeConfigFactory.create("config.json" , ApigeeConfig.class) ; 

		Infra infra = ac.getInfra("MasterWorks" , "MOJ" , "Prod") ;
		String orgName = "stg" ; 
		String envName = "iam-protected" ; 
		String proxyName = "oidc-core" ;
		String region = "dc-1" ; 
		
		 //Infra infra = ac.getInfra("SmartValue" , "Demo" , "Prod") ; 
		 //String orgName =  "smart-value"  ; // "stg" ; 
		 //String envName = "prod"  ; // "iam-protected"
		 //String proxyName = "DZIT" ;
		 //String region = "dc-1" ; 
		
		ManagementServer ms = infra.getManagementServer(region) ; 
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
