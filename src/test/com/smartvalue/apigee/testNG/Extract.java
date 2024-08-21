package com.smartvalue.apigee.testNG;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.smartvalue.apigee.rest.schema.application.Application;
import com.smartvalue.apigee.rest.schema.application.ApplicationServices;

public class Extract extends ApigeeTest{
	 
	 @Test
	 public void exportAllProxies() throws Exception {
		//==================Export All Proxies ===========================
		HashMap<String, HashMap<String, Exception>> objectErrors =  sourceMngServer.getProxyServices(SOURCE_Org_NAME).exportAll(DEST_FOLDER_NAME + ProxiesSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size()); 
	 }

	 @Test
	 public void getAppScopes() throws Exception
	 {
		 ApplicationServices appServices  = (ApplicationServices) sourceMngServer.getApplicationServices(SOURCE_Org_NAME) ; 
		 Application app = appServices.getAppByName("ff7c016c-e78f-4392-bfd3-b734fc76809b") ; 
		 List<Object> scopes = app.getScopes() ;
		 List<Object> products = app.getApiProducts() ; 
		 
		 for (Object x : scopes)
		 {
			 System.out.println(x);
		 }
	 }
	 
	 @Test
	 public void exportAllSharedFlows() throws Exception {
		//==================Export All Sharedflows===========================
		HashMap<String, HashMap<String, Exception>> objectErrors =  sourceMngServer.getSharedFlowServices(SOURCE_Org_NAME).exportAll(DEST_FOLDER_NAME +SharedflowsSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }

	 @Test
	 public void exportAllProducts() throws Exception {
		//==================Export All ===========================
		HashMap<String, HashMap<String, Exception>> objectErrors = sourceMngServer.getProductServices(SOURCE_Org_NAME).exportAll(DEST_FOLDER_NAME + PrtoductsSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }
	
	 @Test
	 public void exportAllDevelopers() throws Exception {
		//==================Export All ===========================
		HashMap<String, HashMap<String, Exception>> objectErrors =  sourceMngServer.getDevelopersServices(SOURCE_Org_NAME).exportAll(DEST_FOLDER_NAME + DevelopersSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }
	 
	 @Test
	 public void exportAllTargetServers() throws Exception {
		//==================Export All ===========================
		HashMap<String, HashMap<String, Exception>> objectErrors =  sourceMngServer.getTargetServersServices(SOURCE_Org_NAME).exportAll(DEST_FOLDER_NAME + targetserversSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }
	 
	 @Test
	 public void exportAllKvms() throws Exception {
		//==================Export All ===========================
		HashMap<String, HashMap<String, Exception>> objectErrors =  sourceMngServer.getKeyValueMapServices(SOURCE_Org_NAME).exportAll(DEST_FOLDER_NAME + kvmsSubFolder) ;		
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }
	 @Test
	 public void exportApps() throws Exception {
		//==================Export All ===========================
		HashMap<String, HashMap<String, Exception>> objectErrors =  sourceMngServer.getApplicationServices(SOURCE_Org_NAME).exportAll(DEST_FOLDER_NAME + appsSubFolder) ;
		assertEquals( objectErrors.size() , 0 , "# of Errors = " + objectErrors.size());
	 }
	 
	 @BeforeMethod
	 public void beforeMethod() {
	 }

	 @BeforeClass
	 public void beforeClass() throws Exception 
	 {
		super.beforeClass(); 
		initalizeSource(); ;
	 }

	  @BeforeTest
	  public void beforeTest() {
	  }

	  @AfterTest
	  public void afterTest() {
	  }

}
