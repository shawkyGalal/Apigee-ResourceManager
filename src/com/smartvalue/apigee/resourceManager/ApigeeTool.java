package com.smartvalue.apigee.resourceManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.ApigeeConfig;
import com.smartvalue.apigee.configuration.infra.Infra;
import com.smartvalue.apigee.rest.schema.organization.Organization;
import com.smartvalue.apigee.rest.schema.product.Product;
import com.smartvalue.apigee.rest.schema.product.ProductsServices;


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
		Organization orgObj = ms.getOrgs().get(org) ;  
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
                
                
            default:
                System.out.println("Unknown operation: " + operation);
                printUsage();
                break;
        }
    	}
    	else { printUsage();  } 
    }

 private static ArrayList<String>  productsWithoutProxies(String[] args) throws UnirestException, IOException {
		
	 Organization orgObj = ms.getOrgs().get(org) ;  
	 ProductsServices ps = ms.getProductServices() ; 
	 ArrayList<String> results  = ps.getProductsWithoutProxies(orgObj) ;
	 System.out.println("=================List Of Products without Proxies ======================");
 	 System.out.println(results); 
	return results; 
		
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
        System.out.println("  listProxiesUsingTargetServer -targetServer <TargetServer> ");
        System.out.println("  listProxiesNotDeployed");
        System.out.println("  productsWithoutProxies");
        
    }
    
    private static HashMap<String , String> convertArgsToHashMap(String[] args )
    {
    	HashMap<String , String> result = new HashMap<String , String>() ; 
    	for (int i = 0; i < args.length - 1; i += 2) {
    		result.put ( args[i] , args[i+1] ) ; 
    	}
		return result;
    }
    
    private static void listProxiesNotDeployed(String[] args ) throws Exception {
   	 
		Organization orgObj = ms.getOrgs().get(org) ;  
		
    	ArrayList<String> proxiesNotDeployed = orgObj.getUndeployedProxies() ;
    	System.out.println("=================List Of Proxies With 0 Deployments ======================");
    	System.out.println(proxiesNotDeployed); 
    }
 
    private static void listProxiesUsingTargetServer(String[] args) throws Exception {
    	HashMap<String , String> argsMap = convertArgsToHashMap(args) ;
    	String targetServer = getMandatoryArg(argsMap, "-targetServer"); //argsMap.get("-targetServer") ;
    	
		Organization orgObj = ms.getOrgs().get(org) ;  
        System.out.println("Config File: " + configFile);
        System.out.println("trgetServer: " + targetServer);
        
        HashMap<String, Object> proxies = orgObj.getAllProxiesUsesTargetServer(targetServer); 
		System.out.println(proxies);
    }
}
