package com.smartvalue.apigee.rest.schema.proxy.transformers;


public interface  ApigeeObjectTransformer {

	/**
	 * Implementation should Tranform the apigeeObjectFile to another apigeeObjectFile file and return new trsnformed file name    
	 * @param bundleZipFileName
	 * @param newBundlePath 
	 * @return
	 */
	public void trasform(  String apigeeObjectFile, String newBundlePath);
	public boolean filter(String bundleZipFileNam) ; 
}
