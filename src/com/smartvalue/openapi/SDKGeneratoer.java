package com.smartvalue.openapi;

import org.openapitools.codegen.ClientOptInput;
import org.openapitools.codegen.DefaultGenerator;
import org.openapitools.codegen.config.CodegenConfigurator;

public class SDKGeneratoer {

	private String lang ; 
	private String outputDir ; 
	private boolean validateSpecs ; 
	
	public static void main(String[] args )
	{
		SDKGeneratoer sdkg = new SDKGeneratoer() ;
		sdkg.setLang("java");
		sdkg.setOutputDir("G:\\My Drive\\MasterWorks\\Eclipse-WS\\MOJ_SDK");
		sdkg.setValidateSpecs(false); 
		sdkg.generateSDK("https://raw.githubusercontent.com/openapitools/openapi-generator/master/modules/openapi-generator/src/test/resources/3_0/petstore.json");
	}
	public  void generateSDK(String urlStr)
	{
		//String urlStr = "https://raw.githubusercontent.com/openapitools/openapi-generator/master/modules/openapi-generator/src/test/resources/3_0/petstore.json" ;  
		//URL url = new URL(urlStr) ; 
 
		CodegenConfigurator configurator = new CodegenConfigurator();
		configurator.setInputSpec(urlStr);
        configurator.setGeneratorName( this.getLang()); // Language: Java
        configurator.setOutputDir(this.getOutputDir()); // Output directory
        configurator.setValidateSpec(this.isValidateSpecs()); 
       
        DefaultGenerator generator = new DefaultGenerator();
        ClientOptInput coi = configurator.toClientOptInput() ;
        generator.opts(coi);
        // Run the generator
        generator.generate();

	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getOutputDir() {
		return outputDir;
	}
	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}
	public boolean isValidateSpecs() {
		return validateSpecs;
	}
	public void setValidateSpecs(boolean validateSpecs) {
		this.validateSpecs = validateSpecs;
	}
}
