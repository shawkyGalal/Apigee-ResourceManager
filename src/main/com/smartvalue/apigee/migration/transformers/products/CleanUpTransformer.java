package com.smartvalue.apigee.migration.transformers.products;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import com.smartvalue.apigee.migration.transformers.TransformResult;

public class CleanUpTransformer extends ProductTransformer {

	@Override
	public TransformResult trasform(String sourceFileName , String destPath) {
		TransformResult result = new TransformResult() ; 
		String  fileName  = new File (sourceFileName).getName() ; 
        Path sourcePath = Path.of(sourceFileName);
        Path destinationPath = Path.of(destPath + File.separatorChar + fileName);
        try {
        	// TODO implement product clean up 
        	// Copy file from source to destination
        	// Create directories if they don't exist
            Files.createDirectories(destinationPath.getParent());
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied successfully!");
        } catch (Exception e) {
        	result.withSource(sourceFileName)
  		  	.withDestination(destPath)
  		  	.withError(e.getMessage())
        	.withFailed(true) ; 
        	result.setTransformerClass(this.getClass());
        	
        	System.out.println(" transformation Error : " + result.toString());
        }
        return result ; 
	}

	@Override
	public boolean filter(String bundleZipFileNam) {
		return true;
	}

}
