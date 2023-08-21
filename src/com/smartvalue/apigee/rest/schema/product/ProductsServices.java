package com.smartvalue.apigee.rest.schema.product;

import java.io.IOException;
import java.util.ArrayList;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.resourceManager.ManagementServer;
import com.smartvalue.apigee.rest.schema.organization.Organization;

public class ProductsServices extends com.smartvalue.apigee.rest.schema.Service {


public  ArrayList <String> getProductsWithoutProxies(Organization m_org ) throws UnirestException, IOException
{
	ArrayList <String> result = new ArrayList <String> () ; 
	ArrayList<String> all = m_org.getAllProductsNames() ; 
	System.out.println("======== Processing "+all.size()+" Products ==========  " );
	int counter = 1 ; 
	for (String productName : all )
	{
		System.out.print(counter + "-Checking Product :" + productName);
		Product product = m_org.getProduct(productName) ;
		int size = product.getProxies().size() ; 
		if (size == 0 )
		{
			result.add(productName) ; 
		}
		System.out.println( size ==0  ? ".....\t\t\t Risky" : ".....\t\t\t OK" ) ; 
		counter++ ; 
	}

	return result;
}

}
