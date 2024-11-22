package com.smartvalue.apigee.resourceManager;
//import io.apigee.buildTools.enterprise4g.utils.ServerProfile;
public class MyServerProfile extends ServerProfile {

	private String oauthHostURL ;
	private int connectionTimeout =0  ; //  The timeout until a connection with the server is established (in milliseconds). Default is 10000. Set to zero to disable the timeout.
	private int socketTimeout = 10000; //The timeout to receive data (in milliseconds). Default is 60000. Set to zero to disable the timeout.
	private String region ; 

	public String getOauthHostURL() {
		return oauthHostURL;
	}

	public void setOauthHostURL(String oauthHostURL) {
		this.oauthHostURL = oauthHostURL;
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

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	
}
