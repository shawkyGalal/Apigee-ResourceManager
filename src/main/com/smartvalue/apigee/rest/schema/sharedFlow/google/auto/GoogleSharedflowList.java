
package com.smartvalue.apigee.rest.schema.sharedFlow.google.auto;

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
    "sharedFlows"
})
@Generated("jsonschema2pojo")
public class GoogleSharedflowList {

    @JsonProperty("sharedFlows")
    private List<SharedFlow> sharedFlows = new ArrayList<SharedFlow>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("sharedFlows")
    public List<SharedFlow> getSharedFlows() {
        return sharedFlows;
    }

    @JsonProperty("sharedFlows")
    public void setSharedFlows(List<SharedFlow> sharedFlows) {
        this.sharedFlows = sharedFlows;
    }

    public GoogleSharedflowList withSharedFlows(List<SharedFlow> sharedFlows) {
        this.sharedFlows = sharedFlows;
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

    public GoogleSharedflowList withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(GoogleSharedflowList.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("sharedFlows");
        sb.append('=');
        sb.append(((this.sharedFlows == null)?"<null>":this.sharedFlows));
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
        result = ((result* 31)+((this.sharedFlows == null)? 0 :this.sharedFlows.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GoogleSharedflowList) == false) {
            return false;
        }
        GoogleSharedflowList rhs = ((GoogleSharedflowList) other);
        return (((this.sharedFlows == rhs.sharedFlows)||((this.sharedFlows!= null)&&this.sharedFlows.equals(rhs.sharedFlows)))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))));
    }

}
