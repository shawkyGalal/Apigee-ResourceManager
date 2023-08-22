
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
    "basePath",
    "configVersion",
    "steps"
})
@Generated("jsonschema2pojo")
public class Configuration {

    @JsonProperty("basePath")
    private String basePath;
    @JsonProperty("configVersion")
    private String configVersion;
    @JsonProperty("steps")
    private List<Object> steps = new ArrayList<Object>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("basePath")
    public String getBasePath() {
        return basePath;
    }

    @JsonProperty("basePath")
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public Configuration withBasePath(String basePath) {
        this.basePath = basePath;
        return this;
    }

    @JsonProperty("configVersion")
    public String getConfigVersion() {
        return configVersion;
    }

    @JsonProperty("configVersion")
    public void setConfigVersion(String configVersion) {
        this.configVersion = configVersion;
    }

    public Configuration withConfigVersion(String configVersion) {
        this.configVersion = configVersion;
        return this;
    }

    @JsonProperty("steps")
    public List<Object> getSteps() {
        return steps;
    }

    @JsonProperty("steps")
    public void setSteps(List<Object> steps) {
        this.steps = steps;
    }

    public Configuration withSteps(List<Object> steps) {
        this.steps = steps;
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

    public Configuration withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Configuration.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("basePath");
        sb.append('=');
        sb.append(((this.basePath == null)?"<null>":this.basePath));
        sb.append(',');
        sb.append("configVersion");
        sb.append('=');
        sb.append(((this.configVersion == null)?"<null>":this.configVersion));
        sb.append(',');
        sb.append("steps");
        sb.append('=');
        sb.append(((this.steps == null)?"<null>":this.steps));
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
        result = ((result* 31)+((this.configVersion == null)? 0 :this.configVersion.hashCode()));
        result = ((result* 31)+((this.steps == null)? 0 :this.steps.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Configuration) == false) {
            return false;
        }
        Configuration rhs = ((Configuration) other);
        return (((((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties)))&&((this.basePath == rhs.basePath)||((this.basePath!= null)&&this.basePath.equals(rhs.basePath))))&&((this.configVersion == rhs.configVersion)||((this.configVersion!= null)&&this.configVersion.equals(rhs.configVersion))))&&((this.steps == rhs.steps)||((this.steps!= null)&&this.steps.equals(rhs.steps))));
    }

}
