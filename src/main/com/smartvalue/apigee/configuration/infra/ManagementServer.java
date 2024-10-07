package com.smartvalue.apigee.configuration.infra;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.security.crypto.codec.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.MultipartBody;
import com.smartvalue.apigee.configuration.AppConfig;
import com.smartvalue.apigee.configuration.filteredList.FilteredList;
import com.smartvalue.apigee.resourceManager.MyServerProfile;
import com.smartvalue.apigee.resourceManager.helpers.Helper;
import com.smartvalue.apigee.rest.cloud.OrganizationList.OrganizationList;
import com.smartvalue.apigee.rest.schema.AccessToken;
import com.smartvalue.apigee.rest.schema.ApigeeAccessToken;
import com.smartvalue.apigee.rest.schema.ApigeeService;
import com.smartvalue.apigee.rest.schema.application.ApplicationServices;
import com.smartvalue.apigee.rest.schema.developer.DeveloperServices;
import com.smartvalue.apigee.rest.schema.environment.Environment;
import com.smartvalue.apigee.rest.schema.keyValueMap.KvmServices;
import com.smartvalue.apigee.rest.schema.organization.Organization;
import com.smartvalue.apigee.rest.schema.product.ProductsServices;
import com.smartvalue.apigee.rest.schema.proxy.ProxyServices;
import com.smartvalue.apigee.rest.schema.server.MPServer;
import com.smartvalue.apigee.rest.schema.server.Server  ;
import com.smartvalue.apigee.rest.schema.server.ServerServices;
import com.smartvalue.apigee.rest.schema.sharedFlow.SharedFlowServices;
import com.smartvalue.apigee.rest.schema.targetServer.TargetServerServices;
import com.smartvalue.google.iam.GoogleServiceAccount;
import com.smartvalue.google.iam.GoogleWebAppCredential;
import com.smartvalue.google.iam.auto.GoogleAccessToken;

public class ManagementServer extends Server{
	
	private MyServerProfile serverProfile ;  
	HashMap <String , Organization> orgs = new HashMap <String , Organization>();
	private Infra infra ;  
	private String infraName ; 
	private GoogleAccessToken googleAccessToken ;
	private AppConfig appConfig ;
	private boolean onPremise = true ;  // New Attribute indicate wheather this management server is for an onpremise or for a google cloud
	private boolean internetProxyCleared = true; 
	
	// -- This Constructor should be used in case of accessing Google Cloud - no selected infra 
	public ManagementServer(AppConfig m_appConfig)
	{
		this.appConfig = m_appConfig ; 
	}

	public ManagementServer(Infra m_infra2) {
		this.infra = m_infra2 ; 
		this.mapConfigFileToServerProfile(); 
	}

	protected  MyServerProfile mapConfigFileToServerProfile(  ) { 
		this.setInfraName(this.infra.getName());
		MyServerProfile result = new MyServerProfile() ;
		
		
		return result;
	}
	protected  MyServerProfile mapConfigFileToServerProfile(  String m_regionName) {
		this.setInfraName(this.infra.getName());
		MyServerProfile result = new MyServerProfile() ;
		
		Region region = this.infra.getRegion(m_regionName) ; 
		result.setTokenUrl(region.getTokenUrl());
		result.setOauthHostURL(region.getOauthMgmServerUrl());
		result.setAuthType(this.infra.getAuthType());
		result.setConnectionTimeout(this.infra.getConnectionTimeout());
		result.setSocketTimeout(this.infra.getSocketTimeout());

		Boolean isGoogleCloud = this.infra.isGooglecloud() ; 
		if  ( !(isGoogleCloud != null && isGoogleCloud) ) 
		{
			result.setHostUrl(region.getMgmServerUrl());
			result.setCredential_user(this.infra.getSysadminCred().getUsername());
			result.setCredential_pwd(this.infra.getSysadminCred().getPassword());
			result.setClientId(this.infra.getSysadminCred().getClientId());
			result.setClientSecret(this.infra.getSysadminCred().getClientSecret());
			
		}
		
		return result;
	}
	/*
	public ManagementServer(MyServerProfile m_serverProfile ) 
	{	
		serverProfile = m_serverProfile; 
	}
   */

	public ArrayList<String> getRegions() throws UnirestException, IOException {
		 String apiPath = "/v1/regions" ; 
		 @SuppressWarnings("unchecked")
		ArrayList<String> result  = this.executeGetMgmntAPI(apiPath , ArrayList.class) ; 
		return result;
	}
	
	public ArrayList<String> getRegionPods(String m_region) throws UnirestException, IOException {
		 String apiPath = "/v1/regions/"+ m_region ; 
		 @SuppressWarnings("unchecked")
		ArrayList<String> result  = this.executeGetMgmntAPI(apiPath , ArrayList.class) ; 
		return result;
	}
	
	public HashMap <String , Organization>  getOrgs() throws UnirestException, IOException {
		return this.getOrgs(false) ; 
	}
	public String[] getAllOrgNames() throws UnirestException, IOException
	{
		String apiPath = "/v1/organizations/" ; 
		String[] orgNames  = this.executeGetMgmntAPI(apiPath , String[].class) ;
		return orgNames ;
	}
	public HashMap <String , Organization>  getOrgs(boolean m_refresh) throws UnirestException, IOException {
		if (orgs.size() ==0  || m_refresh )
		{
			String apiPath = "/v1/organizations/" ; 
			String[] orgNames ; 
			Boolean isGoogleCloud = this.getInfra().isGooglecloud() ;
			if (isGoogleCloud != null && isGoogleCloud)
			{
				OrganizationList orgList = this.executeGetMgmntAPI(apiPath , OrganizationList.class);
				orgNames = orgList.toArray(); 
			}
			else { orgNames  = this.executeGetMgmntAPI(apiPath , String[].class) ; }  
			for (String orgname : orgNames )
			{
				String path2 = "/v1/organizations/" + orgname ; 
				Organization org = this.executeGetMgmntAPI(path2 , Organization.class) ;
				org.setManagmentServer(this);
				orgs.put(orgname  , org) ; 
			}
		}
		return orgs ; 
	}
	public Organization getOrgByName(String m_org) throws UnirestException, IOException
	{
		return (Organization) this.getOrgs().get(m_org) ; 
	}
	private String  getAuthorizationHeader() throws IOException, UnirestException
	{
		String authorization = null ; 
		if (this.onPremise &&  this.getInfra() != null)
		{
			if (this.getInfra().getAuthType().equalsIgnoreCase("Basic"))
			{
				authorization = "Basic "+ new String(Base64.encode((this.getServerProfile().getCredential_user() + ":" + this.getServerProfile().getCredential_pwd()).getBytes()), Charset.forName("UTF-8")) ; 
			}
			else if (this.getInfra().getAuthType().equalsIgnoreCase("OAuth"))
			{
				String accessToken  = this.getAccessToken().getAccess_token() ;
				 authorization = "Bearer "+ accessToken ; 
			}
		}
		else
		{
			String accessToken  = this.getAccessToken().getAccess_token() ;
			 authorization = "Bearer "+ accessToken ; 
		}
		return authorization ; 
	}
	/**
		Retur Apigee Management API's URL configured in the app json config file for the selected Infra 
		// In case of Apigee Cloud , to allow indirect access to apigee cloud management api's  through MOJ AIO Apigee 
		//	Update app config file with  :		
		// googleApigeeAPIUrl = http://gcloud.apigee.moj.gov.local:9001/api-management
	**/
	private String getHostUrl()
	{
		String hostUrl ; 
		if ( isOnPremise() )
		{
			if (this.getServerProfile().getAuthType().equalsIgnoreCase("Basic"))
			{hostUrl = this.getServerProfile().getHostUrl() ;}
			else {hostUrl = this.getServerProfile().getOauthHostURL() ; }
		}
		else // assume user trying to access Apigee on Google cloud 
		{
			hostUrl = this.appConfig.getGoogleApigeeAPIUrl();  
			
		}
		return hostUrl ; 
	}
	public HttpResponse<String> getPostFileHttpResponse(String m_apiPath ,  String postFileName ) throws UnirestException, IOException  {
		
		String hostUrl = getHostUrl () ; 
		String authorization = getAuthorizationHeader() ; 
		HttpRequestWithBody request = Unirest.post(hostUrl + m_apiPath)
				.header("Authorization", authorization ) ; 
		request.field("file", new File(postFileName)); 
		HttpResponse<String> response = sendRequestByPassProxyIfNeeded(request) ; 
		return response ;  
	}
	
	private HttpResponse<String>  sendRequestByPassProxyIfNeeded(HttpRequest request ) throws UnirestException , IOException 
	{
		
		boolean byPassProxy = isByPassProxy(request.getUrl()) ;  
		if (byPassProxy) 
		{
			if (! this.internetProxyCleared) // if it is  not yet cleared 
			{ 
				Unirest.setProxy(null);
				internetProxyCleared = true ;
			}
		}
		
		else 
		{
			if (this.internetProxyCleared)
			{
				AppConfig x = (this.getInfra() != null ) ? this.getInfra().getParentCustomer().getParentConfig() : appConfig ;
				x.setInternetProxy(); 
				internetProxyCleared = false ;
			}
		}	
		
		HttpResponse<String> response = request.asString();
		response = handleResponseErrors(request, response) ; 
		return response ; 
		
	}
	
	public HttpResponse<String>  handleResponseErrors(HttpRequest request , HttpResponse<String> response) throws IOException, UnirestException 
	{
		HttpResponse<String> result = response; 
		if (! Helper.isConsideredSuccess(response.getStatus()) )   
		{
			if (response.getBody().contains("Access Token expired"))
			{
				this.reNewAccessToken() ; 
				result = request.asString(); 
			}
			else 
			throw new UnirestException ( response.getBody()) ; 
		}
		
		return result ; 
	}
	private static final String BY_PASS_HOSTS = "moj.gov.local" ;  
	private boolean isByPassProxy(String m_apiPath) {
		return m_apiPath.contains(BY_PASS_HOSTS);
	}

	public HttpResponse<String> getPostHttpResponse(String m_apiPath ) throws UnirestException, IOException  {
		return getPostHttpResponse(m_apiPath , null , null) ; 
	}
	
	public HttpResponse<String> getPostHttpResponse(String m_apiPath , String m_body , String m_contentType ) throws UnirestException, IOException  {
		String hostUrl = getHostUrl () ; 
		String authorization = getAuthorizationHeader() ; 
		HttpRequestWithBody  request = Unirest.post(hostUrl + m_apiPath)
								.header("Authorization", authorization ); 
		if (m_contentType != null) {
			request.header("Content-Type", m_contentType ) ; 
		}
		if (m_body != null) {
			request.body(m_body);  
		}
		HttpResponse<String> response = this.sendRequestByPassProxyIfNeeded(request);
		return response ;  
	}
	public HttpResponse<InputStream> getGetHttpBinResponse(String m_apiPath  ) throws UnirestException, IOException  {
		
		String hostUrl = getHostUrl() ;
		String authorization = getAuthorizationHeader() ; 
		GetRequest gr = Unirest.get(hostUrl + m_apiPath).header("Authorization", authorization ) ; 
		HttpResponse<InputStream> response =  gr.asBinary() ; 
		
		return response ;
		
	}
	public HttpResponse<String> getGetHttpResponse(String m_apiPath   ) throws UnirestException, IOException  {
		String hostUrl = getHostUrl() ;
		String authorization = getAuthorizationHeader() ; 
		GetRequest gr = Unirest.get(hostUrl + m_apiPath).header("Authorization", authorization ) ; 
		HttpResponse<String> response =  sendRequestByPassProxyIfNeeded(gr)  ; 
		
		return response ;
		
	}
	
	public HttpResponse<String> getDeleteHttpResponse(String m_apiPath ) throws UnirestException, IOException  {
		String hostUrl = getHostUrl() ;
		String authorization = getAuthorizationHeader() ; 
		HttpRequestWithBody  request = Unirest.delete(hostUrl + m_apiPath).header("Authorization", authorization ) ; 
		HttpResponse<String> response  = sendRequestByPassProxyIfNeeded(request);
		return response ;
	}
	
	public HttpResponse<String> getPutHttpResponse(String m_apiPath ) throws UnirestException, IOException  {
		String hostUrl = getHostUrl() ;
		String authorization = getAuthorizationHeader() ;
		HttpRequestWithBody  request = Unirest.put(hostUrl + m_apiPath).header("Authorization", authorization ) ; 
		HttpResponse<String> response  = sendRequestByPassProxyIfNeeded(request);
		return response ;
	}
	
private <T> T GsonClassMapper(HttpResponse<String> response ,  Class<T> classOfT  ) throws UnirestException 
{
	T result = null ; 
	if (Helper.isConsideredSuccess(response.getStatus()) )   
	{
		Gson gson = new Gson();
		result = gson.fromJson(response.getBody(),  classOfT);
	} 
	else {
		throw new UnirestException ( response.getBody()) ; 
	}
	return result ; // Primitives.wrap(classOfT).cast(result);
}
	
	public <T> T executeGetMgmntAPI(String m_apiPath ,  Class<T> classOfT  ) throws UnirestException, IOException
	{
		T result = null ; 
		HttpResponse<String> response =  this.getGetHttpResponse(m_apiPath) ;
		result = GsonClassMapper(response , classOfT  ) ; 
		return result ; // Primitives.wrap(classOfT).cast(result);
	}
	
	public <T> T executePostMgmntAPI(String m_apiPath ,  Class<T> classOfT , String m_body , String m_contentType ) throws UnirestException, IOException
	{
		T result = null ; 
		HttpResponse<String> response =  this.getPostHttpResponse(m_apiPath , m_body , m_contentType) ;
		result = GsonClassMapper(response , classOfT  ) ; 
		return result ; // Primitives.wrap(classOfT).cast(result);
	} 
	
	public <T> T executeMgmntAPI(String m_apiPath, Type typeOfT ) throws Exception 
	{
		return executeMgmntAPI( m_apiPath, typeOfT , null ) ;  
	}
	
	public <T> T executeMgmntAPI(String m_apiPath, Type typeOfT , String m_rootNodeName) throws Exception 
	{
		T result = null ; 
		HttpResponse<String> response = this.getGetHttpResponse(m_apiPath) ;
		if (Helper.isConsideredSuccess(response.getStatus()) )   
		{
			Gson gson = new Gson();
			String responseBody = response.getBody() ; 
			if (m_rootNodeName != null)
			{
				JsonObject convertedObject = new Gson().fromJson(responseBody, JsonObject.class);
				JsonElement elem  = convertedObject.get(m_rootNodeName) ; 
				responseBody  = gson.toJson(elem) ;
				//responseBody = elem.getAsString() ;

			}
			result = gson.fromJson(responseBody  ,  typeOfT);
		} 
		else {
			if (response.getBody().contains("Access Token expired"))
			{
				this.reNewAccessToken() ; 
				executeMgmntAPI(m_apiPath, typeOfT , m_rootNodeName ) ; 
			}
			else 
			throw new UnirestException ( response.getBody()) ; 
		}
		
		return result; //Primitives.wrap(listType).cast(result);
	}
	
	
	
	private void reNewAccessToken() throws IOException, UnirestException  {
		 this.getAccess_token(true) ;
	}

	private AccessToken accessToken; 
	public AccessToken getAccess_token(boolean regenerate ) throws IOException, UnirestException 
	{
		if(accessToken == null || regenerate)
		{
		accessToken = new ApigeeAccessToken();
		HttpResponse<String> response = null ; 
		Gson gson = new Gson();
		Boolean isGoogleCloudBoolean = this.infra.isGooglecloud() ;
		if (isGoogleCloudBoolean != null && isGoogleCloudBoolean )
		{
			GoogleServiceAccount googleServiceAccount = this.infra.getGoogleServiceAccount() ; 
			com.google.auth.oauth2.AccessToken googleAccessToken = googleServiceAccount.getGoogleCredentials().getAccessToken() ; // getGoogleAccessToken(googleServiceAccount.toJson()) ;
			 
			accessToken = new ApigeeAccessToken(); 
			//-- Start map Google Object atts to My Object atts 
			accessToken.setAccess_token(googleAccessToken.getTokenValue());
			int expiresIn = googleAccessToken.getExpirationTime().compareTo(new Date())/1000; 
			accessToken.setExpires_in(expiresIn); 
			accessToken.setScopes(googleAccessToken.getScopes()); 
		}
		else 
		{
			MultipartBody multiPartBody = Unirest.post(this.getServerProfile().getTokenUrl())
					  .header("Content-Type", "application/x-www-form-urlencoded")
					  .header("grant_type", "client_credentials")
					  .header("Authorization", "Basic "+ new String(Base64.encode((this.getServerProfile().getClientId() + ":" + this.getServerProfile().getClientSecret()).getBytes()), Charset.forName("UTF-8")))
					  .field("grant_type", "client_credentials") ; 
		  response = sendRequestByPassProxyIfNeeded(multiPartBody.getHttpRequest()) ; 
		  if (Helper.isConsideredSuccess(response.getStatus()) )   
		  {
			  accessToken = gson.fromJson(response.getBody(), ApigeeAccessToken.class);
		  }
		  else 
		  {
			throw new UnirestException ( "ResponseBody :" + response.getBody() + " Response Code : " + response.getStatus()) ; 
		  }
		}
	}
		
		
		return accessToken ; 
	}
	/**
	@SuppressWarnings("unchecked")
	public ArrayList<String>  getTargetServersList01(String m_org , String m_env ) throws UnirestException, IOException
	{
		ArrayList<String> targetServersNames = null; 
		String apiPath = "/v1/organizations/"+m_org+"/environments/"+m_env+"/targetservers?expand=true" ; 
		targetServersNames = this.executeGetMgmntAPI(apiPath , ArrayList.class ) ; 
		return targetServersNames ; 
	}
	**/
	public ApigeeService getProductServices()
	{
		return  new ProductsServices( this, this.getOrgName()) ; 
	}
	
	public ApigeeService getProductServices(String m_orgName)
	{
		return  new ProductsServices( this, m_orgName) ; 
	}

	public ServerServices  getServerServices( )
	{
		return  new ServerServices(this) ; 
	}
	
	public ApigeeService getKeyValueMapServices()
	{
		return  new KvmServices(this , this.getOrgName()) ; 
	}
	
	public ApigeeService getKeyValueMapServices(String m_orgName)
	{
		return  new KvmServices(this , m_orgName) ; 
	}
	
	public ApigeeService getProxyServices()
	{
		return  new ProxyServices(this , this.getOrgName()) ; 
	}
	
	public ApigeeService getProxyServices(String m_orgName )
	{
		return  new ProxyServices(this , m_orgName) ; 
	}
	
	public ApigeeService getSharedFlowServices()
	{
		return  new SharedFlowServices(this , this.getOrgName() ) ; 
	}
	
	public ApigeeService getSharedFlowServices(String m_orgName )
	{
		return  new SharedFlowServices(this , m_orgName ) ; 
	}
	
	public ApigeeService getApplicationServices()
	{
		return  new ApplicationServices(this , this.getOrgName() ) ; 
	}
	
	public ApigeeService getApplicationServices(String m_orgName )
	{
		return  new ApplicationServices(this , m_orgName ) ; 
	}
	
	public ApigeeService getDevelopersServices() {
		return  new DeveloperServices(this , this.getOrgName() ) ; 
	}
	
	public ApigeeService getDevelopersServices(String m_orgName) {
		return  new DeveloperServices(this , m_orgName ) ; 
	}
	
	public ApigeeService getTargetServersServices() {
		return  new TargetServerServices(this , this.getOrgName()) ; 
	}
	
	public ApigeeService getTargetServersServices(String m_orgName) {
		return  new TargetServerServices(this , m_orgName) ; 
	}
	
	public ArrayList<ApigeeService> getAllServices(String m_orgName)
	{
		ArrayList<ApigeeService> result = new ArrayList<ApigeeService>() ; 
		result.add(this.getProxyServices(m_orgName)) ; 
		result.add(this.getSharedFlowServices(m_orgName)) ;
		result.add(this.getDevelopersServices(m_orgName)) ;
		result.add(this.getApplicationServices(m_orgName)) ;
		result.add(this.getProductServices(m_orgName)) ;
		result.add(this.getTargetServersServices(m_orgName)) ;
		result.add(this.getKeyValueMapServices(m_orgName)) ;
		return result; 
	}
	

	@Override
	public String getSimpleName() {
		return "management-server";
	}

	public ArrayList<MPServer>  getFreeMps(String m_region ) throws Exception {
		ArrayList<MPServer> result = new ArrayList<MPServer>(); 
		FilteredList<MPServer> all =  this.getServerServices().getMPServers(m_region); 
		for ( MPServer mpServer : all )
		{
			HashMap<String , ArrayList<String>> associatedEnvs = mpServer.getAssociatedEnvs(m_region);
			if (associatedEnvs.size() == 0 )
			{ result.add(mpServer) ; }
		}
		
		return result;
	}

	public String getInfraName() {
		return infraName;
	}

	public void setInfraName(String infraName) {
		this.infraName = infraName;
	}
	
	private transient HashMap<String , HashMap<String , Environment>> storedEnvs ; 
	public HashMap<String , HashMap<String , Environment>> getStoredEnvs(boolean m_refresh) throws UnirestException, IOException
	{
		if (storedEnvs == null  || m_refresh )
		{
			storedEnvs= new HashMap<String , HashMap<String , Environment>> () ;
			for ( String orgName : this.getAllOrgNames())
			{
				storedEnvs.put(orgName , this.getOrgByName(orgName).getEnvs() ) ; 
			}
		}
		
		return storedEnvs ; 
	}

	public Infra getInfra() {
		return infra;
	}

	public void setInfra(Infra infra) {
		this.infra = infra;
	}
	public AccessToken getAccessToken() throws IOException, UnirestException {
		if (accessToken == null)
		{
			accessToken = this.getAccess_token(true); 
		}
		return accessToken;
	}
	public void setAccessToken(ApigeeAccessToken accessToken) {
		this.accessToken = accessToken;
	}


	public MyServerProfile getServerProfile() {
		return serverProfile;
	}
	public void setServerProfile(MyServerProfile serverProfile) {
		this.serverProfile = serverProfile;
	}
	
	
	
	private GoogleAccessToken requestAccessTokenByAuthCode( String authCode , String redirectUri) throws UnirestException
	{
		
		if (this.getInfra() != null)
		{
			appConfig = this.getInfra().getParentCustomer().getParentConfig();
		}
		GoogleWebAppCredential googleWebAppCredential = appConfig.getGoogleWebAppCredential() ; 
		googleAccessToken = googleWebAppCredential.getAccessTokenByAuthCode(authCode, redirectUri); 
		return  googleAccessToken ; 
	}
	
	public GoogleAccessToken getGoogelAccessToken()
	{
		return googleAccessToken; 
	}
	
	public void webLogin(String authCode , String redirectUri ) throws UnirestException
	{
		googleAccessToken = requestAccessTokenByAuthCode(authCode, redirectUri) ;
		this.accessToken = googleAccessToken ; 
	}

	public boolean isOnPremise() {
		return onPremise;
	}

	public void setOnPremise(boolean onPremise) {
		this.onPremise = onPremise;
	}
	
	public String getInfraUniqueName()
	{
		String result = null ; 
		if (this.isOnPremise() && this.getInfra() != null){
			result = this.getInfra().getParentCustomer().getName() +"."+ infra.getName() ; 
		}
		else if (this.getGoogelAccessToken() != null) {result = "Google Cloud InfraStructure : (" + this.getGoogelAccessToken().getSourceCredentials().getProjectId() +")" ;
		}
		return result ; 
	}
	
	

}
