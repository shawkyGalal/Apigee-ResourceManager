package com.smartvalue.swagger.v3.parser.util;

import io.swagger.v3.oas.models.info.License;

public class JsonLicense extends License  implements Jsonable{

    public JsonLicense(License license) {
    	this.setExtensions(license.getExtensions());
    	this.setUrl(license.getUrl());
    	this.setName(license.getName());
    	this.setIdentifier(license.getIdentifier());
	}

	public String toJsonString() {
        	StringBuilder sb = new StringBuilder();
        	sb.append("{\n");
        	sb.append("    name: ").append(toIndentedString(getName())).append("\n");
	        sb.append("    url: ").append(toIndentedString(getUrl())).append("\n");
	        sb.append("    identifier: ").append(toIndentedString(getIdentifier())).append("\n");
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
