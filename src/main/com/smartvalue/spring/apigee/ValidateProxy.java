package com.smartvalue.spring.apigee;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.smartvalue.apigee.configuration.transformer.auto.Attribute;
import com.smartvalue.apigee.configuration.transformer.auto.ValidatorConfig;
import com.smartvalue.apigee.configuration.transformer.auto.ValidatorsConfig;
import com.smartvalue.apigee.migration.ProcessResult;
import com.smartvalue.apigee.migration.ProcessResults;
import com.smartvalue.apigee.resourceManager.helpers.Helper;
import com.smartvalue.apigee.rest.schema.BundleObjectService;
import com.smartvalue.apigee.rest.schema.proxyDeployment.auto.Revision;
import com.smartvalue.apigee.rest.schema.proxyRevision.ProxyRevision;
import com.smartvalue.apigee.validators.ApigeeValidator;
import com.smartvalue.apigee.validators.ConditionalFlowHookValidator;
import com.smartvalue.apigee.validators.ProxyValidator;
import com.smartvalue.apigee.validators.SharedFlowValidator;
import com.smartvalue.spring.ThreadStatusManager;

@RestController
public class ValidateProxy extends RestServices {

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
	@PostMapping( value = { "/apigee/infras/{infra}/orgs/{org}/envs/{env}/validators/conditionalFlowHook/{bundleType}"
	})
    public ResponseEntity<String> validate(
    		@RequestHeader(required = false )   String partner,
            @RequestHeader(required = false )   String customer,
            @RequestHeader(required = false )   String migrationBasePath,
            @RequestHeader(required = false )   String env,
            @PathVariable String infra,
            @PathVariable String bundleType,
            @PathVariable String org,
            @RequestHeader("Authorization")  String authorizationHeader
         
    )  {
		try {
			initialize(partner , customer , infra , org, authorizationHeader , migrationBasePath); 
	    	UUID uuid = UUID.randomUUID(); 
	    	Thread thread = new Thread(() -> {
    		ProcessResults results =  new ProcessResults("Validate All Deployed Proxies" , uuid) ;
            
	            	BundleObjectService bundleObjectService = (BundleObjectService) ms.getServiceByType(bundleType , env ) ;
	            	ConditionalFlowHookValidator cfhv = new ConditionalFlowHookValidator(); 
	            	ArrayList<String> allBundleObjectsNames = null ; 
	            	try {
	            	allBundleObjectsNames = bundleObjectService.getAllResources();
	            	} catch (Exception e) {
		                e.printStackTrace();
		                results.add(new ProcessResult(e));
		            }
	            	if (allBundleObjectsNames != null)
	            	{
		            	for (String bundleObjectName : allBundleObjectsNames)
		            	{
		            		try {
			            		List<Revision> revisions = bundleObjectService.getEnvDeployedRevisions(bundleObjectName, env);
			            		for (Revision revision : revisions)
			            		{
			            			ProxyRevision proxyRevision = bundleObjectService.getBundleObjectRevision(bundleObjectName, revision.getName()) ;
			            			results = cfhv.validate(proxyRevision, uuid);
			            		}
		            		}catch (Exception e) {
		            			e.printStackTrace();
				                results.add(new ProcessResult(e)); 
							}
		            	}
		            	String serializePath = ms.getSerlizeProcessResultFileName(uuid.toString()) ; 
			       		try {
							Helper.serialize(serializePath ,results ) ;
						} catch (IOException e) {
							e.printStackTrace();
			                results.add(new ProcessResult(e)); 
						}
	            	}
	        }); 
	    	ThreadStatusManager.startThread(thread, uuid) ;
	    	
	        return new ResponseEntity<String>("{\"processUUID\":\""+uuid+"\"}", HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	/**
	 * Validates a given APigee Bundle File 
	 */
	@PostMapping("/apigee/migrate/validate/{bundleType}")
    public ResponseEntity<?>  validateBundle( 	
    		@RequestParam MultipartFile zipBundle ,  
    		@PathVariable String bundleType	,
    		@RequestParam String validators 
    		)  
	{
    	try 
    	{
	    	String sourceFolder= Helper.TEMP_FOLDER ; 
	    	File originalFile = new File(sourceFolder + File.separator +zipBundle.getOriginalFilename());
	 	    zipBundle.transferTo(originalFile);
	 	    ProcessResults prs ; 
   			Class<? extends ApigeeValidator> valoidatorClazz = (bundleType.equalsIgnoreCase("apis"))?  ProxyValidator.class : SharedFlowValidator.class ;
   	        ArrayList<ApigeeValidator> validatorObjs = buildValidators(valoidatorClazz , validators) ; 
   			prs = BundleObjectService.validateBundleObject(sourceFolder+File.separator + originalFile.getName(), validatorObjs ) ;
	   		return buildJsonResponse(Helper.mapObjectToJsonStr(prs.filterFailed(true)) , HttpStatus.OK) ;
    	}
    	catch (Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@PostMapping({"/apigee/infras/{infra}/orgs/{org}/migrate/validate/{bundleType}/{bundleName}"})
    public ResponseEntity<String> validateProxy(
    		@RequestHeader(required = false )   String partner,
            @RequestHeader(required = false )  String customer,
            @RequestHeader(required = false )   String migrationBasePath,
            @PathVariable  String infra,
            @PathVariable String org,
            @PathVariable(required = false) String env,
            @PathVariable String bundleType,
            @PathVariable String bundleName,
            @RequestHeader("Authorization") String authorizationHeader ) 
            {
				
   			try {
	    			initialize(partner , customer , infra , org, authorizationHeader, migrationBasePath);  
	    			UUID uuid = UUID.randomUUID(); 
	    			BundleObjectService bundleObjectService = (BundleObjectService) ms.getServiceByType(bundleType, env ) ;
	    			ProcessResults prs  = bundleObjectService.validateProxy(bundleName, uuid) ; 
	    	    	return buildJsonResponse(Helper.mapObjectToJsonStr(prs.filterFailed(true)) , HttpStatus.OK) ;
    			}
    			catch (Exception e) {
    				e.printStackTrace();
    				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    			}
    	    }
	
	static <T extends ApigeeValidator> ArrayList<ApigeeValidator> buildValidators(Class<T> type , String validators) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, FileNotFoundException, IOException
	{
		Gson gson = new Gson();
		ValidatorsConfig validatorsConfig = gson.fromJson(validators, ValidatorsConfig.class );  
		ArrayList<ApigeeValidator> result = new ArrayList<ApigeeValidator>(); 
		for (ValidatorConfig tr :  validatorsConfig.getValidators() )
		{
			String transformerClass = tr.getImplClass(); 
			String enabled = tr.getEnabled(); 
			if (enabled.equalsIgnoreCase("false")) continue; 
			Class<?> cls = Class.forName(transformerClass);
			if (type.isAssignableFrom(cls))
			{
				java.lang.reflect.Constructor<?> cons = cls.getDeclaredConstructor();
				ApigeeValidator obj = (ApigeeValidator) cons.newInstance();
	
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

