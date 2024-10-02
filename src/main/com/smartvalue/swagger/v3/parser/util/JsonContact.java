package com.smartvalue.swagger.v3.parser.util;

import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.swagger.v3.oas.models.info.Contact;

public class JsonContact extends Contact implements Jsonable {

	public JsonContact(Contact contact) {
		// TODO Auto-generated constructor stub
		this.setEmail(contact.getEmail());
		this.setExtensions(contact.getExtensions());
		this.setName(contact.getName());
		this.setUrl(contact.getUrl());
	}

	@Override
	public String toJsonString() throws JsonMappingException, JsonProcessingException {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        FifoMap<String , Object > elements = new FifoMap<String , Object >() ;
        elements.put("name", getName()) ; 
        elements.put("url", getUrl()) ;
        elements.put("email", getEmail()) ;
        sb = Jsonable.appendElements(sb, elements);
     
        sb.append("}");
        return sb.toString();
    }
	
	 /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}
