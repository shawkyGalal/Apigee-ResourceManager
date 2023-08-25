package com.smartvalue.apigee.rest.schema.server;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.resourceManager.ManagementServer;
import com.smartvalue.apigee.resourceManager.Renderer;
import com.smartvalue.apigee.rest.schema.environment.Environment;
import com.smartvalue.apigee.rest.schema.organization.Organization;

public class ServerServices extends com.smartvalue.apigee.rest.schema.Service{

		
	
	
	public ServerServices(ManagementServer ms) {
		super(ms);
	}

	@SuppressWarnings("unchecked")
	public List<Server>  getServers(String m_pod , String m_region) throws UnirestException, IOException
	{
		String apiPath = "/v1/servers?pod="+m_pod ; 
		// === Thanks To ChatGPT 
		Type listType = new TypeToken<List<Server>>() {}.getType();
		List<Server> serversArray = this.getMs().executeMgmntAPI(apiPath , listType ) ;
		List<Server> result = new ArrayList<Server> () ; 
		for (Server server : serversArray)
		{
			if (server.getRegion().contains(m_region))
			{
				result.add(server) ; 
			}
		}
		return result ; 
	}

	/**
	 This method will search for all pod's servers with a given type 
	 Available pods ( analytics , gateway , analytics ) 
	 Available serverTypes ( router , message-processor , ... )  
	 * @param m_pod
	 * @param m_serverType
	 * @return
	 * @throws UnirestException
	 * @throws IOException
	 */
	private <T> List<T>  getAllServersOfType(String m_pod , String m_region  , Class<T> classOfT ) throws UnirestException, IOException
	{
		boolean isMessageProcesor = classOfT.equals(MPServer.class) ;
		boolean isRouter = classOfT.equals(Router.class) ;
		boolean isPostgres = classOfT.equals(Postgres.class) ;
		
		String serverType = (isMessageProcesor) ? "message-processor" : ((isRouter)? "router": (isPostgres)? "postgres-server":"Not Yet" );  
		List<T> result = new ArrayList<T>() ; 
		 
		for (Server server : this.getServers(m_pod , m_region) )
		{
			if (server.getType().contains(serverType))
			{
				result.add((T)server) ; 
			}
		}
		return result; 
	}
	
	public List<MPServer>  getAllMessageProcessorServers(String m_region) throws UnirestException, IOException
	{
		List<MPServer> result = (List<MPServer> ) getAllServersOfType("gateway" , m_region , MPServer.class ) ;
		
		return result ; 
	}
	
	public List<Router>  getAllRouterServers(String m_region) throws UnirestException, IOException
	{
		List<Router> result = (List<Router> ) getAllServersOfType("gateway" , m_region ,  Router.class ) ;
		
		return result ; 
	}
	
	public List<Postgres>  getAllPostgres(String m_region) throws UnirestException, IOException
	{
		List<Postgres> result = (List<Postgres> ) getAllServersOfType("analytics" , m_region ,  Postgres.class ) ;
		
		return result ; 
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
	    			Environment envObj = orgObj.getEnv(env) ;
	    			List<MPServer> mps = envObj.getMessageProcesors(region) ;
	    			HashMap<String , List<MPServer>> envMps = new  HashMap<String , List<MPServer>>();
	    			envMps.put(env , mps) ; 
	    			result.put(region , envMps ) ; 
	    		}
	    	}
	    	return result ; 
	    	
	    }

	
}
