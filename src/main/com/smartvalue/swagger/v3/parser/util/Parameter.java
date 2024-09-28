package com.smartvalue.swagger.v3.parser.util;

public class Parameter extends io.swagger.v3.oas.models.parameters.Parameter implements Jsonable {

	public Parameter(io.swagger.v3.oas.models.parameters.Parameter par) {
		this.set$ref(par.get$ref());
		this.setAllowEmptyValue(par.getAllowEmptyValue());
		this.setAllowReserved(par.getAllowReserved());
		this.setContent(par.getContent());
		this.setDeprecated(par.getDeprecated());
		this.setDescription(par.getDescription());
		this.setExample(par.getExample());
		this.setExamples(par.getExamples());
		this.setExplode(par.getExplode());
		this.setExtensions(par.getExtensions());
		this.setIn(par.getIn());
		this.setName(par.getName());
		this.setRequired(par.getRequired());
		this.setSchema(par.getSchema());
		this.setStyle(par.getStyle());
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
