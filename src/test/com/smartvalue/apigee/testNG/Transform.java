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
import com.smartvalue.apigee.rest.schema.ApigeeService;
import com.smartvalue.apigee.rest.schema.proxy.transformers.ApigeeObjectTransformer;
import com.smartvalue.apigee.rest.schema.proxy.transformers.TargetServerTransformer;
import com.smartvalue.apigee.rest.schema.proxy.transformers.TransformResult;
import com.smartvalue.apigee.rest.schema.proxy.transformers.ZipFileEntryModifyTransformer;
import com.smartvalue.apigee.rest.schema.sharedFlow.SharedFlowServices;

public class Transform extends ApigeeTest{
	 
	 @Test
	 public void transformAllProxies() throws Exception {
		//==================Transform All Proxies ===========================
		ApigeeService proxyServ =  sourceMngServer.getProxyServices(sourceOrgName); 
		proxyServ.setTranformers(buildProxyTransformers()); 
		ArrayList<TransformResult> objectErrors =  proxyServ.transformAll(destFolderName + ProxiesSubFolder,  transformFolderName + ProxiesSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size()); 
	 }
	 
	 @Test
	 public void transformAllSharedflows() throws Exception {
		//==================Transform All Sharedflows ===========================
		ApigeeService sharedflowsServ =  sourceMngServer.getSharedFlowServices(sourceOrgName); 
		sharedflowsServ.setTranformers(buildSharedflowsTransformers()); 
		ArrayList<TransformResult> objectErrors =  sharedflowsServ.transformAll(destFolderName + SharedflowsSubFolder,  transformFolderName + SharedflowsSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size()); 
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
	 
	private ArrayList<ApigeeObjectTransformer> buildProxyTransformers()
	{
		//-- Build Transformers ----
		ArrayList<ApigeeObjectTransformer> proxyTransformers = new ArrayList<ApigeeObjectTransformer>(); 
		proxyTransformers.add(new TargetServerTransformer()) ; 
		List<String> searchFor = Arrays.asList("<Pattern/>"	);
		List<String> replaceBy = Arrays.asList("<Pattern>xxxxxxx</Pattern>");
		ZipFileEntryModifyTransformer zfet = new ZipFileEntryModifyTransformer("apiproxy/policies/Regular-Expression-Protection.xml", searchFor, replaceBy);
		proxyTransformers.add(zfet) ; 
		//----End of building transformers 
		return proxyTransformers ; 
	}

	 private ArrayList<ApigeeObjectTransformer> buildSharedflowsTransformers() {
		//-- Build Transformers ----
		ArrayList<ApigeeObjectTransformer> sharedflowsTransformers = new ArrayList<ApigeeObjectTransformer>(); 
		sharedflowsTransformers.add(new TargetServerTransformer()) ; 
		List<String> searchFor = Arrays.asList("xxxxx");
		List<String> replaceBy = Arrays.asList("yyyyy");
		ZipFileEntryModifyTransformer zfet = new ZipFileEntryModifyTransformer("apiproxy/policies/zzzzzz.xml", searchFor, replaceBy);
		sharedflowsTransformers.add(zfet) ; 
		//----End of building transformers 
		return sharedflowsTransformers ; 
	}
	 
	  @BeforeTest
	public void beforeTest() {
	}

	@AfterTest
	public void afterTest() {
	}

}
