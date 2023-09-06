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
   
		String environments = "{\r\n"
				+ " \"Environments\" : \r\n"
				+ " 	[ \r\n"
				+ "	 	{\r\n"
				+ "	 			\"name\" : \"testing\" , \r\n"
				+ "	 			\"url\" : \"https://apis-test.moj.gov.sa/\" ,\r\n"
				+ "	 			\"tokenUrl\" : \"https://apis-test.moj.gov.sa/v1/authorize/access-token\", \r\n"
				+ "	 			\"credential\" : \r\n"
				+ "				{\r\n"
				+ "					\"clientId\" 		:  \"MtQPBZK57lXLJlB93gHYdA6f6o9bNzp8\" , \r\n"
				+ "					\"clientSecret\" 	: \"bcbt6p9TzgsQYw3G\" \r\n"
				+ "				} , \r\n"
				+ "				\"nafath\" : \r\n"
				+ "				{\r\n"
				+ "					\"authorizationUrl\"			: \"https://apis-test.moj.gov.sa/oidc-core/oauth2/authorization\" , \r\n"
				+ "					\"tokenUrl\"          		: \"https://apis-test.moj.gov.sa/oidc-core/oauth2/token\" , \r\n"
				+ "					\"refreshTokenUrl\"   		: \"https://apis-test.moj.gov.sa/oidc-core/oauth2/token/refresh\" ,\r\n"
				+ "\r\n"
				+ "					\"tokenWithPkceUrl\" 			: \"https://apis-test.moj.gov.sa/oidc-core/oauth2/pkce/token\" ,\r\n"
				+ "					\"refreshTokenWithPkceUrl\"  	: \"https://apis-test.moj.gov.sa/oidc-core/oauth2/pkce/token/refresh\" ,\r\n"
				+ "					  \r\n"
				+ "					\"redirectUri\" 				: \"https://apis-test.moj.gov.sa/oidc-helpers/callback-handler.html\", \r\n"
				+ "					\"logoutUrl\"					: \"https://apis-test.moj.gov.sa/oidc-core/logout/wsaml\" \r\n"
				+ "				}\r\n"
				+ "	 	}, \r\n"
				+ "	 	\r\n"
				+ "	 	{\r\n"
				+ "	 			\"name\" : \"prod\" , \r\n"
				+ "	 			\"url\" : \"https://apis.moj.gov.sa/\" ,\r\n"
				+ "	 			\"tokenUrl\" : \"https://api.moj.gov.sa/v1/authorize/access-token\",\r\n"
				+ "				\"credential\" : \r\n"
				+ "				{\r\n"
				+ "					\"clientId\" 		:  \"u0iC4EnKDdVLQvud1GUSzSgwUMhxwyXP\" , \r\n"
				+ "					\"clientSecret\" 	: \"UKLBn325ZIX1mulU\"\r\n"
				+ "				}\r\n"
				+ "				, \r\n"
				+ "				\"nafath\" : \r\n"
				+ "				{\r\n"
				+ "					\"authorizationUrl\"			: \"https://apis.moj.gov.sa/oidc-core/oauth2/authorization\" , \r\n"
				+ "					\"tokenUrl\"          		: \"https://apis.moj.gov.sa/oidc-core/oauth2/token\" , \r\n"
				+ "					\"refreshTokenUrl\"   		: \"https://apis.moj.gov.sa/oidc-core/oauth2/token/refresh\" ,\r\n"
				+ "\r\n"
				+ "					\"tokenWithPkceUrl\" 			: \"https://apis.moj.gov.sa/oidc-core/oauth2/pkce/token\" ,\r\n"
				+ "					\"refreshTokenWithPkceUrl\"  	: \"https://apis.moj.gov.sa/oidc-core/oauth2/pkce/token/refresh\" ,\r\n"
				+ "					  \r\n"
				+ "					\"redirectUri\" 				: \"https://apis.moj.gov.sa/oidc-helpers/callback-handler.html\" , \r\n"
				+ "					\"logoutUrl\"					: \"https://apis.moj.gov.sa/oidc-core/logout/wsaml\"\r\n"
				+ "				}\r\n"
				+ "	 	}\r\n"
				+ "	 	\r\n"
				+ "	 ]\r\n"
				+ "}" ; 
		generateJavaClassFromJson(environments, "Environments" , "com.smartvalue.moj.clients.environments.auto");
		
	}
	
	public static void generateJavaClassFromJson(String jsonObject , String m_ClassName , String m_packageName) throws IOException {
		//=========Generate Java Classes From Json Object 
		File f = new File ("src") ;
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
