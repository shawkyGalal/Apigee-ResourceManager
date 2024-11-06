package com.smartvalue.spring.apigee;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.smartvalue.apigee.configuration.AppConfig;
import com.smartvalue.apigee.configuration.AppConfigFactory;
import com.smartvalue.apigee.configuration.infra.Infra;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.configuration.transformer.auto.Attribute;
import com.smartvalue.apigee.configuration.transformer.auto.TransformerConfig;
import com.smartvalue.apigee.configuration.transformer.auto.TransformersConfig;
import com.smartvalue.apigee.migration.ProcessResults;
import com.smartvalue.apigee.migration.export.ExportResults;
import com.smartvalue.apigee.migration.transformers.ApigeeObjectTransformer;
import com.smartvalue.apigee.migration.transformers.TransformationResults;
import com.smartvalue.apigee.migration.transformers.proxy.ProxyTransformer;
import com.smartvalue.apigee.migration.transformers.sharedflows.SharedflowTransformer;
import com.smartvalue.apigee.resourceManager.helpers.Helper;
import com.smartvalue.apigee.rest.schema.BundleObjectService;

@RestController
public class EtlRestServices {

	ManagementServer  ms ;
	public AppConfig getAppConfig() throws FileNotFoundException, IOException {
		
		return AppConfigFactory.create( "config.json" , AppConfig.class);
	}
	
	private void initialize(String partner , String customer , String infra , String org , String authorizationHeader) throws Exception
	{
		AppConfig ac = getAppConfig();  
		Infra infraObject = ac.getInfra(partner , customer , infra) ;
    	ms = infraObject.getManagementServer(infraObject.getRegions().get(0).getName()) ;
    	ms.setOrgName(org) ; 
    	ms.setAuthorizationHeader(authorizationHeader ) ;
    	ms.getAllOrgNames(); 
	}
	
	private ResponseEntity<String> buildJsonResponse(String jsonString )
	{
		 HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Type", "application/json");
 	    return new ResponseEntity<String>(jsonString, headers , HttpStatus.CREATED);
	}

	@GetMapping("/apigee/migrate/infras/{infra}/orgs/{org}/export/apis/")
    public ResponseEntity<String> listAllExports(
    		@RequestHeader("partner") String partner,
            @RequestHeader("customer") String customer,
    		@PathVariable("infra") String infra,
            @PathVariable("org") String org , 
            @RequestHeader("Authorization")  String authorizationHeader
            )
    {
		try {
		initialize(partner , customer , infra , org, authorizationHeader); 
		System.out.println("");
		return new ResponseEntity<String>("Partner =" + partner + ", customer = " + customer +", infra = " + infra, HttpStatus.ACCEPTED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
    }
	
		
	@GetMapping("/apigee/migrate/infras/{infra}/orgs/{org}/export/apis/{transformUuid}")
    public ResponseEntity<String> getTransformDetails(
    		@RequestHeader("partner") String partner,
            @RequestHeader("customer") String customer,
    		@PathVariable("infra") String infra,
            @PathVariable("org") String org , 
            @PathVariable("transformUuid")   String transformUuid,
            @RequestHeader("Authorization")  String authorizationHeader
            )
    {
		try {
			initialize(partner , customer , infra , org, authorizationHeader); 
			System.out.println("");
			return new ResponseEntity<String>("Partner =" + partner + ", customer = " + customer +", infra = " + infra, HttpStatus.ACCEPTED);
			}
			catch (Exception e) {
				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
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
	@PostMapping("/apigee/migrate/infras/{infra}/orgs/{org}/etl/{bundleType}/{objectName}")
    public ResponseEntity<String> etlBundle(
    		@RequestHeader("partner") String partner,
            @RequestHeader("customer")   String customer,
            @PathVariable("infra") String infra,
            @PathVariable("org") String org,
            @PathVariable("bundleType") String bundleType,
            @PathVariable("objectName") String objectName,
            @RequestHeader("Authorization")  String authorizationHeader
         
    )  {
		try {
		initialize(partner , customer , infra , org, authorizationHeader); 
    	String uuid = UUID.randomUUID().toString(); 
    	Thread thread = new Thread(() -> {
        System.out.println("Starting the complete ETL Process on " + objectName );
        try {
        		BundleObjectService bundleObjectService = ms.getBundleServiceByType(bundleType) ; 
	       		ProcessResults eTLResult = bundleObjectService.performETL(bundleType , objectName , uuid);
	       		String serializePath = ms.getSerlizeProcessResultFileName(uuid) ; 
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
	
	@PostMapping("/apigee/migrate/infras/{infra}/orgs/{org}/rollback/{bundleType}/{objectName}/{exportUUID}")
    public ResponseEntity<String> rollBackSingleEtlBundle(
    		@RequestHeader("partner") String partner,
            @RequestHeader("customer")   String customer,
            @PathVariable("infra") String infra,
            @PathVariable("org") String org,
            @PathVariable("bundleType") String bundleType,
            @PathVariable("exportUUID") String exportUUID,
            @PathVariable("objectName") String objectName,
            @RequestHeader("Authorization")  String authorizationHeader
    )  {
		try {
		initialize(partner , customer , infra , org, authorizationHeader);  
    	String uuid = UUID.randomUUID().toString(); 
    	Thread thread = new Thread(() -> 
	    	{
		        try {
		        		BundleObjectService bundleObjectService = ms.getBundleServiceByType(bundleType) ; 
		        		String deplyStateFileName = ms.getSerlizeDeplyStateFileName(exportUUID) ; 
			       		ProcessResults eTLResult = bundleObjectService.rollBackObjectToLastSerializedDeployStatus(objectName , deplyStateFileName);
			       		String serializePath = ms.getSerlizeProcessResultFileName(uuid ) ; 
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
	@PostMapping("/apigee/migrate/infras/{infra}/orgs/{org}/export/{bundleType}/")
    public ResponseEntity<String> exportAll(
    		@RequestHeader("partner") String partner,
            @RequestHeader("customer")   String customer,
            @PathVariable("infra") String infra,
            @PathVariable("org") String org,
            @PathVariable("bundleType") String bundleType,
            @RequestHeader("Authorization")  String authorizationHeader
         
    )  {
		try {
			initialize(partner , customer , infra , org, authorizationHeader); 
	    	String uuid = UUID.randomUUID().toString(); 
	    	Thread thread = new Thread(() -> {
            try {
	            	BundleObjectService bundleObjectService = ms.getBundleServiceByType(bundleType) ; 
	            	ExportResults result = bundleObjectService.exportAll( ms.getMigPathUpToOrgName(uuid)+"\\"+ bundleObjectService.getMigationSubFoler() ) ;
	            	String serializePath = ms.getSerlizeProcessResultFileName(uuid) ; 
	            	Helper.serialize(serializePath ,result ) ; 
	           	
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
	
	
	@PostMapping("/apigee/migrate/infras/{infra}/orgs/{org}/transform/{bundleType}/process/{exportUuid}")
    public ResponseEntity<String> transformAllProxies(
    		@RequestHeader("partner")   String partner,
            @RequestHeader("customer")  String customer,
            @PathVariable("infra")  String infra,
            @PathVariable("org") String org,
            @PathVariable("bundleType") String bundleType,
            @PathVariable("exportUuid")   String exportUuid, // Transform the result of this exportUuid
            @RequestHeader("Authorization") String authorizationHeader ) 
         
            {
    			try {
    			initialize(partner , customer , infra , org, authorizationHeader);  
    	    	
    	    	Thread thread = new Thread(() -> {
    	            System.out.println("This is a new thread.");
    	            try {
    	            	BundleObjectService bundleObjectService = ms.getBundleServiceByType(bundleType) ;  
    	            	String upToOrgNamePath = ms.getMigPathUpToOrgName(exportUuid) ; 
    	            	TransformationResults transformationResults =  bundleObjectService.transformAll(upToOrgNamePath +"\\" + bundleObjectService.getMigationSubFoler()  
    	            													  				  , upToOrgNamePath +"\\"+ BundleObjectService.TransformedFoldername + "\\" + bundleObjectService.getMigationSubFoler() 	) ;
    	            	String serializePath = ms.getSerlizeProcessResultFileName(exportUuid ) ; 
    		       		Helper.serialize(serializePath ,transformationResults ) ; 
    	            } catch (Exception e) {
    	                e.printStackTrace();
    	            }
    	        }); 
    	    	thread.start() ; 
    	    	
    	    	
    	        // Process the request and return a response
    	        return new ResponseEntity<String>("{\"processUUID\":\""+exportUuid+"\"}", HttpStatus.CREATED);
    			}
    			catch (Exception e) {
    				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    			}
    	    }

	@GetMapping("/apigee/migrate/infras/{infra}/orgs/{org}/proesses/{processUuid}")
    public ResponseEntity<String> getProcessResults(
    		@RequestHeader("partner")   String partner,
            @RequestHeader("customer")  String customer,
            @PathVariable("infra")  String infra,
            @PathVariable("org") String org,
            @PathVariable("processUuid")   String processUuid, // Transform the result of this exportUuid
            @RequestHeader("Authorization") String authorizationHeader ) 
         
	{
		try {
    		initialize(partner , customer , infra , org, authorizationHeader); 
    		String processResultsFileName =    ms.getSerlizeProcessResultFileName(processUuid) ;
    		ProcessResults pr = (ProcessResults) Helper.deSerializeObject(processResultsFileName); 
   	        return buildJsonResponse(pr.toJsonString()) ; 
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
	    	File originalFile = new File(sourceFolder + "\\" +zipBundle.getOriginalFilename());
	 	    zipBundle.transferTo(originalFile);
	        
	        String destFolder= "C:\\temp\\dest" ;
	        Class<? extends ApigeeObjectTransformer> clazz = (bundleType.equalsIgnoreCase("apis"))?  ProxyTransformer.class : SharedflowTransformer.class ;  
	        
	        ArrayList<ApigeeObjectTransformer> transformersObj = buildTransformers(clazz , transformers) ; 
	        
			TransformationResults trs = BundleObjectService.transformBundleObject(sourceFolder+"\\" + originalFile.getName(),  destFolder  , transformersObj ) ;
			TransformationResults failedResults = trs.filterFailed(true); 
			if (failedResults.size() > 0 )
			{
				throw new Exception("Transformation Error : " + failedResults.get(0).getError()) ; 
			}
			
	        File transformedFile = new File(destFolder+"\\" + originalFile.getName()) ; 
	        
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
	
	 
	
    
  
}

