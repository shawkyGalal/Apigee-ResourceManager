package com.smartvalue.spring.apigee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.smartvalue.apigee.rest.schema.product.ProductsServices;

@RestController
public class ProductsRestServices extends RestServices{

		    

	@GetMapping("/apigee/infras/{infra}/orgs/{org}/products/includes/proxies")
    public ResponseEntity<String> getDeployStatus(
    		@RequestHeader(required = false )   String partner,
            @RequestHeader(required = false )  String customer,
            @PathVariable  String infra,
            @PathVariable  String org,
            @RequestBody String proxyNames ,
           
            @RequestHeader("Authorization") String authorizationHeader ) 
         
	{
		try {
			initialize(partner , customer , infra , org, authorizationHeader, "");
			ProductsServices prodServ =   (ProductsServices) ms.getProductServices() ;
			List<String> proxyNamesList = Arrays.asList(proxyNames.split(",")) ;
			List<String> proxyNamesListTrimmed = new ArrayList<String>() ; 
			// Trim whitespace from each element
	        for (String aa : proxyNamesList) {
	        	proxyNamesListTrimmed.add(aa.trim()); 
	        }
			HashMap<String, ArrayList<String>> result = prodServ.getProductsUsesProxies(proxyNamesListTrimmed) ; 
			return new ResponseEntity<String>(result.toString(), HttpStatus.OK); 
   	         
    		}
		
    	catch (Exception e) {
    		return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    	}
	}
	
	
 
}