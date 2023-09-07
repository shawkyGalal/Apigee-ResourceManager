package com.smartvalue.apigee.rest.schema;

public class ApigeeAccessToken {

	private String access_token;
	private String token_type;
	private String refresh_token;
	private String[] api_product_list_json ; 
	private String client_id ; 
	private String application_name ; 
	private String scope ; 
	private String status ; 
	private int expires_in ; 
	private String authoirizationCode ;
	//---id-token attributes 
	private String userid ; 
	private String arabicFirstName ; 
	private String iqamaExpiryDateHijri ; 
	private String nationalityCode ;  
	private String refresh_count ; 
	private String idExpiryDateGregorian ; 
	private String iqamaExpiryDateGregorian ; 
	private String dobHijri ; 
	private String id_token ; 
	private String arabicGrandFatherName ; 
	private String token_generator_messageId ; 
	private String nationality ; 
	private String idExpiryDateHijri ; 
	private String dob ; 
	private String arabicFamilyName ; 
	private String englishFatherName ; 
	private String englishName ; 
	private String gender ; 
	private String cardIssueDateGregorian; 
	private String issued_at ;
	private String idVersionNo ; 
	private String arabicNationality ; 
	private String arabicName ; 
	private String refresh_token_issued_at ; 
	private String issueLocationAr ; 
	private String refresh_token_status ; 
	private String cardIssueDateHijri ; 
	private String englishFirstName ; 
	private String issueLocationEn ; 
	//private String developer.email ; 
	private String englishFamilyName ; 
	private String req_scope ; 
	
	public String getAccess_token() {
		return access_token;
	}
	
	public String getToken_type() {
		return token_type;
	}
	
	public String getRefresh_token() {
		return refresh_token;
	}
	
	
	
	public String[] getApi_product_list_json() {
		return api_product_list_json;
	}
	
	public String getClient_id() {
		return client_id;
	}
	
	public String getApplication_name() {
		return application_name;
	}
	
	public String getScope() {
		return scope;
	}
	
	public String getStatus() {
		return status;
	}
	
	public int getExpires_in() {
		return expires_in;
	}
	
	public String getAuthoirizationCode() {
		return authoirizationCode;
	}
	
	public String getUserid() {
		return userid;
	}
	
	public String getArabicFirstName() {
		return arabicFirstName;
	}
	
	public String getIqamaExpiryDateHijri() {
		return iqamaExpiryDateHijri;
	}
	
	public String getNationalityCode() {
		return nationalityCode;
	}
	
	public String getRefresh_count() {
		return refresh_count;
	}
	
	public String getIdExpiryDateGregorian() {
		return idExpiryDateGregorian;
	}
	
	public String getIqamaExpiryDateGregorian() {
		return iqamaExpiryDateGregorian;
	}
	
	public String getDobHijri() {
		return dobHijri;
	}
	
	public String getId_token() {
		return id_token;
	}
	
	public String getArabicGrandFatherName() {
		return arabicGrandFatherName;
	}
	
	public String getToken_generator_messageId() {
		return token_generator_messageId;
	}
	
	public String getNationality() {
		return nationality;
	}
	
	public String getIdExpiryDateHijri() {
		return idExpiryDateHijri;
	}
	
	public String getDob() {
		return dob;
	}
	
	public String getArabicFamilyName() {
		return arabicFamilyName;
	}
	
	public String getEnglishFatherName() {
		return englishFatherName;
	}
	
	public String getEnglishName() {
		return englishName;
	}
	
	public String getGender() {
		return gender;
	}
	
	public String getCardIssueDateGregorian() {
		return cardIssueDateGregorian;
	}
	
	public String getIssued_at() {
		return issued_at;
	}
	
	public String getIdVersionNo() {
		return idVersionNo;
	}
	
	public String getArabicNationality() {
		return arabicNationality;
	}
	
	public String getArabicName() {
		return arabicName;
	}
	
	public String getRefresh_token_issued_at() {
		return refresh_token_issued_at;
	}
	
	public String getIssueLocationAr() {
		return issueLocationAr;
	}
	
	public String getRefresh_token_status() {
		return refresh_token_status;
	}
	
	public String getCardIssueDateHijri() {
		return cardIssueDateHijri;
	}
	
	public String getEnglishFirstName() {
		return englishFirstName;
	}
	
	public String getIssueLocationEn() {
		return issueLocationEn;
	}
	
	public String getEnglishFamilyName() {
		return englishFamilyName;
	}
	
	public String getReq_scope() {
		return req_scope;
	}

	public void setAuthoirizationCode(String m_authCode) {
		this.authoirizationCode = m_authCode ; 
		
	}
	
	
}


