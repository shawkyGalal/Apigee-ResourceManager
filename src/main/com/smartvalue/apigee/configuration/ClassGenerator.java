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

import com.sun.codemodel.JCodeModel;

public class ClassGenerator {
	public static void main (String[] args) throws Exception
	{
   
		String Transformers = "{	\"Transformers\" : \r\n"
				+ "							[\r\n"
				+ "								{ \r\n"
				+ "									\"implClass\" : \"class01\", \r\n"
				+ "									 \"attributes\" : \r\n"
				+ "									[\r\n"
				+ "										{ \"name\" : \"nam01\" , \"value\" :\"111\"	}\r\n"
				+ "									  , { \"name\" : \"nam02\" , \"value\" :\"222\"	}\r\n"
				+ "									\r\n"
				+ "									]\r\n"
				+ "								}"
				+ "							]}" ; 
		generateJavaClassFromJson(Transformers, "Transformers" , "com.smartvalue.apigee.configuration.transformer.auto");
		//Gson gson = new Gson() ; 
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
