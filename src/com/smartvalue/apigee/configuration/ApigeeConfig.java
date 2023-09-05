package com.smartvalue.apigee.configuration;


import java.util.ArrayList;
import java.util.HashMap;

import com.smartvalue.apigee.configuration.infra.Infra;


public class ApigeeConfig {
	
	private ArrayList<Partner> Partners ;
	private String fileContent ;  
	
	private HashMap<String , HashMap<String , HashMap<String , Infra> > > partnersMap = new HashMap<> () ; 


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
		return customer ; 
	}

	public Infra getInfra( String m_partnerName , String m_customerName , String m_infraName) throws Exception {
		Customer customer =  this.getCustomer( m_partnerName ,  m_customerName);
		Infra infra = customer.getInfraByName(m_infraName) ; 
		if (infra == null )
		{
			throw new Exception ("Infra " + m_infraName  + " Not Found For Customer " + m_customerName + " and Partner " + m_partnerName ) ;

		}
		return infra ; 
	}


	public String getFileContent() {
		return fileContent;
	}
	

	

}
