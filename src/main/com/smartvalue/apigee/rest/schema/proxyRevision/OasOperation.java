package com.smartvalue.apigee.rest.schema.proxyRevision;

import java.io.Serializable;
import java.util.StringTokenizer;
import java.util.UUID;

import com.smartvalue.apigee.rest.schema.proxyEndPoint.auto.Flow;

import io.swagger.v3.oas.models.Operation;

public class OasOperation implements Serializable{

	private static final String UUID_SEPERATOR = "@@@";
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
	
	public void updateOperationIdFromFlowIfNeeded(Flow flow )
	{
		String operationId ; 
		boolean matched = flow.match(this);
		
		if (matched)
		{
			String currentOperationId = this.getOperation().getOperationId() ; 
			boolean containsUUID = currentOperationId!= null && currentOperationId.contains(UUID_SEPERATOR) ;
			String flowUniqueId = flow.getUniqueIdentifier() ; 
			if (containsUUID)
			{
				StringTokenizer st = new StringTokenizer(currentOperationId ) ; 
				String currentOperationID = st.nextToken(UUID_SEPERATOR);
				String currentUUIDStr = st.nextToken() ;
				if (! currentOperationID.equalsIgnoreCase(flowUniqueId))
				{
					operationId = flowUniqueId + UUID_SEPERATOR + currentUUIDStr ;
					this.getOperation().setOperationId(operationId);
				}
				else
				{
				UUID currentUUID = UUID.fromString(currentUUIDStr) ; 
				currentUUID.getLeastSignificantBits() ;
				System.out.println("Operation ID is Set Before :" + currentOperationId + "No action is taken ");
				}
				
			}
			else
			{
				UUID uuid = UUID.randomUUID(); 
				operationId = flowUniqueId + UUID_SEPERATOR + uuid.toString() ;
				this.getOperation().setOperationId(operationId);
			}
			
		}
	}

}
