package com.smartvalue.swagger.v3.parser.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.swagger.v3.oas.models.annotations.OpenAPI31;


public class JsonOperation implements Jsonable {
    private JsonArrayList<String> tags = null;
    private String summary = null;
    private String description = null;
    private ExternalDocumentation externalDocs = null;
    private String operationId = null;
    private JsonArrayList<JsonParameter> jsonParameters = null;
    private JsonRequestBody jsonRequestBody = null;
    private JsonApiResponses jsonResponses = null;
    private Map<String, JsonCallback> callbacks = null;
    private Boolean deprecated = null;
    private JsonArrayList<JsonSecurityRequirement> security = null;
    private List<JsonServer> servers = null;
    private java.util.Map<String, Object> extensions = null;

    public JsonOperation(io.swagger.v3.oas.models.Operation m_operation) {

	   	if (m_operation.getCallbacks() != null && m_operation.getCallbacks().size() > 0 )
	   	{
		    for (Entry<String, io.swagger.v3.oas.models.callbacks.Callback> entry :  m_operation.getCallbacks().entrySet()) 
		   	{
		   		this.callbacks.put(entry.getKey() , new JsonCallback (entry.getValue()) ) ; 
		   	}
	   	}
	   	if (m_operation.getDeprecated() != null ) this.setDeprecated(m_operation.getDeprecated());
	   	if (m_operation.getDescription() != null ) this.setDescription(m_operation.getDescription());
	   	if (m_operation.getExtensions() != null ) this.setExtensions(m_operation.getExtensions());
	   	if (m_operation.getExternalDocs() != null ) this.setExternalDocs(new ExternalDocumentation (m_operation.getExternalDocs()));
	   	if (m_operation.getOperationId() != null ) this.setOperationId(m_operation.getOperationId());
	   	if (m_operation.getParameters() != null && m_operation.getParameters().size() > 0)
		{	jsonParameters = new JsonArrayList<JsonParameter>() ; 
			for (io.swagger.v3.oas.models.parameters.Parameter par : m_operation.getParameters()  )
			{
				jsonParameters.add(new JsonParameter(par)) ; 
			}
		}
	   	if (m_operation.getRequestBody() != null ) this.setRequestBody(new JsonRequestBody(m_operation.getRequestBody()));
	   	if (m_operation.getResponses() != null ) this.setResponses(new JsonApiResponses( m_operation.getResponses())) ;
	   	
	   	if (m_operation.getSecurity() != null && m_operation.getSecurity().size()>0 )
	   	{
	    	this.security = new JsonArrayList<JsonSecurityRequirement>() ; 
	   		for (io.swagger.v3.oas.models.security.SecurityRequirement securityRequirement : m_operation.getSecurity() )
	   		{	security.add(new JsonSecurityRequirement(securityRequirement)) ;	}
	   	}
	   	if  (m_operation.getServers() != null && m_operation.getServers().size() > 0)
		{	servers = new ArrayList<JsonServer>() ; 
			for (io.swagger.v3.oas.models.servers.Server ser : m_operation.getServers()  )
			{servers.add(new JsonServer(ser)) ;}
		}
	   	
	   	this.setSummary(m_operation.getSummary());
	   	if (m_operation.getTags() != null ) this.setTags(new JsonArrayList(m_operation.getTags()));
    	
	}
    
    

	/**
     * returns the tags property from a Operation instance.
     *
     * @return List&lt;String&gt; tags
     **/

    public JsonArrayList<String> getTags() {
        return tags;
    }

    public void setTags(JsonArrayList<String> tags) {
        this.tags = tags;
    }

    public JsonOperation tags(JsonArrayList<String> tags) {
        this.tags = tags;
        return this;
    }

    public JsonOperation addTagsItem(String tagsItem) {
        if (this.tags == null) {
            this.tags = new JsonArrayList<>();
        }
        this.tags.add(tagsItem);
        return this;
    }

    /**
     * returns the summary property from a Operation instance.
     *
     * @return String summary
     **/

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public JsonOperation summary(String summary) {
        this.summary = summary;
        return this;
    }

    /**
     * returns the description property from a Operation instance.
     *
     * @return String description
     **/

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JsonOperation description(String description) {
        this.description = description;
        return this;
    }

    /**
     * returns the externalDocs property from a Operation instance.
     *
     * @return ExternalDocumentation externalDocs
     **/

    public ExternalDocumentation getExternalDocs() {
        return externalDocs;
    }

    public void setExternalDocs(ExternalDocumentation externalDocs) {
        this.externalDocs = externalDocs;
    }

    public JsonOperation externalDocs(ExternalDocumentation externalDocs) {
        this.externalDocs = externalDocs;
        return this;
    }

    /**
     * returns the operationId property from a Operation instance.
     *
     * @return String operationId
     **/

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public JsonOperation operationId(String operationId) {
        this.operationId = operationId;
        return this;
    }

    /**
     * returns the parameters property from a Operation instance.
     *
     * @return List&lt;Parameter&gt; parameters
     **/

    public List<JsonParameter> getParameters() {
        return jsonParameters;
    }

    public void setParameters(JsonArrayList<JsonParameter> parameters) {
        this.jsonParameters = parameters;
    }

    public JsonOperation parameters(JsonArrayList<JsonParameter> parameters) {
        this.jsonParameters = parameters;
        return this;
    }

    
    /**
     * returns the requestBody property from a Operation instance.
     *
     * @return RequestBody requestBody
     **/

    public JsonRequestBody getRequestBody() {
        return jsonRequestBody;
    }

    public void setRequestBody(JsonRequestBody requestBody) {
        this.jsonRequestBody = requestBody;
    }

    public JsonOperation requestBody(JsonRequestBody requestBody) {
        this.jsonRequestBody = requestBody;
        return this;
    }

    /**
     * returns the responses property from a Operation instance.
     *
     * @return ApiResponses responses
     **/

    public JsonApiResponses getResponses() {
        return jsonResponses;
    }

    public void setResponses(JsonApiResponses responses) {
        this.jsonResponses = responses;
    }

    public JsonOperation responses(JsonApiResponses responses) {
        this.jsonResponses = responses;
        return this;
    }

    /**
     * returns the callbacks property from a Operation instance.
     *
     * @return Callbacks callbacks
     **/

    public Map<String, JsonCallback> getCallbacks() {
        return callbacks;
    }

    public void setCallbacks(Map<String, JsonCallback> callbacks) {
        this.callbacks = callbacks;
    }

    public JsonOperation callbacks(Map<String, JsonCallback> callbacks) {
        this.callbacks = callbacks;
        return this;
    }

    public JsonOperation addCallback(String key, JsonCallback callback) {
        if (this.callbacks == null) {
            this.callbacks = new LinkedHashMap<>();
        }
        this.callbacks.put(key, callback);
        return this;
    }

    /**
     * returns the deprecated property from a Operation instance.
     *
     * @return Boolean deprecated
     **/

    public Boolean getDeprecated() {
        return deprecated;
    }

    public void setDeprecated(Boolean deprecated) {
        this.deprecated = deprecated;
    }

    public JsonOperation deprecated(Boolean deprecated) {
        this.deprecated = deprecated;
        return this;
    }

    /**
     * returns the security property from a Operation instance.
     *
     * @return List&lt;SecurityRequirement&gt; security
     **/

    public List<JsonSecurityRequirement> getSecurity() {
        return security;
    }

    public void setSecurity(JsonArrayList<JsonSecurityRequirement> security) {
        this.security = security;
    }

    public JsonOperation security(JsonArrayList<JsonSecurityRequirement> security) {
        this.security = security;
        return this;
    }

    public JsonOperation addSecurityItem(JsonSecurityRequirement securityItem) {
        if (this.security == null) {
            this.security = new JsonArrayList<>();
        }
        this.security.add(securityItem);
        return this;
    }

    /**
     * returns the servers property from a Operation instance.
     *
     * @return List&lt;Server&gt; servers
     **/

    public List<JsonServer> getServers() {
        return servers;
    }

    public void setServers(List<JsonServer> servers) {
        this.servers = servers;
    }

    public JsonOperation servers(List<JsonServer> servers) {
        this.servers = servers;
        return this;
    }

    public JsonOperation addServersItem(JsonServer serversItem) {
        if (this.servers == null) {
            this.servers = new ArrayList<>();
        }
        this.servers.add(serversItem);
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
        JsonOperation operation = (JsonOperation) o;
        return Objects.equals(this.tags, operation.tags) &&
                Objects.equals(this.summary, operation.summary) &&
                Objects.equals(this.description, operation.description) &&
                Objects.equals(this.externalDocs, operation.externalDocs) &&
                Objects.equals(this.operationId, operation.operationId) &&
                Objects.equals(this.jsonParameters, operation.jsonParameters) &&
                Objects.equals(this.jsonRequestBody, operation.jsonRequestBody) &&
                Objects.equals(this.jsonResponses, operation.jsonResponses) &&
                Objects.equals(this.callbacks, operation.callbacks) &&
                Objects.equals(this.deprecated, operation.deprecated) &&
                Objects.equals(this.security, operation.security) &&
                Objects.equals(this.servers, operation.servers) &&
                Objects.equals(this.extensions, operation.extensions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tags, summary, description, externalDocs, operationId, jsonParameters, jsonRequestBody, jsonResponses, callbacks, deprecated, security, servers, extensions);
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

    public JsonOperation extensions(java.util.Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    @Override
    public String toJsonString() throws JsonMappingException, JsonProcessingException {
    	boolean needComma = false ; 
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        FifoMap<String , Object > elements = new FifoMap<String , Object >() ;
        elements.put("requestBody", jsonRequestBody) ; 
        elements.put("tags", tags) ;
        elements.put("responses", jsonResponses) ;
        elements.put("summary", summary) ;
        elements.put("externalDocs", externalDocs) ;
        elements.put("security", security) ;
        elements.put("operationId", operationId) ;
        elements.put("description", description) ;
        elements.put("parameters", jsonParameters) ;
        elements.put("callbacks", callbacks) ;
        elements.put("deprecated", deprecated) ;
        elements.put("servers", servers) ;
        
        sb = Jsonable.appendElements(sb, elements);
        /*
        if (jsonRequestBody != null){sb.append("    \"requestBody\": ").append(toIndentedString(jsonRequestBody.toJsonString())); needComma = true; }
        if (tags != null) 			{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"tags\": ").append(toIndentedString(tags.toJsonString())); needComma = true ; }
        if (jsonResponses != null) 	{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"responses\": ").append(toIndentedString(jsonResponses.toJsonString())) ; needComma = true; }
        if (summary != null) 		{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"summary\": \"").append(toIndentedString(summary)).append("\""); needComma = true ; }
        if (externalDocs != null) 	{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"externalDocs\": ").append(toIndentedString(externalDocs)); needComma = true; }
        if (security != null) 		{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"security\": ").append(toIndentedString(security.toJsonString())); needComma = true; }
        if (operationId != null) 	{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"operationId\": \"").append(toIndentedString(operationId)).append("\""); needComma = true; }
        if (description != null) 	{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"description\": \"").append(toIndentedString(description)).append("\""); needComma = true; }                
        if (jsonParameters != null) {sb.append( Jsonable.appendCommaEnter(needComma)).append("\"parameters\": ").append(toIndentedString(jsonParameters)); needComma = true; }
        if (callbacks != null) 		{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"callbacks\": ").append(toIndentedString(callbacks)); needComma = true; }
        if (deprecated != null) 	{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"deprecated\": ").append(toIndentedString(deprecated)); needComma = true; }
        if (servers != null) 		{sb.append( Jsonable.appendCommaEnter(needComma)).append("\"servers\": ").append(toIndentedString(servers)); needComma = true;  }
        */
        sb.append("\n}");
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

