package com.smartvalue.swagger.v3.parser.util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import io.swagger.v3.oas.models.responses.ApiResponse;

public class JsonApiResponse extends ApiResponse implements Jsonable{

	private JsonContent jsonContent ; 
	public JsonApiResponse(ApiResponse apiResponse) {

		if (apiResponse.get$ref() != null ) this.set$ref(apiResponse.get$ref());
		if (apiResponse.getContent() != null ) this.setJsonContent(new JsonContent(apiResponse.getContent()));
		if (apiResponse.getDescription() != null ) this.setDescription(apiResponse.getDescription());
		if (apiResponse.getExtensions() != null ) this.setExtensions(apiResponse.getExtensions());
		if (apiResponse.getHeaders() != null ) this.setHeaders(apiResponse.getHeaders());
		if (apiResponse.getLinks() != null ) this.setLinks(apiResponse.getLinks());
		
	}

	 @Override
	    public String toJsonString() {
	        StringBuilder sb = new StringBuilder();
	        sb.append("{\n");

	        TreeMap<String , Object > elements = new TreeMap<String , Object >() ;
	        elements.put("description", getDescription()) ;
	        elements.put("content", getJsonContent()) ; 
	        elements.put("headers", getHeaders()) ;
	        elements.put("links", getLinks()) ;
	        elements.put("extensions", getExtensions()) ;
	        elements.put("$ref", get$ref()) ;
	        
	        sb = Jsonable.appendElements(sb, elements);
	        /*
	        if (getJsonContent()!= null ) 	{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"content\": ").append(toIndentedString(getJsonContent().toJsonString())).append("\n"); needComma = true ; }
	        if (getDescription() != null) 	{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"description\": \"").append(toIndentedString(getDescription())).append("\"\n"); needComma = true ; }
	        if (getHeaders()!= null ) 		{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"headers\": ").append(toIndentedString(getHeaders())).append("\n"); needComma = true ; }
	        if (getLinks()!= null ) 		{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"links\": ").append(toIndentedString(getLinks())).append("\n"); needComma = true ; }
	        if (getExtensions()!= null ) 	{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"extensions\": ").append(toIndentedString(getExtensions())).append("\n"); needComma = true ; }
	        if (get$ref()!= null ) 			{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"$ref\": ").append(toIndentedString(get$ref())).append("\n"); needComma = true ; }
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
