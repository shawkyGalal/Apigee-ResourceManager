package com.smartvalue.apigee.migration.transformers;

public interface  IApigeeObjectTransformer {

	/**
	 * Implementation should Tranform the apigeeObjectFile to another apigeeObjectFile file and return a TransformResult object    
	 * @param bundleZipFileName
	 * @param newBundlePath 
	 * @return
	 * Sample Implementation : 
	 * ZipFileEntryModifyTransformer
	 * TargetServerTransformer
	 * 
	 */
	public TransformResult  trasform(  String apigeeObjectFile, String newBundlePath);
	public boolean filter(String bundleZipFileNam) ; 
}
