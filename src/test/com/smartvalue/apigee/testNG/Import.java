package com.smartvalue.apigee.testNG;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.Properties;

public class Import extends ApigeeTest{
	 
	 @Test
	 public void importAllProxies() throws Exception {
		//==================Import All Proxies ===========================
		//System.setProperty("http.proxyHost", "proxy.moj.gov.local");
		//System.setProperty("http.proxyPort", "8080");
		//destMngServer.getInfra().buildTransformers(); 
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
