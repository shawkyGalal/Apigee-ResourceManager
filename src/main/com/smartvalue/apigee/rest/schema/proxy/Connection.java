
package com.smartvalue.apigee.rest.schema.proxy;

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
    "basePath",
    "connectionType",
    "virtualHost"
})
@Generated("jsonschema2pojo")
public class Connection {

    @JsonProperty("basePath")
    private String basePath;
    @JsonProperty("connectionType")
    private String connectionType;
    @JsonProperty("virtualHost")
    private List<String> virtualHost = new ArrayList<String>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("basePath")
    public String getBasePath() {
        return basePath;
    }

    @JsonProperty("basePath")
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public Connection withBasePath(String basePath) {
        this.basePath = basePath;
        return this;
    }

    @JsonProperty("connectionType")
    public String getConnectionType() {
        return connectionType;
    }

    @JsonProperty("connectionType")
    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public Connection withConnectionType(String connectionType) {
        this.connectionType = connectionType;
        return this;
    }

    @JsonProperty("virtualHost")
    public List<String> getVirtualHost() {
        return virtualHost;
    }

    @JsonProperty("virtualHost")
    public void setVirtualHost(List<String> virtualHost) {
        this.virtualHost = virtualHost;
    }

    public Connection withVirtualHost(List<String> virtualHost) {
        this.virtualHost = virtualHost;
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

    public Connection withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Connection.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("basePath");
        sb.append('=');
        sb.append(((this.basePath == null)?"<null>":this.basePath));
        sb.append(',');
        sb.append("connectionType");
        sb.append('=');
        sb.append(((this.connectionType == null)?"<null>":this.connectionType));
        sb.append(',');
        sb.append("virtualHost");
        sb.append('=');
        sb.append(((this.virtualHost == null)?"<null>":this.virtualHost));
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
        result = ((result* 31)+((this.basePath == null)? 0 :this.basePath.hashCode()));
        result = ((result* 31)+((this.virtualHost == null)? 0 :this.virtualHost.hashCode()));
        result = ((result* 31)+((this.connectionType == null)? 0 :this.connectionType.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Connection) == false) {
            return false;
        }
        Connection rhs = ((Connection) other);
        return (((((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties)))&&((this.basePath == rhs.basePath)||((this.basePath!= null)&&this.basePath.equals(rhs.basePath))))&&((this.virtualHost == rhs.virtualHost)||((this.virtualHost!= null)&&this.virtualHost.equals(rhs.virtualHost))))&&((this.connectionType == rhs.connectionType)||((this.connectionType!= null)&&this.connectionType.equals(rhs.connectionType))));
    }

}
