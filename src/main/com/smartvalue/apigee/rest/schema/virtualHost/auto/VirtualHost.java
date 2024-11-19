
package com.smartvalue.apigee.rest.schema.virtualHost.auto;

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
import com.smartvalue.apigee.rest.schema.ApigeeComman;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "hostAliases",
    "interfaces",
    "listenOptions",
    "name",
    "port",
    "propagateTLSInformation",
    "retryOptions",
    "sSLInfo",
    "useBuiltInFreeTrialCert"
})
@Generated("jsonschema2pojo")
public class VirtualHost extends ApigeeComman {

    @JsonProperty("hostAliases")
    private List<String> hostAliases = new ArrayList<String>();
    @JsonProperty("interfaces")
    private List<Object> interfaces = new ArrayList<Object>();
    @JsonProperty("listenOptions")
    private List<Object> listenOptions = new ArrayList<Object>();
    @JsonProperty("name")
    private String name;
    @JsonProperty("port")
    private String port;
    @JsonProperty("propagateTLSInformation")
    private PropagateTLSInformation propagateTLSInformation;
    @JsonProperty("retryOptions")
    private List<Object> retryOptions = new ArrayList<Object>();
    @JsonProperty("sSLInfo")
    private SSLInfo sSLInfo;
    @JsonProperty("useBuiltInFreeTrialCert")
    private Boolean useBuiltInFreeTrialCert;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("hostAliases")
    public List<String> getHostAliases() {
        return hostAliases;
    }

    @JsonProperty("hostAliases")
    public void setHostAliases(List<String> hostAliases) {
        this.hostAliases = hostAliases;
    }

    public VirtualHost withHostAliases(List<String> hostAliases) {
        this.hostAliases = hostAliases;
        return this;
    }

    @JsonProperty("interfaces")
    public List<Object> getInterfaces() {
        return interfaces;
    }

    @JsonProperty("interfaces")
    public void setInterfaces(List<Object> interfaces) {
        this.interfaces = interfaces;
    }

    public VirtualHost withInterfaces(List<Object> interfaces) {
        this.interfaces = interfaces;
        return this;
    }

    @JsonProperty("listenOptions")
    public List<Object> getListenOptions() {
        return listenOptions;
    }

    @JsonProperty("listenOptions")
    public void setListenOptions(List<Object> listenOptions) {
        this.listenOptions = listenOptions;
    }

    public VirtualHost withListenOptions(List<Object> listenOptions) {
        this.listenOptions = listenOptions;
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

    public VirtualHost withName(String name) {
        this.name = name;
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

    public VirtualHost withPort(String port) {
        this.port = port;
        return this;
    }

    @JsonProperty("propagateTLSInformation")
    public PropagateTLSInformation getPropagateTLSInformation() {
        return propagateTLSInformation;
    }

    @JsonProperty("propagateTLSInformation")
    public void setPropagateTLSInformation(PropagateTLSInformation propagateTLSInformation) {
        this.propagateTLSInformation = propagateTLSInformation;
    }

    public VirtualHost withPropagateTLSInformation(PropagateTLSInformation propagateTLSInformation) {
        this.propagateTLSInformation = propagateTLSInformation;
        return this;
    }

    @JsonProperty("retryOptions")
    public List<Object> getRetryOptions() {
        return retryOptions;
    }

    @JsonProperty("retryOptions")
    public void setRetryOptions(List<Object> retryOptions) {
        this.retryOptions = retryOptions;
    }

    public VirtualHost withRetryOptions(List<Object> retryOptions) {
        this.retryOptions = retryOptions;
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

    public VirtualHost withsSLInfo(SSLInfo sSLInfo) {
        this.sSLInfo = sSLInfo;
        return this;
    }

    @JsonProperty("useBuiltInFreeTrialCert")
    public Boolean getUseBuiltInFreeTrialCert() {
        return useBuiltInFreeTrialCert;
    }

    @JsonProperty("useBuiltInFreeTrialCert")
    public void setUseBuiltInFreeTrialCert(Boolean useBuiltInFreeTrialCert) {
        this.useBuiltInFreeTrialCert = useBuiltInFreeTrialCert;
    }

    public VirtualHost withUseBuiltInFreeTrialCert(Boolean useBuiltInFreeTrialCert) {
        this.useBuiltInFreeTrialCert = useBuiltInFreeTrialCert;
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

    public VirtualHost withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(VirtualHost.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("hostAliases");
        sb.append('=');
        sb.append(((this.hostAliases == null)?"<null>":this.hostAliases));
        sb.append(',');
        sb.append("interfaces");
        sb.append('=');
        sb.append(((this.interfaces == null)?"<null>":this.interfaces));
        sb.append(',');
        sb.append("listenOptions");
        sb.append('=');
        sb.append(((this.listenOptions == null)?"<null>":this.listenOptions));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("port");
        sb.append('=');
        sb.append(((this.port == null)?"<null>":this.port));
        sb.append(',');
        sb.append("propagateTLSInformation");
        sb.append('=');
        sb.append(((this.propagateTLSInformation == null)?"<null>":this.propagateTLSInformation));
        sb.append(',');
        sb.append("retryOptions");
        sb.append('=');
        sb.append(((this.retryOptions == null)?"<null>":this.retryOptions));
        sb.append(',');
        sb.append("sSLInfo");
        sb.append('=');
        sb.append(((this.sSLInfo == null)?"<null>":this.sSLInfo));
        sb.append(',');
        sb.append("useBuiltInFreeTrialCert");
        sb.append('=');
        sb.append(((this.useBuiltInFreeTrialCert == null)?"<null>":this.useBuiltInFreeTrialCert));
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
        result = ((result* 31)+((this.listenOptions == null)? 0 :this.listenOptions.hashCode()));
        result = ((result* 31)+((this.interfaces == null)? 0 :this.interfaces.hashCode()));
        result = ((result* 31)+((this.retryOptions == null)? 0 :this.retryOptions.hashCode()));
        result = ((result* 31)+((this.port == null)? 0 :this.port.hashCode()));
        result = ((result* 31)+((this.propagateTLSInformation == null)? 0 :this.propagateTLSInformation.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.hostAliases == null)? 0 :this.hostAliases.hashCode()));
        result = ((result* 31)+((this.sSLInfo == null)? 0 :this.sSLInfo.hashCode()));
        result = ((result* 31)+((this.useBuiltInFreeTrialCert == null)? 0 :this.useBuiltInFreeTrialCert.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof VirtualHost) == false) {
            return false;
        }
        VirtualHost rhs = ((VirtualHost) other);
        return (((((((((((this.listenOptions == rhs.listenOptions)||((this.listenOptions!= null)&&this.listenOptions.equals(rhs.listenOptions)))&&((this.interfaces == rhs.interfaces)||((this.interfaces!= null)&&this.interfaces.equals(rhs.interfaces))))&&((this.retryOptions == rhs.retryOptions)||((this.retryOptions!= null)&&this.retryOptions.equals(rhs.retryOptions))))&&((this.port == rhs.port)||((this.port!= null)&&this.port.equals(rhs.port))))&&((this.propagateTLSInformation == rhs.propagateTLSInformation)||((this.propagateTLSInformation!= null)&&this.propagateTLSInformation.equals(rhs.propagateTLSInformation))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.hostAliases == rhs.hostAliases)||((this.hostAliases!= null)&&this.hostAliases.equals(rhs.hostAliases))))&&((this.sSLInfo == rhs.sSLInfo)||((this.sSLInfo!= null)&&this.sSLInfo.equals(rhs.sSLInfo))))&&((this.useBuiltInFreeTrialCert == rhs.useBuiltInFreeTrialCert)||((this.useBuiltInFreeTrialCert!= null)&&this.useBuiltInFreeTrialCert.equals(rhs.useBuiltInFreeTrialCert))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))));
    }

	@Override
	protected String getUniqueId() {
		return this.getName();
	}

}
