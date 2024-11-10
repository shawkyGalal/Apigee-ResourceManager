package com.smartvalue.apigee.rest.schema.proxy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.AppConfig;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.migration.ProcessResult;
import com.smartvalue.apigee.migration.ProcessResults;
import com.smartvalue.apigee.migration.deploy.DeployResult;
import com.smartvalue.apigee.migration.deploy.DeployResults;
import com.smartvalue.apigee.migration.export.ExportResult;
import com.smartvalue.apigee.migration.export.ExportResults;
import com.smartvalue.apigee.migration.load.LoadResult;
import com.smartvalue.apigee.migration.transformers.ApigeeObjectTransformer;
import com.smartvalue.apigee.migration.transformers.TransformResult;
import com.smartvalue.apigee.migration.transformers.TransformationResults;
import com.smartvalue.apigee.resourceManager.helpers.Helper;
import com.smartvalue.apigee.rest.schema.Deployable;
import com.smartvalue.apigee.rest.schema.BundleObjectService;
import com.smartvalue.apigee.rest.schema.organization.Organization;
import com.smartvalue.apigee.rest.schema.proxy.google.auto.GoogleProxiesList;
import com.smartvalue.apigee.rest.schema.proxy.google.auto.GoogleProxy;
import com.smartvalue.apigee.rest.schema.proxyDeployment.ProxyDeployment;
import com.smartvalue.apigee.rest.schema.proxyDeployment.auto.Environment;
import com.smartvalue.apigee.rest.schema.proxyDeployment.auto.Revision;
import com.smartvalue.apigee.rest.schema.proxyUploadResponse.ProxyUploadResponse;


public class ProxyServices extends BundleObjectService implements Deployable {


	public ProxyServices(ManagementServer ms, String m_orgName) {
		super(ms, m_orgName);
	}
	public ProxyServices(ManagementServer ms) {
		super(ms);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Proxy>  getAllProxies() throws Exception
	{
		return this.getAllResourcesList(Proxy.class) ; 
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList<String>  getOnPremiseAllProxiesList() throws Exception
	{
		ArrayList<String> proxiesList = this.getAllResources(ArrayList.class) ;  
		return proxiesList ;  
	}
	
	public <T> T  getAllProxiesList( Class<T> classOfT ) throws Exception
	{
		
		T proxiesList = this.getAllResources(classOfT) ;  
		return proxiesList ;  
	}
	
	/**
	 * REturn a MashMap with proxyname and revision numbers that Does uses the given polices  
	 * @param m_polices
	 * @param m_deployedVersionOnly
	 * @return
	 * @throws Exception 
	 */
	public HashMap<String, List<Object>>  getProxiesWithoutPolices(String[] m_polices , boolean m_deployedVersionOnly ) throws Exception
	{
		HashMap<String, List<Object>> result = new HashMap<>() ; 
		ArrayList<String> proxiesName = getOnPremiseAllProxiesList() ; 
		ManagementServer ms = this.getMs() ;
		Organization org = (Organization) ms.getCurrentOrg() ;
		int count = 0 ; 
		System.out.println("===============Start Searching for Proxies ("+proxiesName.size()+") Does not Use Polices with names " +  m_polices +"=======");
		for (String proxyName : proxiesName )
		{   count++; 
			System.out.print(count + "- Processing Proxy " + proxyName );
			Proxy proxy = org.getProxy(proxyName);
			ArrayList<Object> revisionWithoutPolices = proxy.getRevisionsNotUsingPolices(m_polices , m_deployedVersionOnly) ; 
			if (revisionWithoutPolices.size() > 0 )
			{  System.out.println("....  Found in revision(s)" +  revisionWithoutPolices );
				result.put(proxyName , revisionWithoutPolices ) ;
			}
			else {System.out.println("...   Ok " );}
		}
		System.out.println("===============End Searching for Proxies ("+proxiesName.size()+") =======");
		return result;
		
	}

	public ArrayList<TransformResult> transformPundle(String pundleZipFileName , String newFilePath) throws Exception
	{
		int count=0; 
		ArrayList<ApigeeObjectTransformer> transformers  = this.getMs().getInfra().buildProxyTransformers() ;
		
		int transformersSize = transformers.size();
		String sourceFile = pundleZipFileName ;
		ArrayList<TransformResult> result = new ArrayList<TransformResult>() ; 
		for (ApigeeObjectTransformer aot : transformers)
		{
			count++; 
			String tranformedFile = (count == transformersSize)? newFilePath : newFilePath+"_Tranform_"+count;
			if(aot.filter(pundleZipFileName))
			{
			 result.add( aot.trasform(sourceFile , tranformedFile));
			 sourceFile = tranformedFile + File.separator + new File(pundleZipFileName).getName() ;
			}
		}
		
		return result; 
	}


	public HttpResponse<String> deleteProxy( String m_proxyName) throws UnirestException, IOException
	{
		HttpResponse<String> result = null; 
		String apiPath = getResourcePath()+"/"+m_proxyName ; 
		ManagementServer ms = this.getMs() ; 
		result = ms.getDeleteHttpResponse(apiPath ) ;
		return result ; 
	}
	
	
	
	public  DeleteResults deleteAll() throws Exception
	{
		Boolean isGoogle = this.getMs().getInfra().isGooglecloud() ;  
		if (isGoogle != null && isGoogle)
		{
			GoogleProxiesList proxiesList = this.getAllProxiesList(GoogleProxiesList.class) ;  
			return deleteAll(proxiesList); 
		}
		else
		{
			return deleteAll(this.getOnPremiseAllProxiesList() , null) ;
		}
	}
	
	private DeleteResults  deleteAll(ArrayList<String> allProxiesList , UUID uuid) {
		if (uuid == null)  uuid = UUID.randomUUID();
		DeleteResults deletResults =  new DeleteResults("" , uuid); 
		for (int i = 0 ;  i< allProxiesList.size() ; i++ )
		{
			DeleteResult deletResult =  new DeleteResult(); 
			String proxyName = allProxiesList.get(i) ; 
			
			HttpResponse<String> result = null;
			try {
				result = deleteProxy(proxyName);
				deletResult.setSource(this.getMs().getInfraName() +"." + this.getMs().getOrgName() +"." +  proxyName );
				deletResult.setFailed(! Helper.isConsideredSuccess(result.getStatus()));
				
			} catch (UnirestException | IOException e) {
				deletResult.setFailed(true);
				deletResult.setError(e.getMessage()); 
				deletResult.setExceptionClassName(e.getClass().getName());
			}
			deletResult.setHttpResponse(result);
			deletResults.add(deletResult); 
		}
		
		return deletResults;
	}

	public  DeleteResults deleteAll(GoogleProxiesList proxiesList) throws UnirestException, IOException
	{
		ArrayList<String> simpleProxiesList = new ArrayList<String>() ;  
		for (com.smartvalue.apigee.rest.schema.proxy.google.auto.GoogleProxy proxy : proxiesList.getProxies())
		{
			simpleProxiesList.add(proxy.getName()) ; 
		}
		return this.deleteAll(simpleProxiesList, null) ; 
	}
	

	public ArrayList<String>  getAllBundledObjectNameList() throws Exception
	{
		ArrayList<String> allProxies ; 
		Boolean isGoogleCloud = this.getMs().getInfra().isGooglecloud() ;
		if (isGoogleCloud != null && isGoogleCloud)
		{
			GoogleProxiesList proxiesList = this.getAllProxiesList(GoogleProxiesList.class);
			allProxies = new ArrayList<String>();
			for (GoogleProxy googleproxy : proxiesList.getProxies())
			{
				allProxies.add(googleproxy.getName()) ;  
			}
		}
		else {
			allProxies =  this.getOnPremiseAllProxiesList();
		}
		return allProxies ; 
	}

	@Override
	public String getResourcePath() {
		return AppConfig.BASE_BATH+this.getMs().getOrgName()+"/"+getApigeeObjectType();
	}

	@Override
	public String getApigeeObjectType() {
		return "apis";
	}

	@Override
	public ArrayList<ApigeeObjectTransformer> buildTransformers() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, FileNotFoundException, IOException {
		return this.getMs().getInfra().buildProxyTransformers();
	}
	
}
