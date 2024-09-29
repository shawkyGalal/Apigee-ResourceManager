package com.smartvalue.swagger.v3.parser.util;


public class JsonInfo extends io.swagger.v3.oas.models.info.Info implements Jsonable {

	private JsonContact jsonContact = null;
    private JsonLicense jsonLicense = null;
    
	public JsonInfo(io.swagger.v3.oas.models.info.Info m_info) {
		if (m_info.getContact() != null) this.setJsonContact( new JsonContact ( m_info.getContact()));
		if (m_info.getDescription() != null) this.setDescription(m_info.getDescription());
		if (m_info.getExtensions() != null) this.setExtensions(m_info.getExtensions());
		if (m_info.getLicense() != null) this.setJsonLicense(new JsonLicense( m_info.getLicense()));
		if (m_info.getSummary() != null) this.setSummary(m_info.getSummary());
		if (m_info.getTermsOfService() != null) this.setTermsOfService(m_info.getTermsOfService());
		if (m_info.getTitle() != null) this.setTitle(m_info.getTitle());
		if (m_info.getVersion() != null) this.setVersion(m_info.getVersion());
	}
	
	
	public String toJsonString() {
		boolean needComma = false ; 
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        									//{sb.append( Jsonable.appendCommaEnter(needComma)).append("    ").append("\"tags\": ").append(toIndentedString(tags)).append("\n"); needComma = true ; }
        if (getTitle() != null) 			{ sb.append("    \"title\": \"").append(toIndentedString(getTitle())).append("\""); needComma = true ; }
        if (getDescription() != null) 		{ sb.append( Jsonable.appendCommaEnter(needComma)).append("\"description\": \"").append(toIndentedString(getDescription())); needComma = true ; }
        if (getSummary() != null) 			{ sb.append( Jsonable.appendCommaEnter(needComma)).append("\"summary\": \"").append(toIndentedString(getSummary())).append("\""); needComma = true ;}
        if (getTermsOfService() !=null ) 	{ sb.append( Jsonable.appendCommaEnter(needComma)).append("\"termsOfService\": \"").append(toIndentedString(getTermsOfService())).append("\""); needComma = true ;} 
        if (getContact() !=null ) 			{ sb.append( Jsonable.appendCommaEnter(needComma)).append("\"contact\": ").append(toIndentedString(getJsonContact().toJsonString())); needComma = true ; }
        if (getLicense() !=null ) 			{ sb.append( Jsonable.appendCommaEnter(needComma)).append("\"license\": ").append(toIndentedString(getJsonLicense().toJsonString())); needComma = true ; } 
        if (getVersion() !=null ) 			{ sb.append( Jsonable.appendCommaEnter(needComma)).append("\"version\": \"").append(toIndentedString(getVersion())).append("\""); needComma = true ; }
        sb.append("\n}");
        return sb.toString();
    }
	/**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }


	public JsonContact getJsonContact() {
		return jsonContact;
	}


	public void setJsonContact(JsonContact jsonContact) {
		this.jsonContact = jsonContact;
	}


	public JsonLicense getJsonLicense() {
		return jsonLicense;
	}


	public void setJsonLicense(JsonLicense jsonLicense) {
		this.jsonLicense = jsonLicense;
	}



	
}
