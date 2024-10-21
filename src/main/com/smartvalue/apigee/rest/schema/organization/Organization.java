package com.smartvalue.apigee.rest.schema.organization;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.rest.schema.application.Application;
import com.smartvalue.apigee.rest.schema.developer.Developer;
import com.smartvalue.apigee.rest.schema.environment.Environment;
import com.smartvalue.apigee.rest.schema.product.Product;
import com.smartvalue.apigee.rest.schema.proxy.Proxy;
import com.smartvalue.apigee.rest.schema.sharedFlow.SharedFlow;
import com.smartvalue.apigee.rest.schema.sharedFlow.auto.RevisionedObject;

public class Organization extends com.smartvalue.apigee.rest.schema.organization.auto.Organization {

	//private ManagementServer ms ; 
	//private String name ;
	private ArrayList<Environment>  envs ;
	private static final String BASE_BATH = "/v1/organizations/" ; 
	
	
	
	
	@SuppressWarnings("unchecked")
	public HashMap<String , Environment> getEnvs() throws UnirestException, IOException {
		ArrayList<String> envNames = null; 
		HashMap<String , Environment > result  = new HashMap<> () ;
		String apiPath = BASE_BATH +this.getName() +"/environments" ; 
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
	public HashMap<Object , Proxy>  getAllProxies() throws UnirestException, IOException
	{
		ArrayList<String> proxiesName = null; 
		String apiPath = BASE_BATH +this.getName()+"/apis" ; 
		ManagementServer ms = this.getManagmentServer() ;
		proxiesName = ms.executeGetMgmntAPI(apiPath , ArrayList.class ) ; 
		HashMap<Object , Proxy> proxies = new HashMap<>() ; 
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
		String apiPath = BASE_BATH +this.getName()+"/apis" ; 
		ManagementServer ms = this.getManagmentServer() ;
		proxiesName = ms.executeGetMgmntAPI(apiPath , ArrayList.class ) ;
		return proxiesName ;  
	}
	
	public HashMap < Object , Object > getAllProxiesUsesTargetServer(String m_targetServerName , boolean m_deployedVersionOnly) throws UnirestException, IOException
	{
		HashMap < Object , Object > result = new HashMap < >() ;
		ArrayList<String> allProxies = getAllProxiesNames(); 
		int counter = 1;
		this.getPrintStream().println("======== Searching over " + allProxies.size()  +  "  Proxies Using Target Server "+m_targetServerName+"===" ) ;  
		for (String proxyName : allProxies )
		{
			
			Proxy proxy = this.getProxy(proxyName);
			int revisionsSize = proxy.getRevision().size() ;  
			this.getPrintStream().print("<br>"+counter + "- Checking Proxy : "+ proxyName + " ("+revisionsSize+") revisions ...");
			HashMap<String, ArrayList<String>> revisions = proxy.getRevisionsUsesTargetServer(m_targetServerName , m_deployedVersionOnly) ; 
			if (revisions.size() > 0 )
			{
				result.put(proxyName , revisions ) ; 
				this.getPrintStream().println("\t\t\t\t Found ") ; 
			}
			else 
			{
				this.getPrintStream().println("\t\t\t\t  Not") ;
			}
			counter++; 
		}
		return result;
		
	}
	
	@SuppressWarnings("unchecked")
	public Proxy  getProxy(String ProxyName) throws UnirestException, IOException
	{
		String apiPath = BASE_BATH +this.getName()+"/apis/" + ProxyName ; 
		ManagementServer ms = this.getManagmentServer() ;
		Proxy proxy  = ms.executeGetMgmntAPI(apiPath , Proxy.class ) ;
		proxy.setOrgName(this.getName()) ; 
		proxy.setManagmentServer(ms) ; 
		
		return proxy ; 
	}
	
	@SuppressWarnings("unchecked")
	public RevisionedObject  getRevisionedObject(String type , String ProxyName) throws UnirestException, IOException
	{
		String apiPath = BASE_BATH +this.getName()+"/"+type+"/" + ProxyName ; 
		ManagementServer ms = this.getManagmentServer() ;
		RevisionedObject revisionedObject  = ms.executeGetMgmntAPI(apiPath , RevisionedObject.class  ) ; //Proxy.class ) ; //:  )) ;
		revisionedObject.setOrgName(this.getName()) ; 
		revisionedObject.setManagmentServer(ms) ; 
		
		return revisionedObject ; 
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object>  getProxyDeployments(String m_ProxyName) throws UnirestException, IOException
	{
		Map<String, Object> result = null; 
		String apiPath =  BASE_BATH +this.getName()+"/apis/"+m_ProxyName+"/deployments" ; 
		ManagementServer ms = this.getManagmentServer() ;
		result = ms.executeGetMgmntAPI(apiPath , Map.class ) ;
		return result ; 
	}
	
	
	
	@SuppressWarnings("unchecked")
	public ArrayList<String>  getAllShardFlow() throws UnirestException, IOException
	{
		String apiPath =  BASE_BATH +this.getName()+"/sharedflows/"  ; 
		ManagementServer ms = this.getManagmentServer() ;
		ArrayList<String> sharedFlows  = ms.executeGetMgmntAPI(apiPath , ArrayList.class ) ;
		return sharedFlows ; 
	}
	
	@SuppressWarnings("unchecked")
	public SharedFlow  getShardFlow(String sharedFlowName) throws UnirestException, IOException
	{
		String apiPath = BASE_BATH +this.getName()+"/sharedflows/" + sharedFlowName ; 
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
		HashMap<String , String> proxiesFailed = new HashMap<>();
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
		
		String apiPath = BASE_BATH +this.getName()+"/apiproducts" ; 
		ManagementServer ms = this.getManagmentServer() ;
		@SuppressWarnings("unchecked")
		ArrayList<String> allProducts  = ms.executeGetMgmntAPI(apiPath , ArrayList.class ) ;
		return allProducts ; 
		
	}
	public Product getProductByName(String m_productName) throws UnirestException, IOException
	{
		Product result ; 
		String apiPath = BASE_BATH +this.getName()+"/apiproducts/"+ m_productName ;
		ManagementServer ms = this.getManagmentServer() ;
		result = ms.executeGetMgmntAPI(apiPath , Product.class ) ;
		result.setManagmentServer(ms);
		result.setOrgName(this.getName()) ; 
		return result;
		
	}

	public Environment getEnvByName(String m_envName) throws UnirestException, IOException {
		ManagementServer ms = this.getManagmentServer() ;
		String apiPath = BASE_BATH +this.getName() +"/environments" ; 
		Environment env = ms.executeGetMgmntAPI(apiPath +"/" + m_envName , Environment.class ) ;
		env.setMs(ms) ;
		env.setOrgName(this.getName()) ; 
		return env;
	}
	
	public ArrayList<String> getDeveloperNames() throws UnirestException, IOException {
		
		String apiPath = BASE_BATH +this.getName()+"/developers/" ; 
		ManagementServer ms = this.getManagmentServer() ;
		@SuppressWarnings("unchecked")
		ArrayList<String> developers  = ms.executeGetMgmntAPI(apiPath , ArrayList.class ) ;
		return developers ; 
	}
	
	public Developer  getDeveloper(String uniqueId ) throws UnirestException, IOException {
		
		String apiPath = BASE_BATH +this.getName()+"/developers/"+ uniqueId; 
		ManagementServer ms = this.getManagmentServer() ;
		@SuppressWarnings("unchecked")
		Developer dev  = ms.executeGetMgmntAPI(apiPath , Developer.class ) ;
		return dev ; 
	}
	
	public ArrayList<String>  getAllAppsIds() throws UnirestException, IOException {
		
		String apiPath = this.getResourcePath()+"/apps/"; 
		ManagementServer ms = this.getManagmentServer() ;
		@SuppressWarnings("unchecked")
		ArrayList<String> result  = ms.executeGetMgmntAPI(apiPath , ArrayList.class ) ;
		return result ; 
	}
	
	public Application  getAppByAppId(String m_appId ) throws UnirestException, IOException {
		
		String apiPath = this.getResourcePath()+"/apps/"+ m_appId; 
		ManagementServer ms = this.getManagmentServer() ;
		@SuppressWarnings("unchecked")
		Application app  = ms.executeGetMgmntAPI(apiPath , Application.class ) ;
		return app ; 
	}

	public ArrayList<Application>  getAllApps() throws Exception {
		
		String apiPath = this.getResourcePath()+"/apps?expand=true"; 
		ManagementServer ms = this.getManagmentServer() ;
		@SuppressWarnings("unchecked")
		Type listType = new TypeToken<List<Application>>() {}.getType();
		ArrayList<Application> allApps  = ms.executeMgmntAPI(apiPath , listType  , "app") ;

		return allApps ; 
	}

	public String getResourcePath() {
		return "/v1/organizations/"+this.getName();
	}

	
}