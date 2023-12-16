package com.smartvalue.openapi;

import org.openapitools.codegen.ClientOptInput;
import org.openapitools.codegen.DefaultGenerator;
import org.openapitools.codegen.config.CodegenConfigurator;

public class SDKGeneratoer {

	private String lang ; 
	private String outputDir ; 
	private boolean validateSpecs ;
	private String packageName ; 
	
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public static void main(String[] args )
	{
		SDKGeneratoer sdkg = new SDKGeneratoer() ;
		sdkg.setLang("java");
		String specsUrl = "https://raw.githubusercontent.com/openapitools/openapi-generator/master/modules/openapi-generator/src/test/resources/3_0/petstore.json" ;  
		sdkg.generateSDK(specsUrl);
		sdkg.setOutputDir("G:\\My Drive\\MasterWorks\\Eclipse-WS\\MOJ_SDK");
		sdkg.setValidateSpecs(false); 
		sdkg.setPackageName("org.moj.najiz.sdk");
		

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
        
        //configurator.setPackageName(this.getPackageName()) ; 
        configurator.setInvokerPackage(this.getPackageName()) ; 
        configurator.setApiPackage(this.getPackageName()+".api") ;
        configurator.setModelPackage(this.getPackageName()+".model") ;
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
