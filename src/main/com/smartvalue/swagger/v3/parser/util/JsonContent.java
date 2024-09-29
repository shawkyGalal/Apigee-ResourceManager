package com.smartvalue.swagger.v3.parser.util;

import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;

public class JsonContent extends JsonLinkedHashMap<String, JsonMediaType>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JsonContent(Content content) {
		for (java.util.Map.Entry<String, MediaType> entry :   content.entrySet())
		{
			MediaType mediaType = entry.getValue() ; 
			JsonMediaType jsonMediaType = new JsonMediaType(mediaType) ; 
			this.put(entry.getKey(), jsonMediaType ) ; 
		}
	}

	
}
