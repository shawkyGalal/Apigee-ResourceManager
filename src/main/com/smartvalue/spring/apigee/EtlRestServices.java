package com.smartvalue.spring.apigee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartvalue.apigee.configuration.AppConfig;
import com.smartvalue.apigee.configuration.AppConfigFactory;
import com.smartvalue.apigee.configuration.infra.Infra;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.migration.export.ExportResults;
import com.smartvalue.apigee.rest.schema.BundleObjectService;

@RestController
public class EtlRestServices {
    
    
    @GetMapping("/apigee/migration/{infra}/{org}/apis/")
    public ResponseEntity<String> exportAllProxies(
            @PathVariable("infra") 
            String infra,
            @PathVariable("org") 
            String org,
            @PathVariable("proxyName") 
            String proxyName,
            @RequestHeader("Authorization") 
            String authorizationHeader,
            @RequestParam("action") 
            String action,
            @RequestHeader("destFolder") 
            String destFolder
    ) throws Exception {
    	AppConfig ac = AppConfigFactory.create( "config.json" , AppConfig.class) ;  //(ApigeeConfig) apigeeConfigParser.getObject(configFile , ApigeeConfig.class) ; 

		Infra infraObject = ac.getInfra("MasterWorks" , "MOJ" , infra) ;
    	ManagementServer  ms = infraObject.getManagementServer(infraObject.getRegions().get(0).getName()) ;
    	ms.setAuthorizationHeader(authorizationHeader ) ; 
    	BundleObjectService ps = ms.getProxyServices(org);
    	ExportResults ers = new ExportResults() ; 
    	if (action.equalsIgnoreCase("exportAll"))
    	{
		 ers = ps.exportAll(destFolder);
    	}
        // Process the request and return a response
        return new ResponseEntity<String>(ers.toString(), HttpStatus.CREATED);
    }
    
}

