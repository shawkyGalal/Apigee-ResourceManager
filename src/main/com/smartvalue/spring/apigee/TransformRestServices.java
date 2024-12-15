package com.smartvalue.spring.apigee;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.smartvalue.apigee.configuration.transformer.auto.Attribute;
import com.smartvalue.apigee.configuration.transformer.auto.TransformerConfig;
import com.smartvalue.apigee.configuration.transformer.auto.TransformersConfig;
import com.smartvalue.apigee.configuration.transformer.auto.ValidatorConfig;
import com.smartvalue.apigee.configuration.transformer.auto.ValidatorsConfig;
import com.smartvalue.apigee.migration.ProcessResults;
import com.smartvalue.apigee.migration.transformers.ApigeeObjectTransformer;
import com.smartvalue.apigee.migration.transformers.TransformationResults;
import com.smartvalue.apigee.migration.transformers.proxy.ProxyTransformer;
import com.smartvalue.apigee.migration.transformers.sharedflows.SharedflowTransformer;
import com.smartvalue.apigee.resourceManager.helpers.Helper;
import com.smartvalue.apigee.rest.schema.ApigeeService;
import com.smartvalue.apigee.rest.schema.BundleObjectService;
import com.smartvalue.apigee.rest.schema.proxy.Proxy;
import com.smartvalue.apigee.rest.schema.proxyDeployment.ProxyDeployment;
import com.smartvalue.apigee.rest.schema.proxyDeployment.auto.Environment;
import com.smartvalue.apigee.rest.schema.proxyDeployment.auto.Revision;
import com.smartvalue.apigee.rest.schema.proxyRevision.ProxyRevision;
import com.smartvalue.apigee.validators.ApigeeValidator;
import com.smartvalue.apigee.validators.ProxyValidator;
import com.smartvalue.apigee.validators.SharedFlowValidator;
import com.smartvalue.spring.ThreadStatusManager;

@RestController
public class TransformRestServices extends RestServices {

	

	/**
	 * Transforms a given APigee Bundle File 
	 */
	@PostMapping("/apigee/migrate/transform/{bundleType}")
    public ResponseEntity<?>  transformBundle( 	
    		@RequestParam MultipartFile zipBundle ,  
    		@RequestParam String transformers ,
    		@PathVariable String bundleType	,
    		@RequestParam (required = false ) 	String validators ,
    		@RequestHeader(required = false )   String validateBeforTransform 
    		)  
	{
    	try 
    	{
			
	    	String sourceFolder= Helper.TEMP_FOLDER ; 
	    	File originalFile = new File(sourceFolder + File.separator +zipBundle.getOriginalFilename());
	 	    zipBundle.transferTo(originalFile);
	        
	 	   
	   		if (validateBeforTransform!= null & validateBeforTransform.equalsIgnoreCase("true"))
	   		{
	   			Class<? extends ApigeeValidator> valoidatorClazz = (bundleType.equalsIgnoreCase("apis"))?  ProxyValidator.class : SharedFlowValidator.class ;
	   			
	   	        ArrayList<ApigeeValidator> validatorObjs = ValidateProxy.buildValidators(valoidatorClazz , validators) ; 
	   	        
	   			ProcessResults trs = BundleObjectService.validateBundleObject(sourceFolder+File.separator + originalFile.getName(), validatorObjs ) ;
	   			ProcessResults failedResults = trs.filterFailed(true); 
	   			if (failedResults.size() > 0 )
	   			{
	   				throw new Exception("Validation Error : " + failedResults.get(0).getError()) ; 
	   			}
	   		}
   		
	 	    
	        String destFolder= Helper.TEMP_FOLDER ;
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
	
	private static <T extends ApigeeObjectTransformer> ArrayList<ApigeeObjectTransformer> buildTransformers(Class<T> type , String transformers) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, FileNotFoundException, IOException
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
	

	
	@PostMapping({"/apigee/infras/{infra}/orgs/{org}/migrate/transform/{bundleType}/process/{exportUuid}"
		, "/apigee/infras/{infra}/orgs/{org}/envs/{env}/migrate/transform/{bundleType}/process/{exportUuid}"
	})
    public ResponseEntity<String> transformAllProxies(
    		@RequestHeader(required = false )   String partner,
            @RequestHeader(required = false )  String customer,
            @RequestHeader(required = false )   String migrationBasePath,
            @PathVariable  String infra,
            @PathVariable String org,
            @PathVariable(required = false) String env,
            @PathVariable String bundleType,
            @PathVariable   String exportUuid, // Transform the result of this exportUuid
            @RequestHeader("Authorization") String authorizationHeader ) 
            {
    			try {
    			initialize(partner , customer , infra , org, authorizationHeader, migrationBasePath);  
    			UUID uuid = UUID.randomUUID(); 
    	    	Thread thread = new Thread(() -> {
    	            System.out.println("This is a new thread.");
    	            try {
    	            	ApigeeService bundleObjectService = ms.getServiceByType(bundleType, env ) ;  
    	            	String source = ms.getMigPathUpToOrgName(exportUuid) +File.separator + bundleObjectService.getMigationSubFoler(); 
    	            	String dest = ms.getMigPathUpToOrgName(uuid.toString()) +File.separator+ BundleObjectService.TransformedFoldername + File.separator + bundleObjectService.getMigationSubFoler();
    	            	TransformationResults transformationResults =  bundleObjectService.transformAll(source, dest, uuid ) ;
    	            	String serializePath = ms.getSerlizeProcessResultFileName(uuid.toString() ) ; 
    		       		Helper.serialize(serializePath ,transformationResults ) ; 
    	            } catch (Exception e) {
    	                e.printStackTrace();
    	            }
    	        }); 
    	    	ThreadStatusManager.startThread(thread, uuid) ;
    	    	
    	    	
    	        // Process the request and return a response
    	        return new ResponseEntity<String>("{\"transformUUID\":\""+uuid+"\"}", HttpStatus.CREATED);
    			}
    			catch (Exception e) {
    				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    			}
    	    }
	
	
}

