
package com.smartvalue.apigee.configuration.infra.googleServiceAccount.auto;

import java.util.LinkedHashMap;
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
    "type",
    "project_id",
    "private_key_id",
    "private_key",
    "client_email",
    "client_id",
    "auth_uri",
    "token_uri",
    "auth_provider_x509_cert_url",
    "client_x509_cert_url",
    "universe_domain"
})
@Generated("jsonschema2pojo")
public class GoogleServiceAccount {

    @JsonProperty("type")
    private String type;
    @JsonProperty("project_id")
    private String project_id;
    @JsonProperty("private_key_id")
    private String private_key_id;
    @JsonProperty("private_key")
    private String private_key;
    @JsonProperty("client_email")
    private String client_email;
    @JsonProperty("client_id")
    private String client_id;
    @JsonProperty("auth_uri")
    private String auth_uri;
    @JsonProperty("token_uri")
    private String token_uri;
    @JsonProperty("auth_provider_x509_cert_url")
    private String auth_provider_x509_cert_url;
    @JsonProperty("client_x509_cert_url")
    private String client_x509_cert_url;
    @JsonProperty("universe_domain")
    private String universe_domain;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public GoogleServiceAccount withType(String type) {
        this.type = type;
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

    public GoogleServiceAccount withProjectId(String projectId) {
        this.project_id = projectId;
        return this;
    }

    @JsonProperty("private_key_id")
    public String getPrivateKeyId() {
        return private_key_id;
    }

    @JsonProperty("private_key_id")
    public void setPrivateKeyId(String privateKeyId) {
        this.private_key_id = privateKeyId;
    }

    public GoogleServiceAccount withPrivateKeyId(String privateKeyId) {
        this.private_key_id = privateKeyId;
        return this;
    }

    @JsonProperty("private_key")
    public String getPrivateKey() {
        return private_key;
    }

    @JsonProperty("private_key")
    public void setPrivateKey(String privateKey) {
        this.private_key = privateKey;
    }

    public GoogleServiceAccount withPrivateKey(String privateKey) {
        this.private_key = privateKey;
        return this;
    }

    @JsonProperty("client_email")
    public String getClientEmail() {
        return client_email;
    }

    @JsonProperty("client_email")
    public void setClientEmail(String clientEmail) {
        this.client_email = clientEmail;
    }

    public GoogleServiceAccount withClientEmail(String clientEmail) {
        this.client_email = clientEmail;
        return this;
    }

    @JsonProperty("client_id")
    public String getClientId() {
        return client_id;
    }

    @JsonProperty("client_id")
    public void setClientId(String clientId) {
        this.client_id = clientId;
    }

    public GoogleServiceAccount withClientId(String clientId) {
        this.client_id = clientId;
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

    public GoogleServiceAccount withAuthUri(String authUri) {
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

    public GoogleServiceAccount withTokenUri(String tokenUri) {
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

    public GoogleServiceAccount withAuthProviderX509CertUrl(String authProviderX509CertUrl) {
        this.auth_provider_x509_cert_url = authProviderX509CertUrl;
        return this;
    }

    @JsonProperty("client_x509_cert_url")
    public String getClientX509CertUrl() {
        return client_x509_cert_url;
    }

    @JsonProperty("client_x509_cert_url")
    public void setClientX509CertUrl(String clientX509CertUrl) {
        this.client_x509_cert_url = clientX509CertUrl;
    }

    public GoogleServiceAccount withClientX509CertUrl(String clientX509CertUrl) {
        this.client_x509_cert_url = clientX509CertUrl;
        return this;
    }

    @JsonProperty("universe_domain")
    public String getUniverseDomain() {
        return universe_domain;
    }

    @JsonProperty("universe_domain")
    public void setUniverseDomain(String universeDomain) {
        this.universe_domain = universeDomain;
    }

    public GoogleServiceAccount withUniverseDomain(String universeDomain) {
        this.universe_domain = universeDomain;
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

    public GoogleServiceAccount withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(GoogleServiceAccount.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));

        sb.append(',');
        sb.append("projectId");
        sb.append('=');
        sb.append(((this.project_id == null)?"<null>":this.project_id));
        sb.append(',');
        sb.append("privateKeyId");
        sb.append('=');
        sb.append(((this.private_key_id == null)?"<null>":this.private_key_id));
        sb.append(',');
        sb.append("privateKey");
        sb.append('=');
        sb.append(((this.private_key == null)?"<null>":this.private_key));
        sb.append(',');
        sb.append("clientEmail");
        sb.append('=');
        sb.append(((this.client_email == null)?"<null>":this.client_email));
        sb.append(',');
        sb.append("clientId");
        sb.append('=');
        sb.append(((this.client_id == null)?"<null>":this.client_id));
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
        sb.append("clientX509CertUrl");
        sb.append('=');
        sb.append(((this.client_x509_cert_url == null)?"<null>":this.client_x509_cert_url));
        sb.append(',');
        sb.append("universeDomain");
        sb.append('=');
        sb.append(((this.universe_domain == null)?"<null>":this.universe_domain));
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
    
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        //sb.append(GoogleServiceAccount.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("{");
        sb.append("\"type\"");
        sb.append(':');
        sb.append(((this.type == null)?"<null>":"\""+this.type+"\""));
        sb.append(',');
        sb.append("\"project_id\"");
        sb.append(':');
        sb.append(((this.project_id == null)?"<null>":"\""+this.project_id+"\""));
        sb.append(',');
        sb.append("\"private_key_id\"");
        sb.append(':');
        sb.append(((this.private_key_id == null)?"<null>":"\""+this.private_key_id+"\""));
        sb.append(',');
        sb.append("\"private_key\"");
        sb.append(':');
        sb.append("\"").append(this.private_key.replaceAll("\n", "\\\\n")).append("\"");
        sb.append(',');
        sb.append("\"client_email\"");
        sb.append(':');
        sb.append(((this.client_email == null)?"<null>":"\""+this.client_email+"\""));
        sb.append(',');
        sb.append("\"client_id\"");
        sb.append(':');
        sb.append(((this.client_id == null)?"<null>":"\""+this.client_id+"\""));
        sb.append(',');
        sb.append("\"auth_uri\"");
        sb.append(':');
        sb.append(((this.auth_uri == null)?"<null>":"\""+this.auth_uri+"\""));
        sb.append(',');
        sb.append("\"token_uri\"");
        sb.append(':');
        sb.append(((this.token_uri == null)?"<null>":"\""+this.token_uri+"\""));
        sb.append(',');
        sb.append("\"auth_provider_x509_cert_url\"");
        sb.append(':');
        sb.append(((this.auth_provider_x509_cert_url == null)?"<null>":"\""+this.auth_provider_x509_cert_url+"\""));
        sb.append(',');
        sb.append("\"client_x509_cert_url\"");
        sb.append(':');
        sb.append(((this.client_x509_cert_url == null)?"<null>":"\""+this.client_x509_cert_url+"\""));
        sb.append(',');
        sb.append("\"universe_domain\"");
        sb.append(':');
        sb.append(((this.universe_domain == null)?"<null>":"\""+this.universe_domain+"\""));
        
        sb.append("}");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.client_id == null)? 0 :this.client_id.hashCode()));
        result = ((result* 31)+((this.client_email == null)? 0 :this.client_email.hashCode()));
        result = ((result* 31)+((this.token_uri == null)? 0 :this.token_uri.hashCode()));
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        result = ((result* 31)+((this.auth_provider_x509_cert_url == null)? 0 :this.auth_provider_x509_cert_url.hashCode()));
        result = ((result* 31)+((this.universe_domain == null)? 0 :this.universe_domain.hashCode()));
        result = ((result* 31)+((this.private_key == null)? 0 :this.private_key.hashCode()));
        result = ((result* 31)+((this.auth_uri == null)? 0 :this.auth_uri.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.client_x509_cert_url == null)? 0 :this.client_x509_cert_url.hashCode()));
        result = ((result* 31)+((this.project_id == null)? 0 :this.project_id.hashCode()));
        result = ((result* 31)+((this.private_key_id == null)? 0 :this.private_key_id.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GoogleServiceAccount) == false) {
            return false;
        }
        GoogleServiceAccount rhs = ((GoogleServiceAccount) other);
        return (((((((((((((this.client_id == rhs.client_id)||((this.client_id!= null)&&this.client_id.equals(rhs.client_id)))&&((this.client_email == rhs.client_email)||((this.client_email!= null)&&this.client_email.equals(rhs.client_email))))&&((this.token_uri == rhs.token_uri)||((this.token_uri!= null)&&this.token_uri.equals(rhs.token_uri))))&&((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type))))&&((this.auth_provider_x509_cert_url == rhs.auth_provider_x509_cert_url)||((this.auth_provider_x509_cert_url!= null)&&this.auth_provider_x509_cert_url.equals(rhs.auth_provider_x509_cert_url))))&&((this.universe_domain == rhs.universe_domain)||((this.universe_domain!= null)&&this.universe_domain.equals(rhs.universe_domain))))&&((this.private_key == rhs.private_key)||((this.private_key!= null)&&this.private_key.equals(rhs.private_key))))&&((this.auth_uri == rhs.auth_uri)||((this.auth_uri!= null)&&this.auth_uri.equals(rhs.auth_uri))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.client_x509_cert_url == rhs.client_x509_cert_url)||((this.client_x509_cert_url!= null)&&this.client_x509_cert_url.equals(rhs.client_x509_cert_url))))&&((this.project_id == rhs.project_id)||((this.project_id!= null)&&this.project_id.equals(rhs.project_id))))&&((this.private_key_id == rhs.private_key_id)||((this.private_key_id!= null)&&this.private_key_id.equals(rhs.private_key_id))));
    }

}
