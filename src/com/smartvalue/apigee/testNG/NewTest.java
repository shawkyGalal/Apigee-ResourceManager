package com.smartvalue.apigee.testNG;

import java.io.IOException;
import java.lang.reflect.Type;
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
import com.smartvalue.apigee.configuration.infra.Infra;
import com.smartvalue.apigee.resourceManager.ManagementServer;
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
	ManagementServer ms ; 
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
			
			ArrayList<Object> proxiesNotDeployed = org.getUndeployedProxies() ; 
			System.out.println(proxiesNotDeployed.toString());
	 		
			
			List<MPServer> envMpServers = env.getMessageProcesors(region) ;
			Renderer.arrayListToHtmlTable(envMpServers) ; 

			MPServer mps = ((MPServer)envMpServers.get(0)) ;
			
			boolean healthy = mps.healthCheck() ;
			ArrayList<String> result = mps.removeFromEnvironmnt(org , env ) ; 
			Renderer.arrayListToHtmlTable(result) ;
			
			result = mps.addToEnvironmnt(org , env ) ; 
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
		  	Type apigeeConfigType = (Type) ApigeeConfig.class ;
	  		JsonParser apigeeConfigParser = new JsonParser( apigeeConfigType ) ;
			ac = (ApigeeConfig) apigeeConfigParser.getObject("config.json") ;
		  
		  infra = ac.getInfra("SmartValue" , "Demo" , "Prod") ;
		  orgName =  "smart-value"  ; // "stg" ; 
		  envName = "prod"  ; // "iam-protected"
		  proxyName = "DZIT" ;
		  region = "dc-1" ; 

		  ms = new ManagementServer(infra) ; 
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
