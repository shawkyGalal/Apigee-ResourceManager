
package com.smartvalue.apigee.rest.schema.proxy;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "connection",
    "connectionType",
    "description",
    "faultRules",
    "flows",
    "name",
    "postClientFlow",
    "postFlow",
    "preFlow",
    "routeRule",
    "routeRuleNames",
    "type"
})
@Generated("jsonschema2pojo")
public class ProxyEndpoint {

    @JsonProperty("connection")
    private Connection connection;
    @JsonProperty("connectionType")
    private String connectionType;
    @JsonProperty("description")
    private String description;
    @JsonProperty("faultRules")
    private List<Object> faultRules = new ArrayList<Object>();
    @JsonProperty("flows")
    private List<Flow> flows = new ArrayList<Flow>();
    @JsonProperty("name")
    private String name;
    @JsonProperty("postClientFlow")
    private PostClientFlow postClientFlow;
    @JsonProperty("postFlow")
    private PostFlow postFlow;
    @JsonProperty("preFlow")
    private PreFlow preFlow;
    @JsonProperty("routeRule")
    private List<RouteRule> routeRule = new ArrayList<RouteRule>();
    @JsonProperty("routeRuleNames")
    private List<String> routeRuleNames = new ArrayList<String>();
    @JsonProperty("type")
    private String type;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("connection")
    public Connection getConnection() {
        return connection;
    }

    @JsonProperty("connection")
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public ProxyEndpoint withConnection(Connection connection) {
        this.connection = connection;
        return this;
    }

    @JsonProperty("connectionType")
    public String getConnectionType() {
        return connectionType;
    }

    @JsonProperty("connectionType")
    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public ProxyEndpoint withConnectionType(String connectionType) {
        this.connectionType = connectionType;
        return this;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public ProxyEndpoint withDescription(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("faultRules")
    public List<Object> getFaultRules() {
        return faultRules;
    }

    @JsonProperty("faultRules")
    public void setFaultRules(List<Object> faultRules) {
        this.faultRules = faultRules;
    }

    public ProxyEndpoint withFaultRules(List<Object> faultRules) {
        this.faultRules = faultRules;
        return this;
    }

    @JsonProperty("flows")
    public List<Flow> getFlows() {
        return flows;
    }

    @JsonProperty("flows")
    public void setFlows(List<Flow> flows) {
        this.flows = flows;
    }

    public ProxyEndpoint withFlows(List<Flow> flows) {
        this.flows = flows;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public ProxyEndpoint withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("postClientFlow")
    public PostClientFlow getPostClientFlow() {
        return postClientFlow;
    }

    @JsonProperty("postClientFlow")
    public void setPostClientFlow(PostClientFlow postClientFlow) {
        this.postClientFlow = postClientFlow;
    }

    public ProxyEndpoint withPostClientFlow(PostClientFlow postClientFlow) {
        this.postClientFlow = postClientFlow;
        return this;
    }

    @JsonProperty("postFlow")
    public PostFlow getPostFlow() {
        return postFlow;
    }

    @JsonProperty("postFlow")
    public void setPostFlow(PostFlow postFlow) {
        this.postFlow = postFlow;
    }

    public ProxyEndpoint withPostFlow(PostFlow postFlow) {
        this.postFlow = postFlow;
        return this;
    }

    @JsonProperty("preFlow")
    public PreFlow getPreFlow() {
        return preFlow;
    }

    @JsonProperty("preFlow")
    public void setPreFlow(PreFlow preFlow) {
        this.preFlow = preFlow;
    }

    public ProxyEndpoint withPreFlow(PreFlow preFlow) {
        this.preFlow = preFlow;
        return this;
    }

    @JsonProperty("routeRule")
    public List<RouteRule> getRouteRule() {
        return routeRule;
    }

    @JsonProperty("routeRule")
    public void setRouteRule(List<RouteRule> routeRule) {
        this.routeRule = routeRule;
    }

    public ProxyEndpoint withRouteRule(List<RouteRule> routeRule) {
        this.routeRule = routeRule;
        return this;
    }

    @JsonProperty("routeRuleNames")
    public List<String> getRouteRuleNames() {
        return routeRuleNames;
    }

    @JsonProperty("routeRuleNames")
    public void setRouteRuleNames(List<String> routeRuleNames) {
        this.routeRuleNames = routeRuleNames;
    }

    public ProxyEndpoint withRouteRuleNames(List<String> routeRuleNames) {
        this.routeRuleNames = routeRuleNames;
        return this;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public ProxyEndpoint withType(String type) {
        this.type = type;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public ProxyEndpoint withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ProxyEndpoint.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("connection");
        sb.append('=');
        sb.append(((this.connection == null)?"<null>":this.connection));
        sb.append(',');
        sb.append("connectionType");
        sb.append('=');
        sb.append(((this.connectionType == null)?"<null>":this.connectionType));
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null)?"<null>":this.description));
        sb.append(',');
        sb.append("faultRules");
        sb.append('=');
        sb.append(((this.faultRules == null)?"<null>":this.faultRules));
        sb.append(',');
        sb.append("flows");
        sb.append('=');
        sb.append(((this.flows == null)?"<null>":this.flows));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("postClientFlow");
        sb.append('=');
        sb.append(((this.postClientFlow == null)?"<null>":this.postClientFlow));
        sb.append(',');
        sb.append("postFlow");
        sb.append('=');
        sb.append(((this.postFlow == null)?"<null>":this.postFlow));
        sb.append(',');
        sb.append("preFlow");
        sb.append('=');
        sb.append(((this.preFlow == null)?"<null>":this.preFlow));
        sb.append(',');
        sb.append("routeRule");
        sb.append('=');
        sb.append(((this.routeRule == null)?"<null>":this.routeRule));
        sb.append(',');
        sb.append("routeRuleNames");
        sb.append('=');
        sb.append(((this.routeRuleNames == null)?"<null>":this.routeRuleNames));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.preFlow == null)? 0 :this.preFlow.hashCode()));
        result = ((result* 31)+((this.routeRuleNames == null)? 0 :this.routeRuleNames.hashCode()));
        result = ((result* 31)+((this.description == null)? 0 :this.description.hashCode()));
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        result = ((result* 31)+((this.connectionType == null)? 0 :this.connectionType.hashCode()));
        result = ((result* 31)+((this.flows == null)? 0 :this.flows.hashCode()));
        result = ((result* 31)+((this.postFlow == null)? 0 :this.postFlow.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.routeRule == null)? 0 :this.routeRule.hashCode()));
        result = ((result* 31)+((this.connection == null)? 0 :this.connection.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.faultRules == null)? 0 :this.faultRules.hashCode()));
        result = ((result* 31)+((this.postClientFlow == null)? 0 :this.postClientFlow.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ProxyEndpoint) == false) {
            return false;
        }
        ProxyEndpoint rhs = ((ProxyEndpoint) other);
        return ((((((((((((((this.preFlow == rhs.preFlow)||((this.preFlow!= null)&&this.preFlow.equals(rhs.preFlow)))&&((this.routeRuleNames == rhs.routeRuleNames)||((this.routeRuleNames!= null)&&this.routeRuleNames.equals(rhs.routeRuleNames))))&&((this.description == rhs.description)||((this.description!= null)&&this.description.equals(rhs.description))))&&((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type))))&&((this.connectionType == rhs.connectionType)||((this.connectionType!= null)&&this.connectionType.equals(rhs.connectionType))))&&((this.flows == rhs.flows)||((this.flows!= null)&&this.flows.equals(rhs.flows))))&&((this.postFlow == rhs.postFlow)||((this.postFlow!= null)&&this.postFlow.equals(rhs.postFlow))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.routeRule == rhs.routeRule)||((this.routeRule!= null)&&this.routeRule.equals(rhs.routeRule))))&&((this.connection == rhs.connection)||((this.connection!= null)&&this.connection.equals(rhs.connection))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.faultRules == rhs.faultRules)||((this.faultRules!= null)&&this.faultRules.equals(rhs.faultRules))))&&((this.postClientFlow == rhs.postClientFlow)||((this.postClientFlow!= null)&&this.postClientFlow.equals(rhs.postClientFlow))));
    }

}
