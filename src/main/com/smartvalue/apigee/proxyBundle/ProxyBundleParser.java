package com.smartvalue.apigee.proxyBundle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Child;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Flow;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Request;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Response;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

//import com.smartvalue.apigee.rest.schema.proxyEndPoint.ProxyEndpoint; 
public class ProxyBundleParser {

	private HashMap<String , TargetEndPoint> targets = new HashMap<String , TargetEndPoint>(); 
	private HashMap<String ,BundleProxyEndPoint> proxies = new HashMap<String ,BundleProxyEndPoint>();
	private HashMap<String ,Policy> policies = new HashMap<String ,Policy>() ;
	private HashMap<String ,JsResource> jsResources = new HashMap<String ,JsResource>();
	private HashMap<String ,JavaResource> javaResources = new HashMap<String ,JavaResource>(); 
	private HashMap<String ,OpenApiResource> openApiResources = new HashMap<String ,OpenApiResource>();
	
	public ProxyBundleParser(String inputZipFilePath) throws ParserConfigurationException, SAXException
	{
		String proxyName = new File(inputZipFilePath).getName();
		proxyName = proxyName.substring(0 , proxyName.indexOf(".zip")) ; 
		try (
		        FileInputStream fileInputStream = new FileInputStream(inputZipFilePath);
		        ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
		    ) 
		{
		        ZipEntry entry;
		        while ((entry = zipInputStream.getNextEntry()) != null) 
		        {
		        	Element element ; 
		            String entryName = entry.getName();
		            String elementPath = "apiproxy/policies/" ; 
		            String elementName ; 
		            if (entryName.startsWith(elementPath)) 
		            {   element= BundleElement.buildXmlDocument(zipInputStream).getDocumentElement() ;
		            	elementName = getElementName (entryName , elementPath ) ; this.policies.put (elementName , new Policy(proxyName , element));  continue ; } 
		            
		            elementPath = "apiproxy/proxies/" ; 
		            if (entryName.startsWith(elementPath)) 
		            {   element= BundleElement.buildXmlDocument(zipInputStream).getDocumentElement() ;
		            	elementName = getElementName (entryName , elementPath ) ; this.proxies.put (elementName ,  new BundleProxyEndPoint(proxyName , element)); continue; }
		            
		            elementPath = "apiproxy/targets/" ;
		            if (entryName.startsWith(elementPath)) 
		            { 	element= BundleElement.buildXmlDocument(zipInputStream).getDocumentElement() ;
		            	elementName = getElementName (entryName , elementPath) ; this.targets.put( elementName ,  new TargetEndPoint(proxyName , element)); continue ; } 
		            
		            elementPath = "apiproxy/resources/jsc/" ;
		            if (entryName.startsWith(elementPath)) 
		            { elementName = getElementName (entryName, elementPath ) ; this.jsResources.put(elementName , new JsResource(elementName , zipInputStream)); continue ;  } 
		            
		            elementPath = "apiproxy/resources/openapi/" ;
		            if (entryName.startsWith(elementPath)) 
		            { elementName = getElementName (entryName, elementPath ) ; this.openApiResources.put(elementName , new OpenApiResource(elementName , zipInputStream)); continue ;  } 

		            
		            elementPath = "apiproxy/resources/java/" ;
		            if (entryName.startsWith(elementPath)) 
		            { elementName = getElementName (entryName, elementPath ) ; this.javaResources.put(elementName , new JavaResource(elementName , zipInputStream)); continue ;  }
		            
		        }
		        System.out.println("");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private String  getElementName(String entryName , String elementPath )
	{
		int dotIndex = 0 ;  
 
		if (entryName.startsWith(elementPath))  
		{
			if (elementPath.equalsIgnoreCase("apiproxy/resources/jsc/")) 
			{dotIndex = entryName.indexOf(".js") ;}
			
			else if (elementPath.equalsIgnoreCase("apiproxy/resources/java/")) 
			{dotIndex = entryName.indexOf(".jar") ;}
			
			else if (elementPath.equalsIgnoreCase("apiproxy/resources/openapi/")) 
			{dotIndex = entryName.indexOf(".json") ;}
			
			else {dotIndex = entryName.indexOf(".xml") ;}	
			return entryName.substring(0,dotIndex).substring(elementPath.length()) ;	
		}
		
		return "Unknow Proxy Element ";
		 
	}

	public HashMap<String, TargetEndPoint> getTargets() {
		return targets;
	}
	
	public HashMap<String, BundleProxyEndPoint> getProxies() {
		return this.proxies;
	}
	
	public HashMap<String, Policy> getPolices() {
		return this.policies;
	}
	
	public HashMap<String, JsResource> getJsResources() {
		return this.jsResources;
	}
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException
	{
		ProxyBundleParser smsGovernanceProxyBundle =  new ProxyBundleParser("C:\\temp\\Stage\\proxies\\moj-internal-clients\\SMS-Governance\\147\\SMS-Governance.zip") ;
		BundleProxyEndPoint pep = smsGovernanceProxyBundle.getProxies().get("default");
		List<Flow> allFlows = pep.getFlows() ; 
		for (Flow  flow : allFlows )
		{
			String flowName = flow.getName(); 
			Request req = flow.getRequest();
			Response res = flow.getResponse(); 
		}
		
		
		/**
		* 1- Get XML Node for the GetOAS flow from /proxies/default (or others if found )  
	   	 * 2- Get the last Step Name  ( normally returnOpenApiSpecs)  ( file Name for the policy returned the oas ) 
	   	 * 3- parse the policy file /polices/returnOpenApiSpecs.xml  ( it should be of type RaiseFault policy )
	   	 */ 
	}
	
	
	public Paths getOasJson(String getOasFlowName) throws XPathExpressionException
	{
		Paths result ; 
		Request request = searchForGetOasFlow(getOasFlowName).getRequest() ; // "GetOAS"
		List<Child>  xx =request.getChildren();
		//-- Assume the last step contains the proxy OAS json   
		int size = xx.size() ; 
		String policyName = xx.get(size-1).getStep().getName() ;
		
		Policy policy = this.getPolices().get(policyName) ; 
		String oasStr= policy.getXpathValue("/RaiseFault/FaultResponse/Set/Payload") ; 
		String abc = oasStr.replace("@oas.servers#", "\"zzzzzzzzzzzzzzzzzzz\"") ;
		
		OpenAPIParser parser = new OpenAPIParser();
		
		SwaggerParseResult swaggerParse =  parser.readContents(abc , null , null);
		 
		OpenAPI openapi = swaggerParse.getOpenAPI() ; 
		
		result =  openapi.getPaths(); 
		return result ; 
	}
	
	public BundleFlow searchForGetOasFlow(String getOasFlowName)
	{
		BundleFlow result = null ; 
		for (Entry<String, BundleProxyEndPoint> entry : this.getProxies().entrySet())
		{
			String proxyEndPointName= entry.getKey() ;
			BundleProxyEndPoint pep = entry.getValue() ;
			result = (BundleFlow) pep.getFlowByName(getOasFlowName) ;  
			if ( result != null ) {
				break; 
			}
		}
		return result ; 
	}
	
	
	
}
