
package com.smartvalue.apigee.rest.schema.virtualHost.auto;

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
    "clientProperties",
    "connectionProperties"
})
@Generated("jsonschema2pojo")
public class PropagateTLSInformation {

    @JsonProperty("clientProperties")
    private Boolean clientProperties;
    @JsonProperty("connectionProperties")
    private Boolean connectionProperties;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("clientProperties")
    public Boolean getClientProperties() {
        return clientProperties;
    }

    @JsonProperty("clientProperties")
    public void setClientProperties(Boolean clientProperties) {
        this.clientProperties = clientProperties;
    }

    public PropagateTLSInformation withClientProperties(Boolean clientProperties) {
        this.clientProperties = clientProperties;
        return this;
    }

    @JsonProperty("connectionProperties")
    public Boolean getConnectionProperties() {
        return connectionProperties;
    }

    @JsonProperty("connectionProperties")
    public void setConnectionProperties(Boolean connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

    public PropagateTLSInformation withConnectionProperties(Boolean connectionProperties) {
        this.connectionProperties = connectionProperties;
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

    public PropagateTLSInformation withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(PropagateTLSInformation.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("clientProperties");
        sb.append('=');
        sb.append(((this.clientProperties == null)?"<null>":this.clientProperties));
        sb.append(',');
        sb.append("connectionProperties");
        sb.append('=');
        sb.append(((this.connectionProperties == null)?"<null>":this.connectionProperties));
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
        result = ((result* 31)+((this.connectionProperties == null)? 0 :this.connectionProperties.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.clientProperties == null)? 0 :this.clientProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PropagateTLSInformation) == false) {
            return false;
        }
        PropagateTLSInformation rhs = ((PropagateTLSInformation) other);
        return ((((this.connectionProperties == rhs.connectionProperties)||((this.connectionProperties!= null)&&this.connectionProperties.equals(rhs.connectionProperties)))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.clientProperties == rhs.clientProperties)||((this.clientProperties!= null)&&this.clientProperties.equals(rhs.clientProperties))));
    }

}
