package com.smartvalue.apigee.testNG;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mashape.unirest.http.HttpResponse;

public class Delete extends ApigeeTest{
	 
	 @Test
	 public void deleteAllProxies() throws Exception {
		//==================Delete All Proxies ===========================
		ArrayList<HttpResponse<String>> objectErrors =  destMngServer.getProxyServices().deleteAll() ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size()); 
	 }
	 
	 @Test
	 public void deleteAllSharedFlows() throws Exception {
		//==================Delete All Sharedflows ===========================
		ArrayList<HttpResponse<String>> objectErrors =  destMngServer.getSharedFlowServices().deleteAll() ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }

	 @Test
	 public void deleteAllProducts() throws Exception {
		//==================Delete All ===========================
		 ArrayList<HttpResponse<String>> objectErrors = destMngServer.getProductServices().deleteAll() ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }
	
	 @Test
	 public void deleteAllDevelopers() throws Exception {
		//==================Delete All ===========================
		 ArrayList<HttpResponse<String>> objectErrors =  destMngServer.getDevelopersServices().deleteAll() ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }
	 
	 @Test
	 public void deleteAllTargetServers() throws Exception {
		//==================Delete All ===========================
		 ArrayList<HttpResponse<String>> objectErrors =  destMngServer.getTargetServersServices().deleteAll() ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }
	 
	 @Test
	 public void deleteAllKvms() throws Exception {
		//==================Delete All ===========================
		ArrayList<HttpResponse<String>> objectErrors =  destMngServer.getKeyValueMapServices().deleteAll() ;		
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }
	 @Test
	 public void deleteAllApps() throws Exception {
		//==================Delete All ===========================
		 ArrayList<HttpResponse<String>> objectErrors =  destMngServer.getApplicationServices().deleteAll() ;
		assertEquals( objectErrors.size() , 0 , "# of Errors = " + objectErrors.size());
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
