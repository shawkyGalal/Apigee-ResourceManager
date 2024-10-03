package com.smartvalue.apigee.testNG;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mashape.unirest.http.HttpResponse;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.migration.transformers.IApigeeObjectTransformer;
import com.smartvalue.apigee.migration.transformers.TransformResult;
import com.smartvalue.apigee.migration.transformers.TransformationResults;
import com.smartvalue.apigee.migration.transformers.proxy.TargetServerTransformer;
import com.smartvalue.apigee.migration.transformers.proxy.ZipFileEntryModifyTransformer;
import com.smartvalue.apigee.rest.schema.ApigeeService;
import com.smartvalue.apigee.rest.schema.proxy.ProxyServices;
import com.smartvalue.apigee.rest.schema.sharedFlow.SharedFlowServices;

public class Transform extends ApigeeTest{
	 
	 @Test
	 public void transformAllProxies() throws Exception {
		//==================Transform All Proxies ===========================
		 ProxyServices proxyServ =  (ProxyServices) sourceMngServer.getProxyServices(); 
		//proxyServ.setTranformers(buildProxyTransformers()); 
		 TransformationResults trnsformResults =  proxyServ.transformAll(DEST_FOLDER_NAME + ProxiesSubFolder,  TRANSFORM_FOLDER_NAME + ProxiesSubFolder) ;
		 int successCount = trnsformResults.filterFailed(false).size() ; 
		 int failureCount = trnsformResults.filterFailed(true).size() ;
		 System.out.println("Failed Transormation : " + failureCount );
		 System.out.println("Success Transformation : " + successCount );
		assertEquals( failureCount , 0 , "# of Errors = " + failureCount ); 
	 }
	 
	 @Test
	 public void transformOneProxy() throws Exception {
		//==================Transform All Proxies ===========================
		ProxyServices proxyServ =  (ProxyServices) sourceMngServer.getProxyServices(); 
		 
		TransformationResults trnsformResults =  proxyServ.transformProxy("C:\\temp\\Stage\\proxies\\moj-external-clients\\OldAttorney-API\\267\\OldAttorney-API.zip"
				,  "C:\\temp\\") ;
		
		assertEquals( trnsformResults.filterFailed(false).size(), 0 , "# of Errors = " + trnsformResults.filterFailed(false).size()); 
	 }
	 
	 @Test
	 public void transformAllSharedflows() throws Exception {
		//==================Transform All Sharedflows ===========================
		ApigeeService sharedflowsServ =  sourceMngServer.getSharedFlowServices(); 
		//sharedflowsServ.setTranformers(buildSharedflowsTransformers()); 
		ArrayList<TransformResult> objectErrors =  sharedflowsServ.transformAll(DEST_FOLDER_NAME + SharedflowsSubFolder,  TRANSFORM_FOLDER_NAME + SharedflowsSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size()); 
	 }
	 
	 @Test
	 public void transformAllDevelopers() throws Exception {
		//==================Transform All Sharedflows ===========================
		ApigeeService objectServices =  sourceMngServer.getDevelopersServices(); 
		//objectServices.setTranformers(buildDevelopersTransformers()); 
		ArrayList<TransformResult> objectErrors =  objectServices.transformAll(DEST_FOLDER_NAME + DevelopersSubFolder,  TRANSFORM_FOLDER_NAME + DevelopersSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size()); 
	 }
	 
	 @Test
	 public void transformAllApps() throws Exception {
		//==================Transform All Sharedflows ===========================
		ApigeeService objectServices =  sourceMngServer.getDevelopersServices(); 
		//objectServices.setTranformers(buildAppsTransformers()); 
		ArrayList<TransformResult> objectErrors =  objectServices.transformAll(DEST_FOLDER_NAME + appsSubFolder,  TRANSFORM_FOLDER_NAME + appsSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size()); 
	 } 
	 
	 @Test
	 public void transformAllProducts() throws Exception {
		//==================Transform All Products ===========================
		ApigeeService objectServices =  sourceMngServer.getProductServices(); 
		//objectServices.setTranformers(buildProductssTransformers()); 
		ArrayList<TransformResult> objectErrors =  objectServices.transformAll(DEST_FOLDER_NAME + PrtoductsSubFolder,  TRANSFORM_FOLDER_NAME + PrtoductsSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size()); 
	 } 
	 
	 @Test
	 public void transformAllTargetServers() throws Exception {
		//==================Transform All Sharedflows ===========================
		ApigeeService objectServices =  sourceMngServer.getDevelopersServices(); 
		//objectServices.setTranformers(buildTargetServersTransformers()); 
		ArrayList<TransformResult> objectErrors =  objectServices.transformAll(DEST_FOLDER_NAME + targetserversSubFolder,  TRANSFORM_FOLDER_NAME + targetserversSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size()); 
	 } 
	 
	 @Test
	 public void transformAllKvms() throws Exception {
		//==================Transform All Sharedflows ===========================
		ApigeeService objectServices =  sourceMngServer.getDevelopersServices(); 
		//objectServices.setTranformers(buildKvmsTransformers()); 
		ArrayList<TransformResult> objectErrors =  objectServices.transformAll(DEST_FOLDER_NAME + kvmsSubFolder,  TRANSFORM_FOLDER_NAME + kvmsSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size()); 
	 } 
	 
	 
	 
	 
	private ArrayList<IApigeeObjectTransformer> buildKvmsTransformers() {
		return new ArrayList<IApigeeObjectTransformer>() ;
	}

	private ArrayList<IApigeeObjectTransformer> buildTargetServersTransformers() {
		return new ArrayList<IApigeeObjectTransformer>() ;
	}

	private ArrayList<IApigeeObjectTransformer> buildProductssTransformers() {
		return new ArrayList<IApigeeObjectTransformer>() ;
	}

	private ArrayList<IApigeeObjectTransformer> buildAppsTransformers() {
		return new ArrayList<IApigeeObjectTransformer>() ;
	}

	private ArrayList<IApigeeObjectTransformer> buildDevelopersTransformers() {
		return new ArrayList<IApigeeObjectTransformer>() ;
	}

	@BeforeMethod
	 public void beforeMethod() {
	 }

	 @BeforeClass
	 public void beforeClass() throws Exception 
	 {
		super.beforeClass(); 
		initalizeSource();
	 }
	/*
	private ArrayList<ApigeeObjectTransformer> buildProxyTransformers()
	{
		//-- Build Transformers ----
		ArrayList<ApigeeObjectTransformer> proxyTransformers = new ArrayList<ApigeeObjectTransformer>(); 
		proxyTransformers.add(new TargetServerTransformer()) ; 
		String searchFor = "<Pattern/>, oldvlaue "	;
		String replaceBy = "<Pattern>xxxxxxx</Pattern> , newValue";
		ZipFileEntryModifyTransformer zfet = new ZipFileEntryModifyTransformer("apiproxy/policies/Regular-Expression-Protection.xml", searchFor, replaceBy);
		proxyTransformers.add(zfet) ; 
		//----End of building transformers 
		return proxyTransformers ; 
	}

	 private ArrayList<ApigeeObjectTransformer> buildSharedflowsTransformers() {
		//-- Build Transformers ----
		ArrayList<ApigeeObjectTransformer> sharedflowsTransformers = new ArrayList<ApigeeObjectTransformer>(); 
		sharedflowsTransformers.add(new TargetServerTransformer()) ; 
		String searchFor = "xxxxx , aaaaa";
		String replaceBy = "yyyyy , bbbbb";
		ZipFileEntryModifyTransformer zfet = new ZipFileEntryModifyTransformer("apiproxy/policies/zzzzzz.xml", searchFor, replaceBy);
		sharedflowsTransformers.add(zfet) ; 
		//----End of building transformers 
		return sharedflowsTransformers ; 
	}
	 */
	  @BeforeTest
	public void beforeTest() {
	}

	@AfterTest
	public void afterTest() {
	}

}
