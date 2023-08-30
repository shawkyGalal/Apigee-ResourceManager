package com.smartvalue.apigee.rest.schema.server;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.filteredList.FilteredList;
import com.smartvalue.apigee.configuration.filteredList.ListFilter;
import com.smartvalue.apigee.resourceManager.ManagementServer;
import com.smartvalue.apigee.resourceManager.Renderer;
import com.smartvalue.apigee.rest.schema.environment.Environment;
import com.smartvalue.apigee.rest.schema.organization.Organization;

public class ServerServices extends com.smartvalue.apigee.rest.schema.Service{
	
	public ServerServices(ManagementServer ms) {
		super(ms);
	}

	@SuppressWarnings("unchecked")
	public <T> List<Server>  getServers(String m_pod , String m_region ) throws UnirestException, IOException
	{
		String apiPath = "/v1/servers?pod="+m_pod ; 
		// === Thanks To ChatGPT 
		Type listType = new TypeToken<List<Server>>() {}.getType();
		List<Server> servers = this.getMs().executeMgmntAPI(apiPath , listType ) ;
		
		List<Server> result = new ArrayList<> () ; 
		
		for (Server server : servers)
		{
			if ( server.getRegion().contains(m_region))
			{
				result.add(server) ; 
			}
		}
		return servers ; 
	}
	public FilteredList<MPServer> getMPServers( String m_region ) throws UnirestException, IOException
	{
		FilteredList<MPServer> result = getServersByTypeAndRegion(MPServer.class , m_region , "gateway" ) ; 
		return result ; 
	}
	
	public FilteredList<Router> getRouterServers( String m_region ) throws UnirestException, IOException
	{
		String podName = "gateway" ; 
		FilteredList<Router> result = getServersByTypeAndRegion(Router.class , m_region , podName ) ; 
		return result ; 
	}
	public FilteredList<Postgres> getPostgresServers( String m_region ) throws UnirestException, IOException
	{
		FilteredList<Postgres> result = getServersByTypeAndRegion(Postgres.class , m_region , "analytics") ; 
		return result ; 
	}
	
	public FilteredList<QupidServer> getQupidServers( String m_region ) throws UnirestException, IOException
	{
		FilteredList<QupidServer> result = getServersByTypeAndRegion(QupidServer.class , m_region , "central") ; 
		return result ; 
	}
	
	public FilteredList<ManagementServer> getManagementServers( String m_region ) throws UnirestException, IOException
	{
		FilteredList<ManagementServer> result = getServersByTypeAndRegion(ManagementServer.class , m_region , "central") ; 
		return result ; 
	}
	
	public <T extends Server> FilteredList<T> getServersByTypeAndRegion(Class<T> serverClass, String m_region , String m_pod ) throws UnirestException, IOException {
	    String apiPath = "/v1/servers?pod="+m_pod;
	    Type listType = TypeToken.getParameterized(FilteredList.class, serverClass).getType();
	    FilteredList<T> servers = this.getMs().executeMgmntAPI(apiPath, listType);

	    FilteredList<T> result = new FilteredList<>();

	    for (T server : servers) {
	    	String serverSimpleName = server.getSimpleName() ; 
	    	server.setManagmentServer(this.getMs());
	        if (server.getRegion().contains(m_region) && server.getType().contains(serverSimpleName.toLowerCase())) {
	            result.add(server);
	        }
	    }
	    return result;
	}
	
	public FilteredList<MPServer>  getOnlyUpMpServers(String m_region ) throws Exception
	{
		FilteredList<MPServer> mpServers = getMPServers (m_region) ; 
		ListFilter<MPServer> onlyUpServersFilter = new OnlyUpServersFilter () ; 		//ListFilter<MPServer>() 
	
		return mpServers.filter(onlyUpServersFilter) ; 
	}
	
	
   public HashMap<String , HashMap< String , List<MPServer>> >  getAllEnvsMessageProcessors(String m_org) throws Exception
    {
    	Organization orgObj = (Organization) this.getMs().getOrgByName(m_org) ;
    	List<String> regions = this.getMs().getRegions() ;
    	HashMap<String , HashMap< String , List<MPServer>> > result = new HashMap<> () ; 
    	for (String region : regions)
    	{
    		List<String> envs =  orgObj.getEnvironments() ; 
    		for (String env : envs )
    		{
	    			Environment envObj = orgObj.getEnvByName(env) ;
	    			List<MPServer> mps = envObj.getMessageProcesors(region) ;
	    			HashMap<String , List<MPServer>> envMps = new  HashMap<String , List<MPServer>>();
	    			envMps.put(env , mps) ; 
	    			result.put(region , envMps ) ; 
    		}
    	}
    	return result ; 
    }

	
}
