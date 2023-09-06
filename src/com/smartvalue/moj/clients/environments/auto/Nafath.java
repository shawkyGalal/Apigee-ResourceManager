
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
    "authorizationUrl",
    "tokenUrl",
    "refreshTokenUrl",
    "tokenWithPkceUrl",
    "refreshTokenWithPkceUrl",
    "redirectUri",
    "logoutUrl"
})
@Generated("jsonschema2pojo")
public class Nafath {

    @JsonProperty("authorizationUrl")
    private String authorizationUrl;
    @JsonProperty("tokenUrl")
    private String tokenUrl;
    @JsonProperty("refreshTokenUrl")
    private String refreshTokenUrl;
    @JsonProperty("tokenWithPkceUrl")
    private String tokenWithPkceUrl;
    @JsonProperty("refreshTokenWithPkceUrl")
    private String refreshTokenWithPkceUrl;
    @JsonProperty("redirectUri")
    private String redirectUri;
    @JsonProperty("logoutUrl")
    private String logoutUrl;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("authorizationUrl")
    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    @JsonProperty("authorizationUrl")
    public void setAuthorizationUrl(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
    }

    public Nafath withAuthorizationUrl(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
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

    public Nafath withTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
        return this;
    }

    @JsonProperty("refreshTokenUrl")
    public String getRefreshTokenUrl() {
        return refreshTokenUrl;
    }

    @JsonProperty("refreshTokenUrl")
    public void setRefreshTokenUrl(String refreshTokenUrl) {
        this.refreshTokenUrl = refreshTokenUrl;
    }

    public Nafath withRefreshTokenUrl(String refreshTokenUrl) {
        this.refreshTokenUrl = refreshTokenUrl;
        return this;
    }

    @JsonProperty("tokenWithPkceUrl")
    public String getTokenWithPkceUrl() {
        return tokenWithPkceUrl;
    }

    @JsonProperty("tokenWithPkceUrl")
    public void setTokenWithPkceUrl(String tokenWithPkceUrl) {
        this.tokenWithPkceUrl = tokenWithPkceUrl;
    }

    public Nafath withTokenWithPkceUrl(String tokenWithPkceUrl) {
        this.tokenWithPkceUrl = tokenWithPkceUrl;
        return this;
    }

    @JsonProperty("refreshTokenWithPkceUrl")
    public String getRefreshTokenWithPkceUrl() {
        return refreshTokenWithPkceUrl;
    }

    @JsonProperty("refreshTokenWithPkceUrl")
    public void setRefreshTokenWithPkceUrl(String refreshTokenWithPkceUrl) {
        this.refreshTokenWithPkceUrl = refreshTokenWithPkceUrl;
    }

    public Nafath withRefreshTokenWithPkceUrl(String refreshTokenWithPkceUrl) {
        this.refreshTokenWithPkceUrl = refreshTokenWithPkceUrl;
        return this;
    }

    @JsonProperty("redirectUri")
    public String getRedirectUri() {
        return redirectUri;
    }

    @JsonProperty("redirectUri")
    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public Nafath withRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

    @JsonProperty("logoutUrl")
    public String getLogoutUrl() {
        return logoutUrl;
    }

    @JsonProperty("logoutUrl")
    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    public Nafath withLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
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

    public Nafath withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Nafath.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("authorizationUrl");
        sb.append('=');
        sb.append(((this.authorizationUrl == null)?"<null>":this.authorizationUrl));
        sb.append(',');
        sb.append("tokenUrl");
        sb.append('=');
        sb.append(((this.tokenUrl == null)?"<null>":this.tokenUrl));
        sb.append(',');
        sb.append("refreshTokenUrl");
        sb.append('=');
        sb.append(((this.refreshTokenUrl == null)?"<null>":this.refreshTokenUrl));
        sb.append(',');
        sb.append("tokenWithPkceUrl");
        sb.append('=');
        sb.append(((this.tokenWithPkceUrl == null)?"<null>":this.tokenWithPkceUrl));
        sb.append(',');
        sb.append("refreshTokenWithPkceUrl");
        sb.append('=');
        sb.append(((this.refreshTokenWithPkceUrl == null)?"<null>":this.refreshTokenWithPkceUrl));
        sb.append(',');
        sb.append("redirectUri");
        sb.append('=');
        sb.append(((this.redirectUri == null)?"<null>":this.redirectUri));
        sb.append(',');
        sb.append("logoutUrl");
        sb.append('=');
        sb.append(((this.logoutUrl == null)?"<null>":this.logoutUrl));
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
        result = ((result* 31)+((this.redirectUri == null)? 0 :this.redirectUri.hashCode()));
        result = ((result* 31)+((this.tokenUrl == null)? 0 :this.tokenUrl.hashCode()));
        result = ((result* 31)+((this.authorizationUrl == null)? 0 :this.authorizationUrl.hashCode()));
        result = ((result* 31)+((this.tokenWithPkceUrl == null)? 0 :this.tokenWithPkceUrl.hashCode()));
        result = ((result* 31)+((this.logoutUrl == null)? 0 :this.logoutUrl.hashCode()));
        result = ((result* 31)+((this.refreshTokenWithPkceUrl == null)? 0 :this.refreshTokenWithPkceUrl.hashCode()));
        result = ((result* 31)+((this.refreshTokenUrl == null)? 0 :this.refreshTokenUrl.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Nafath) == false) {
            return false;
        }
        Nafath rhs = ((Nafath) other);
        return (((((((((this.redirectUri == rhs.redirectUri)||((this.redirectUri!= null)&&this.redirectUri.equals(rhs.redirectUri)))&&((this.tokenUrl == rhs.tokenUrl)||((this.tokenUrl!= null)&&this.tokenUrl.equals(rhs.tokenUrl))))&&((this.authorizationUrl == rhs.authorizationUrl)||((this.authorizationUrl!= null)&&this.authorizationUrl.equals(rhs.authorizationUrl))))&&((this.tokenWithPkceUrl == rhs.tokenWithPkceUrl)||((this.tokenWithPkceUrl!= null)&&this.tokenWithPkceUrl.equals(rhs.tokenWithPkceUrl))))&&((this.logoutUrl == rhs.logoutUrl)||((this.logoutUrl!= null)&&this.logoutUrl.equals(rhs.logoutUrl))))&&((this.refreshTokenWithPkceUrl == rhs.refreshTokenWithPkceUrl)||((this.refreshTokenWithPkceUrl!= null)&&this.refreshTokenWithPkceUrl.equals(rhs.refreshTokenWithPkceUrl))))&&((this.refreshTokenUrl == rhs.refreshTokenUrl)||((this.refreshTokenUrl!= null)&&this.refreshTokenUrl.equals(rhs.refreshTokenUrl))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))));
    }

}
