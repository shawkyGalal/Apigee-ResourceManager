package com.smartvalue.apigee.rest.schema.product;

import java.io.IOException;
import java.util.ArrayList;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.resourceManager.ManagementServer;
import com.smartvalue.apigee.rest.schema.organization.Organization;

public class ProductsServices {

	private ManagementServer ms ;
	//private Organization org ; 
	
public  ArrayList <String> getProductsWithoutProxies(Organization m_org ) throws UnirestException, IOException
{
	ArrayList <String> result = new ArrayList <String> () ; 
	ArrayList<String> all = m_org.getAllProductsNames() ; 
	System.out.println("======== Processing "+all.size()+" Products ==========  " );
	int counter = 1 ; 
	for (String productName : all )
	{
		System.out.println(counter + "-Checking Product :" + productName);
		Product product = m_org.getProduct(productName) ; 
		if (product.getProxies().size() == 0 )
		{
			result.add(productName) ; 
		}
		counter++ ; 
	}

	return result;
}
public ManagementServer getMs() {
	return ms;
}
public void setMs(ManagementServer ms) {
	this.ms = ms;
}
}
