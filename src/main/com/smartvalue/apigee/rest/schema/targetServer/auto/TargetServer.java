
package com.smartvalue.apigee.rest.schema.targetServer.auto;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.smartvalue.apigee.rest.schema.ApigeeComman;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "host",
    "port",
    "isEnabled",
    "sSLInfo"
})
@Generated("jsonschema2pojo") 
public class TargetServer extends ApigeeComman {

    @JsonProperty("name")
    private String name;
    @JsonProperty("host")
    private String host;
    @JsonProperty("port")
    private String port;
    @JsonProperty("isEnabled")
    private String isEnabled;
    @JsonProperty("sSLInfo")
    private SSLInfo sSLInfo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public TargetServer withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("host")
    public String getHost() {
        return host;
    }

    @JsonProperty("host")
    public void setHost(String host) {
        this.host = host;
    }

    public TargetServer withHost(String host) {
        this.host = host;
        return this;
    }

    @JsonProperty("port")
    public String getPort() {
        return port;
    }

    @JsonProperty("port")
    public void setPort(String port) {
        this.port = port;
    }

    public TargetServer withPort(String port) {
        this.port = port;
        return this;
    }

    @JsonProperty("isEnabled")
    public String getIsEnabled() {
        return isEnabled;
    }

    @JsonProperty("isEnabled")
    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public TargetServer withIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    @JsonProperty("sSLInfo")
    public SSLInfo getsSLInfo() {
        return sSLInfo;
    }

    @JsonProperty("sSLInfo")
    public void setsSLInfo(SSLInfo sSLInfo) {
        this.sSLInfo = sSLInfo;
    }

    public TargetServer withsSLInfo(SSLInfo sSLInfo) {
        this.sSLInfo = sSLInfo;
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

    public TargetServer withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(TargetServer.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("host");
        sb.append('=');
        sb.append(((this.host == null)?"<null>":this.host));
        sb.append(',');
        sb.append("port");
        sb.append('=');
        sb.append(((this.port == null)?"<null>":this.port));
        sb.append(',');
        sb.append("isEnabled");
        sb.append('=');
        sb.append(((this.isEnabled == null)?"<null>":this.isEnabled));
        sb.append(',');
        sb.append("sSLInfo");
        sb.append('=');
        sb.append(((this.sSLInfo == null)?"<null>":this.sSLInfo));
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
        result = ((result* 31)+((this.port == null)? 0 :this.port.hashCode()));
        result = ((result* 31)+((this.isEnabled == null)? 0 :this.isEnabled.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.host == null)? 0 :this.host.hashCode()));
        result = ((result* 31)+((this.sSLInfo == null)? 0 :this.sSLInfo.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TargetServer) == false) {
            return false;
        }
        TargetServer rhs = ((TargetServer) other);
        return (((((((this.port == rhs.port)||((this.port!= null)&&this.port.equals(rhs.port)))&&((this.isEnabled == rhs.isEnabled)||((this.isEnabled!= null)&&this.isEnabled.equals(rhs.isEnabled))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.host == rhs.host)||((this.host!= null)&&this.host.equals(rhs.host))))&&((this.sSLInfo == rhs.sSLInfo)||((this.sSLInfo!= null)&&this.sSLInfo.equals(rhs.sSLInfo))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))));
    }

}
