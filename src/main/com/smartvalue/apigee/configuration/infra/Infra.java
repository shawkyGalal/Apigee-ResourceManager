package com.smartvalue.apigee.configuration.infra;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartvalue.apigee.configuration.infra.googleServiceAccount.auto.GoogleServiceAccount;
import com.smartvalue.apigee.resourceManager.MyServerProfile;
import com.smartvalue.apigee.rest.schema.ApigeeAccessToken;

public class Infra {
	private String Name ; 
	private SysAdminCred sysadminCred ; 
	private String Ansible_hosts_file ; 
	private DevPortal DevPortal ; 
	//private String MgmServerUrl ;
	//private String OauthMgmServerUrl ; 
	private ArrayList<Region> regions; 
	private String AuthType ;
	//private String tokenUrl ; 
	
	private Boolean googleCloud;
	
	@JsonProperty("googleServiceAccount")
	private GoogleServiceAccount googleServiceAccount ;  // if the infra is a Google Cloud infra
	
	private int connectionTimeout =0 ; //  The timeout until a connection with the server is established (in milliseconds). Default is 10000. Set to zero to disable the timeout.
	private int socketTimeout = 1000; //The timeout to receive data (in milliseconds). Default is 60000. Set to zero to disable the timeout.
	 
	
	public SysAdminCred getSysadminCred() {
		return sysadminCred;
	}
	public void setSysadminCred(SysAdminCred sysadminCred) {
		this.sysadminCred = sysadminCred;
	}
	public String getAnsible_hosts_file() {
		return Ansible_hosts_file;
	}
	public void setAnsible_hosts_file(String ansible_hosts_file) {
		Ansible_hosts_file = ansible_hosts_file;
	}
	public DevPortal getDevPortal() {
		return DevPortal;
	}
	public void setDevPortal(DevPortal devPortal) {
		DevPortal = devPortal;
	}
	/*
	public String getMgmServerUrl() {
		return MgmServerUrl;
	}
	public void setMgmServerUrl(String mgmServerUrl) {
		MgmServerUrl = mgmServerUrl;
	}
	*/
	public String getAuthType() {
		return AuthType;
	}
	public void setAuthType(String authType) {
		AuthType = authType;
	}
	/*
	public String getTokenUrl() {
		return tokenUrl;
	}
	public void setTokenUrl(String tokenUrl) {
		this.tokenUrl = tokenUrl;
	}
	*/
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	} 
	//----------------
	public ArrayList<String> getServers() {
		return null;
		
	}
	/*
	public String getOauthMgmServerUrl() {
		return OauthMgmServerUrl;
	}
	public void setOauthMgmServerUrl(String oauthMgmServerUrl) {
		OauthMgmServerUrl = oauthMgmServerUrl;
	}
	*/
	public int getConnectionTimeout() {
		return connectionTimeout;
	}
	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	public int getSocketTimeout() {
		return socketTimeout;
	}
	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}
	public ArrayList<Region> getRegions() {
		return regions;
	}
	public void setRegions(ArrayList<Region>regions) {
		this.regions = regions;
	}
	
	public Region getRegion(String regionName)
	{
		Region result = null ; 
		for (Region region : this.getRegions())
		{
			if (region.getName().equalsIgnoreCase(regionName)) 
			{
				result = region ; 
				break; 
			}
			 
		}
		return result ; 
		
	}
	
	public ManagementServer getManagementServer(String m_region) throws Exception
	{
		ManagementServer result = null ; 
		for (ManagementServer mServer :  this.getManagementServers() )
		{
			if (mServer.getRegion().equalsIgnoreCase(m_region))
			{
				result = mServer ; 
				break; 
			}
			
		}
		return result ; 
	}
	
	ArrayList<ManagementServer> managementServers ;  
	private ArrayList<ManagementServer> getManagementServers() throws Exception
	{
		
		if ( managementServers == null )
		{
			managementServers = new ArrayList<ManagementServer>() ; 
			
			{
				for (Region region : this.getRegions())
				{
					managementServers.add((this.buildManagementServer(region.getName()))); 
				}
			}
		}
		return managementServers;
	}
	

	private ManagementServer buildManagementServer(String m_region) throws Exception
	{
		ManagementServer ms = new ManagementServer() ; 
		MyServerProfile m_serverProfile = ms.mapConfigFileToServerProfile(this , m_region ) ;
		ms.setServerProfile(m_serverProfile); 
		ms.setInfra(this);
		boolean oauthType = ms.getServerProfile().getAuthType() != null && ms.getServerProfile().getAuthType().equalsIgnoreCase("OAuth") ; 
		Boolean isGoogleCloudBoolean = this.getGooglecloud() ; 
		if (isGoogleCloudBoolean != null && isGoogleCloudBoolean || oauthType)
		{
			ApigeeAccessToken at = ms.getAccess_token(true) ;
			//ms.serverProfile.setBearerToken(at.getAccess_token()) ;
			//ms.serverProfile.setRefreshToken(at.getRefresh_token()) ;
		}
		ms.setRegion(m_region);
		
		return ms ; 
	}

	
	public GoogleServiceAccount getGoogleServiceAccount() {
		return googleServiceAccount;
	}

	public Boolean getGooglecloud() {
		return googleCloud;
	}

	
}
