package com.smartvalue.apigee.rest.schema.organization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.resourceManager.ManagementServer;
import com.smartvalue.apigee.rest.schema.environment.Environment;
import com.smartvalue.apigee.rest.schema.product.Product;
import com.smartvalue.apigee.rest.schema.proxy.Proxy;
import com.smartvalue.apigee.rest.schema.sharedFlow.SharedFlow;

public class Organization extends com.smartvalue.apigee.rest.schema.organization.auto.Organization {

	//private ManagementServer ms ; 
	//private String name ;
	private ArrayList<Environment>  envs ;
	
	
	
	
	@SuppressWarnings("unchecked")
	public HashMap<String , Object> getEnvs() throws UnirestException, IOException {
		ArrayList<String> envNames = null; 
		HashMap<String , Object > result  = new HashMap<String , Object> () ;
		String apiPath = "/v1/o/"+this.getName() +"/e" ; 
		ManagementServer ms = this.getManagmentServer() ; 
		envNames = ms.executeGetMgmntAPI(apiPath , ArrayList.class ) ;
		for (String envName : envNames )
		{
			Environment env = ms.executeGetMgmntAPI(apiPath +"/" + envName , Environment.class ) ;
			env.setOrgName(this.getName());
			env.setMs(ms);
			result.put(envName , env) ; 
		}
		return result ; 
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String , Proxy>  getAllProxies() throws UnirestException, IOException
	{
		ArrayList<String> proxiesName = null; 
		String apiPath = "/v1/o/"+this.getName()+"/apis" ; 
		ManagementServer ms = this.getManagmentServer() ;
		proxiesName = ms.executeGetMgmntAPI(apiPath , ArrayList.class ) ; 
		HashMap<String , Proxy> proxies = new HashMap<String , Proxy>() ; 
		for (String proxyName : proxiesName)
		{
			String apiPath01 = apiPath + "/" + proxyName ; 
			@SuppressWarnings("deprecation")
			Proxy proxy = ms.executeGetMgmntAPI(apiPath01 , Proxy.class ) ;
			proxy.setOrgName(this.getName()) ; 
			proxy.setManagmentServer(ms) ; 
			proxies.put (proxyName , proxy) ; 
		}
		return proxies ; 
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String>  getAllProxiesNames() throws UnirestException, IOException
	{
		ArrayList<String> proxiesName = null; 
		String apiPath = "/v1/o/"+this.getName()+"/apis" ; 
		ManagementServer ms = this.getManagmentServer() ;
		proxiesName = ms.executeGetMgmntAPI(apiPath , ArrayList.class ) ;
		return proxiesName ;  
	}
	
	public HashMap < String , Object > getAllProxiesUsesTargetServer(String m_targetServerName , boolean m_deployedVersionOnly) throws UnirestException, IOException
	{
		HashMap < String , Object > result = new HashMap < String , Object >() ;
		ArrayList<String> allProxies = getAllProxiesNames(); 
		int counter = 1;
		System.out.println("======== Searching over " + allProxies.size()  +  "  Proxies Using Target Server "+m_targetServerName+"===" ) ;  
		for (String proxyName : allProxies )
		{
			
			Proxy proxy = this.getProxy(proxyName);
			int revisionsSize = proxy.getRevision().size() ;  
			System.out.print(counter + "- Checking Proxy <"+ proxyName + "> ("+revisionsSize+") revisions ...");
			HashMap<String, ArrayList<String>> revisions = proxy.getRevisionsUsesTargetServer(m_targetServerName , m_deployedVersionOnly) ; 
			if (revisions.size() > 0 )
			{
				result.put(proxyName , revisions ) ; 
				System.out.println("\t\t\t\t Found ") ; 
			}
			else 
			{
				System.out.println("\t\t\t\t  Not") ;
			}
			counter++; 
		}
		return result;
		
	}
	
	@SuppressWarnings("unchecked")
	public Proxy  getProxy(String ProxyName) throws UnirestException, IOException
	{
		String apiPath = "/v1/o/"+this.getName()+"/apis/" + ProxyName ; 
		ManagementServer ms = this.getManagmentServer() ;
		Proxy proxy  = ms.executeGetMgmntAPI(apiPath , Proxy.class ) ;
		proxy.setOrgName(this.getName()) ; 
		proxy.setManagmentServer(ms) ; 
		
		return proxy ; 
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object>  getProxyDeployments(String m_ProxyName) throws UnirestException, IOException
	{
		Map<String, Object> result = null; 
		String apiPath = "/v1/o/"+this.getName()+"/apis/"+m_ProxyName+"/deployments" ; 
		ManagementServer ms = this.getManagmentServer() ;
		result = ms.executeGetMgmntAPI(apiPath , Map.class ) ;
		return result ; 
	}
	
	
	
	@SuppressWarnings("unchecked")
	public String[]  getAllShardFlow() throws UnirestException, IOException
	{
		String apiPath = "/v1/o/"+this.getName()+"/sharedflows/"  ; 
		ManagementServer ms = this.getManagmentServer() ;
		String[] sharedFlows  = ms.executeGetMgmntAPI(apiPath , String[].class ) ;
		return sharedFlows ; 
	}
	
	@SuppressWarnings("unchecked")
	public SharedFlow  getShardFlow(String sharedFlowName) throws UnirestException, IOException
	{
		String apiPath = "/v1/o/"+this.getName()+"/sharedflows/" + sharedFlowName ; 
		ManagementServer ms = this.getManagmentServer() ;
		SharedFlow sharedFlow  = ms.executeGetMgmntAPI(apiPath , SharedFlow.class ) ;
		sharedFlow.setOrgName(this.getName()) ; 
		sharedFlow.setManagmentServer(ms) ; 
		
		return sharedFlow ; 
	}
	
	
	public ArrayList<String> getUndeployedProxies() throws UnirestException, InterruptedException, IOException
	{
		ArrayList<String> apis = this.getAllProxiesNames();
		ArrayList<String> proxiesNotDeployed = new ArrayList<String>();  
		HashMap<String , String> proxiesFailed = new HashMap<String , String>();
		int count =1 ; 
		for (String proxyname : apis )
		{
		 System.out.print(count + "-Procesing Proxy " + proxyname );
		  TimeUnit.MILLISECONDS.sleep(100);
		  try {
			  //Proxy proxy = this.getProxy(proxyname) ; 
	  		  Map<String , Object > proxyDeployments  = this.getProxyDeployments(proxyname) ;
	  		  @SuppressWarnings("unchecked")
	  		  int  deploymentsCount = ((ArrayList<Object>) proxyDeployments.get("environment") ).size()  ;
	  		  if (deploymentsCount ==0 )
	  		  {
	  			proxiesNotDeployed.add(proxyname) ; 
	  		  }
	  		  System.out.println("...." + deploymentsCount + " Deployments" );
	  		count++; 
		  }
		  catch ( Exception e) {proxiesFailed.put (proxyname , e.getMessage()) ; }
		}
		
		return proxiesNotDeployed ; 
		
	}

	public ArrayList<String> getAllProductsNames() throws UnirestException, IOException {
		
		String apiPath = "/v1/o/"+this.getName()+"/apiproducts" ; 
		ManagementServer ms = this.getManagmentServer() ;
		@SuppressWarnings("unchecked")
		ArrayList<String> allProducts  = ms.executeGetMgmntAPI(apiPath , ArrayList.class ) ;
		return allProducts ; 
		
	}
	public Product getProduct(String m_productName) throws UnirestException, IOException
	{
		Product result ; 
		String apiPath = "/v1/o/"+this.getName()+"/apiproducts/"+ m_productName ;
		ManagementServer ms = this.getManagmentServer() ;
		result = ms.executeGetMgmntAPI(apiPath , Product.class ) ; 
		return result;
		
	}
}