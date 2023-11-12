package com.smartvalue.apigee.configuration.infra;

public class SysAdminCred {
private String Username ; 
private String Password ; 

 private String clientId ; 
 private String clientSecret ;

 
 
 public String getClientId() {
	return clientId;
}
public void setClientId(String clientId) {
	this.clientId = clientId;
}
public String getClientSecret() {
	return clientSecret;
}
public void setClientSecret(String clientSecret) {
	this.clientSecret = clientSecret;
}
public String getUsername() {
	return Username;
}
public void setUsername(String username) {
	Username = username;
}
public String getPassword() {
	return Password;
}
public void setPassword(String password) {
	Password = password;
}
}
