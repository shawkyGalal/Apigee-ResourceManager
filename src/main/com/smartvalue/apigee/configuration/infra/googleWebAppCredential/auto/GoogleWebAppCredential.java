
package com.smartvalue.apigee.configuration.infra.googleWebAppCredential.auto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "client_id",
    "project_id",
    "auth_uri",
    "token_uri",
    "auth_provider_x509_cert_url",
    "client_secret",
    "redirect_uris",
    "javascript_origins"
})
@Generated("jsonschema2pojo")
public class GoogleWebAppCredential {

	
    //@JsonProperty("client_id")
    private String client_id;
    //@JsonProperty("project_id")
    private String project_id;
    //@JsonProperty("auth_uri")
    private String auth_uri;
    //@JsonProperty("token_uri")
    private String token_uri;
    //@JsonProperty("auth_provider_x509_cert_url")
    private String auth_provider_x509_cert_url;
    //@JsonProperty("client_secret")
    private String client_secret;
    //@JsonProperty("redirect_uris")
    private List<String> redirect_uris = new ArrayList<String>();
    //@JsonProperty("javascript_origins")
    private List<String> javascript_origins = new ArrayList<String>();
    //@JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("client_id")
    public String getClient_id() {
        return client_id;
    }

    @JsonProperty("client_id")
    public void setClientId(String clientId) {
        this.client_id = clientId;
    }

    public GoogleWebAppCredential withClientId(String clientId) {
        this.client_id = clientId;
        return this;
    }

    @JsonProperty("project_id")
    public String getProjectId() {
        return project_id;
    }

    @JsonProperty("project_id")
    public void setProjectId(String projectId) {
        this.project_id = projectId;
    }

    public GoogleWebAppCredential withProjectId(String projectId) {
        this.project_id = projectId;
        return this;
    }

    @JsonProperty("auth_uri")
    public String getAuthUri() {
        return auth_uri;
    }

    @JsonProperty("auth_uri")
    public void setAuthUri(String authUri) {
        this.auth_uri = authUri;
    }

    public GoogleWebAppCredential withAuthUri(String authUri) {
        this.auth_uri = authUri;
        return this;
    }

    @JsonProperty("token_uri")
    public String getTokenUri() {
        return token_uri;
    }

    @JsonProperty("token_uri")
    public void setTokenUri(String tokenUri) {
        this.token_uri = tokenUri;
    }

    public GoogleWebAppCredential withTokenUri(String tokenUri) {
        this.token_uri = tokenUri;
        return this;
    }

    @JsonProperty("auth_provider_x509_cert_url")
    public String getAuthProviderX509CertUrl() {
        return auth_provider_x509_cert_url;
    }

    @JsonProperty("auth_provider_x509_cert_url")
    public void setAuthProviderX509CertUrl(String authProviderX509CertUrl) {
        this.auth_provider_x509_cert_url = authProviderX509CertUrl;
    }

    public GoogleWebAppCredential withAuthProviderX509CertUrl(String authProviderX509CertUrl) {
        this.auth_provider_x509_cert_url = authProviderX509CertUrl;
        return this;
    }

    @JsonProperty("client_secret")
    public String getClientSecret() {
        return client_secret;
    }

    @JsonProperty("client_secret")
    public void setClientSecret(String clientSecret) {
        this.client_secret = clientSecret;
    }

    public GoogleWebAppCredential withClientSecret(String clientSecret) {
        this.client_secret = clientSecret;
        return this;
    }

    @JsonProperty("redirect_uris")
    public List<String> getRedirectUris() {
        return redirect_uris;
    }

    @JsonProperty("redirect_uris")
    public void setRedirectUris(List<String> redirectUris) {
        this.redirect_uris = redirectUris;
    }

    public GoogleWebAppCredential withRedirectUris(List<String> redirectUris) {
        this.redirect_uris = redirectUris;
        return this;
    }

    @JsonProperty("javascript_origins")
    public List<String> getJavascriptOrigins() {
        return javascript_origins;
    }

    @JsonProperty("javascript_origins")
    public void setJavascriptOrigins(List<String> javascriptOrigins) {
        this.javascript_origins = javascriptOrigins;
    }

    public GoogleWebAppCredential withJavascriptOrigins(List<String> javascriptOrigins) {
        this.javascript_origins = javascriptOrigins;
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

    public GoogleWebAppCredential withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(GoogleWebAppCredential.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("clientId");
        sb.append('=');
        sb.append(((this.client_id == null)?"<null>":this.client_id));
        sb.append(',');
        sb.append("projectId");
        sb.append('=');
        sb.append(((this.project_id == null)?"<null>":this.project_id));
        sb.append(',');
        sb.append("authUri");
        sb.append('=');
        sb.append(((this.auth_uri == null)?"<null>":this.auth_uri));
        sb.append(',');
        sb.append("tokenUri");
        sb.append('=');
        sb.append(((this.token_uri == null)?"<null>":this.token_uri));
        sb.append(',');
        sb.append("authProviderX509CertUrl");
        sb.append('=');
        sb.append(((this.auth_provider_x509_cert_url == null)?"<null>":this.auth_provider_x509_cert_url));
        sb.append(',');
        sb.append("clientSecret");
        sb.append('=');
        sb.append(((this.client_secret == null)?"<null>":this.client_secret));
        sb.append(',');
        sb.append("redirectUris");
        sb.append('=');
        sb.append(((this.redirect_uris == null)?"<null>":this.redirect_uris));
        sb.append(',');
        sb.append("javascriptOrigins");
        sb.append('=');
        sb.append(((this.javascript_origins == null)?"<null>":this.javascript_origins));
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
        result = ((result* 31)+((this.auth_uri == null)? 0 :this.auth_uri.hashCode()));
        result = ((result* 31)+((this.javascript_origins == null)? 0 :this.javascript_origins.hashCode()));
        result = ((result* 31)+((this.client_id == null)? 0 :this.client_id.hashCode()));
        result = ((result* 31)+((this.token_uri == null)? 0 :this.token_uri.hashCode()));
        result = ((result* 31)+((this.client_secret == null)? 0 :this.client_secret.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.auth_provider_x509_cert_url == null)? 0 :this.auth_provider_x509_cert_url.hashCode()));
        result = ((result* 31)+((this.project_id == null)? 0 :this.project_id.hashCode()));
        result = ((result* 31)+((this.redirect_uris == null)? 0 :this.redirect_uris.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GoogleWebAppCredential) == false) {
            return false;
        }
        GoogleWebAppCredential rhs = ((GoogleWebAppCredential) other);
        return ((((((((((this.auth_uri == rhs.auth_uri)||((this.auth_uri!= null)&&this.auth_uri.equals(rhs.auth_uri)))&&((this.javascript_origins == rhs.javascript_origins)||((this.javascript_origins!= null)&&this.javascript_origins.equals(rhs.javascript_origins))))&&((this.client_id == rhs.client_id)||((this.client_id!= null)&&this.client_id.equals(rhs.client_id))))&&((this.token_uri == rhs.token_uri)||((this.token_uri!= null)&&this.token_uri.equals(rhs.token_uri))))&&((this.client_secret == rhs.client_secret)||((this.client_secret!= null)&&this.client_secret.equals(rhs.client_secret))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.auth_provider_x509_cert_url == rhs.auth_provider_x509_cert_url)||((this.auth_provider_x509_cert_url!= null)&&this.auth_provider_x509_cert_url.equals(rhs.auth_provider_x509_cert_url))))&&((this.project_id == rhs.project_id)||((this.project_id!= null)&&this.project_id.equals(rhs.project_id))))&&((this.redirect_uris == rhs.redirect_uris)||((this.redirect_uris!= null)&&this.redirect_uris.equals(rhs.redirect_uris))));
    }

}
