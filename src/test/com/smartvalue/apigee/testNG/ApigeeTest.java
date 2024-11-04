package com.smartvalue.apigee.testNG;

import org.testng.annotations.BeforeClass;

import com.smartvalue.apigee.configuration.AppConfig;
import com.smartvalue.apigee.configuration.AppConfigFactory;
import com.smartvalue.apigee.configuration.infra.Infra;
import com.smartvalue.apigee.configuration.infra.ManagementServer;

public class ApigeeTest {

	
	protected static AppConfig ac ; 
	protected static final String SOURCE_INFRA_NAME = "Stage";
	public static final String SOURCE_Org_NAME = "stg"; 
	private static final String User_Email= "sfoda@moj.gov.sa" ; 
	protected Infra sourceInfra ; 
	protected ManagementServer sourceMngServer ;
	protected static final String DEST_FOLDER_NAME 		= AppConfig.getMigrationBasePath() +"\\" + User_Email +"\\"+SOURCE_INFRA_NAME +"\\"+SOURCE_Org_NAME;
	protected static final String TRANSFORM_FOLDER_NAME = DEST_FOLDER_NAME +"\\Transformed" ;
	
	
	protected static final String DEST_INFRA_NAME = "Gcloud(shawky.foda@gmail.com)"; // "VMWare"  
	private static final String DEST_ORG_NAME = "moj-prod-apigee" ; //"VALIDATE" ; 
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
		destInfra = ac.getInfra("MasterWorks" , "MOJ" , DEST_INFRA_NAME) ;
		destMngServer = destInfra.getManagementServer(destInfra.getRegions().get(0).getName()) ;
		if ( destInfra.isGooglecloud() != null && destInfra.isGooglecloud() )
		{
			destMngServer.setOrgName(destInfra.getGoogleServiceAccount().getProjectId());
		}
		else destMngServer.setOrgName(DEST_ORG_NAME);
	}

}
