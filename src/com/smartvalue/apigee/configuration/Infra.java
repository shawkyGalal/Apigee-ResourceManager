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
	

}
