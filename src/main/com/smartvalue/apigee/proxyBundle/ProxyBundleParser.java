package com.smartvalue.apigee.proxyBundle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.ProxyEndpoint;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Child;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Flow;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Request;
import com.smartvalue.apigee.rest.schema.proxyRevision.OasOperation;
import com.smartvalue.apigee.rest.schema.proxyRevision.ProxyRevision;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.parser.core.models.SwaggerParseResult;


public class ProxyBundleParser  
{

	public  static final String PAYLOAD_XPTH = "/RaiseFault/FaultResponse/Set/Payload";
	private HashMap<String , TargetEndPoint> targets = new HashMap<String , TargetEndPoint>(); 
	private HashMap<String ,BundleProxyEndPoint> proxies = new HashMap<String ,BundleProxyEndPoint>();
	private HashMap<String ,Policy> policies = new HashMap<String ,Policy>() ;
	private HashMap<String ,JsResource> jsResources = new HashMap<String ,JsResource>();
	private HashMap<String ,JavaResource> javaResources = new HashMap<String ,JavaResource>(); 
	private HashMap<String ,OpenApiResource> openApiResources = new HashMap<String ,OpenApiResource>();
	private SwaggerParseResult swaggerParseResult ;
	private String estimatedOasPolicyName ;  
	
	public ProxyBundleParser(String inputZipFilePath) throws ParserConfigurationException, SAXException, XPathExpressionException, FileNotFoundException
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

		        this.estimateSwaggerParser(ProxyRevision.OAS_FLOW_NAME); 

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e ; 
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
	
	public static void main(String[] args) throws Exception
	{
		ProxyBundleParser smsGovernanceProxyBundle =  new ProxyBundleParser("C:\\temp\\Stage\\proxies\\moj-internal-clients\\SMS-Governance\\147\\SMS-Governance.zip") ;
		smsGovernanceProxyBundle.checkFlowsConsistancy(false, false); 

	}
	
	public SwaggerParseResult getSwaggerParser() throws XPathExpressionException
	{
		return getSwaggerParser(ProxyRevision.OAS_FLOW_NAME) ; 
	}
	
	
	public String getEstimatedOasPolicyName() {
		return estimatedOasPolicyName;
	}
	
	private void estimateSwaggerParser(String getOasFlowName) throws XPathExpressionException
	{
		Request request = searchForGetOasFlow(getOasFlowName).getRequest() ; // "GetOAS"
		List<Child>  xx =request.getChildren();
		//-- Assume the last step contains the proxy OAS json   
		int size = xx.size() ; 
		estimatedOasPolicyName = xx.get(size-1).getStep().getName() ;
		
		Policy policy = this.getPolices().get(estimatedOasPolicyName) ; 
		String oasStr= policy.getXpathValue(ProxyBundleParser.PAYLOAD_XPTH ) ; 
		
		String modifiedOasString = oasStr.replace("@oas.servers#", "[{\"url\":\"https://api-test.moj.gov.local/xxxxxxxx\"}]") ;
		
		OpenAPIParser parser = new OpenAPIParser();
		
		swaggerParseResult =  parser.readContents(modifiedOasString , null , null);
	}
	
	public SwaggerParseResult getSwaggerParser(String getOasFlowName) throws XPathExpressionException
	{
		if (swaggerParseResult == null)
		{
			 estimateSwaggerParser(getOasFlowName) ; 
		}
		return swaggerParseResult ; 
	}

	public HashMap<Flow, OasOperation> checkFlowsConsistancy (boolean fixOperationId, boolean execludeKnownFlows ) throws Exception 
	{
		SwaggerParseResult swaggerParse = this.getSwaggerParser(ProxyRevision.OAS_FLOW_NAME) ; 
		ArrayList<String> execuldedFlowNames = new ArrayList<String>() ; 
		if (execludeKnownFlows)
		{execuldedFlowNames.add(ProxyRevision.OAS_FLOW_NAME); 
		execuldedFlowNames.add(ProxyRevision.SERVICE_NOT_AVAILABLE);}
		List<Flow> allApigeeFlows = this.getAllFlows(execuldedFlowNames) ;
		ProxyEndpoint oasProxyEndPoint = this.getOASProxyEndpoint(); 
		return ProxyRevision.checkFlowsConsistancy(swaggerParse, allApigeeFlows , oasProxyEndPoint.getConnection().getBasePath(), fixOperationId , execludeKnownFlows ) ; 
	}
	
	public HashMap<OasOperation , Flow> checkOpenApiConsistancy (boolean fixOperationId, boolean execludeKnownFlows ) throws Exception 
	{
		SwaggerParseResult swaggerParse = this.getSwaggerParser(ProxyRevision.OAS_FLOW_NAME) ; 
		ArrayList<String> execuldedFlowNames = new ArrayList<String>() ; 
		if (execludeKnownFlows)
		{execuldedFlowNames.add(ProxyRevision.OAS_FLOW_NAME); 
		execuldedFlowNames.add(ProxyRevision.SERVICE_NOT_AVAILABLE);}
		List<Flow> allApigeeFlows = this.getAllFlows(execuldedFlowNames) ;
		return ProxyRevision.checkOpenApiConsistancy(swaggerParse, allApigeeFlows , this.getOASProxyEndpoint().getConnection().getBasePath() , fixOperationId ) ; 
	}
	
	
	private List<Flow> getAllFlows(ArrayList<String> execuldedFlowNames) {
		List<Flow> result = new ArrayList<Flow>() ; 
		
		for ( Entry<String , BundleProxyEndPoint>  entry : this.getProxies().entrySet())
		{
			BundleProxyEndPoint pep = entry.getValue() ;
			result.addAll(pep.getFlows(execuldedFlowNames) ); 
		}
		return result;
	}


	public BundleFlow searchForGetOasFlow(String getOasFlowName)
	{
		BundleFlow result = null ; 
		for (Entry<String, BundleProxyEndPoint> entry : this.getProxies().entrySet())
		{
			BundleProxyEndPoint pep = entry.getValue() ;
			result = (BundleFlow) pep.getFlowByName(getOasFlowName) ;  
			if ( result != null ) {
				break; 
			}
		}
		return result ; 
	}
	
	private ProxyEndpoint getOASProxyEndpoint() throws UnirestException, IOException
	{
		ProxyEndpoint result = null ; 
		for ( Entry<String, BundleProxyEndPoint> entry : this.getProxies().entrySet()) 
		{
		    ProxyEndpoint proxyEndPoint = entry.getValue();
		    Flow flow  = proxyEndPoint.getFlowByName(ProxyRevision.OAS_FLOW_NAME) ; 
		    if (flow != null) { result = proxyEndPoint ;  break ; } 
		}
		return result ;
	}


	public String getOasJsonStr() throws XPathExpressionException {
		// TODO Auto-generated method stub
		return this.getSwaggerParser().getOpenAPI().toString();
	}
	
	
	
}
