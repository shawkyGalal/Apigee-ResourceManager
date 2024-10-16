
package com.smartvalue.apigee.rest.schema.proxyEndPoint.auto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.w3c.dom.Element;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.smartvalue.apigee.proxyBundle.BundleElement;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "children"
})
@Generated("jsonschema2pojo")
public class Response extends BundleElement {

      
	@JsonProperty("children")
    private List<Child> children = new ArrayList<Child>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("children")
    public List<Child> getChildren() {
        return children;
    }

    @JsonProperty("children")
    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public Response withChildren(List<Child> children) {
        this.children = children;
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

    public Response withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Response .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("children");
        sb.append('=');
        sb.append(((this.children == null)?"<null>":this.children));
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
        result = ((result* 31)+((this.children == null)? 0 :this.children.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Response) == false) {
            return false;
        }
        Response rhs = ((Response) other);
        return (((this.children == rhs.children)||((this.children!= null)&&this.children.equals(rhs.children)))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))));
    }

    //-------------- end of auto code generated -----
    public Response(String proxyName , Element element) {
  		super(proxyName , element);
  	}
}
