package com.smartvalue.apigee.rest.schema.proxy.transformers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipFileEntryModifyTransformer implements ApigeeObjectTransformer {
	
	private List<String> searchFor ;
	private List<String> replaceBy ; 
	private String filePathInZip ; 

	/**
	 * a General Purpose transformer to replace specific content searchFor in a given filePathInZip  
	 * @param m_filePathInZip  relative file need to be modified inside the zip file
	 * @param m_searchFor		Search for this value in file content 
	 * @param m_replaceBy		replace with this value
	 */
	public ZipFileEntryModifyTransformer(String m_filePathInZip , List<String> m_searchFor , List<String> m_replaceBy)
	{
		this.searchFor = m_searchFor ; 
		this.replaceBy = m_replaceBy ; 
		this.filePathInZip = m_filePathInZip ; 
	}
	
	
	@Override
	public  TransformResult  trasform(String  bundleZipFileName , String outputZipFile) {
		return replaceStringInZipEntry(bundleZipFileName , this.getFilePathInZip() , this.getSearchFor() , this.getReplaceBy() , outputZipFile ) ; 
	}

	
	 public static TransformResult replaceStringInZipEntry(String bundleZipFileName, String filePathInZip, List<String> searchFor, List<String> replaceBy, String outputZipFile) {
		 TransformResult result = new TransformResult() ;    
		 try{ Files.createDirectories(Paths.get(outputZipFile));}
		 catch (IOException e) {
	        	result.withError(e.getMessage())
	        		  .withFailed(true) 
	      		  	  .withSource(bundleZipFileName)
	      		  	  .withDestination(outputZipFile); 
	        	e.printStackTrace();
	        	return result; 
	        	}
		  
		 try (ZipFile zipFile = new ZipFile(bundleZipFileName);
	          ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(outputZipFile +File.separatorChar+ new File(bundleZipFileName).getName()))) 
		 {

	            Enumeration<? extends ZipEntry> entries = zipFile.entries();
	            // Create the destination folder if it doesn't exist
	            
	            while (entries.hasMoreElements()) {
	                ZipEntry entry = entries.nextElement();

	                if (!entry.getName().equals(filePathInZip)) {
	                    // Copy non-matching entry as is
	                    copyZipEntry(zipFile, entry, zipOutputStream);
	                } else {
	                    // Replace content of matching entry
	                    replaceAndCopyZipEntry(zipFile, entry, zipOutputStream, searchFor, replaceBy);
	                }
	            }
	            zipOutputStream.closeEntry();

	        } catch (IOException e) {
	        	result.withError(e.getMessage())
	      		  	  .withFailed(true) 
	      		  	  .withSource(bundleZipFileName)
	      		  	  .withDestination(outputZipFile); 
	        	e.printStackTrace();
	        }
		 
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

	            //zipOutputStream.closeEntry();
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
	            	modifiedContent = modifiedContent.replace(searchFor.get(i), replaceBy.get(i));
                }
	            ZipEntry newEntry = new ZipEntry(entry.getName());
	            zipOutputStream.putNextEntry(newEntry);
	            zipOutputStream.write(modifiedContent.getBytes(StandardCharsets.UTF_8));
	            //zipOutputStream.closeEntry();
            
	        }
	    }

	    public static void main(String[] args) {
	        String zipFilePath = "C:\\temp\\Stage\\proxies\\moj-external-clients\\Apigee-Commandment-API\\2\\Apigee-Commandment-API.zip";
	        String filePathInZip = "apiproxy/policies/Regular-Expression-Protection.xml";
	        List<String> searchFor = Arrays.asList("<Pattern/>"	);
	        List<String> replaceBy = Arrays.asList("<Pattern>xxxxxxx</Pattern>");
	        String destFolder = "C:\\temp\\transformed\\Stage\\proxies\\moj-external-clients\\Apigee-Commandment-API\\2";
	        replaceStringInZipEntry(zipFilePath, filePathInZip, searchFor, replaceBy, destFolder);
	    }
	
	
	@Override
	public boolean filter(String bundleZipFileNam) {
		// TODO Auto-generated method stub
		return true;
	}


	public List<String> getSearchFor() {
		return searchFor;
	}


	public void setSearchFor(List<String> searchFor) {
		this.searchFor = searchFor;
	}


	public List<String> getReplaceBy() {
		return replaceBy;
	}


	public void setReplaceBy(List<String> replaceBy) {
		this.replaceBy = replaceBy;
	}


	public String getFilePathInZip() {
		return filePathInZip;
	}


	public void setFilePathInZip(String filePathInZip) {
		this.filePathInZip = filePathInZip;
	}
	

	

}
