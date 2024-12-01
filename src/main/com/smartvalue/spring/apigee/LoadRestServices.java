package com.smartvalue.spring.apigee;

import java.io.File;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.smartvalue.apigee.migration.load.LoadResults;
import com.smartvalue.apigee.resourceManager.helpers.Helper;
import com.smartvalue.apigee.rest.schema.ApigeeService;
import com.smartvalue.apigee.rest.schema.BundleObjectService;
import com.smartvalue.apigee.rest.schema.proxy.ProxyServices;
import com.smartvalue.apigee.rest.schema.sharedFlow.SharedFlowServices;
import com.smartvalue.spring.ThreadStatusManager;

@RestController
public class LoadRestServices extends RestServices {

	@PostMapping({"/apigee/infras/{infra}/orgs/{org}/migrate/load/{bundleType}/process/{sourceProcessId}"
		, "/apigee/infras/{infra}/orgs/{org}/envs/{env}/migrate/load/{bundleType}/process/{sourceProcessId}"
	})
    public ResponseEntity<String> loadAndDeployAll(
    		@RequestHeader(required = false )   String partner,
            @RequestHeader(required = false )  String customer,
            @RequestHeader(required = false )   String migrationBasePath,
            @PathVariable  String infra,
            @PathVariable String org,
            @PathVariable(required = false) String env,
            @PathVariable String bundleType,
            @PathVariable   String sourceProcessId, // Transform the result of this exportUuid
            @RequestHeader("Authorization") String authorizationHeader ) 
            {
    			try {
    			initialize(partner , customer , infra , org, authorizationHeader, migrationBasePath);  
    			UUID uuid = UUID.randomUUID(); 
    	    	Thread thread = new Thread(() -> {
    	            System.out.println("This is a new thread.");
    	            try {
    	            	ApigeeService apigeeService =  ms.getServiceByType(bundleType, env ) ; 
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
    	    	ThreadStatusManager.startThread(thread, uuid) ; 

    	    	return new ResponseEntity<String>("{\"loadUUID\":\""+uuid+"\"}", HttpStatus.CREATED);
    			}
    			catch (Exception e) {
    				return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    			}
    	    }	
}

