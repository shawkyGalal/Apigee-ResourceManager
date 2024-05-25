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
	 @Test
	 public void loadAllProxies() throws Exception {
		//==================Import All Proxies ===========================
		//System.setProperty("http.proxyHost", "proxy.moj.gov.local");
		//System.setProperty("http.proxyPort", "8080");
		//destMngServer.getInfra().buildTransformers(); 
		ArrayList<HttpResponse<String>> objectErrors =  destMngServer.getProxyServices(destOrgName).importAll(loadingSourceFolder + ProxiesSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size()); 
	 }
	 
	 @Test
	 public void loadAllSharedFlows() throws Exception {
		//==================Import All Sharedflows ===========================
		ArrayList<HttpResponse<String>> objectErrors =  destMngServer.getSharedFlowServices(destOrgName).importAll(loadingSourceFolder +SharedflowsSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }

	 @Test
	 public void loadAllProducts() throws Exception {
		//==================Import All ===========================
		 ArrayList<HttpResponse<String>> objectErrors = destMngServer.getProductServices(destOrgName).importAll(loadingSourceFolder + PrtoductsSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }
	
	 @Test
	 public void loadAllDevelopers() throws Exception {
		//==================Import All ===========================
		 ArrayList<HttpResponse<String>> objectErrors =  destMngServer.getDevelopersServices(destOrgName).importAll(loadingSourceFolder + DevelopersSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }
	 
	 @Test
	 public void loadAllTargetServers() throws Exception {
		//==================Import All ===========================
		 ArrayList<HttpResponse<String>> objectErrors =  destMngServer.getTargetServersServices(destOrgName).importAll(loadingSourceFolder +"\\targetservers") ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }
	 
	 @Test
	 public void loadAllKvms() throws Exception {
		//==================Import All ===========================
		ArrayList<HttpResponse<String>> objectErrors =  destMngServer.getKeyValueMapServices(destOrgName).importAll(loadingSourceFolder +"\\kvms") ;		
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }
	 @Test
	 public void loadApps() throws Exception {
		//==================Import All ===========================
		 ArrayList<HttpResponse<String>> objectErrors =  destMngServer.getApplicationServices(destOrgName).importAll(loadingSourceFolder +"\\apps") ;
		assertEquals( objectErrors.size() , 0 , "# of Errors = " + objectErrors.size());
	 }
	 
	 @BeforeMethod
	 public void beforeMethod() {
	 }

	 @BeforeClass
	 public void beforeClass() throws Exception 
	 {
		super.beforeClass(); 
		initalizeDest();
		this.loadingSourceFolder = this.destFolderName  ; 
	 }

	  @BeforeTest
	  public void beforeTest() {
	  }

	  @AfterTest
	  public void afterTest() {
	  }

}
