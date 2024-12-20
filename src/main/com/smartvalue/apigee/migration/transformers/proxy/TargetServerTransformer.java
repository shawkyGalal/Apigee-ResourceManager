package com.smartvalue.apigee.migration.transformers.proxy;

import java.io.File;

import com.smartvalue.apigee.migration.transformers.TransformResult;
import com.smartvalue.apigee.migration.transformers.tools.ZipXmlModifier;

public class TargetServerTransformer extends ProxyTransformer {
/**
 * The Main Objective of this tranformer is to change HTTPTargetConnection from using a hardcoded backend server URL , to an Environment Configured TargetServer 
 */
	
	private String targetServerName  ;
	String targetServerPath = "pathyyyy"; 
	private String fileName ;
    private String xpath ;
	
	@Override
	public  TransformResult  trasform(String  bundleZipFileName , String outputZipFile) {

		//1- Creare a new Target Server specific for this Proxy Pundle
		File bundleZipFile = new File(bundleZipFileName) ; 
		int dotIndex = bundleZipFile.getName().indexOf(".") ; 
		targetServerName = bundleZipFile.getName().substring(0, dotIndex)+"_TS" ;
		targetServerPath = "pathyyyy"; 
		
		//2- Use the 
		
		TransformResult result = new TransformResult() ; 
		        
        //String outputZipFile = bundleZipFileName.substring(0, bundleZipFileName.indexOf("."))+"_TSTransormed.zip" ; //"G:\\My Drive\\MasterWorks\\Apigee\\Customers\\MOJ\\10.162.3.3.etc.apigee\\apigee-migrate-tool\\data_history\\MOJ\\Prod\\moj-prod\\iam-protected\\2023-11-19-03-33\\proxies\\AccessCaseFile_updated.zip";
        String newXmlString = "<HTTPTargetConnection><SSLInfo>\r\n" + 
        		"            <Enabled>true</Enabled>\r\n" + 
        		"        </SSLInfo>\r\n" + 
        		"        <LoadBalancer>\r\n" + 
        		"            <Server name=\""+targetServerName+"\"/>\r\n" + 
        		"        </LoadBalancer>\r\n" + 
        		"        <Path>"+targetServerPath+"</Path></HTTPTargetConnection>";
        
		try {
            ZipXmlModifier.modifyXmlElement(bundleZipFileName, fileName, xpath, newXmlString, outputZipFile);
            System.out.println("New Updated Proxy Pundle " + outputZipFile + "Is Created"); 
             
        } catch (Exception e) {
        	result.withSource(bundleZipFileName)
  		  	.withDestination(bundleZipFileName)
  		  	.withError(e.getMessage())
  		  	.withFailed(true);
        	result.setTransformerClass(this.getClass());
        }
		return result ; 
	}

	@Override
	public boolean filter(String bundleZipFileNam) {
		return true;
	}
	

}
