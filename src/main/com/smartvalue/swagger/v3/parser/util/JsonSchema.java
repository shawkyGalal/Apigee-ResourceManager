package com.smartvalue.swagger.v3.parser.util;

import java.util.Map;

import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Schema;

public class JsonSchema extends Schema  implements Jsonable {

	private JsonProperties  jsonProperties ;
	private JsonExamples jsonExamples ;
	
	public JsonSchema(Schema schema) {

		this.set$anchor(schema.get$anchor());
		this.set$comment(schema.get$comment());
		this.set$id(schema.get$id());
		this.set$ref(schema.get$ref());
		this.set$schema(schema.get$schema());
		
		this.setAdditionalItems(schema.getAdditionalItems());
		this.setAdditionalProperties(schema.getAdditionalProperties());
		this.setAllOf(schema.getAllOf());
		this.setAnyOf(schema.getAnyOf());
		this.setBooleanSchemaValue(schema.getBooleanSchemaValue());
		
		this.setConst(schema.getConst());
		this.setContains(schema.getContains());
		this.setContentEncoding(schema.getContentEncoding());
		this.setContentMediaType(schema.getContentMediaType());
		this.setContentSchema(schema.getContentSchema());
		
		this.setDefault(schema.getDefault());
		this.setDependentRequired(schema.getDependentRequired());
		this.setDependentSchemas(schema.getDependentSchemas());
		this.setDeprecated(schema.getDeprecated());
		this.setDescription(schema.getDescription());
		this.setDiscriminator(schema.getDiscriminator());
		
		this.setExternalDocs(schema.getExternalDocs());
		this.setElse(schema.getElse());
		this.setEnum(schema.getEnum());
		this.setExample(schema.getExample());
		this.setExamples(schema.getExamples());
		this.setExampleSetFlag(schema.getExampleSetFlag());
		this.setExclusiveMaximum(schema.getExclusiveMaximum());
		this.setExclusiveMaximumValue(schema.getExclusiveMaximumValue());
		this.setExclusiveMinimum(schema.getExclusiveMinimum());
		this.setExclusiveMinimumValue(schema.getExclusiveMinimumValue());
		this.setExtensions(schema.getExtensions());
		this.setExternalDocs(schema.getExternalDocs());
		
		this.setFormat(schema.getFormat());
		this.setIf(schema.getIf());
		this.setItems(schema.getItems());
		this.setJsonSchema(schema.getJsonSchema()) ; 
		this.setJsonSchemaImpl(schema.getJsonSchemaImpl());
		
		this.setMaxContains(schema.getMaxContains());
		this.setMaximum(schema.getMaximum());
		this.setMaxItems(schema.getMaxItems());
		this.setMaxLength(schema.getMaxLength());
		this.setMaxProperties(schema.getMaxProperties());

		this.setMinContains(schema.getMinContains());
		this.setMinimum(schema.getMinimum());
		this.setMinItems(schema.getMinItems());
		this.setMinLength(schema.getMinLength());
		this.setMinProperties(schema.getMinProperties());

		this.setMultipleOf(schema.getMultipleOf());
		this.setName(schema.getName());
		this.setNot(schema.getNot());
		this.setNullable(schema.getNullable());
		
		this.setOneOf(schema.getOneOf()) ; 
		
		this.setPattern(schema.getPattern());
		this.setPatternProperties(schema.getPatternProperties());
		this.setPrefixItems(schema.getPrefixItems());
		
		if (schema.getProperties() != null ) this.setJsonProperties(new JsonProperties (schema.getProperties()) );
		
		this.setPropertyNames(schema.getPropertyNames());
		this.setReadOnly(schema.getReadOnly());
		this.setRequired(schema.getRequired());
		
		this.setSpecVersion(schema.getSpecVersion());
		
		this.setThen(schema.getThen());
		this.setType(schema.getType());
		this.setTitle(schema.getTitle());
		this.setTypes(schema.getTypes());
		
		this.setUnevaluatedItems(schema.getUnevaluatedItems());
		this.setUnevaluatedProperties(schema.getUnevaluatedProperties());
		this.setUniqueItems(schema.getUniqueItems());
		
		this.setWriteOnly(schema.getWriteOnly());
		
		this.setXml(schema.getXml());
		
	}

	 @Override
	    public String toJsonString() {
	        StringBuilder sb = new StringBuilder();
	        sb.append("{\n");
	        Object typeStr = getSpecVersion() == SpecVersion.V30 ? getType() : getTypes();
	        if(typeStr != null ) sb.append("    \"type\": \"").append(toIndentedString(typeStr)).append("\"\n");
	        if(getFormat() != null ) sb.append("    \"format\": \"").append(toIndentedString(getFormat())).append("\"\n");
	        if(get$ref() != null ) sb.append("    \"$ref\": \"").append(toIndentedString(get$ref())).append("\"\n");
	        if(getDescription() != null ) sb.append("    \"description\": \"").append(toIndentedString(getDescription())).append("\"\n");
	        if(getTitle() != null ) sb.append("    \"title\": \"").append(toIndentedString(getTitle())).append("\"\n");
	        if(getMultipleOf() != null ) sb.append("    \"multipleOf\": ").append(toIndentedString(getMultipleOf())).append("\n");
	        if(getMaximum() != null ) sb.append("    \"maximum\": ").append(toIndentedString(getMaximum())).append("\n");
	        Object exclusiveMaximumStr = getSpecVersion() == SpecVersion.V30 ? getExclusiveMaximum() : getExclusiveMaximumValue();
	        if(exclusiveMaximumStr != null ) sb.append("    \"exclusiveMaximum\": \"").append(toIndentedString(exclusiveMaximumStr)).append("\"\n");
	        if(getMinimum() != null ) sb.append("    \"minimum\": ").append(toIndentedString(getMinimum())).append("\n");
	        Object exclusiveMinimumStr = getSpecVersion() == SpecVersion.V30 ? getExclusiveMinimum() : getExclusiveMinimumValue();
	        if(exclusiveMinimumStr != null ) sb.append("    \"exclusiveMinimum\": \"").append(toIndentedString(exclusiveMinimumStr)).append("\"\n");
	        if(getMaxLength() != null ) sb.append("    \"maxLength\": ").append(toIndentedString(getMaxLength())).append("\n");
	        if(getMinLength() != null ) sb.append("    \"minLength\": ").append(toIndentedString(getMinLength())).append("\n");
	        if(getPattern() != null ) sb.append("    \"pattern\": \"").append(toIndentedString(getPattern())).append("\"\n");
	        if(getMaxItems() != null ) sb.append("    \"maxItems\": ").append(toIndentedString(getMaxItems())).append("\n");
	        if(getMinItems() != null ) sb.append("    \"minItems\": ").append(toIndentedString(getMinItems())).append("\n");
	        if(getUniqueItems() != null ) sb.append("    \"uniqueItems\": ").append(toIndentedString(getUniqueItems())).append("\n");
	        if(getMaxProperties() != null ) sb.append("    \"maxProperties\": ").append(toIndentedString(getMaxProperties())).append("\n");
	        if(getMinProperties() != null ) sb.append("    \"minProperties\": ").append(toIndentedString(getMinProperties())).append("\n");
	        if(getRequired() != null ) sb.append("    \"required\": ").append(toIndentedString(getRequired())).append("\n");
	        if(getNot() != null ) sb.append("    \"not\": ").append(toIndentedString(getNot())).append("\n");
	        if(getJsonProperties() != null ) sb.append("    \"properties\": ").append(toIndentedString(getJsonProperties().toJsonString())).append("\n");
	        if(getAdditionalProperties() != null ) sb.append("    \"additionalProperties\": ").append(toIndentedString(getAdditionalProperties())).append("\n");
	        if(getNullable() != null ) sb.append("    \"nullable\": ").append(toIndentedString(getNullable())).append("\n");
	        if(getReadOnly() != null ) sb.append("    \"readOnly\": ").append(toIndentedString(getReadOnly())).append("\n");
	        if(getWriteOnly() != null ) sb.append("    \"writeOnly\": ").append(toIndentedString(getWriteOnly())).append("\n");
	        if(example != null ) sb.append("    \"example\": ").append(toIndentedString(example)).append("\n");
	        if(getExternalDocs() != null ) sb.append("    \"externalDocs\": ").append(toIndentedString(getExternalDocs())).append("\n");
	        if(getDeprecated() != null ) sb.append("    \"deprecated\": ").append(toIndentedString(getDeprecated())).append("\n");
	        if(getDiscriminator() != null ) sb.append("    \"discriminator\": ").append(toIndentedString(getDiscriminator())).append("\n");
	        if(getXml() != null ) sb.append("    \"xml\": ").append(toIndentedString(getXml())).append("\n");
	        if (getSpecVersion() == SpecVersion.V31) {
	            sb.append("    patternProperties: ").append(toIndentedString(getPatternProperties())).append("\n");
	            sb.append("    contains: ").append(toIndentedString(getContains())).append("\n");
	            sb.append("    $id: ").append(toIndentedString(get$id())).append("\n");
	            sb.append("    $anchor: ").append(toIndentedString(get$anchor())).append("\n");
	            sb.append("    $schema: ").append(toIndentedString(get$schema())).append("\n");
	            sb.append("    const: ").append(toIndentedString(_const)).append("\n");
	            sb.append("    contentEncoding: ").append(toIndentedString(getContentEncoding())).append("\n");
	            sb.append("    contentMediaType: ").append(toIndentedString(getContentMediaType())).append("\n");
	            sb.append("    contentSchema: ").append(toIndentedString(getContentSchema())).append("\n");
	            sb.append("    propertyNames: ").append(toIndentedString(getPropertyNames())).append("\n");
	            sb.append("    unevaluatedProperties: ").append(toIndentedString(getUnevaluatedProperties())).append("\n");
	            sb.append("    maxContains: ").append(toIndentedString(getMaxContains())).append("\n");
	            sb.append("    minContains: ").append(toIndentedString(getMinContains())).append("\n");
	            sb.append("    additionalItems: ").append(toIndentedString(getAdditionalItems())).append("\n");
	            sb.append("    unevaluatedItems: ").append(toIndentedString(getUnevaluatedItems())).append("\n");
	            sb.append("    _if: ").append(toIndentedString(getIf())).append("\n");
	            sb.append("    _else: ").append(toIndentedString(getElse())).append("\n");
	            sb.append("    then: ").append(toIndentedString(getThen())).append("\n");
	            sb.append("    dependentRequired: ").append(toIndentedString(getDependentRequired())).append("\n");
	            sb.append("    dependentSchemas: ").append(toIndentedString(getDependentSchemas())).append("\n");
	            sb.append("    $comment: ").append(toIndentedString(get$comment())).append("\n");
	            sb.append("    prefixItems: ").append(toIndentedString(getPrefixItems())).append("\n");
	        }
	        sb.append("}");
	        return sb.toString();
	    }

	    /**
	     * Convert the given object to string with each line indented by 4 spaces
	     * (except the first line).
	     */
	    protected String toIndentedString(java.lang.Object o) {
	        if (o == null) {
	            return "null";
	        }
	        return o.toString().replace("\n", "\n    ");
	    }

		public JsonProperties getJsonProperties() {
			return jsonProperties;
		}

		public void setJsonProperties(JsonProperties jsonProperties) {
			this.jsonProperties = jsonProperties;
		}

		public JsonExamples getJsonExamples() {
			return jsonExamples;
		}

		public void setJsonExamples(JsonExamples jsonExamples) {
			this.jsonExamples = jsonExamples;
		}


}
