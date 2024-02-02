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
		System.setProperty("http.proxyHost", "proxy.moj.gov.local");
		System.setProperty("http.proxyPort", "8080");
		destMngServer.getInfra().buildTransformers(); 
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
	  
	  @Test
	  public void  xxx() throws UnirestException, ParseException, IOException
	  {
		 
        
		  System.setProperty("java.net.useSystemProxies", "true");
		  System.setProperty("https.proxyHost", "10.179.8.22");
	      System.setProperty("https.proxyPort", "8080");
	      System.setProperty("http.proxyHost", "10.179.8.22");
	      System.setProperty("http.proxyPort", "8080");
	      
	      System.out.println("http.proxyHost: " + System.getProperty("http.proxyHost"));
	      System.out.println("http.proxyPort: " + System.getProperty("http.proxyPort"));
	      System.out.println("https.proxyHost: " + System.getProperty("https.proxyHost"));
	      System.out.println("https.proxyPort: " + System.getProperty("https.proxyPort"));

	      CloseableHttpClient httpClient = HttpClients.createDefault();
	      HttpPost httpPost = new HttpPost("https://oauth2.googleapis.com/token");
	      httpPost.setHeader("abc", "123");
	      httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
	      httpPost.setHeader("Authorization", "Basic **********");
	      List<NameValuePair> params = new ArrayList<NameValuePair>();
	      params.add(new BasicNameValuePair("grant_type", "authorization_code"));
	      httpPost.setEntity(new UrlEncodedFormEntity(params));
	      CloseableHttpResponse responsexx = httpClient.execute(httpPost);
	      String responseBody = EntityUtils.toString(responsexx.getEntity());
	      
	      Unirest.setTimeouts(4000, 8000);
		  HttpResponse<String> response = Unirest.post("https://oauth2.googleapis.com/token")
		    .header("abc", "123")
		    .header("Content-Type", "application/x-www-form-urlencoded")
		    .header("Authorization", "Basic NDU1NjczODk3MTMxLWY2MTBjOXRhdTFpNTgydHBrOG5xMnE1Nzk0cWRiMW9pLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tOkdPQ1NQWC1jdDNaU1NJSEhqYi1pT1ZhaWFibXBYdlRXbnZ6")
		    .field("grant_type", "authorization_code")
		    .field("code", "4/0AfJohXkbYM7vQBrvWf1mNtTnZpafjuVNS8ooSc1dFI-7dEBZmQzrO_rwtF30cTt7WoVVVw")
		    .field("redirect_uri", "https://apigeeadmin.moj.gov.sa:8443/ResourceManagerWeb/loginWithGoogle/authCodeHandler.jsp")
		    .asString();
		  
		  // return response ; 

	  }

}
