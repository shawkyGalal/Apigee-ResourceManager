package com.smartvalue.apigee.resourceManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.ApigeeConfig;
import com.smartvalue.apigee.configuration.infra.Infra;
import com.smartvalue.apigee.rest.schema.environment.Environment;
import com.smartvalue.apigee.rest.schema.organization.Organization;
import com.smartvalue.apigee.rest.schema.product.ProductsServices;
import com.smartvalue.apigee.rest.schema.proxy.ProxyServices;
import com.smartvalue.apigee.rest.schema.server.MPServer;

public class ApigeeTool 
{
	private static String configFile ; 
	private static String infra ;
	private static String org ; 
	private static String operation ;
	private static ManagementServer ms ; 
	
	private static void initialize(String[] args) throws Exception
	{
		HashMap<String , String> argsMap = convertArgsToHashMap(args) ;
		System.out.println(argsMap );
		operation = getMandatoryArg ( argsMap , "-operation") ; 
	 	configFile = argsMap.get("-configFile") ; 
    	infra = argsMap.get("-infra") ;
    	org = argsMap.get("-org") ;
    	
    	ApigeeConfig ac = new ApigeeConfig(configFile ) ; 
    	Infra infraObj = ac.getInfra("MasterWorks" , "MOJ" , infra) ;
    	ms = new ManagementServer(infraObj) ; 
	}

	public static void main(String[] args) throws Exception {
    	initialize(args);
    	if (operation != null)
    	{
        switch (operation) {
            case "listProxiesUsingTargetServer":
            	listProxiesUsingTargetServer(args);
                break;
            case "listProxiesNotDeployed":
                listProxiesNotDeployed(args);
                break;
            case "productsWithoutProxies":
            	productsWithoutProxies(args);
                break;
            case "ProxiesWithoutPolices":
            	ProxiesWithoutPolices(args);
                break;
            case "listAllEnvsMessageProcessors":
            	listAllEnvsMessageProcessors(args);
                break;  
            default:
                System.out.println("Unknown operation: " + operation);
                printUsage();
                break;
        	}
    	}
    	else { printUsage();  } 
    }

	private static String getMandatoryArg( HashMap<String , String>  args , String arg)
	 { 
		String result = args.get(arg) ;
		if ( result == null && ! arg.equalsIgnoreCase("-operation"))
		{
			String operation = getMandatoryArg(args , "-operation") ; 
			System.out.println(" Argument " + arg + " is Mandatory for your selected operation "+ operation +" in your command argument list ");
			System.exit(1) ; 
		}
		return result;
		 
	 }
   
	private static void printUsage() {
	        System.out.println("Usage:");
	        System.out.println("java ApigeeTool -configFile <configFile> -infra <infra> -org <orgName> -operation <operation> [<operation-specific-args>]");
	        System.out.println("Operations:");
	        System.out.println("  listProxiesUsingTargetServer -targetServer <TargetServer> -deployedRevisionOnly <true/false>");
	        System.out.println("  		Search for All Apigee Proxies that use a given targetServer ");
	        System.out.println("  listProxiesNotDeployed");
	        System.out.println("  		Search for Apigee Proxies with no deployments");
	        System.out.println("  productsWithoutProxies:");
	        System.out.println("  		Search for Apigee Products with no proxies, if granted to any developer he will have access to all proxies ");
	        System.out.println("  ProxiesWithoutPolices  -policesName <policesName> -deployedRevisionOnly <true/false> ");
	        System.out.println("  		Search for Apigee Proxies do not use any of the given policy names");
	        System.out.println("  listAllEnvsMessageProcessors -orgName <orgName>");
	        System.out.println("  		List Of Regions/Env Message Processors ");
    }
	    
	private static HashMap<String , String> convertArgsToHashMap(String[] args )
    {
	    	HashMap<String , String> result = new HashMap<>() ; 
	    	for (int i = 0; i < args.length - 1; i += 2) {
	    		result.put ( args[i] , args[i+1] ) ; 
	    	}
			return result;
    }
	    
	
	
	
	private static HashMap<String, List<Object>>  ProxiesWithoutPolices(String[] args) throws UnirestException, IOException 
	{
	 HashMap<String , String> argsMap = convertArgsToHashMap(args) ;
 	 String policesName = getMandatoryArg(argsMap, "-policesName"); //argsMap.get("-targetServer") ;
 	 String[]  policesNameArray =  policesName.split(",") ; 
 	 String deployedRevisionOnly =  getMandatoryArg(argsMap, "-deployedRevisionOnly");
 	
	 ProxyServices ps = ms.getProxyServices(org) ;
	 HashMap<String,List<Object>>  result  = ps.getProxiesWithoutPolices(policesNameArray, deployedRevisionOnly.equalsIgnoreCase("true")) ;

	 System.out.println("=================List Of Proxies Do not Use the following policies "+policesName+" ======================");
	 System.out.println(Renderer.hashMapWithArraylisttoHtmlTable(result));
	 return result ; 	
	}

 	private static ArrayList<Object>  productsWithoutProxies(String[] args) throws UnirestException, IOException 
 	{
	 ProductsServices ps = ms.getProductServices(org) ; 
	 ArrayList<Object> results  = ps.getProductsWithoutProxies() ;
	 System.out.println("=================List Of Products without Proxies ======================");
	 System.out.println(Renderer.arrayListToHtmlTable(results)); 
	 return results; 
	}
 
    private static void listProxiesNotDeployed(String[] args ) throws Exception 
    {
		Organization orgObj = (Organization) ms.getOrgByName(org) ;  
		
    	ArrayList<Object> proxiesNotDeployed = orgObj.getUndeployedProxies() ;
    	System.out.println("=================List Of Proxies With No Deployments ======================");
    	System.out.println(Renderer.arrayListToHtmlTable(proxiesNotDeployed));
    	System.out.println(proxiesNotDeployed); 
    }
 
    private static void listProxiesUsingTargetServer(String[] args) throws Exception 
    {
    	HashMap<String , String> argsMap = convertArgsToHashMap(args) ;
    	String targetServer = getMandatoryArg(argsMap, "-targetServer"); //argsMap.get("-targetServer") ;
    	String deployedRevisionOnly =  getMandatoryArg(argsMap, "-deployedRevisionOnly");
    	
		Organization orgObj = (Organization) ms.getOrgByName(org) ;  
		HashMap<String, Object> proxies = orgObj.getAllProxiesUsesTargetServer(targetServer , deployedRevisionOnly.equals("true")); 
		System.out.println("=================List Of Proxies Using a Target Server : "+ targetServer +" ======================");
		System.out.println(Renderer.hashMaptoHtmlTable(proxies));
		System.out.println(proxies);
    }
    
    private static void listAllEnvsMessageProcessors(String[] args) throws Exception
    {
    	HashMap<String , String> argsMap = convertArgsToHashMap(args) ;
    	String orgName = getMandatoryArg(argsMap, "-orgName") ; 
    	HashMap<String , HashMap< String , List<MPServer>> > result = ms.getServerServices().getAllEnvsMessageProcessors(orgName) ; 
    	System.out.println("=================List Of Regions/Env Message Processors  ======================");
		System.out.println(Renderer.hashMaptoHtmlTable(result));
    }
}