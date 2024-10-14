package com.smartvalue.apigee.rest.schema.proxy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.migration.export.ExportResults;
import com.smartvalue.apigee.migration.transformers.ApigeeObjectTransformer;
import com.smartvalue.apigee.migration.transformers.TransformResult;
import com.smartvalue.apigee.rest.schema.Deployable;
import com.smartvalue.apigee.rest.schema.BundleObjectService;
import com.smartvalue.apigee.rest.schema.organization.Organization;
import com.smartvalue.apigee.rest.schema.proxy.google.auto.GoogleProxiesList;
import com.smartvalue.apigee.rest.schema.proxy.google.auto.GoogleProxy;


public class ProxyServices extends BundleObjectService implements Deployable {


	public ProxyServices(ManagementServer ms, String m_orgName) {
		super(ms, m_orgName);
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
		Organization org = (Organization) ms.getOrgByName(this.orgName) ;
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
	
	
	
	public  ArrayList<HttpResponse<String>> deleteAll() throws Exception
	{
		Boolean isGoogle = this.getMs().getInfra().isGooglecloud() ;  
		if (isGoogle != null && isGoogle)
		{
			GoogleProxiesList proxiesList = this.getAllProxiesList(GoogleProxiesList.class) ;  
			return deleteAll(proxiesList); 
		}
		else
		{
			return deleteAll(this.getOnPremiseAllProxiesList()) ;
		}
		
	}
	
	private ArrayList<HttpResponse<String>> deleteAll(ArrayList<String> allProxiesList) {
		ArrayList<HttpResponse<String>> allResults = new ArrayList<HttpResponse<String>>();  
		for (int i = 0 ;  i< allProxiesList.size() ; i++ )
		{
			String proxyName = allProxiesList.get(i) ; 
			
			HttpResponse<String> result = null;
			try {
				result = deleteProxy(proxyName);
			} catch (UnirestException | IOException e) {
				e.printStackTrace();
			}
			
			if (result != null && result.getStatus() == 200)
			{
				System.out.println( "proxyName :" + proxyName + " Deleted");
			}
			allResults.add(result) ; 
			
		}
		return allResults;
	}

	public  ArrayList<HttpResponse<String>> deleteAll(GoogleProxiesList proxiesList) throws UnirestException, IOException
	{
		ArrayList<HttpResponse<String>> failedResult = new ArrayList<HttpResponse<String>>();  
		for (com.smartvalue.apigee.rest.schema.proxy.google.auto.GoogleProxy proxy : proxiesList.getProxies())
		{
			String proxyName = proxy.getName() ; 
			System.out.println( "proxyName :" + proxyName + " Deleted");
			HttpResponse<String> result = deleteProxy(proxyName) ; 
			int status = result.getStatus() ; 
			if (status != 200)
			{
				failedResult.add(result) ; 
			}
		}
		return failedResult;
	}
	
	public  ExportResults exportAll(String folderDest , String serlizeFileName) throws Exception
	{
		// Keep a copy of the current proxies deployment statuses in a file in case a roll back of a Load Process is required  
		serializeDeployStatus(serlizeFileName);
		return exportAll(getAllProxiesList() , folderDest ); 
	} 
	public  ExportResults exportAll(String folderDest) throws Exception
	{
		return exportAll(getAllProxiesList() , folderDest ); 
	}
	

	public ArrayList<String>  getAllProxiesList() throws Exception
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
	

	private  ExportResults exportAll( ArrayList<String> proxiesList , String folderDest) throws UnirestException, IOException
	{
		
		ExportResults exportResults = new ExportResults();  
		{
			for (String proxyName : proxiesList)
			{
				System.out.println( "Start Exporting Proxy :" + proxyName );
				Proxy proxy = this.getOrganization().getProxy(proxyName); 
				exportResults.addAll( proxy.exportAllDeployedRevisions(folderDest)) ;
				
			}
		}
		return exportResults;
	}

	@Override
	public String getResourcePath() {
		return "/v1/organizations/"+orgName+"/apis";
	}

	@Override
	public String getApigeeObjectType() {
		return "apis";
	}

	@Override
	public ArrayList<ApigeeObjectTransformer> buildTransformers() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, FileNotFoundException, IOException {
		return this.getMs().getInfra().buildProxyTransformers();
	}

	
	public HashMap<String , HashMap<String, ArrayList<String>>> getDeploymentStatus() throws UnirestException, IOException, Exception
	{
		HashMap<String , HashMap<String, ArrayList<String>>> result = new HashMap<String , HashMap<String, ArrayList<String>>> () ;  
		for ( String proxyName : this.getAllProxiesList())
		{
			Proxy proxy = this.getOrganization().getProxy(proxyName);
			HashMap<String, ArrayList<String>> proxyDeployMentREvisions  = proxy.getDeployedRevisions()	 ;
			result.put(proxyName, proxyDeployMentREvisions) ; 
		}
		return result ; 
	}	
	
}
