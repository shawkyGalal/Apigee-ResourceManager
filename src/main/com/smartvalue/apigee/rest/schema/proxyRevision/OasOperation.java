package com.smartvalue.apigee.rest.schema.proxyRevision;

import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Flow;

import io.swagger.v3.oas.models.Operation;

public class OasOperation extends Operation{

	private String path; 
	private String verb ;
	//private Operation operation ; 
	public OasOperation(String m_path, String m_verb , Operation m_operation) {
		this.path = m_path ; 
		this.verb = m_verb ; 
		this.setCallbacks(m_operation.getCallbacks());
		this.setDeprecated(getDeprecated());
		this.setDescription(m_operation.getDescription());
		this.setExtensions(m_operation.getExtensions());
		this.setExternalDocs(m_operation.getExternalDocs());
		this.setOperationId(m_operation.getOperationId());
		this.setParameters(m_operation.getParameters());
		this.setRequestBody(m_operation.getRequestBody());
		this.setResponses(m_operation.getResponses());
		this.setSecurity(m_operation.getSecurity());
		this.setServers(m_operation.getServers());
		this.setSummary(m_operation.getSummary());
		this.setTags(m_operation.getTags());
 
	}
	public String getPath() {
		return path;
	}
	public String getVerb() {
		return verb;
	}
	
	public boolean match(Flow flow)
	{
		return flow.match(this); 
	}
	
	public void setOperationId(Flow flow )
	{
		boolean matched = flow.match(this);
		if (matched) this.setOperationId(flow.getUniqueIdentifier());
	}

}
