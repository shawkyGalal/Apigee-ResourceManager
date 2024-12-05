package com.smartvalue.apigee.rest.schema.product;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.AppConfig;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.migration.ProcessResults;
import com.smartvalue.apigee.migration.deploy.DeployResults;
import com.smartvalue.apigee.migration.transformers.ApigeeObjectTransformer;
import com.smartvalue.apigee.rest.schema.organization.Organization;
import com.smartvalue.apigee.rest.schema.proxy.DeleteResults;

public class ProductsServices extends com.smartvalue.apigee.rest.schema.ApigeeService {

public ProductsServices(ManagementServer ms) {
	super(ms);
}

public  ArrayList<Object> getProductsWithoutProxies() throws UnirestException, IOException
{
	Organization m_org= (Organization) this.getMs().getCurrentOrg() ; 
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

public  ArrayList<String> getProductsUsesProxy(String proxyname) throws UnirestException, IOException
{
	Organization m_org= (Organization) this.getMs().getCurrentOrg() ; 
	ArrayList <String> result = new ArrayList <String> () ; 
	ArrayList<String> all = m_org.getAllProductsNames() ; 
	this.getPrintStream().println("======== Processing "+all.size()+" Products ==========  " );
	int counter = 1 ; 
	for (String productName : all )
	{
		this.getPrintStream().println(counter + "-Checking Product :" + productName);
		Product product = m_org.getProductByName(productName) ;
		try {Thread.sleep(10);} catch (InterruptedException e) {	e.printStackTrace();}
		for (String productProxyName  : product.getProxies() )
		{
			if (productProxyName.equalsIgnoreCase(proxyname))
			{
				result.add(productName) ; 	
			}
		}
		counter++ ; 
	}
	return result;
}

public  HashMap<String , ArrayList<String>> getProductsUsesProxies(List<String> proxynames) throws UnirestException, IOException
{
	Organization m_org= (Organization) this.getMs().getCurrentOrg() ; 
	HashMap<String , ArrayList<String>> result = new HashMap<String , ArrayList<String>> () ; 
	ArrayList<String> all = m_org.getAllProductsNames() ; 
	this.getPrintStream().println("======== Processing "+all.size()+" Products ==========  " );
	int counter = 1 ; 
	for (String productName : all )
	{
		this.getPrintStream().println(counter + "-Checking Product :" + productName);
		Product product = m_org.getProductByName(productName) ;
		try {Thread.sleep(10);} catch (InterruptedException e) {	e.printStackTrace();}
		for (String productProxyName  : product.getProxies() )
		{
			if (proxynames.contains(productProxyName))
			{
				if (result.get(productProxyName) == null)
				{
					result.put(productProxyName, new ArrayList<String>() ); 
				}
				result.get(productProxyName).add(productName); 
			}
		}
		counter++ ; 
	}
	return result;
}


@Override
public DeleteResults deleteAll() throws UnirestException, IOException {
	return null;
}

@Override
public String getResourcePath() {
	return AppConfig.BASE_BATH+this.getMs().getOrgName()+"/apiproducts";
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

public ArrayList<Product> searchForProduct(String m_scopes , String m_resourceProxyName)
{
	//TODO
	return null ; 
}

@Override
public ProcessResults performETL( String objectId, UUID uuid) throws Exception {
	// TODO Auto-generated method stub
	return null;
}

}
