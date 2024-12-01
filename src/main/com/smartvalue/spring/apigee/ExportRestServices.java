package com.smartvalue.spring.apigee;

import java.io.File;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.smartvalue.apigee.migration.export.ExportResults;
import com.smartvalue.apigee.resourceManager.helpers.Helper;
import com.smartvalue.apigee.rest.schema.ApigeeService;
import com.smartvalue.spring.ThreadStatusManager;

@RestController
public class ExportRestServices extends RestServices {

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
	@PostMapping( value = { "/apigee/infras/{infra}/orgs/{org}/migrate/export/{bundleType}"
		, "/apigee/infras/{infra}/orgs/{org}/envs/{env}/migrate/export/{bundleType}"
	})
    public ResponseEntity<String> exportAll(
    		@RequestHeader(required = false )   String partner,
            @RequestHeader(required = false )  String customer,
            @RequestHeader(required = false )   String migrationBasePath,
            @PathVariable String infra,
            @PathVariable String org,
            @PathVariable(required = false) String env,
            @PathVariable String bundleType,
            @RequestHeader("Authorization")  String authorizationHeader
         
    )  {
		try {
			initialize(partner , customer , infra , org, authorizationHeader , migrationBasePath); 
	    	UUID uuid = UUID.randomUUID(); 
	    	Thread thread = new Thread(() -> {
            try {
	            	ApigeeService apigeeService = ms.getServiceByType(bundleType , env ) ; 
	            	String destFolder = ms.getMigPathUpToOrgName(uuid.toString())+File.separator+ apigeeService.getMigationSubFoler() ; 
	            	ExportResults result = apigeeService.exportAll( destFolder , uuid ) ;
	            	String serializePath = ms.getSerlizeProcessResultFileName(uuid.toString()) ; 
	            	Helper.serialize(serializePath ,result ) ; 
	           	
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }); 
	    	ThreadStatusManager.startThread(thread, uuid) ;
	    	
	        return new ResponseEntity<String>("{\"exportUUID\":\""+uuid+"\"}", HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }	

}

