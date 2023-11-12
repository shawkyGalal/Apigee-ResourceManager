
package com.smartvalue.apigee.rest.schema.product.auto;

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
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.rest.schema.ApigeeComman;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "apiResources",
    "approvalType",
    "attributes",
    "createdAt",
    "createdBy",
    "description",
    "displayName",
    "environments",
    "lastModifiedAt",
    "lastModifiedBy",
    "name",
    "proxies",
    "quota",
    "quotaInterval",
    "quotaTimeUnit",
    "scopes"
})
@Generated("jsonschema2pojo")
public class Product extends ApigeeComman{

	@JsonProperty("apiResources")
    private List<String> apiResources = new ArrayList<String>();
    @JsonProperty("approvalType")
    private String approvalType;
    @JsonProperty("attributes")
    private List<Attribute> attributes = new ArrayList<Attribute>();
    @JsonProperty("createdAt")
    private Long createdAt;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("description")
    private String description;
    @JsonProperty("displayName")
    private String displayName;
    @JsonProperty("environments")
    private List<String> environments = new ArrayList<String>();
    @JsonProperty("lastModifiedAt")
    private Long lastModifiedAt;
    @JsonProperty("lastModifiedBy")
    private String lastModifiedBy;
    @JsonProperty("name")
    private String name;
    @JsonProperty("proxies")
    private List<String> proxies = new ArrayList<String>();
    @JsonProperty("quota")
    private String quota;
    @JsonProperty("quotaInterval")
    private String quotaInterval;
    @JsonProperty("quotaTimeUnit")
    private String quotaTimeUnit;
    @JsonProperty("scopes")
    private List<String> scopes = new ArrayList<String>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("apiResources")
    public List<String> getApiResources() {
        return apiResources;
    }

    @JsonProperty("apiResources")
    public void setApiResources(List<String> apiResources) {
        this.apiResources = apiResources;
    }

    public Product withApiResources(List<String> apiResources) {
        this.apiResources = apiResources;
        return this;
    }

    @JsonProperty("approvalType")
    public String getApprovalType() {
        return approvalType;
    }

    @JsonProperty("approvalType")
    public void setApprovalType(String approvalType) {
        this.approvalType = approvalType;
    }

    public Product withApprovalType(String approvalType) {
        this.approvalType = approvalType;
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

    public Product withAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
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

    public Product withCreatedAt(Long createdAt) {
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

    public Product withCreatedBy(String createdBy) {
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

    public Product withDescription(String description) {
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

    public Product withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    @JsonProperty("environments")
    public List<String> getEnvironments() {
        return environments;
    }

    @JsonProperty("environments")
    public void setEnvironments(List<String> environments) {
        this.environments = environments;
    }

    public Product withEnvironments(List<String> environments) {
        this.environments = environments;
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

    public Product withLastModifiedAt(Long lastModifiedAt) {
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

    public Product withLastModifiedBy(String lastModifiedBy) {
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

    public Product withName(String name) {
        this.name = name;
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

    public Product withProxies(List<String> proxies) {
        this.proxies = proxies;
        return this;
    }

    @JsonProperty("quota")
    public String getQuota() {
        return quota;
    }

    @JsonProperty("quota")
    public void setQuota(String quota) {
        this.quota = quota;
    }

    public Product withQuota(String quota) {
        this.quota = quota;
        return this;
    }

    @JsonProperty("quotaInterval")
    public String getQuotaInterval() {
        return quotaInterval;
    }

    @JsonProperty("quotaInterval")
    public void setQuotaInterval(String quotaInterval) {
        this.quotaInterval = quotaInterval;
    }

    public Product withQuotaInterval(String quotaInterval) {
        this.quotaInterval = quotaInterval;
        return this;
    }

    @JsonProperty("quotaTimeUnit")
    public String getQuotaTimeUnit() {
        return quotaTimeUnit;
    }

    @JsonProperty("quotaTimeUnit")
    public void setQuotaTimeUnit(String quotaTimeUnit) {
        this.quotaTimeUnit = quotaTimeUnit;
    }

    public Product withQuotaTimeUnit(String quotaTimeUnit) {
        this.quotaTimeUnit = quotaTimeUnit;
        return this;
    }

    @JsonProperty("scopes")
    public List<String> getScopes() {
        return scopes;
    }

    @JsonProperty("scopes")
    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public Product withScopes(List<String> scopes) {
        this.scopes = scopes;
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

    public Product withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Product.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("apiResources");
        sb.append('=');
        sb.append(((this.apiResources == null)?"<null>":this.apiResources));
        sb.append(',');
        sb.append("approvalType");
        sb.append('=');
        sb.append(((this.approvalType == null)?"<null>":this.approvalType));
        sb.append(',');
        sb.append("attributes");
        sb.append('=');
        sb.append(((this.attributes == null)?"<null>":this.attributes));
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
        sb.append("environments");
        sb.append('=');
        sb.append(((this.environments == null)?"<null>":this.environments));
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
        sb.append("proxies");
        sb.append('=');
        sb.append(((this.proxies == null)?"<null>":this.proxies));
        sb.append(',');
        sb.append("quota");
        sb.append('=');
        sb.append(((this.quota == null)?"<null>":this.quota));
        sb.append(',');
        sb.append("quotaInterval");
        sb.append('=');
        sb.append(((this.quotaInterval == null)?"<null>":this.quotaInterval));
        sb.append(',');
        sb.append("quotaTimeUnit");
        sb.append('=');
        sb.append(((this.quotaTimeUnit == null)?"<null>":this.quotaTimeUnit));
        sb.append(',');
        sb.append("scopes");
        sb.append('=');
        sb.append(((this.scopes == null)?"<null>":this.scopes));
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
        result = ((result* 31)+((this.quotaInterval == null)? 0 :this.quotaInterval.hashCode()));
        result = ((result* 31)+((this.lastModifiedAt == null)? 0 :this.lastModifiedAt.hashCode()));
        result = ((result* 31)+((this.environments == null)? 0 :this.environments.hashCode()));
        result = ((result* 31)+((this.displayName == null)? 0 :this.displayName.hashCode()));
        result = ((result* 31)+((this.approvalType == null)? 0 :this.approvalType.hashCode()));
        result = ((result* 31)+((this.lastModifiedBy == null)? 0 :this.lastModifiedBy.hashCode()));
        result = ((result* 31)+((this.quotaTimeUnit == null)? 0 :this.quotaTimeUnit.hashCode()));
        result = ((result* 31)+((this.description == null)? 0 :this.description.hashCode()));
        result = ((result* 31)+((this.createdAt == null)? 0 :this.createdAt.hashCode()));
        result = ((result* 31)+((this.createdBy == null)? 0 :this.createdBy.hashCode()));
        result = ((result* 31)+((this.apiResources == null)? 0 :this.apiResources.hashCode()));
        result = ((result* 31)+((this.quota == null)? 0 :this.quota.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.attributes == null)? 0 :this.attributes.hashCode()));
        result = ((result* 31)+((this.proxies == null)? 0 :this.proxies.hashCode()));
        result = ((result* 31)+((this.scopes == null)? 0 :this.scopes.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Product) == false) {
            return false;
        }
        Product rhs = ((Product) other);
        return ((((((((((((((((((this.quotaInterval == rhs.quotaInterval)||((this.quotaInterval!= null)&&this.quotaInterval.equals(rhs.quotaInterval)))&&((this.lastModifiedAt == rhs.lastModifiedAt)||((this.lastModifiedAt!= null)&&this.lastModifiedAt.equals(rhs.lastModifiedAt))))&&((this.environments == rhs.environments)||((this.environments!= null)&&this.environments.equals(rhs.environments))))&&((this.displayName == rhs.displayName)||((this.displayName!= null)&&this.displayName.equals(rhs.displayName))))&&((this.approvalType == rhs.approvalType)||((this.approvalType!= null)&&this.approvalType.equals(rhs.approvalType))))&&((this.lastModifiedBy == rhs.lastModifiedBy)||((this.lastModifiedBy!= null)&&this.lastModifiedBy.equals(rhs.lastModifiedBy))))&&((this.quotaTimeUnit == rhs.quotaTimeUnit)||((this.quotaTimeUnit!= null)&&this.quotaTimeUnit.equals(rhs.quotaTimeUnit))))&&((this.description == rhs.description)||((this.description!= null)&&this.description.equals(rhs.description))))&&((this.createdAt == rhs.createdAt)||((this.createdAt!= null)&&this.createdAt.equals(rhs.createdAt))))&&((this.createdBy == rhs.createdBy)||((this.createdBy!= null)&&this.createdBy.equals(rhs.createdBy))))&&((this.apiResources == rhs.apiResources)||((this.apiResources!= null)&&this.apiResources.equals(rhs.apiResources))))&&((this.quota == rhs.quota)||((this.quota!= null)&&this.quota.equals(rhs.quota))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.attributes == rhs.attributes)||((this.attributes!= null)&&this.attributes.equals(rhs.attributes))))&&((this.proxies == rhs.proxies)||((this.proxies!= null)&&this.proxies.equals(rhs.proxies))))&&((this.scopes == rhs.scopes)||((this.scopes!= null)&&this.scopes.equals(rhs.scopes))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))));
    }

}
