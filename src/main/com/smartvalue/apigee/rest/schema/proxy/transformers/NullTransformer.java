package com.smartvalue.apigee.rest.schema.proxy.transformers;

public class NullTransformer implements BundleUploadTransformer {

	@Override
	public String trasform(String bundleZipFileName , String newBundlePath) {
		// TODO Auto-generated method stub
		// Copy bundleZipFileName  to newBundlePath
		return bundleZipFileName;
	}

	@Override
	public boolean filter(String bundleZipFileNam) {
		// TODO Auto-generated method stub
		return false;
	}

}
