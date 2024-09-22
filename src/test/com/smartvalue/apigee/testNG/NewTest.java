package com.smartvalue.apigee.testNG;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.AppConfig;
import com.smartvalue.apigee.configuration.AppConfigFactory;
import com.smartvalue.apigee.configuration.infra.Infra;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.migration.transformers.IApigeeObjectTransformer;
import com.smartvalue.apigee.migration.transformers.TransformResult;
import com.smartvalue.apigee.migration.transformers.proxy.ZipFileEntryModifyTransformer;
import com.smartvalue.apigee.proxyBundle.BundleProxyEndPoint;
import com.smartvalue.apigee.proxyBundle.Policy;
import com.smartvalue.apigee.proxyBundle.ProxyBundleParser;
import com.smartvalue.apigee.resourceManager.Renderer;
import com.smartvalue.apigee.rest.schema.ApigeeService;
import com.smartvalue.apigee.rest.schema.TargetServer;
import com.smartvalue.apigee.rest.schema.application.Application;
import com.smartvalue.apigee.rest.schema.application.ApplicationServices;
import com.smartvalue.apigee.rest.schema.application.auto.ApiProduct;
import com.smartvalue.apigee.rest.schema.developer.DeveloperServices;
import com.smartvalue.apigee.rest.schema.environment.Environment;
import com.smartvalue.apigee.rest.schema.organization.Organization;
import com.smartvalue.apigee.rest.schema.product.ProductsServices;
import com.smartvalue.apigee.rest.schema.proxy.Proxy;
import com.smartvalue.apigee.rest.schema.proxy.ProxyServices;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.ProxyEndpoint;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Child;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Flow;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Request;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Response;
import com.smartvalue.apigee.rest.schema.proxyRevision.ProxyRevision;
import com.smartvalue.apigee.rest.schema.server.MPServer;
import com.smartvalue.apigee.rest.schema.sharedFlow.SharedFlowServices;
import com.smartvalue.moj.clients.environments.JsonParser;
import com.smartvalue.openapi.SDKGeneratoer;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

public class NewTest extends ApigeeTest {
	
	boolean deployUponImport = false ; 
	
	
	AppConfig ac ; 
	String envName ; 
	String proxyName ;
	String region ; 
	
	Organization org  ; 
	Environment env ; 
	
	String transformFolderName = "C:\\temp\\Transform\\Stage" ; 
	
	@Test
	public void getOasFromOasFlow() throws ParserConfigurationException, SAXException, UnirestException, IOException, XPathExpressionException
	{
		ProxyBundleParser smsGovernanceProxyBundle =  new ProxyBundleParser("C:\\temp\\Stage\\proxies\\moj-internal-clients\\SMS-Governance\\147\\SMS-Governance.zip") ;
 		Paths paths =  smsGovernanceProxyBundle.getOasJson(); 
	}
	
	@Test
	public void parseProxyBundle() throws ParserConfigurationException, SAXException, UnirestException, IOException
	{
		ProxyBundleParser smsGovernanceProxyBundle =  new ProxyBundleParser("C:\\temp\\Stage\\proxies\\moj-internal-clients\\SMS-Governance\\147\\SMS-Governance.zip") ;
		BundleProxyEndPoint pep = smsGovernanceProxyBundle.getProxies().get("default");
		List<Flow> allFlows = pep.getFlows() ; 
		for (Flow  flow : allFlows )
		{
			String flowName = flow.getName(); 
			Request req = flow.getRequest();
			Response res = flow.getResponse(); 
			String id = flow.getUniqueIdentifier() ; 
			ProxyEndpoint xx =  flow.getParentProxyEndPoint() ; 
		}
		Flow f = pep.getFlowByName("GetOAS") ; 
	}
	@Test
	  public void checkOpenApiConsistancy() throws Exception {
		//==================Export All ===========================
		JsonParser apigeeConfigParser = new JsonParser( ) ;
		AppConfig ac = apigeeConfigParser.getObject("config.json" , AppConfig.class) ; 
		Infra mojStageinfra = ac.getInfra("MasterWorks" , "MOJ" , "Stage") ;
		ManagementServer sourceMngServer = mojStageinfra.getManagementServer(mojStageinfra.getRegions().get(0).getName()) ;
		
		sourceMngServer.setOrgName("stg") ;
		ProxyRevision pr =    sourceMngServer.getOrgByName("stg").getProxy("SMS-Governance").getRevision("147") ;
		String serverUrl = "https://api-test.moj.gov.local/" ; 
					
		HashMap<Flow , Operation > flowMatchedOper = pr.checkFlowsConsistancy(serverUrl) ;
		HashMap<Operation , Flow>  operMatchedFlow = pr.checkOpenApiConsistancy(serverUrl , false); 
		 
	  }
	 
	@Test
	  public void testExportAll() throws Exception {
		//==================Export All ===========================
		JsonParser apigeeConfigParser = new JsonParser( ) ;
		AppConfig ac = apigeeConfigParser.getObject("config.json" , AppConfig.class) ; 
		Infra mojStageinfra = ac.getInfra("MasterWorks" , "MOJ" , "Stage") ;
		ManagementServer sourceMngServer = mojStageinfra.getManagementServer(mojStageinfra.getRegions().get(0).getName()) ;
		//String destFolderName = "C:\\temp\\Stage" ;
		//HashMap<String, HashMap<String, Exception>> targetServerFaults =  sourceMngServer.getTargetServersServices(sourceOrgName).exportAll(destFolderName +"\\targetservers") ;
		//HashMap<String, HashMap<String, Exception>> productsFaults = sourceMngServer.getProductServices(sourceOrgName).exportAll(destFolderName +"\\products") ; 
		//HashMap<String, HashMap<String, Exception>> appsFaults = sourceMngServer.getApplicationServices(sourceOrgName).exportAll(destFolderName +"\\apps") ;
		//HashMap<String, HashMap<String, Exception>> proxiesFaults =  sourceMngServer.getProxyServices(sourceOrgName).exportAll(destFolderName +"\\proxies") ;
		//HashMap<String, HashMap<String, Exception>> sharedflowsFaults =  sourceMngServer.getSharedFlowServices().exportAll(destFolderName +"\\sharedflows") ;
		//HashMap<String, HashMap<String, Exception>> devsFaults =  sourceMngServer.getDevelopersServices(sourceOrgName).exportAll(destFolderName +"\\developers") ;
		//HashMap<String, HashMap<String, Exception>> kvmsFaults =  sourceMngServer.getKeyValueMapServices(sourceOrgName).exportAll(destFolderName +"\\kvms") ;
		sourceMngServer.setOrgName("stg") ;
		sourceMngServer.getOrgByName("stg").getProxy("SMS-Governance").generateOpenApi("147") ;  
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
		
		SharedFlowServices sfs = (SharedFlowServices) sourceMngServer.getSharedFlowServices();
		//sfs.setTranformers(transformers); 
		ArrayList<TransformResult> sharedflowsFaults =  sfs.transformAll(sourceFolderName +"\\sharedflows" , transformFolderName +"\\sharedflows") ;
		
		ApigeeService proxyServ =  sourceMngServer.getProxyServices(); 
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
		//String destOrgName = "moj-prod-apigee" ; 
		//ArrayList<HttpResponse<String>> targetServerFaults =  sourceMngServer.getTargetServersServices(sourceOrgName).importAll(sourceFolderName +"\\targetservers") ;
		//ArrayList<HttpResponse<String>> kvmsFaults =  sourceMngServer.getKeyValueMapServices(sourceOrgName).importAll(sourceFolderName +"\\kvms") ;
		ArrayList<HttpResponse<String>> sharedflowsFaults =  ((SharedFlowServices) destMngServer.getSharedFlowServices()).withDeployUponUpload(deployUponImport).importAll(sourceFolderName +"\\sharedflows") ;
		ArrayList<HttpResponse<String>> proxiesFaults =  ((ProxyServices) destMngServer.getProxyServices()).withDeployUponUpload(deployUponImport).importAll(sourceFolderName +"\\proxies") ;
		//ArrayList<HttpResponse<String>> productsFaults = sourceMngServer.getProductServices(sourceOrgName).importAll(sourceFolderName +"\\products") ; 
		//ArrayList<HttpResponse<String>> devsFaults =  sourceMngServer.getDevelopersServices(sourceOrgName).importAll(sourceFolderName +"\\developers") ;
		//ArrayList<HttpResponse<String>> appsFaults = sourceMngServer.getApplicationServices(sourceOrgName).importAll(sourceFolderName +"\\apps") ;

		
	  }
	 
	 @Test(groups = "deleteAll")
	  public void testDeleteAllProxies() throws Exception {
		//==================Import All ===========================
		destMngServer = destInfra.getManagementServer(destInfra.getRegions().get(0).getName()) ; 
		ArrayList<HttpResponse<String>> proxiesFaults =  destMngServer.getProxyServices().deleteAll() ;
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
		ArrayList<HttpResponse<String>> sharedflowsFaults =  destMngServer.getSharedFlowServices().deleteAll() ;
	 }
	 
	 @Test(groups = "deleteAll")
	  public void testDeleteAllTargetservers() throws Exception {
		ArrayList<HttpResponse<String>> sharedflowsFaults =  destMngServer.getTargetServersServices().deleteAll() ;
	 }

	 @Test(groups = "deleteAll")
	 public void testDeleteAllKvms() throws Exception {
		 ArrayList<HttpResponse<String>> kvmsFaults =  destMngServer.getKeyValueMapServices().deleteAll() ;	 
	 }

	 @Test(groups = "deleteAll")
	 public void testDeleteAllProducts() throws Exception {
		 ArrayList<HttpResponse<String>> productsFaults = destMngServer.getProductServices().deleteAll() ; 	 
	 }
	 
	 @Test(groups = "deleteAll")
	 public void testDeleteAllDevelopers() throws Exception {
			ArrayList<HttpResponse<String>> devsFaults =  destMngServer.getDevelopersServices().deleteAll() ;
	 }
	 
	 @Test(groups = "deleteAll")
	 public void testDeleteAllApplications() throws Exception {
			ArrayList<HttpResponse<String>> appsFaults = destMngServer.getApplicationServices().deleteAll() ;
	 }
	 

	 
	 @Test
	  public void testProductsWithoutProxies() throws Exception {
		initalizeSource(); 
		ProductsServices   productServices = (ProductsServices) sourceMngServer.getProductServices() ; 
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
	  public void testApproveAppProduct() throws Exception {
	  
		  initalizeSource(); 
		  DeveloperServices ds = (DeveloperServices) sourceMngServer.getDevelopersServices(); 
		  Application app = ds.getDeveloperById("sfoda@moj.gov.sa").getAppByname("App01") ;
		  String key = "MtQPBZK57lXLJlB93gHYdA6f6o9bNzp8" ; 
		  String productName = "SDK_Generator" ; 
		  app.approveApiProduct(key, productName); ;
		  app.revokeApiProduct(key, productName); ;
		  ApiProduct xxy =  app.getCredentialByKey(key).getApiProductByname(productName);
		  
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
			ProxyServices ps = (ProxyServices) destMngServer.getProxyServices() ;  
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

	  /*
	  @BeforeClass
	  @Test(dataProvider = "testData" , groups = "deleteAll")
	  public void beforeClass() throws Exception 
	  {
		AppConfig ac  = AppConfigFactory.create("config.json" , AppConfig.class) ; 
			
		destInfra = ac.getInfra("MasterWorks" , "Moj" , destInfraName) ;
		destMngServer = destInfra.getManagementServer(destInfra.getRegions().get(0).getName()) ;
		

		sourceInfra = ac.getInfra("MasterWorks" , "MOJ" , sourceInfraName) ;
		//sourceMngServer = sourceInfra.getManagementServer(region); // com.smartvalue.apigee.configuration.infra.ManagementServer(infra) ;
		//org =  sourceMngServer.getOrgByName(sourceOrgName) ;  
		//env =  org.getEnvByName(envName);

	  }
	  */

	  @BeforeTest
	  public void beforeTest() {
	  }

	  @AfterTest
	  public void afterTest() {
	  }
	  
	 

	}
