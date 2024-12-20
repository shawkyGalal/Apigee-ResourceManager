package com.smartvalue.apigee.testNG;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mashape.unirest.http.HttpResponse;
import com.smartvalue.apigee.rest.schema.proxy.DeleteResults;

public class Delete extends ApigeeTest{
	 
	 @Test
	 public void deleteAllProxies() throws Exception {
		//==================Delete All Proxies ===========================
		DeleteResults objectErrors =  destMngServer.getProxyServices().deleteAll() ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size()); 
	 }
	 
	 @Test
	 public void deleteAllSharedFlows() throws Exception {
		//==================Delete All Sharedflows ===========================
		DeleteResults objectErrors =  destMngServer.getSharedFlowServices().deleteAll() ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }

	 @Test
	 public void deleteAllProducts() throws Exception {
		//==================Delete All ===========================
		 DeleteResults objectErrors = destMngServer.getProductServices().deleteAll() ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }
	
	 @Test
	 public void deleteAllDevelopers() throws Exception {
		//==================Delete All ===========================
		 DeleteResults deleteResults =  destMngServer.getDevelopersServices().deleteAll() ;
		 int failureCount = deleteResults.filterFailed(true).size() ;
		assertEquals( failureCount , 0 , "# of Errors = " + failureCount);
	 }
	 
	 @Test
	 public void deleteAllTargetServers() throws Exception {
		//==================Delete All ===========================
		 DeleteResults deleteResults =  destMngServer.getTargetServersServices().deleteAll() ;
		 int failureCount = deleteResults.filterFailed(true).size() ;
		 assertEquals( failureCount , 0 , "# of Errors = " + failureCount);
	 }
	 
	 @Test
	 public void deleteAllKvms() throws Exception {
		//==================Delete All ===========================
		 DeleteResults deleteResults =  destMngServer.getKeyValueMapServices().deleteAll() ;		
		 int failureCount = deleteResults.filterFailed(true).size() ;
		assertEquals( failureCount , 0 , "# of Errors = " + failureCount);
	 }
	 @Test
	 public void deleteAllApps() throws Exception {
		//==================Delete All ===========================
		 DeleteResults deleteResults =  destMngServer.getApplicationServices().deleteAll() ;
		 int failureCount = deleteResults.filterFailed(true).size() ;
		assertEquals( failureCount , 0 , "# of Errors = " + failureCount);
	 }
	
	 
	 @BeforeMethod
	 public void beforeMethod() {
	 }

	 @BeforeClass
	 public void beforeClass() throws Exception 
	 {
		super.beforeClass(); 
		initalizeDest(); ;
	 }

	  @BeforeTest
	  public void beforeTest() {
	  }

	  @AfterTest
	  public void afterTest() {
	  }

}
