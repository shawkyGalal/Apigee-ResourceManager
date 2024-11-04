package com.smartvalue.apigee.migration.load;

import com.github.jknack.handlebars.internal.text.StringTokenizer;
import com.smartvalue.apigee.configuration.AppConfig;
import com.smartvalue.apigee.migration.ProcessResult;


public class LoadResult extends ProcessResult {
	
	public String extractEnvNameFromsource()
	{
		String  xx  = this.getSource().substring(AppConfig.getMigrationBasePath().length()) ;
		StringTokenizer st = new StringTokenizer(xx , "\\"); 
		return st.getTokenList().get(5); 
	}

}
