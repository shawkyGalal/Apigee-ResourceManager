package com.smartvalue.apigee.migration.transformers.proxy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import com.smartvalue.apigee.migration.transformers.TransformResult;

public class NullTransformer extends ProxyTransformer {

	@Override
	public TransformResult trasform(String bundleZipFileName , String newBundlePath) {
		TransformResult result = new TransformResult() ; 
		String  fileName  = new File (bundleZipFileName).getName() ; 
        Path sourcePath = Path.of(bundleZipFileName);
        Path destinationPath = Path.of(newBundlePath + File.separatorChar + fileName);
        try {
            // Copy file from source to destination
        	// Create directories if they don't exist
            Files.createDirectories(destinationPath.getParent());
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied successfully!");
        } catch (IOException e) {
        	result.withSource(bundleZipFileName)
  		  	.withDestination(newBundlePath)
  		  	.withTransformerClass(this.getClass())
  		  	.withError(e.getMessage())
  		  	.withFailed(true);

        }
        return result ; 
	}

	@Override
	public boolean filter(String bundleZipFileNam) {
		return true;
	}

}
