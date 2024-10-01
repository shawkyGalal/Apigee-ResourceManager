package com.smartvalue.swagger.v3.parser.util;

public class JsonParameter extends io.swagger.v3.oas.models.parameters.Parameter implements Jsonable {

	private JsonSchema jsonSchema ; 
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
		if (par.getSchema() != null ) 	{ 	this.setSchema(par.getSchema()); 
											this.setJsonSchema(new JsonSchema(getSchema()));	} 
		if (par.getStyle() != null ) this.setStyle(par.getStyle());
	}

	 public String toJsonString() {
	        StringBuilder sb = new StringBuilder();
	        sb.append("{\n");

	        if (getName() != null ) sb.append("    \"name\": \"").append(toIndentedString(getName())).append("\"\n");
	        if (getDescription() != null )  	sb.append("    \"description\": \"").append(toIndentedString(getDescription())).append("\"\n");
	        if (getJsonSchema() != null ) 		sb.append("    \"schema\": ").append(toIndentedString(getJsonSchema().toJsonString())).append("\n");
	        if (getIn() != null ) 				sb.append("    \"in\": \"").append(toIndentedString(getIn())).append("\"\n");
	        if (getRequired() != null ) 		sb.append("    \"required\": ").append(toIndentedString(getRequired())).append("\n");
	        if (getDeprecated() != null ) 		sb.append("    \"deprecated\": ").append(toIndentedString(getDeprecated())).append("\n");
	        if (getAllowEmptyValue() != null ) 	sb.append("    \"allowEmptyValue\": ").append(toIndentedString(getAllowEmptyValue())).append("\n");
	        //if (getStyle() != null ) 			sb.append("    \"style\": \"").append(toIndentedString(getStyle())).append("\"\n");
	        //if (getExplode() != null ) 			sb.append("    \"explode\": ").append(toIndentedString(getExplode())).append("\n");
	        if (getAllowReserved() != null ) 	sb.append("    \"allowReserved\": ").append(toIndentedString(getAllowReserved())).append("\n");
	        if (getExamples() != null ) 		sb.append("    \"examples\": ").append(toIndentedString(getExamples())).append("\n");
	        if (getExample() != null ) 			sb.append("    \"example\": ").append(toIndentedString(getExample())).append("\n");
	        if (getContent() != null ) 			sb.append("    \"content\": ").append(toIndentedString(getContent())).append("\n");
	        if (get$ref() != null ) 			sb.append("    $ref: ").append(toIndentedString(get$ref())).append("\n");
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

	public JsonSchema getJsonSchema() {
		return jsonSchema;
	}

	public void setJsonSchema(JsonSchema jsonSchema) {
		this.jsonSchema = jsonSchema;
	}

}
