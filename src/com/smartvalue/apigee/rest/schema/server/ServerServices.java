package com.smartvalue.apigee.rest.schema.server;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.resourceManager.ManagementServer;

public class ServerServices {

	private ManagementServer managementServer;
	
	@SuppressWarnings("unchecked")
	public List<Server>  getServers(String m_pod) throws UnirestException, IOException
	{
		String apiPath = "/v1/servers?pod="+m_pod ; 
		// === Thanks To ChatGPT 
		Type listType = new TypeToken<List<Server>>() {}.getType();
		List<Server> serversArray = this.managementServer.executeMgmntAPI(apiPath , listType ) ; 
		return serversArray ; 
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
	private <T> List<T>  getAllServersOfType(String m_pod , Class<T> classOfT ) throws UnirestException, IOException
	{
		boolean isMessageProcesor = classOfT.equals(MPServer.class) ;
		boolean isRouter = classOfT.equals(Router.class) ;
		boolean isPostgres = classOfT.equals(Postgres.class) ;
		
		String serverType = (isMessageProcesor) ? "message-processor" : ((isRouter)? "router": (isPostgres)? "postgres-server":"Not Yet" );  
		List<T> result = new ArrayList<T>() ; 
		 
		for (Server server : this.getServers(m_pod) )
		{
			if (server.getType().contains(serverType))
			{
				result.add((T)server) ; 
			}
		}
		return result; 
	}
	
	public List<MPServer>  getAllMessageProcessorServers() throws UnirestException, IOException
	{
		List<MPServer> result = (List<MPServer> ) getAllServersOfType("gateway" , MPServer.class ) ;
		
		return result ; 
	}
	
	public List<Router>  getAllRouterServers() throws UnirestException, IOException
	{
		List<Router> result = (List<Router> ) getAllServersOfType("gateway" , Router.class ) ;
		
		return result ; 
	}
	
	public List<Postgres>  getAllPostgres() throws UnirestException, IOException
	{
		List<Postgres> result = (List<Postgres> ) getAllServersOfType("analytics" , Postgres.class ) ;
		
		return result ; 
	}

	public void setMs(ManagementServer m_managementServer) {
		this.managementServer = m_managementServer; 
		
	}
	
}
