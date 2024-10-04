package com.smartvalue.apigee.migration.transformers;

import java.util.ArrayList;
import java.util.HashMap;

import com.smartvalue.swagger.v3.parser.util.FifoMap;

public class TransformationResults extends ArrayList<TransformResult>{

	
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
		notMatchedResult = new TransformationResults(); 
		TransformationResults results = new TransformationResults() ; 
		for( int i= 0 ; i< this.size() ; i++ )
		{
			if (this.get(i).isFailed() == status) results.add(this.get(i));
			else {
				notMatchedResult.add(this.get(i));
			}
		}
		
		return results ; 
	}
	
	public FifoMap <String , TransformationResults> filterErrorDesc(String[] ErrorContains )
	{
		notMatchedResult = new TransformationResults(); 
		FifoMap <String , TransformationResults> results = new FifoMap <String , TransformationResults>() ; 
		for (String contains : ErrorContains)
		{
			results.put(contains, new TransformationResults() ) ; 
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
