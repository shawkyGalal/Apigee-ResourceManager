package com.smartvalue.apigee.testNG;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.smartvalue.apigee.configuration.AppConfig;
import com.smartvalue.apigee.migration.export.ExportResults;
import com.smartvalue.apigee.rest.schema.application.Application;
import com.smartvalue.apigee.rest.schema.application.ApplicationServices;

public class Extract extends ApigeeTest{
	 
	 @Test
	 public void exportAllProxies() throws Exception {
		//==================Export All Proxies ===========================
		 ExportResults expotrtresults =  sourceMngServer.getProxyServices().exportAll(DEST_FOLDER_NAME + AppConfig.ProxiesSubFolder) ;
		 int failureCount = expotrtresults.filterFailed(true).size() ;  
		 assertEquals( failureCount , 0 , "# of Errors = " + failureCount); 
	 }

	 @Test
	 public void getAppScopes() throws Exception
	 {
		 ApplicationServices appServices  = (ApplicationServices) sourceMngServer.getApplicationServices() ; 
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
		ExportResults expotrtresults =  sourceMngServer.getSharedFlowServices().exportAll(DEST_FOLDER_NAME +AppConfig.SharedflowsSubFolder) ;
		int failureCount = expotrtresults.filterFailed(true).size() ;  
		assertEquals( failureCount , 0 , "# of Errors = " + failureCount);
	 }

	 @Test
	 public void exportAllProducts() throws Exception {
		//==================Export All ===========================
		 ExportResults expotrtresults = sourceMngServer.getProductServices().exportAll(DEST_FOLDER_NAME + AppConfig.PrtoductsSubFolder) ;
		 int failureCount = expotrtresults.filterFailed(true).size() ;  
		 assertEquals( failureCount , 0 , "# of Errors = " + failureCount);
	 }
	
	 @Test
	 public void exportAllDevelopers() throws Exception {
		//==================Export All ===========================
		ExportResults expotrtresults =  sourceMngServer.getDevelopersServices().exportAll(DEST_FOLDER_NAME + AppConfig.DevelopersSubFolder) ;
		 int failureCount = expotrtresults.filterFailed(true).size() ;  
		 assertEquals( failureCount , 0 , "# of Errors = " + failureCount);
	 }
	 
	 @Test
	 public void exportAllTargetServers() throws Exception {
		//==================Export All ===========================
		ExportResults expotrtresults =  sourceMngServer.getTargetServersServices().exportAll(DEST_FOLDER_NAME + AppConfig.targetserversSubFolder) ;
		int failureCount = expotrtresults.filterFailed(true).size() ;  
		 assertEquals( failureCount , 0 , "# of Errors = " + failureCount);
	 }
	 
	 @Test
	 public void exportAllKvms() throws Exception {
		//==================Export All ===========================
		 ExportResults expotrtresults =  sourceMngServer.getKeyValueMapServices().exportAll(DEST_FOLDER_NAME + AppConfig.kvmsSubFolder) ;		
		 int failureCount = expotrtresults.filterFailed(true).size() ;  
		 assertEquals( failureCount , 0 , "# of Errors = " + failureCount);
	 }
	 @Test
	 public void exportApps() throws Exception {
		//==================Export All ===========================
		 ExportResults expotrtresults =  sourceMngServer.getApplicationServices().exportAll(DEST_FOLDER_NAME + AppConfig.appsSubFolder) ;
		 int failureCount = expotrtresults.filterFailed(true).size() ;  
		 assertEquals( failureCount , 0 , "# of Errors = " + failureCount);
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
