package com.smartvalue.apigee.migration.transformers.proxy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.smartvalue.apigee.migration.transformers.IApigeeObjectTransformer;
import com.smartvalue.apigee.migration.transformers.TransformResult;
import com.smartvalue.apigee.resourceManager.ApigeeTool;

public class ZipFileEntryModifyTransformer extends ProxyTransformer {
	
	
	protected static final Logger logger = LogManager.getLogger(ApigeeTool.class);
	private String searchFor ;
	private String replaceBy ;
	private String filesPathInZip ; 
	private String valueDelimiter = "," ; 

	/**
	 * a General Purpose transformer to replace specific content searchFor in a given filePathInZip  
	 * @param m_filePathInZip  relative file need to be modified inside the zip file
	 * @param m_searchFor		Search for this value in file content 
	 * @param m_replaceBy		replace with this value
	 */
	public ZipFileEntryModifyTransformer()
	{
		
	}
		
	@Override
	public  TransformResult  trasform(String  bundleZipFileName , String outputZipFile) {
		return replaceStringInZipEntry(bundleZipFileName ,  outputZipFile ) ;
		
	}

	
	 public  TransformResult replaceStringInZipEntry(String bundleZipFileName, String outputZipFile) {
		 logger.info("Start Processing Transformer " + this.getClass()) ; 
		 TransformResult result = new TransformResult() ;    
		 try{ Files.createDirectories(Paths.get(outputZipFile));}
		 catch (IOException e) {
	        	result.withError(e.getMessage())
	        		  .withFailed(true) 
	      		  	  .withSource(bundleZipFileName)
	      		  	  .withDestination(outputZipFile); 
	        	e.printStackTrace();
	        	logger.error(e.getMessage());
	        	return result; 
	        	}
		  
		 try (ZipFile zipFile = new ZipFile(bundleZipFileName);
	          ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(outputZipFile +File.separatorChar+ new File(bundleZipFileName).getName()))) 
		 {
	            Enumeration<? extends ZipEntry> entries = zipFile.entries();
	            // Create the destination folder if it doesn't exist
	            List<String> filesPathInZipArray = new ArrayList<String>() ; 
	            for (String element : filesPathInZip.split(valueDelimiter)) {
	            	filesPathInZipArray.add(element.trim());
	            	}
	            
	            while (entries.hasMoreElements()) {
	                ZipEntry entry = entries.nextElement();
               
	                if (! filesPathInZipArray.contains(entry.getName()) ) {
	                    // Copy non-matching entry as is
	                    copyZipEntry(zipFile, entry, zipOutputStream);
	                } else {
	                    // Replace content of matching entry
	                	logger.info ("Zip File entry " + entry.getName() + " Found in " + bundleZipFileName) ;
	                	System.out.println("Zip File entry " + entry.getName() + " Found in " + bundleZipFileName);
	                	List<String> searchForAsList = Arrays.asList( searchFor.split(valueDelimiter)) ; 
	                	List<String> replaceByAsList = Arrays.asList( replaceBy.split(valueDelimiter)) ; 
	                    replaceAndCopyZipEntry(zipFile, entry, zipOutputStream, searchForAsList, replaceByAsList);
	                }
	                
	            }
	            zipOutputStream.closeEntry();

	        } catch (IOException e) {
	        	result.withError(e.getMessage())
	      		  	  .withFailed(true) 
	      		  	  .withSource(bundleZipFileName)
	      		  	  .withDestination(outputZipFile)
	        		  .withTransformerClass(this.getClass());
	        	System.out.println(result.toString()); 
	        }
		 logger.info("End Processing Transformer " + this.getClass()) ;
		 return result; 
	    }

	    private static void copyZipEntry(ZipFile zipFile, ZipEntry entry, ZipOutputStream zipOutputStream) throws IOException {
	        try (InputStream inputStream = zipFile.getInputStream(entry)) {
	            ZipEntry newEntry = new ZipEntry(entry.getName());
	            zipOutputStream.putNextEntry(newEntry);

	            byte[] buffer = new byte[1024];
	            int len;
	            while ((len = inputStream.read(buffer)) > 0) {
	                zipOutputStream.write(buffer, 0, len);
	            }
            
	        }
	    }

	    private static void replaceAndCopyZipEntry(ZipFile zipFile, ZipEntry entry, ZipOutputStream zipOutputStream, List<String> searchFor, List<String> replaceBy) throws IOException {
	        try (BufferedReader reader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry) , StandardCharsets.UTF_8));       ) 
	        {
	            StringBuilder content = new StringBuilder();
	            
	            String line;
	            while ((line = reader.readLine()) != null) {
	                content.append(line).append("\n");
	            }
	            String modifiedContent = content.toString(); 
	            for (int i = 0; i < searchFor.size(); i++) {
	            	String oldValue = searchFor.get(i).trim() ; 
	            	String newValue = replaceBy.get(i).trim() ; 
	            	logger.info("Replacing  Old value : <" + oldValue + ">  With New Value : <" + newValue + ">");
	            	System.out.println("Replacing  Old value : <" + oldValue + ">  With New Value : <" + newValue + ">");
	            	modifiedContent = modifiedContent.replace(oldValue, newValue);
	            	
                }
	            ZipEntry newEntry = new ZipEntry(entry.getName());
	            zipOutputStream.putNextEntry(newEntry);
	            zipOutputStream.write(modifiedContent.getBytes(StandardCharsets.UTF_8));
	        }
	    }

	    public static void main(String[] args) {
	    	Configurator.initialize(null, "resources/log4j/log4j2.xml");  	
	        String zipFilePath = "C:\\temp\\Stage\\proxies\\moj-external-clients\\Apigee-Commandment-API\\2\\Apigee-Commandment-API.zip";
	        String filePathInZip = "apiproxy/policies/Regular-Expression-Protection.xml";
	        String searchFor = "<Pattern/>"	;
	        String replaceBy = "<Pattern>zzzzzzz</Pattern>";
	        String destFolder = "C:\\temp\\transformed\\Stage\\proxies\\moj-external-clients\\Apigee-Commandment-API\\2";
	        ZipFileEntryModifyTransformer zfemt = new ZipFileEntryModifyTransformer() ; 
	        zfemt.setSearchFor(searchFor);
	        zfemt.setReplaceBy(replaceBy);
	        zfemt.setFilePathInZip(filePathInZip);
	        zfemt.replaceStringInZipEntry(zipFilePath,   destFolder);
	    }
	
	
	@Override
	public boolean filter(String bundleZipFileNam) {
		return true;
	}


	public String getFilePathInZip() {
		return filesPathInZip;
	}


	public void setFilePathInZip(String filePathInZip) {
		this.filesPathInZip = filePathInZip;
	}


	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}


	public void setReplaceBy(String replaceBy) {
		this.replaceBy = replaceBy;
	}
	

	

}
