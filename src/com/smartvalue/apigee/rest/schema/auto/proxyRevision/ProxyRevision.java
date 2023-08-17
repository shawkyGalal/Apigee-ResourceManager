
package com.smartvalue.apigee.rest.schema.auto.proxyRevision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.smartvalue.apigee.rest.schema.auto.ApigeeComman;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "basepaths",
    "configurationVersion",
    "contextInfo",
    "createdAt",
    "createdBy",
    "description",
    "displayName",
    "entityMetaDataAsProperties",
    "lastModifiedAt",
    "lastModifiedBy",
    "manifestVersion",
    "name",
    "policies",
    "proxies",
    "proxyEndpoints",
    "resourceFiles",
    "resources",
    "revision",
    "sharedFlows",
    "spec",
    "targetEndpoints",
    "targetServers",
    "targets",
    "type"
})
@Generated("jsonschema2pojo")
public class ProxyRevision extends ApigeeComman {

    @JsonProperty("basepaths")
    private List<String> basepaths = new ArrayList<String>();
    @JsonProperty("configurationVersion")
    private ConfigurationVersion configurationVersion;
    @JsonProperty("contextInfo")
    private String contextInfo;
    @JsonProperty("createdAt")
    private Long createdAt;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("description")
    private String description;
    @JsonProperty("displayName")
    private String displayName;
    @JsonProperty("entityMetaDataAsProperties")
    private EntityMetaDataAsProperties entityMetaDataAsProperties;
    @JsonProperty("lastModifiedAt")
    private Long lastModifiedAt;
    @JsonProperty("lastModifiedBy")
    private String lastModifiedBy;
    @JsonProperty("manifestVersion")
    private String manifestVersion;
    @JsonProperty("name")
    private String name;
    @JsonProperty("policies")
    private List<String> policies = new ArrayList<String>();
    @JsonProperty("proxies")
    private List<String> proxies = new ArrayList<String>();
    @JsonProperty("proxyEndpoints")
    private List<String> proxyEndpoints = new ArrayList<String>();
    @JsonProperty("resourceFiles")
    private ResourceFiles resourceFiles;
    @JsonProperty("resources")
    private List<String> resources = new ArrayList<String>();
    @JsonProperty("revision")
    private String revision;
    @JsonProperty("sharedFlows")
    private List<Object> sharedFlows = new ArrayList<Object>();
    @JsonProperty("spec")
    private String spec;
    @JsonProperty("targetEndpoints")
    private List<Object> targetEndpoints = new ArrayList<Object>();
    @JsonProperty("targetServers")
    private List<Object> targetServers = new ArrayList<Object>();
    @JsonProperty("targets")
    private List<Object> targets = new ArrayList<Object>();
    @JsonProperty("type")
    private String type;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("basepaths")
    public List<String> getBasepaths() {
        return basepaths;
    }

    @JsonProperty("basepaths")
    public void setBasepaths(List<String> basepaths) {
        this.basepaths = basepaths;
    }

    public ProxyRevision withBasepaths(List<String> basepaths) {
        this.basepaths = basepaths;
        return this;
    }

    @JsonProperty("configurationVersion")
    public ConfigurationVersion getConfigurationVersion() {
        return configurationVersion;
    }

    @JsonProperty("configurationVersion")
    public void setConfigurationVersion(ConfigurationVersion configurationVersion) {
        this.configurationVersion = configurationVersion;
    }

    public ProxyRevision withConfigurationVersion(ConfigurationVersion configurationVersion) {
        this.configurationVersion = configurationVersion;
        return this;
    }

    @JsonProperty("contextInfo")
    public String getContextInfo() {
        return contextInfo;
    }

    @JsonProperty("contextInfo")
    public void setContextInfo(String contextInfo) {
        this.contextInfo = contextInfo;
    }

    public ProxyRevision withContextInfo(String contextInfo) {
        this.contextInfo = contextInfo;
        return this;
    }

    @JsonProperty("createdAt")
    public Long getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("createdAt")
    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public ProxyRevision withCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @JsonProperty("createdBy")
    public String getCreatedBy() {
        return createdBy;
    }

    @JsonProperty("createdBy")
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ProxyRevision withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public ProxyRevision withDescription(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("displayName")
    public String getDisplayName() {
        return displayName;
    }

    @JsonProperty("displayName")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public ProxyRevision withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    @JsonProperty("entityMetaDataAsProperties")
    public EntityMetaDataAsProperties getEntityMetaDataAsProperties() {
        return entityMetaDataAsProperties;
    }

    @JsonProperty("entityMetaDataAsProperties")
    public void setEntityMetaDataAsProperties(EntityMetaDataAsProperties entityMetaDataAsProperties) {
        this.entityMetaDataAsProperties = entityMetaDataAsProperties;
    }

    public ProxyRevision withEntityMetaDataAsProperties(EntityMetaDataAsProperties entityMetaDataAsProperties) {
        this.entityMetaDataAsProperties = entityMetaDataAsProperties;
        return this;
    }

    @JsonProperty("lastModifiedAt")
    public Long getLastModifiedAt() {
        return lastModifiedAt;
    }

    @JsonProperty("lastModifiedAt")
    public void setLastModifiedAt(Long lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public ProxyRevision withLastModifiedAt(Long lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
        return this;
    }

    @JsonProperty("lastModifiedBy")
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    @JsonProperty("lastModifiedBy")
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ProxyRevision withLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    @JsonProperty("manifestVersion")
    public String getManifestVersion() {
        return manifestVersion;
    }

    @JsonProperty("manifestVersion")
    public void setManifestVersion(String manifestVersion) {
        this.manifestVersion = manifestVersion;
    }

    public ProxyRevision withManifestVersion(String manifestVersion) {
        this.manifestVersion = manifestVersion;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public ProxyRevision withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("policies")
    public List<String> getPolicies() {
        return policies;
    }

    @JsonProperty("policies")
    public void setPolicies(List<String> policies) {
        this.policies = policies;
    }

    public ProxyRevision withPolicies(List<String> policies) {
        this.policies = policies;
        return this;
    }

    @JsonProperty("proxies")
    public List<String> getProxies() {
        return proxies;
    }

    @JsonProperty("proxies")
    public void setProxies(List<String> proxies) {
        this.proxies = proxies;
    }

    public ProxyRevision withProxies(List<String> proxies) {
        this.proxies = proxies;
        return this;
    }

    @JsonProperty("proxyEndpoints")
    public List<String> getProxyEndpoints() {
        return proxyEndpoints;
    }

    @JsonProperty("proxyEndpoints")
    public void setProxyEndpoints(List<String> proxyEndpoints) {
        this.proxyEndpoints = proxyEndpoints;
    }

    public ProxyRevision withProxyEndpoints(List<String> proxyEndpoints) {
        this.proxyEndpoints = proxyEndpoints;
        return this;
    }

    @JsonProperty("resourceFiles")
    public ResourceFiles getResourceFiles() {
        return resourceFiles;
    }

    @JsonProperty("resourceFiles")
    public void setResourceFiles(ResourceFiles resourceFiles) {
        this.resourceFiles = resourceFiles;
    }

    public ProxyRevision withResourceFiles(ResourceFiles resourceFiles) {
        this.resourceFiles = resourceFiles;
        return this;
    }

    @JsonProperty("resources")
    public List<String> getResources() {
        return resources;
    }

    @JsonProperty("resources")
    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    public ProxyRevision withResources(List<String> resources) {
        this.resources = resources;
        return this;
    }

    @JsonProperty("revision")
    public String getRevision() {
        return revision;
    }

    @JsonProperty("revision")
    public void setRevision(String revision) {
        this.revision = revision;
    }

    public ProxyRevision withRevision(String revision) {
        this.revision = revision;
        return this;
    }

    @JsonProperty("sharedFlows")
    public List<Object> getSharedFlows() {
        return sharedFlows;
    }

    @JsonProperty("sharedFlows")
    public void setSharedFlows(List<Object> sharedFlows) {
        this.sharedFlows = sharedFlows;
    }

    public ProxyRevision withSharedFlows(List<Object> sharedFlows) {
        this.sharedFlows = sharedFlows;
        return this;
    }

    @JsonProperty("spec")
    public String getSpec() {
        return spec;
    }

    @JsonProperty("spec")
    public void setSpec(String spec) {
        this.spec = spec;
    }

    public ProxyRevision withSpec(String spec) {
        this.spec = spec;
        return this;
    }

    @JsonProperty("targetEndpoints")
    public List<Object> getTargetEndpoints() {
        return targetEndpoints;
    }

    @JsonProperty("targetEndpoints")
    public void setTargetEndpoints(List<Object> targetEndpoints) {
        this.targetEndpoints = targetEndpoints;
    }

    public ProxyRevision withTargetEndpoints(List<Object> targetEndpoints) {
        this.targetEndpoints = targetEndpoints;
        return this;
    }

    @JsonProperty("targetServers")
    public List<Object> getTargetServers() {
        return targetServers;
    }

    @JsonProperty("targetServers")
    public void setTargetServers(List<Object> targetServers) {
        this.targetServers = targetServers;
    }

    public ProxyRevision withTargetServers(List<Object> targetServers) {
        this.targetServers = targetServers;
        return this;
    }

    @JsonProperty("targets")
    public List<Object> getTargets() {
        return targets;
    }

    @JsonProperty("targets")
    public void setTargets(List<Object> targets) {
        this.targets = targets;
    }

    public ProxyRevision withTargets(List<Object> targets) {
        this.targets = targets;
        return this;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public ProxyRevision withType(String type) {
        this.type = type;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public ProxyRevision withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ProxyRevision.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("basepaths");
        sb.append('=');
        sb.append(((this.basepaths == null)?"<null>":this.basepaths));
        sb.append(',');
        sb.append("configurationVersion");
        sb.append('=');
        sb.append(((this.configurationVersion == null)?"<null>":this.configurationVersion));
        sb.append(',');
        sb.append("contextInfo");
        sb.append('=');
        sb.append(((this.contextInfo == null)?"<null>":this.contextInfo));
        sb.append(',');
        sb.append("createdAt");
        sb.append('=');
        sb.append(((this.createdAt == null)?"<null>":this.createdAt));
        sb.append(',');
        sb.append("createdBy");
        sb.append('=');
        sb.append(((this.createdBy == null)?"<null>":this.createdBy));
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null)?"<null>":this.description));
        sb.append(',');
        sb.append("displayName");
        sb.append('=');
        sb.append(((this.displayName == null)?"<null>":this.displayName));
        sb.append(',');
        sb.append("entityMetaDataAsProperties");
        sb.append('=');
        sb.append(((this.entityMetaDataAsProperties == null)?"<null>":this.entityMetaDataAsProperties));
        sb.append(',');
        sb.append("lastModifiedAt");
        sb.append('=');
        sb.append(((this.lastModifiedAt == null)?"<null>":this.lastModifiedAt));
        sb.append(',');
        sb.append("lastModifiedBy");
        sb.append('=');
        sb.append(((this.lastModifiedBy == null)?"<null>":this.lastModifiedBy));
        sb.append(',');
        sb.append("manifestVersion");
        sb.append('=');
        sb.append(((this.manifestVersion == null)?"<null>":this.manifestVersion));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("policies");
        sb.append('=');
        sb.append(((this.policies == null)?"<null>":this.policies));
        sb.append(',');
        sb.append("proxies");
        sb.append('=');
        sb.append(((this.proxies == null)?"<null>":this.proxies));
        sb.append(',');
        sb.append("proxyEndpoints");
        sb.append('=');
        sb.append(((this.proxyEndpoints == null)?"<null>":this.proxyEndpoints));
        sb.append(',');
        sb.append("resourceFiles");
        sb.append('=');
        sb.append(((this.resourceFiles == null)?"<null>":this.resourceFiles));
        sb.append(',');
        sb.append("resources");
        sb.append('=');
        sb.append(((this.resources == null)?"<null>":this.resources));
        sb.append(',');
        sb.append("revision");
        sb.append('=');
        sb.append(((this.revision == null)?"<null>":this.revision));
        sb.append(',');
        sb.append("sharedFlows");
        sb.append('=');
        sb.append(((this.sharedFlows == null)?"<null>":this.sharedFlows));
        sb.append(',');
        sb.append("spec");
        sb.append('=');
        sb.append(((this.spec == null)?"<null>":this.spec));
        sb.append(',');
        sb.append("targetEndpoints");
        sb.append('=');
        sb.append(((this.targetEndpoints == null)?"<null>":this.targetEndpoints));
        sb.append(',');
        sb.append("targetServers");
        sb.append('=');
        sb.append(((this.targetServers == null)?"<null>":this.targetServers));
        sb.append(',');
        sb.append("targets");
        sb.append('=');
        sb.append(((this.targets == null)?"<null>":this.targets));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.lastModifiedAt == null)? 0 :this.lastModifiedAt.hashCode()));
        result = ((result* 31)+((this.manifestVersion == null)? 0 :this.manifestVersion.hashCode()));
        result = ((result* 31)+((this.displayName == null)? 0 :this.displayName.hashCode()));
        result = ((result* 31)+((this.policies == null)? 0 :this.policies.hashCode()));
        result = ((result* 31)+((this.description == null)? 0 :this.description.hashCode()));
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        result = ((result* 31)+((this.targets == null)? 0 :this.targets.hashCode()));
        result = ((result* 31)+((this.spec == null)? 0 :this.spec.hashCode()));
        result = ((result* 31)+((this.proxyEndpoints == null)? 0 :this.proxyEndpoints.hashCode()));
        result = ((result* 31)+((this.createdAt == null)? 0 :this.createdAt.hashCode()));
        result = ((result* 31)+((this.configurationVersion == null)? 0 :this.configurationVersion.hashCode()));
        result = ((result* 31)+((this.proxies == null)? 0 :this.proxies.hashCode()));
        result = ((result* 31)+((this.basepaths == null)? 0 :this.basepaths.hashCode()));
        result = ((result* 31)+((this.contextInfo == null)? 0 :this.contextInfo.hashCode()));
        result = ((result* 31)+((this.lastModifiedBy == null)? 0 :this.lastModifiedBy.hashCode()));
        result = ((result* 31)+((this.targetServers == null)? 0 :this.targetServers.hashCode()));
        result = ((result* 31)+((this.resources == null)? 0 :this.resources.hashCode()));
        result = ((result* 31)+((this.sharedFlows == null)? 0 :this.sharedFlows.hashCode()));
        result = ((result* 31)+((this.revision == null)? 0 :this.revision.hashCode()));
        result = ((result* 31)+((this.createdBy == null)? 0 :this.createdBy.hashCode()));
        result = ((result* 31)+((this.targetEndpoints == null)? 0 :this.targetEndpoints.hashCode()));
        result = ((result* 31)+((this.resourceFiles == null)? 0 :this.resourceFiles.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.entityMetaDataAsProperties == null)? 0 :this.entityMetaDataAsProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ProxyRevision) == false) {
            return false;
        }
        ProxyRevision rhs = ((ProxyRevision) other);
        return ((((((((((((((((((((((((((this.lastModifiedAt == rhs.lastModifiedAt)||((this.lastModifiedAt!= null)&&this.lastModifiedAt.equals(rhs.lastModifiedAt)))&&((this.manifestVersion == rhs.manifestVersion)||((this.manifestVersion!= null)&&this.manifestVersion.equals(rhs.manifestVersion))))&&((this.displayName == rhs.displayName)||((this.displayName!= null)&&this.displayName.equals(rhs.displayName))))&&((this.policies == rhs.policies)||((this.policies!= null)&&this.policies.equals(rhs.policies))))&&((this.description == rhs.description)||((this.description!= null)&&this.description.equals(rhs.description))))&&((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type))))&&((this.targets == rhs.targets)||((this.targets!= null)&&this.targets.equals(rhs.targets))))&&((this.spec == rhs.spec)||((this.spec!= null)&&this.spec.equals(rhs.spec))))&&((this.proxyEndpoints == rhs.proxyEndpoints)||((this.proxyEndpoints!= null)&&this.proxyEndpoints.equals(rhs.proxyEndpoints))))&&((this.createdAt == rhs.createdAt)||((this.createdAt!= null)&&this.createdAt.equals(rhs.createdAt))))&&((this.configurationVersion == rhs.configurationVersion)||((this.configurationVersion!= null)&&this.configurationVersion.equals(rhs.configurationVersion))))&&((this.proxies == rhs.proxies)||((this.proxies!= null)&&this.proxies.equals(rhs.proxies))))&&((this.basepaths == rhs.basepaths)||((this.basepaths!= null)&&this.basepaths.equals(rhs.basepaths))))&&((this.contextInfo == rhs.contextInfo)||((this.contextInfo!= null)&&this.contextInfo.equals(rhs.contextInfo))))&&((this.lastModifiedBy == rhs.lastModifiedBy)||((this.lastModifiedBy!= null)&&this.lastModifiedBy.equals(rhs.lastModifiedBy))))&&((this.targetServers == rhs.targetServers)||((this.targetServers!= null)&&this.targetServers.equals(rhs.targetServers))))&&((this.resources == rhs.resources)||((this.resources!= null)&&this.resources.equals(rhs.resources))))&&((this.sharedFlows == rhs.sharedFlows)||((this.sharedFlows!= null)&&this.sharedFlows.equals(rhs.sharedFlows))))&&((this.revision == rhs.revision)||((this.revision!= null)&&this.revision.equals(rhs.revision))))&&((this.createdBy == rhs.createdBy)||((this.createdBy!= null)&&this.createdBy.equals(rhs.createdBy))))&&((this.targetEndpoints == rhs.targetEndpoints)||((this.targetEndpoints!= null)&&this.targetEndpoints.equals(rhs.targetEndpoints))))&&((this.resourceFiles == rhs.resourceFiles)||((this.resourceFiles!= null)&&this.resourceFiles.equals(rhs.resourceFiles))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.entityMetaDataAsProperties == rhs.entityMetaDataAsProperties)||((this.entityMetaDataAsProperties!= null)&&this.entityMetaDataAsProperties.equals(rhs.entityMetaDataAsProperties))));
    }

}
