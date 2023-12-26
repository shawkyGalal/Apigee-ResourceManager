
package com.smartvalue.apigee.rest.schema.targetServer.auto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
    "clientAuthEnabled",
    "ciphers",
    "ignoreValidationErrors",
    "protocols",
    "keyAlias",
    "keyStore",
    "trustStore"
})
@Generated("jsonschema2pojo")
public class SSLInfo {

    @JsonProperty("clientAuthEnabled")
    private String clientAuthEnabled;
    @JsonProperty("ciphers")
    private List<Object> ciphers = new ArrayList<Object>();
    @JsonProperty("ignoreValidationErrors")
    private String ignoreValidationErrors;
    @JsonProperty("protocols")
    private List<Object> protocols = new ArrayList<Object>();
    @JsonProperty("keyAlias")
    private Object keyAlias;
    @JsonProperty("keyStore")
    private Object keyStore;
    @JsonProperty("trustStore")
    private Object trustStore;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("clientAuthEnabled")
    public String getClientAuthEnabled() {
        return clientAuthEnabled;
    }

    @JsonProperty("clientAuthEnabled")
    public void setClientAuthEnabled(String clientAuthEnabled) {
        this.clientAuthEnabled = clientAuthEnabled;
    }

    public SSLInfo withClientAuthEnabled(String clientAuthEnabled) {
        this.clientAuthEnabled = clientAuthEnabled;
        return this;
    }

    @JsonProperty("ciphers")
    public List<Object> getCiphers() {
        return ciphers;
    }

    @JsonProperty("ciphers")
    public void setCiphers(List<Object> ciphers) {
        this.ciphers = ciphers;
    }

    public SSLInfo withCiphers(List<Object> ciphers) {
        this.ciphers = ciphers;
        return this;
    }

    @JsonProperty("ignoreValidationErrors")
    public String getIgnoreValidationErrors() {
        return ignoreValidationErrors;
    }

    @JsonProperty("ignoreValidationErrors")
    public void setIgnoreValidationErrors(String ignoreValidationErrors) {
        this.ignoreValidationErrors = ignoreValidationErrors;
    }

    public SSLInfo withIgnoreValidationErrors(String ignoreValidationErrors) {
        this.ignoreValidationErrors = ignoreValidationErrors;
        return this;
    }

    @JsonProperty("protocols")
    public List<Object> getProtocols() {
        return protocols;
    }

    @JsonProperty("protocols")
    public void setProtocols(List<Object> protocols) {
        this.protocols = protocols;
    }

    public SSLInfo withProtocols(List<Object> protocols) {
        this.protocols = protocols;
        return this;
    }

    @JsonProperty("keyAlias")
    public Object getKeyAlias() {
        return keyAlias;
    }

    @JsonProperty("keyAlias")
    public void setKeyAlias(Object keyAlias) {
        this.keyAlias = keyAlias;
    }

    public SSLInfo withKeyAlias(Object keyAlias) {
        this.keyAlias = keyAlias;
        return this;
    }

    @JsonProperty("keyStore")
    public Object getKeyStore() {
        return keyStore;
    }

    @JsonProperty("keyStore")
    public void setKeyStore(Object keyStore) {
        this.keyStore = keyStore;
    }

    public SSLInfo withKeyStore(Object keyStore) {
        this.keyStore = keyStore;
        return this;
    }

    @JsonProperty("trustStore")
    public Object getTrustStore() {
        return trustStore;
    }

    @JsonProperty("trustStore")
    public void setTrustStore(Object trustStore) {
        this.trustStore = trustStore;
    }

    public SSLInfo withTrustStore(Object trustStore) {
        this.trustStore = trustStore;
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

    public SSLInfo withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SSLInfo.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("clientAuthEnabled");
        sb.append('=');
        sb.append(((this.clientAuthEnabled == null)?"<null>":this.clientAuthEnabled));
        sb.append(',');
        sb.append("ciphers");
        sb.append('=');
        sb.append(((this.ciphers == null)?"<null>":this.ciphers));
        sb.append(',');
        sb.append("ignoreValidationErrors");
        sb.append('=');
        sb.append(((this.ignoreValidationErrors == null)?"<null>":this.ignoreValidationErrors));
        sb.append(',');
        sb.append("protocols");
        sb.append('=');
        sb.append(((this.protocols == null)?"<null>":this.protocols));
        sb.append(',');
        sb.append("keyAlias");
        sb.append('=');
        sb.append(((this.keyAlias == null)?"<null>":this.keyAlias));
        sb.append(',');
        sb.append("keyStore");
        sb.append('=');
        sb.append(((this.keyStore == null)?"<null>":this.keyStore));
        sb.append(',');
        sb.append("trustStore");
        sb.append('=');
        sb.append(((this.trustStore == null)?"<null>":this.trustStore));
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
        result = ((result* 31)+((this.clientAuthEnabled == null)? 0 :this.clientAuthEnabled.hashCode()));
        result = ((result* 31)+((this.keyAlias == null)? 0 :this.keyAlias.hashCode()));
        result = ((result* 31)+((this.keyStore == null)? 0 :this.keyStore.hashCode()));
        result = ((result* 31)+((this.ciphers == null)? 0 :this.ciphers.hashCode()));
        result = ((result* 31)+((this.trustStore == null)? 0 :this.trustStore.hashCode()));
        result = ((result* 31)+((this.ignoreValidationErrors == null)? 0 :this.ignoreValidationErrors.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.protocols == null)? 0 :this.protocols.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SSLInfo) == false) {
            return false;
        }
        SSLInfo rhs = ((SSLInfo) other);
        return (((((((((this.clientAuthEnabled == rhs.clientAuthEnabled)||((this.clientAuthEnabled!= null)&&this.clientAuthEnabled.equals(rhs.clientAuthEnabled)))&&((this.keyAlias == rhs.keyAlias)||((this.keyAlias!= null)&&this.keyAlias.equals(rhs.keyAlias))))&&((this.keyStore == rhs.keyStore)||((this.keyStore!= null)&&this.keyStore.equals(rhs.keyStore))))&&((this.ciphers == rhs.ciphers)||((this.ciphers!= null)&&this.ciphers.equals(rhs.ciphers))))&&((this.trustStore == rhs.trustStore)||((this.trustStore!= null)&&this.trustStore.equals(rhs.trustStore))))&&((this.ignoreValidationErrors == rhs.ignoreValidationErrors)||((this.ignoreValidationErrors!= null)&&this.ignoreValidationErrors.equals(rhs.ignoreValidationErrors))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.protocols == rhs.protocols)||((this.protocols!= null)&&this.protocols.equals(rhs.protocols))));
    }

}
