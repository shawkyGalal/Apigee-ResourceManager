package com.smartvalue.swagger.v3.parser.util;

public interface Jsonable {

	public String toJsonString(); 
	
	public static String appendCommaEnter(boolean needed )
	{
		return ( (needed)? ",\n    ":"" )  ; 
	}
}
