
package com.smartvalue.apigee.rest.schema.keyValueMap.auto;

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
    "encrypted",
    "entry",
    "name"
})
@Generated("jsonschema2pojo")
public class KeyValueMap extends ApigeeComman {

    @JsonProperty("encrypted")
    private Boolean encrypted;
    @JsonProperty("entry")
    private List<Entry> entry = new ArrayList<Entry>();
    @JsonProperty("name")
    private String name;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("encrypted")
    public Boolean getEncrypted() {
        return encrypted;
    }

    @JsonProperty("encrypted")
    public void setEncrypted(Boolean encrypted) {
        this.encrypted = encrypted;
    }

    public KeyValueMap withEncrypted(Boolean encrypted) {
        this.encrypted = encrypted;
        return this;
    }

    @JsonProperty("entry")
    public List<Entry> getEntry() {
        return entry;
    }

    @JsonProperty("entry")
    public void setEntry(List<Entry> entry) {
        this.entry = entry;
    }

    public KeyValueMap withEntry(List<Entry> entry) {
        this.entry = entry;
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

    public KeyValueMap withName(String name) {
        this.name = name;
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

    public KeyValueMap withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(KeyValueMap.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("encrypted");
        sb.append('=');
        sb.append(((this.encrypted == null)?"<null>":this.encrypted));
        sb.append(',');
        sb.append("entry");
        sb.append('=');
        sb.append(((this.entry == null)?"<null>":this.entry));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
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
        result = ((result* 31)+((this.entry == null)? 0 :this.entry.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.encrypted == null)? 0 :this.encrypted.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof KeyValueMap) == false) {
            return false;
        }
        KeyValueMap rhs = ((KeyValueMap) other);
        return (((((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.entry == rhs.entry)||((this.entry!= null)&&this.entry.equals(rhs.entry))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.encrypted == rhs.encrypted)||((this.encrypted!= null)&&this.encrypted.equals(rhs.encrypted))));
    }

}
