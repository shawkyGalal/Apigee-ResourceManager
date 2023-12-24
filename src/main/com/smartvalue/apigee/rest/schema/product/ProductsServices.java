package com.smartvalue.apigee.rest.schema.product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.rest.schema.organization.Organization;

public class ProductsServices extends com.smartvalue.apigee.rest.schema.Service {





public ProductsServices(ManagementServer ms, String m_orgName) {
		super(ms, m_orgName);
	}

public  ArrayList<Object> getProductsWithoutProxies() throws UnirestException, IOException
{
	Organization m_org= (Organization) this.getMs().getOrgByName(this.orgName) ; 
	ArrayList <Object> result = new ArrayList <Object> () ; 
	ArrayList<String> all = m_org.getAllProductsNames() ; 
	this.getPrintStream().println("======== Processing "+all.size()+" Products ==========  " );
	int counter = 1 ; 
	for (String productName : all )
	{
		this.getPrintStream().print(counter + "-Checking Product :" + productName);
		Product product = m_org.getProductByName(productName) ;
		int size = product.getProxies().size() ; 
		if (size == 0 )
		{
			result.add(productName) ; 
		}
		this.getPrintStream().println( size ==0  ? ".....\t\t\t Risky" : ".....\t\t\t OK" ) ; 
		counter++ ; 
	}

	return result;
}

@Override
public ArrayList<HttpResponse<String>> importAll(String folderPath, boolean m_deploy)
		throws UnirestException, IOException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public HashMap<String, HashMap<Integer, Exception>> exportAll(String folderDest)
		throws UnirestException, IOException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public ArrayList<HttpResponse<String>> deleteAll() throws UnirestException, IOException {
	// TODO Auto-generated method stub
	return null;
}

}
