package com.smartvalue.swagger.v3.parser.util;

import java.util.TreeMap;

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
	public String toJsonString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        TreeMap<String , Object > elements = new TreeMap<String , Object >() ;
        elements.put("name", getName()) ; 
        elements.put("url", getUrl()) ;
        elements.put("email", getEmail()) ;
        sb = Jsonable.appendElements(sb, elements);
        
        /*
        if(getName() != null ) sb.append("    name: ").append(toIndentedString(getName())).append("\n");
        if(getUrl() != null ) sb.append("    url: ").append(toIndentedString(getUrl())).append("\n");
        if(getEmail() != null ) sb.append("    email: ").append(toIndentedString(getEmail())).append("\n");
        */
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
