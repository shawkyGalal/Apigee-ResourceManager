package com.smartvalue.apigee.configuration;

import java.util.ArrayList;

import com.smartvalue.apigee.resourceManager.DevPortal;
import com.smartvalue.apigee.resourceManager.SysAdminCred;

public class Infra {
	private String Name ; 
	private SysAdminCred sysadminCred ; 
	private String Ansible_hosts_file ; 
	private DevPortal DevPortal ; 
	private String MgmServerUrl ;
	private String OauthMgmServerUrl ; 
	private String AuthType ;
	private String tokenUrl ; 
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
	public String getMgmServerUrl() {
		return MgmServerUrl;
	}
	public void setMgmServerUrl(String mgmServerUrl) {
		MgmServerUrl = mgmServerUrl;
	}
	public String getAuthType() {
		return AuthType;
	}
	public void setAuthType(String authType) {
		AuthType = authType;
	}
	public String getTokenUrl() {
		return tokenUrl;
	}
	public void setTokenUrl(String tokenUrl) {
		this.tokenUrl = tokenUrl;
	}
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
	
	public String getOauthMgmServerUrl() {
		return OauthMgmServerUrl;
	}
	public void setOauthMgmServerUrl(String oauthMgmServerUrl) {
		OauthMgmServerUrl = oauthMgmServerUrl;
	}
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
	
	

}
