package com.smartvalue.swagger.v3.parser.util;

import java.util.List;

public class SecurityRequirement extends io.swagger.v3.oas.models.security.SecurityRequirement 
{

	public SecurityRequirement(io.swagger.v3.oas.models.security.SecurityRequirement securityRequirement) 
	{
		for ( java.util.Map.Entry<String, List<String>>  entry :  securityRequirement.entrySet() )
		{
			String key = entry.getKey();
			List<String> value = entry.getValue() ; 
			this.put(key, value) ; 
		}
		
	}
		
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("{\n");
        for ( java.util.Map.Entry<String, List<String>>  entry :  this.entrySet() )
		{
        	String key = entry.getKey();
			List<String> value = entry.getValue() ;
			sb.append("    \"").append(toIndentedString(key)).append("\"");
			sb.append("    : ").append(toIndentedString(value)).append("\n");
		}
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
