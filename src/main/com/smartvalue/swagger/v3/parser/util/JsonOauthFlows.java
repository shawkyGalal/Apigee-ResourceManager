package com.smartvalue.swagger.v3.parser.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.swagger.v3.oas.models.security.OAuthFlows;

public class JsonOauthFlows extends OAuthFlows implements Jsonable{

	public JsonOauthFlows(OAuthFlows flows) {
		this.setAuthorizationCode(flows.getAuthorizationCode());
		this.setClientCredentials(flows.getClientCredentials());
		this.setExtensions(flows.getExtensions());
		this.setImplicit(flows.getImplicit());
		this.setPassword(flows.getPassword());
	}

	
	@Override
	public String toJsonString() throws JsonMappingException, JsonProcessingException {
		StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        
        FifoMap<String , Object > elements = new FifoMap<String , Object >() ;
        if (getImplicit() != null)  		elements.put("implicit", new JsonOauthFlow (getImplicit())  ) ; 
        if (getPassword() != null) 			elements.put("password", new JsonOauthFlow (getPassword()) ) ; 
        if (getClientCredentials() != null) elements.put("clientCredentials", new JsonOauthFlow ( getClientCredentials()) ) ; 
        if (getAuthorizationCode() != null) elements.put("authorizationCode", new JsonOauthFlow ( getAuthorizationCode()) ) ; 
        sb = Jsonable.appendElements(sb, elements);
        
        sb.append("}");
        return sb.toString();
	}

}
