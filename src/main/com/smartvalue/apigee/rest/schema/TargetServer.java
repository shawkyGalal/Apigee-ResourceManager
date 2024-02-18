package com.smartvalue.apigee.rest.schema;

import java.util.ArrayList;


public class TargetServer {
	
	private String host;
    private boolean isEnabled;
    private String name;
    private int port;
    private SSLInfo sSLInfo;
	public TargetServer() {
	}
	public String getHost() {
		return host;
	}
	private void setHost(String host) {
		this.host = host;
	}
	public boolean isEnabled() {
		return isEnabled;
	}
	private void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public String getName() {
		return name;
	}
	private void setName(String name) {
		this.name = name;
	}
	public int getPort() {
		return port;
	}
	private void setPort(int port) {
		this.port = port;
	}
	public SSLInfo getsSLInfo() {
		return sSLInfo;
	}
	private void setsSLInfo(SSLInfo sSLInfo) {
		this.sSLInfo = sSLInfo;
	}

	public class SSLInfo{
	    private ArrayList<Object> ciphers;
	    private String clientAuthEnabled;
	    private String enabled;
	    private boolean ignoreValidationErrors;
	    private ArrayList<Object> protocols;
		public ArrayList<Object> getCiphers() {
			return ciphers;
		}
		private void setCiphers(ArrayList<Object> ciphers) {
			this.ciphers = ciphers;
		}
		public String getClientAuthEnabled() {
			return clientAuthEnabled;
		}
		private void setClientAuthEnabled(String clientAuthEnabled) {
			this.clientAuthEnabled = clientAuthEnabled;
		}
		public String getEnabled() {
			return enabled;
		}
		private void setEnabled(String enabled) {
			this.enabled = enabled;
		}
		public boolean isIgnoreValidationErrors() {
			return ignoreValidationErrors;
		}
		private void setIgnoreValidationErrors(boolean ignoreValidationErrors) {
			this.ignoreValidationErrors = ignoreValidationErrors;
		}
		public ArrayList<Object> getProtocols() {
			return protocols;
		}
		private void setProtocols(ArrayList<Object> protocols) {
			this.protocols = protocols;
		}
	}

}
