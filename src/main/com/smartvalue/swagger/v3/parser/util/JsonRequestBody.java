package com.smartvalue.swagger.v3.parser.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class JsonRequestBody extends io.swagger.v3.oas.models.parameters.RequestBody implements Jsonable {

	private JsonContent jsonContent ; 
	
	
	public JsonRequestBody(io.swagger.v3.oas.models.parameters.RequestBody m_requestBody)  {
		if (m_requestBody.get$ref() != null ) this.set$ref(m_requestBody.get$ref());
		if (m_requestBody.getContent() != null ) { this.setContent(m_requestBody.getContent());
												 this.setJsonContent(new JsonContent (m_requestBody.getContent())); } 
		if (m_requestBody.getDescription() != null ) this.setDescription(m_requestBody.getDescription());
		if (m_requestBody.getExtensions() != null ) this.setExtensions(m_requestBody.getExtensions());
		if (m_requestBody.getRequired() != null ) this.setRequired(m_requestBody.getRequired());
		
	}

	
	public String toJsonString() throws JsonMappingException, JsonProcessingException {
		boolean needComma = false ; 
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        
        FifoMap<String , Object > elements = new FifoMap<String , Object >() ;
        elements.put("$ref", get$ref() ) ; 
        elements.put("content", getJsonContent()) ;
        elements.put("required", getRequired()) ;
        elements.put("description", getDescription()) ;
        elements.put("extensions", getExtensions()) ;
        
        sb = Jsonable.appendElements(sb, elements);
        /*
        if (get$ref() != null) 		  { sb.append("    \"$ref\": \"").append(toIndentedString(get$ref())).append("\"\n"); needComma = true ; }
        if (getJsonContent() != null) { sb.append("    " + Jsonable.appendCommaEnter(needComma) +"\"content\": ").append(toIndentedString(getJsonContent().toJsonString())).append("\n"); needComma = true ; }
        if (getRequired() != null)    { sb.append("    " + Jsonable.appendCommaEnter(needComma) +"\"required\": ").append(toIndentedString(getRequired())).append("\n"); needComma = true ; }
        if (getDescription() != null) { sb.append("    " + Jsonable.appendCommaEnter(needComma) +"\"description\": \"").append(toIndentedString(getDescription())).append("\"\n"); needComma = true ; }
        if (getExtensions() != null)  { sb.append("    " + Jsonable.appendCommaEnter(needComma) +"\"extensions\": ").append(toIndentedString(getExtensions())).append("\n"); needComma = true ; }
		*/
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


	public JsonContent getJsonContent() {
		return jsonContent;
	}


	public void setJsonContent(JsonContent jsonContent) {
		this.jsonContent = jsonContent;
	}
}
