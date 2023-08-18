
package com.smartvalue.apigee.rest.schema.server.auto;

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
    "externalHostName",
    "externalIP",
    "internalHostName",
    "internalIP",
    "isUp",
    "pod",
    "reachable",
    "region",
    "tags",
    "type",
    "uUID"
})
@Generated("jsonschema2pojo")
public class Server extends ApigeeComman {

    @JsonProperty("externalHostName")
    private String externalHostName;
    @JsonProperty("externalIP")
    private String externalIP;
    @JsonProperty("internalHostName")
    private String internalHostName;
    @JsonProperty("internalIP")
    private String internalIP;
    @JsonProperty("isUp")
    private Boolean isUp;
    @JsonProperty("pod")
    private String pod;
    @JsonProperty("reachable")
    private Boolean reachable;
    @JsonProperty("region")
    private String region;
    @JsonProperty("tags")
    private Tags tags;
    @JsonProperty("type")
    private List<String> type = new ArrayList<String>();
    @JsonProperty("uUID")
    private String uUID;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("externalHostName")
    public String getExternalHostName() {
        return externalHostName;
    }

    @JsonProperty("externalHostName")
    public void setExternalHostName(String externalHostName) {
        this.externalHostName = externalHostName;
    }

    public Server withExternalHostName(String externalHostName) {
        this.externalHostName = externalHostName;
        return this;
    }

    @JsonProperty("externalIP")
    public String getExternalIP() {
        return externalIP;
    }

    @JsonProperty("externalIP")
    public void setExternalIP(String externalIP) {
        this.externalIP = externalIP;
    }

    public Server withExternalIP(String externalIP) {
        this.externalIP = externalIP;
        return this;
    }

    @JsonProperty("internalHostName")
    public String getInternalHostName() {
        return internalHostName;
    }

    @JsonProperty("internalHostName")
    public void setInternalHostName(String internalHostName) {
        this.internalHostName = internalHostName;
    }

    public Server withInternalHostName(String internalHostName) {
        this.internalHostName = internalHostName;
        return this;
    }

    @JsonProperty("internalIP")
    public String getInternalIP() {
        return internalIP;
    }

    @JsonProperty("internalIP")
    public void setInternalIP(String internalIP) {
        this.internalIP = internalIP;
    }

    public Server withInternalIP(String internalIP) {
        this.internalIP = internalIP;
        return this;
    }

    @JsonProperty("isUp")
    public Boolean getIsUp() {
        return isUp;
    }

    @JsonProperty("isUp")
    public void setIsUp(Boolean isUp) {
        this.isUp = isUp;
    }

    public Server withIsUp(Boolean isUp) {
        this.isUp = isUp;
        return this;
    }

    @JsonProperty("pod")
    public String getPod() {
        return pod;
    }

    @JsonProperty("pod")
    public void setPod(String pod) {
        this.pod = pod;
    }

    public Server withPod(String pod) {
        this.pod = pod;
        return this;
    }

    @JsonProperty("reachable")
    public Boolean getReachable() {
        return reachable;
    }

    @JsonProperty("reachable")
    public void setReachable(Boolean reachable) {
        this.reachable = reachable;
    }

    public Server withReachable(Boolean reachable) {
        this.reachable = reachable;
        return this;
    }

    @JsonProperty("region")
    public String getRegion() {
        return region;
    }

    @JsonProperty("region")
    public void setRegion(String region) {
        this.region = region;
    }

    public Server withRegion(String region) {
        this.region = region;
        return this;
    }

    @JsonProperty("tags")
    public Tags getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(Tags tags) {
        this.tags = tags;
    }

    public Server withTags(Tags tags) {
        this.tags = tags;
        return this;
    }

    @JsonProperty("type")
    public List<String> getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(List<String> type) {
        this.type = type;
    }

    public Server withType(List<String> type) {
        this.type = type;
        return this;
    }

    @JsonProperty("uUID")
    public String getuUID() {
        return uUID;
    }

    @JsonProperty("uUID")
    public void setuUID(String uUID) {
        this.uUID = uUID;
    }

    public Server withuUID(String uUID) {
        this.uUID = uUID;
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

    public Server withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Server.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("externalHostName");
        sb.append('=');
        sb.append(((this.externalHostName == null)?"<null>":this.externalHostName));
        sb.append(',');
        sb.append("externalIP");
        sb.append('=');
        sb.append(((this.externalIP == null)?"<null>":this.externalIP));
        sb.append(',');
        sb.append("internalHostName");
        sb.append('=');
        sb.append(((this.internalHostName == null)?"<null>":this.internalHostName));
        sb.append(',');
        sb.append("internalIP");
        sb.append('=');
        sb.append(((this.internalIP == null)?"<null>":this.internalIP));
        sb.append(',');
        sb.append("isUp");
        sb.append('=');
        sb.append(((this.isUp == null)?"<null>":this.isUp));
        sb.append(',');
        sb.append("pod");
        sb.append('=');
        sb.append(((this.pod == null)?"<null>":this.pod));
        sb.append(',');
        sb.append("reachable");
        sb.append('=');
        sb.append(((this.reachable == null)?"<null>":this.reachable));
        sb.append(',');
        sb.append("region");
        sb.append('=');
        sb.append(((this.region == null)?"<null>":this.region));
        sb.append(',');
        sb.append("tags");
        sb.append('=');
        sb.append(((this.tags == null)?"<null>":this.tags));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("uUID");
        sb.append('=');
        sb.append(((this.uUID == null)?"<null>":this.uUID));
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
        result = ((result* 31)+((this.pod == null)? 0 :this.pod.hashCode()));
        result = ((result* 31)+((this.externalHostName == null)? 0 :this.externalHostName.hashCode()));
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        result = ((result* 31)+((this.internalIP == null)? 0 :this.internalIP.hashCode()));
        result = ((result* 31)+((this.uUID == null)? 0 :this.uUID.hashCode()));
        result = ((result* 31)+((this.reachable == null)? 0 :this.reachable.hashCode()));
        result = ((result* 31)+((this.tags == null)? 0 :this.tags.hashCode()));
        result = ((result* 31)+((this.isUp == null)? 0 :this.isUp.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.internalHostName == null)? 0 :this.internalHostName.hashCode()));
        result = ((result* 31)+((this.region == null)? 0 :this.region.hashCode()));
        result = ((result* 31)+((this.externalIP == null)? 0 :this.externalIP.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Server) == false) {
            return false;
        }
        Server rhs = ((Server) other);
        return (((((((((((((this.pod == rhs.pod)||((this.pod!= null)&&this.pod.equals(rhs.pod)))&&((this.externalHostName == rhs.externalHostName)||((this.externalHostName!= null)&&this.externalHostName.equals(rhs.externalHostName))))&&((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type))))&&((this.internalIP == rhs.internalIP)||((this.internalIP!= null)&&this.internalIP.equals(rhs.internalIP))))&&((this.uUID == rhs.uUID)||((this.uUID!= null)&&this.uUID.equals(rhs.uUID))))&&((this.reachable == rhs.reachable)||((this.reachable!= null)&&this.reachable.equals(rhs.reachable))))&&((this.tags == rhs.tags)||((this.tags!= null)&&this.tags.equals(rhs.tags))))&&((this.isUp == rhs.isUp)||((this.isUp!= null)&&this.isUp.equals(rhs.isUp))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.internalHostName == rhs.internalHostName)||((this.internalHostName!= null)&&this.internalHostName.equals(rhs.internalHostName))))&&((this.region == rhs.region)||((this.region!= null)&&this.region.equals(rhs.region))))&&((this.externalIP == rhs.externalIP)||((this.externalIP!= null)&&this.externalIP.equals(rhs.externalIP))));
    }

}
