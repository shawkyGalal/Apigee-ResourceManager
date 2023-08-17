package com.smartvalue.apigee.resourceManager;
import io.apigee.buildTools.enterprise4g.utils.ServerProfile;
public class MyServerProfile extends ServerProfile {

	private String oauthHostURL ;

	public String getOauthHostURL() {
		return oauthHostURL;
	}

	public void setOauthHostURL(String oauthHostURL) {
		this.oauthHostURL = oauthHostURL;
	}  
}
