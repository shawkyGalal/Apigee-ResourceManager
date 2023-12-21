package com.smartvalue.apigee.rest.schema.proxy;


public interface  BundleUploadTransformer {

	/**
	 * Implementation should Tranform the bundleZipFileName to another pundle zip file and return new trsnformed file name    
	 * @param bundleZipFileName
	 * @return
	 */
	public String trasform(  String bundleZipFileName); 
}
