package com.smartvalue.apigee.migration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.smartvalue.apigee.migration.deploy.DeployResult;
import com.smartvalue.apigee.migration.export.ExportResult;
import com.smartvalue.apigee.migration.load.LoadResult;
import com.smartvalue.apigee.migration.transformers.TransformResult;
import com.smartvalue.apigee.rest.schema.proxy.DeleteResult;
import com.smartvalue.swagger.v3.parser.util.FifoMap;

public class ProcessResults extends ArrayList<ProcessResult>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected ProcessResults notMatchedResult ; 

	public ProcessResults getNotMatchedResult() {
		return notMatchedResult;
	}

	public ProcessResults filterFailed(boolean status )
	{
		notMatchedResult = new ProcessResults(); 
		ProcessResults results = new ProcessResults() ; 
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
		notMatchedResult = new ProcessResults(); 
		FifoMap <String , ProcessResults> results = new FifoMap <String , ProcessResults>() ; 
		for (String contains : ErrorContains)
		{
			results.put(contains, new ProcessResults() ) ; 
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
                familiesMap.put(exceptionClassName, new ProcessResults());
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
				familiesMap.put(clazz, new ProcessResults());	
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
}
