package com.smartvalue.spring.apigee;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.smartvalue.apigee.configuration.AppConfig;
import com.smartvalue.apigee.configuration.AppConfigFactory;
import com.smartvalue.apigee.configuration.infra.Infra;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.configuration.infra.ServiceFactory;
import com.smartvalue.apigee.migration.export.ExportResults;
import com.smartvalue.apigee.migration.transformers.TransformationResults;
import com.smartvalue.apigee.rest.schema.BundleObjectService;
import com.smartvalue.apigee.rest.schema.proxy.ProxyServices;
import com.smartvalue.apigee.rest.schema.sharedFlow.SharedFlowServices;

@RestController
public class EtlRestServices {

	public AppConfig getAppConfig() throws FileNotFoundException, IOException {
		
		return AppConfigFactory.create( "config.json" , AppConfig.class);
	}

	@GetMapping("/apigee/etl/infras/{infra}/orgs/{org}/export/apis/")
    public ResponseEntity<String> listAllExports(
    		@RequestHeader("partner") String partner,
            @RequestHeader("customer") String customer,
    		@PathVariable("infra") String infra,
            @PathVariable("org") String org , 
            @RequestHeader("Authorization")  String authorizationHeader
            )
    {
		System.out.println("");
		return new ResponseEntity<String>("Partner =" + partner + ", customer = " + customer +", infra = " + infra, HttpStatus.ACCEPTED);
    }
    
	
	@PostMapping("/apigee/etl/infras/{infra}/orgs/{org}/export/{bundleType}/")
    public ResponseEntity<String> exportAllProxies(
    		@RequestHeader("partner") String partner,
            @RequestHeader("customer")   String customer,
            @PathVariable("infra") String infra,
            @PathVariable("org") String org,
            @PathVariable("bundleType") String bundleType,
            @RequestHeader("Authorization")  String authorizationHeader
         
    )  {
		try {
    	AppConfig ac = getAppConfig();  
		Infra infraObject = ac.getInfra(partner , customer , infra) ;
    	ManagementServer  ms = infraObject.getManagementServer(infraObject.getRegions().get(0).getName()) ;
    	ms.setOrgName(org) ; 
    	ms.setAuthorizationHeader(authorizationHeader ) ; 
    	
    	String uuid = UUID.randomUUID().toString(); 
    	Thread thread = new Thread(() -> {
            System.out.println("This is a new thread.");
            // Your process here
            try {
            	Class<? extends BundleObjectService> type = null ;
            	if (bundleType.equalsIgnoreCase("proxies") || bundleType.equalsIgnoreCase("apis")  ) type =  ProxyServices.class ;
            	if (bundleType.equalsIgnoreCase("sharedFlows")) type =  SharedFlowServices.class ;
            	BundleObjectService bundleObjectService = ServiceFactory.createBundleServiceInstance(type, ms ) ; 
            	ExportResults result = bundleObjectService.exportAllBundledObjects( type ,  uuid ) ;
           	
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
	
	
	@PostMapping("/apigee/etl/infras/{infra}/orgs/{org}/transform/{bundleType}/{exportUuid}")
    public ResponseEntity<String> transformAllProxies(
    		@RequestHeader("partner")   String partner,
            @RequestHeader("customer")  String customer,
            @PathVariable("infra")  String infra,
            @PathVariable("org") String org,
            @PathVariable("bundleType") String bundleType,
            @PathVariable("exportUuid")   String exportUuid,
            @RequestHeader("Authorization") String authorizationHeader ) 
         
            {
    			try {
    	    	AppConfig ac = getAppConfig();  
    			Infra infraObject = ac.getInfra(partner , customer , infra) ;
    	    	ManagementServer  ms = infraObject.getManagementServer(infraObject.getRegions().get(0).getName()) ;
    	    	ms.setOrgName(org) ; 
    	    	ms.setAuthorizationHeader(authorizationHeader ) ; 
    	    	
    	    	Thread thread = new Thread(() -> {
    	            System.out.println("This is a new thread.");
    	            // Your process here
    	            try {
    	            	Class<? extends BundleObjectService> type = null ;
    	            	if (bundleType.equalsIgnoreCase("proxies") || bundleType.equalsIgnoreCase("apis")  ) type =  ProxyServices.class ;
    	            	if (bundleType.equalsIgnoreCase("sharedFlows")) type =  SharedFlowServices.class ;
    	            	BundleObjectService bundleObjectService = ServiceFactory.createBundleServiceInstance(type, ms ) ; 
 	            	
    	            	String migrationBasePath = AppConfig.getMigrationBasePath() ;  ; 
    	            	TransformationResults transformationResults =  bundleObjectService.transformAll(migrationBasePath	+"\\"+ exportUuid + "\\"+infra+"\\"+ org +"\\proxies"
    	            														  				, migrationBasePath +"\\"+ exportUuid + "\\"+infra+"\\"+ org +"\\Transformed" + "\\proxies" 	) ;
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

    
}

