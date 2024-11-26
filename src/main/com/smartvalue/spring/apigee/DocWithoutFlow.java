package com.smartvalue.spring.apigee;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.smartvalue.apigee.resourceManager.helpers.Helper;
import com.smartvalue.swagger.v3.parser.util.Jsonable;

public class DocWithoutFlow implements Serializable  {

		private String proxyname ,revision , path , verb ; 
		
		public DocWithoutFlow(String m_proxyname, String m_revision, String m_path, String m_verb) {
			this.path = m_path ; 
			this.proxyname = m_proxyname ; 
			this.revision =m_revision ;  
			this.verb = m_verb ; 
		}

		public String getProxyname() {
			return proxyname;
		}

		public String getRevision() {
			return revision;
		}

		public String getPath() {
			return path;
		}

		public String getVerb() {
			return verb;
		}
	
			
}
