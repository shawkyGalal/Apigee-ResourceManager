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
		HashMap<String, HttpResponse<String>> allResponses =  destMngServer.getProxyServices().importAll(DEST_FOLDER_NAME + ProxiesSubFolder) ;
		LoadResults lrs = new LoadResults(allResponses) ;
		lrs.filterSuccess() ;
		int errorCount = lrs.getUnMatchedResponses().size() ; 
		assertEquals( errorCount , 0 , "# of Errors = " + errorCount); 
	 }
	 
	 @Test
	 public void loadTransformedProxies() throws Exception {
			//==================Import All Proxies ===========================
			HashMap<String, HttpResponse<String>> allResponses =  destMngServer.getProxyServices().importAll(TRANSFORM_FOLDER_NAME + ProxiesSubFolder) ;
			LoadResults lrs = new LoadResults(allResponses) ;
			lrs.filterSuccess() ;
			int errorCount = lrs.getUnMatchedResponses().size() ; 
			assertEquals( errorCount , 0 , "# of Errors = " + errorCount); 
		 }
	 
	 @Test
	 public void loadAllSharedFlows() throws Exception {
		//==================Import All Sharedflows ===========================
		HashMap<String, HttpResponse<String>> allResponses =  destMngServer.getSharedFlowServices().importAll(loadingSourceFolder +SharedflowsSubFolder) ;
		LoadResults lrs = new LoadResults(allResponses) ;
		lrs.filterSuccess() ;
		int errorCount = lrs.getUnMatchedResponses().size() ; 
		assertEquals( errorCount , 0 , "# of Errors = " + errorCount);
	 }

	 @Test
	 public void loadAllProducts() throws Exception {
		//==================Import All ===========================
		 HashMap<String, HttpResponse<String>> allResponses = destMngServer.getProductServices().importAll(loadingSourceFolder + PrtoductsSubFolder) ;
		 LoadResults lrs = new LoadResults(allResponses) ;
		 lrs.filterSuccess() ;
		 int errorCount = lrs.getUnMatchedResponses().size() ; 
		 assertEquals( errorCount , 0 , "# of Errors = " + errorCount);
	 }
	
	 @Test
	 public void loadAllDevelopers() throws Exception {
		//==================Import All ===========================
		 HashMap<String, HttpResponse<String>> allResponses =  destMngServer.getDevelopersServices().importAll(loadingSourceFolder + DevelopersSubFolder) ;
		 LoadResults lrs = new LoadResults(allResponses) ;
		 lrs.filterSuccess() ;
		 int errorCount = lrs.getUnMatchedResponses().size() ; 
		 assertEquals( errorCount , 0 , "# of Errors = " + errorCount);
	 }
	 
	 @Test
	 public void loadAllTargetServers() throws Exception {
		//==================Import All ===========================
		 HashMap<String, HttpResponse<String>> allResponses =  destMngServer.getTargetServersServices().importAll(loadingSourceFolder +"\\targetservers") ;
		 LoadResults lrs = new LoadResults(allResponses) ;
		 lrs.filterSuccess() ;
		 int errorCount = lrs.getUnMatchedResponses().size() ; 
		 assertEquals( errorCount , 0 , "# of Errors = " + errorCount);
	 }
	 
	 @Test
	 public void loadAllKvms() throws Exception {
		//==================Import All ===========================
		HashMap<String, HttpResponse<String>> allResponses =  destMngServer.getKeyValueMapServices().importAll(loadingSourceFolder +"\\kvms") ;		
		 LoadResults lrs = new LoadResults(allResponses) ;
		 lrs.filterSuccess() ;
		 int errorCount = lrs.getUnMatchedResponses().size() ; 
		 assertEquals( errorCount , 0 , "# of Errors = " + errorCount);
	 }
	 @Test
	 public void loadApps() throws Exception {
		//==================Import All ===========================
		 HashMap<String, HttpResponse<String>> allResponses =  destMngServer.getApplicationServices().importAll(loadingSourceFolder +"\\apps") ;
		 LoadResults lrs = new LoadResults(allResponses) ;
		 lrs.filterSuccess() ;
		 int errorCount = lrs.getUnMatchedResponses().size() ; 
		 assertEquals( errorCount , 0 , "# of Errors = " + errorCount);
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
