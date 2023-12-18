package com.smartvalue.apigee.resourceManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.ApigeeConfig;
import com.smartvalue.apigee.configuration.infra.Infra;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.rest.schema.organization.Organization;
import com.smartvalue.apigee.rest.schema.product.ProductsServices;
import com.smartvalue.apigee.rest.schema.proxy.ProxyServices;
import com.smartvalue.apigee.rest.schema.proxy.google.auto.GoogleProxiesList;
import com.smartvalue.apigee.rest.schema.server.MPServer;
import com.smartvalue.moj.clients.environments.JsonParser;

public class ApigeeTool 
{
	private static String configFile ; 
	private static String infra ;
	private static String org ; 
	private static String operation ;
	private static Infra infraObject; 
	private static ManagementServer ms ; 
	
	private static void initialize(String[] args) throws Exception
	{
		
		HashMap<String , String> argsMap = convertArgsToHashMap(args) ;
		System.out.println(argsMap );
		operation = getMandatoryArg ( argsMap , "-operation") ; 
	 	configFile = argsMap.get("-configFile") ; 
    	infra = argsMap.get("-infra") ;
    	
		JsonParser apigeeConfigParser = new JsonParser(  ) ;
		ApigeeConfig ac = (ApigeeConfig) apigeeConfigParser.getObject(configFile , ApigeeConfig.class) ; 

		infraObject = ac.getInfra("MasterWorks" , "MOJ" , infra) ;
    	ms = infraObject.getManagementServer(infraObject.getRegions().get(0).getName()) ;
    	Unirest.setTimeouts(ms.getServerProfile().getConnectionTimeout(), ms.getServerProfile().getSocketTimeout());
	}

	public static void main(String[] args) throws Exception {
    	initialize(args);
    	if (operation != null)
    	{
        switch (operation) {
            case "listProxiesUsingTargetServer": 	listProxiesUsingTargetServer(args);   break;
            case "listProxiesNotDeployed": 			listProxiesNotDeployed(args);         break;
            case "productsWithoutProxies":       	productsWithoutProxies(args);         break;
            case "ProxiesWithoutPolices":         	ProxiesWithoutPolices(args);          break;
            case "listAllEnvsMessageProcessors":   	listAllEnvsMessageProcessors(args);   break;  
            case "migrate":			            	migrate(args);			              break;  
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
 	 org = getMandatoryArg(argsMap, "-org");
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
		
    	ArrayList<String> proxiesNotDeployed = orgObj.getUndeployedProxies() ;
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
    
    private static void migrate(String[] args) throws Exception {
    	HashMap<String , String> argsMap = convertArgsToHashMap(args) ;
    	String importAll = argsMap.get("-importAll") ;
    	String exportAll =  argsMap.get("-exportAll") ;
    	String deleteAll =  argsMap.get("-deleteAll") ;
    	if (importAll != null)
    	{
        switch (importAll) {
            case "proxies": 		importAllProxies(args);       	break;
            case "sharedFlows": 	importAllSharedFlows(args);    	break;
            case "products":   		importAllProducts(args);		break;
            case "apps":       		importAllApps(args);           	break;
            case "developers": 		importAllDevelopers(args);     	break;  
            case "kvms":       		importAllKvms(args);           	break;
            case "targetServers":   importTargetServers(args);     	break; 
            default: System.out.println("Unknown import argument:  " + importAll);
                printImportUsage();
                break;
        	}
    	}
    	else if (exportAll != null)
    	{
    		
    		switch (exportAll) {
            case "proxies": 		exportAllProxies(args);       	break;
            case "sharedFlows": 	exportAllSharedFlows(args);   	break;
            case "products":   		exportAllProducts(args);		break;
            case "apps":       		exportAllApps(args);           	break;
            case "developers": 		exportAllDevelopers(args);     	break;  
            case "kvms":       		exportAllKvms(args);           	break; 
            case "targetServers":   exportAllTargetServers(args); 	break;
            default: System.out.println("Unknown exportAll argument : " + exportAll);
                printExportUsage();
                break;
        	}
    	}
    	else if (deleteAll != null)
    	{
    		
    		switch (deleteAll) {
            case "proxies": 		deleteAllProxies(args);       	break;
            case "sharedFlows": 	deleteAlltSharedFlows(args);    break;
            case "products":   		deleteAllProxies(args);			break;
            case "apps":       		deleteAllApps(args);           	break;
            case "developers": 		deleteAllDevelopers(args);     	break;  
            case "kvms":       		deleteAllKvms(args);           	break; 
            case "targetServers":	deleteAllTargetServers(args);   break;
            default: System.out.println("Unknown deleteAll argument : " + deleteAll);
                printExportUsage();
                break;
        	}
    	}
    	else { printUsage();  } 
    	
		
	}

	private static void deleteAllTargetServers(String[] args) {
		// TODO Auto-generated method stub
		
	}

	private static void deleteAllKvms(String[] args) {
		// TODO Auto-generated method stub
		
	}

	private static void deleteAllDevelopers(String[] args) {
		// TODO Auto-generated method stub
		
	}

	private static void deleteAllApps(String[] args) {
		// TODO Auto-generated method stub
		
	}

	private static void deleteAlltSharedFlows(String[] args) {
		// TODO Auto-generated method stub
		
	}

	private static void deleteAllProxies(String[] args) throws FileNotFoundException, IOException, UnirestException {
		HashMap<String , String> argsMap = convertArgsToHashMap(args) ;
		org = getMandatoryArg(argsMap, "-org"); 
		ProxyServices proxiesServices = ms.getProxyServices(org); 
		proxiesServices.deleteAllProxies() ;
		
	}

	private static void exportAllTargetServers(String[] args) {
		// TODO Auto-generated method stub
		
	}

	private static void importTargetServers(String[] args) {
		// TODO Auto-generated method stub
		
	}

	private static void exportAllKvms(String[] args) {
		// TODO Auto-generated method stub
		
	}

	private static void exportAllDevelopers(String[] args) {
		// TODO Auto-generated method stub
		
	}

	private static void exportAllApps(String[] args) {
		// TODO Auto-generated method stub
		
	}

	private static void exportAllProducts(String[] args) {
		// TODO Auto-generated method stub
		
	}

	private static void exportAllSharedFlows(String[] args) {
		// TODO Auto-generated method stub
		
	}

	private static void exportAllProxies(String[] args) throws UnirestException, IOException {
		HashMap<String , String> argsMap = convertArgsToHashMap(args) ;
		org = getMandatoryArg(argsMap, "-org"); 
		String folderDest = getMandatoryArg(argsMap, "-folderDest"); 
		ProxyServices proxiesServices = ms.getProxyServices(org);
		proxiesServices.exportAllProxies(folderDest);
		
	}

	private static void printExportUsage() {
		// TODO Auto-generated method stub
		
	}

	private static void printImportUsage() {
		// TODO Auto-generated method stub
		
	}

	private static void importAllKvms(String[] args) {
		// TODO Auto-generated method stub
		
	}

	private static void importAllDevelopers(String[] args) {
		// TODO Auto-generated method stub
		
	}

	private static void importAllApps(String[] args) {
		// TODO Auto-generated method stub
		
	}

	private static void importAllProducts(String[] args) {
		// TODO Auto-generated method stub
		
	}

	private static void importAllSharedFlows(String[] args) {
		// TODO Auto-generated method stub
		
	}

	private static void importAllProxies(String[] args) throws FileNotFoundException, IOException, UnirestException {
		HashMap<String , String> argsMap = convertArgsToHashMap(args) ;
    	String proxiesFolderPath = getMandatoryArg(argsMap, "-folderPath") + "/proxies" ;
    	org = getMandatoryArg(argsMap, "-org");
    	ProxyServices proxiesServices = ms.getProxyServices(org); 
		proxiesServices.uploadFolder(proxiesFolderPath) ;
		
	}

	
    
    
}