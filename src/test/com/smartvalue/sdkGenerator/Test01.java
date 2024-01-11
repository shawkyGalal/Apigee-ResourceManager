package com.smartvalue.sdkGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.openapi.SDKGeneratoer;
import com.smartvalue.zip.ZipUtility;

public class Test01 {

	 @Test
	  public void testxxx() throws UnirestException, IOException {
		 String specsUrl = "https://raw.githubusercontent.com/openapitools/openapi-generator/master/modules/openapi-generator/src/test/resources/3_0/petstore.json" ;
			String lang = "php"; 
			String outputDirectory = "C:\\Users\\ShawkyFoda\\Downloads\\MOJ_SDK_"+lang ;
			TestSDKGenerator(specsUrl , lang , outputDirectory); 
			File outFile = new File(outputDirectory) ; 
			List<File> fileList = new ArrayList<File>() ; 
			fileList.add(outFile); 
			ZipUtility.zip(fileList, outputDirectory+".zip");
			
			
		assert true;
	  }
	 
	 @BeforeMethod
	  public void beforeMethod() {
	  }

	  @BeforeClass
	  @Test(dataProvider = "testData")
	  public void beforeClass() throws Exception 
	  {
			

	  }

	  @BeforeTest
	  public void beforeTest() {
	  }

	  @AfterTest
	  public void afterTest() {
	  }

	  public static void TestSDKGenerator(String specsUrl , String lang , String outputDirectory ) {
			SDKGeneratoer sdkg = new SDKGeneratoer() ;
			sdkg.setLang(lang);
			sdkg.setOutputDir(outputDirectory ) ; 
			sdkg.setPackageName("org.moj.najiz.sdk");
			sdkg.setValidateSpecs(false); 
			sdkg.generateSDK(specsUrl);
		}
}
