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
	  public void testOtherServices() throws UnirestException, IOException, InterruptedException {
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
		  JsonParser apigeeConfigParser = new JsonParser( ) ;
			ApigeeConfig ac  = ApigeeConfigFactory.create("config.json" , ApigeeConfig.class) ; 

			infra = ac.getInfra("MasterWorks" , "MOJ" , "Stage") ;
			region = "dc-1" ; 
			orgName = "stg" ; 
			envName = "iam-protected" ; 
			proxyName = "oidc-core" ;
	  
		  ms = infra.getManagementServer(region); // com.smartvalue.apigee.configuration.infra.ManagementServer(infra) ; 
		  org = (Organization) ms.getOrgByName(orgName) ;  
		  env = (Environment) org.getEnvByName(envName);

	  }

	  @BeforeTest
	  public void beforeTest() {
	  }

	  @AfterTest
	  public void afterTest() {
	  }

	}
