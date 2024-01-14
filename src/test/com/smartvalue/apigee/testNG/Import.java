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

public class Import extends ApigeeTest{
	 
	 @Test
	 public void importAllProxies() throws Exception {
		//==================Import All Proxies ===========================
		ArrayList<HttpResponse<String>> objectErrors =  destMngServer.getProxyServices(sourceOrgName).importAll(transformFolderName + ProxiesSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size()); 
	 }
	 
	 @Test
	 public void importAllSharedFlows() throws Exception {
		//==================Import All Sharedflows ===========================
		ArrayList<HttpResponse<String>> objectErrors =  destMngServer.getSharedFlowServices(sourceOrgName).importAll(transformFolderName +SharedflowsSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }

	 @Test
	 public void importAllProducts() throws Exception {
		//==================Import All ===========================
		 ArrayList<HttpResponse<String>> objectErrors = destMngServer.getProductServices(sourceOrgName).importAll(transformFolderName + PrtoductsSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }
	
	 @Test
	 public void ImportAllDevelopers() throws Exception {
		//==================Import All ===========================
		 ArrayList<HttpResponse<String>> objectErrors =  destMngServer.getDevelopersServices(sourceOrgName).importAll(transformFolderName + DevelopersSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }
	 
	 @Test
	 public void ImportAllTargetServers() throws Exception {
		//==================Import All ===========================
		 ArrayList<HttpResponse<String>> objectErrors =  destMngServer.getTargetServersServices(sourceOrgName).importAll(transformFolderName +"\\targetservers") ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }
	 
	 @Test
	 public void ImportAllKvms() throws Exception {
		//==================Import All ===========================
		ArrayList<HttpResponse<String>> objectErrors =  destMngServer.getKeyValueMapServices(sourceOrgName).importAll(transformFolderName +"\\kvms") ;		
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }
	 @Test
	 public void ImportApps() throws Exception {
		//==================Import All ===========================
		 ArrayList<HttpResponse<String>> objectErrors =  destMngServer.getApplicationServices(sourceOrgName).importAll(transformFolderName +"\\apps") ;
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
