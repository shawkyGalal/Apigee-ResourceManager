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
   
		String accessToken = "{\r\n" + 
				"  \"refresh_token_expires_in\" : \"1295999\",\r\n" + 
				"  \"arabicFatherName\" : \"محمد\",\r\n" + 
				"  \"sessionIndex\" : \"e84efe0e-136b-4ee0-90d1-2d96d454dda1\",\r\n" + 
				"  \"englishGrandFatherName\" : \"GALAL\",\r\n" + 
				"  \"userid\" : \"2354900017\",\r\n" + 
				"  \"client_id\" : \"X0sPwcBpGxJPUrpBIr1IlcHQ0bTlrCd5\",\r\n" + 
				"  \"saml_issueInstant\" : \"2023-09-07T12:31:02.003Z\",\r\n" + 
				"  \"arabicFirstName\" : \"شوقى\",\r\n" + 
				"  \"iqamaExpiryDateHijri\" : \"1446/02/29\",\r\n" + 
				"  \"nationalityCode\" : \"207\",\r\n" + 
				"  \"application_name\" : \"efe547b7-ca33-471d-b000-55e7760ff1c8\",\r\n" + 
				"  \"refresh_count\" : \"0\",\r\n" + 
				"  \"idExpiryDateGregorian\" : \"Mon Sep 02 03:00:00 AST 2024\",\r\n" + 
				"  \"iqamaExpiryDateGregorian\" : \"Mon Sep 02 03:00:00 AST 2024\",\r\n" + 
				"  \"api_product_list\" : \"[SMSMOI, ldapProduct, NationalAddress, Deeds Inquiry, active-directory, oidc-core, EmailService, MCIProduct, ELM-Product, LDAP, ShortenURLProduct, Tahseel Biller 169, QaimResponseAPIs, Tahseel_JudicialCosts, INTEG_GSN_TAHSEEL_TO_MOJ_JudicialCosts, Tahseel_Servicese_Outgoing160, test01, Najiz-Mobile-Product, Proxy-Taqadhi_ICMS_IAM_Product, NajizACL]\",\r\n" + 
				"  \"dobHijri\" : \"1388/06/03\",\r\n" + 
				"  \"id_token\" : \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMzU0OTAwMDE3IiwiYmlydGhkYXRlIjoiMTM4OFwvMDZcLzAzIiwiZ2VuZGVyIjoiTWFsZSIsImxvZ2luVHlwZSI6IkluZGl2aWR1YWwiLCJhbXIiOiJleHRlcm5hbCIsImlzcyI6Imh0dHBzOlwvXC9hcGlzLm1vai5nb3Yuc2EiLCJzZXJ2aWNlU3BlY2lmaWNJbmZvIjoiYXBpZy1wLXI3Lm1vai5nb3YtMTQyNTUtMTc1MjUxMjYtMSIsInByZWZlcnJlZF91c2VybmFtZSI6Iti02YjZgtmJIiwibmF0aW9uYWxpdHlfbmFtZSI6ItmF2LXYsSIsImNsaWVudF9pZCI6Ilgwc1B3Y0JwR3hKUFVycEJJcjFJbGNIUTBiVGxyQ2Q1Iiwic2NvcGUiOiJvcGVuaWQiLCJhdXRoX3RpbWUiOiIxNjk0MDg5ODYyIiwic2Vzc2lvbl9pZGVudGlmaWVyIjoiZTg0ZWZlMGUtMTM2Yi00ZWUwLTkwZDEtMmQ5NmQ0NTRkZGExIiwiaWRwdHlwZSI6ImFwaWdlZSIsImV4cCI6MTY5NDA5MDQ2MiwiaWF0IjoxNjk0MDg5ODYyLCJqdGkiOiI1YzVhMDY4OC0yZTZjLTQ0YmUtYWNlNi0xMTI2ODc1M2RjNmQiLCJuYXRpb25hbGl0eUlkIjoiMjA3IiwidGhpcmRfbmFtZSI6Itis2YTYp9mEIiwiZ2l2ZW5fbmFtZSI6Iti02YjZgtmJINmF2K3ZhdivINis2YTYp9mEINin2KjYsdin2YfZitmFIiwibWlkZGxlX25hbWUiOiLZhdit2YXYryIsIm5vbmNlIjoidlJLUDQtMWlJLWtWYk1SNkxfNEdGUSIsImF1ZCI6Ilgwc1B3Y0JwR3hKUFVycEJJcjFJbGNIUTBiVGxyQ2Q1IiwiaWRwIjoiSUFNIiwibmF0aW9uYWxpdHkiOiJFZ3lwdCIsInVzZXJfaWQiOiJjNzIzOTdlNjM0YmRjZmM0MDQwZThmYTVjYmE5NTBjNSIsImRvYiI6Ik1vbiBBdWcgMjYgMDM6MDA6MDAgQVNUIDE5NjgiLCJpZEV4cGlyeURhdGVIaWpyaSI6IjE0NDZcLzAyXC8yOSIsIm5hbWUiOiIyMzU0OTAwMDE3IiwiZmFtaWx5X25hbWUiOiLYp9io2LHYp9mH2YrZhSIsImFnZSI6IjU1IiwidXNlcl9pZF9oYXNoZWQiOiJjNzIzOTdlNjM0YmRjZmM0MDQwZThmYTVjYmE5NTBjNSJ9.o9__gVI8lO0qbh0-ScVgamrjzP0v3iMWot0BX7ucALM\",\r\n" + 
				"  \"organization_name\" : \"moj-prod\",\r\n" + 
				"  \"nonce\" : \"vRKP4-1iI-kVbMR6L_4GFQ\",\r\n" + 
				"  \"arabicGrandFatherName\" : \"جلال\",\r\n" + 
				"  \"access_token\" : \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMzU0OTAwMDE3IiwiYmlydGhkYXRlIjoiMTM4OFwvMDZcLzAzIiwiZ2VuZGVyIjoiTWFsZSIsImxvZ2luVHlwZSI6IkluZGl2aWR1YWwiLCJhbXIiOiJleHRlcm5hbCIsImlzcyI6Imh0dHBzOlwvXC9hcGlzLm1vai5nb3Yuc2EiLCJzZXJ2aWNlU3BlY2lmaWNJbmZvIjoiYXBpZy1wLXI3Lm1vai5nb3YtMTQyNTUtMTc1MjUxMjYtMSIsInByZWZlcnJlZF91c2VybmFtZSI6Iti02YjZgtmJIiwibmF0aW9uYWxpdHlfbmFtZSI6ItmF2LXYsSIsImNsaWVudF9pZCI6Ilgwc1B3Y0JwR3hKUFVycEJJcjFJbGNIUTBiVGxyQ2Q1Iiwic2NvcGUiOiJvcGVuaWQiLCJhdXRoX3RpbWUiOiIxNjk0MDg5ODYyIiwic2Vzc2lvbl9pZGVudGlmaWVyIjoiZTg0ZWZlMGUtMTM2Yi00ZWUwLTkwZDEtMmQ5NmQ0NTRkZGExIiwiaWRwdHlwZSI6ImFwaWdlZSIsImV4cCI6MTY5NDA5MDQ2MiwiaWF0IjoxNjk0MDg5ODYyLCJqdGkiOiI1YzVhMDY4OC0yZTZjLTQ0YmUtYWNlNi0xMTI2ODc1M2RjNmQiLCJuYXRpb25hbGl0eUlkIjoiMjA3IiwidGhpcmRfbmFtZSI6Itis2YTYp9mEIiwiZ2l2ZW5fbmFtZSI6Iti02YjZgtmJINmF2K3ZhdivINis2YTYp9mEINin2KjYsdin2YfZitmFIiwibWlkZGxlX25hbWUiOiLZhdit2YXYryIsIm5vbmNlIjoidlJLUDQtMWlJLWtWYk1SNkxfNEdGUSIsImF1ZCI6Ilgwc1B3Y0JwR3hKUFVycEJJcjFJbGNIUTBiVGxyQ2Q1IiwiaWRwIjoiSUFNIiwibmF0aW9uYWxpdHkiOiJFZ3lwdCIsInVzZXJfaWQiOiJjNzIzOTdlNjM0YmRjZmM0MDQwZThmYTVjYmE5NTBjNSIsImRvYiI6Ik1vbiBBdWcgMjYgMDM6MDA6MDAgQVNUIDE5NjgiLCJpZEV4cGlyeURhdGVIaWpyaSI6IjE0NDZcLzAyXC8yOSIsIm5hbWUiOiIyMzU0OTAwMDE3IiwiZmFtaWx5X25hbWUiOiLYp9io2LHYp9mH2YrZhSIsImFnZSI6IjU1IiwidXNlcl9pZF9oYXNoZWQiOiJjNzIzOTdlNjM0YmRjZmM0MDQwZThmYTVjYmE5NTBjNSJ9.o9__gVI8lO0qbh0-ScVgamrjzP0v3iMWot0BX7ucALM\",\r\n" + 
				"  \"refresh_token\" : \"afzFi9qXyXiPnxXMFFts1VtIhSUfVbOz\",\r\n" + 
				"  \"token_generator_messageId\" : \"apig-p-r7.moj.gov-14255-17525249-1\",\r\n" + 
				"  \"nationality\" : \"Egypt\",\r\n" + 
				"  \"idExpiryDateHijri\" : \"1446/02/29\",\r\n" + 
				"  \"dob\" : \"Mon Aug 26 03:00:00 AST 1968\",\r\n" + 
				"  \"preferredLang\" : \"\",\r\n" + 
				"  \"arabicFamilyName\" : \"ابراهيم\",\r\n" + 
				"  \"status\" : \"approved\",\r\n" + 
				"  \"englishFatherName\" : \"MOHAMED\",\r\n" + 
				"  \"englishName\" : \"SHAWKI MOHAMED GALAL IBRAHIM\",\r\n" + 
				"  \"gender\" : \"Male\",\r\n" + 
				"  \"cardIssueDateGregorian\" : \"Wed Jul 08 03:00:00 AST 2015\",\r\n" + 
				"  \"token_type\" : \"BearerToken\",\r\n" + 
				"  \"issued_at\" : \"1694089873628\",\r\n" + 
				"  \"idVersionNo\" : \"7\",\r\n" + 
				"  \"arabicNationality\" : \"مصر\",\r\n" + 
				"  \"arabicName\" : \"شوقى محمد جلال ابراهيم\",\r\n" + 
				"  \"scope\" : \"openid\",\r\n" + 
				"  \"refresh_token_issued_at\" : \"1694089873628\",\r\n" + 
				"  \"lang\" : \"ar\",\r\n" + 
				"  \"expires_in\" : \"1799\",\r\n" + 
				"  \"issueLocationAr\" : \"جوازات الرياض\",\r\n" + 
				"  \"refresh_token_status\" : \"approved\",\r\n" + 
				"  \"api_product_list_json\" : [ \"SMSMOI\", \"ldapProduct\", \"NationalAddress\", \"Deeds Inquiry\", \"active-directory\", \"oidc-core\", \"EmailService\", \"MCIProduct\", \"ELM-Product\", \"LDAP\", \"ShortenURLProduct\", \"Tahseel Biller 169\", \"QaimResponseAPIs\", \"Tahseel_JudicialCosts\", \"INTEG_GSN_TAHSEEL_TO_MOJ_JudicialCosts\", \"Tahseel_Servicese_Outgoing160\", \"test01\", \"Najiz-Mobile-Product\", \"Proxy-Taqadhi_ICMS_IAM_Product\", \"NajizACL\" ],\r\n" + 
				"  \"cardIssueDateHijri\" : \"1436/09/21\",\r\n" + 
				"  \"englishFirstName\" : \"SHAWKI\",\r\n" + 
				"  \"issueLocationEn\" : \"Riyadh Passports\",\r\n" + 
				"  \"developer.email\" : \"sfoda@moj.gov.sa\",\r\n" + 
				"  \"englishFamilyName\" : \"IBRAHIM\",\r\n" + 
				"  \"req_scope\" : \"openid\"\r\n" + 
				"}\r\n" + 
				"" ; 
		//generateJavaClassFromJson(accessToken, "ApigeeAccessToken" , "com.smartvalue.apigee.rest.schema.accessToken.auto");
		Gson gson = new Gson() ; 
		ApigeeAccessToken at = gson.fromJson(accessToken , com.smartvalue.apigee.rest.schema.ApigeeAccessToken.class) ; 
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
