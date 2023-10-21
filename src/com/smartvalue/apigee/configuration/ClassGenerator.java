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
   
		String appointments = "{\"appointments\":[{\r\n" + 
				"      \"siteName\": \"المحكمة العامة بالرياض\",\r\n" + 
				"      \"endTime\": \"08:30:00\",\r\n" + 
				"      \"serviceName\": \"أخرى\",\r\n" + 
				"      \"categoryId\": 1,\r\n" + 
				"      \"id\": 3271448,\r\n" + 
				"      \"startTime\": \"08:00:00\",\r\n" + 
				"      \"departmentId\": null,\r\n" + 
				"      \"protectedId\": \"CfDJ8Hpf0LF32ZZEl5LBltTmiYUvMIxcKmdHvXvrnemwvGVuvUQsUPY_7SmbVok-l9BvuSlEfckrAyawaXia6TA7wqdQIqqlcMZVNcu6ZbSPG9M_HLZAJGWO-2wO13RJNLt9jg\",\r\n" + 
				"      \"externalId\": \"ea2b0f12-93e1-4b5c-a6ad-8d8885c1e5db\",\r\n" + 
				"      \"departmentName\": \"الدائرة القضائية\",\r\n" + 
				"      \"date\": \"2023-10-16T00:00:00\",\r\n" + 
				"      \"siteId\": null,\r\n" + 
				"      \"regionName\": \"الرياض\",\r\n" + 
				"      \"reservationDateTime\": \"2023-10-15T08:15:09.947\",\r\n" + 
				"      \"regionId\": null,\r\n" + 
				"      \"slotId\": 0\r\n" + 
				"    }],\"requests\":[{\"id\":2313822,\"protectedId\":\"CfDJ8Hpf0LF32ZZEl5LBltTmiYVHaekkz-BrPVgEtCqGz1h82VIY5Rp1Vf2GW6hFJBzwULv2mv4U3H728YtxZ0wdADQ8R7BpvZkbSm2FEarKxBwESDh79_u-PZdRCV8KMvSz7g\",\"creationDateTime\":\"2023-10-16T12:39:34.457\",\"regionName\":\"الرياض\",\"siteName\":\"المحكمة العامة بالرياض\",\"departmentName\":\"الدائرة القضائية\",\"subject\":\"إستلام صك غير إلكتروني\",\"statusId\":2,\"processingDateTime\":\"2023-10-16T12:50:07.757\",\"rejectionReasons\":\"عزيزي المستفيد نسعد بخدمتكم ونأمل توضيح نوع ورقم الصك المطلوب استلامه أو تعديله\",\"categoryId\":1,\"slotId\":5,\"date\":\"2023-10-22T00:00:00\",\"startTime\":\"08:00:00\",\"endTime\":\"08:30:00\"}]}" ; 
		generateJavaClassFromJson(appointments, "Appointments" , "com.smartvalue.moj.najiz.mapping.appointments.auto");
		Gson gson = new Gson() ; 
		//com.smartvalue.moj.najiz.services.appointments.auto.Appointments appouRequest = gson.fromJson(appointments , com.smartvalue.moj.najiz.services.appointments.auto.Appointments.class) ;
		//System.out.print(appouRequest);
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
