
package com.smartvalue.apigee.rest.schema.proxyRevision.auto;

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
    "resourceFile"
})
@Generated("jsonschema2pojo")
public class ResourceFiles {

    @JsonProperty("resourceFile")
    private List<ResourceFile> resourceFile = new ArrayList<ResourceFile>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("resourceFile")
    public List<ResourceFile> getResourceFile() {
        return resourceFile;
    }

    @JsonProperty("resourceFile")
    public void setResourceFile(List<ResourceFile> resourceFile) {
        this.resourceFile = resourceFile;
    }

    public ResourceFiles withResourceFile(List<ResourceFile> resourceFile) {
        this.resourceFile = resourceFile;
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

    public ResourceFiles withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ResourceFiles.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("resourceFile");
        sb.append('=');
        sb.append(((this.resourceFile == null)?"<null>":this.resourceFile));
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
        result = ((result* 31)+((this.resourceFile == null)? 0 :this.resourceFile.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ResourceFiles) == false) {
            return false;
        }
        ResourceFiles rhs = ((ResourceFiles) other);
        return (((this.resourceFile == rhs.resourceFile)||((this.resourceFile!= null)&&this.resourceFile.equals(rhs.resourceFile)))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))));
    }

}
