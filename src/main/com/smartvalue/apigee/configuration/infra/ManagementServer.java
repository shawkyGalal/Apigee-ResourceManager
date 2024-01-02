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
import com.mashape.unirest.request.HttpRequestWithBody;
import com.smartvalue.apigee.configuration.filteredList.FilteredList;
import com.smartvalue.apigee.configuration.infra.googleAccessToken.auto.GoogleAccessToken;
import com.smartvalue.apigee.configuration.infra.googleServiceAccount.GoogleServiceAccount;
import com.smartvalue.apigee.resourceManager.MyServerProfile;
import com.smartvalue.apigee.resourceManager.helpers.Helper;
import com.smartvalue.apigee.rest.schema.AccessToken;
import com.smartvalue.apigee.rest.schema.ApigeeAccessToken;
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

public class ManagementServer extends Server{
	
	private MyServerProfile serverProfile ;  
	HashMap <String , Organization> orgs = new HashMap <String , Organization>();
	private Infra infra ;  
	private String infraName ; 
	
	/*
	public ManagementServer(Infra m_infra , String m_regionName  ) throws UnirestException 
	{
		MyServerProfile m_serverProfile = mapConfigFileToServerProfile(m_infra , m_regionName ) ;
		serverProfile = m_serverProfile; 
		if (serverProfile.getAuthType().equalsIgnoreCase("OAuth") ) 
		{
			ApigeeAccessToken at = this.getAccess_token() ;
			this.serverProfile.setBearerToken(at.getAccess_token()) ;
			this.serverProfile.setRefreshToken(at.getRefresh_token()) ;
		}
		this.setRegion(m_regionName);
		this.setInfra(m_infra);
	}
	*/
	protected  MyServerProfile mapConfigFileToServerProfile( Infra m_infra ) { 
		this.setInfraName(m_infra.getName());
		MyServerProfile result = new MyServerProfile() ;
		
		
		return result;
	}
	protected  MyServerProfile mapConfigFileToServerProfile( Infra m_infra , String m_regionName) {
		this.setInfraName(m_infra.getName());
		MyServerProfile result = new MyServerProfile() ;
		
		Region region = m_infra.getRegion(m_regionName) ; 
		result.setTokenUrl(region.getTokenUrl());
		result.setOauthHostURL(region.getOauthMgmServerUrl());
		result.setAuthType(m_infra.getAuthType());
		result.setConnectionTimeout(m_infra.getConnectionTimeout());
		result.setSocketTimeout(m_infra.getSocketTimeout());
		
		Boolean isGoogleCloud = m_infra.getGooglecloud() ; 
		if  ( !(isGoogleCloud != null && isGoogleCloud) ) 
		{
			result.setHostUrl(region.getMgmServerUrl());
			result.setCredential_user(m_infra.getSysadminCred().getUsername());
			result.setCredential_pwd(m_infra.getSysadminCred().getPassword());
			result.setClientId(m_infra.getSysadminCred().getClientId());
			result.setClientSecret(m_infra.getSysadminCred().getClientSecret());
			
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
		// TODO Auto-generated method stub
		 String apiPath = "/v1/regions/" ; 
		 @SuppressWarnings("unchecked")
		ArrayList<String> result  = this.executeGetMgmntAPI(apiPath , ArrayList.class) ; 
		return result;
	}
	
	public ArrayList<String> getRegionPods(String m_region) throws UnirestException, IOException {
		// TODO Auto-generated method stub
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
			String[] orgNames  = this.executeGetMgmntAPI(apiPath , String[].class) ;  
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
	private String  getAuthorizationHeader()
	{
		String authorization = null ; 
		if (this.getServerProfile().getAuthType().equalsIgnoreCase("Basic"))
		{
			authorization = "Basic "+ new String(Base64.encode((this.getServerProfile().getCredential_user() + ":" + this.getServerProfile().getCredential_pwd()).getBytes()), Charset.forName("UTF-8")) ; 
		}
		else if (this.getServerProfile().getAuthType().equalsIgnoreCase("OAuth"))
		{
			String accessToken  = this.getAccessToken().getAccess_token() ;
			 authorization = "Bearer "+ accessToken ; 
		}
		return authorization ; 
	}
	
	private String getHostUrl()
	{
		String hostUrl ; 
		if (this.getServerProfile().getAuthType().equalsIgnoreCase("Basic"))
		{hostUrl = this.getServerProfile().getHostUrl() ;}
		else {hostUrl = this.getServerProfile().getOauthHostURL() ; }
		return hostUrl ; 
	}
	public HttpResponse<String> getPostFileHttpResponse(String m_apiPath ,  String postFileName ) throws UnirestException, IOException  {
		String hostUrl = getHostUrl () ; 
		String authorization = getAuthorizationHeader() ; 
		HttpRequestWithBody request = Unirest.post(hostUrl + m_apiPath)
				.header("Authorization", authorization ) ; 
		request.field("file", new File(postFileName)); 
		HttpResponse<String> response = request.asString();
		return response ;  
	}
	public HttpResponse<String> getPostHttpResponse(String m_apiPath , String m_body , String m_contentType ) throws UnirestException, IOException  {
		String hostUrl = getHostUrl () ; 
		String authorization = getAuthorizationHeader() ; 
		HttpRequestWithBody  xx = Unirest.post(hostUrl + m_apiPath)
								.header("Authorization", authorization ); 
		if (m_contentType != null) {
			xx.header("Content-Type", m_contentType ) ; 
		}
		if (m_body != null) {
			xx.body(m_body);  
		}
		HttpResponse<String> response = xx.asString();
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
		HttpResponse<String> response =  gr.asString() ; 
		
		return response ;
		
	}
	
	public HttpResponse<String> getDeleteHttpResponse(String m_apiPath ) throws UnirestException, IOException  {
		String hostUrl = getHostUrl() ;
		String authorization = getAuthorizationHeader() ; 
		HttpResponse<String> response  = Unirest.delete(hostUrl + m_apiPath).header("Authorization", authorization ).asString();
		return response ;
	}
	
	public HttpResponse<String> getPutHttpResponse(String m_apiPath ) throws UnirestException, IOException  {
		String hostUrl = getHostUrl() ;
		String authorization = getAuthorizationHeader() ; 
		HttpResponse<String> response  = Unirest.put(hostUrl + m_apiPath).header("Authorization", authorization ).asString();
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
	
	
	
	private void reNewAccessToken() throws Exception {
		 this.getAccess_token(true) ;
		//this.serverProfile.setRefreshToken(at.getRefresh_token()) ;
		
	}

	private AccessToken accessToken; 
	public AccessToken getAccess_token(boolean regenerate ) throws Exception 
	{
		if(accessToken == null || regenerate)
		{
		Unirest.setTimeouts(0, 0);
		accessToken = new ApigeeAccessToken();
		HttpResponse<String> response = null ; 
		Gson gson = new Gson();
		Boolean isGoogleCloudBoolean = this.infra.getGooglecloud() ;
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
		  response = Unirest.post(this.getServerProfile().getTokenUrl())
			  .header("Content-Type", "application/x-www-form-urlencoded")
			  .header("grant_type", "client_credentials")
			  .header("Authorization", "Basic "+ new String(Base64.encode((this.getServerProfile().getClientId() + ":" + this.getServerProfile().getClientSecret()).getBytes()), Charset.forName("UTF-8")))
			  .field("grant_type", "client_credentials")
			  .asString();
		  if (Helper.isConsideredSuccess(response.getStatus()) )   
		  {
			  accessToken = gson.fromJson(response.getBody(), ApigeeAccessToken.class);
		  }
		  else 
		  {
			throw new UnirestException ( response.getBody()) ; 
		  }
		}
	}
		
		
		return accessToken ; 
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String>  getTargetServersList01(String m_org , String m_env ) throws UnirestException, IOException
	{
		ArrayList<String> targetServersNames = null; 
		String apiPath = "/v1/organizations/"+m_org+"/environments/"+m_env+"/targetservers?expand=true" ; 
		targetServersNames = this.executeGetMgmntAPI(apiPath , ArrayList.class ) ; 
		return targetServersNames ; 
	}
	
	public ProductsServices getProductServices(String m_orgName)
	{
		ProductsServices srv = new ProductsServices( this, m_orgName) ; 
		srv.setMs(this);
		return srv;
		
	}

	public ServerServices getServerServices( )
	{
		ServerServices srv = new ServerServices(this) ; 
		srv.setMs(this);
		return srv;
	}
	
	public KvmServices getKeyValueMapServices(String m_orgName)
	{
		KvmServices  srv = new KvmServices(this , m_orgName) ; 
		srv.setMs(this);
		return srv;
		
	}
	
	public ProxyServices getProxyServices(String m_orgName )
	{
		ProxyServices  srv = new ProxyServices(this , m_orgName) ; 
		srv.setMs(this);
		return srv;
		
	}
	public SharedFlowServices getSharedFlowServices(String m_orgName )
	{
		SharedFlowServices  sfrv = new SharedFlowServices(this , m_orgName ) ; 
		sfrv.setMs(this);
		return sfrv;
	}
	
	public ApplicationServices getApplicationServices(String m_orgName )
	{
		ApplicationServices  appServices = new ApplicationServices(this , m_orgName ) ; 
		appServices.setMs(this);
		return appServices;
	}
	
	public DeveloperServices getDevelopersServices(String m_orgName) {
		DeveloperServices  developersServices = new DeveloperServices(this , m_orgName ) ; 
		developersServices.setMs(this);
		return developersServices;
	}
	
	public TargetServerServices getTargetServersServices(String m_orgName) {
		TargetServerServices  targetServerServices = new TargetServerServices(this , m_orgName) ; 
		return targetServerServices;
	}
	

	@Override
	public String getSimpleName() {
		return "management-server";
	}

	public ArrayList<MPServer>  getFreeMps(String m_region ) throws Exception {
		// TODO Auto-generated method stub
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
	public AccessToken getAccessToken() {
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
	
	public HttpResponse<String>  getAccessTokenByAuthCode( String authCode , String redirectUri) throws UnirestException
	{
		return  this.getInfra().getGoogleWebAppCredential().getAccessTokenByAuthCode(authCode, redirectUri); 
	}
	
	public void webLogin(String authCode , String redirectUri ) throws UnirestException
	{
		Gson json = new Gson(); 
		GoogleAccessToken webLoginAccessToken = json.fromJson( getAccessTokenByAuthCode(authCode, redirectUri).getBody() , GoogleAccessToken.class ) ;
		this.accessToken = webLoginAccessToken ; 
		
	}

	


}
