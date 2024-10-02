package com.smartvalue.swagger.v3.parser.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.swagger.v3.oas.models.annotations.OpenAPI31;
//import io.swagger.v3.oas.models.parameters.Parameter;
//import io.swagger.v3.oas.models.servers.Server;

public class JsonPathItem implements Jsonable{
    private String summary = null;
    private String description = null;
    private JsonOperation get = null;
    private JsonOperation put = null;
    private JsonOperation post = null;
    private JsonOperation delete = null;
    private JsonOperation options = null;
    private JsonOperation head = null;
    private JsonOperation patch = null;
    private JsonOperation trace = null;
    private JsonArrayList<JsonServer> servers = null;
    private JsonArrayList<JsonParameter> parameters = null;
    private String $ref = null;
    private java.util.Map<String, Object> extensions = null;

    public JsonPathItem(io.swagger.v3.oas.models.PathItem m_pathItem) {
		if (m_pathItem.getDelete() != null) this.setDelete(new JsonOperation(m_pathItem.getDelete()));
		if (m_pathItem.get$ref() != null) this.set$ref(m_pathItem.get$ref());
		if (m_pathItem.getDescription() != null) this.setDescription(m_pathItem.getDescription());
		if (m_pathItem.getExtensions() != null) this.setExtensions(m_pathItem.getExtensions());
		if (m_pathItem.getGet() != null) this.setGet(new JsonOperation(m_pathItem.getGet()));
		if (m_pathItem.getHead() != null) this.setHead(new JsonOperation(m_pathItem.getHead()));
		if (m_pathItem.getOptions() != null) this.setOptions(new JsonOperation(m_pathItem.getOptions()));
		if (m_pathItem.getParameters() != null && m_pathItem.getParameters().size() > 0)
		{	parameters = new JsonArrayList<JsonParameter>() ; 
			for (io.swagger.v3.oas.models.parameters.Parameter par : m_pathItem.getParameters()  )
			{
				parameters.add(new JsonParameter(par)) ; 
			}
		}
		if (m_pathItem.getPatch() != null) this.setPatch(new JsonOperation(m_pathItem.getPatch()));
		if (m_pathItem.getPost() != null) this.setPost(new JsonOperation(m_pathItem.getPost()));
		if (m_pathItem.getPut() != null) this.setPut(new JsonOperation(m_pathItem.getPut()));
		if (m_pathItem.getServers() != null && m_pathItem.getServers().size() > 0)
		{	servers = new JsonArrayList<JsonServer>() ; 
			for (io.swagger.v3.oas.models.servers.Server ser : m_pathItem.getServers()  )
			{
				servers.add(new JsonServer(ser)) ; 
			}
		}
		this.setSummary(m_pathItem.getSummary());
		if (m_pathItem.getTrace() != null) this.setTrace(new JsonOperation(m_pathItem.getTrace()));
		
	}

	/**
     * returns the summary property from a PathItem instance.
     *
     * @return String summary
     **/

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public JsonPathItem summary(String summary) {
        this.summary = summary;
        return this;
    }

    /**
     * returns the description property from a PathItem instance.
     *
     * @return String description
     **/

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JsonPathItem description(String description) {
        this.description = description;
        return this;
    }

    /**
     * returns the get property from a PathItem instance.
     *
     * @return Operation get
     **/

    public JsonOperation getGet() {
        return get;
    }

    public void setGet(JsonOperation get) {
        this.get = get;
    }

    public JsonPathItem get(JsonOperation get) {
        this.get = get;
        return this;
    }

    /**
     * returns the put property from a PathItem instance.
     *
     * @return Operation put
     **/

    public JsonOperation getPut() {
        return put;
    }

    public void setPut(JsonOperation put) {
        this.put = put;
    }

    public JsonPathItem put(JsonOperation put) {
        this.put = put;
        return this;
    }

    /**
     * returns the post property from a PathItem instance.
     *
     * @return Operation post
     **/

    public JsonOperation getPost() {
        return post;
    }

    public void setPost(JsonOperation post) {
        this.post = post;
    }

    public JsonPathItem post(JsonOperation post) {
        this.post = post;
        return this;
    }

    /**
     * returns the delete property from a PathItem instance.
     *
     * @return Operation delete
     **/

    public JsonOperation getDelete() {
        return delete;
    }

    public void setDelete(JsonOperation delete) {
        this.delete = delete;
    }

    public JsonPathItem delete(JsonOperation delete) {
        this.delete = delete;
        return this;
    }

    /**
     * returns the options property from a PathItem instance.
     *
     * @return Operation options
     **/

    public JsonOperation getOptions() {
        return options;
    }

    public void setOptions(JsonOperation options) {
        this.options = options;
    }

    public JsonPathItem options(JsonOperation options) {
        this.options = options;
        return this;
    }

    /**
     * returns the head property from a PathItem instance.
     *
     * @return Operation head
     **/

    public JsonOperation getHead() {
        return head;
    }

    public void setHead(JsonOperation head) {
        this.head = head;
    }

    public JsonPathItem head(JsonOperation head) {
        this.head = head;
        return this;
    }

    /**
     * returns the patch property from a PathItem instance.
     *
     * @return Operation patch
     **/

    public JsonOperation getPatch() {
        return patch;
    }

    public void setPatch(JsonOperation patch) {
        this.patch = patch;
    }

    public JsonPathItem patch(JsonOperation patch) {
        this.patch = patch;
        return this;
    }

    /**
     * returns the trace property from a PathItem instance.
     *
     * @return Operation trace
     **/

    public JsonOperation getTrace() {
        return trace;
    }

    public void setTrace(JsonOperation trace) {
        this.trace = trace;
    }

    public JsonPathItem trace(JsonOperation trace) {
        this.trace = trace;
        return this;
    }

    public List<JsonOperation> readOperations() {
        List<JsonOperation> allOperations = new ArrayList<>();
        if (this.get != null) {
            allOperations.add(this.get);
        }
        if (this.put != null) {
            allOperations.add(this.put);
        }
        if (this.head != null) {
            allOperations.add(this.head);
        }
        if (this.post != null) {
            allOperations.add(this.post);
        }
        if (this.delete != null) {
            allOperations.add(this.delete);
        }
        if (this.patch != null) {
            allOperations.add(this.patch);
        }
        if (this.options != null) {
            allOperations.add(this.options);
        }
        if (this.trace != null) {
            allOperations.add(this.trace);
        }

        return allOperations;
    }

    public void operation(HttpMethod method, JsonOperation operation) {
        switch (method) {
            case PATCH:
                this.patch = operation;
                break;
            case POST:
                this.post = operation;
                break;
            case PUT:
                this.put = operation;
                break;
            case GET:
                this.get = operation;
                break;
            case OPTIONS:
                this.options = operation;
                break;
            case TRACE:
                this.trace = operation;
                break;
            case HEAD:
                this.head = operation;
                break;
            case DELETE:
                this.delete = operation;
                break;
            default:
        }
    }

    public enum HttpMethod {
        POST,
        GET,
        PUT,
        PATCH,
        DELETE,
        HEAD,
        OPTIONS,
        TRACE
    }

    public Map<HttpMethod, JsonOperation> readOperationsMap() {
        Map<HttpMethod, JsonOperation> result = new LinkedHashMap<>();

        if (this.get != null) {
            result.put(HttpMethod.GET, this.get);
        }
        if (this.put != null) {
            result.put(HttpMethod.PUT, this.put);
        }
        if (this.post != null) {
            result.put(HttpMethod.POST, this.post);
        }
        if (this.delete != null) {
            result.put(HttpMethod.DELETE, this.delete);
        }
        if (this.patch != null) {
            result.put(HttpMethod.PATCH, this.patch);
        }
        if (this.head != null) {
            result.put(HttpMethod.HEAD, this.head);
        }
        if (this.options != null) {
            result.put(HttpMethod.OPTIONS, this.options);
        }
        if (this.trace != null) {
            result.put(HttpMethod.TRACE, this.trace);
        }

        return result;
    }

    /**
     * returns the servers property from a PathItem instance.
     *
     * @return List&lt;Server&gt; servers
     **/

    public List<JsonServer> getServers() {
        return servers;
    }

    public void setServers(JsonArrayList<JsonServer> servers) {
        this.servers = servers;
    }

    public JsonPathItem servers(JsonArrayList<JsonServer> servers) {
        this.servers = servers;
        return this;
    }

    public JsonPathItem addServersItem(JsonServer serversItem) {
        if (this.servers == null) {
            this.servers = new JsonArrayList<>();
        }
        this.servers.add(serversItem);
        return this;
    }

    /**
     * returns the parameters property from a PathItem instance.
     *
     * @return List&lt;Parameter&gt; parameters
     **/

    public List<JsonParameter> getParameters() {
        return parameters;
    }

    public void setParameters(JsonArrayList<JsonParameter> parameters) {
        this.parameters = parameters;
    }

    public JsonPathItem parameters(JsonArrayList<JsonParameter> parameters) {
        this.parameters = parameters;
        return this;
    }

    public JsonPathItem addParametersItem(JsonParameter parametersItem) {
        if (this.parameters == null) {
            this.parameters = new JsonArrayList<>();
        }
        this.parameters.add(parametersItem);
        return this;
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

    public JsonPathItem extensions(java.util.Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    /**
     * returns the ref property from a PathItem instance.
     *
     * @return String ref
     **/
    public String get$ref() {
        return $ref;
    }

    public void set$ref(String $ref) {
        this.$ref = $ref;
    }

    public JsonPathItem $ref(String $ref) {
        set$ref($ref);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JsonPathItem)) {
            return false;
        }

        JsonPathItem pathItem = (JsonPathItem) o;

        if (summary != null ? !summary.equals(pathItem.summary) : pathItem.summary != null) {
            return false;
        }
        if (description != null ? !description.equals(pathItem.description) : pathItem.description != null) {
            return false;
        }
        if (get != null ? !get.equals(pathItem.get) : pathItem.get != null) {
            return false;
        }
        if (put != null ? !put.equals(pathItem.put) : pathItem.put != null) {
            return false;
        }
        if (post != null ? !post.equals(pathItem.post) : pathItem.post != null) {
            return false;
        }
        if (delete != null ? !delete.equals(pathItem.delete) : pathItem.delete != null) {
            return false;
        }
        if (options != null ? !options.equals(pathItem.options) : pathItem.options != null) {
            return false;
        }
        if (head != null ? !head.equals(pathItem.head) : pathItem.head != null) {
            return false;
        }
        if (patch != null ? !patch.equals(pathItem.patch) : pathItem.patch != null) {
            return false;
        }
        if (trace != null ? !trace.equals(pathItem.trace) : pathItem.trace != null) {
            return false;
        }
        if (servers != null ? !servers.equals(pathItem.servers) : pathItem.servers != null) {
            return false;
        }
        if (parameters != null ? !parameters.equals(pathItem.parameters) : pathItem.parameters != null) {
            return false;
        }
        if ($ref != null ? !$ref.equals(pathItem.$ref) : pathItem.$ref != null) {
            return false;
        }
        return extensions != null ? extensions.equals(pathItem.extensions) : pathItem.extensions == null;

    }

    @Override
    public int hashCode() {
        int result = summary != null ? summary.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (get != null ? get.hashCode() : 0);
        result = 31 * result + (put != null ? put.hashCode() : 0);
        result = 31 * result + (post != null ? post.hashCode() : 0);
        result = 31 * result + (delete != null ? delete.hashCode() : 0);
        result = 31 * result + (options != null ? options.hashCode() : 0);
        result = 31 * result + (head != null ? head.hashCode() : 0);
        result = 31 * result + (patch != null ? patch.hashCode() : 0);
        result = 31 * result + (trace != null ? trace.hashCode() : 0);
        result = 31 * result + (servers != null ? servers.hashCode() : 0);
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        result = 31 * result + ($ref != null ? $ref.hashCode() : 0);
        result = 31 * result + (extensions != null ? extensions.hashCode() : 0);
        return result;
    }

    public String toJsonString() throws JsonMappingException, JsonProcessingException {
        StringBuilder sb = new StringBuilder();
         
        sb.append("{\n");
        
        FifoMap<String , Object > elements = new FifoMap<String , Object >() ;
        elements.put("summary", summary ) ; 
        elements.put("description", getDescription()) ;
        elements.put("get", (Jsonable)get) ;
        elements.put("put", (Jsonable)put) ;
        elements.put("post", (Jsonable)post) ;
        elements.put("delete", (Jsonable)delete) ;
        elements.put("options", (Jsonable)options) ;
        elements.put("head", (Jsonable)head) ;
        elements.put("trace", (Jsonable)trace) ;
        elements.put("servers", servers) ;
        elements.put("parameters", parameters) ;
        elements.put("$ref", $ref) ;
        
        sb = Jsonable.appendElements(sb, elements);
        /*
         boolean needComma = false ;
        if(summary != null) 	{ sb.append(Jsonable.appendCommaEnter(needComma)).append ("\"summary\": ").append(toIndentedString(summary)).append("\n"); needComma = true; } 
        if(description != null) { sb.append("    \"description\": ").append(toIndentedString(description)).append("\n"); needComma = true; } 
        if(get != null) 		{ sb.append("    \"get\": ").append(toIndentedString(((Jsonable)get).toJsonString())).append("\n"); needComma = true; }
        if(put != null) 		{ sb.append("    \"put\": ").append(toIndentedString(((Jsonable)put).toJsonString())).append("\n"); needComma = true; }
        if(post != null) 		{ sb.append("    \"post\": ").append(toIndentedString(((Jsonable)post).toJsonString())).append("\n"); needComma = true; }
        if(delete != null) 		{ sb.append("    \"delete\": ").append(toIndentedString(((Jsonable)delete).toJsonString())).append("\n"); needComma = true; }
        if(options != null) 	{ sb.append("    \"options\": ").append(toIndentedString(((Jsonable)options).toJsonString())).append("\n"); needComma = true; }
        if(head != null) 		{ sb.append("    \"head\": ").append(toIndentedString(((Jsonable)head).toJsonString())).append("\n"); needComma = true; }
        if(patch != null) 		{ sb.append("    \"patch\": ").append(toIndentedString(((Jsonable)patch).toJsonString())).append("\n"); needComma = true; }
        if(trace != null) 		{ sb.append("    \"trace\": ").append(toIndentedString(((Jsonable)trace).toJsonString())).append("\n"); needComma = true; }
        if(servers != null) 	{ sb.append("    \"servers\": ").append(toIndentedString(servers)).append("\n"); needComma = true; }
        if(parameters != null) 	{ sb.append("    \"parameters\": ").append(toIndentedString(parameters.toJsonString())).append("\n"); needComma = true; }
        if($ref != null) 		{ sb.append("    \"$ref\": ").append(toIndentedString($ref)).append("\n"); needComma = true; }
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
}
