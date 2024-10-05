package com.smartvalue.apigee.migration.transformers.proxy;

import java.io.File;
import java.util.HashMap;

import com.smartvalue.apigee.migration.transformers.TransformResult;
import com.smartvalue.apigee.migration.transformers.tools.ZipXmlModifier;
import com.smartvalue.apigee.proxyBundle.ProxyBundleParser;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Flow;
import com.smartvalue.apigee.rest.schema.proxyRevision.OasOperation;

public class FixOasInconsistancyTransformer extends ProxyTransformer {

	
	@Override
	public TransformResult trasform(String bundleZipFilePath , String newBundlePath)  {
		TransformResult result = new TransformResult() ; 
        try {
        	ProxyBundleParser proxyBundle = new ProxyBundleParser(bundleZipFilePath) ;
        	HashMap<OasOperation , Flow >  oas = proxyBundle.checkOpenApiConsistancy(true, false);
        	String estimatedOasPolicyFileName = "apiproxy/policies/"+ proxyBundle.getEstimatedOasPolicyName() + ".xml";
    		String oasJsonStr = proxyBundle.getOasJsonStr(); 
    		String modifiedOasElement = "<Payload contentType=\"application/json\" variablePrefix=\"@\" variableSuffix=\"#\">\n"+oasJsonStr+"\n </Payload>" ; 
        	        	
        	ZipXmlModifier.modifyXmlElement(bundleZipFilePath , estimatedOasPolicyFileName ,ProxyBundleParser.PAYLOAD_XPTH , modifiedOasElement , newBundlePath  );
        	result.withFailed(false)
  		  	.withSource(bundleZipFilePath)
  		  	.withDestination(newBundlePath)	
  		  	.withTransformerClass(this.getClass()); 

        } catch (Exception e) {
        	result.withError(e.getMessage())
        	.withFailed(true)
  		  	.withSource(bundleZipFilePath)
  		  	.withDestination(newBundlePath+File.pathSeparator + new File (bundleZipFilePath).getName())	
  		  	.withTransformerClass(this.getClass()); 
        	//System.out.println("Error Transforming Proxy Bundle : " +  bundleZipFileName );
        	e.printStackTrace();
        }
        return result ; 
	}

	@Override
	public boolean filter(String bundleZipFileNam) {
		return true;
	}

}
