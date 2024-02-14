package com.smartvalue.apigee.testNG;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.smartvalue.apigee.rest.schema.ApigeeObjectTransformer;
import com.smartvalue.apigee.rest.schema.ApigeeService;
import com.smartvalue.apigee.rest.schema.TargetServer;
import com.smartvalue.apigee.rest.schema.environment.Environment;
import com.smartvalue.apigee.rest.schema.organization.Organization;
import com.smartvalue.apigee.rest.schema.product.ProductsServices;
import com.smartvalue.apigee.rest.schema.proxy.Proxy;
import com.smartvalue.apigee.rest.schema.proxy.ProxyServices;
import com.smartvalue.apigee.rest.schema.proxy.transformers.TransformResult;
import com.smartvalue.apigee.rest.schema.proxy.transformers.ZipFileEntryModifyTransformer;
import com.smartvalue.apigee.rest.schema.server.MPServer;
import com.smartvalue.apigee.rest.schema.sharedFlow.SharedFlowServices;
import com.smartvalue.moj.clients.environments.JsonParser;
import com.smartvalue.openapi.SDKGeneratoer;

public class NewTest {
	
	String sourceInfraName = "Stage";
	String sourceOrgName = "stg"; 
	Infra sourceInfra ; 
	ManagementServer sourceMngServer ; 
	
	String destInfraName = "Gcloud(shawky.foda@gmail.com)";  
	String destOrgName = "apigee-moj-stage"; 
	Infra destInfra; 
	ManagementServer destMngServer ; 
	boolean deployUponImport = false ; 
	
	
	ApigeeConfig ac ; 
	String envName ; 
	String proxyName ;
	String region ; 
	
	Organization org  ; 
	Environment env ; 
	
	String transformFolderName = "C:\\temp\\Transform\\Stage" ; 
	
	 @Test
	  public void testExportAll() throws Exception {
		//==================Export All ===========================
		JsonParser apigeeConfigParser = new JsonParser( ) ;
		ApigeeConfig ac = apigeeConfigParser.getObject("config.json" , ApigeeConfig.class) ; 
		Infra mojStageinfra = ac.getInfra("MasterWorks" , "MOJ" , "Stage") ;
		ManagementServer sourceMngServer = mojStageinfra.getManagementServer(mojStageinfra.getRegions().get(0).getName()) ;
		String destFolderName = "C:\\temp\\Stage" ;
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
		ManagementServer sourceMngServer = sourceInfra.getManagementServer(sourceInfra.getRegions().get(0).getName()) ;
		String sourceFolderName = "C:\\temp\\Stage" ;
		
		//ArrayList<TransformResult> targetServerFaults =  sourceMngServer.getTargetServersServices(sourceOrgName).transformAll(sourceFolderName +"\\targetservers" , transformFolderName +"\\targetservers") ;
		//ArrayList<TransformResult> kvmsFaults =  sourceMngServer.getKeyValueMapServices(sourceOrgName).transformAll(sourceFolderName +"\\kvms" , transformFolderName +"\\kvms" ) ;
		
		
		//-- Build Transformers ----
		//ArrayList<ApigeeObjectTransformer> transformers = new ArrayList<ApigeeObjectTransformer>(); 
		//transformers.add(new TargetServerTransformer()) ; 
		//List<String> searchFor = Arrays.asList("<Pattern/>"	);
	    //List<String> replaceBy = Arrays.asList("<Pattern>xxxxxxx</Pattern>");
		//ZipFileEntryModifyTransformer zfet = new ZipFileEntryModifyTransformer("apiproxy/policies/Regular-Expression-Protection.xml", searchFor, replaceBy);
		//transformers.add(zfet) ; 
		//----End of building transformers 
		
		SharedFlowServices sfs = (SharedFlowServices) sourceMngServer.getSharedFlowServices(sourceOrgName);
		//sfs.setTranformers(transformers); 
		ArrayList<TransformResult> sharedflowsFaults =  sfs.transformAll(sourceFolderName +"\\sharedflows" , transformFolderName +"\\sharedflows") ;
		
		ApigeeService proxyServ =  sourceMngServer.getProxyServices(sourceOrgName); 
		//proxyServ.setTranformers(transformers); 
		ArrayList<TransformResult> proxiesFaults =  proxyServ.transformAll(sourceFolderName +"\\proxies" , transformFolderName +"\\proxies") ;
		//ArrayList<TransformResult> productsFaults = sourceMngServer.getProductServices(sourceOrgName).transformAll(sourceFolderName +"\\products" , transformFolderName +"\\products") ; 
		//ArrayList<TransformResult> devsFaults =  sourceMngServer.getDevelopersServices(sourceOrgName).transformAll(sourceFolderName +"\\developers" , transformFolderName +"\\developers" ) ;
		//ArrayList<TransformResult> appsFaults = sourceMngServer.getApplicationServices(sourceOrgName).transformAll(sourceFolderName +"\\apps" , transformFolderName +"\\apps" ) ;
	  }
	 
	  private static final Logger svlogger = LogManager.getLogger(NewTest.class);

	 
	 @Test
	  public void testImportAll() throws Exception {
    	//==================Import All ===========================
		String sourceFolderName = transformFolderName ; // "C:\\temp\\Stage" ;
		//String destOrgName = "apigee-moj-stage" ; 
		//ArrayList<HttpResponse<String>> targetServerFaults =  sourceMngServer.getTargetServersServices(sourceOrgName).importAll(sourceFolderName +"\\targetservers") ;
		//ArrayList<HttpResponse<String>> kvmsFaults =  sourceMngServer.getKeyValueMapServices(sourceOrgName).importAll(sourceFolderName +"\\kvms") ;
		ArrayList<HttpResponse<String>> sharedflowsFaults =  ((SharedFlowServices) destMngServer.getSharedFlowServices(destOrgName)).withDeployUponUpload(deployUponImport).importAll(sourceFolderName +"\\sharedflows") ;
		ArrayList<HttpResponse<String>> proxiesFaults =  ((ProxyServices) destMngServer.getProxyServices(destOrgName)).withDeployUponUpload(deployUponImport).importAll(sourceFolderName +"\\proxies") ;
		//ArrayList<HttpResponse<String>> productsFaults = sourceMngServer.getProductServices(sourceOrgName).importAll(sourceFolderName +"\\products") ; 
		//ArrayList<HttpResponse<String>> devsFaults =  sourceMngServer.getDevelopersServices(sourceOrgName).importAll(sourceFolderName +"\\developers") ;
		//ArrayList<HttpResponse<String>> appsFaults = sourceMngServer.getApplicationServices(sourceOrgName).importAll(sourceFolderName +"\\apps") ;

		
	  }
	 
	 @Test(groups = "deleteAll")
	  public void testDeleteAllProxies() throws Exception {
		//==================Import All ===========================
		destMngServer = destInfra.getManagementServer(destInfra.getRegions().get(0).getName()) ; 
		ArrayList<HttpResponse<String>> proxiesFaults =  destMngServer.getProxyServices(destOrgName).deleteAll() ;
	  }

	 
	 @Test()
	  public void testDeleteAll() throws Exception {
		testDeleteAllApplications();
		testDeleteAllDevelopers();
		testDeleteAllProducts();
		testDeleteAllTargetservers();
		testDeleteAllKvms();
		testDeleteAllsharedflows();
		testDeleteAllProxies();
	 }
	 
	 @Test(groups = "deleteAll")
	  public void testDeleteAllsharedflows() throws Exception {
		ArrayList<HttpResponse<String>> sharedflowsFaults =  destMngServer.getSharedFlowServices(destOrgName).deleteAll() ;
	 }
	 
	 @Test(groups = "deleteAll")
	  public void testDeleteAllTargetservers() throws Exception {
		ArrayList<HttpResponse<String>> sharedflowsFaults =  destMngServer.getTargetServersServices(destOrgName).deleteAll() ;
	 }

	 @Test(groups = "deleteAll")
	 public void testDeleteAllKvms() throws Exception {
		 ArrayList<HttpResponse<String>> kvmsFaults =  destMngServer.getKeyValueMapServices(destOrgName).deleteAll() ;	 
	 }

	 @Test(groups = "deleteAll")
	 public void testDeleteAllProducts() throws Exception {
		 ArrayList<HttpResponse<String>> productsFaults = destMngServer.getProductServices(destOrgName).deleteAll() ; 	 
	 }
	 
	 @Test(groups = "deleteAll")
	 public void testDeleteAllDevelopers() throws Exception {
			ArrayList<HttpResponse<String>> devsFaults =  destMngServer.getDevelopersServices(destOrgName).deleteAll() ;
	 }
	 
	 @Test(groups = "deleteAll")
	 public void testDeleteAllApplications() throws Exception {
			ArrayList<HttpResponse<String>> appsFaults = destMngServer.getApplicationServices(destOrgName).deleteAll() ;
	 }
	 

	 
	 @Test
	  public void testProductsWithoutProxies() throws UnirestException, IOException {
		ProductsServices   productServices = (ProductsServices) destMngServer.getProductServices(sourceOrgName) ; 
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
		  HashMap<Object, Proxy> allProxies = org.getAllProxies();
			Renderer.hashMaptoHtmlTable(allProxies); 
			
			HashMap<Object, Object> proxies = org.getAllProxiesUsesTargetServer("Yesser_Server" , true);
			System.out.println(proxies);
			String[] aa = {"FC-ELK-Logger" ,  "ELK-Logger" ,  "FC-Elk-Logger" } ; 
			ProxyServices ps = (ProxyServices) destMngServer.getProxyServices(sourceOrgName) ;  
			ps.getProxiesWithoutPolices(aa, true) ; 
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
	  @Test(dataProvider = "testData" , groups = "deleteAll")
	  public void beforeClass() throws Exception 
	  {
		ApigeeConfig ac  = ApigeeConfigFactory.create("config.json" , ApigeeConfig.class) ; 
			
		destInfra = ac.getInfra("MasterWorks" , "Moj" , destInfraName) ;
		destMngServer = destInfra.getManagementServer(destInfra.getRegions().get(0).getName()) ;
		

		sourceInfra = ac.getInfra("MasterWorks" , "MOJ" , sourceInfraName) ;
		//sourceMngServer = sourceInfra.getManagementServer(region); // com.smartvalue.apigee.configuration.infra.ManagementServer(infra) ;
		//org =  sourceMngServer.getOrgByName(sourceOrgName) ;  
		//env =  org.getEnvByName(envName);

	  }

	  @BeforeTest
	  public void beforeTest() {
	  }

	  @AfterTest
	  public void afterTest() {
	  }
	  
	 

	}
