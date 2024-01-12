package com.smartvalue.apigee.testNG;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.ApigeeConfig;
import com.smartvalue.apigee.configuration.ApigeeConfigFactory;
import com.smartvalue.apigee.configuration.infra.Infra;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.resourceManager.Renderer;
import com.smartvalue.apigee.rest.schema.ApigeeService;
import com.smartvalue.apigee.rest.schema.TargetServer;
import com.smartvalue.apigee.rest.schema.environment.Environment;
import com.smartvalue.apigee.rest.schema.organization.Organization;
import com.smartvalue.apigee.rest.schema.product.ProductsServices;
import com.smartvalue.apigee.rest.schema.proxy.Proxy;
import com.smartvalue.apigee.rest.schema.proxy.transformers.TargetServerTransformer;
import com.smartvalue.apigee.rest.schema.proxy.transformers.TransformResult;
import com.smartvalue.apigee.rest.schema.server.MPServer;
import com.smartvalue.moj.clients.environments.ClientEnvironmentsFactory;
import com.smartvalue.moj.clients.environments.Environments;
import com.smartvalue.moj.clients.environments.JsonParser;
import com.smartvalue.openapi.SDKGeneratoer;
import com.smartvalue.zip.ZipUtility;

public class NewTest {
	Infra infra ; 
	String orgName ; 
	ApigeeConfig ac ; 
	String envName ; 
	String proxyName ;
	String region ; 
	com.smartvalue.apigee.configuration.infra.ManagementServer ms ; 
	Organization org  ; 
	Environment env ; 
	
	String transformFolderName = "C:\\temp\\Transform\\Stage" ; 
	
	 @Test
	  public void testExportAll() throws Exception {
		//==================Export All ===========================
		JsonParser apigeeConfigParser = new JsonParser( ) ;
		ApigeeConfig ac = apigeeConfigParser.getObject("config_Example.json" , ApigeeConfig.class) ; 
		Infra mojStageinfra = ac.getInfra("YourCompanyName" , "Customer01" , "Stage") ;
		ManagementServer sourceMngServer = mojStageinfra.getManagementServer(mojStageinfra.getRegions().get(0).getName()) ;
		String destFolderName = "C:\\temp\\Stage" ;
		String sourceOrgName = "stg" ; 
		//HashMap<String, HashMap<String, Exception>> targetServerFaults =  sourceMngServer.getTargetServersServices(sourceOrgName).exportAll(destFolderName +"\\targetservers") ;
		//HashMap<String, HashMap<String, Exception>> productsFaults = sourceMngServer.getProductServices(sourceOrgName).exportAll(destFolderName +"\\products") ; 
		//HashMap<String, HashMap<String, Exception>> appsFaults = sourceMngServer.getApplicationServices(sourceOrgName).exportAll(destFolderName +"\\apps") ;
		//HashMap<String, HashMap<String, Exception>> proxiesFaults =  sourceMngServer.getProxyServices(sourceOrgName).exportAll(destFolderName +"\\proxies") ;
		HashMap<String, HashMap<String, Exception>> sharedflowsFaults =  sourceMngServer.getSharedFlowServices(sourceOrgName).exportAll(destFolderName +"\\sharedflows") ;
		//HashMap<String, HashMap<String, Exception>> devsFaults =  sourceMngServer.getDevelopersServices(sourceOrgName).exportAll(destFolderName +"\\developers") ;
		//HashMap<String, HashMap<String, Exception>> kvmsFaults =  sourceMngServer.getKeyValueMapServices(sourceOrgName).exportAll(destFolderName +"\\kvms") ;
		
	  }

	  @Test
	  public void testTransformAll() throws Exception {
		//==================Transform All ===========================
		JsonParser apigeeConfigParser = new JsonParser( ) ;
		ApigeeConfig ac = apigeeConfigParser.getObject("config.json" , ApigeeConfig.class) ; 
		Infra mojStageinfra = ac.getInfra("MasterWorks" , "Moj" , "dev") ;
		ManagementServer sourceMngServer = mojStageinfra.getManagementServer(mojStageinfra.getRegions().get(0).getName()) ;
		String sourceFolderName = "C:\\temp\\Stage" ;
		
		String sourceOrgName = "training01" ; 
		//ArrayList<TransformResult> targetServerFaults =  sourceMngServer.getTargetServersServices(sourceOrgName).transformAll(sourceFolderName +"\\targetservers" , transformFolderName +"\\targetservers") ;
		//ArrayList<TransformResult> kvmsFaults =  sourceMngServer.getKeyValueMapServices(sourceOrgName).transformAll(sourceFolderName +"\\kvms" , transformFolderName +"\\kvms" ) ;
		//ArrayList<TransformResult> sharedflowsFaults =  sourceMngServer.getSharedFlowServices(sourceOrgName).transformAll(sourceFolderName +"\\sharedflows" , transformFolderName +"\\sharedflows") ;
		ApigeeService serv =  sourceMngServer.getProxyServices(sourceOrgName); 
		serv.getTransformers().add(new TargetServerTransformer()) ; 
		ArrayList<TransformResult> proxiesFaults =  serv.transformAll(sourceFolderName +"\\proxies" , transformFolderName +"\\proxies") ;
		//ArrayList<TransformResult> productsFaults = sourceMngServer.getProductServices(sourceOrgName).transformAll(sourceFolderName +"\\products" , transformFolderName +"\\products") ; 
		//ArrayList<TransformResult> devsFaults =  sourceMngServer.getDevelopersServices(sourceOrgName).transformAll(sourceFolderName +"\\developers" , transformFolderName +"\\developers" ) ;
		//ArrayList<TransformResult> appsFaults = sourceMngServer.getApplicationServices(sourceOrgName).transformAll(sourceFolderName +"\\apps" , transformFolderName +"\\apps" ) ;
	  }
	 
	 
	 @Test
	  public void testImportAll() throws Exception {
		//==================Import All ===========================
		JsonParser apigeeConfigParser = new JsonParser( ) ;
		ApigeeConfig ac = apigeeConfigParser.getObject("config.json" , ApigeeConfig.class) ; 
		Infra mojStageinfra = ac.getInfra("MasterWorks" , "Moj" , "dev") ;
		ManagementServer sourceMngServer = mojStageinfra.getManagementServer(mojStageinfra.getRegions().get(0).getName()) ;
		String sourceFolderName = transformFolderName ; // "C:\\temp\\Stage" ;
		String sourceOrgName = "training01" ; 
		//ArrayList<HttpResponse<String>> targetServerFaults =  sourceMngServer.getTargetServersServices(sourceOrgName).importAll(sourceFolderName +"\\targetservers") ;
		//ArrayList<HttpResponse<String>> kvmsFaults =  sourceMngServer.getKeyValueMapServices(sourceOrgName).importAll(sourceFolderName +"\\kvms") ;
		//ArrayList<HttpResponse<String>> sharedflowsFaults =  sourceMngServer.getSharedFlowServices(sourceOrgName).importAll(sourceFolderName +"\\sharedflows") ;
		ArrayList<HttpResponse<String>> proxiesFaults =  sourceMngServer.getProxyServices(sourceOrgName).withDeployUponUpload(true).importAll(sourceFolderName +"\\proxies") ;
		//ArrayList<HttpResponse<String>> productsFaults = sourceMngServer.getProductServices(sourceOrgName).importAll(sourceFolderName +"\\products") ; 
		//ArrayList<HttpResponse<String>> devsFaults =  sourceMngServer.getDevelopersServices(sourceOrgName).importAll(sourceFolderName +"\\developers") ;
		//ArrayList<HttpResponse<String>> appsFaults = sourceMngServer.getApplicationServices(sourceOrgName).importAll(sourceFolderName +"\\apps") ;
	  }
	 
	 @Test
	  public void testDeleteAll() throws Exception {
		//==================Import All ===========================
		JsonParser apigeeConfigParser = new JsonParser( ) ;
		ApigeeConfig ac = apigeeConfigParser.getObject("config.json" , ApigeeConfig.class) ; 
		Infra mojStageinfra = ac.getInfra("SmartValue" , "Moj" , "dev") ;
		ManagementServer sourceMngServer = mojStageinfra.getManagementServer(mojStageinfra.getRegions().get(0).getName()) ;
		String sourceOrgName = "training01" ; 
		ArrayList<HttpResponse<String>> targetServerFaults =  sourceMngServer.getTargetServersServices(sourceOrgName).deleteAll() ;
		ArrayList<HttpResponse<String>> kvmsFaults =  sourceMngServer.getKeyValueMapServices(sourceOrgName).deleteAll() ;
		ArrayList<HttpResponse<String>> sharedflowsFaults =  sourceMngServer.getSharedFlowServices(sourceOrgName).deleteAll() ;
		ArrayList<HttpResponse<String>> proxiesFaults =  sourceMngServer.getProxyServices(sourceOrgName).deleteAll() ;
		ArrayList<HttpResponse<String>> productsFaults = sourceMngServer.getProductServices(sourceOrgName).deleteAll() ; 
		ArrayList<HttpResponse<String>> devsFaults =  sourceMngServer.getDevelopersServices(sourceOrgName).deleteAll() ;
		ArrayList<HttpResponse<String>> appsFaults = sourceMngServer.getApplicationServices(sourceOrgName).deleteAll() ;
	  }
	 
	  @Test
	  public void testProductsWithoutProxies() throws UnirestException, IOException {
		ProductsServices   productServices = ms.getProductServices(orgName) ; 
		ArrayList<Object>  productsWithoutProxies  =productServices.getProductsWithoutProxies() ;  
		System.out.println(productsWithoutProxies); 
		assert productsWithoutProxies.size() == 0 : "Product With No Proxies not Found!";
	  }
	  
	  @Test
	  public void testRevisionsUsesTargetServer() throws UnirestException, IOException {
		  Proxy proxy = org.getProxy(proxyName); 
			HashMap<String, ArrayList<String>> xx= proxy.getRevisionsUsesTargetServer("Yesser_Server" , true) ; 
			System.out.println(xx);
	  }
	  @Test
	  public void testSdkGenerator() throws FileNotFoundException, IOException
	  {
		  String specsUrl = "https://api-test.moj.gov.local/v1/najiz-services/portal/openapi.json" ; //"https://api.moj.gov.local/v1/najiz-services/portal/openapi.json" ;   // "https://raw.githubusercontent.com/openapitools/openapi-generator/master/modules/openapi-generator/src/test/resources/3_0/petstore.json" ;
		  				     
		  String lang = "java"; 
			String outputDirectory = "C:\\Users\\sfoda\\Documents\\GitHub\\MOJ_SDK" ;
			TestSDKGenerator(specsUrl , lang , outputDirectory , "org.moj.najiz.sdk" , false); 
			//File outFile = new File(outputDirectory) ; 
			//List<File> fileList = new ArrayList<File>() ; 
			//fileList.add(outFile); 
			//ZipUtility.zip(fileList, outputDirectory+".zip");
			
	  }
	  
	  private static void TestSDKGenerator(String specsUrl , String lang , String outputDirectory , String packageName , boolean validateSpecs ) {
			SDKGeneratoer sdkg = new SDKGeneratoer() ;
			sdkg.setLang(lang);
					
			// "https://raw.githubusercontent.com/openapitools/openapi-generator/master/modules/openapi-generator/src/test/resources/3_0/petstore.json" ;  
			sdkg.setOutputDir(outputDirectory ) ; 
			sdkg.setPackageName(packageName );
			sdkg.setValidateSpecs(validateSpecs); 
			// "https://api.moj.gov.local/realestateidentityid/openapi.json" ; //
			  
			sdkg.generateSDK(specsUrl);
		}
	  
	  @Test
	  public void testOtherServices() throws Exception {
		  HashMap<String, Proxy> allProxies = org.getAllProxies();
			Renderer.hashMaptoHtmlTable(allProxies); 
			
			HashMap<String, Object> proxies = org.getAllProxiesUsesTargetServer("Yesser_Server" , true);
			System.out.println(proxies);
			String[] aa = {"FC-ELK-Logger" ,  "ELK-Logger" ,  "FC-Elk-Logger" } ; 
			ms.getProxyServices(orgName).getProxiesWithoutPolices(aa, true) ; 
			HashMap<String , TargetServer> allTargetServers = env.getTargetServers();  
			System.out.println(allTargetServers);
			
			ArrayList<String> proxiesNotDeployed = org.getUndeployedProxies() ; 
			System.out.println(proxiesNotDeployed.toString());
	 		
			
			List<MPServer> envMpServers = env.getMessageProcesors(region) ;
			Renderer.arrayListToHtmlTable(envMpServers) ; 

			MPServer mps = ((MPServer)envMpServers.get(0)) ;
			
			boolean healthy = mps.healthCheck() ;
			ArrayList<String> result = mps.removeFromEnvironmnt(env ) ; 
			Renderer.arrayListToHtmlTable(result) ;
			
			result = mps.addToEnvironmnt(env ) ; 
			Renderer.arrayListToHtmlTable(result) ;
			
			result = env.removeMessageProcessor(mps) ; 
			result = env.addMessageProcessor(mps) ;
	  }
	  
	  @BeforeMethod
	  public void beforeMethod() {
	  }

	  @BeforeClass
	  @Test(dataProvider = "testData")
	  public void beforeClass() throws Exception 
	  {
			ApigeeConfig ac  = ApigeeConfigFactory.create("config.json" , ApigeeConfig.class) ; 
			infra = ac.getInfra("MasterWorks" , "MOJ" , "Stage") ;
			region = "dc-1" ; 
			orgName = "stg" ; 
			envName = "iam-protected" ; 
			proxyName = "oidc-core" ;
	  
		  ms = infra.getManagementServer(region); // com.smartvalue.apigee.configuration.infra.ManagementServer(infra) ; 
		  org =  ms.getOrgByName(orgName) ;  
		  env =  org.getEnvByName(envName);

	  }

	  @BeforeTest
	  public void beforeTest() {
	  }

	  @AfterTest
	  public void afterTest() {
	  }
	  
	 

	}
