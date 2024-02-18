package com.smartvalue.apigee.configuration;


import java.util.ArrayList;

import org.apache.http.HttpHost;


import com.mashape.unirest.http.Unirest;
import com.smartvalue.apigee.configuration.infra.Infra;
import com.smartvalue.apigee.configuration.infra.googleWebAppCredential.GoogleWebAppCredential;


public class AppConfig {
	public static final String GoogleWebAppCredential = "googleWebAppCredential" ;
	private GoogleWebAppCredential googleWebAppCredential ; 
	private ArrayList<Partner> Partners ;
	private String fileContent ;
	private String proxyHost ; 
	private String proxyPort ; 
	
	

	public ArrayList<Partner> getPartners() {
		return Partners;
	}
	public void setPartners(ArrayList<Partner> partners) {
		this.Partners = partners;
	}

	public Partner getPartnerByName(String m_name)
	{
		Partner result = null ; 
		for ( Partner x : this.getPartners() ) 
		{
			if (x.getName().equalsIgnoreCase(m_name) )
			{	result = x ;break ;  }
		}
		return result ; 
	}
	
	
	public Customer getCustomer(String m_partnerName , String m_customerName) throws Exception {
		Partner Partner =  this.getPartnerByName( m_partnerName);
		Customer  customer =  Partner.getCustomerByName(m_customerName);
		if (customer == null)
		{
			throw new Exception ("Customer " + m_customerName + " Not Found For Partner " + m_partnerName ) ;
		}
		customer.setParentConfig(this) ; 
		return customer ; 
	}

	public Infra getInfra( String m_partnerName , String m_customerName , String m_infraName) throws Exception {
		Customer customer =  this.getCustomer( m_partnerName ,  m_customerName);
		Infra infra = customer.getInfraByName(m_infraName) ; 
		if (infra == null )
		{
			throw new Exception ("Infra " + m_infraName  + " Not Found For Customer " + m_customerName + " and Partner " + m_partnerName ) ;
		}

		infra.setParentCustomer(customer) ; 
		return infra ; 
	}


	public String getFileContent() {
		return fileContent;
	}
	public String getProxyHost() {
		return proxyHost;
	}
	public String getProxyPort() {
		return proxyPort;
	}
	

	public void setInternetProxy()
	{
		String proxyHost = this.getProxyHost() ; 
		if (proxyHost != null)
		{
			String proxyPort = this.getProxyPort() ; 
			int proxyPortInt = Integer.parseInt(proxyPort)  ; 
			System.setProperty("http.proxyHost", proxyHost );
		    System.setProperty("http.proxyPort", proxyPort);
		       
			System.setProperty("https.proxyHost", proxyHost) ; 
			System.setProperty("https.proxyPort", proxyPort) ;
			// System.setProperty("http.noProxy", "localhost|127.0.0.1|10.*.*.*|*.moj.gov.*|etc");
			
			Unirest.setProxy(new HttpHost( proxyHost , proxyPortInt ));
		}
			
		}
		

	
	public static void clearInternetProxy()
	{
		System.clearProperty("http.proxyHost") ; 
		System.clearProperty("http.proxyPort") ;
		
		System.clearProperty("https.proxyHost") ;
		System.clearProperty("https.proxyPort") ;
		Unirest.setProxy(null) ; 
	}
	public void setFileContent(String fileContent2) {
		 this.fileContent = fileContent2 ; 
		
	}
	
	public GoogleWebAppCredential getGoogleWebAppCredential() {
		return googleWebAppCredential;
	}

	
}
