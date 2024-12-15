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
import com.smartvalue.spring.ThreadStatusManager;

@RestController
public class EtlRestServices extends RestServices{

		    
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
	@PostMapping({"/apigee/infras/{infra}/orgs/{org}/migrate/etl/{bundleType}/{objectName}" 
		        , "/apigee/infras/{infra}/orgs/{org}/envs/{env}/migrate/etl/{bundleType}/{objectName}"  })
    public ResponseEntity<String> etlApigeeObject(
    		@RequestHeader(required = false )   String partner,
            @RequestHeader(required = false )   String customer,
            @RequestHeader(required = false )   String migrationBasePath,
            @PathVariable String infra,
            @PathVariable String org,
            @PathVariable(required = false) String env,
            @PathVariable String bundleType,
            @PathVariable String objectName,
            @RequestHeader("Authorization")  String authorizationHeader
         
    )  {
		try {
		initialize(partner , customer , infra , org, authorizationHeader , migrationBasePath); 
    	UUID uuid = UUID.randomUUID(); 
    	Thread thread = new Thread(() -> {
        System.out.println("Starting the complete ETL Process on " + objectName );
        try {
        		ApigeeService bundleObjectService = ms.getServiceByType(bundleType , env) ; 
	       		ProcessResults eTLResult = bundleObjectService.performETL(objectName , uuid);
	       		String serializePath = ms.getSerlizeProcessResultFileName(uuid.toString()) ; 
	       		Helper.serialize(serializePath ,eTLResult ) ; 
        	} catch (Exception e) {
             e.printStackTrace();
        	}
        }); 
    	ThreadStatusManager.startThread(thread, uuid) ;
        // Process the request and return a response
        return new ResponseEntity<String>("{\"processUUID\":\""+uuid+"\"}", HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@PostMapping({"/apigee/infras/{infra}/orgs/{org}/migrate/etl/{bundleType}", 
				"/apigee/infras/{infra}/orgs/{org}/envs/{env}/migrate/etl/{bundleType}"} )
    public ResponseEntity<String> etlSetOfObjects(
    		@RequestHeader( required = false )   String partner,
            @RequestHeader( required = false )  String customer,
            @RequestHeader( required = false )   String migrationBasePath,
            @PathVariable String infra,
            @PathVariable String org,
            @PathVariable( required = false) String env,
            @PathVariable String bundleType,
            @RequestBody  String ObjectsNameListStr ,  
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
    		ApigeeService bundleObjectService = ms.getServiceByType(bundleType , env) ;
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
    	ThreadStatusManager.startThread(thread, uuid) ; 
        // Process the request and return a response
        return new ResponseEntity<String>("{\"processUUID\":\""+uuid+"\"}", HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    } 
	
	
	
	
	

	
	
	
	
	
	
	@GetMapping("/apigee/infras/{infra}/orgs/{org}/{objectType}")
    public ResponseEntity<String> ListObjects(
    		@RequestHeader(required = false )   String partner,
            @RequestHeader(required = false )  String customer,
            @RequestHeader(required = false )   String migrationBasePath,
            @PathVariable String infra,
            @PathVariable String org,
            @PathVariable String objectType,
            @RequestHeader("Authorization") String authorizationHeader ) 
         
	{
		HashMap<String , String > result = new HashMap<String , String > () ; 
		try {
			initialize(partner , customer , infra , org, authorizationHeader, migrationBasePath);
			System.out.println("=============Processing Org :" + org +"===============" );
			ApigeeService apigeeService =  ms.getServiceByType(objectType, null) ;
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
	
	
	
	@PostMapping("/apigee/infras/{infra}/orgs/{org}/check/proxy/docWithoutService")
    public ResponseEntity<String> checkConsistancy(
    		@RequestHeader(required = false )   String partner,
            @RequestHeader(required = false )  String customer,
            @RequestHeader(required = false )   String migrationBasePath,
            @PathVariable String infra,
            @PathVariable String org,
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
    		BundleObjectService bundleObjectService = (BundleObjectService) ms.getServiceByType("apis" , null) ;
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
    	ThreadStatusManager.startThread(thread, uuid) ;
        // Process the request and return a response
        return new ResponseEntity<String>("{\"processUUID\":\""+uuid+"\"}", HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    } 
	
	@GetMapping("/apigee/infras/{infra}/orgs/{org}/processes/{loadUuid}/deployHistory")
    public ResponseEntity<String> getDeployStatus(
    		@RequestHeader(required = false )   String partner,
            @RequestHeader(required = false )  String customer,
            @RequestHeader(required = false )   String migrationBasePath,
            @PathVariable  String infra,
            @PathVariable String org,
            @PathVariable   String loadUuid, // Transform the result of this exportUuid
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
	
	@GetMapping("/apigee/infras/{infra}/orgs/{org}/proesses/{processUuid}/logs")
    public ResponseEntity<String> getProcessResults(
    		@RequestHeader(required = false )   String partner,
            @RequestHeader(required = false )  String customer,
            @RequestHeader(required = false )   String migrationBasePath,
            @PathVariable  String infra,
            @PathVariable String org,
            @PathVariable   String processUuid, // Transform the result of this exportUuid
            @RequestHeader("Authorization") String authorizationHeader ) 
         
	{
		try {
			Thread task = ThreadStatusManager.getThread(processUuid) ;
			if(task.isAlive()) {return new ResponseEntity<String>("{ \"Status\" :  \"Process "+ processUuid +" Still in Progress... \" }", HttpStatus.OK); }
			initialize(partner , customer , infra , org, authorizationHeader, migrationBasePath);
    		String processResultsFileName =    ms.getSerlizeProcessResultFileName(processUuid) ;
    		Object obj = Helper.deSerializeObject(processResultsFileName); 
    		return buildJsonResponse(Helper.mapObjectToJsonStr(obj) , HttpStatus.OK) ;
    		}
		catch (FileNotFoundException ex  )
		{
			return new ResponseEntity<String>("{ \"Error\" : \"Process "+ processUuid +" Not Found \" }", HttpStatus.INTERNAL_SERVER_ERROR);
		}
    	catch (Exception e) {
    		return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    	}
	}
 
}