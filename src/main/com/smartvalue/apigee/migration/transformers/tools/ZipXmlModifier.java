package com.smartvalue.apigee.migration.transformers.tools;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.*;
import org.xml.sax.InputSource;

import com.smartvalue.apigee.configuration.AppConfig;

public class ZipXmlModifier {

	public static void modifyXmlElement(String inputZipFilePath, String fileName, String xpath, String newElementStr, String outputZipFile) throws Exception {
	
		modifyXmlElement(inputZipFilePath, fileName, xpath, convertStringToElement(newElementStr) , outputZipFile); 
	}
	
	public static void modifyXmlElement(String inputZipFilePath, String fileName, String xpath, Element newElement, String outputZipFolder) throws Exception {
		String  inputzipfile = new File(inputZipFilePath).getName(); 
		java.io.File file = new File(outputZipFolder);
		if (!file.exists()) { file.mkdirs(); }
		String outputFilePath  = outputZipFolder + File.separatorChar+  inputzipfile ; 
		File  outputFile = new File (outputFilePath ) ;  
		outputFile.createNewFile(); 
		try (
	        FileInputStream fileInputStream = new FileInputStream(inputZipFilePath);
	        ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
			
	        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(outputFilePath))
	    ) {
	        ZipEntry entry;
	        while ((entry = zipInputStream.getNextEntry()) != null) {
	            String entryName = entry.getName();
	            if (entryName.equals(fileName)) {
	                // Process the XML file and modify the specified element
	                String modifiedXmlContent = processXml(zipInputStream, xpath, newElement);

	                // Add the modified content to the new ZIP entry
	                zipOutputStream.putNextEntry(new ZipEntry(entryName));
	                zipOutputStream.write(modifiedXmlContent.getBytes(AppConfig.getCharset()));
	                zipOutputStream.closeEntry(); // Close the entry after writing
	            } else {
	                // Copy other entries without modification
	                zipOutputStream.putNextEntry(new ZipEntry(entryName));
	                byte[] data = new byte[1024];
	                int bytesRead;
	                while ((bytesRead = zipInputStream.read(data)) != -1) {
	                    zipOutputStream.write(data, 0, bytesRead);
	                }
	                zipOutputStream.closeEntry(); // Close the entry after writing
	            }
	        }
	    }
	}

	private static String processXml(ZipInputStream zipInputStream, String xpath, String newElementStr) throws Exception {
		
		return processXml( zipInputStream,  xpath,  convertStringToElement(newElementStr)) ; 
	}
	private static String processXml(ZipInputStream zipInputStream, String xpath, Element newElement) throws Exception {
	    // Buffer for reading data from the zip entry
	    byte[] buffer = new byte[1024];
	    int bytesRead;

	    // Use a StringBuilder to accumulate the XML content
	    StringBuilder xmlContentBuilder = new StringBuilder();

	    // Read XML content from the zip entry
	    while ((bytesRead = zipInputStream.read(buffer)) != -1) {
	        // Append the read bytes to the StringBuilder
	        xmlContentBuilder.append(new String(buffer, 0, bytesRead));
	    }

	    // Parse the XML content into a Document
	    InputSource source = new InputSource(new StringReader(xmlContentBuilder.toString()));
	    Document document = ZipXmlModifier.getDocBuilder().parse(source);

	    // Apply XPath to select nodes based on the provided expression
	    XPathFactory xPathfactory = XPathFactory.newInstance();
	    XPath xpathInstance = xPathfactory.newXPath();
	    NodeList nodes = (NodeList) xpathInstance.evaluate(xpath, document, XPathConstants.NODESET);

	    // Import the newElement into the document
	    Node importedNewElement = document.importNode(newElement, true);

	    // Implement your logic to modify the selected nodes using importedNewElement
	    for (int i = 0; i < nodes.getLength(); i++) {
	        Node node = nodes.item(i);
	        // Modify the selected node using importedNewElement
	        // For example, replace the node with the importedNewElement
	        node.getParentNode().replaceChild(importedNewElement, node);
	    }

	    // Serialize the modified document back to a string
	    StringWriter writer = new StringWriter();
	    ZipXmlModifier.getTransformer().transform(new DOMSource(document), new StreamResult(writer));
	    return writer.toString();
	}




    public static void main(String[] args) {
        try {
            String inputZipFilePath = "G:\\My Drive\\MasterWorks\\Apigee\\Customers\\MOJ\\10.162.3.3.etc.apigee\\apigee-migrate-tool\\data_history\\MOJ\\Prod\\moj-prod\\iam-protected\\2023-11-19-03-33\\proxies\\AccessCaseFile.zip";
            String fileName = "apiproxy/targets/default.xml";
            String xpath = "/TargetEndpoint/HTTPTargetConnection";
            
            String outputZipFile = "G:\\My Drive\\MasterWorks\\Apigee\\Customers\\MOJ\\10.162.3.3.etc.apigee\\apigee-migrate-tool\\data_history\\MOJ\\Prod\\moj-prod\\iam-protected\\2023-11-19-03-33\\proxies\\AccessCaseFile_updated.zip";
            String newXmlString = "<HTTPTargetConnection><xyz>123</xyz></HTTPTargetConnection>";
            
            // Convert XML string to Element
            Element newXmlElement = convertStringToElement(newXmlString);
            
            modifyXmlElement(inputZipFilePath, fileName, xpath, newXmlElement, outputZipFile);
            System.out.println("New Updated Proxy Pundle " + outputZipFile + "Is Created"); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Element convertStringToElement(String xmlString) throws Exception {
        Document document = ZipXmlModifier.getDocBuilder().parse(new InputSource(new StringReader(xmlString)));
        // Retrieve the root element of the document
        Element rootElement = document.getDocumentElement();
        return rootElement;
    }

     
    private static DocumentBuilder docBuilder ; 
    public static DocumentBuilder getDocBuilder() throws ParserConfigurationException {
		if (docBuilder == null)
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        docBuilder = factory.newDocumentBuilder();
		}
		return docBuilder;
	}

    private static Transformer transformer ;
    public static Transformer getTransformer() throws TransformerConfigurationException, TransformerFactoryConfigurationError {
    	if (transformer == null)
    	{
    		TransformerFactory transformerFactory = TransformerFactory.newInstance("com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl" , null);
    		//transformerFactory.setFeature(javax.xml.transform.sax.SAXTransformerFactory.FEATURE, true); // Enable SAXTransformerFactory
    		transformer = transformerFactory.newTransformer() ; 
    				

    		//Transformer transformer = TransformerFactory.newInstance().newTransformer();
    	}
		return transformer;
	}

}
