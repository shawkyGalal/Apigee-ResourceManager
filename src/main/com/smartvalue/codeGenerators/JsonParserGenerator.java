package com.smartvalue.codeGenerators;

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

public class JsonParserGenerator {
	public static void main (String[] args) throws Exception
	{
   
		String ProxyEndpoint = "{\r\n"
				+ "    \"connection\": {\r\n"
				+ "        \"basePath\": \"/s3archiving\",\r\n"
				+ "        \"connectionType\": \"httpConnection\",\r\n"
				+ "        \"virtualHost\": [\r\n"
				+ "            \"default\"\r\n"
				+ "        ]\r\n"
				+ "    },\r\n"
				+ "    \"connectionType\": \"httpConnection\",\r\n"
				+ "    \"description\": \"\",\r\n"
				+ "    \"faultRules\": [],\r\n"
				+ "    \"flows\": [\r\n"
				+ "        {\r\n"
				+ "            \"condition\": \"(proxy.pathsuffix MatchesPath \\\"/api/Files/DownloadFile\\\")\",\r\n"
				+ "            \"description\": \"\",\r\n"
				+ "            \"name\": \"download\",\r\n"
				+ "            \"request\": {\r\n"
				+ "                \"children\": [\r\n"
				+ "                    {\r\n"
				+ "                        \"Step\": {\r\n"
				+ "                            \"faultRules\": [],\r\n"
				+ "                            \"name\": \"OA-Download-Scope\"\r\n"
				+ "                        }\r\n"
				+ "                    }\r\n"
				+ "                ]\r\n"
				+ "            },\r\n"
				+ "            \"response\": {\r\n"
				+ "                \"children\": []\r\n"
				+ "            }\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"condition\": \"(proxy.pathsuffix MatchesPath \\\"/api/Files/UploadFile\\\")\",\r\n"
				+ "            \"description\": \"\",\r\n"
				+ "            \"name\": \"upload\",\r\n"
				+ "            \"request\": {\r\n"
				+ "                \"children\": [\r\n"
				+ "                    {\r\n"
				+ "                        \"Step\": {\r\n"
				+ "                            \"faultRules\": [],\r\n"
				+ "                            \"name\": \"OA-Upload-Scope\"\r\n"
				+ "                        }\r\n"
				+ "                    }\r\n"
				+ "                ]\r\n"
				+ "            },\r\n"
				+ "            \"response\": {\r\n"
				+ "                \"children\": []\r\n"
				+ "            }\r\n"
				+ "        }\r\n"
				+ "    ],\r\n"
				+ "    \"name\": \"default\",\r\n"
				+ "    \"postClientFlow\": {\r\n"
				+ "        \"name\": \"PostClientFlow\",\r\n"
				+ "        \"request\": {\r\n"
				+ "            \"children\": []\r\n"
				+ "        },\r\n"
				+ "        \"response\": {\r\n"
				+ "            \"children\": [\r\n"
				+ "                {\r\n"
				+ "                    \"Step\": {\r\n"
				+ "                        \"faultRules\": [],\r\n"
				+ "                        \"name\": \"FC-ELK-Logger\"\r\n"
				+ "                    }\r\n"
				+ "                }\r\n"
				+ "            ]\r\n"
				+ "        }\r\n"
				+ "    },\r\n"
				+ "    \"postFlow\": {\r\n"
				+ "        \"name\": \"PostFlow\",\r\n"
				+ "        \"request\": {\r\n"
				+ "            \"children\": []\r\n"
				+ "        },\r\n"
				+ "        \"response\": {\r\n"
				+ "            \"children\": []\r\n"
				+ "        }\r\n"
				+ "    },\r\n"
				+ "    \"preFlow\": {\r\n"
				+ "        \"name\": \"PreFlow\",\r\n"
				+ "        \"request\": {\r\n"
				+ "            \"children\": [\r\n"
				+ "                {\r\n"
				+ "                    \"Step\": {\r\n"
				+ "                        \"faultRules\": [],\r\n"
				+ "                        \"name\": \"AE-Application-info\"\r\n"
				+ "                    }\r\n"
				+ "                },\r\n"
				+ "                {\r\n"
				+ "                    \"Step\": {\r\n"
				+ "                        \"faultRules\": [],\r\n"
				+ "                        \"name\": \"EV-APP-Custom-Att\"\r\n"
				+ "                    }\r\n"
				+ "                },\r\n"
				+ "                {\r\n"
				+ "                    \"Step\": {\r\n"
				+ "                        \"faultRules\": [],\r\n"
				+ "                        \"name\": \"AM-SET-Backend-Cred\"\r\n"
				+ "                    }\r\n"
				+ "                }\r\n"
				+ "            ]\r\n"
				+ "        },\r\n"
				+ "        \"response\": {\r\n"
				+ "            \"children\": []\r\n"
				+ "        }\r\n"
				+ "    },\r\n"
				+ "    \"routeRule\": [\r\n"
				+ "        {\r\n"
				+ "            \"empty\": false,\r\n"
				+ "            \"name\": \"default\",\r\n"
				+ "            \"targetEndpoint\": \"default\"\r\n"
				+ "        }\r\n"
				+ "    ],\r\n"
				+ "    \"routeRuleNames\": [\r\n"
				+ "        \"default\"\r\n"
				+ "    ],\r\n"
				+ "    \"type\": \"Proxy\"\r\n"
				+ "}" ; 
		generateJavaClassFromJson(ProxyEndpoint, "ProxyEndpoint" , "com.smartvalue.apigee.rest.schema.proxy");
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
