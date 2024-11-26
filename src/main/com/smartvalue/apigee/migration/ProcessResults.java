package com.smartvalue.apigee.migration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartvalue.apigee.migration.deploy.DeployResult;
import com.smartvalue.apigee.migration.export.ExportResult;
import com.smartvalue.apigee.migration.load.LoadResult;
import com.smartvalue.apigee.migration.transformers.TransformResult;
import com.smartvalue.apigee.resourceManager.helpers.Helper;
import com.smartvalue.apigee.rest.schema.proxy.DeleteResult;
import com.smartvalue.swagger.v3.parser.util.FifoMap;
import com.smartvalue.swagger.v3.parser.util.Jsonable;

public class ProcessResults extends ArrayList<ProcessResult> implements Serializable , Jsonable {
	
	/**
	 * 
	 */
	private String description  ; 
	private UUID uuid ; 
	private ArrayList<ProcessResults> subProcessResults ; 
	
	public ProcessResults (String desc , UUID m_uuid)
	{
		this.uuid = m_uuid ; 
		this.description = desc ; 
		subProcessResults = new ArrayList<ProcessResults> (); 
	}
	
	public ProcessResults (String desc)
	{
		this.uuid = UUID.randomUUID() ; 
		this.description = desc ; 
		subProcessResults = new ArrayList<ProcessResults> (); 
	}
	
	private static final long serialVersionUID = 1L;
	
	protected ProcessResults notMatchedResult ; 

	public ProcessResults getNotMatchedResult() {
		return notMatchedResult;
	}

	public ProcessResults filterFailed(boolean status )
	{
		notMatchedResult = new ProcessResults(this.description , this.uuid); 
		ProcessResults results = new ProcessResults(this.description , this.uuid) ; 
		for( int i= 0 ; i< this.size() ; i++ )
		{
			if (this.get(i).isFailed() == status) results.add(this.get(i));
			else {
				notMatchedResult.add(this.get(i));
			}
		}
		
		return results ; 
	}
	
	public FifoMap <String , ProcessResults> filterErrorDesc(String[] ErrorContains )
	{
		notMatchedResult = new ProcessResults(this.description , this.uuid); 
		FifoMap <String , ProcessResults> results = new FifoMap <String , ProcessResults>() ; 
		for (String contains : ErrorContains)
		{
			results.put(contains, new ProcessResults(this.description , this.uuid) ) ; 
		}
		
		for( int i= 0 ; i< this.size() ; i++ )
		{
			boolean matched= false; 
			for (String contains : ErrorContains)
			{
				if (this.get(i).getError().contains(contains) ) 
				{
					results.get(contains).add(this.get(i)); matched = true ; 
				}
			}
			if (!matched)
			{
				notMatchedResult.add(this.get(i)); 
			}
		}
		
		return results ; 
	}
	
	/**
	 * 
	 * @return a classified LoadResults according to their ExceptionClassName 
	 */
	public HashMap<String, ProcessResults > getExceptionClasses() {
        
		HashMap<String, ProcessResults> familiesMap = new HashMap<>();
        for (ProcessResult lr : this) {
            String exceptionClassName = lr.getExceptionClassName(); 
            if (!familiesMap.containsKey(exceptionClassName)) {
                familiesMap.put(exceptionClassName, new ProcessResults(this.description , this.uuid));
            }
            familiesMap.get(exceptionClassName).add(lr);
        }
        return familiesMap;
    }
	
public HashMap<Class<?>, ProcessResults > classify() {
        
		HashMap<Class<?>, ProcessResults> familiesMap = new HashMap<>();
		
		List<Class<?>> allTypes = Arrays.asList( ExportResult.class 
												, TransformResult.class 
												, LoadResult.class 
												, DeleteResult.class 
												, DeployResult.class
												, ProcessResult.class);
		for (Class<?> clazz : allTypes)
		{
			if (!familiesMap.containsKey(clazz))
			{
				familiesMap.put(clazz, new ProcessResults(this.description , this.uuid));	
			}

			for (ProcessResult pr : this) 
	        {
	        	if (pr.getClass() == clazz  )
	            {
	        		familiesMap.get(clazz).add(pr);    
	            }
	        }
		}
        return familiesMap;
    }

public String toJsonString() throws JsonProcessingException
{
	return Helper.mapObjectToJsonStr(this); 
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public UUID getUuid() {
	return uuid;
}

public void setUuid(UUID uuid) {
	this.uuid = uuid;
}

public void addAll(ProcessResults xx) 
{
	this.subProcessResults.add(xx) ; 
	super.addAll(xx) ; 
}

public ArrayList<ProcessResults> getSubProcessResults() {
	return subProcessResults;
} 
	
}
