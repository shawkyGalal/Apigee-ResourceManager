package com.smartvalue.apigee.testNG;

import org.testng.annotations.BeforeClass;

import com.smartvalue.apigee.configuration.AppConfig;
import com.smartvalue.apigee.configuration.AppConfigFactory;
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
	
	protected static AppConfig ac ; 
	protected static final String SOURCE_INFRA_NAME = "Stage";
	private static final String SOURCE_Org_NAME = "stg"; 
	protected Infra sourceInfra ; 
	protected ManagementServer sourceMngServer ;
	protected static final String DEST_FOLDER_NAME 		= "C:\\temp\\Apigee\\"+SOURCE_INFRA_NAME +"\\"+SOURCE_Org_NAME;
	protected static final String TRANSFORM_FOLDER_NAME = DEST_FOLDER_NAME +"\\Transformed" ;
	
	
	protected static final String DEST_INFRA_NAME = "VMWare" ; //"Gcloud(shawky.foda@gmail.com)";  
	private static final String DEST_ORG_NAME = "VALIDATE" ;// "moj-prod-apigee"; 
	protected Infra destInfra; 
	protected ManagementServer destMngServer ; 
	protected boolean deployUponImport = false ; 
	
	
	@BeforeClass
	public void beforeClass() throws Exception 
	{
		ac  = AppConfigFactory.create("config.json" , AppConfig.class) ; 
	}
	
	protected void initalizeSource() throws Exception
	{
		sourceInfra = ac.getInfra("MasterWorks" , "MOJ" , SOURCE_INFRA_NAME) ;
		sourceMngServer = sourceInfra.getManagementServer(sourceInfra.getRegions().get(0).getName());
		if ( sourceInfra.isGooglecloud() != null && sourceInfra.isGooglecloud())
		{
			sourceMngServer.setOrgName(sourceInfra.getGoogleServiceAccount().getProjectId());
		}
		sourceMngServer.setOrgName(SOURCE_Org_NAME);
	}
	
	protected void initalizeDest() throws Exception
	{
		destInfra = ac.getInfra("SmartValue" , "Demo" , "VMWare") ;
		destMngServer = destInfra.getManagementServer(destInfra.getRegions().get(0).getName()) ;
		if ( destInfra.isGooglecloud() != null && destInfra.isGooglecloud() )
		{
			destMngServer.setOrgName(destInfra.getGoogleServiceAccount().getProjectId());
		}
		else destMngServer.setOrgName(DEST_ORG_NAME);
	}

}
