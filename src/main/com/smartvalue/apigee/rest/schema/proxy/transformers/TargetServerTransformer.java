package com.smartvalue.apigee.rest.schema.proxy.transformers;

public class TargetServerTransformer implements ApigeeObjectTransformer {

	@Override
	public  TransformResult  trasform(String  bundleZipFileName , String outputZipFile) {

		TransformResult result = new TransformResult() ; 
		String fileName = "apiproxy/targets/default.xml";
        String xpath = "/TargetEndpoint/HTTPTargetConnection";
        
        //String outputZipFile = bundleZipFileName.substring(0, bundleZipFileName.indexOf("."))+"_TSTransormed.zip" ; //"G:\\My Drive\\MasterWorks\\Apigee\\Customers\\MOJ\\10.162.3.3.etc.apigee\\apigee-migrate-tool\\data_history\\MOJ\\Prod\\moj-prod\\iam-protected\\2023-11-19-03-33\\proxies\\AccessCaseFile_updated.zip";
        String newXmlString = "<HTTPTargetConnection><SSLInfo>\r\n" + 
        		"            <Enabled>true</Enabled>\r\n" + 
        		"        </SSLInfo>\r\n" + 
        		"        <LoadBalancer>\r\n" + 
        		"            <Server name=\"xxxxxxx\"/>\r\n" + 
        		"        </LoadBalancer>\r\n" + 
        		"        <Path>yyyyy</Path></HTTPTargetConnection>";
        
		try {
            ZipXmlModifier.modifyXmlElement(bundleZipFileName, fileName, xpath, newXmlString, outputZipFile);
            System.out.println("New Updated Proxy Pundle " + outputZipFile + "Is Created"); 
             
        } catch (Exception e) {
        	result.withError(e.getMessage())
        		  .withStatus("Failed")
        		  .withSource(bundleZipFileName)
        		  .withDestination(outputZipFile); 
            e.printStackTrace();
        }
		return result ; 
	}

	@Override
	public boolean filter(String bundleZipFileNam) {
		// TODO Auto-generated method stub
		return true;
	}
	

}
