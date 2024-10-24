package com.smartvalue.apigee.rest.schema.server;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.AppConfig;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.rest.schema.environment.Environment;
import com.smartvalue.apigee.rest.schema.organization.Organization;

public class MPServer extends Server {
	

	private  ArrayList<String>  associateWithEnvironment( Environment env , String m_operation ) throws UnirestException, IOException
	{
		String path = AppConfig.BASE_BATH+env.getOrgName()+"/environments/"+env.getName()+"/servers" ; 
		String uuid = this.getuUID() ;
		@SuppressWarnings("unchecked")
		ArrayList<String>  result =  this.getManagmentServer().executePostMgmntAPI(path, ArrayList.class, "action="+m_operation+"&uuid=" + uuid , "application/x-www-form-urlencoded") ; 
		return result ; 
	}
	
	public ArrayList<String> removeFromEnvironmnt( Environment env ) throws UnirestException, IOException
	{
		return associateWithEnvironment( env , "remove" ) ; 
	}
	
	public ArrayList<String> addToEnvironmnt( Environment env ) throws UnirestException, IOException
	{
		return associateWithEnvironment( env , "add" ) ; 
	}
	
	public String getSimpleName()
	{
		return "message-processor" ; 
	}

	public HashMap<String , ArrayList<String>>  getAssociatedEnvs(String m_region ) throws Exception {
		ManagementServer ms = this.getManagmentServer(); 
		HashMap<String , ArrayList<String>> result = new HashMap<String , ArrayList<String>>() ; 
		for (String orgName : ms.getAllOrgNames() )
			{
				Organization org = ms.getOrgByName(orgName) ; 
				HashMap<String, Environment> envs = ms.getOrgByName(orgName).getEnvs(); 
				ArrayList<String> foundEnvs = new ArrayList<String>() ; 
				for ( String envName : envs.keySet())
				{
					Environment env =  org.getEnvByName(envName) ; 
					List<MPServer>  associatedMps = env.getMessageProcesors(m_region);
					
					if (associatedMps.contains(this))
					{
						foundEnvs.add(envName);
					}
				}
				if (foundEnvs.size() > 0 )
				{ result.put(orgName, foundEnvs) ; }  
			}
		return result;
	}
	
	
	  @Override
	    public int hashCode() {
	        int result = 1;
	        result = ((result* 31)+((this.getPod() == null)? 0 :this.getPod().hashCode()));
	        result = ((result* 31)+((this.getExternalHostName() == null)? 0 :this.getExternalHostName().hashCode()));
	        result = ((result* 31)+((this.getType() == null)? 0 :this.getType().hashCode()));
	        result = ((result* 31)+((this.getInternalIP() == null)? 0 :this.getInternalIP().hashCode()));
	        result = ((result* 31)+((this.getuUID() == null)? 0 :this.getuUID().hashCode()));
	        result = ((result* 31)+((this.getReachable() == null)? 0 :this.getReachable().hashCode()));
	        //result = ((result* 31)+((this.tags == null)? 0 :this.tags.hashCode()));
	        result = ((result* 31)+((this.getIsUp() == null)? 0 :this.getIsUp().hashCode()));
	        result = ((result* 31)+((this.getAdditionalProperties() == null)? 0 :this.getAdditionalProperties().hashCode()));
	        result = ((result* 31)+((this.getInternalHostName() == null)? 0 :this.getInternalHostName().hashCode()));
	        result = ((result* 31)+((this.getRegion() == null)? 0 :this.getRegion().hashCode()));
	        result = ((result* 31)+((this.getExternalIP() == null)? 0 :this.getExternalIP().hashCode()));
	        return result;
	    }

	    @Override
	    public boolean equals(Object other) {
	        if (other == this) {
	            return true;
	        }
	        if ((other instanceof Server) == false) {
	            return false;
	        }
	        Server rhs = ((Server) other);
	        int hashCode = this.hashCode() ;
	        int otherHashCode =  rhs.hashCode() ; 
	        return ( hashCode == otherHashCode) ; 
	        // return (((((((((((((this.pod == rhs.pod)||((this.pod!= null)&&this.pod.equals(rhs.pod)))&&((this.externalHostName == rhs.externalHostName)||((this.externalHostName!= null)&&this.externalHostName.equals(rhs.externalHostName))))&&((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type))))&&((this.internalIP == rhs.internalIP)||((this.internalIP!= null)&&this.internalIP.equals(rhs.internalIP))))&&((this.uUID == rhs.uUID)||((this.uUID!= null)&&this.uUID.equals(rhs.uUID))))&&((this.reachable == rhs.reachable)||((this.reachable!= null)&&this.reachable.equals(rhs.reachable))))&&((this.tags == rhs.tags)||((this.tags!= null)&&this.tags.equals(rhs.tags))))&&((this.isUp == rhs.isUp)||((this.isUp!= null)&&this.isUp.equals(rhs.isUp))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.internalHostName == rhs.internalHostName)||((this.internalHostName!= null)&&this.internalHostName.equals(rhs.internalHostName))))&&((this.region == rhs.region)||((this.region!= null)&&this.region.equals(rhs.region))))&&((this.externalIP == rhs.externalIP)||((this.externalIP!= null)&&this.externalIP.equals(rhs.externalIP))));
	    }
	
}
