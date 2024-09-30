package com.smartvalue.swagger.v3.parser.util;

import io.swagger.v3.oas.models.examples.Example;

public class JsonExample extends Example implements Jsonable {

	public JsonExample(Example m_example) {
		this.set$ref(m_example.get$ref());
		this.setDescription(m_example.getDescription());
		this.setExtensions(m_example.getExtensions());
		this.setExternalValue(m_example.getExternalValue());
		this.setSummary(m_example.getSummary());
		this.setValue(m_example.getValue());
		this.setValueSetFlag(m_example.getValueSetFlag());
	}

	

	public String toJsonString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        sb.append("    summary: ").append(toIndentedString(getSummary())).append("\n");
        sb.append("    description: ").append(toIndentedString(getDescription())).append("\n");
        sb.append("    value: ").append(toIndentedString(getValue())).append("\n");
        sb.append("    externalValue: ").append(toIndentedString(getExternalValue())).append("\n");
        sb.append("    $ref: ").append(toIndentedString(get$ref())).append("\n");
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
