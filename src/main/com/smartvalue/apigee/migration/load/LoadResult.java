package com.smartvalue.apigee.migration.load;

import java.io.File;

import com.github.jknack.handlebars.internal.text.StringTokenizer;
import com.smartvalue.apigee.configuration.AppConfig;
import com.smartvalue.apigee.migration.ProcessResult;


public class LoadResult extends ProcessResult {
	
	public String extractEnvNameFromsource(AppConfig appConfig)
	{
		String  xx  = this.getSource().substring(appConfig.getMigrationBasePath().length()) ;
		StringTokenizer st = new StringTokenizer(xx , File.separator); 
		return st.getTokenList().get(5); 
	}

}
