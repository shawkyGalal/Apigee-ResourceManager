package com.smartvalue.swagger.v3.parser.util;

public class JsonParameter extends io.swagger.v3.oas.models.parameters.Parameter implements Jsonable {

	public JsonParameter(io.swagger.v3.oas.models.parameters.Parameter par) {
		if (par.get$ref() != null ) this.set$ref(par.get$ref());
		if (par.getAllowEmptyValue() != null ) this.setAllowEmptyValue(par.getAllowEmptyValue());
		if (par.getAllowReserved() != null ) this.setAllowReserved(par.getAllowReserved());
		if (par.getContent() != null ) this.setContent(par.getContent());
		if (par.getDeprecated() != null ) this.setDeprecated(par.getDeprecated());
		if (par.getDescription() != null ) this.setDescription(par.getDescription());
		if (par.getExample() != null ) this.setExample(par.getExample());
		if (par.getExamples() != null ) this.setExamples(par.getExamples());
		if (par.getExplode() != null ) this.setExplode(par.getExplode());
		if (par.getExtensions() != null ) this.setExtensions(par.getExtensions());
		if (par.getIn() != null ) this.setIn(par.getIn());
		if (par.getName() != null ) this.setName(par.getName());
		if (par.getRequired() != null ) this.setRequired(par.getRequired());
		if (par.getSchema() != null ) this.setSchema(par.getSchema());
		if (par.getStyle() != null ) this.setStyle(par.getStyle());
	}

	 public String toJsonString() {
	        StringBuilder sb = new StringBuilder();
	        sb.append("{\n");

	        sb.append("    \"name: ").append(toIndentedString(getName())).append("\n");
	        sb.append("    \"in\": ").append(toIndentedString(getIn())).append("\n");
	        sb.append("    \"description\": ").append(toIndentedString(getDescription())).append("\n");
	        sb.append("    \"required\": ").append(toIndentedString(getRequired())).append("\n");
	        sb.append("    \"deprecated\": ").append(toIndentedString(getDeprecated())).append("\n");
	        sb.append("    \"allowEmptyValue\": ").append(toIndentedString(getAllowEmptyValue())).append("\n");
	        sb.append("    \"style\": ").append(toIndentedString(getStyle())).append("\n");
	        sb.append("    \"explode\": ").append(toIndentedString(getExplode())).append("\n");
	        sb.append("    \"allowReserved\": ").append(toIndentedString(getAllowReserved())).append("\n");
	        sb.append("    \"schema\": ").append(toIndentedString(getSchema())).append("\n");
	        sb.append("    \"examples\": ").append(toIndentedString(getExamples())).append("\n");
	        sb.append("    \"example\": ").append(toIndentedString(getExample())).append("\n");
	        sb.append("    \"content\": ").append(toIndentedString(getContent())).append("\n");
	        sb.append("    $ref: ").append(toIndentedString(get$ref())).append("\n");
	        sb.append("}");
	        return sb.toString();
	    }
	 /**
	     * Convert the given object to string with each line indented by 4 spaces
	     * (except the first line).
	     */
	    static String toIndentedString(java.lang.Object o) {
	        if (o == null) {
	            return "null";
	        }
	        return o.toString().replace("\n", "\n    ");
	    }

}
