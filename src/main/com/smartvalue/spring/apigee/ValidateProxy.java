package com.smartvalue.spring.apigee;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.migration.ProcessResult;
import com.smartvalue.apigee.migration.ProcessResults;
import com.smartvalue.apigee.migration.export.ExportResults;
import com.smartvalue.apigee.resourceManager.helpers.Helper;
import com.smartvalue.apigee.rest.schema.ApigeeService;
import com.smartvalue.apigee.rest.schema.BundleObjectService;
import com.smartvalue.apigee.rest.schema.proxy.Proxy;
import com.smartvalue.apigee.rest.schema.proxyDeployment.auto.Revision;
import com.smartvalue.apigee.rest.schema.proxyRevision.ProxyRevision;
import com.smartvalue.apigee.validators.ConditionalFlowHookValidator;
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

}

