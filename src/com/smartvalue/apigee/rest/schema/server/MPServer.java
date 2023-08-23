package com.smartvalue.apigee.rest.schema.server;


import java.io.IOException;
import java.util.ArrayList;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.rest.schema.environment.Environment;
import com.smartvalue.apigee.rest.schema.organization.Organization;

public class MPServer extends Server {
	
	
	
	
	private  ArrayList<String>  associateWithEnvironment(Organization org , Environment env , String m_operation ) throws UnirestException, IOException
	{
		String path = "/v1/o/"+org.getName()+"/e/"+env.getName()+"/servers" ; 
		String uuid = this.getuUID() ;
		@SuppressWarnings("unchecked")
		ArrayList<String>  result =  this.getManagmentServer().executePostMgmntAPI(path, ArrayList.class, "action="+m_operation+"&uuid=" + uuid , "application/x-www-form-urlencoded") ; 
		return result ; 
	}
	
	public ArrayList<String> removeFromEnvironmnt(Organization org , Environment env ) throws UnirestException, IOException
	{
		return associateWithEnvironment(org , env , "remove" ) ; 
	}
	
	public ArrayList<String> addToEnvironmnt(Organization org , Environment env ) throws UnirestException, IOException
	{
		return associateWithEnvironment(org , env , "add" ) ; 
	}
	
}
