
package com.smartvalue.moj.clients.environments.auto;

import java.util.HashMap;
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
    "name",
    "url",
    "tokenUrl",
    "credential",
    "nafath"
})
@Generated("jsonschema2pojo")
public class Environment {

    @JsonProperty("name")
    private String name;
    @JsonProperty("url")
    private String url;
    @JsonProperty("tokenUrl")
    private String tokenUrl;
    @JsonProperty("credential")
    private Credential credential;
    @JsonProperty("nafath")
    private Nafath nafath;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Environment withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    public Environment withUrl(String url) {
        this.url = url;
        return this;
    }

    @JsonProperty("tokenUrl")
    public String getTokenUrl() {
        return tokenUrl;
    }

    @JsonProperty("tokenUrl")
    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public Environment withTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
        return this;
    }

    @JsonProperty("credential")
    public Credential getCredential() {
        return credential;
    }

    @JsonProperty("credential")
    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    public Environment withCredential(Credential credential) {
        this.credential = credential;
        return this;
    }

    @JsonProperty("nafath")
    public Nafath getNafath() {
        return nafath;
    }

    @JsonProperty("nafath")
    public void setNafath(Nafath nafath) {
        this.nafath = nafath;
    }

    public Environment withNafath(Nafath nafath) {
        this.nafath = nafath;
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

    public Environment withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("url");
        sb.append('=');
        sb.append(((this.url == null)?"<null>":this.url));
        sb.append(',');
        sb.append("tokenUrl");
        sb.append('=');
        sb.append(((this.tokenUrl == null)?"<null>":this.tokenUrl));
        sb.append(',');
        sb.append("credential");
        sb.append('=');
        sb.append(((this.credential == null)?"<null>":this.credential));
        sb.append(',');
        sb.append("nafath");
        sb.append('=');
        sb.append(((this.nafath == null)?"<null>":this.nafath));
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
        result = ((result* 31)+((this.tokenUrl == null)? 0 :this.tokenUrl.hashCode()));
        result = ((result* 31)+((this.credential == null)? 0 :this.credential.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.nafath == null)? 0 :this.nafath.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.url == null)? 0 :this.url.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Environment) == false) {
            return false;
        }
        Environment rhs = ((Environment) other);
        return (((((((this.tokenUrl == rhs.tokenUrl)||((this.tokenUrl!= null)&&this.tokenUrl.equals(rhs.tokenUrl)))&&((this.credential == rhs.credential)||((this.credential!= null)&&this.credential.equals(rhs.credential))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.nafath == rhs.nafath)||((this.nafath!= null)&&this.nafath.equals(rhs.nafath))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.url == rhs.url)||((this.url!= null)&&this.url.equals(rhs.url))));
    }

}
