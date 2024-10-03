package com.smartvalue.apigee.migration.transformers;

import java.util.ArrayList;

public class TransformationResults extends ArrayList<TransformResult>{

	public TransformationResults filterFailed(boolean status )
	{
		TransformationResults results = new TransformationResults() ; 
		for( int i= 0 ; i< this.size() ; i++ )
		{
			if (this.get(i).isFailed() == status) results.add(this.get(i));
		}
		
		return results ; 
	}
}
