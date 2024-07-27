
package com.smartvalue.google.iam.auto;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
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

    //@JsonProperty("access_token")
    private String access_token;
    //@JsonProperty("expires_in")
    private int expires_in;
    //@JsonProperty("refresh_token")
    private String refresh_token;
    //@JsonProperty("scope")
    private String scope;
    //@JsonProperty("token_type")
    private String token_type;
    //@JsonProperty("id_token")
    private String id_token;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("access_token")
    public String getAccess_token() {
        return access_token;
    }

    @JsonProperty("access_token")
    public void setAccess_token(String accessToken) {
        this.access_token = accessToken;
    }

    public GoogleAccessToken withAccessToken(String accessToken) {
        this.access_token = accessToken;
        return this;
    }

    @JsonProperty("expires_in")
    public int getExpires_in() {
        return expires_in;
    }

    @JsonProperty("expires_in")
    public void setExpires_in(int expiresIn) {
        this.expires_in = expiresIn;
    }

    public GoogleAccessToken withExpiresIn(Integer expiresIn) {
        this.expires_in = expiresIn;
        return this;
    }

    @JsonProperty("refresh_token")
    public String getRefreshToken() {
        return refresh_token;
    }

    @JsonProperty("refresh_token")
    public void setRefreshToken(String refreshToken) {
        this.refresh_token = refreshToken;
    }

    public GoogleAccessToken withRefreshToken(String refreshToken) {
        this.refresh_token = refreshToken;
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
        return token_type;
    }

    @JsonProperty("token_type")
    public void setTokenType(String tokenType) {
        this.token_type = tokenType;
    }

    public GoogleAccessToken withTokenType(String tokenType) {
        this.token_type = tokenType;
        return this;
    }

    @JsonProperty("id_token")
    public String getIdToken() {
        return id_token;
    }

    @JsonProperty("id_token")
    public void setIdToken(String idToken) {
        this.id_token = idToken;
    }

    public GoogleAccessToken withIdToken(String idToken) {
        this.id_token = idToken;
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
        sb.append(((this.access_token == null)?"<null>":this.access_token));
        sb.append(',');
        sb.append("expiresIn");
        sb.append('=');
        sb.append(this.expires_in);
        sb.append(',');
        sb.append("refreshToken");
        sb.append('=');
        sb.append(((this.refresh_token == null)?"<null>":this.refresh_token));
        sb.append(',');
        sb.append("scope");
        sb.append('=');
        sb.append(((this.scope == null)?"<null>":this.scope));
        sb.append(',');
        sb.append("tokenType");
        sb.append('=');
        sb.append(((this.token_type == null)?"<null>":this.token_type));
        sb.append(',');
        sb.append("idToken");
        sb.append('=');
        sb.append(((this.id_token == null)?"<null>":this.id_token));
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
        result = ((result* 31)+( this.expires_in));
        result = ((result* 31)+((this.scope == null)? 0 :this.scope.hashCode()));
        result = ((result* 31)+((this.id_token == null)? 0 :this.id_token.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.access_token == null)? 0 :this.access_token.hashCode()));
        result = ((result* 31)+((this.token_type == null)? 0 :this.token_type.hashCode()));
        result = ((result* 31)+((this.refresh_token == null)? 0 :this.refresh_token.hashCode()));
        return result;
    }
    
    public GoogleIdToken verifyIdToken( ) throws GeneralSecurityException, IOException
	{
		String client_id = this.getSourceCredentials().getClient_id(); 
    	HttpTransport httpTransport = new NetHttpTransport() ;
	
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, new JacksonFactory())
				// Specify the CLIENT_ID of the app that accesses the backend:
				.setAudience(Collections.singletonList(client_id))
				// Or, if multiple clients access the backend:
				//.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
				.build();
		return  verifier.verify(this.getIdToken());
	}
    
    public GoogleIdToken getGoogleIdToken( ) throws GeneralSecurityException, IOException
   	{
    	GoogleIdToken result = null ; 
    	if (this.getIdToken()!=null)
    	{
    		result = GoogleIdToken.parse(new JacksonFactory(), this.getIdToken());
    	}
       	return  result ; 
   	}
    

    private GoogleWebAppCredential sourceCredential ; 
	public void setSourceCredentials(GoogleWebAppCredential m_googleWebAppCredential) {
		sourceCredential = m_googleWebAppCredential ; 
		
	}

	public GoogleWebAppCredential getSourceCredentials() {
		return sourceCredential;
	}

  	
}
