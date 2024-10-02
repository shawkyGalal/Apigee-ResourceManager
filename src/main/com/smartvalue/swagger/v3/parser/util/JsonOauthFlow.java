package com.smartvalue.swagger.v3.parser.util;

import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.swagger.v3.oas.models.security.OAuthFlow;

public class JsonOauthFlow extends OAuthFlow implements Jsonable  {

	private JsonLinkedHashMap<String, String> jsonScopes ; 
	public JsonOauthFlow(OAuthFlow m_oauthFlow) {
		this.setAuthorizationUrl(m_oauthFlow.getAuthorizationUrl());
		this.setExtensions(m_oauthFlow.getExtensions());
		this.setRefreshUrl(m_oauthFlow.getRefreshUrl());
		if (m_oauthFlow.getScopes() != null)
			{	this.setScopes(m_oauthFlow.getScopes());
				jsonScopes = new JsonLinkedHashMap<String, String>(m_oauthFlow.getScopes()) ; 
			}
		this.setTokenUrl(m_oauthFlow.getTokenUrl());
	}

	@Override
	public String toJsonString() throws JsonMappingException, JsonProcessingException {
		StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        
        FifoMap<String , Object > elements = new FifoMap<String , Object >() ;
        elements.put("authorizationUrl", getAuthorizationUrl()) ; 
        elements.put("tokenUrl", getTokenUrl()) ;
        elements.put("refreshUrl", getRefreshUrl()) ;
        elements.put("scopes", getJsonScopes()) ;
        elements.put("extensions", getExtensions()) ;
                 
        sb = Jsonable.appendElements(sb, elements);
                        
        sb.append("}");
        return sb.toString();
	}

	public JsonLinkedHashMap<String, String> getJsonScopes() {
		return jsonScopes;
	}

	public void setJsonScopes(JsonLinkedHashMap<String, String> jsonScopes) {
		this.jsonScopes = jsonScopes;
	}

	

}
