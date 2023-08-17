package com.smartvalue.apigee.rest.schema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.resourceManager.ManagementServer;

public class ProxyManualCreated {

	private ApiMetaData metaData;
	private String name;
	private ArrayList<String> revision;
	public ApiMetaData getMetaData() {
		return metaData;
	}
	public void setMetaData(ApiMetaData metaData) {
		this.metaData = metaData;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<String> getRevision() {
		return revision;
	}
	public void setRevision(ArrayList<String> revision) {
		this.revision = revision;
	}
	
	
	private ManagementServer ms;

	public ProxyManualCreated(ManagementServer ms, String proxyName) {
		// TODO Auto-generated constructor stub
		this.name = proxyName ; 
		this.ms =ms ; 
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object>  getDeployments(String m_org ) throws UnirestException, IOException
	{
		Map<String, Object> result = null; 
		String apiPath = "/v1/o/"+m_org+"/apis/"+this.name+"/deployments" ; 
		result = this.ms.executeMgmntAPI(apiPath , Map.class , "GET") ;
		return result ; 
	}
	
	public class ApiMetaData{
		private long createdAt;
		private String createdBy;
		private long lastModifiedAt;
		private String lastModifiedBy;
		private String subType;
		public long getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(long createdAt) {
			this.createdAt = createdAt;
		}
		public String getCreatedBy() {
			return createdBy;
		}
		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}
		public long getLastModifiedAt() {
			return lastModifiedAt;
		}
		public void setLastModifiedAt(long lastModifiedAt) {
			this.lastModifiedAt = lastModifiedAt;
		}
		public String getLastModifiedBy() {
			return lastModifiedBy;
		}
		public void setLastModifiedBy(String lastModifiedBy) {
			this.lastModifiedBy = lastModifiedBy;
		}
		public String getSubType() {
			return subType;
		}
		public void setSubType(String subType) {
			this.subType = subType;
		}
	}

	public void setManagmentServer(ManagementServer ms2) {
		this.ms = ms2 ; 
		
	}
	
}
