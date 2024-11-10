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

import com.smartvalue.apigee.configuration.AppConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.ProxyEndpoint;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Child;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Flow;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Request;
import com.smartvalue.apigee.rest.schema.proxyRevision.OasOperation;
import com.smartvalue.apigee.rest.schema.proxyRevision.ProxyRevision;
import com.smartvalue.swagger.v3.parser.util.OpenAPIDeserializer;
import com.smartvalue.swagger.v3.parser.util.SwaggerParseResult;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.parser.ObjectMapperFactory;


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
	private String oasStr;
	private JsonNode oasJsonNode;  
	
	public JsonNode getOasJsonNode() {
		return oasJsonNode;
	}


	public String getOasStr() {
		return oasStr;
	}


	public ProxyBundleParser(String inputZipFilePath) throws ParserConfigurationException, SAXException, XPathExpressionException, FileNotFoundException
	{
		String proxyName = new File(inputZipFilePath).getName();
		proxyName = proxyName.substring(0 , proxyName.indexOf(".zip")) ; 
		try (
		        FileInputStream fileInputStream = new FileInputStream(inputZipFilePath);
		        ZipInputStream zipInputStream = new ZipInputStream(fileInputStream , AppConfig.getCharset());
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

		        this.estimateSwaggerParser(ProxyRevision.getOasFlowName()); 

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
			
			return (dotIndex==-1)? entryName : entryName.substring(0,dotIndex).substring(elementPath.length()) ;	
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
	
	public SwaggerParseResult getSwaggerParser() throws XPathExpressionException, JsonMappingException, JsonProcessingException
	{
		return getSwaggerParser(ProxyRevision.getOasFlowName()) ; 
	}
	
	
	public String getEstimatedOasPolicyName() {
		return estimatedOasPolicyName;
	}
	
	private void estimateSwaggerParser(String oasFlowName) throws XPathExpressionException, JsonMappingException, JsonProcessingException
	{
		Flow oasflow =  searchForGetOasFlow(oasFlowName);  
		if ( oasflow == null ) 
		{
			throw new IllegalArgumentException ("OAS Flow ("+oasFlowName+")  Not Found  " ) ;   
		}
		Request request = oasflow.getRequest() ; // "GetOAS"
		List<Child>  xx =request.getChildren();
		//-- Assume the last step contains the proxy OAS json   
		int size = xx.size() ; 
		if (size == 0 ) {
			throw new IllegalArgumentException ("OAS Flow ("+oasFlowName+") Found But Empty " ) ;
		}
		estimatedOasPolicyName = xx.get(size-1).getStep().getName() ;
		
		Policy policy = this.getPolices().get(estimatedOasPolicyName) ; 
		oasStr= policy.getXpathValue(ProxyBundleParser.PAYLOAD_XPTH ) ; 
		 
		String modifiedOasString = ProxyBundleParser.substituteVarValues(oasStr, ProxyBundleParser.getVariableSubstitutions()) ; 
		
		oasJsonNode = ObjectMapperFactory.createJson().readTree(modifiedOasString);
		
		OpenAPIDeserializer openAPIDeserializer = new OpenAPIDeserializer() ;
		swaggerParseResult = openAPIDeserializer.mydeserialize(oasJsonNode) ; 
		
	}
	
	private static String substituteVarValues(String inputStr , HashMap<String , String> varsNameValuePairs )
	{
		String result = inputStr ; 
		for (Entry<String , String> entry :  varsNameValuePairs.entrySet() )
		{
			result = result.replace(entry.getKey() , entry.getValue()); 
		}
		return result ; 
	}
	private static String unSubstituteVarValues(String inputStr , HashMap<String , String> varsNameValuePairs )
	{
		String result = inputStr ; 
		for (Entry<String , String> entry :  varsNameValuePairs.entrySet() )
		{
			result = result.replace( entry.getValue() , entry.getKey() ); 
		}
		return result ; 
	}
	private static HashMap<String , String> variableSubstitutions = null; 
	private static HashMap<String , String> getVariableSubstitutions() 
	{
		if (variableSubstitutions == null)
		{	variableSubstitutions = new HashMap<String , String>() ; 
			variableSubstitutions.put("@oas.servers#", "[{\"url\":\"https://api-test.moj.gov.local/1111\"}]") ; 
			//variableSubstitutions.put("\"@oas.servers#\"", "[{\"url\":\"https://api-test.moj.gov.local/1111\"}]") ;
			

			variableSubstitutions.put("@servers#"    , "[{\"url\":\"https://api-test.moj.gov.local/2222\"}]" ) ;
			variableSubstitutions.put("@oas.contact.email#", "XXXXXYYYYZZZZ@moj.gov.sa") ; 
			variableSubstitutions.put("@oas.securitySchemes#", "\"AAAAAAABBBBBBB\"") ;
		
		}
		return variableSubstitutions;
	}


	public void setVariableSubstitutions(HashMap<String, String> variableSubstitutions) {
		this.variableSubstitutions = variableSubstitutions;
	}


	public SwaggerParseResult getSwaggerParser(String getOasFlowName) throws XPathExpressionException, JsonMappingException, JsonProcessingException
	{
		if (swaggerParseResult == null)
		{
			 estimateSwaggerParser(getOasFlowName) ; 
		}
		return swaggerParseResult ; 
	}

	public HashMap<Flow, OasOperation> checkFlowsConsistancy (boolean fixOperationId, boolean execludeKnownFlows ) throws Exception 
	{
		SwaggerParseResult swaggerParse = this.getSwaggerParser(ProxyRevision.getOasFlowName()) ; 
		ArrayList<String> execuldedFlowNames = new ArrayList<String>() ; 
		if (execludeKnownFlows)
		{execuldedFlowNames.add(ProxyRevision.getOasFlowName()); 
		execuldedFlowNames.add(ProxyRevision.SERVICE_NOT_AVAILABLE);}
		List<Flow> allApigeeFlows = this.getAllFlows(execuldedFlowNames) ;
		ProxyEndpoint oasProxyEndPoint = this.getOASProxyEndpoint(); 
		return ProxyRevision.checkFlowsConsistancy(swaggerParse, allApigeeFlows , oasProxyEndPoint.getConnection().getBasePath(), fixOperationId , execludeKnownFlows ) ; 
	}
	
	public HashMap<OasOperation , Flow> checkOpenApiConsistancy (boolean fixOperationId, boolean execludeKnownFlows ) throws Exception 
	{
		SwaggerParseResult swaggerParse = this.getSwaggerParser(ProxyRevision.getOasFlowName()) ; 
		ArrayList<String> execuldedFlowNames = new ArrayList<String>() ; 
		if (execludeKnownFlows)
		{execuldedFlowNames.add(ProxyRevision.getOasFlowName()); 
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
		    Flow flow  = proxyEndPoint.getFlowByName(ProxyRevision.getOasFlowName()) ; 
		    if (flow != null) { result = proxyEndPoint ;  break ; } 
		}
		return result ;
	}

	
	
	public String getOasJsonStr() throws XPathExpressionException, JsonMappingException, JsonProcessingException {
		String jsonString = this.getSwaggerParser().getOpenAPI().toJsonString(); 
		return unSubstituteVarValues(jsonString , ProxyBundleParser.getVariableSubstitutions()); 
	}
	
	
	
}
