package com.smartvalue.apigee.configuration.transformer.auto;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidatorsConfig {
	
	@JsonProperty("validators")
	private ArrayList<ValidatorConfig> validators ;

	public ArrayList<ValidatorConfig> getValidators() {
		return validators;
	}

}
