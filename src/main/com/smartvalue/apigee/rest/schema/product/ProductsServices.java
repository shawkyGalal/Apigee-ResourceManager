package com.smartvalue.apigee.rest.schema.product;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.migration.transformers.ApigeeObjectTransformer;
import com.smartvalue.apigee.rest.schema.organization.Organization;

public class ProductsServices extends com.smartvalue.apigee.rest.schema.ApigeeService {





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

public ArrayList<Product>  getAllProducs( ) throws Exception
{
	ArrayList<Product> products = this.getAllResourcesList(Product.class) ; 
	return products ;  
}




@Override
public String getApigeeObjectType() {
	return "apiproducts";
}

@Override
public ArrayList<ApigeeObjectTransformer> buildTransformers()
		throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException,
		IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, FileNotFoundException, IOException {
	return this.getMs().getInfra().buildProductsTransformers();
}



}
