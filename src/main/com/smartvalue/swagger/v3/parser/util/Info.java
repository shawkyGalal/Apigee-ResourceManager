package com.smartvalue.swagger.v3.parser.util;

public class Info extends io.swagger.v3.oas.models.info.Info {

	public Info(io.swagger.v3.oas.models.info.Info m_info) {
		this.setContact(m_info.getContact());
		this.setDescription(m_info.getDescription());
		this.setExtensions(m_info.getExtensions());
		this.setLicense(m_info.getLicense());
		this.setSummary(m_info.getSummary());
		this.setTermsOfService(m_info.getTermsOfService());
		this.setTitle(m_info.getTitle());
		this.setVersion(m_info.getVersion());
	}
	
	
	public String toJsonString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        sb.append("    \"title\": ").append(toIndentedString(getTitle())).append("\n");
        if (getDescription() != null) sb.append("    \"description\": ").append(toIndentedString(getDescription())).append("\n");
        if (getSummary() != null) sb.append("    \"summary\": ").append(toIndentedString(getSummary())).append("\n");
        if (getTermsOfService() !=null ) sb.append("    \"termsOfService\": ").append(toIndentedString(getTermsOfService())).append("\n");
        if (getContact() !=null ) sb.append("    \"contact\": ").append(toIndentedString(getContact())).append("\n");
        if (getLicense() !=null ) sb.append("    \"license\": ").append(toIndentedString(getLicense())).append("\n");
        if (getVersion() !=null ) sb.append("    \"version\": ").append(toIndentedString(getVersion())).append("\n");
        sb.append("}");
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
}
