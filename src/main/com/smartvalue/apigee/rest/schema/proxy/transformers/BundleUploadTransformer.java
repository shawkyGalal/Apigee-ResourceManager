package com.smartvalue.apigee.rest.schema.proxy.transformers;


public interface  BundleUploadTransformer {

	/**
	 * Implementation should Tranform the bundleZipFileName to another pundle zip file and return new trsnformed file name    
	 * @param bundleZipFileName
	 * @param newBundlePath 
	 * @return
	 */
	public String trasform(  String bundleZipFileName, String newBundlePath);
	public boolean filter(String bundleZipFileNam) ; 
}
