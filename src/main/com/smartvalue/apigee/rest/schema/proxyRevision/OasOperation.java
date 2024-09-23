package com.smartvalue.apigee.rest.schema.proxyRevision;

import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Flow;

import io.swagger.v3.oas.models.Operation;

public class OasOperation {

	private String path; 
	private String verb ;
	private Operation operation ; 
	public OasOperation(String m_path, String m_verb , Operation m_operation) {
		this.path = m_path ; 
		this.verb = m_verb ; 
		this.operation = m_operation ; 
	}
	public String getPath() {
		return path;
	}
	public Operation getOperation() {
		return operation;
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
		if (matched) this.getOperation().setOperationId(flow.getUniqueIdentifier());
	}

}
