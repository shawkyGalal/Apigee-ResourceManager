package com.smartvalue.apigee.testNG;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.ApigeeConfig;
import com.smartvalue.apigee.configuration.ApigeeConfigFactory;
import com.smartvalue.apigee.configuration.infra.Infra;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.resourceManager.Renderer;
import com.smartvalue.apigee.rest.schema.TargetServer;
import com.smartvalue.apigee.rest.schema.environment.Environment;
import com.smartvalue.apigee.rest.schema.organization.Organization;
import com.smartvalue.apigee.rest.schema.product.ProductsServices;
import com.smartvalue.apigee.rest.schema.proxy.Proxy;
import com.smartvalue.apigee.rest.schema.server.MPServer;
import com.smartvalue.moj.clients.environments.JsonParser;

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
	
	 @Test
	  public void testExportAll() throws Exception {
		//==================Export All ===========================
		JsonParser apigeeConfigParser = new JsonParser( ) ;
		ApigeeConfig ac = apigeeConfigParser.getObject("config_Example.json" , ApigeeConfig.class) ; 
		Infra mojStageinfra = ac.getInfra("YourCompanyName" , "Customer01" , "dev_onpremise") ;
		ManagementServer sourceMngServer = mojStageinfra.getManagementServer(mojStageinfra.getRegions().get(0).getName()) ;
		String exportFolderName = "C:\\temp\\Stage" ;
		String sourceOrgName = "stg" ; 
		HashMap<String, HashMap<String, Exception>> targetServerFaults =  sourceMngServer.getTargetServersServices(sourceOrgName).exportAll(exportFolderName +"targetservers") ;
		HashMap<String, HashMap<String, Exception>> productsFaults = sourceMngServer.getProductServices(sourceOrgName).exportAll(exportFolderName +"\\products") ; 
		HashMap<String, HashMap<String, Exception>> appsFaults = sourceMngServer.getApplicationServices(sourceOrgName).exportAll(exportFolderName +"\\apps") ;
		HashMap<String, HashMap<String, Exception>> proxiesFaults =  sourceMngServer.getProxyServices(sourceOrgName).exportAll(exportFolderName +"\\proxies") ;
		HashMap<String, HashMap<String, Exception>> sharedflowsFaults =  sourceMngServer.getSharedFlowServices(sourceOrgName).exportAll(exportFolderName +"\\sharedflows") ;
		HashMap<String, HashMap<String, Exception>> devsFaults =  sourceMngServer.getDevelopersServices(sourceOrgName).exportAll(exportFolderName +"\\developers") ;
		HashMap<String, HashMap<String, Exception>> kvmsFaults =  sourceMngServer.getKeyValueMapServices(sourceOrgName).exportAll(exportFolderName +"\\kvms") ;
		
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
