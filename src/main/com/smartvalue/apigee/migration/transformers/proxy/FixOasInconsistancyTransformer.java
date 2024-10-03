package com.smartvalue.apigee.migration.transformers.proxy;

import java.util.HashMap;

import com.smartvalue.apigee.migration.transformers.TransformResult;
import com.smartvalue.apigee.migration.transformers.tools.ZipXmlModifier;
import com.smartvalue.apigee.proxyBundle.ProxyBundleParser;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Flow;
import com.smartvalue.apigee.rest.schema.proxyRevision.OasOperation;

public class FixOasInconsistancyTransformer extends ProxyTransformer {

	
	@Override
	public TransformResult trasform(String bundleZipFileName , String newBundlePath)  {
		TransformResult result = new TransformResult() ; 
        try {
        	ProxyBundleParser proxyBundle = new ProxyBundleParser(bundleZipFileName) ;
        	HashMap<OasOperation , Flow >  oas = proxyBundle.checkOpenApiConsistancy(true, false);
        	String estimatedOasPolicyFileName = "apiproxy/policies/"+ proxyBundle.getEstimatedOasPolicyName() + ".xml";
    		String oasJsonStr = proxyBundle.getOasJsonStr(); 
    		String modifiedOasElement = "<Payload contentType=\"application/json\" variablePrefix=\"@\" variableSuffix=\"#\">\n"+oasJsonStr+"\n </Payload>" ; 
        	        	
        	ZipXmlModifier.modifyXmlElement(bundleZipFileName , estimatedOasPolicyFileName ,ProxyBundleParser.PAYLOAD_XPTH , modifiedOasElement , newBundlePath  );
        	result.withFailed(false)
  		  	.withSource(bundleZipFileName)
  		  	.withDestination(newBundlePath)	
  		  	.withTransformerClass(this.getClass()); 

        } catch (Exception e) {
        	result.withError(e.getMessage())
        	.withFailed(true)
  		  	.withSource(bundleZipFileName)
  		  	.withDestination(newBundlePath)	
  		  	.withTransformerClass(this.getClass()); 
        	e.printStackTrace();
        }
        return result ; 
	}

	@Override
	public boolean filter(String bundleZipFileNam) {
		return true;
	}

}
