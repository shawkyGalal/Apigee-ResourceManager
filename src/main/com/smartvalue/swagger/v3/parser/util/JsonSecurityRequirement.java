package com.smartvalue.swagger.v3.parser.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class JsonSecurityRequirement extends JsonLinkedHashMap<String, List<String>>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JsonSecurityRequirement(io.swagger.v3.oas.models.security.SecurityRequirement securityRequirement) 
	{
		for ( java.util.Map.Entry<String, List<String>>  entry :  securityRequirement.entrySet() )
		{
			String key = entry.getKey();
			List<String> value = entry.getValue() ; 
			this.put(key, value) ; 
		}
	}
	

    public JsonSecurityRequirement addList(String name, String item) {
        this.put(name, Arrays.asList(item));
        return this;
    }

    public JsonSecurityRequirement addList(String name, List<String> item) {
        this.put(name, item);
        return this;
    }

    public JsonSecurityRequirement addList(String name) {
        this.put(name, new ArrayList<>());
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
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SecurityRequirement {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
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
