package com.smartvalue.apigee.migration.load;
import java.util.UUID;

import com.smartvalue.apigee.migration.ProcessResults;

public class LoadResults  extends ProcessResults {
	
	public LoadResults(String desc, UUID m_uuid) {
		super(desc, m_uuid);
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public LoadResults filterFailed(boolean status )
	{
		notMatchedResult = new LoadResults(this.getDescription() , this.getUuid()); 
		LoadResults results = new LoadResults(this.getDescription() , this.getUuid()) ; 
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
