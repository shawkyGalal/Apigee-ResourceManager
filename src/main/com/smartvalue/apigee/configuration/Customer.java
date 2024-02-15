package com.smartvalue.apigee.configuration;

import java.util.ArrayList;
import java.util.HashMap;

import com.smartvalue.apigee.configuration.infra.Infra;

public class Customer {
	
	private ApigeeConfig parentConfig ; 
	private String Name ; 
	private ArrayList<Infra> Infras ;
	private HashMap<String , Infra> InfrasMap = new HashMap<>();

	public ArrayList<Infra> getInfras() {
		return Infras;
	}

	public void setInfras(ArrayList<Infra> infras) {
		this.Infras = infras;
		
		
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public HashMap<String , Infra> getInfrasMap() {
		return InfrasMap;
	}

	public Infra getInfraByName(String m_name )
	{
		Infra result = null ; 
		for (Infra x : this.getInfras() )
		{
			if (x.getName().equalsIgnoreCase(m_name) )
			{	result = x ;break ;  }
		}
		result.setParentCustomer(this);
		return result ; 
	}

	public void setParentConfig(ApigeeConfig m_apigeeConfig) {
		parentConfig = m_apigeeConfig;
		
	}

	public ApigeeConfig getParentConfig() {
		return parentConfig;
	}

}
