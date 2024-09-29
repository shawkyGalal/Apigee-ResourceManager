package com.smartvalue.swagger.v3.parser.util;

public class JsonRequestBody extends io.swagger.v3.oas.models.parameters.RequestBody implements Jsonable {

	private JsonContent jsonContent ; 
	
	public JsonRequestBody(io.swagger.v3.oas.models.parameters.RequestBody requestBody)  {
		if (requestBody.get$ref() != null ) this.set$ref(requestBody.get$ref());
		if (requestBody.getContent() != null ) this.setJsonContent(new JsonContent (requestBody.getContent())); 
		if (requestBody.getDescription() != null ) this.setDescription(requestBody.getDescription());
		if (requestBody.getExtensions() != null ) this.setExtensions(requestBody.getExtensions());
		if (requestBody.getRequired() != null ) this.setRequired(requestBody.getRequired());
		
	}

	
	public String toJsonString() {
		boolean needComma = false ; 
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        if (get$ref() != null) 		  { sb.append("    \"$ref\": \"").append(toIndentedString(get$ref())).append("\"\n"); needComma = true ; }
        if (getJsonContent() != null) { sb.append("    " + Jsonable.appendCommaEnter(needComma) +"\"content\": ").append(toIndentedString(getJsonContent().toJsonString())).append("\n"); needComma = true ; }
        if (getRequired() != null)    { sb.append("    " + Jsonable.appendCommaEnter(needComma) +"\"required\": ").append(toIndentedString(getRequired())).append("\n"); needComma = true ; }
        if (getDescription() != null) { sb.append("    " + Jsonable.appendCommaEnter(needComma) +"\"description\": \"").append(toIndentedString(getDescription())).append("\"\n"); needComma = true ; }
        if (getExtensions() != null)  { sb.append("    " + Jsonable.appendCommaEnter(needComma) +"\"extensions\": ").append(toIndentedString(getExtensions())).append("\n"); needComma = true ; }

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
