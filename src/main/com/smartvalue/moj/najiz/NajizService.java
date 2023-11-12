package com.smartvalue.moj.najiz;

import com.smartvalue.moj.clients.environments.Environment;

public class NajizService {

	private Environment environment ;

	public Environment getEnvironment() {
		return environment;
	}

	
	public NajizService(Environment environment )
	{
		this.environment = environment ; 
	}
}
