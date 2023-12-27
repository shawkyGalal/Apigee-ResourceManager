package com.smartvalue.apigee.rest.schema;

import java.util.List;

public abstract class AccessToken {
	
	public abstract String  getAccess_token() ;
	public abstract void setAccess_token(String accessToken); 

	public abstract void setExpires_in (int exp) ; 
	public abstract int getExpires_in() ;
	
	
	List<String> scopes ; 
	public void setScopes(List<String> m_scopes) {
		 this.scopes = m_scopes ; 
		
	} 

}
