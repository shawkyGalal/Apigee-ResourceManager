package com.smartvalue.apigee.testNG;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.smartvalue.apigee.configuration.AppConfig;
import com.smartvalue.apigee.migration.ProcessResults;
import com.smartvalue.apigee.migration.deploy.DeployResults;
import com.smartvalue.apigee.migration.export.ExportResults;
import com.smartvalue.apigee.resourceManager.helpers.Helper;
import com.smartvalue.apigee.rest.schema.ApigeeService;
import com.smartvalue.apigee.rest.schema.BundleObjectService;
import com.smartvalue.apigee.rest.schema.DeploymentsStatus;
import com.smartvalue.apigee.rest.schema.RollBackable;
import com.smartvalue.apigee.rest.schema.application.Application;
import com.smartvalue.apigee.rest.schema.application.ApplicationServices;
import com.smartvalue.apigee.rest.schema.proxy.ProxyServices ;
import com.smartvalue.apigee.rest.schema.sharedFlow.SharedFlowServices; 

public class Extract extends ApigeeTest{
	 
	 private UUID exportUUID;

	@Test
	 public void exportAllProxies() throws Exception {
		//==================Export All Proxies ===========================
		 
		 BundleObjectService bundleObjectService = (BundleObjectService)this.sourceMngServer.getServiceByType("apis") ;
		 ExportResults expotrtresults =  bundleObjectService.exportAll(DEST_FOLDER_NAME + File.separator+AppConfig.ProxiesSubFolder ) ;
		 String userEmail ="sfoda@moj.gov.sa";
		 String serializeToFile = sourceMngServer.getSerlizeProcessResultFileName( userEmail ) ; 
		 Helper.serialize(serializeToFile, expotrtresults )  ; 
		 int failureCount = expotrtresults.filterFailed(true).size() ;  
		 assertEquals( failureCount , 0 , "# of Errors = " + failureCount); 
	 }
	 
	 @Test
	 public void performETL() throws Exception {
		//==================Export One Proxy ===========================
		 String proxyName = "SMS-Governance" ;
		 ApigeeService bundleObjectService = this.sourceMngServer.getServiceByType("apis") ;
		 String userEmail ="sfoda@moj.gov.sa"; 
		 ProcessResults results = bundleObjectService.performETL(proxyName , UUID.randomUUID()) ;
		 UUID exportUUId = results.getUuid();
		 this.setExportUUID(exportUUId); 
		 String serializeToFile = sourceMngServer.getSerlizeProcessResultFileName( userEmail ) ; 
		 Helper.serialize(serializeToFile, results )  ; 
	 }

	 private void setExportUUID(UUID exportUUId) {
		this.exportUUID= exportUUId ;
		
	}

	@Test
	 public void rollBackETL() throws Exception {
		//==================Export One Proxy ===========================
		 String proxyName = "SMS-Governance" ;
		 RollBackable bundleObjectService = (RollBackable) this.sourceMngServer.getServiceByType("apis") ;
		 String serlizeDeplyStateFileName = sourceMngServer.getSerlizeDeplyStateFileName("sfoda@moj.gov.sa") ;
		 bundleObjectService.rollBackObjectToSerializedDeployStatus(proxyName ,  this.exportUUID ) ;
	 }

	 
	 @Test
	 public void exportAllProxiesWithDeploymentStatus() throws Exception {
		//==================Export All Proxies ===========================
		 String deplyStatusFileName = DEST_FOLDER_NAME + "\\PROXIES_DEPLOYMENTS_STATUS.ser" ; 
		 ProxyServices ps = (ProxyServices) sourceMngServer.getProxyServices() ; 
		 ExportResults expotrtresults = ps.exportAll(DEST_FOLDER_NAME + File.separator+ AppConfig.ProxiesSubFolder ) ;
		 DeployResults xx = ps.rollBackAllToSerializedDeployStatus(expotrtresults.getUuid() ); 
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
		//String deplyStatusFileName = DEST_FOLDER_NAME + "\\SHAREDFLOWS_DEPLOYMENTS_STATUS.ser" ; 
		BundleObjectService bundleObjectService = (BundleObjectService) this.sourceMngServer.getServiceByType("sharedflows") ; 
		ExportResults expotrtresults =  bundleObjectService.exportAll(DEST_FOLDER_NAME + File.separator +AppConfig.SharedflowsSubFolder ) ;
		String serlizeDeplyStateFileName = sourceMngServer.getSerlizeDeplyStateFileName(expotrtresults.getUuid().toString()) ;
		DeploymentsStatus xx = bundleObjectService.deSerializeDeployStatus( serlizeDeplyStateFileName );
		int failureCount = expotrtresults.filterFailed(true).size() ;  
		assertEquals( failureCount , 0 , "# of Errors = " + failureCount);
	 }

	 @Test
	 public void exportAllProducts() throws Exception {
		//==================Export All ===========================
		 ExportResults expotrtresults = sourceMngServer.getProductServices().exportAll(DEST_FOLDER_NAME + File.separator + AppConfig.PrtoductsSubFolder , null) ;
		 int failureCount = expotrtresults.filterFailed(true).size() ;  
		 assertEquals( failureCount , 0 , "# of Errors = " + failureCount);
	 }
	
	 @Test
	 public void exportAllDevelopers() throws Exception {
		//==================Export All ===========================
		ExportResults expotrtresults =  sourceMngServer.getDevelopersServices().exportAll(DEST_FOLDER_NAME + File.separator + AppConfig.DevelopersSubFolder , null ) ;
		 int failureCount = expotrtresults.filterFailed(true).size() ;  
		 assertEquals( failureCount , 0 , "# of Errors = " + failureCount);
	 }
	 
	 @Test
	 public void exportAllTargetServers() throws Exception {
		//==================Export All ===========================
		ExportResults expotrtresults =  sourceMngServer.getTargetServersServices().exportAll(DEST_FOLDER_NAME + File.separator + AppConfig.targetserversSubFolder , null ) ;
		int failureCount = expotrtresults.filterFailed(true).size() ;  
		 assertEquals( failureCount , 0 , "# of Errors = " + failureCount);
	 }
	 
	 @Test
	 public void exportAllKvms() throws Exception {
		//==================Export All ===========================
		 ExportResults expotrtresults =  sourceMngServer.getKeyValueMapServices().exportAll(DEST_FOLDER_NAME + File.separator + AppConfig.kvmsSubFolder, null ) ;		
		 int failureCount = expotrtresults.filterFailed(true).size() ;  
		 assertEquals( failureCount , 0 , "# of Errors = " + failureCount);
	 }
	 @Test
	 public void exportApps() throws Exception {
		//==================Export All ===========================
		 ExportResults expotrtresults =  sourceMngServer.getApplicationServices().exportAll(DEST_FOLDER_NAME + File.separator+ AppConfig.appsSubFolder , null ) ;
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
