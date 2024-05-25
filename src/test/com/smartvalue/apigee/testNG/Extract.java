package com.smartvalue.apigee.testNG;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Extract extends ApigeeTest{
	 
	 @Test
	 public void exportAllProxies() throws Exception {
		//==================Export All Proxies ===========================
		HashMap<String, HashMap<String, Exception>> objectErrors =  sourceMngServer.getProxyServices(sourceOrgName).exportAll(destFolderName + ProxiesSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size()); 
	 }
	 
	 @Test
	 public void exportAllSharedFlows() throws Exception {
		//==================Export All Sharedflows===========================
		HashMap<String, HashMap<String, Exception>> objectErrors =  sourceMngServer.getSharedFlowServices(sourceOrgName).exportAll(destFolderName +SharedflowsSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }

	 @Test
	 public void exportAllProducts() throws Exception {
		//==================Export All ===========================
		HashMap<String, HashMap<String, Exception>> objectErrors = sourceMngServer.getProductServices(sourceOrgName).exportAll(destFolderName + PrtoductsSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }
	
	 @Test
	 public void exportAllDevelopers() throws Exception {
		//==================Export All ===========================
		HashMap<String, HashMap<String, Exception>> objectErrors =  sourceMngServer.getDevelopersServices(sourceOrgName).exportAll(destFolderName + DevelopersSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }
	 
	 @Test
	 public void exportAllTargetServers() throws Exception {
		//==================Export All ===========================
		HashMap<String, HashMap<String, Exception>> objectErrors =  sourceMngServer.getTargetServersServices(sourceOrgName).exportAll(destFolderName + targetserversSubFolder) ;
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }
	 
	 @Test
	 public void exportAllKvms() throws Exception {
		//==================Export All ===========================
		HashMap<String, HashMap<String, Exception>> objectErrors =  sourceMngServer.getKeyValueMapServices(sourceOrgName).exportAll(destFolderName + kvmsSubFolder) ;		
		assertEquals( objectErrors.size(), 0 , "# of Errors = " + objectErrors.size());
	 }
	 @Test
	 public void exportApps() throws Exception {
		//==================Export All ===========================
		HashMap<String, HashMap<String, Exception>> objectErrors =  sourceMngServer.getApplicationServices(sourceOrgName).exportAll(destFolderName + appsSubFolder) ;
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
