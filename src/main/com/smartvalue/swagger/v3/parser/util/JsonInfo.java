package com.smartvalue.swagger.v3.parser.util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class JsonInfo extends io.swagger.v3.oas.models.info.Info implements Jsonable {

	private JsonContact jsonContact = null;
    private JsonLicense jsonLicense = null;
    
	public JsonInfo(io.swagger.v3.oas.models.info.Info m_info) {
		if (m_info.getContact() != null) this.setJsonContact( new JsonContact ( m_info.getContact()));
		if (m_info.getDescription() != null) this.setDescription(m_info.getDescription());
		if (m_info.getExtensions() != null) this.setExtensions(m_info.getExtensions());
		if (m_info.getLicense() != null) this.setJsonLicense(new JsonLicense( m_info.getLicense()));
		if (m_info.getSummary() != null) this.setSummary(m_info.getSummary());
		if (m_info.getTermsOfService() != null) this.setTermsOfService(m_info.getTermsOfService());
		if (m_info.getTitle() != null) this.setTitle(m_info.getTitle());
		if (m_info.getVersion() != null) this.setVersion(m_info.getVersion());
	}
	
	
	public String toJsonString() throws JsonMappingException, JsonProcessingException {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        
        FifoMap<String , Object > elements = new FifoMap<String , Object >() ;
        elements.put("title", getTitle()) ; 
        elements.put("description", getDescription()) ;
        elements.put("summary", getSummary()) ;
        elements.put("termsOfService", getTermsOfService()) ;
        elements.put("contact", getJsonContact()) ;
        elements.put("license", getLicense()) ;
        elements.put("version", getVersion()) ;
        
        sb = Jsonable.appendElements(sb, elements);
        sb.append("\n}");
        return sb.toString();
    }
	

	public JsonContact getJsonContact() {
		return jsonContact;
	}


	public void setJsonContact(JsonContact jsonContact) {
		this.jsonContact = jsonContact;
	}


	public JsonLicense getJsonLicense() {
		return jsonLicense;
	}


	public void setJsonLicense(JsonLicense jsonLicense) {
		this.jsonLicense = jsonLicense;
	}



	
}
