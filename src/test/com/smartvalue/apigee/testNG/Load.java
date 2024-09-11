package com.smartvalue.apigee.testNG;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mashape.unirest.http.HttpResponse;

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
		ArrayList<HttpResponse<String>> objectErrors =  destMngServer.getProxyServices().importAll(DEST_FOLDER_NAME + ProxiesSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size()); 
	 }
	 
	 @Test
	 public void loadTransformedProxies() throws Exception {
			//==================Import All Proxies ===========================
			ArrayList<HttpResponse<String>> objectErrors =  destMngServer.getProxyServices().importAll(TRANSFORM_FOLDER_NAME + ProxiesSubFolder) ;
			assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size()); 
		 }
	 
	 @Test
	 public void loadAllSharedFlows() throws Exception {
		//==================Import All Sharedflows ===========================
		ArrayList<HttpResponse<String>> objectErrors =  destMngServer.getSharedFlowServices().importAll(loadingSourceFolder +SharedflowsSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }

	 @Test
	 public void loadAllProducts() throws Exception {
		//==================Import All ===========================
		 ArrayList<HttpResponse<String>> objectErrors = destMngServer.getProductServices().importAll(loadingSourceFolder + PrtoductsSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }
	
	 @Test
	 public void loadAllDevelopers() throws Exception {
		//==================Import All ===========================
		 ArrayList<HttpResponse<String>> objectErrors =  destMngServer.getDevelopersServices().importAll(loadingSourceFolder + DevelopersSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }
	 
	 @Test
	 public void loadAllTargetServers() throws Exception {
		//==================Import All ===========================
		 ArrayList<HttpResponse<String>> objectErrors =  destMngServer.getTargetServersServices().importAll(loadingSourceFolder +"\\targetservers") ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }
	 
	 @Test
	 public void loadAllKvms() throws Exception {
		//==================Import All ===========================
		ArrayList<HttpResponse<String>> objectErrors =  destMngServer.getKeyValueMapServices().importAll(loadingSourceFolder +"\\kvms") ;		
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }
	 @Test
	 public void loadApps() throws Exception {
		//==================Import All ===========================
		 ArrayList<HttpResponse<String>> objectErrors =  destMngServer.getApplicationServices().importAll(loadingSourceFolder +"\\apps") ;
		assertEquals( objectErrors.size() , 0 , "# of Errors = " + objectErrors.size());
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
