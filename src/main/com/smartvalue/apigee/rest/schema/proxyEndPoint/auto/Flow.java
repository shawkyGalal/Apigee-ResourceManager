
package com.smartvalue.apigee.rest.schema.proxyEndPoint.auto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;

import org.w3c.dom.Element;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import com.smartvalue.apigee.proxyBundle.BundleElement;
import com.smartvalue.apigee.rest.schema.proxyRevision.OasOperation;
import com.smartvalue.apigee.rest.schema.proxyRevision.ProxyRevision;

import io.swagger.v3.oas.models.Operation;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "condition",
    "description",
    "name",
    "request",
    "response"
})
@Generated("jsonschema2pojo")
public class Flow extends BundleElement {

   

	@JsonProperty("condition")
    private String condition;
    @JsonProperty("description")
    private String description;
    @JsonProperty("name")
    private String name;
    @JsonProperty("request")
    private Request request;
    @JsonProperty("response")
    private Response response;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("condition")
    public String getCondition() {
        return condition;
    }

    @JsonProperty("condition")
    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Flow withCondition(String condition) {
        this.condition = condition;
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

    public Flow withDescription(String description) {
        this.description = description;
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

    public Flow withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("request")
    public Request getRequest() {
        return request;
    }

    @JsonProperty("request")
    public void setRequest(Request request) {
        this.request = request;
    }

    public Flow withRequest(Request request) {
        this.request = request;
        return this;
    }

    @JsonProperty("response")
    public Response getResponse() {
        return response;
    }

    @JsonProperty("response")
    public void setResponse(Response response) {
        this.response = response;
    }

    public Flow withResponse(Response response) {
        this.response = response;
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

    public Flow withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Flow.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("condition");
        sb.append('=');
        sb.append(((this.condition == null)?"<null>":this.condition));
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null)?"<null>":this.description));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("request");
        sb.append('=');
        sb.append(((this.request == null)?"<null>":this.request));
        sb.append(',');
        sb.append("response");
        sb.append('=');
        sb.append(((this.response == null)?"<null>":this.response));
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
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.description == null)? 0 :this.description.hashCode()));
        result = ((result* 31)+((this.request == null)? 0 :this.request.hashCode()));
        result = ((result* 31)+((this.condition == null)? 0 :this.condition.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.response == null)? 0 :this.response.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Flow) == false) {
            return false;
        }
        Flow rhs = ((Flow) other);
        return (((((((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.description == rhs.description)||((this.description!= null)&&this.description.equals(rhs.description))))&&((this.request == rhs.request)||((this.request!= null)&&this.request.equals(rhs.request))))&&((this.condition == rhs.condition)||((this.condition!= null)&&this.condition.equals(rhs.condition))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.response == rhs.response)||((this.response!= null)&&this.response.equals(rhs.response))));
    }
  
    
    //---------------End of Auto generated Code ------------------------------------
    
    //--------------Extra Manual  Code------------------
    public Flow(String proxyName, Element element) {
		super(proxyName, element);
		pathSuffix = extractPathSuffixFromCondition();
		this.verb = extractVerbFromCondition(); 
	}
    
    public HttpResponse<String>  call(String serverURL , String accessToken) throws Exception {
		String pathsuffix = extractPathSuffixFromCondition() ; 
		String basePath = this.getParentProxyEndPoint().getConnection().getBasePath(); 
		String authorization = "Bearer " + accessToken ; 
		GetRequest request = Unirest.get(serverURL + basePath + pathsuffix).header("Authorization", authorization ) ; 
		HttpResponse<String> response =  request.asString();  
		return response; 
		
	}
    private String pathSuffix ; 
    private String verb ; 
    public String getPathSuffix() {
		return pathSuffix;
	}

	private String extractPathSuffixFromCondition() 
	{	
		String result = this.getPathSuffix(); 
		if (result == null)
		{
			try {
			String condition = this.getCondition().replaceAll(" ", "");    // (proxy.pathsuffix MatchesPath \"/api/Files/UploadFile\")
			
			int pathSuffixStartIndex = "(proxy.pathsuffixMatchesPath\"".length() ;
			int pathSuffixEndIndex = condition.substring(pathSuffixStartIndex).indexOf("\"") ; 
			result = condition.substring(pathSuffixStartIndex).substring(0, pathSuffixEndIndex  ).trim() ;
			pathSuffix = result ; 
			}
			catch (Exception e ) {
				//System.out.println("Warnning : Unable to extract PathSuffix from Condition " + this.toString());
			}
		}
		
		return result ; 
	}
	
	private String extractVerbFromCondition()
	{
		String result = null ; 
		try {
		String condition = this.getCondition().replaceAll(" ", "");    // (proxy.pathsuffix MatchesPath \"/api/Files/UploadFile\")
		String prefix = "(request.verb=\"" ; 
		int verbStartIndex = condition.indexOf(prefix ) + prefix.length();
		int verbEndIndex = condition.substring(verbStartIndex).indexOf("\"") ; 
		result = condition.substring(verbStartIndex).substring(0, verbEndIndex  ).trim() ; 
		}
		catch (Exception e ) {
			System.out.println("Warnning : Unable to extract Verb from Condition " + this.toString());
		}
		return result ; 
	}
	
	private com.smartvalue.apigee.rest.schema.proxyEndPoint.ProxyEndpoint parentProxyEndPoint ; 
	
	public com.smartvalue.apigee.rest.schema.proxyEndPoint.ProxyEndpoint getParentProxyEndPoint() {
		return parentProxyEndPoint;
	}

	public void setParentProxyEndPoint(com.smartvalue.apigee.rest.schema.proxyEndPoint.ProxyEndpoint parentProxyEndPoint) {
		this.parentProxyEndPoint = parentProxyEndPoint;
	}
 
	public boolean match(OasOperation oper )
	{
		boolean result = false ;
		try {
			result = 	this.getCompletePath().equalsIgnoreCase(oper.getPath())	
					&& 	this.getVerb().equalsIgnoreCase(oper.getVerb()) ;
		} catch (Exception e )
		{
		 e.printStackTrace();	
		}
		return result ; 
		
	}
	public String getCompletePath()
	{
		String result = null ; 
		try {
			result=  this.getParentProxyEndPoint().getConnection().getBasePath() + this.extractPathSuffixFromCondition() ;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result ; 
	}
	

	/* 
	 * private ArrayList<Operation> matchedOper = new ArrayList<Operation>() ; 
	 
	public ArrayList<Operation> getMatchedOasOper()
	{
		return matchedOper ; 
	}
	public void addMatchedOperation(Operation m_operation) {
		if(matchedOper == null) matchedOper = new ArrayList<Operation>(); 
		matchedOper.add(m_operation) ; 
		
	}
	*/
	
	public String getUniqueIdentifier()
	{
		com.smartvalue.apigee.rest.schema.proxyEndPoint.ProxyEndpoint pep = this.getParentProxyEndPoint() ; 
		ProxyRevision pr = pep.getParentProxyRevision() ; 
		return pr.getName() + "."+ pep.getName() +"." + this.getName() + "." +this.getVerb(); 
	}

	public String getVerb() {
		return verb;
	}

	public void setVerb(String verb) {
		this.verb = verb;
	}

	
}
