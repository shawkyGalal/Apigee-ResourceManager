package com.smartvalue.apigee.migration.load;

import com.github.jknack.handlebars.internal.text.StringTokenizer;
import com.smartvalue.apigee.migration.ProcessResult;


public class LoadResult extends ProcessResult {
	
	public String extractEnvNameFromsource(String basePath )
	{
		// C:\temp\Apigee\sfoda@moj.gov.sa\Stage\stg\proxies\moj-internal-clients\_Proxy_RE_Licenses\11
		
		String  xx  = this.getSource().substring(basePath.length()) ;
		// xx = \proxies\moj-internal-clients\_Proxy_RE_Licenses\11 
		StringTokenizer st = new StringTokenizer(xx , "\\"); 
		return st.getTokenList().get(1); 
	}

}
