package com.smartvalue.apigee.testNG;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.smartvalue.apigee.configuration.AppConfig;
import com.smartvalue.apigee.migration.ProcessResult;
import com.smartvalue.apigee.migration.ProcessResults;
import com.smartvalue.apigee.migration.deploy.DeployResult;
import com.smartvalue.apigee.migration.deploy.DeployResults;
import com.smartvalue.apigee.migration.export.ExportResult;
import com.smartvalue.apigee.migration.export.ExportResults;
import com.smartvalue.apigee.migration.load.LoadResult;
import com.smartvalue.apigee.migration.transformers.TransformResult;
import com.smartvalue.apigee.migration.transformers.TransformationResults;
import com.smartvalue.apigee.rest.schema.DeploymentsStatus;
import com.smartvalue.apigee.rest.schema.application.Application;
import com.smartvalue.apigee.rest.schema.application.ApplicationServices;
import com.smartvalue.apigee.rest.schema.proxy.Proxy;
import com.smartvalue.apigee.rest.schema.proxy.ProxyServices ;
import com.smartvalue.apigee.rest.schema.proxyUploadResponse.ProxyUploadResponse;
import com.smartvalue.apigee.rest.schema.sharedFlow.SharedFlowServices; 

public class Extract extends ApigeeTest{
	 
	 @Test
	 public void exportAllProxies() throws Exception {
		//==================Export All Proxies ===========================
		 
		 ExportResults expotrtresults =  ((ProxyServices)sourceMngServer.getProxyServices()).exportAll(DEST_FOLDER_NAME + "\\"+AppConfig.ProxiesSubFolder , DEST_FOLDER_NAME+"/xxxx.ser") ;
		 int failureCount = expotrtresults.filterFailed(true).size() ;  
		 assertEquals( failureCount , 0 , "# of Errors = " + failureCount); 
	 }
	 
	 @Test
	 public void exportThenTransformThenUploadProxy() throws Exception {
		//==================Export One Proxy ===========================
		
		 String proxyName = "SMS-Governance" ;
		 ProxyServices proxyServ =  (ProxyServices) sourceMngServer.getProxyServices();
		 Proxy proxy = proxyServ.getOrganization().getProxy(proxyName);
		 String exportDest = DEST_FOLDER_NAME + "\\"+ AppConfig.ProxiesSubFolder ; 
		 ExportResults ers =  proxy.exportAllDeployedRevisions(exportDest);
		 
		 //==================Transform the Exported Proxy ===========================
	
		 TransformationResults trnsformResults = new TransformationResults() ; 
		 for (ProcessResult er : ers )
		 {
			String transformSource = er.getDestination() ;
			String dest = transformSource.replaceAll(AppConfig.ProxiesSubFolder, AppConfig.ProxiesSubFolder +File.separatorChar+File.separatorChar+"Transformed") ;  
			trnsformResults.addAll( proxyServ.transformProxy(transformSource +proxyName+".zip" , dest  ) ) ;
		 }
		 System.out.println(trnsformResults);
		 
		//==================Load the Transformed Proxy ===========================
			
		 ProcessResults uploadResults = new ProcessResults() ;
		 LoadResult  loadResult ; 
		 
		 for (TransformResult transformResult : trnsformResults )
		 {
			 String source = transformResult.getDestination() +"\\"+ proxyName+".zip" ;
			 loadResult = proxyServ.importObject(source, proxyName) ; 
			 uploadResults.add(loadResult) ;
			 if ( ! loadResult.isFailed())
			 {
				 Gson json = new Gson(); 
				 ProxyUploadResponse pur = json.fromJson(loadResult.getHttpResponse().getBody(), ProxyUploadResponse.class);
				 int newRevesion = Integer.parseInt(pur.getRevision());
				 String envName = loadResult.getDestination(); 
				 DeployResult deployResult = proxyServ.deployRevision(proxyName, loadResult.extractEnvNameFromsource(exportDest) , newRevesion) ;
				 uploadResults.add(deployResult) ; 
			 }
			 
		 }
		 System.out.println(uploadResults);
		 
	 }
	 
	 
	 @Test
	 public void exportAllProxiesWithDeploymentStatus() throws Exception {
		//==================Export All Proxies ===========================
		 String deplyStatusFileName = DEST_FOLDER_NAME + "\\PROXIES_DEPLOYMENTS_STATUS.ser" ; 
		 ProxyServices ps = (ProxyServices) sourceMngServer.getProxyServices() ; 
		 ExportResults expotrtresults = ps.exportAll(DEST_FOLDER_NAME + "\\"+ AppConfig.ProxiesSubFolder , deplyStatusFileName) ;
		 DeployResults xx = ps.rollBackToLastSerializedDeployStatus(deplyStatusFileName); 
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
		String deplyStatusFileName = DEST_FOLDER_NAME + "\\SHAREDFLOWS_DEPLOYMENTS_STATUS.ser" ; 
		SharedFlowServices sharedFlowServices = (SharedFlowServices) sourceMngServer.getSharedFlowServices() ; 
		ExportResults expotrtresults =  sharedFlowServices.exportAll(DEST_FOLDER_NAME + "\\" +AppConfig.SharedflowsSubFolder , deplyStatusFileName ) ;
		DeploymentsStatus xx = sharedFlowServices.deSerializeDeployStatus(deplyStatusFileName);
		int failureCount = expotrtresults.filterFailed(true).size() ;  
		assertEquals( failureCount , 0 , "# of Errors = " + failureCount);
	 }

	 @Test
	 public void exportAllProducts() throws Exception {
		//==================Export All ===========================
		 ExportResults expotrtresults = sourceMngServer.getProductServices().exportAll(DEST_FOLDER_NAME + "\\" + AppConfig.PrtoductsSubFolder) ;
		 int failureCount = expotrtresults.filterFailed(true).size() ;  
		 assertEquals( failureCount , 0 , "# of Errors = " + failureCount);
	 }
	
	 @Test
	 public void exportAllDevelopers() throws Exception {
		//==================Export All ===========================
		ExportResults expotrtresults =  sourceMngServer.getDevelopersServices().exportAll(DEST_FOLDER_NAME + "\\" + AppConfig.DevelopersSubFolder) ;
		 int failureCount = expotrtresults.filterFailed(true).size() ;  
		 assertEquals( failureCount , 0 , "# of Errors = " + failureCount);
	 }
	 
	 @Test
	 public void exportAllTargetServers() throws Exception {
		//==================Export All ===========================
		ExportResults expotrtresults =  sourceMngServer.getTargetServersServices().exportAll(DEST_FOLDER_NAME + "\\" + AppConfig.targetserversSubFolder) ;
		int failureCount = expotrtresults.filterFailed(true).size() ;  
		 assertEquals( failureCount , 0 , "# of Errors = " + failureCount);
	 }
	 
	 @Test
	 public void exportAllKvms() throws Exception {
		//==================Export All ===========================
		 ExportResults expotrtresults =  sourceMngServer.getKeyValueMapServices().exportAll(DEST_FOLDER_NAME + "\\" + AppConfig.kvmsSubFolder) ;		
		 int failureCount = expotrtresults.filterFailed(true).size() ;  
		 assertEquals( failureCount , 0 , "# of Errors = " + failureCount);
	 }
	 @Test
	 public void exportApps() throws Exception {
		//==================Export All ===========================
		 ExportResults expotrtresults =  sourceMngServer.getApplicationServices().exportAll(DEST_FOLDER_NAME + "\\"+ AppConfig.appsSubFolder) ;
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
