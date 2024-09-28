package com.smartvalue.swagger.v3.parser.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;


import io.swagger.v3.oas.models.annotations.OpenAPI31;

public class Paths extends LinkedHashMap<String, PathItem> {
    public Paths() {
    }

    public Paths(io.swagger.v3.oas.models.Paths paths) {
    	for ( java.util.Map.Entry<String, io.swagger.v3.oas.models.PathItem>  entry :  paths.entrySet() )
		{
			String key = entry.getKey();
			PathItem value = new PathItem(entry.getValue()) ; 
			this.put(key, value) ; 
		}
	}

	private java.util.Map<String, Object> extensions = null;

    public Paths addPathItem(String name, PathItem item) {
        this.put(name, item);
        return this;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Paths paths = (Paths) o;
        return Objects.equals(this.extensions, paths.extensions) &&
                super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(extensions, super.hashCode());
    }

    public java.util.Map<String, Object> getExtensions() {
        return extensions;
    }

    public void addExtension(String name, Object value) {
        if (name == null || name.isEmpty() || !name.startsWith("x-")) {
            return;
        }
        if (this.extensions == null) {
            this.extensions = new java.util.LinkedHashMap<>();
        }
        this.extensions.put(name, value);
    }

    @OpenAPI31
    public void addExtension31(String name, Object value) {
        if (name != null && (name.startsWith("x-oas-") || name.startsWith("x-oai-"))) {
            return;
        }
        addExtension(name, value);
    }

    public void setExtensions(java.util.Map<String, Object> extensions) {
        this.extensions = extensions;
    }

    public Paths extensions(java.util.Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String toJsonString() {
        StringBuilder sb = new StringBuilder();
        sb.append(toIndentedString("{\n"));
        int counter = 0 ; 
        int size = this.size() ; 
        for ( java.util.Map.Entry<String, PathItem> entry : this.entrySet() )
        {
        	counter ++; 
        	String key = entry.getKey(); 
        	PathItem value = entry.getValue() ; 
        	sb.append("\"").append(toIndentedString(key)).append("\" :") ;
        	sb.append("    \n").append(toIndentedString(value)) ;
        	if (counter < size ) sb.append(",").append(" \n") ; 
        	
        }

        sb.append( toIndentedString("\n}"));
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

