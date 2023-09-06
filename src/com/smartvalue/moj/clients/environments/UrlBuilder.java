package com.smartvalue.moj.clients.environments;

import java.security.SecureRandom;

public class UrlBuilder {
	private Environment env ; 
	private boolean forthAuth = false; 
	private String scope ; 
	private String responseType ;
	
	public UrlBuilder (Environment m_env)
	{
		this.env = m_env ; 
	}
	public String buildAuthorizationURL()
	{
 		StringBuffer result = new StringBuffer() ; 
		result.append(this.env.getNafath().getAuthorizationUrl())
		.append( this.buildOidcPath())	; 
		return result.toString();
	}
	public String buildAuthorizationURL(String codeChallange)
	{
		return buildAuthorizationURL() + "&code_challenge=" + codeChallange ; 
	}
	
	public String buildLogoutURL()
	{
		StringBuffer result = new StringBuffer() ; 
		result.append(this.env.getNafath().getLogoutUrl())
		.append( this.buildOidcPath())	; 
		return result.toString();
	}
	
	private StringBuffer buildOidcPath()
	{
		StringBuffer result = new StringBuffer() ; 
		result.append("?client_id=" + this.env.getCredential().getClientId())
		.append("&redirect_uri=" + this.env.getNafath().getRedirectUri())
		.append("&response_type=" + getResponseType() )
		.append("&state=" + generateRandom() )
		.append("&scope=" + getScope() )
		.append("&nonce=" + generateRandom() ) 
		.append("&force-authn=" + isForthAuth() )	; 
		return result;
	}

	private String generateRandom() {
		// Create a secure random number generator
        SecureRandom secureRandom = new SecureRandom();

        // Generate a random byte array
        byte[] nonceBytes = new byte[16]; // 16 bytes = 128 bits (recommended size for nonce)

        secureRandom.nextBytes(nonceBytes);

        // Encode the byte array as a Base64 URL-safe string
        String nonce = java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(nonceBytes);

        return nonce;
		
	}
	
	public boolean isForthAuth() {
		return forthAuth;
	}

		
	public UrlBuilder withForthAuth(boolean forthAuth) {
		this.forthAuth = forthAuth;
		return this ; 
	}

	public String getScope() {
		return scope;
	}
	
	public UrlBuilder withScope(String scope) {
		this.scope = scope;
		return this ; 
	}

	public String getResponseType() {
		return responseType;
	}

		
	public UrlBuilder withResponseType(String responseType) {
		this.responseType = responseType;
		return this ; 
	}
}
