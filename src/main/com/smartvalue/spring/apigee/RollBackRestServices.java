package com.smartvalue.spring.apigee;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.smartvalue.apigee.migration.ProcessResults;
import com.smartvalue.apigee.resourceManager.helpers.Helper;
import com.smartvalue.apigee.rest.schema.BundleObjectService;
import com.smartvalue.apigee.rest.schema.RollBackable;
import com.smartvalue.spring.ThreadStatusManager;

@RestController
public class RollBackRestServices extends RestServices {

	@PostMapping( { "/apigee/infras/{infra}/orgs/{org}/migrate/rollback/{bundleType}/deployProcessId/{deployUUID}" 
		, "/apigee/infras/{infra}/orgs/{org}/envs/{env}/migrate/rollback/{bundleType}/deployProcessId/{deployUUID}"  
	} )
    public ResponseEntity<String> rollBackAll(
    		@RequestHeader(required = false )   String partner,
            @RequestHeader(required = false )  String customer,
            @RequestHeader(required = false )   String migrationBasePath,
            @PathVariable String infra,
            @PathVariable String org,
            @PathVariable( required = false) String env,
            @PathVariable String bundleType,
            @PathVariable String deployUUID,
            @RequestHeader("Authorization")  String authorizationHeader
    )  {
		try {
		initialize(partner , customer , infra , org, authorizationHeader , migrationBasePath);  
    	UUID uuid = UUID.randomUUID(); 
    	Thread thread = new Thread(() -> 
	    	{
		        try {
		        		BundleObjectService apigeeService = (BundleObjectService) ms.getServiceByType(bundleType, env) ; 
		        		UUID deployUUIDObj = UUID.fromString(deployUUID); 
			       		ProcessResults eTLResult = apigeeService.rollBackAllToSerializedDeployStatus( deployUUIDObj);
			       		String serializePath = ms.getSerlizeProcessResultFileName(uuid.toString() ) ; 
			       		Helper.serialize(serializePath ,eTLResult ) ; 
		        	} catch (Exception e) {
		             e.printStackTrace();
		        	}
		    }
    	); 
    	ThreadStatusManager.startThread(thread, uuid) ; 
        // Process the request and return a response
        return new ResponseEntity<String>("{\"rollBackAllUUID\":\""+uuid+"\"}", HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@PostMapping( { "/apigee/infras/{infra}/orgs/{org}/migrate/rollback/{bundleType}/{objectName}/{deployUUID}"
			, "/apigee/infras/{infra}/orgs/{org}/envs/{env}/migrate/rollback/{bundleType}/{objectName}/{deployUUID}"} )
    public ResponseEntity<String> rollBackSingleEtlBundle(
    		@RequestHeader(required = false )   String partner,
            @RequestHeader(required = false )  String customer,
            @RequestHeader(required = false )   String migrationBasePath,
            @PathVariable String infra,
            @PathVariable String org,
            @PathVariable(required = false) String env,
            @PathVariable String bundleType,
            @PathVariable String deployUUID,
            @PathVariable String objectName,
            @RequestHeader("Authorization")  String authorizationHeader
    )  {
		try {
		initialize(partner , customer , infra , org, authorizationHeader, migrationBasePath);  
    	UUID uuid = UUID.randomUUID(); 
    	Thread thread = new Thread(() -> 
	    	{
		        try {
		        		RollBackable apigeeService = (RollBackable) ms.getServiceByType(bundleType , env ) ; 
		        		UUID deployUUIDObj = UUID.fromString(deployUUID); 
			       		ProcessResults eTLResult = apigeeService.rollBackObjectToSerializedDeployStatus(objectName ,  deployUUIDObj);
			       		String serializePath = ms.getSerlizeProcessResultFileName(uuid.toString() ) ; 
			       		Helper.serialize(serializePath ,eTLResult ) ; 
		        	} catch (Exception e) {
		             e.printStackTrace();
		        	}
		    }
    	); 
    	ThreadStatusManager.startThread(thread, uuid) ; 
        // Process the request and return a response
        return new ResponseEntity<String>("{\"processUUID\":\""+uuid+"\"}", HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }	
 
}