package com.smartvalue.apigee.migration.load;
import com.smartvalue.apigee.migration.ProcessResults;

public class LoadResults  extends ProcessResults {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public LoadResults filterFailed(boolean status )
	{
		notMatchedResult = new LoadResults(); 
		LoadResults results = new LoadResults() ; 
		for( int i= 0 ; i< this.size() ; i++ )
		{
			if (this.get(i).isFailed() == status) results.add(this.get(i));
			else {
				notMatchedResult.add(this.get(i));
			}
		}
		
		return results ; 
	}
	
	
	
	}
