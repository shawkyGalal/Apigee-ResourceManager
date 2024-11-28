package com.smartvalue.spring.apigee;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.smartvalue.apigee.configuration.AppConfig;
import com.smartvalue.apigee.configuration.AppConfigFactory;
import com.smartvalue.apigee.configuration.infra.Infra;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.configuration.transformer.auto.Attribute;
import com.smartvalue.apigee.configuration.transformer.auto.TransformerConfig;
import com.smartvalue.apigee.configuration.transformer.auto.TransformersConfig;
import com.smartvalue.apigee.migration.ProcessResult;
import com.smartvalue.apigee.migration.ProcessResults;
import com.smartvalue.apigee.migration.export.ExportResult;
import com.smartvalue.apigee.migration.export.ExportResults;
import com.smartvalue.apigee.migration.load.LoadResults;
import com.smartvalue.apigee.migration.transformers.ApigeeObjectTransformer;
import com.smartvalue.apigee.migration.transformers.TransformationResults;
import com.smartvalue.apigee.migration.transformers.proxy.ProxyTransformer;
import com.smartvalue.apigee.migration.transformers.sharedflows.SharedflowTransformer;
import com.smartvalue.apigee.proxyBundle.ProxyBundleParser;
import com.smartvalue.apigee.resourceManager.helpers.Helper;
import com.smartvalue.apigee.rest.schema.ApigeeComman;
import com.smartvalue.apigee.rest.schema.ApigeeService;
import com.smartvalue.apigee.rest.schema.BundleObjectService;
import com.smartvalue.apigee.rest.schema.DeploymentsStatus;
import com.smartvalue.apigee.rest.schema.RollBackable;
import com.smartvalue.apigee.rest.schema.product.Product;
import com.smartvalue.apigee.rest.schema.proxy.Proxy;
import com.smartvalue.apigee.rest.schema.proxy.ProxyServices;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Flow;
import com.smartvalue.apigee.rest.schema.proxyRevision.OasOperation;
import com.smartvalue.apigee.rest.schema.sharedFlow.SharedFlowServices;

@RestController
public class EtlRestServices {

	private static final String DEFAULT_PARTNER = "MasterWorks";
	private static final String DEFAULT_CUSTOMER = "MOJ";
	ManagementServer  ms ;
	private AppConfig getAppConfig() throws FileNotFoundException, IOException {
		
		return AppConfigFactory.create( "config.json" , AppConfig.class);
	}
	
	private void initialize(String partner , String customer , String infra , String org , String authorizationHeader , String migrationBasePath) throws Exception
	{
		if(partner == null) partner = DEFAULT_PARTNER ;
		if (customer == null) customer = DEFAULT_CUSTOMER; 
		AppConfig ac = getAppConfig();
		if (migrationBasePath != null) ac.setMigrationBasePath(migrationBasePath);
		Infra infraObject = ac.getInfra(partner , customer , infra) ;
    	ms = infraObject.getManagementServer(infraObject.getRegions().get(0).getName()) ;
    	ms.setOrgName(org) ; 
    	ms.setAuthorizationHeader(authorizationHeader ) ;
    	ms.getAllOrgNames(); 
	}
	
	private ResponseEntity<String> buildJsonResponse(String jsonString , HttpStatus status )
	{
		 HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Type", "application/json");
 	    return new ResponseEntity<String>(jsonString, headers , status);
	}

		
	    
	/**
	 * Perform a complete ETLD - Extract , Transform , Load and Deploy on a bundle object ( proxy or sharedflow ) 
	 * using the already defined and active transformers  
	 * @param partner
	 * @param customer
	 * @param infra
	 * @param org
	 * @param bundleType
	 * @param objectName
	 * @param authorizationHeader
	 * @return
	 */
	@PostMapping("/apigee/infras/{infra}/orgs/{org}/migrate/etl/{bundleType}/{objectName}")
    public ResponseEntity<String> etlBundle(
    		@RequestHeader(value = "partner" , required = false )   String partner,
            @RequestHeader(value = "customer", required = false )  String customer,
            @RequestHeader(value = "migrationBasePath" , required = false )   String migrationBasePath,
            @PathVariable("infra") String infra,
            @PathVariable("org") String org,
            @PathVariable("bundleType") String bundleType,
            @PathVariable("objectName") String objectName,
            @RequestHeader("Authorization")  String authorizationHeader
         
    )  {
		try {
		initialize(partner , customer , infra , org, authorizationHeader , migrationBasePath); 
    	UUID uuid = UUID.randomUUID(); 
    	Thread thread = new Thread(() -> {
        System.out.println("Starting the complete ETL Process on " + objectName );
        try {
        		ApigeeService bundleObjectService = ms.getServiceByType(bundleType) ; 
	       		ProcessResults eTLResult = bundleObjectService.performETL(objectName , uuid);
	       		String serializePath = ms.getSerlizeProcessResultFileName(uuid.toString()) ; 
	       		Helper.serialize(serializePath ,eTLResult ) ; 
        	} catch (Exception e) {
             e.printStackTrace();
        	}
        }); 
    	thread.start() ; 
        // Process the request and return a response
        return new ResponseEntity<String>("{\"processUUID\":\""+uuid+"\"}", HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@PostMapping("/apigee/infras/{infra}/orgs/{org}/migrate/etl/{bundleType}")
    public ResponseEntity<String> etlSetOfBundles(
    		@RequestHeader(value = "partner" , required = false )   String partner,
            @RequestHeader(value = "customer", required = false )  String customer,
            @RequestHeader(value = "migrationBasePath" , required = false )   String migrationBasePath,
            @PathVariable("infra") String infra,
            @PathVariable("org") String org,
            @PathVariable("bundleType") String bundleType,
            @org.springframework.web.bind.annotation.RequestBody() String ObjectsNameListStr ,  
            @RequestHeader("Authorization")  String authorizationHeader
         
    )  {
		try {
		initialize(partner , customer , infra , org, authorizationHeader , migrationBasePath); 
    	UUID uuid = UUID.randomUUID(); 
    	Gson gson = new Gson(); 
    	ArrayList<String> ObjectsNameList = (ArrayList<String>) gson.fromJson(ObjectsNameListStr , ArrayList.class) ; 
    	Thread thread = new Thread(() -> {
    	
        try {
        	ProcessResults eTLResult = new ProcessResults("ETL a set Of Proxies/SharedFlows" , uuid) ; 
    		ApigeeService bundleObjectService = ms.getServiceByType(bundleType) ;
        	for (String objectName : ObjectsNameList )
	        	{
	                System.out.println("Starting the complete ETL Process on " + objectName );
 		       		eTLResult.addAll( bundleObjectService.performETL(objectName , uuid));
		       		String serializePath = ms.getSerlizeProcessResultFileName(uuid.toString()) ; 
		       		Helper.serialize(serializePath ,eTLResult ) ;
	        	}
        	} catch (Exception e) {
             e.printStackTrace();
        	}
        }); 
    	thread.start() ; 
        // Process the request and return a response
        return new ResponseEntity<String>("{\"processUUID\":\""+uuid+"\"}", HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    } 
	
	
	
	@PostMapping("/apigee/infras/{infra}/orgs/{org}/migrate/rollback/{bundleType}/deployProcessId/{deployUUID}")
    public ResponseEntity<String> rollBackAll(
    		@RequestHeader(value = "partner" , required = false )   String partner,
            @RequestHeader(value = "customer", required = false )  String customer,
            @RequestHeader(value = "migrationBasePath" , required = false )   String migrationBasePath,
            @PathVariable("infra") String infra,
            @PathVariable("org") String org,
            @PathVariable("bundleType") String bundleType,
            @PathVariable("deployUUID") String deployUUID,
            @RequestHeader("Authorization")  String authorizationHeader
    )  {
		try {
		initialize(partner , customer , infra , org, authorizationHeader , migrationBasePath);  
    	UUID uuid = UUID.randomUUID(); 
    	Thread thread = new Thread(() -> 
	    	{
		        try {
		        		BundleObjectService apigeeService = (BundleObjectService) ms.getServiceByType(bundleType) ; 
		        		UUID deployUUIDObj = UUID.fromString(deployUUID); 
			       		ProcessResults eTLResult = apigeeService.rollBackAllToSerializedDeployStatus( deployUUIDObj);
			       		String serializePath = ms.getSerlizeProcessResultFileName(uuid.toString() ) ; 
			       		Helper.serialize(serializePath ,eTLResult ) ; 
		        	} catch (Exception e) {
		             e.printStackTrace();
		        	}
		    }
    	); 
    	thread.start() ; 
        // Process the request and return a response
        return new ResponseEntity<String>("{\"rollBackAllUUID\":\""+uuid+"\"}", HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@PostMapping("/apigee/infras/{infra}/orgs/{org}/migrate/rollback/{bundleType}/{objectName}/{deployUUID}")
    public ResponseEntity<String> rollBackSingleEtlBundle(
    		@RequestHeader(value = "partner" , required = false )   String partner,
            @RequestHeader(value = "customer", required = false )  String customer,
            @RequestHeader(value = "migrationBasePath" , required = false )   String migrationBasePath,
            @PathVariable("infra") String infra,
            @PathVariable("org") String org,
            @PathVariable("bundleType") String bundleType,
            @PathVariable("deployUUID") String deployUUID,
            @PathVariable("objectName") String objectName,
            @RequestHeader("Authorization")  String authorizationHeader
    )  {
		try {
		initialize(partner , customer , infra , org, authorizationHeader, migrationBasePath);  
    	UUID uuid = UUID.randomUUID(); 
    	Thread thread = new Thread(() -> 
	    	{
		        try {
		        		RollBackable apigeeService = (RollBackable) ms.getServiceByType(bundleType) ; 
		        		UUID deployUUIDObj = UUID.fromString(deployUUID); 
			       		ProcessResults eTLResult = apigeeService.rollBackObjectToSerializedDeployStatus(objectName ,  deployUUIDObj);
			       		String serializePath = ms.getSerlizeProcessResultFileName(uuid.toString() ) ; 
			       		Helper.serialize(serializePath ,eTLResult ) ; 
		        	} catch (Exception e) {
		             e.printStackTrace();
		        	}
		    }
    	); 
    	thread.start() ; 
        // Process the request and return a response
        return new ResponseEntity<String>("{\"processUUID\":\""+uuid+"\"}", HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	/**
	 * Exports All Objects 
	 * @param partner
	 * @param customer
	 * @param infra
	 * @param org
	 * @param bundleType
	 * @param authorizationHeader
	 * @return
	 */
	@PostMapping("/apigee/infras/{infra}/orgs/{org}/migrate/export/{bundleType}/")
    public ResponseEntity<String> exportAll(
    		@RequestHeader(value = "partner" , required = false )   String partner,
            @RequestHeader(value = "customer", required = false )  String customer,
            @RequestHeader(value = "migrationBasePath" , required = false )   String migrationBasePath,
            @PathVariable("infra") String infra,
            @PathVariable("org") String org,
            @PathVariable("bundleType") String bundleType,
            @RequestHeader("Authorization")  String authorizationHeader
         
    )  {
		try {
			initialize(partner , customer , infra , org, authorizationHeader , migrationBasePath); 
	    	UUID uuid = UUID.randomUUID(); 
	    	Thread thread = new Thread(() -> {
            try {
	            	ApigeeService apigeeService = ms.getServiceByType(bundleType) ; 
	            	String destFolder = ms.getMigPathUpToOrgName(uuid.toString())+File.separator+ apigeeService.getMigationSubFoler() ; 
	            	ExportResults result = apigeeService.exportAll( destFolder , uuid ) ;
	            	String serializePath = ms.getSerlizeProcessResultFileName(uuid.toString()) ; 
	            	Helper.serialize(serializePath ,result ) ; 
	           	
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }); 
	    	thread.start() ; 
	    	
	    	
	        // Process the request and return a response
	        return new ResponseEntity<String>("{\"exportUUID\":\""+uuid+"\"}", HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	
	@PostMapping("/apigee/infras/{infra}/orgs/{org}/migrate/transform/{bundleType}/process/{exportUuid}")
    public ResponseEntity<String> transformAllProxies(
    		@RequestHeader(value = "partner" , required = false )   String partner,
            @RequestHeader(value = "customer", required = false )  String customer,
            @RequestHeader(value = "migrationBasePath" , required = false )   String migrationBasePath,
            @PathVariable("infra")  String infra,
            @PathVariable("org") String org,
            @PathVariable("bundleType") String bundleType,
            @PathVariable("exportUuid")   String exportUuid, // Transform the result of this exportUuid
            @RequestHeader("Authorization") String authorizationHeader ) 
            {
    			try {
    			initialize(partner , customer , infra , org, authorizationHeader, migrationBasePath);  
    			UUID uuid = UUID.randomUUID(); 
    	    	Thread thread = new Thread(() -> {
    	            System.out.println("This is a new thread.");
    	            try {
    	            	ApigeeService bundleObjectService = ms.getServiceByType(bundleType) ;  
    	            	String source = ms.getMigPathUpToOrgName(exportUuid) +File.separator + bundleObjectService.getMigationSubFoler(); 
    	            	String dest = ms.getMigPathUpToOrgName(uuid.toString()) +File.separator+ BundleObjectService.TransformedFoldername + File.separator + bundleObjectService.getMigationSubFoler();
    	            	TransformationResults transformationResults =  bundleObjectService.transformAll(source, dest, uuid ) ;
    	            	String serializePath = ms.getSerlizeProcessResultFileName(uuid.toString() ) ; 
    		       		Helper.serialize(serializePath ,transformationResults ) ; 
    	            } catch (Exception e) {
    	                e.printStackTrace();
    	            }
    	        }); 
    	    	thread.start() ; 
    	    	
    	    	
    	        // Process the request and return a response
    	        return new ResponseEntity<String>("{\"transformUUID\":\""+uuid+"\"}", HttpStatus.CREATED);
    			}
    			catch (Exception e) {
    				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    			}
    	    }

	@PostMapping("/apigee/infras/{infra}/orgs/{org}/migrate/load/{bundleType}/process/{sourceProcessId}")
    public ResponseEntity<String> loadAndDeployAll(
    		@RequestHeader(value = "partner" , required = false )   String partner,
            @RequestHeader(value = "customer", required = false )  String customer,
            @RequestHeader(value = "migrationBasePath" , required = false )   String migrationBasePath,
            @PathVariable("infra")  String infra,
            @PathVariable("org") String org,
            @PathVariable("bundleType") String bundleType,
            @PathVariable("sourceProcessId")   String sourceProcessId, // Transform the result of this exportUuid
            @RequestHeader("Authorization") String authorizationHeader ) 
            {
    			try {
    			initialize(partner , customer , infra , org, authorizationHeader, migrationBasePath);  
    			UUID uuid = UUID.randomUUID(); 
    	    	Thread thread = new Thread(() -> {
    	            System.out.println("This is a new thread.");
    	            try {
    	            	ApigeeService apigeeService =  ms.getServiceByType(bundleType) ; 
    	            	if (apigeeService.getClass() == ProxyServices.class || apigeeService.getClass() == SharedFlowServices.class)
    	            	{  	 ((BundleObjectService) apigeeService).setDeployUponUpload(true) ; 	}
    	            	String upToOrgNamePath = ms.getMigPathUpToOrgName(sourceProcessId) ; 
    	            	String importFromFolder = upToOrgNamePath +File.separator+ BundleObjectService.TransformedFoldername + File.separator + apigeeService.getMigationSubFoler() ;
    	            	
    	            	LoadResults importResults =  apigeeService.importAll( importFromFolder, uuid ) ;
    	            	String serializePath = ms.getSerlizeProcessResultFileName(uuid.toString()) ; 
    		       		Helper.serialize(serializePath ,importResults ) ; 
    	            } catch (Exception e) {
    	                e.printStackTrace();
    	            }
    	        }); 
    	    	thread.start() ; 

    	    	return new ResponseEntity<String>("{\"loadUUID\":\""+uuid+"\"}", HttpStatus.CREATED);
    			}
    			catch (Exception e) {
    				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    			}
    	    }
	
	@GetMapping("/apigee/infras/{infra}/orgs/{org}/proesses/{processUuid}/logs")
    public ResponseEntity<String> getProcessResults(
    		@RequestHeader(value = "partner" , required = false )   String partner,
            @RequestHeader(value = "customer", required = false )  String customer,
            @RequestHeader(value = "migrationBasePath" , required = false )   String migrationBasePath,
            @PathVariable("infra")  String infra,
            @PathVariable("org") String org,
            @PathVariable("processUuid")   String processUuid, // Transform the result of this exportUuid
            @RequestHeader("Authorization") String authorizationHeader ) 
         
	{
		try {
			initialize(partner , customer , infra , org, authorizationHeader, migrationBasePath);
    		String processResultsFileName =    ms.getSerlizeProcessResultFileName(processUuid) ;
    		Object obj = Helper.deSerializeObject(processResultsFileName); 
    		
    		return buildJsonResponse(Helper.mapObjectToJsonStr(obj) , HttpStatus.OK) ;
    		
    		}
		catch (FileNotFoundException ex  )
		{
			return new ResponseEntity<String>("{ \"Error\" : \"Process "+ processUuid +" Not Found Or Still in Progress \" }", HttpStatus.INTERNAL_SERVER_ERROR);
		}
    	catch (Exception e) {
    		return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    	}
	}
	
	@GetMapping("/apigee/infras/{infra}/orgs/{org}/processes/{loadUuid}/deployHistory")
    public ResponseEntity<String> getDeployStatus(
    		@RequestHeader(value = "partner" , required = false )   String partner,
            @RequestHeader(value = "customer", required = false )  String customer,
            @RequestHeader(value = "migrationBasePath" , required = false )   String migrationBasePath,
            @PathVariable("infra")  String infra,
            @PathVariable("org") String org,
            @PathVariable("loadUuid")   String loadUuid, // Transform the result of this exportUuid
            @RequestHeader("Authorization") String authorizationHeader ) 
         
	{
		try {
			initialize(partner , customer , infra , org, authorizationHeader, migrationBasePath);
    		String processResultsFileName =    ms.getSerlizeDeplyStateFileName(loadUuid) ;
    		DeploymentsStatus ds = (DeploymentsStatus) Helper.deSerializeObject(processResultsFileName); 
   	        return buildJsonResponse(ds.toJsonString() , HttpStatus.OK) ; 
    		}
		catch (FileNotFoundException ex  )
		{
			return new ResponseEntity<String>("{ \"Error\" : \"Process "+ loadUuid +" Not Found Or Still in Progress \" }", HttpStatus.INTERNAL_SERVER_ERROR);
		}
    	catch (Exception e) {
    		return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    	}
	}
	
	/**
	 * Transforms a given APigee Bundle File 
	 */
	@PostMapping("/apigee/migrate/transform/{bundleType}")
    public ResponseEntity<?>  transformBundle( 	@RequestParam("zipBundle") MultipartFile zipBundle ,  
    											@RequestParam("transformers") String transformers ,
    											@PathVariable("bundleType") String bundleType
    										 ) throws IOException 
	{
    	try 
    	{
	    	String sourceFolder= "C:\\temp\\source" ; 
	    	File originalFile = new File(sourceFolder + File.separator +zipBundle.getOriginalFilename());
	 	    zipBundle.transferTo(originalFile);
	        
	        String destFolder= "C:\\temp\\dest" ;
	        Class<? extends ApigeeObjectTransformer> clazz = (bundleType.equalsIgnoreCase("apis"))?  ProxyTransformer.class : SharedflowTransformer.class ;  
	        
	        ArrayList<ApigeeObjectTransformer> transformersObj = buildTransformers(clazz , transformers) ; 
	        
			TransformationResults trs = BundleObjectService.transformBundleObject(sourceFolder+File.separator + originalFile.getName(),  destFolder  , transformersObj ) ;
			TransformationResults failedResults = trs.filterFailed(true); 
			if (failedResults.size() > 0 )
			{
				throw new Exception("Transformation Error : " + failedResults.get(0).getError()) ; 
			}
			
	        File transformedFile = new File(destFolder+File.separator + originalFile.getName()) ; 
	        
	        byte[] processedFileContent = Files.readAllBytes(transformedFile.toPath());
	
	        // Clean up temporary files
	        originalFile.delete();
	        transformedFile.delete();
	
	        return ResponseEntity.ok()
	                .contentType(MediaType.APPLICATION_OCTET_STREAM)
	                .body(processedFileContent);
    	}
    	catch (Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	private <T extends ApigeeObjectTransformer> ArrayList<ApigeeObjectTransformer> buildTransformers(Class<T> type , String transformers) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, FileNotFoundException, IOException
	{
		Gson gson = new Gson();
		TransformersConfig transformersConfig = gson.fromJson(transformers, TransformersConfig.class );  
		ArrayList<ApigeeObjectTransformer> result = new ArrayList<ApigeeObjectTransformer>(); 
		for (TransformerConfig tr :  transformersConfig.getTransformers() )
		{
			String transformerClass = tr.getImplClass(); 
			String enabled = tr.getEnabled(); 
			if (enabled.equalsIgnoreCase("false")) continue; 
			Class<?> cls = Class.forName(transformerClass);
			if (type.isAssignableFrom(cls))
			{
				java.lang.reflect.Constructor<?> cons = cls.getDeclaredConstructor();
				ApigeeObjectTransformer obj = (ApigeeObjectTransformer) cons.newInstance();
	
				for (Attribute att :  tr.getAttributes())
				{
					Field field = cls.getDeclaredField(att.getName());
					field.setAccessible(true);
					field.set(obj, att.getValue());
				}
				result.add(obj);
			}
		}
		
		return result ; 
	}
	/*
	@GetMapping("/apigee/infras/{infra}/orgs/{org}/{bundleType}")
    public ResponseEntity<String> ListOrgProxiesCreationDates(
    		@RequestHeader("partner")   String partner,
            @RequestHeader("customer")  String customer,
            @PathVariable("infra")  String infra,
            @PathVariable("org") String org,
            @PathVariable("bundleType") String bundleType,
            @RequestHeader("Authorization") String authorizationHeader ) 
         
	{
		HashMap<String , String > result = new HashMap<String , String > () ; 
		try {
			initialize(partner , customer , infra , org, authorizationHeader);
			System.out.println("=============Processing Org :" + org +"===============" );
			 ProxyServices bundleService =   (ProxyServices) ms.getServiceByType(bundleType) ;
    		ArrayList<String> allNames = bundleService.getAllBundledObjectNameList() ; 
    		for (String proxyName : allNames )
    		{	
    			System.out.println("Processing Proxy :" + proxyName );
    			//	--------------------Method 1 -------
    			Proxy  proxy = (Proxy) bundleService.getRevisionedObject(proxyName) ; 
    			Date createdDateTime = new java.util.Date(proxy.getMetaData().getCreatedAt()) ; 
    			result.put(proxyName, createdDateTime.toString()) ; 
    			   			
    		}
    		System.out.println("=============End Processing Org :" + org +"===============" );
    		
   	        return buildJsonResponse(Helper.mapObjectToJsonStr(result) , HttpStatus.OK) ;  
    		}
		
    	catch (Exception e) {
    		return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    	}
	}
	*/
	@GetMapping("/apigee/infras/{infra}/orgs/{org}/{objectType}")
    public ResponseEntity<String> ListObjects(
    		@RequestHeader(value = "partner" , required = false )   String partner,
            @RequestHeader(value = "customer", required = false )  String customer,
            @RequestHeader(value = "migrationBasePath" , required = false )   String migrationBasePath,
            @PathVariable("infra")  String infra,
            @PathVariable("org") String org,
            @PathVariable("objectType") String objectType,
            @RequestHeader("Authorization") String authorizationHeader ) 
         
	{
		HashMap<String , String > result = new HashMap<String , String > () ; 
		try {
			initialize(partner , customer , infra , org, authorizationHeader, migrationBasePath);
			System.out.println("=============Processing Org :" + org +"===============" );
			ApigeeService apigeeService =  ms.getServiceByType(objectType) ;
    		ArrayList<String> allNames = apigeeService.getAllResources() ; 
    		for (String objectName : allNames )
    		{	
    			System.out.println("Processing Object :" + objectName );
    			ApigeeComman  apigeeComman =  apigeeService.getResource(objectName , Helper.mapObjectTypeToClass(objectType)) ; 
    			if (apigeeComman instanceof Proxy) 
    			{
    				Proxy  proxy = (Proxy) apigeeComman ; 
        			Date createdDateTime = new java.util.Date(proxy.getMetaData().getCreatedAt()) ; 
        			result.put(objectName, createdDateTime.toString()) ;
    			}
    			else 
    			{
    				result.put(objectName, apigeeComman.toJsonString()) ;
    			}
    			   			
    		}
    		
   	        return buildJsonResponse(Helper.mapObjectToJsonStr(result) , HttpStatus.OK) ;  
    		}
		
    	catch (Exception e) {
    		return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    	}
	}
	
	@PutMapping("/apigee/infras/{infra}/orgs/{org}/products/processes/{operation}/wildCardScopes")
    public ResponseEntity<String> updateLegacyProductScopes(
    		@RequestHeader(value = "partner" , required = false )   String partner,
            @RequestHeader(value = "customer", required = false )  String customer,
            @RequestHeader(value = "migrationBasePath" , required = false )   String migrationBasePath,
            @PathVariable("infra")  	String infra,
            @PathVariable("org") 		String org,
            @PathVariable("operation")  String operation,
            @RequestHeader("Authorization") String authorizationHeader ) 
	{
		HashMap<String , String > result = new HashMap<String , String > () ; 
		try {
			initialize(partner , customer , infra , org, authorizationHeader, migrationBasePath);
			ApigeeService apigeeService =  ms.getServiceByType("products") ;
    		ArrayList<String> allProductsNames = apigeeService.getAllResources() ; 
    		for (String productName : allProductsNames )
    		{	
    			ApigeeComman  apigeeComman =  apigeeService.getResource(productName , Helper.mapObjectTypeToClass("products")) ; 
    			Product  product = (Product) apigeeComman ;
    			List<String> scopes = product.getScopes() ; 
    			List<String> proxies = product.getProxies(); 
    			boolean autoGeneratedProduct = false ;
    			try {
    				UUID.fromString(product.getName()) ; 
    				autoGeneratedProduct = true ; 
    			}
    			catch (Exception e) {}
    			if (! autoGeneratedProduct) // Start Updating the legacy products 
    			{
    				String productJsonStrBeforeUpdate = product.toJsonString();
    				System.out.print( "Processing Product : " + productName  );
    				for (String proxy : proxies)
	    			{
	    				String proxyAllFlowsScope = proxy + "."+ Helper.WILD_CARD_OAUTHS_COPE  ;
	    				if(operation.equalsIgnoreCase("add"))
	    				{if (! scopes.contains(proxyAllFlowsScope)) scopes.add(proxyAllFlowsScope);	}
	    				else if (operation.equalsIgnoreCase("add") )
	    				{scopes.remove(proxyAllFlowsScope); 	}
	    			}
	    			String productJsonStr = product.toJsonString(); 
	    			String path = "/v1/organizations/"+ms.getOrgName()+"/apiproducts/"+product.getUniqueId() ; 
	    			try { Thread.sleep(1000); // 1000 milliseconds = 1 second  To Allow for Service Quota Validation 
	    			} catch (InterruptedException e) { e.printStackTrace(); }
	    			HttpResponse<String> response = ms.getPutHttpResponse(path,  productJsonStr , "application/json") ; 
	    			result.put(productName, response.toString()) ;
    			}
   			}
   		
   	        return buildJsonResponse(Helper.mapObjectToJsonStr(result) , HttpStatus.OK) ;  
    		}
		
    	catch (Exception e) {
    		return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    	}
	}
	
	@PostMapping("/apigee/infras/{infra}/orgs/{org}/check/proxy/docWithoutService")
    public ResponseEntity<String> checkConsistancy(
    		@RequestHeader(value = "partner" , required = false )   String partner,
            @RequestHeader(value = "customer", required = false )  String customer,
            @RequestHeader(value = "migrationBasePath" , required = false )   String migrationBasePath,
            @PathVariable("infra") String infra,
            @PathVariable("org") String org,
            @RequestBody() String ObjectsNameListStr ,  
            @RequestHeader("Authorization")  String authorizationHeader
         
    )  {
		try {
		initialize(partner , customer , infra , org, authorizationHeader , migrationBasePath); 
    	UUID uuid = UUID.randomUUID(); 
    	Gson gson = new Gson(); 
    	ArrayList<String> ObjectsNameList = (ArrayList<String>) gson.fromJson(ObjectsNameListStr , ArrayList.class) ; 
    	Thread thread = new Thread(() -> {
    	
    		ArrayList<DocWithoutFlow> results = new ArrayList<DocWithoutFlow>();
        try {
    		BundleObjectService bundleObjectService = (BundleObjectService) ms.getServiceByType("apis") ;
        	for (String proxyname : ObjectsNameList )
	        	{
	                System.out.println("Starting the Checking Proxy : " + proxyname );
	                String destPath = ms.getMigPathUpToOrgName(uuid.toString()); 
	                ExportResults exportResults = bundleObjectService.exportResource(proxyname , destPath) ;
	                
	                for (ProcessResult exportResult : exportResults)
	                {
	                	String bundleFilePath = exportResult.getDestination() ;
	                	String revision = ((ExportResult)exportResult).getRevision() +"" ; 
	                	ProxyBundleParser proxyBundle = new ProxyBundleParser(bundleFilePath + proxyname + ".zip") ;
	                	HashMap<OasOperation, Flow> revisionOperationsFlows = proxyBundle.checkOpenApiConsistancy(false, false) ; 
	                	for (Entry<OasOperation, Flow> operationsFlows : revisionOperationsFlows.entrySet())
	                	{
	                		Flow flow = operationsFlows.getValue() ;
	                		OasOperation  oasOperation = operationsFlows.getKey() ; 
	                		if (flow==null)
	                		{
	                			DocWithoutFlow zz = new  DocWithoutFlow(proxyname , revision , oasOperation.getPath() , oasOperation.getVerb()) ; 
		                		//HashMap<String , String> xx = new HashMap<String , String > ();  
		                		//xx.put(operationsFlows.getKey().getPath() , operationsFlows.getKey().getVerb()) ;
		                		//HashMap<String , HashMap<String, String>> yy = new HashMap<String , HashMap<String, String>>();
		                		//yy.put(revision, xx) ; 
	                			results.add(zz);    
	                		}					    ; 
	                	}
	                	
	                }
	                
	                
	        	}

            
       		String serializePath = ms.getSerlizeProcessResultFileName(uuid.toString()) ; 
       		Helper.serialize(serializePath ,results ) ;
       		
        	} catch (Exception e) {
             e.printStackTrace();
        	}
        }); 
    	thread.start() ; 
        // Process the request and return a response
        return new ResponseEntity<String>("{\"processUUID\":\""+uuid+"\"}", HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    } 
    
  
}

