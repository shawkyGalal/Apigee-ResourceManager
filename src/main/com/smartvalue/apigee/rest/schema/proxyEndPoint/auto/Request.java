
package com.smartvalue.apigee.rest.schema.proxyEndPoint.auto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
public class Request extends BundleElement{

	
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

    public Request withChildren(List<Child> children) {
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

    public Request withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Request.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        if ((other instanceof Request) == false) {
            return false;
        }
        Request rhs = ((Request) other);
        return (((this.children == rhs.children)||((this.children!= null)&&this.children.equals(rhs.children)))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))));
    }

  //---------------End of Auto generated Code ------------------------------------
    
    public Request(String proxyname , Element element) throws ParserConfigurationException, SAXException, IOException {
  		super(proxyname , element);
  		populate(proxyname , element) ; 
  	}

  	public void populate(String proxyname , Element element) throws ParserConfigurationException, SAXException, IOException {

  		NodeList stepsElement = element.getElementsByTagName("Step") ;
  		List<Child> children = new ArrayList<Child>();
  		for (int i =0 ; i<stepsElement.getLength() ; i++)
  		{
  			Element stepElement = (Element) stepsElement.item(i) ;
  			Element stepNameElement = (Element) stepElement.getElementsByTagName("Name").item(0); 
  			
  			String stepNameElementStr = stepNameElement.getTextContent() ; 
  			
  			Child child = new Child() ;
  			Step step = new Step() ;
  			step.setName(stepNameElementStr);
  			child.setStep(step);
  			
  			children.add(child) ; 
  	  		 
  		}
  		this.setChildren(children);
  		 
  		
  	}
}
