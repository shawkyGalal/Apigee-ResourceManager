package com.smartvalue.apigee.rest.schema.server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.filteredList.FilteredList;
import com.smartvalue.apigee.configuration.filteredList.ListFilter;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.migration.export.ExportResults;
import com.smartvalue.apigee.migration.transformers.ApigeeObjectTransformer;
import com.smartvalue.apigee.migration.transformers.TransformResult;
import com.smartvalue.apigee.migration.transformers.TransformationResults;
import com.smartvalue.apigee.rest.schema.environment.Environment;
import com.smartvalue.apigee.rest.schema.organization.Organization;
import com.smartvalue.apigee.rest.schema.proxy.DeleteResults;

public class ServerServices extends com.smartvalue.apigee.rest.schema.ApigeeService{
	
	public ServerServices(ManagementServer ms) {
		super(ms);
	}

	@SuppressWarnings("unchecked")
	public <T> List<Server>  getServers(String m_pod , String m_region ) throws Exception
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
	public FilteredList<MPServer> getMPServers( String m_region ) throws Exception
	{
		FilteredList<MPServer> result = getServersByTypeAndRegion(MPServer.class , m_region , "gateway" ) ; 
		return result ; 
	}
	
	public MPServer getMPServerByUUID( String m_region , String UUID ) throws Exception
	{
		FilteredList<MPServer> allservers = getServersByTypeAndRegion(MPServer.class , m_region , "gateway" ) ;
		MPServer result = null ; 
		for (MPServer server : allservers)
		{
			if (server.getuUID().equalsIgnoreCase(UUID))
			{
				result = server ; 
				break ; 
			}
		}
		return result ; 
	}
	
	
	public FilteredList<Router> getRouterServers( String m_region ) throws Exception
	{
		String podName = this.getMs().getInfra().getRegion(m_region).getPodGateWayName() ; // (m_region.equalsIgnoreCase("dc-1"))? "gateway" : "gateway-2" ; 
		
		FilteredList<Router> result = getServersByTypeAndRegion(Router.class , m_region , podName ) ; 
		return result ; 
	}
	public FilteredList<Postgres> getPostgresServers( String m_region ) throws Exception
	{
		FilteredList<Postgres> result = getServersByTypeAndRegion(Postgres.class , m_region , "analytics") ; 
		return result ; 
	}
	
	public FilteredList<QupidServer> getQupidServers( String m_region ) throws Exception
	{
		FilteredList<QupidServer> result = getServersByTypeAndRegion(QupidServer.class , m_region , "central") ; 
		return result ; 
	}
	
	public FilteredList<ManagementServer> getManagementServers( String m_region ) throws Exception
	{
		FilteredList<ManagementServer> result = getServersByTypeAndRegion(ManagementServer.class , m_region , "central") ; 
		return result ; 
	}
	
	public <T extends Server> FilteredList<T> getServersByTypeAndRegion(Class<T> serverClass, String m_region , String m_pod ) throws Exception {
	    String apiPath = "/v1/servers?pod="+m_pod;
	    Type listType = TypeToken.getParameterized(FilteredList.class, serverClass).getType();
	    ManagementServer mServer = this.getMs() ;
	    if (! mServer.getRegion().equalsIgnoreCase(m_region))
	    {
	    	mServer = mServer.getInfra().getManagementServer(m_region); 
	    }
	    FilteredList<T> servers = mServer.executeMgmntAPI(apiPath, listType);

	    FilteredList<T> result = new FilteredList<>();

	    for (T server : servers) {
	    	String serverSimpleName = server.getSimpleName() ; 
	    	server.setManagmentServer(mServer);
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
	
	
   public HashMap<Object , HashMap< String , List<MPServer>> >  getAllEnvsMessageProcessors(String m_org) throws Exception
    {
    	Organization orgObj = (Organization) this.getMs().getOrgByName(m_org) ;
    	List<String> regions = this.getMs().getRegions() ;
    	HashMap<Object , HashMap< String , List<MPServer>> > result = new HashMap<> () ; 
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


@Override
public ExportResults exportAll(String folderDest) throws UnirestException, IOException {
	// Not Applicable 
	return null;
}

@Override
public DeleteResults deleteAll() throws UnirestException, IOException {
	// Not Applicable
	return null;
}

@Override
public String getResourcePath() {
	return null;
}

@Override
public String getApigeeObjectType() {
	return "Server";
}

@Override
public TransformationResults transformAll(String inputFolderPath, String outputFolderPath) {
	return null;
}

@Override
public ArrayList<ApigeeObjectTransformer> buildTransformers()
		throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException,
		IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
	// TODO Auto-generated method stub
	return null;
}

	
}
