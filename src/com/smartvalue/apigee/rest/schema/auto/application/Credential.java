
package com.smartvalue.apigee.rest.schema.auto.application;

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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "apiProducts",
    "attributes",
    "consumerKey",
    "consumerSecret",
    "expiresAt",
    "issuedAt",
    "scopes",
    "status"
})
@Generated("jsonschema2pojo")
public class Credential {

    @JsonProperty("apiProducts")
    private List<ApiProduct> apiProducts = new ArrayList<ApiProduct>();
    @JsonProperty("attributes")
    private List<Object> attributes = new ArrayList<Object>();
    @JsonProperty("consumerKey")
    private String consumerKey;
    @JsonProperty("consumerSecret")
    private String consumerSecret;
    @JsonProperty("expiresAt")
    private Integer expiresAt;
    @JsonProperty("issuedAt")
    private Long issuedAt;
    @JsonProperty("scopes")
    private List<Object> scopes = new ArrayList<Object>();
    @JsonProperty("status")
    private String status;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("apiProducts")
    public List<ApiProduct> getApiProducts() {
        return apiProducts;
    }

    @JsonProperty("apiProducts")
    public void setApiProducts(List<ApiProduct> apiProducts) {
        this.apiProducts = apiProducts;
    }

    public Credential withApiProducts(List<ApiProduct> apiProducts) {
        this.apiProducts = apiProducts;
        return this;
    }

    @JsonProperty("attributes")
    public List<Object> getAttributes() {
        return attributes;
    }

    @JsonProperty("attributes")
    public void setAttributes(List<Object> attributes) {
        this.attributes = attributes;
    }

    public Credential withAttributes(List<Object> attributes) {
        this.attributes = attributes;
        return this;
    }

    @JsonProperty("consumerKey")
    public String getConsumerKey() {
        return consumerKey;
    }

    @JsonProperty("consumerKey")
    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public Credential withConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
        return this;
    }

    @JsonProperty("consumerSecret")
    public String getConsumerSecret() {
        return consumerSecret;
    }

    @JsonProperty("consumerSecret")
    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public Credential withConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
        return this;
    }

    @JsonProperty("expiresAt")
    public Integer getExpiresAt() {
        return expiresAt;
    }

    @JsonProperty("expiresAt")
    public void setExpiresAt(Integer expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Credential withExpiresAt(Integer expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }

    @JsonProperty("issuedAt")
    public Long getIssuedAt() {
        return issuedAt;
    }

    @JsonProperty("issuedAt")
    public void setIssuedAt(Long issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Credential withIssuedAt(Long issuedAt) {
        this.issuedAt = issuedAt;
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

    public Credential withScopes(List<Object> scopes) {
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

    public Credential withStatus(String status) {
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

    public Credential withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Credential.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("apiProducts");
        sb.append('=');
        sb.append(((this.apiProducts == null)?"<null>":this.apiProducts));
        sb.append(',');
        sb.append("attributes");
        sb.append('=');
        sb.append(((this.attributes == null)?"<null>":this.attributes));
        sb.append(',');
        sb.append("consumerKey");
        sb.append('=');
        sb.append(((this.consumerKey == null)?"<null>":this.consumerKey));
        sb.append(',');
        sb.append("consumerSecret");
        sb.append('=');
        sb.append(((this.consumerSecret == null)?"<null>":this.consumerSecret));
        sb.append(',');
        sb.append("expiresAt");
        sb.append('=');
        sb.append(((this.expiresAt == null)?"<null>":this.expiresAt));
        sb.append(',');
        sb.append("issuedAt");
        sb.append('=');
        sb.append(((this.issuedAt == null)?"<null>":this.issuedAt));
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
        result = ((result* 31)+((this.consumerSecret == null)? 0 :this.consumerSecret.hashCode()));
        result = ((result* 31)+((this.attributes == null)? 0 :this.attributes.hashCode()));
        result = ((result* 31)+((this.issuedAt == null)? 0 :this.issuedAt.hashCode()));
        result = ((result* 31)+((this.scopes == null)? 0 :this.scopes.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.consumerKey == null)? 0 :this.consumerKey.hashCode()));
        result = ((result* 31)+((this.apiProducts == null)? 0 :this.apiProducts.hashCode()));
        result = ((result* 31)+((this.expiresAt == null)? 0 :this.expiresAt.hashCode()));
        result = ((result* 31)+((this.status == null)? 0 :this.status.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Credential) == false) {
            return false;
        }
        Credential rhs = ((Credential) other);
        return ((((((((((this.consumerSecret == rhs.consumerSecret)||((this.consumerSecret!= null)&&this.consumerSecret.equals(rhs.consumerSecret)))&&((this.attributes == rhs.attributes)||((this.attributes!= null)&&this.attributes.equals(rhs.attributes))))&&((this.issuedAt == rhs.issuedAt)||((this.issuedAt!= null)&&this.issuedAt.equals(rhs.issuedAt))))&&((this.scopes == rhs.scopes)||((this.scopes!= null)&&this.scopes.equals(rhs.scopes))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.consumerKey == rhs.consumerKey)||((this.consumerKey!= null)&&this.consumerKey.equals(rhs.consumerKey))))&&((this.apiProducts == rhs.apiProducts)||((this.apiProducts!= null)&&this.apiProducts.equals(rhs.apiProducts))))&&((this.expiresAt == rhs.expiresAt)||((this.expiresAt!= null)&&this.expiresAt.equals(rhs.expiresAt))))&&((this.status == rhs.status)||((this.status!= null)&&this.status.equals(rhs.status))));
    }

}
