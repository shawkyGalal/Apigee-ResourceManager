package com.smartvalue.apigee.rest.schema;

public class ApigeeAccessToken {

	private String access_token;
	private String token_type;
	private String refresh_token;
	private String[] api_product_list_json ; 
	private String client_id ; 
	private String application_name ; 
	private String scope ; 
	private String status ; 
	private int expires_in ; 
	private String authoirizationCode ; 
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	
	@Override
	public String toString() {
		return "AccessToken [access_token=" + access_token + ", token_type=" + token_type + ", refresh_token="
				+ refresh_token + "]";
	}
	public String[] getApi_product_list_json() {
		return api_product_list_json;
	}
	public void setApi_product_list_json(String[] api_product_list_json) {
		this.api_product_list_json = api_product_list_json;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getApplication_name() {
		return application_name;
	}
	public void setApplication_name(String application_name) {
		this.application_name = application_name;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
	public String getAuthoirizationCode() {
		return authoirizationCode;
	}
	public void setAuthoirizationCode(String authoirizationCode) {
		this.authoirizationCode = authoirizationCode;
	}
	
}


