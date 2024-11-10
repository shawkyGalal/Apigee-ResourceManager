package com.smartvalue.swagger.v3.parser.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.swagger.v3.oas.models.media.XML;

public class JsonXml extends XML implements Jsonable {

	public JsonXml(XML xml) {
		this.setAttribute(xml.getAttribute());
		this.setExtensions(xml.getExtensions());
		this.setName(xml.getName());
		this.setNamespace(xml.getNamespace());
		this.setPrefix(xml.getPrefix());
		this.setWrapped(xml.getWrapped());
	}
	@Override
	public String toJsonString() throws JsonMappingException, JsonProcessingException {
		 
		        StringBuilder sb = new StringBuilder();
		        sb.append("{\n");
		        FifoMap<String , Object > elements = new FifoMap<String , Object >() ;
		        elements.put("name", getName()) ;
		        elements.put("namespace", getNamespace()) ;
		        elements.put("prefix", getPrefix()) ;
		        elements.put("attribute", getAttribute()) ;
		        elements.put("wrapped", getWrapped()) ; 
		        
		        sb = Jsonable.appendElements(sb, elements);
		        sb.append("\n}");
		        return sb.toString();
	}
	

}
