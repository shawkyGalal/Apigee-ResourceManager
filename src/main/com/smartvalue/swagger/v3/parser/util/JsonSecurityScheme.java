package com.smartvalue.swagger.v3.parser.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.swagger.v3.oas.models.security.SecurityScheme;

public class JsonSecurityScheme extends SecurityScheme implements Jsonable{

	private JsonOauthFlows jsonFlows ;
	
	public JsonSecurityScheme(SecurityScheme m_secSchema) {
		// TODO Auto-generated constructor stub
		this.set$ref(m_secSchema.get$ref());
		this.setBearerFormat(m_secSchema.getBearerFormat());
		this.setDescription(m_secSchema.getDescription());
		this.setExtensions(m_secSchema.getExtensions());
		if (m_secSchema.getFlows() != null ) {
			this.setFlows(m_secSchema.getFlows());
			this.setJsonFlows(new JsonOauthFlows (m_secSchema.getFlows()));
		}
		this.setIn(m_secSchema.getIn());
		this.setName(m_secSchema.getName());
		this.setOpenIdConnectUrl(m_secSchema.getOpenIdConnectUrl());
		this.setScheme(m_secSchema.getScheme());
		this.setType(m_secSchema.getType());
	}

	@Override
	public String toJsonString() throws JsonMappingException, JsonProcessingException {
		 StringBuilder sb = new StringBuilder();
	        sb.append("{\n");

	        FifoMap<String , Object > elements = new FifoMap<String , Object >() ;
	        elements.put("type", getType().toString()) ; 
	        elements.put("description", getDescription()) ;
	        elements.put("name", getName()) ;
	        elements.put("in", getIn()) ;
	        elements.put("scheme", getScheme()) ;
	        elements.put("bearerFormat", getBearerFormat()) ;
	        elements.put("flows", getJsonFlows()) ;
	        elements.put("openIdConnectUrl", getOpenIdConnectUrl()) ;
	        elements.put("$ref", get$ref()) ;
	        
	        sb = Jsonable.appendElements(sb, elements);
	       
	        sb.append("}");
	        return sb.toString();
	}

	public JsonOauthFlows getJsonFlows() {
		return jsonFlows;
	}

	public void setJsonFlows(JsonOauthFlows jsonOauthFlows) {
		this.jsonFlows = jsonOauthFlows;
	}

}
