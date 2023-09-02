package com.smartvalue.apigee.configuration;

import java.util.ArrayList;
import java.util.HashMap;

import com.smartvalue.apigee.configuration.infra.Infra;

public class Partner {
	private ArrayList<Customer> Customers ;
	private String Name ;  

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public ArrayList<Customer> getCustomers() {
		return Customers;
	}

	public void setCustomers(ArrayList<Customer> customers) {
		Customers = customers;
		
	}

	public HashMap<String, HashMap<String, Infra>> getCustomersMap() {
		HashMap<String, HashMap<String, Infra>> result = new HashMap<>() ; 
		for (Customer cust : this.getCustomers())
		{
			
			HashMap<String, Infra> infraMap = new HashMap<>() ; 
			for ( Infra infra : cust.getInfras())
			{
				infraMap.put(infra.getName() , infra) ; 
				
			}
			result.put(cust.getName() , infraMap ) ; 
			
		}
		
		return result;
	}

	public Customer getCustomerByName(String m_name )
	{
		Customer result = null ; 
		for (Customer x : this.getCustomers())
		{
			if (x.getName().equalsIgnoreCase(m_name) )
			{	result = x ;break ;  }
		}
		
		return result ; 
	}
	

	
}
