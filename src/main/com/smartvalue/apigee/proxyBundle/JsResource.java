package com.smartvalue.apigee.proxyBundle;

import java.io.IOException;
import java.util.zip.ZipInputStream;

public class JsResource  {

	private String content ; 
	private String name ; 
	public JsResource(String m_name , ZipInputStream m_zipInputStream) throws IOException {
		this.content = BundleElement.readFromInputStream(m_zipInputStream) ;
		this.name = m_name ; 
	}
	public String getContent() {
		return content;
	}
	public String getName() {
		return name;
	}

	

}
