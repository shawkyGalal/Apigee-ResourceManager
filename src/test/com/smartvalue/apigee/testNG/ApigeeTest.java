package com.smartvalue.apigee.testNG;

import org.testng.annotations.BeforeClass;

import com.smartvalue.apigee.configuration.ApigeeConfig;
import com.smartvalue.apigee.configuration.ApigeeConfigFactory;
import com.smartvalue.apigee.configuration.infra.Infra;
import com.smartvalue.apigee.configuration.infra.ManagementServer;

public class ApigeeTest {

	protected static final String ProxiesSubFolder = "\\proxies" ; 
	protected static final String SharedflowsSubFolder = "\\sharedflows" ;
	protected static final String PrtoductsSubFolder = "\\products" ;
	protected static final String DevelopersSubFolder = "\\developers" ;
	protected static final String appsSubFolder = "\\apps" ;
	protected static final String kvmsSubFolder = "\\kvms" ;
	protected static final String targetserversSubFolder = "\\targetservers" ; 
	
	protected static ApigeeConfig ac ; 
	protected static String sourceInfraName = "Stage";
	protected String sourceOrgName = "stg"; 
	protected Infra sourceInfra ; 
	protected ManagementServer sourceMngServer ;
	protected static final String destFolderName = "C:\\temp\\"+sourceInfraName ;
	protected static final String transformFolderName = "C:\\temp\\"+sourceInfraName+"\\Transformed" ;
	
	
	protected String destInfraName = "Gcloud(shawky.foda@gmail.com)";  
	protected String destOrgName = "apigee-moj-stage"; 
	protected Infra destInfra; 
	protected ManagementServer destMngServer ; 
	protected boolean deployUponImport = false ; 
	
	
	@BeforeClass
	public void beforeClass() throws Exception 
	{
		ac  = ApigeeConfigFactory.create("config.json" , ApigeeConfig.class) ; 
	}
	
	protected void initalizeSource() throws Exception
	{
		sourceInfra = ac.getInfra("MasterWorks" , "MOJ" , sourceInfraName) ;
		sourceMngServer = sourceInfra.getManagementServer(sourceInfra.getRegions().get(0).getName());
	}
	
	protected void initalizeDest() throws Exception
	{
		destInfra = ac.getInfra("MasterWorks" , "Moj" , destInfraName) ;
		destMngServer = destInfra.getManagementServer(destInfra.getRegions().get(0).getName()) ;
	}

}
