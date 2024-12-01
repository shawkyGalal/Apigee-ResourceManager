package com.smartvalue.spring.apigee;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.smartvalue.apigee.configuration.AppConfig;
import com.smartvalue.apigee.configuration.AppConfigFactory;
import com.smartvalue.apigee.configuration.infra.Infra;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.resourceManager.helpers.Helper;
import com.smartvalue.apigee.rest.schema.DeploymentsStatus;
import com.smartvalue.spring.ThreadStatusManager;

public abstract class RestServices {

	private static final String DEFAULT_PARTNER = "MasterWorks";
	private static final String DEFAULT_CUSTOMER = "MOJ";
	ManagementServer  ms ;
	private AppConfig getAppConfig() throws FileNotFoundException, IOException {
		
		return AppConfigFactory.create( "config.json" , AppConfig.class);
	}
	
	protected void initialize(String partner , String customer , String infra , String org , String authorizationHeader , String migrationBasePath) throws Exception
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
	
	protected ResponseEntity<String> buildJsonResponse(String jsonString , HttpStatus status )
	{
		 HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Type", "application/json");
 	    return new ResponseEntity<String>(jsonString, headers , status);
	}
	
	
	
	
}
