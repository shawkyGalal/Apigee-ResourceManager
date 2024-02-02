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
import com.smartvalue.apigee.rest.schema.ApigeeObjectTransformer;
import com.smartvalue.apigee.rest.schema.ApigeeService;
import com.smartvalue.apigee.rest.schema.proxy.transformers.TargetServerTransformer;
import com.smartvalue.apigee.rest.schema.proxy.transformers.TransformResult;
import com.smartvalue.apigee.rest.schema.proxy.transformers.ZipFileEntryModifyTransformer;
import com.smartvalue.apigee.rest.schema.sharedFlow.SharedFlowServices;

public class Transform extends ApigeeTest{
	 
	 @Test
	 public void transformAllProxies() throws Exception {
		//==================Transform All Proxies ===========================
		ApigeeService proxyServ =  sourceMngServer.getProxyServices(sourceOrgName); 
		//proxyServ.setTranformers(buildProxyTransformers()); 
		ArrayList<TransformResult> objectErrors =  proxyServ.transformAll(destFolderName + ProxiesSubFolder,  transformFolderName + ProxiesSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size()); 
	 }
	 
	 @Test
	 public void transformAllSharedflows() throws Exception {
		//==================Transform All Sharedflows ===========================
		ApigeeService sharedflowsServ =  sourceMngServer.getSharedFlowServices(sourceOrgName); 
		//sharedflowsServ.setTranformers(buildSharedflowsTransformers()); 
		ArrayList<TransformResult> objectErrors =  sharedflowsServ.transformAll(destFolderName + SharedflowsSubFolder,  transformFolderName + SharedflowsSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size()); 
	 }
	 
	 @Test
	 public void transformAllDevelopers() throws Exception {
		//==================Transform All Sharedflows ===========================
		ApigeeService objectServices =  sourceMngServer.getDevelopersServices(sourceOrgName); 
		//objectServices.setTranformers(buildDevelopersTransformers()); 
		ArrayList<TransformResult> objectErrors =  objectServices.transformAll(destFolderName + DevelopersSubFolder,  transformFolderName + DevelopersSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size()); 
	 }
	 
	 @Test
	 public void transformAllApps() throws Exception {
		//==================Transform All Sharedflows ===========================
		ApigeeService objectServices =  sourceMngServer.getDevelopersServices(sourceOrgName); 
		//objectServices.setTranformers(buildAppsTransformers()); 
		ArrayList<TransformResult> objectErrors =  objectServices.transformAll(destFolderName + appsSubFolder,  transformFolderName + appsSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size()); 
	 } 
	 
	 @Test
	 public void transformAllProducts() throws Exception {
		//==================Transform All Sharedflows ===========================
		ApigeeService objectServices =  sourceMngServer.getDevelopersServices(sourceOrgName); 
		//objectServices.setTranformers(buildProductssTransformers()); 
		ArrayList<TransformResult> objectErrors =  objectServices.transformAll(destFolderName + PrtoductsSubFolder,  transformFolderName + PrtoductsSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size()); 
	 } 
	 
	 @Test
	 public void transformAllTargetServers() throws Exception {
		//==================Transform All Sharedflows ===========================
		ApigeeService objectServices =  sourceMngServer.getDevelopersServices(sourceOrgName); 
		//objectServices.setTranformers(buildTargetServersTransformers()); 
		ArrayList<TransformResult> objectErrors =  objectServices.transformAll(destFolderName + targetserversSubFolder,  transformFolderName + targetserversSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size()); 
	 } 
	 
	 @Test
	 public void transformAllKvms() throws Exception {
		//==================Transform All Sharedflows ===========================
		ApigeeService objectServices =  sourceMngServer.getDevelopersServices(sourceOrgName); 
		//objectServices.setTranformers(buildKvmsTransformers()); 
		ArrayList<TransformResult> objectErrors =  objectServices.transformAll(destFolderName + kvmsSubFolder,  transformFolderName + kvmsSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size()); 
	 } 
	 
	 
	 
	 
	private ArrayList<ApigeeObjectTransformer> buildKvmsTransformers() {
		return new ArrayList<ApigeeObjectTransformer>() ;
	}

	private ArrayList<ApigeeObjectTransformer> buildTargetServersTransformers() {
		return new ArrayList<ApigeeObjectTransformer>() ;
	}

	private ArrayList<ApigeeObjectTransformer> buildProductssTransformers() {
		return new ArrayList<ApigeeObjectTransformer>() ;
	}

	private ArrayList<ApigeeObjectTransformer> buildAppsTransformers() {
		return new ArrayList<ApigeeObjectTransformer>() ;
	}

	private ArrayList<ApigeeObjectTransformer> buildDevelopersTransformers() {
		return new ArrayList<ApigeeObjectTransformer>() ;
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
