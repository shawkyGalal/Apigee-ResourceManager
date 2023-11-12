
package com.smartvalue.apigee.rest.schema.proxyDeployment.auto;

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
    "configuration",
    "name",
    "server",
    "state"
})
@Generated("jsonschema2pojo")
public class Revision {

    @JsonProperty("configuration")
    private Configuration configuration;
    @JsonProperty("name")
    private String name;
    @JsonProperty("server")
    private List<Server> server = new ArrayList<Server>();
    @JsonProperty("state")
    private String state;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("configuration")
    public Configuration getConfiguration() {
        return configuration;
    }

    @JsonProperty("configuration")
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Revision withConfiguration(Configuration configuration) {
        this.configuration = configuration;
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

    public Revision withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("server")
    public List<Server> getServer() {
        return server;
    }

    @JsonProperty("server")
    public void setServer(List<Server> server) {
        this.server = server;
    }

    public Revision withServer(List<Server> server) {
        this.server = server;
        return this;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    public Revision withState(String state) {
        this.state = state;
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

    public Revision withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Revision.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("configuration");
        sb.append('=');
        sb.append(((this.configuration == null)?"<null>":this.configuration));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("server");
        sb.append('=');
        sb.append(((this.server == null)?"<null>":this.server));
        sb.append(',');
        sb.append("state");
        sb.append('=');
        sb.append(((this.state == null)?"<null>":this.state));
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
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.server == null)? 0 :this.server.hashCode()));
        result = ((result* 31)+((this.state == null)? 0 :this.state.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.configuration == null)? 0 :this.configuration.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Revision) == false) {
            return false;
        }
        Revision rhs = ((Revision) other);
        return ((((((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.server == rhs.server)||((this.server!= null)&&this.server.equals(rhs.server))))&&((this.state == rhs.state)||((this.state!= null)&&this.state.equals(rhs.state))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.configuration == rhs.configuration)||((this.configuration!= null)&&this.configuration.equals(rhs.configuration))));
    }

}
