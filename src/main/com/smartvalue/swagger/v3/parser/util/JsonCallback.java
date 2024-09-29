package com.smartvalue.swagger.v3.parser.util;

import java.util.LinkedHashMap;
import java.util.Objects;

import io.swagger.v3.oas.models.annotations.OpenAPI31;
import io.swagger.v3.oas.models.callbacks.Callback;


public class JsonCallback extends LinkedHashMap<String, JsonPathItem>  
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private java.util.Map<String, Object> extensions = null;
    private String $ref = null;

    public JsonCallback(Callback callback) {
    	this.set$ref(callback.get$ref());
    	this.setExtensions(callback.getExtensions());
	}

	/**
     * @since 2.0.3
     */
    public String get$ref() {
        return $ref;
    }

    /**
     * @since 2.0.3
     */
    public void set$ref(String $ref) {
        if ($ref != null && ($ref.indexOf('.') == -1 && $ref.indexOf('/') == -1)) {
            $ref = "#/components/callbacks/" + $ref;
        }
        this.$ref = $ref;
    }

    /**
     * @since 2.0.3
     */
    public JsonCallback $ref(String $ref) {
        set$ref($ref);
        return this;
    }

    public JsonCallback addPathItem(String name, JsonPathItem item) {
        this.put(name, item);
        return this;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JsonCallback callback = (JsonCallback) o;
        if ($ref != null ? !$ref.equals(callback.$ref) : callback.$ref != null) {
            return false;
        }
        return Objects.equals(this.extensions, callback.extensions) &&
                super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(extensions, $ref, super.hashCode());
    }

    public java.util.Map<String, Object> getExtensions() {
        return extensions;
    }

    public void addExtension(String name, Object value) {
        if (name == null || name.isEmpty() || !name.startsWith("x-")) {
            return;
        }
        if (this.extensions == null) {
            this.extensions = new java.util.LinkedHashMap<>();
        }
        this.extensions.put(name, value);
    }

    @OpenAPI31
    public void addExtension31(String name, Object value) {
        if (name != null && (name.startsWith("x-oas-") || name.startsWith("x-oai-"))) {
            return;
        }
        addExtension(name, value);
    }

    public void setExtensions(java.util.Map<String, Object> extensions) {
        this.extensions = extensions;
    }

    public JsonCallback extensions(java.util.Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Callback {\n");
        sb.append("    $ref: ").append(toIndentedString($ref)).append("\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
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
