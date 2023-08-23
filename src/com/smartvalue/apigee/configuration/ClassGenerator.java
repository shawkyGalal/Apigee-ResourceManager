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
   
		String proxyDeployment = "{\r\n" + 
				"    \"environment\": [\r\n" + 
				"        {\r\n" + 
				"            \"name\": \"iam-protected\",\r\n" + 
				"            \"revision\": [\r\n" + 
				"                {\r\n" + 
				"                    \"configuration\": {\r\n" + 
				"                        \"basePath\": \"/\",\r\n" + 
				"                        \"configVersion\": \"SHA-512:fe4d685b79cc9a75ead8f0ec6b1b4bb4724b14f0eba7727059bfffea3cede679323174c0c037a931a7ae0bcfd42fb6ced33fc662a02fbd1bb4201283023b6e15:dc-1\",\r\n" + 
				"                        \"steps\": []\r\n" + 
				"                    },\r\n" + 
				"                    \"name\": \"45\",\r\n" + 
				"                    \"server\": [\r\n" + 
				"                        {\r\n" + 
				"                            \"pod\": {\r\n" + 
				"                                \"name\": \"gateway-2\",\r\n" + 
				"                                \"region\": \"dc-2\"\r\n" + 
				"                            },\r\n" + 
				"                            \"status\": \"deployed\",\r\n" + 
				"                            \"type\": [\r\n" + 
				"                                \"message-processor\"\r\n" + 
				"                            ],\r\n" + 
				"                            \"uUID\": \"b633d588-c343-49d0-89e4-2b92230d6b88\"\r\n" + 
				"                        },\r\n" + 
				"                        {\r\n" + 
				"                            \"pod\": {\r\n" + 
				"                                \"name\": \"gateway\",\r\n" + 
				"                                \"region\": \"dc-1\"\r\n" + 
				"                            },\r\n" + 
				"                            \"status\": \"deployed\",\r\n" + 
				"                            \"type\": [\r\n" + 
				"                                \"message-processor\"\r\n" + 
				"                            ],\r\n" + 
				"                            \"uUID\": \"bf9d3f83-0b44-4801-bfc6-d049d377e37b\"\r\n" + 
				"                        },\r\n" + 
				"                        {\r\n" + 
				"                            \"pod\": {\r\n" + 
				"                                \"name\": \"gateway\",\r\n" + 
				"                                \"region\": \"dc-1\"\r\n" + 
				"                            },\r\n" + 
				"                            \"status\": \"deployed\",\r\n" + 
				"                            \"type\": [\r\n" + 
				"                                \"message-processor\"\r\n" + 
				"                            ],\r\n" + 
				"                            \"uUID\": \"a3c6d03a-9fd6-4cc9-a88f-5e943f645388\"\r\n" + 
				"                        },\r\n" + 
				"                        {\r\n" + 
				"                            \"pod\": {\r\n" + 
				"                                \"name\": \"gateway-2\",\r\n" + 
				"                                \"region\": \"dc-2\"\r\n" + 
				"                            },\r\n" + 
				"                            \"status\": \"deployed\",\r\n" + 
				"                            \"type\": [\r\n" + 
				"                                \"message-processor\"\r\n" + 
				"                            ],\r\n" + 
				"                            \"uUID\": \"05cfbb50-df64-411b-b0c3-3a334b827fc4\"\r\n" + 
				"                        },\r\n" + 
				"                        {\r\n" + 
				"                            \"pod\": {\r\n" + 
				"                                \"name\": \"gateway\",\r\n" + 
				"                                \"region\": \"dc-1\"\r\n" + 
				"                            },\r\n" + 
				"                            \"status\": \"deployed\",\r\n" + 
				"                            \"type\": [\r\n" + 
				"                                \"router\"\r\n" + 
				"                            ],\r\n" + 
				"                            \"uUID\": \"8b7f25ce-7725-4a3b-a53b-1d435129b10e\"\r\n" + 
				"                        },\r\n" + 
				"                        {\r\n" + 
				"                            \"pod\": {\r\n" + 
				"                                \"name\": \"gateway-2\",\r\n" + 
				"                                \"region\": \"dc-2\"\r\n" + 
				"                            },\r\n" + 
				"                            \"status\": \"deployed\",\r\n" + 
				"                            \"type\": [\r\n" + 
				"                                \"router\"\r\n" + 
				"                            ],\r\n" + 
				"                            \"uUID\": \"7cce8a50-ecd6-4bcd-a146-8ec9a85ed1c5\"\r\n" + 
				"                        },\r\n" + 
				"                        {\r\n" + 
				"                            \"pod\": {\r\n" + 
				"                                \"name\": \"gateway-2\",\r\n" + 
				"                                \"region\": \"dc-2\"\r\n" + 
				"                            },\r\n" + 
				"                            \"status\": \"deployed\",\r\n" + 
				"                            \"type\": [\r\n" + 
				"                                \"router\"\r\n" + 
				"                            ],\r\n" + 
				"                            \"uUID\": \"49d74066-c784-4b29-9b96-a8c504ab981d\"\r\n" + 
				"                        },\r\n" + 
				"                        {\r\n" + 
				"                            \"pod\": {\r\n" + 
				"                                \"name\": \"gateway\",\r\n" + 
				"                                \"region\": \"dc-1\"\r\n" + 
				"                            },\r\n" + 
				"                            \"status\": \"deployed\",\r\n" + 
				"                            \"type\": [\r\n" + 
				"                                \"router\"\r\n" + 
				"                            ],\r\n" + 
				"                            \"uUID\": \"6a1098ac-ab2f-44c5-9a3b-3530241897fa\"\r\n" + 
				"                        },\r\n" + 
				"                        {\r\n" + 
				"                            \"pod\": {\r\n" + 
				"                                \"name\": \"gateway\",\r\n" + 
				"                                \"region\": \"dc-1\"\r\n" + 
				"                            },\r\n" + 
				"                            \"status\": \"deployed\",\r\n" + 
				"                            \"type\": [\r\n" + 
				"                                \"router\"\r\n" + 
				"                            ],\r\n" + 
				"                            \"uUID\": \"6dcd178d-94ba-464b-af07-b674de15712f\"\r\n" + 
				"                        },\r\n" + 
				"                        {\r\n" + 
				"                            \"pod\": {\r\n" + 
				"                                \"name\": \"gateway-2\",\r\n" + 
				"                                \"region\": \"dc-2\"\r\n" + 
				"                            },\r\n" + 
				"                            \"status\": \"deployed\",\r\n" + 
				"                            \"type\": [\r\n" + 
				"                                \"router\"\r\n" + 
				"                            ],\r\n" + 
				"                            \"uUID\": \"bdab8124-64c0-4a89-8923-73ec96a2bcf3\"\r\n" + 
				"                        }\r\n" + 
				"                    ],\r\n" + 
				"                    \"state\": \"deployed\"\r\n" + 
				"                }\r\n" + 
				"            ]\r\n" + 
				"        }\r\n" + 
				"    ],\r\n" + 
				"    \"name\": \"oidc-core\",\r\n" + 
				"    \"organization\": \"stg\"\r\n" + 
				"}" ; 
		generateJavaClassFromJson(proxyDeployment, "ProxyDeploymentxx" , "com.smartvalue.apigee.rest.schema.proxyDeployment.auto");
		
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
