package com.smartvalue.apigee.resourceManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.ApigeeConfig;
import com.smartvalue.apigee.configuration.ApigeeConfigFactory;
import com.smartvalue.apigee.configuration.infra.Infra;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.rest.schema.ApigeeService;
import com.smartvalue.apigee.rest.schema.application.ApplicationServices;
import com.smartvalue.apigee.rest.schema.developer.DeveloperServices;
import com.smartvalue.apigee.rest.schema.keyValueMap.KvmServices;
import com.smartvalue.apigee.rest.schema.organization.Organization;
import com.smartvalue.apigee.rest.schema.product.ProductsServices;
import com.smartvalue.apigee.rest.schema.proxy.ProxyServices;
import com.smartvalue.apigee.rest.schema.proxy.transformers.NullTransformer;
import com.smartvalue.apigee.rest.schema.proxy.transformers.TargetServerTransformer;
import com.smartvalue.apigee.rest.schema.proxy.transformers.ZipFileEntryModifyTransformer;
import com.smartvalue.apigee.rest.schema.server.MPServer;
import com.smartvalue.apigee.rest.schema.sharedFlow.SharedFlowServices;
import com.smartvalue.apigee.rest.schema.targetServer.TargetServerServices;


public class ApigeeTool 
{
	private static String configFile = "config.json" ; 
	private static String infra ;
	private static String org ; 
	private static String operation ;
	private static Infra infraObject; 
	private static ManagementServer ms ; 
	private static String partner = "MasterWorks" ;
	private static String customer = "MOJ" ;
	
	
	private static HashMap<String , String> convertArgsToHashMap(String[] args )
    {
	    	HashMap<String , String> result = new HashMap<>() ; 
	    	for (int i = 0; i < args.length - 1; i += 2) {
	    		result.put ( args[i] , args[i+1] ) ; 
	    	}
			return result;
    }
	
	public static HashMap<String , String> getArgsHashMap() {
		return argsHashMap;
	}

	public static void setArgsHashMap(String[] args) {
		argsHashMap = ApigeeTool.convertArgsToHashMap(args);
	}
	
	private static void initialize(String[] args) throws Exception
	{
		setArgsHashMap(args); 
		HashMap<String , String> argsMap = getArgsHashMap() ; //convertArgsToHashMap(args) ;
		System.out.println(argsMap );
		operation = getMandatoryArg ( argsMap , "-operation") ; 
	 	configFile = argsMap.get("-configFile") ;
	 	try {  partner  = getMandatoryArg (argsMap , "-partner") ;} catch (Exception e) { /* -- Use the Defaults -- */ 	}
	 	try {  customer = getMandatoryArg (argsMap , "-customer") ; } catch (Exception e) { /* -- Use the Defaults -- */ 	}
    	infra = getMandatoryArg(argsMap, "-infra") ;
    	
		ApigeeConfig ac = ApigeeConfigFactory.create( configFile , ApigeeConfig.class) ;  //(ApigeeConfig) apigeeConfigParser.getObject(configFile , ApigeeConfig.class) ; 

		infraObject = ac.getInfra(partner , customer , infra) ;
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
	        System.out.println("  migrate -exportAll <ApigeeObjectType> -orgName <orgName> -folderDest <Dest. Folder Path>");
	        System.out.println("  		Export All <ApigeeObjectType> of the given org name to the given folder ");
	        System.out.println("  		Avialable Apigee Objects Type to Export : proxies, developers , apps , products , kvms , targetServers ");
	        System.out.println("  migrate exportAll -orgName <orgName> -folderDest <Dest. Folder Path>");
	        System.out.println("  		Import All <ApigeeObjectType> of the given org name to the given folder ");
	        System.out.println("  		Avialable Apigee Objects Type to Export : proxies, developers , apps , products , kvms , targetServers ");

    }
	
	private static HashMap<String , String> argsHashMap ; 

	
	private static HashMap<String, List<Object>>  ProxiesWithoutPolices(String[] args) throws Exception 
	{
	 HashMap<String , String> argsMap = convertArgsToHashMap(args) ;
 	 String policesName = getMandatoryArg(argsMap, "-policesName"); //argsMap.get("-targetServer") ;
 	 org = getMandatoryArg(argsMap, "-org");
 	 String[]  policesNameArray =  policesName.split(",") ; 
 	 String deployedRevisionOnly =  getMandatoryArg(argsMap, "-deployedRevisionOnly");
	 ProxyServices ps = (ProxyServices) ms.getProxyServices(org) ;
	 HashMap<String,List<Object>>  result  = ps.getProxiesWithoutPolices(policesNameArray, deployedRevisionOnly.equalsIgnoreCase("true")) ;
	 System.out.println("=================List Of Proxies Do not Use the following policies "+policesName+" ======================");
	 System.out.println(Renderer.hashMapWithArraylisttoHtmlTable(result));
	 return result ; 	
	}

 	private static ArrayList<Object>  productsWithoutProxies(String[] args) throws UnirestException, IOException 
 	{
	 ProductsServices ps = (ProductsServices) ms.getProductServices(org) ; 
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
    	HashMap<String , String> argsMap = getArgsHashMap() ;
    	String importAll 	= argsMap.get("-importAll") ;
    	String exportAll 	= argsMap.get("-exportAll") ;
    	String deleteAll 	= argsMap.get("-deleteAll") ;
    	String transformAll = argsMap.get("-transformAll") ;
    	if (importAll != null)
    	{
        switch (importAll) {
            case "proxies": 			importAllProxies();       	break;
            case "sharedFlows": 		importAllSharedFlows();    	break;
            case "products":   			importAllProducts();		break;
            case "apps":       			importAllApps();           	break;
            case "developers": 			importAllDevelopers();     	break;  
            case "kvms":       			importAllKvms();           	break;
            case "targetServers":   	importTargetServers();     	break;
            case "--help":   			printImportUsage();     		break; 
            default: System.out.println("Unknown import argument:  " + importAll);
                printImportUsage();
                break;
        	}
    	}
    	else if (exportAll != null)
    	{
    		switch (exportAll) {
            case "proxies": 		exportAllProxies();       	break;
            case "sharedFlows": 	exportAllSharedFlows();   	break;
            case "products":   		exportAllProducts();		break;
            case "apps":       		exportAllApps();           	break;
            case "developers": 		exportAllDevelopers();     	break;  
            case "kvms":       		exportAllKvms();           	break; 
            case "targetServers":   exportAllTargetServers(); 	break;
            case "--help":   		printExportUsage();     		break; 
            default: System.out.println("Unknown exportAll argument : " + exportAll);
                printExportUsage();
                break;
        	}
    	}
    	else if (transformAll !=null)
    	{
    		switch (transformAll) {
            case "proxies": 		transformAllProxies();       		break;
            case "sharedFlows": 	transformAlltSharedFlows();    	break;
            case "products":   		transformAllProducts();			break;
            case "apps":       		transformAllApps();           	break;
            case "developers": 		transformAllDevelopers();     	break;  
            case "kvms":       		transformAllKvms();           	break; 
            case "targetServers":	transformAllTargetServers();	break;
            case "--help":   		transformUsage();     		break; 
            default: System.out.println("Unknown deleteAll argument : " + deleteAll);
                printTransformUsage();
                break;
        	}
    	}
    	else if (deleteAll != null)
    	{
    		switch (deleteAll) {
            case "proxies": 		deleteAllProxies();       	break;
            case "sharedFlows": 	deleteAlltSharedFlows();    break;
            case "products":   		deleteAllProducts();		break;
            case "apps":       		deleteAllApps();           	break;
            case "developers": 		deleteAllDevelopers();     	break;  
            case "kvms":       		deleteAllKvms();           	break; 
            case "targetServers":	deleteAllTargetServers();   break;
            case "all":				deleteAll();   				break;
            
            case "--help":   		printDeleteUsage();    		break; 
            default: System.out.println("Unknown deleteAll argument : " + deleteAll);
                printDeleteUsage();
                break;
        	}
    	}
    	else { printUsage();  } 
    	
		
	}


  private static void printTransformUsage() {
		// TODO Auto-generated method stub
		
	}

//---------------TranformAll Operations --------------
	private static void transformUsage() {
		// TODO Auto-generated method stub
		
	}

	
	private static void transformAllTargetServers() throws Exception {
		HashMap<String, String> argsAsHashMap = getArgsHashMap(); 
		org = getMandatoryArg(argsAsHashMap, "-org");
		ApigeeService service = ms.getTargetServersServices(org); 
		String sourceFolder = getMandatoryArg(argsAsHashMap, "-sourceFolder");
		String destFolder = getMandatoryArg(argsAsHashMap, "-destFolder");
		service.transformAll(sourceFolder, destFolder);
		
	}

	private static void transformAllKvms() throws Exception {
		HashMap<String, String> argsAsHashMap = getArgsHashMap(); 
		org = getMandatoryArg(argsAsHashMap, "-org");
		ApigeeService service = ms.getKeyValueMapServices(org); 
		String sourceFolder = getMandatoryArg(argsAsHashMap, "-sourceFolder");
		String destFolder = getMandatoryArg(argsAsHashMap, "-destFolder");
		service.transformAll(sourceFolder, destFolder);
		
	}

	private static void transformAllDevelopers() throws Exception {
		HashMap<String, String> argsAsHashMap = getArgsHashMap(); 
		org = getMandatoryArg(argsAsHashMap, "-org");
		ApigeeService service = ms.getDevelopersServices(org); 
		String sourceFolder = getMandatoryArg(argsAsHashMap, "-sourceFolder");
		String destFolder = getMandatoryArg(argsAsHashMap, "-destFolder");
		service.transformAll(sourceFolder, destFolder);
		
	}

	private static void transformAllApps() throws Exception {
		HashMap<String, String> argsAsHashMap = getArgsHashMap(); 
		org = getMandatoryArg(argsAsHashMap, "-org");
		ApigeeService service = ms.getApplicationServices(org); 
		String sourceFolder = getMandatoryArg(argsAsHashMap, "-sourceFolder");
		String destFolder = getMandatoryArg(argsAsHashMap, "-destFolder");
		service.transformAll(sourceFolder, destFolder);
	}

	private static void transformAllProducts() throws Exception {
		HashMap<String, String> argsAsHashMap = getArgsHashMap(); 
		org = getMandatoryArg(argsAsHashMap, "-org");
		ApigeeService service = ms.getProductServices(org); 
		String sourceFolder = getMandatoryArg(argsAsHashMap, "-sourceFolder");
		String destFolder = getMandatoryArg(argsAsHashMap, "-destFolder");
		
		service.transformAll(sourceFolder, destFolder);
		
	}

	private static void transformAlltSharedFlows() throws Exception {
		HashMap<String, String> argsAsHashMap = getArgsHashMap(); 
		org = getMandatoryArg(argsAsHashMap, "-org");
		ApigeeService service = ms.getSharedFlowServices(org); 
		String sourceFolder = getMandatoryArg(argsAsHashMap, "-sourceFolder");
		String destFolder = getMandatoryArg(argsAsHashMap, "-destFolder");
		//service.getTransformers().add(new NullTransformer()) ;
		service.transformAll(sourceFolder, destFolder);
		
	}

	private static void transformAllProxies() throws Exception {
		org = getMandatoryArg(getArgsHashMap(), "-org");
		String sourceFolder = getMandatoryArg(getArgsHashMap(), "-sourceFolder");
		String destFolder = getMandatoryArg(getArgsHashMap(), "-destFolder");
		ProxyServices ps = (ProxyServices) ms.getProxyServices(org); 
		//ps.getTransformers().add(new TargetServerTransformer()) ; 
		//ps.getTransformers().add(new NullTransformer()) ;
		//List<String> searchFor = Arrays.asList("<Pattern/>"	);
	    //List<String> replaceBy = Arrays.asList("<Pattern>xxxxxxx</Pattern>");
		//ZipFileEntryModifyTransformer zfet = new ZipFileEntryModifyTransformer("apiproxy/policies/Regular-Expression-Protection.xml", searchFor, replaceBy);
		//ps.getTransformers().add(zfet) ; 
		ps.transformAll(sourceFolder, destFolder);
	}

	//---------------DeleteAll Operations --------------
	private static void printDeleteUsage() {
		// TODO Auto-generated method stub
		org = getMandatoryArg(getArgsHashMap(), "-org");
	}

	private static void deleteAll() throws Exception {
		org = getMandatoryArg(getArgsHashMap(), "-org");
		ArrayList<HttpResponse<String>> appResults  = ms.getApplicationServices(org).deleteAll();
		ArrayList<HttpResponse<String>> devResults  = ms.getDevelopersServices(org).deleteAll();
		ArrayList<HttpResponse<String>> podResults  = ms.getProductServices(org).deleteAll(); 
		ArrayList<HttpResponse<String>> sfResults   = ms.getSharedFlowServices(org).deleteAll(); 
		ArrayList<HttpResponse<String>> proResults  = ms.getProxyServices(org).deleteAll();
		ArrayList<HttpResponse<String>> kvmResults  = ms.getKeyValueMapServices(org).deleteAll();
		ArrayList<HttpResponse<String>> tarResults  = ms.getTargetServersServices(org).deleteAll();		
		
	}
	
	private static void deleteAllProducts() throws UnirestException, IOException {
		org = getMandatoryArg(getArgsHashMap(), "-org");
		ProductsServices service = (ProductsServices) ms.getProductServices(org); 
		service.deleteAll(); 
	}
	private static void deleteAllTargetServers() throws UnirestException, IOException {
		org = getMandatoryArg(getArgsHashMap(), "-org");
		TargetServerServices service = (TargetServerServices) ms.getTargetServersServices(org);
		service.deleteAll(); 
		
	}

	private static void deleteAllKvms() throws UnirestException, IOException {
		org = getMandatoryArg(getArgsHashMap(), "-org");
		KvmServices service = (KvmServices) ms.getKeyValueMapServices(org);
		service.deleteAll(); 
		
	}

	private static void deleteAllDevelopers() throws UnirestException, IOException {
		org = getMandatoryArg(getArgsHashMap(), "-org");
		DeveloperServices service = (DeveloperServices) ms.getDevelopersServices(org);
		service.deleteAll(); 
	}

	private static void deleteAllApps() throws UnirestException, IOException {
		org = getMandatoryArg(getArgsHashMap(), "-org");
		ApplicationServices service = (ApplicationServices) ms.getApplicationServices(org);
		service.deleteAll(); 
	}

	private static void deleteAlltSharedFlows() throws Exception {
		org = getMandatoryArg(getArgsHashMap(), "-org"); 
		SharedFlowServices sfs = (SharedFlowServices) ms.getSharedFlowServices(org); 
		sfs.deleteAll() ;
	}

	private static void deleteAllProxies() throws Exception {
		HashMap<String , String> argsMap = getArgsHashMap() ;
		org = getMandatoryArg(argsMap, "-org"); 
		ProxyServices proxiesServices = (ProxyServices) ms.getProxyServices(org); 
		proxiesServices.deleteAll() ;
		
	}

	private static void exportAllTargetServers() {
		// TODO Auto-generated method stub
		
	}

	private static void importTargetServers() {
		// TODO Auto-generated method stub
		
	}

	private static void exportAllKvms() {
		// TODO Auto-generated method stub
		
	}

	private static void exportAllDevelopers() {
		// TODO Auto-generated method stub
		
	}

	private static void exportAllApps() {
		// TODO Auto-generated method stub
		
	}

	private static void exportAllProducts() {
		// TODO Auto-generated method stub
		
	}

	private static void exportAllSharedFlows() throws Exception {
		HashMap<String , String> argsMap = getArgsHashMap() ;
		org = getMandatoryArg(argsMap, "-org"); 
		String folderDest = getMandatoryArg(argsMap, "-folderDest"); 
		SharedFlowServices sfs = (SharedFlowServices) ms.getSharedFlowServices(org);
		sfs.exportAll(folderDest);
		
	}

	private static void exportAllProxies() throws Exception {
		HashMap<String , String> argsMap = getArgsHashMap() ;
		org = getMandatoryArg(argsMap, "-org"); 
		String folderDest = getMandatoryArg(argsMap, "-folderDest"); 
		ProxyServices ps = (ProxyServices) ms.getProxyServices(org);
		ps.exportAll(folderDest);
		
	}

	private static void printExportUsage() {
		// TODO Auto-generated method stub
		
	}

	private static void printImportUsage() {
		// TODO Auto-generated method stub
		
	}

	private static void importAllKvms() {
		// TODO Auto-generated method stub
		
	}

	private static void importAllDevelopers() {
		// TODO Auto-generated method stub
		
	}

	private static void importAllApps() {
		// TODO Auto-generated method stub
		
	}

	private static void importAllProducts() {
		// TODO Auto-generated method stub
		
	}

	private static void importAllSharedFlows() throws UnirestException, IOException {
		HashMap<String , String> argsMap = getArgsHashMap() ;
		String sourceFolder = getMandatoryArg(argsMap, "-sourceFolder")  ;
		org = getMandatoryArg(argsMap, "-org");
		String deploy = argsMap.get("-deploy"); 
    	SharedFlowServices sharedFlowServices = (SharedFlowServices) ms.getSharedFlowServices(org); 
		boolean isdeploy =  deploy != null && deploy.equalsIgnoreCase("yes") ;
		sharedFlowServices.setDeployUponUpload(isdeploy);
		sharedFlowServices.importAll(sourceFolder) ;
		
	}

	private static void importAllProxies() throws FileNotFoundException, IOException, UnirestException {
		HashMap<String , String> argsMap = getArgsHashMap() ;
		String sourceFolder = getMandatoryArg(argsMap, "-sourceFolder")  ;
		org = getMandatoryArg(argsMap, "-org");
		String deploy = argsMap.get("-deploy"); 
    	ProxyServices proxiesServices = (ProxyServices) ms.getProxyServices(org); 
		boolean isdeploy =  deploy != null && deploy.equalsIgnoreCase("yes") ;
		proxiesServices.setDeployUponUpload(isdeploy);
		proxiesServices.importAll(sourceFolder) ;
	}



	
    
    
}