package com.smartvalue.swagger.v3.parser.util;

import io.swagger.v3.oas.models.tags.Tag;

public class JsonTag extends io.swagger.v3.oas.models.tags.Tag implements Jsonable {

	public JsonTag(Tag tag) {
		this.setDescription(tag.getDescription());
		this.setExtensions(tag.getExtensions());
		this.setExternalDocs(tag.getExternalDocs());
		this.setName(tag.getName());
	}

	@Override
    public String toJsonString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        sb.append("    \"name\": \"").append(toIndentedString(getName())).append("\"\n");
        sb.append("    \"description\": \"").append(toIndentedString(getDescription())).append("\"\n");
        sb.append("    \"externalDocs\": ").append(toIndentedString(getExternalDocs())).append("\n");
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
