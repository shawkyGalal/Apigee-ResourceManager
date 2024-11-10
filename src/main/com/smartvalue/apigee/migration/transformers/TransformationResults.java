package com.smartvalue.apigee.migration.transformers;


import java.util.UUID;

import com.smartvalue.apigee.migration.ProcessResults;
import com.smartvalue.swagger.v3.parser.util.FifoMap;

public class TransformationResults extends ProcessResults {

	
	public TransformationResults(String desc, UUID m_uuid) {
		super(desc, m_uuid);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TransformationResults notMatchedResult ; 

	public TransformationResults getNotMatchedResult() {
		return notMatchedResult;
	}

	public TransformationResults filterFailed(boolean status )
	{
		notMatchedResult = new TransformationResults(this.getDescription() , this.getUuid()); 
		TransformationResults results = new TransformationResults(this.getDescription() , this.getUuid()) ; 
		for( int i= 0 ; i< this.size() ; i++ )
		{
			if (this.get(i).isFailed() == status) results.add(this.get(i));
			else {
				notMatchedResult.add(this.get(i));
			}
		}
		
		return results ; 
	}
	
	public FifoMap<String, ProcessResults> filterErrorDesc(String[] ErrorContains )
	{
		notMatchedResult = new TransformationResults(this.getDescription() , this.getUuid()); 
		FifoMap<String, ProcessResults> results = new FifoMap <String , ProcessResults>() ; 
		for (String contains : ErrorContains)
		{
			results.put(contains, new TransformationResults(this.getDescription() , this.getUuid()) ) ; 
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
}
