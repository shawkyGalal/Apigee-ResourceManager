package com.smartvalue.apigee.configuration.infra;

public class Region {
	private String MgmServerUrl ;
	private String name ;
	private String tokenUrl ;
	private String OauthMgmServerUrl ;
	private String podGateWayName ; 
	public String getMgmServerUrl() {
		return MgmServerUrl;
	}
	public void setMgmServerUrl(String mgmServerUrl) {
		MgmServerUrl = mgmServerUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTokenUrl() {
		return tokenUrl;
	}
	public void setTokenUrl(String tokenUrl) {
		this.tokenUrl = tokenUrl;
	}
	public String getOauthMgmServerUrl() {
		return OauthMgmServerUrl;
	}
	public void setOauthMgmServerUrl(String oauthMgmServerUrl) {
		OauthMgmServerUrl = oauthMgmServerUrl;
	}
	public String getPodGateWayName() {
		return podGateWayName;
	}
	public void setPodGateWayName(String podGateWayName) {
		this.podGateWayName = podGateWayName;
	} 
}
