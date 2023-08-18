
package com.smartvalue.apigee.rest.schema.application.auto;

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
import com.smartvalue.apigee.resourceManager.ManagementServer;
import com.smartvalue.apigee.rest.schema.ApigeeComman;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "accessType",
    "apiProducts",
    "appFamily",
    "appId",
    "attributes",
    "callbackUrl",
    "createdAt",
    "createdBy",
    "credentials",
    "credentialsLoaded",
    "developerId",
    "lastModifiedAt",
    "lastModifiedBy",
    "name",
    "scopes",
    "status"
})
@Generated("jsonschema2pojo")
public class Application extends ApigeeComman {
    
	@JsonProperty("accessType")
    private String accessType;
    @JsonProperty("apiProducts")
    private List<Object> apiProducts = new ArrayList<Object>();
    @JsonProperty("appFamily")
    private String appFamily;
    @JsonProperty("appId")
    private String appId;
    @JsonProperty("attributes")
    private List<Attribute> attributes = new ArrayList<Attribute>();
    @JsonProperty("callbackUrl")
    private String callbackUrl;
    @JsonProperty("createdAt")
    private Long createdAt;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("credentials")
    private List<Credential> credentials = new ArrayList<Credential>();
    @JsonProperty("credentialsLoaded")
    private Boolean credentialsLoaded;
    @JsonProperty("developerId")
    private String developerId;
    @JsonProperty("lastModifiedAt")
    private Long lastModifiedAt;
    @JsonProperty("lastModifiedBy")
    private String lastModifiedBy;
    @JsonProperty("name")
    private String name;
    @JsonProperty("scopes")
    private List<Object> scopes = new ArrayList<Object>();
    @JsonProperty("status")
    private String status;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("accessType")
    public String getAccessType() {
        return accessType;
    }

    @JsonProperty("accessType")
    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public Application withAccessType(String accessType) {
        this.accessType = accessType;
        return this;
    }

    @JsonProperty("apiProducts")
    public List<Object> getApiProducts() {
        return apiProducts;
    }

    @JsonProperty("apiProducts")
    public void setApiProducts(List<Object> apiProducts) {
        this.apiProducts = apiProducts;
    }

    public Application withApiProducts(List<Object> apiProducts) {
        this.apiProducts = apiProducts;
        return this;
    }

    @JsonProperty("appFamily")
    public String getAppFamily() {
        return appFamily;
    }

    @JsonProperty("appFamily")
    public void setAppFamily(String appFamily) {
        this.appFamily = appFamily;
    }

    public Application withAppFamily(String appFamily) {
        this.appFamily = appFamily;
        return this;
    }

    @JsonProperty("appId")
    public String getAppId() {
        return appId;
    }

    @JsonProperty("appId")
    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Application withAppId(String appId) {
        this.appId = appId;
        return this;
    }

    @JsonProperty("attributes")
    public List<Attribute> getAttributes() {
        return attributes;
    }

    @JsonProperty("attributes")
    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public Application withAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
        return this;
    }

    @JsonProperty("callbackUrl")
    public String getCallbackUrl() {
        return callbackUrl;
    }

    @JsonProperty("callbackUrl")
    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public Application withCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
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

    public Application withCreatedAt(Long createdAt) {
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

    public Application withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    @JsonProperty("credentials")
    public List<Credential> getCredentials() {
        return credentials;
    }

    @JsonProperty("credentials")
    public void setCredentials(List<Credential> credentials) {
        this.credentials = credentials;
    }

    public Application withCredentials(List<Credential> credentials) {
        this.credentials = credentials;
        return this;
    }

    @JsonProperty("credentialsLoaded")
    public Boolean getCredentialsLoaded() {
        return credentialsLoaded;
    }

    @JsonProperty("credentialsLoaded")
    public void setCredentialsLoaded(Boolean credentialsLoaded) {
        this.credentialsLoaded = credentialsLoaded;
    }

    public Application withCredentialsLoaded(Boolean credentialsLoaded) {
        this.credentialsLoaded = credentialsLoaded;
        return this;
    }

    @JsonProperty("developerId")
    public String getDeveloperId() {
        return developerId;
    }

    @JsonProperty("developerId")
    public void setDeveloperId(String developerId) {
        this.developerId = developerId;
    }

    public Application withDeveloperId(String developerId) {
        this.developerId = developerId;
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

    public Application withLastModifiedAt(Long lastModifiedAt) {
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

    public Application withLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
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

    public Application withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("scopes")
    public List<Object> getScopes() {
        return scopes;
    }

    @JsonProperty("scopes")
    public void setScopes(List<Object> scopes) {
        this.scopes = scopes;
    }

    public Application withScopes(List<Object> scopes) {
        this.scopes = scopes;
        return this;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public Application withStatus(String status) {
        this.status = status;
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

    public Application withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Application.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("accessType");
        sb.append('=');
        sb.append(((this.accessType == null)?"<null>":this.accessType));
        sb.append(',');
        sb.append("apiProducts");
        sb.append('=');
        sb.append(((this.apiProducts == null)?"<null>":this.apiProducts));
        sb.append(',');
        sb.append("appFamily");
        sb.append('=');
        sb.append(((this.appFamily == null)?"<null>":this.appFamily));
        sb.append(',');
        sb.append("appId");
        sb.append('=');
        sb.append(((this.appId == null)?"<null>":this.appId));
        sb.append(',');
        sb.append("attributes");
        sb.append('=');
        sb.append(((this.attributes == null)?"<null>":this.attributes));
        sb.append(',');
        sb.append("callbackUrl");
        sb.append('=');
        sb.append(((this.callbackUrl == null)?"<null>":this.callbackUrl));
        sb.append(',');
        sb.append("createdAt");
        sb.append('=');
        sb.append(((this.createdAt == null)?"<null>":this.createdAt));
        sb.append(',');
        sb.append("createdBy");
        sb.append('=');
        sb.append(((this.createdBy == null)?"<null>":this.createdBy));
        sb.append(',');
        sb.append("credentials");
        sb.append('=');
        sb.append(((this.credentials == null)?"<null>":this.credentials));
        sb.append(',');
        sb.append("credentialsLoaded");
        sb.append('=');
        sb.append(((this.credentialsLoaded == null)?"<null>":this.credentialsLoaded));
        sb.append(',');
        sb.append("developerId");
        sb.append('=');
        sb.append(((this.developerId == null)?"<null>":this.developerId));
        sb.append(',');
        sb.append("lastModifiedAt");
        sb.append('=');
        sb.append(((this.lastModifiedAt == null)?"<null>":this.lastModifiedAt));
        sb.append(',');
        sb.append("lastModifiedBy");
        sb.append('=');
        sb.append(((this.lastModifiedBy == null)?"<null>":this.lastModifiedBy));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("scopes");
        sb.append('=');
        sb.append(((this.scopes == null)?"<null>":this.scopes));
        sb.append(',');
        sb.append("status");
        sb.append('=');
        sb.append(((this.status == null)?"<null>":this.status));
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
        result = ((result* 31)+((this.credentials == null)? 0 :this.credentials.hashCode()));
        result = ((result* 31)+((this.lastModifiedBy == null)? 0 :this.lastModifiedBy.hashCode()));
        result = ((result* 31)+((this.appFamily == null)? 0 :this.appFamily.hashCode()));
        result = ((result* 31)+((this.accessType == null)? 0 :this.accessType.hashCode()));
        result = ((result* 31)+((this.createdAt == null)? 0 :this.createdAt.hashCode()));
        result = ((result* 31)+((this.developerId == null)? 0 :this.developerId.hashCode()));
        result = ((result* 31)+((this.createdBy == null)? 0 :this.createdBy.hashCode()));
        result = ((result* 31)+((this.credentialsLoaded == null)? 0 :this.credentialsLoaded.hashCode()));
        result = ((result* 31)+((this.appId == null)? 0 :this.appId.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.attributes == null)? 0 :this.attributes.hashCode()));
        result = ((result* 31)+((this.callbackUrl == null)? 0 :this.callbackUrl.hashCode()));
        result = ((result* 31)+((this.scopes == null)? 0 :this.scopes.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.apiProducts == null)? 0 :this.apiProducts.hashCode()));
        result = ((result* 31)+((this.status == null)? 0 :this.status.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Application) == false) {
            return false;
        }
        Application rhs = ((Application) other);
        return ((((((((((((((((((this.lastModifiedAt == rhs.lastModifiedAt)||((this.lastModifiedAt!= null)&&this.lastModifiedAt.equals(rhs.lastModifiedAt)))&&((this.credentials == rhs.credentials)||((this.credentials!= null)&&this.credentials.equals(rhs.credentials))))&&((this.lastModifiedBy == rhs.lastModifiedBy)||((this.lastModifiedBy!= null)&&this.lastModifiedBy.equals(rhs.lastModifiedBy))))&&((this.appFamily == rhs.appFamily)||((this.appFamily!= null)&&this.appFamily.equals(rhs.appFamily))))&&((this.accessType == rhs.accessType)||((this.accessType!= null)&&this.accessType.equals(rhs.accessType))))&&((this.createdAt == rhs.createdAt)||((this.createdAt!= null)&&this.createdAt.equals(rhs.createdAt))))&&((this.developerId == rhs.developerId)||((this.developerId!= null)&&this.developerId.equals(rhs.developerId))))&&((this.createdBy == rhs.createdBy)||((this.createdBy!= null)&&this.createdBy.equals(rhs.createdBy))))&&((this.credentialsLoaded == rhs.credentialsLoaded)||((this.credentialsLoaded!= null)&&this.credentialsLoaded.equals(rhs.credentialsLoaded))))&&((this.appId == rhs.appId)||((this.appId!= null)&&this.appId.equals(rhs.appId))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.attributes == rhs.attributes)||((this.attributes!= null)&&this.attributes.equals(rhs.attributes))))&&((this.callbackUrl == rhs.callbackUrl)||((this.callbackUrl!= null)&&this.callbackUrl.equals(rhs.callbackUrl))))&&((this.scopes == rhs.scopes)||((this.scopes!= null)&&this.scopes.equals(rhs.scopes))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.apiProducts == rhs.apiProducts)||((this.apiProducts!= null)&&this.apiProducts.equals(rhs.apiProducts))))&&((this.status == rhs.status)||((this.status!= null)&&this.status.equals(rhs.status))));
    }

}
