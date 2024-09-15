package com.smartvalue.apigee.migration.transformers.sharedflows;

import com.smartvalue.apigee.migration.transformers.TransformResult;

public class ZipFileEntryModifyTransformer extends SharedflowTransformer {

	@Override
	public TransformResult trasform(String apigeeObjectFile, String newBundlePath) {
			return new com.smartvalue.apigee.migration.transformers.proxy.ZipFileEntryModifyTransformer().trasform(apigeeObjectFile, newBundlePath);
	}

	@Override
	public boolean filter(String bundleZipFileNam) {
		
		return new com.smartvalue.apigee.migration.transformers.proxy.ZipFileEntryModifyTransformer().filter(bundleZipFileNam);
	}

}
