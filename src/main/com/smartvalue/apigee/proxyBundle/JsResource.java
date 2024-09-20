package com.smartvalue.apigee.proxyBundle;

import java.io.IOException;
import java.util.zip.ZipInputStream;

public class JsResource extends BundleElement {

	public JsResource(String m_name, ZipInputStream m_zipInputStream) throws IOException {
		super(m_name, m_zipInputStream);
	}

	

}
