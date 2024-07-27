package com.smartvalue.google.iam;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.google.auth.oauth2.GoogleCredentials;

public class GoogleServiceAccount extends com.smartvalue.google.iam.auto.GoogleServiceAccount{

	public com.google.auth.oauth2.GoogleCredentials getGoogleCredentials() throws IOException {
		GoogleCredentials credentials;
			byte[] bytes = this.toJson().getBytes(StandardCharsets.UTF_8);
	        InputStream inputStream  = new java.io.ByteArrayInputStream(bytes);
			credentials = GoogleCredentials.fromStream(inputStream).createScoped("https://www.googleapis.com/auth/cloud-platform");
			credentials.refreshIfExpired();
			//com.google.auth.oauth2.AccessToken token = credentials.getAccessToken();
			return credentials;
	}
	
	  public String toJson() {
	        StringBuilder sb = new StringBuilder();
	        //sb.append(GoogleServiceAccount.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
	        sb.append("{");
	        sb.append("\"type\"");
	        sb.append(':');
	        sb.append(((this.getType() == null)?"<null>":"\""+this.getType()+"\""));
	        sb.append(',');
	        sb.append("\"project_id\"");
	        sb.append(':');
	        sb.append(((this.getProjectId() == null)?"<null>":"\""+this.getProjectId()+"\""));
	        sb.append(',');
	        sb.append("\"private_key_id\"");
	        sb.append(':');
	        sb.append(((this.getPrivateKeyId() == null)?"<null>":"\""+this.getPrivateKeyId()+"\""));
	        sb.append(',');
	        sb.append("\"private_key\"");
	        sb.append(':');
	        sb.append("\"").append(this.getPrivateKey().replaceAll("\n", "\\\\n")).append("\"");
	        sb.append(',');
	        sb.append("\"client_email\"");
	        sb.append(':');
	        sb.append(((this.getClientEmail() == null)?"<null>":"\""+this.getClientEmail()+"\""));
	        sb.append(',');
	        sb.append("\"client_id\"");
	        sb.append(':');
	        sb.append(((this.getClientId() == null)?"<null>":"\""+this.getClientId()+"\""));
	        sb.append(',');
	        sb.append("\"auth_uri\"");
	        sb.append(':');
	        sb.append(((this.getAuthUri() == null)?"<null>":"\""+this.getAuthUri()+"\""));
	        sb.append(',');
	        sb.append("\"token_uri\"");
	        sb.append(':');
	        sb.append(((this.getTokenUri() == null)?"<null>":"\""+this.getTokenUri()+"\""));
	        sb.append(',');
	        sb.append("\"auth_provider_x509_cert_url\"");
	        sb.append(':');
	        sb.append(((this.getAuthProviderX509CertUrl() == null)?"<null>":"\""+this.getAuthProviderX509CertUrl()+"\""));
	        sb.append(',');
	        sb.append("\"client_x509_cert_url\"");
	        sb.append(':');
	        sb.append(((this.getClientX509CertUrl() == null)?"<null>":"\""+this.getClientX509CertUrl()+"\""));
	        sb.append(',');
	        sb.append("\"universe_domain\"");
	        sb.append(':');
	        sb.append(((this.getUniverseDomain() == null)?"<null>":"\""+this.getUniverseDomain()+"\""));
	        
	        sb.append("}");
	        return sb.toString();
	    }
	
}
