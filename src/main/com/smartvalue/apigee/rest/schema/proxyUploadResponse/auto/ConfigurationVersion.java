
package com.smartvalue.apigee.rest.schema.proxyUploadResponse.auto;

import java.util.LinkedHashMap;
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
    "majorVersion",
    "minorVersion"
})
@Generated("jsonschema2pojo")
public class ConfigurationVersion {

    @JsonProperty("majorVersion")
    private Integer majorVersion;
    @JsonProperty("minorVersion")
    private Integer minorVersion;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("majorVersion")
    public Integer getMajorVersion() {
        return majorVersion;
    }

    @JsonProperty("majorVersion")
    public void setMajorVersion(Integer majorVersion) {
        this.majorVersion = majorVersion;
    }

    public ConfigurationVersion withMajorVersion(Integer majorVersion) {
        this.majorVersion = majorVersion;
        return this;
    }

    @JsonProperty("minorVersion")
    public Integer getMinorVersion() {
        return minorVersion;
    }

    @JsonProperty("minorVersion")
    public void setMinorVersion(Integer minorVersion) {
        this.minorVersion = minorVersion;
    }

    public ConfigurationVersion withMinorVersion(Integer minorVersion) {
        this.minorVersion = minorVersion;
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

    public ConfigurationVersion withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ConfigurationVersion.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("majorVersion");
        sb.append('=');
        sb.append(((this.majorVersion == null)?"<null>":this.majorVersion));
        sb.append(',');
        sb.append("minorVersion");
        sb.append('=');
        sb.append(((this.minorVersion == null)?"<null>":this.minorVersion));
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
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.majorVersion == null)? 0 :this.majorVersion.hashCode()));
        result = ((result* 31)+((this.minorVersion == null)? 0 :this.minorVersion.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ConfigurationVersion) == false) {
            return false;
        }
        ConfigurationVersion rhs = ((ConfigurationVersion) other);
        return ((((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties)))&&((this.majorVersion == rhs.majorVersion)||((this.majorVersion!= null)&&this.majorVersion.equals(rhs.majorVersion))))&&((this.minorVersion == rhs.minorVersion)||((this.minorVersion!= null)&&this.minorVersion.equals(rhs.minorVersion))));
    }

}
