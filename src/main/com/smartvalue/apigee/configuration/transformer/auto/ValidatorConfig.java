
package com.smartvalue.apigee.configuration.transformer.auto;

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
    "implClass",
    "attributes", 
    "description"
})
@Generated("jsonschema2pojo")
public class ValidatorConfig {

    @JsonProperty("implClass")
    private String implClass;
    
    @JsonProperty("attributes")
    private List<Attribute> attributes = new ArrayList<Attribute>();
    
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
    
    @JsonProperty("enabled")
    private String enabled;

    @JsonProperty("description")
    private String description;
    
    
    public String getDescription() {
		return description;
	}

	@JsonProperty("implClass")
    public String getImplClass() {
        return implClass;
    }

    @JsonProperty("implClass")
    public void setImplClass(String implClass) {
        this.implClass = implClass;
    }

    public ValidatorConfig withImplClass(String implClass) {
        this.implClass = implClass;
        return this;
    }

    @JsonProperty("attributes")
    public List<Attribute> getAttributes() {
        return attributes;
    }

    @JsonProperty("attributes")
    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public ValidatorConfig withAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
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

    public ValidatorConfig withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    public String getEnabled() {
		return enabled;
	}
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ValidatorConfig.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("implClass");
        sb.append('=');
        sb.append(((this.implClass == null)?"<null>":this.implClass));
        sb.append(',');
        sb.append("attributes");
        sb.append('=');
        sb.append(((this.attributes == null)?"<null>":this.attributes));
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
        result = ((result* 31)+((this.implClass == null)? 0 :this.implClass.hashCode()));
        result = ((result* 31)+((this.attributes == null)? 0 :this.attributes.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ValidatorConfig) == false) {
            return false;
        }
        ValidatorConfig rhs = ((ValidatorConfig) other);
        return ((((this.implClass == rhs.implClass)||((this.implClass!= null)&&this.implClass.equals(rhs.implClass)))&&((this.attributes == rhs.attributes)||((this.attributes!= null)&&this.attributes.equals(rhs.attributes))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))));
    }

}
