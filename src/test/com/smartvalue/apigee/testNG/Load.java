package com.smartvalue.apigee.testNG;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mashape.unirest.http.HttpResponse;
import com.smartvalue.apigee.configuration.AppConfig;
import com.smartvalue.apigee.migration.load.LoadResults;
import com.smartvalue.apigee.migration.load.LoadResults;

public class Load extends ApigeeTest{
	 private String loadingSourceFolder ;
	 private static final boolean LOAD_TRANSFORMED = true ; 
	 
	 @BeforeClass
	 public void beforeClass() throws Exception 
	 {
		super.beforeClass(); 
		initalizeDest();
		this.loadingSourceFolder = (LOAD_TRANSFORMED)? TRANSFORM_FOLDER_NAME  : DEST_FOLDER_NAME ;  
	 }
	 
	 @Test
	 public void loadOriginalProxies() throws Exception {
		//==================Import All Proxies ===========================
		LoadResults allResponses =  destMngServer.getProxyServices().importAll(DEST_FOLDER_NAME + AppConfig.ProxiesSubFolder) ;
		int failedSize = allResponses.filterFailed(true).size() ; 
		assertEquals( failedSize , 0 , "# of Errors = " + failedSize); 
	 }
	 
	 @Test
	 public void loadTransformedProxies() throws Exception {
			//==================Import All Proxies ===========================
		 	LoadResults allResponses =  destMngServer.getProxyServices().importAll(TRANSFORM_FOLDER_NAME + AppConfig.ProxiesSubFolder) ;
		 	int failedSize = allResponses.filterFailed(true).size() ; 
			assertEquals( failedSize , 0 , "# of Errors = " + failedSize);
		 }
	 
	 @Test
	 public void loadAllSharedFlows() throws Exception {
		//==================Import All Sharedflows ===========================
		LoadResults  allResponses =  destMngServer.getSharedFlowServices().importAll(loadingSourceFolder +AppConfig.SharedflowsSubFolder) ;
	 	int failedSize = allResponses.filterFailed(true).size() ; 
		assertEquals( failedSize , 0 , "# of Errors = " + failedSize);
	}

	 @Test
	 public void loadAllProducts() throws Exception {
		//==================Import All ===========================
		 LoadResults allResponses = destMngServer.getProductServices().importAll(loadingSourceFolder + AppConfig.PrtoductsSubFolder) ;
		 int failedSize = allResponses.filterFailed(true).size() ; 
		 assertEquals( failedSize , 0 , "# of Errors = " + failedSize);
	 }
	
	 @Test
	 public void loadAllDevelopers() throws Exception {
		//==================Import All ===========================
		 LoadResults allResponses =  destMngServer.getDevelopersServices().importAll(loadingSourceFolder + AppConfig.DevelopersSubFolder) ;
		 int failedSize = allResponses.filterFailed(true).size() ; 
		 assertEquals( failedSize , 0 , "# of Errors = " + failedSize);
	 }
	 
	 @Test
	 public void loadAllTargetServers() throws Exception {
		//==================Import All ===========================
		 LoadResults  allResponses =  destMngServer.getTargetServersServices().importAll(loadingSourceFolder +"\\targetservers") ;
		 int failedSize = allResponses.filterFailed(true).size() ; 
		 assertEquals( failedSize , 0 , "# of Errors = " + failedSize);
	 }
	 
	 @Test
	 public void loadAllKvms() throws Exception {
		//==================Import All ===========================
		 LoadResults  allResponses =  destMngServer.getKeyValueMapServices().importAll(loadingSourceFolder +"\\kvms") ;		
		 int failedSize = allResponses.filterFailed(true).size() ; 
		 assertEquals( failedSize , 0 , "# of Errors = " + failedSize);
	 }
	 @Test
	 public void loadApps() throws Exception {
		//==================Import All ===========================
		 LoadResults  allResponses =  destMngServer.getApplicationServices().importAll(loadingSourceFolder +"\\apps") ;
		 int failedSize = allResponses.filterFailed(true).size() ; 
		 assertEquals( failedSize , 0 , "# of Errors = " + failedSize);
	 }
	 
	 @BeforeMethod
	 public void beforeMethod() {
	 }

	 

	  @BeforeTest
	  public void beforeTest() {
	  }

	  @AfterTest
	  public void afterTest() {
	  }

}
