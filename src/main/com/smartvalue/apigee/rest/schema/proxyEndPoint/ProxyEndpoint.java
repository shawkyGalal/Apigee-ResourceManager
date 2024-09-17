package com.smartvalue.apigee.rest.schema.proxyEndPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Flow;
import com.smartvalue.apigee.rest.schema.proxyRevision.ProxyRevision;

public class ProxyEndpoint  extends com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.ProxyEndpoint {

	private ProxyRevision parentProxyRevision ; 
	private ManagementServer ms ; 
		
	public Flow getFlowByName(String m_flowName) throws UnirestException, IOException
		{
			Flow result = null ; 
			for ( com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Flow flow : this.getFlows())
			{
				if ( flow.getName().equalsIgnoreCase( m_flowName) )
				{ 	result = flow ; 
					result.setParentProxyEndPoint(this);		
					break ;
				} 
			}
			 
			return result ; 
		}
	    
	    public Flow getOpenApiFlow() throws UnirestException, IOException
		{
	    	 return getFlowByName("GetOAS") ; 
		}
	    

		public ProxyRevision getParentProxyRevision() {
			return parentProxyRevision;
		}

		public void setParentProxyRevision(ProxyRevision parentProxyRevision) {
			this.parentProxyRevision = parentProxyRevision;
		}

		public ManagementServer getManagmentServer() {
			return ms;
		}

		public void setManagmentServer(ManagementServer ms) {
			this.ms = ms;
		}
		
		public List<Flow> getFlows()
		{
			List<Flow> result = super.getFlows();
			for ( Flow f : result )
			{
				f.setParentProxyEndPoint(this);
			}
			return result ;  
		}

		public List<Flow> getFlows(ArrayList<String> execludeFlowNames) {
			// TODO Auto-generated method stub
			List<Flow> allFlows =  super.getFlows();
			List<Flow> result = allFlows ; 
			for (String flowName : execludeFlowNames )
			{
				try {
					Flow excludedFlow = this.getFlowByName(flowName) ;
					if (excludedFlow != null) result.remove(excludedFlow) ; 
				} catch (UnirestException | IOException e) {
					e.printStackTrace();
				} 
			}
			
			return result; 
			
		}
		
}
