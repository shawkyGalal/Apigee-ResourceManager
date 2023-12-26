package com.smartvalue.apigee.configuration;

import java.io.File;
import java.io.IOException;

import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;

import com.google.gson.Gson;
import com.smartvalue.apigee.rest.schema.ApigeeAccessToken;
import com.sun.codemodel.JCodeModel;

public class ClassGenerator {
	public static void main (String[] args) throws Exception
	{
   
		String TargetServer = "{\r\n"
				+ "                \"name\": \"Yesser_MOJGSB\",\r\n"
				+ "                \"host\": \"10.179.9.22\",\r\n"
				+ "                \"port\": \"443\",\r\n"
				+ "                \"isEnabled\": \"true\",\r\n"
				+ "                \"sSLInfo\": {\r\n"
				+ "                    \"clientAuthEnabled\": \"false\",\r\n"
				+ "                    \"ciphers\": [],\r\n"
				+ "                    \"ignoreValidationErrors\": \"false\",\r\n"
				+ "                    \"protocols\": [],\r\n"
				+ "                    \"keyAlias\": null,\r\n"
				+ "                    \"keyStore\": null,\r\n"
				+ "                    \"trustStore\": null\r\n"
				+ "                }\r\n"
				+ "            }" ; 
		generateJavaClassFromJson(TargetServer, "TargetServer" , "com.smartvalue.apigee.rest.schema.targetServer.auto");
		Gson gson = new Gson() ; 
		//com.smartvalue.moj.najiz.services.appointments.auto.Appointments appouRequest = gson.fromJson(appointments , com.smartvalue.moj.najiz.services.appointments.auto.Appointments.class) ;
		//System.out.print(appouRequest);
	}
	
	public static void generateJavaClassFromJson(String jsonObject , String m_ClassName , String m_packageName) throws IOException {
		//=========Generate Java Classes From Json Object 
		File f = new File ("src/main") ;
		convertJsonToJavaClass(jsonObject ,  f, m_packageName, m_ClassName  )  ;
		//=======
	}
	
	private static void convertJsonToJavaClass(String jsonObject , File outputJavaClassDirectory, String packageName, String javaClassName)			  throws IOException 
	{
				
	    JCodeModel jcodeModel = new JCodeModel();

	    GenerationConfig config = new DefaultGenerationConfig() {
			    @Override
			    public boolean isGenerateBuilders() 
			    {
				     return true;
				}
		
				@Override
				public SourceType getSourceType() 
				{
				   return SourceType.JSON;
				}
		 };

		 	SchemaGenerator schemaGenerator = new SchemaGenerator();
	        Jackson2Annotator annotator = new Jackson2Annotator(config);
	        SchemaStore schemaStore = new SchemaStore() ; 
	        RuleFactory ruleFactory = new RuleFactory(config, annotator, schemaStore) ;
	                
	        SchemaMapper mapper01 = new SchemaMapper( ruleFactory , schemaGenerator );
			mapper01.generate(jcodeModel, javaClassName, packageName, jsonObject) ; 
	 		jcodeModel.build(outputJavaClassDirectory);
	}

}
