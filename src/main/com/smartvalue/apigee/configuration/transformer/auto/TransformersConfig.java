package com.smartvalue.apigee.configuration.transformer.auto;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransformersConfig {
	
	@JsonProperty("transformers")
	private ArrayList<TransformerConfig> transformers ;

	public ArrayList<TransformerConfig> getTransformers() {
		return transformers;
	}  
}
