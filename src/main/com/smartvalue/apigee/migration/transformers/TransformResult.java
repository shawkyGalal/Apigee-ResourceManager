package com.smartvalue.apigee.migration.transformers;

import com.github.jknack.handlebars.internal.text.StringTokenizer;
import com.smartvalue.apigee.migration.ProcessResult;

public class TransformResult extends ProcessResult {
	

	private Class transformerClass ;

	
	public void setTransformerClass(Class transformerClass) {
		this.transformerClass = transformerClass;
	}
	
	public TransformResult withTransformerClass(Class transformerClass) {
		this.transformerClass = transformerClass;
		return this ; 
	}
	
	public String toString()
	{
		return super.toString() ;
	}

	public Class getTransformerClass() {
		return transformerClass;
	}


	public String extractEnvNameFromsource(String basePath )
	{
		// C:\temp\Apigee\sfoda@moj.gov.sa\Stage\stg\proxies\moj-internal-clients\_Proxy_RE_Licenses\11
		
		String  xx  = this.getSource().substring(basePath.length()) ;
		// xx = \proxies\moj-internal-clients\_Proxy_RE_Licenses\11 
		StringTokenizer st = new StringTokenizer(xx , "\\"); 
		return st.getTokenList().get(1); 
	}

}
