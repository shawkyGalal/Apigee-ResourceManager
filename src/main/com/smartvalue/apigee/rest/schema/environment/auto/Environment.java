
package com.smartvalue.apigee.rest.schema.environment.auto;

import java.util.HashMap;
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
    "createdAt",
    "createdBy",
    "lastModifiedAt",
    "lastModifiedBy",
    "name",
    "properties"
})
@Generated("jsonschema2pojo")
public class Environment {

    @JsonProperty("createdAt")
    private Long createdAt;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("lastModifiedAt")
    private Long lastModifiedAt;
    @JsonProperty("lastModifiedBy")
    private String lastModifiedBy;
    @JsonProperty("name")
    private String name;
    @JsonProperty("properties")
    private Properties properties;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("createdAt")
    public Long getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("createdAt")
    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Environment withCreatedAt(Long createdAt) {
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

    public Environment withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public Environment withLastModifiedAt(Long lastModifiedAt) {
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

    public Environment withLastModifiedBy(String lastModifiedBy) {
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

    public Environment withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("properties")
    public Properties getProperties() {
        return properties;
    }

    @JsonProperty("properties")
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Environment withProperties(Properties properties) {
        this.properties = properties;
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

    public Environment withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("createdAt");
        sb.append('=');
        sb.append(((this.createdAt == null)?"<null>":this.createdAt));
        sb.append(',');
        sb.append("createdBy");
        sb.append('=');
        sb.append(((this.createdBy == null)?"<null>":this.createdBy));
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
        sb.append("properties");
        sb.append('=');
        sb.append(((this.properties == null)?"<null>":this.properties));
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
        result = ((result* 31)+((this.createdAt == null)? 0 :this.createdAt.hashCode()));
        result = ((result* 31)+((this.lastModifiedAt == null)? 0 :this.lastModifiedAt.hashCode()));
        result = ((result* 31)+((this.createdBy == null)? 0 :this.createdBy.hashCode()));
        result = ((result* 31)+((this.lastModifiedBy == null)? 0 :this.lastModifiedBy.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.properties == null)? 0 :this.properties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Environment) == false) {
            return false;
        }
        Environment rhs = ((Environment) other);
        return ((((((((this.createdAt == rhs.createdAt)||((this.createdAt!= null)&&this.createdAt.equals(rhs.createdAt)))&&((this.lastModifiedAt == rhs.lastModifiedAt)||((this.lastModifiedAt!= null)&&this.lastModifiedAt.equals(rhs.lastModifiedAt))))&&((this.createdBy == rhs.createdBy)||((this.createdBy!= null)&&this.createdBy.equals(rhs.createdBy))))&&((this.lastModifiedBy == rhs.lastModifiedBy)||((this.lastModifiedBy!= null)&&this.lastModifiedBy.equals(rhs.lastModifiedBy))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.properties == rhs.properties)||((this.properties!= null)&&this.properties.equals(rhs.properties))));
    }

}
