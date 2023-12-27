
package com.smartvalue.apigee.configuration.infra.googleAccessToken.auto;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.smartvalue.apigee.rest.schema.AccessToken;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "access_token",
    "expires_in",
    "refresh_token",
    "scope",
    "token_type",
    "id_token"
})
@Generated("jsonschema2pojo")
public class GoogleAccessToken  extends AccessToken {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private int expiresIn;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("id_token")
    private String idToken;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("access_token")
    public String getAccess_token() {
        return accessToken;
    }

    @JsonProperty("access_token")
    public void setAccess_token(String accessToken) {
        this.accessToken = accessToken;
    }

    public GoogleAccessToken withAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @JsonProperty("expires_in")
    public int getExpires_in() {
        return expiresIn;
    }

    @JsonProperty("expires_in")
    public void setExpires_in(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public GoogleAccessToken withExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    @JsonProperty("refresh_token")
    public String getRefreshToken() {
        return refreshToken;
    }

    @JsonProperty("refresh_token")
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public GoogleAccessToken withRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    @JsonProperty("scope")
    public String getScope() {
        return scope;
    }

    @JsonProperty("scope")
    public void setScope(String scope) {
        this.scope = scope;
    }

    public GoogleAccessToken withScope(String scope) {
        this.scope = scope;
        return this;
    }

    @JsonProperty("token_type")
    public String getTokenType() {
        return tokenType;
    }

    @JsonProperty("token_type")
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public GoogleAccessToken withTokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    @JsonProperty("id_token")
    public String getIdToken() {
        return idToken;
    }

    @JsonProperty("id_token")
    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public GoogleAccessToken withIdToken(String idToken) {
        this.idToken = idToken;
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

    public GoogleAccessToken withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(GoogleAccessToken.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("accessToken");
        sb.append('=');
        sb.append(((this.accessToken == null)?"<null>":this.accessToken));
        sb.append(',');
        sb.append("expiresIn");
        sb.append('=');
        sb.append(this.expiresIn);
        sb.append(',');
        sb.append("refreshToken");
        sb.append('=');
        sb.append(((this.refreshToken == null)?"<null>":this.refreshToken));
        sb.append(',');
        sb.append("scope");
        sb.append('=');
        sb.append(((this.scope == null)?"<null>":this.scope));
        sb.append(',');
        sb.append("tokenType");
        sb.append('=');
        sb.append(((this.tokenType == null)?"<null>":this.tokenType));
        sb.append(',');
        sb.append("idToken");
        sb.append('=');
        sb.append(((this.idToken == null)?"<null>":this.idToken));
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
        result = ((result* 31)+( this.expiresIn));
        result = ((result* 31)+((this.scope == null)? 0 :this.scope.hashCode()));
        result = ((result* 31)+((this.idToken == null)? 0 :this.idToken.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.accessToken == null)? 0 :this.accessToken.hashCode()));
        result = ((result* 31)+((this.tokenType == null)? 0 :this.tokenType.hashCode()));
        result = ((result* 31)+((this.refreshToken == null)? 0 :this.refreshToken.hashCode()));
        return result;
    }

  	
}
