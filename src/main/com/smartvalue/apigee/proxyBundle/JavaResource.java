package com.smartvalue.apigee.proxyBundle;

import java.io.IOException;
import java.util.zip.ZipInputStream;

public class JavaResource {

	//private String content;
	private String name;

	public JavaResource(String m_name, ZipInputStream m_zipInputStream) throws IOException {
		//this.content = BundleElement.readFromInputStream(m_zipInputStream) ;
		this.name = m_name ; 
	}
	
	public String getName() {
		return name;
	}

}
