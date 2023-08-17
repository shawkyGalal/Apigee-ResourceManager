package com.smartvalue.apigee.rest.schema;

import java.util.ArrayList;

public class PojoObjects {
	// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
	// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
	/* ObjectMapper om = new ObjectMapper();
	Root root = om.readValue(myJsonString, Root.class); */
	
	
	//-------------------
	public class API{
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

	//---------------------
	
	public class ApiRevision{
		private ArrayList<String> basepaths;
		private ConfigurationVersion configurationVersion;
		private String contextInfo;
		private long createdAt;
		private String createdBy;
		private String description;
		private String displayName;
		private EntityMetaDataAsProperties entityMetaDataAsProperties;
		private long lastModifiedAt;
		private String lastModifiedBy;
		private String manifestVersion;
		private String name;
		private ArrayList<String> policies;
		private ArrayList<String> proxies;
		private ArrayList<String> proxyEndpoints;
		private ResourceFiles resourceFiles;
		private ArrayList<String> resources;
		private String revision;
		private ArrayList<Object> sharedFlows;
		private String spec;
		private ArrayList<Object> targetEndpoints;
		private ArrayList<Object> targetServers;
		private ArrayList<Object> targets;
		private String type;
		
		public ArrayList<String> getBasepaths() {
			return basepaths;
		}
		public void setBasepaths(ArrayList<String> basepaths) {
			this.basepaths = basepaths;
		}
		public ConfigurationVersion getConfigurationVersion() {
			return configurationVersion;
		}
		public void setConfigurationVersion(ConfigurationVersion configurationVersion) {
			this.configurationVersion = configurationVersion;
		}
		public String getContextInfo() {
			return contextInfo;
		}
		public void setContextInfo(String contextInfo) {
			this.contextInfo = contextInfo;
		}
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
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getDisplayName() {
			return displayName;
		}
		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}
		public EntityMetaDataAsProperties getEntityMetaDataAsProperties() {
			return entityMetaDataAsProperties;
		}
		public void setEntityMetaDataAsProperties(EntityMetaDataAsProperties entityMetaDataAsProperties) {
			this.entityMetaDataAsProperties = entityMetaDataAsProperties;
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
		public String getManifestVersion() {
			return manifestVersion;
		}
		public void setManifestVersion(String manifestVersion) {
			this.manifestVersion = manifestVersion;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public ArrayList<String> getPolicies() {
			return policies;
		}
		public void setPolicies(ArrayList<String> policies) {
			this.policies = policies;
		}
		public ArrayList<String> getProxies() {
			return proxies;
		}
		public void setProxies(ArrayList<String> proxies) {
			this.proxies = proxies;
		}
		public ArrayList<String> getProxyEndpoints() {
			return proxyEndpoints;
		}
		public void setProxyEndpoints(ArrayList<String> proxyEndpoints) {
			this.proxyEndpoints = proxyEndpoints;
		}
		public ResourceFiles getResourceFiles() {
			return resourceFiles;
		}
		public void setResourceFiles(ResourceFiles resourceFiles) {
			this.resourceFiles = resourceFiles;
		}
		public ArrayList<String> getResources() {
			return resources;
		}
		public void setResources(ArrayList<String> resources) {
			this.resources = resources;
		}
		public String getRevision() {
			return revision;
		}
		public void setRevision(String revision) {
			this.revision = revision;
		}
		public ArrayList<Object> getSharedFlows() {
			return sharedFlows;
		}
		public void setSharedFlows(ArrayList<Object> sharedFlows) {
			this.sharedFlows = sharedFlows;
		}
		public String getSpec() {
			return spec;
		}
		public void setSpec(String spec) {
			this.spec = spec;
		}
		public ArrayList<Object> getTargetEndpoints() {
			return targetEndpoints;
		}
		public void setTargetEndpoints(ArrayList<Object> targetEndpoints) {
			this.targetEndpoints = targetEndpoints;
		}
		public ArrayList<Object> getTargetServers() {
			return targetServers;
		}
		public void setTargetServers(ArrayList<Object> targetServers) {
			this.targetServers = targetServers;
		}
		public ArrayList<Object> getTargets() {
			return targets;
		}
		public void setTargets(ArrayList<Object> targets) {
			this.targets = targets;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	}

	public class ConfigurationVersion{
	    private int majorVersion;
	    private int minorVersion;
		public int getMajorVersion() {
			return majorVersion;
		}
		public void setMajorVersion(int majorVersion) {
			this.majorVersion = majorVersion;
		}
		public int getMinorVersion() {
			return minorVersion;
		}
		public void setMinorVersion(int minorVersion) {
			this.minorVersion = minorVersion;
		}
	}

	public class EntityMetaDataAsProperties{
	    private String bundle_type;
	    private String lastModifiedBy;
	    private String createdBy;
	    private String lastModifiedAt;
	    private String subType;
	    private String createdAt;
		public String getBundle_type() {
			return bundle_type;
		}
		public void setBundle_type(String bundle_type) {
			this.bundle_type = bundle_type;
		}
		public String getLastModifiedBy() {
			return lastModifiedBy;
		}
		public void setLastModifiedBy(String lastModifiedBy) {
			this.lastModifiedBy = lastModifiedBy;
		}
		public String getCreatedBy() {
			return createdBy;
		}
		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}
		public String getLastModifiedAt() {
			return lastModifiedAt;
		}
		public void setLastModifiedAt(String lastModifiedAt) {
			this.lastModifiedAt = lastModifiedAt;
		}
		public String getSubType() {
			return subType;
		}
		public void setSubType(String subType) {
			this.subType = subType;
		}
		public String getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(String createdAt) {
			this.createdAt = createdAt;
		}
	}

	public class ResourceFile{
		private String name;
		private String type;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	}

	public class ResourceFiles{
	    public ArrayList<ResourceFile> resourceFile;
	}
//-------------------
	
	public class Users {
		private User[] user ;

		public User[] getUser() {
			return user;
		}

		public void setUser(User[] user) {
			this.user = user;
		}
		
		public class User {
			private String emailId ;
			private String firstName ;
			private String lastName ;
			public String getEmailId() {
				return emailId;
			}
			public void setEmailId(String emailId) {
				this.emailId = emailId;
			}
			public String getFirstName() {
				return firstName;
			}
			public void setFirstName(String firstName) {
				this.firstName = firstName;
			}
			public String getLastName() {
				return lastName;
			}
			public void setLastName(String lastName) {
				this.lastName = lastName;
			}
		}

	}

}
