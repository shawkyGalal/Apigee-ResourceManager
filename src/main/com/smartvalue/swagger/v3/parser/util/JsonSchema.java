package com.smartvalue.swagger.v3.parser.util;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Schema;

public class JsonSchema extends Schema  implements Jsonable {

	private JsonProperties  jsonProperties ;
	private JsonExamples jsonExamples ;
	private JsonAdditionalProperties jsonAdditionalProperties ;  
	private JsonArrayList<JsonSchema> jsonAllOf ;
	private JsonSchema  jsonItems ;
	private JsonXml jsonXml ; 
	public JsonSchema getJsonItems() {
		return jsonItems;
	}

	public void setJsonItems(JsonSchema isonItems) {
		this.jsonItems = isonItems;
	}

	public JsonSchema(Schema schema) {

		this.set$anchor(schema.get$anchor());
		this.set$comment(schema.get$comment());
		this.set$id(schema.get$id());
		this.set$ref(schema.get$ref());
		this.set$schema(schema.get$schema());
		
		this.setAdditionalItems(schema.getAdditionalItems());
		if (schema.getAdditionalProperties() != null) { 
			this.setAdditionalProperties(schema.getAdditionalProperties());
			this.setJsonAdditionalProperties(new JsonAdditionalProperties (this.getAdditionalProperties()));
		}
		if (schema.getAllOf() != null) { 
			this.setAllOf(schema.getAllOf()); 
			
			jsonAllOf = new JsonArrayList<JsonSchema>() ; 
			for (int i = 0 ; i< schema.getAllOf().size() ; i++)
			{
				Schema xx= (Schema) schema.getAllOf().get(i) ; 
				jsonAllOf.add(new JsonSchema(xx)) ; 	
			}
			
			} 
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
		if (schema.getItems() != null) {
			this.setItems(schema.getItems());
			this.setJsonItems( new JsonSchema(this.getItems()) ) ; 
		}
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
		
		if (schema.getProperties() != null ) { 
			this.setJsonProperties(new JsonProperties (schema.getProperties()) );
			}
		
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
		if (schema.getXml() != null) {
		this.setJsonXml(new JsonXml(schema.getXml()));
		}
	}

	 @Override
	    public String toJsonString() throws JsonMappingException, JsonProcessingException {
	        StringBuilder sb = new StringBuilder();
	        sb.append("{\n");
	        
	        FifoMap<String , Object > elements = new FifoMap<String , Object >() ;
	        Object typeStr = getSpecVersion() == SpecVersion.V30 ? getType() : getTypes();
	        elements.put("type", typeStr) ; 
	        elements.put("format", getFormat()) ;
	        elements.put("$ref", get$ref()) ;
	        elements.put("description", getDescription()) ;
	        elements.put("items", getJsonItems()) ;
	        elements.put("title", getTitle()) ;
	        elements.put("allOf", getJsonAllOf()) ;
	        elements.put("multipleOf", getMultipleOf()) ;
	        elements.put("maximum", getMaximum()) ;
	        Object exclusiveMaximumStr = getSpecVersion() == SpecVersion.V30 ? getExclusiveMaximum() : getExclusiveMaximumValue();
	        elements.put("exclusiveMaximum", exclusiveMaximumStr ) ;
	        elements.put("minimum", getMinimum()) ;
	        Object exclusiveMinimumStr = getSpecVersion() == SpecVersion.V30 ? getExclusiveMinimum() : getExclusiveMinimumValue();
	        elements.put("exclusiveMinimum", exclusiveMinimumStr) ;
	        elements.put("maxLength", getMaxLength()) ;
	        elements.put("minLength", getMinLength()) ;
	        elements.put("pattern", getPattern()) ;
	        elements.put("maxItems", getMaxItems()) ;
	        elements.put("minItems", getMinItems()) ;
	        elements.put("uniqueItems", getUniqueItems()) ;
	        elements.put("maxProperties", getMaxProperties()) ;
	        elements.put("minProperties", getMinProperties()) ;
	        elements.put("required", getRequired()) ;
	        elements.put("not", getNot()) ;
	        elements.put("properties", getJsonProperties()) ;
	        if (getJsonAdditionalProperties() != null ) 
	        { 
	        	elements.put("additionalProperties", getJsonAdditionalProperties()) ;
        	}
	        elements.put("nullable", getNullable()) ;
	        elements.put("readOnly", getReadOnly()) ;
	        elements.put("writeOnly", getWriteOnly()) ;
	        //if( example != null && (example instanceof String ))
		    //{
	        //	System.out.print(example);
		    //}
	        elements.put("example", example) ;
	        
	        elements.put("externalDocs", getExternalDocs()) ;
	        elements.put("deprecated", getDeprecated()) ;
	        elements.put("discriminator", getDiscriminator()) ;
	        elements.put("xml", getJsonXml()) ;
	        if (getSpecVersion() == SpecVersion.V31) {
	        	elements.put("patternProperties", getPatternProperties()) ;
	        	elements.put("contains", getContains()) ;
	        	elements.put("$id", get$id()) ;
	        	elements.put("$anchor", get$anchor()) ;
	        	elements.put("$schema", get$schema()) ;
	        	elements.put("const", getConst()) ;
	        	elements.put("contentEncoding", getContentEncoding()) ;
	        	elements.put("contentMediaType", getContentMediaType()) ;
	        	elements.put("contentSchema", getContentSchema()) ;
	        	elements.put("propertyNames", getPropertyNames()) ;
	        	elements.put("unevaluatedProperties", getUnevaluatedProperties()) ;
	        	elements.put("maxContains", getMaxContains()) ;
	        	elements.put("minContains", getMinContains()) ;
	        	elements.put("additionalItems", getAdditionalItems()) ;
	        	elements.put("unevaluatedItems", getUnevaluatedItems()) ;
	        	elements.put("_if", getIf()) ;
	        	elements.put("_else", getElse()) ;
	        	elements.put("then", getThen()) ;
	        	elements.put("dependentRequired", getDependentRequired()) ;
	        	elements.put("dependentSchemas", getDependentSchemas()) ;
	        	elements.put("$comment", get$comment()) ;
	        	elements.put("prefixItems", getPrefixItems()) ;
	        }

	        sb = Jsonable.appendElements(sb , elements);
	        
	        //=================================
	       /*
	        Object typeStr = getSpecVersion() == SpecVersion.V30 ? getType() : getTypes();
	        if(typeStr != null ) sb.append("    \"type\": \"").append(toIndentedString(typeStr)).append("\"\n");
	        if(getFormat() != null ) sb.append("    \"format\": \"").append(toIndentedString(getFormat())).append("\"\n");
	        if(get$ref() != null ) sb.append("    \"$ref\": \"").append(toIndentedString(get$ref())).append("\"\n");
	        if(getDescription() != null ) sb.append("    \"description\": \"").append(toIndentedString(getDescription())).append("\"\n");
	        if(getTitle() != null ) sb.append("    \"title\": \"").append(toIndentedString(getTitle())).append("\"\n");
	        if(getJsonAllOf() != null ) sb.append("    \"allOf\": \"").append(toIndentedString(getJsonAllOf().toJsonString())).append("\"\n");
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
	        if(getJsonAdditionalProperties() != null ) sb.append("    \"additionalProperties\": ").append(toIndentedString(getJsonAdditionalProperties().toJsonString())).append("\n");
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
	        */
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

		public JsonAdditionalProperties getJsonAdditionalProperties() {
			return jsonAdditionalProperties;
		}

		public void setJsonAdditionalProperties(JsonAdditionalProperties jsonAdditionalProperties) {
			this.jsonAdditionalProperties = jsonAdditionalProperties;
		}

		public JsonArrayList<JsonSchema> getJsonAllOf() {
			return jsonAllOf;
		}

		public void setJsonAllOf(JsonArrayList<JsonSchema> jsonAllOf) {
			this.jsonAllOf = jsonAllOf;
		}

		public JsonXml getJsonXml() {
			return jsonXml;
		}

		public void setJsonXml(JsonXml jsonXml) {
			this.jsonXml = jsonXml;
		}

		


}
