
package com.smartvalue.apigee.rest.schema.proxyEndPoint.auto;

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
    "empty",
    "name",
    "targetEndpoint"
})
@Generated("jsonschema2pojo")
public class RouteRule {

    @JsonProperty("empty")
    private Boolean empty;
    @JsonProperty("name")
    private String name;
    @JsonProperty("targetEndpoint")
    private String targetEndpoint;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("empty")
    public Boolean getEmpty() {
        return empty;
    }

    @JsonProperty("empty")
    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }

    public RouteRule withEmpty(Boolean empty) {
        this.empty = empty;
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

    public RouteRule withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("targetEndpoint")
    public String getTargetEndpoint() {
        return targetEndpoint;
    }

    @JsonProperty("targetEndpoint")
    public void setTargetEndpoint(String targetEndpoint) {
        this.targetEndpoint = targetEndpoint;
    }

    public RouteRule withTargetEndpoint(String targetEndpoint) {
        this.targetEndpoint = targetEndpoint;
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

    public RouteRule withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(RouteRule.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("empty");
        sb.append('=');
        sb.append(((this.empty == null)?"<null>":this.empty));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("targetEndpoint");
        sb.append('=');
        sb.append(((this.targetEndpoint == null)?"<null>":this.targetEndpoint));
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
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.empty == null)? 0 :this.empty.hashCode()));
        result = ((result* 31)+((this.targetEndpoint == null)? 0 :this.targetEndpoint.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof RouteRule) == false) {
            return false;
        }
        RouteRule rhs = ((RouteRule) other);
        return (((((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.empty == rhs.empty)||((this.empty!= null)&&this.empty.equals(rhs.empty))))&&((this.targetEndpoint == rhs.targetEndpoint)||((this.targetEndpoint!= null)&&this.targetEndpoint.equals(rhs.targetEndpoint))));
    }

}
