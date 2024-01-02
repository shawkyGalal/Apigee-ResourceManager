package com.smartvalue.apigee.rest.schema.product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.rest.schema.organization.Organization;
import com.smartvalue.apigee.rest.schema.proxy.transformers.TransformResult;
import com.smartvalue.apigee.rest.schema.sharedFlow.SharedFlow;

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
public ArrayList<HttpResponse<String>> deleteAll() throws UnirestException, IOException {
	return null;
}

@Override
public String getResourcePath() {
	return "/v1/organizations/"+this.orgName+"/apiproducts";
}

public Product getProductByName(String productName) throws Exception
{
	return this.getResource(productName, Product.class) ; 
}

@SuppressWarnings("unchecked")
public ArrayList<String>  getAllProducsList() throws Exception
{
	ArrayList<String> producsList = this.getAllResources(ArrayList.class); 
	return producsList ;  
}

public <T> T  getAllProducsList( Class<T> classOfT ) throws Exception
{
	T proxiesList  = this.getAllResources(classOfT) ; 
	return proxiesList ;  
}

@Override
public String getApigeeObjectType() {
	return "apiproducts";
}



}
