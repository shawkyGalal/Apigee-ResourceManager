package com.smartvalue.apigee.configuration;

import java.util.ArrayList;
import java.util.HashMap;

import com.smartvalue.apigee.configuration.infra.Infra;

public class Customer {
	
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


}
