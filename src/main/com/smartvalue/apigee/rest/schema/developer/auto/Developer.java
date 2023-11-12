
package com.smartvalue.apigee.rest.schema.developer.auto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.rest.schema.ApigeeComman;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "apps",
    "companies",
    "email",
    "developerId",
    "firstName",
    "lastName",
    "userName",
    "organizationName",
    "status",
    "attributes",
    "createdAt",
    "createdBy",
    "lastModifiedAt",
    "lastModifiedBy"
})
@Generated("jsonschema2pojo")
public class Developer extends ApigeeComman {

    @JsonProperty("apps")
    private List<String> apps = new ArrayList<String>();
    @JsonProperty("companies")
    private List<Object> companies = new ArrayList<Object>();
    @JsonProperty("email")
    private String email;
    @JsonProperty("developerId")
    private String developerId;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("organizationName")
    private String organizationName;
    @JsonProperty("status")
    private String status;
    @JsonProperty("attributes")
    private List<Attribute> attributes = new ArrayList<Attribute>();
    @JsonProperty("createdAt")
    private Long createdAt;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("lastModifiedAt")
    private Long lastModifiedAt;
    @JsonProperty("lastModifiedBy")
    private String lastModifiedBy;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("apps")
    public List<String> getApps() {
        return apps;
    }

    @JsonProperty("apps")
    public void setApps(List<String> apps) {
        this.apps = apps;
    }

    public Developer withApps(List<String> apps) {
        this.apps = apps;
        return this;
    }

    @JsonProperty("companies")
    public List<Object> getCompanies() {
        return companies;
    }

    @JsonProperty("companies")
    public void setCompanies(List<Object> companies) {
        this.companies = companies;
    }

    public Developer withCompanies(List<Object> companies) {
        this.companies = companies;
        return this;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    public Developer withEmail(String email) {
        this.email = email;
        return this;
    }

    @JsonProperty("developerId")
    public String getDeveloperId() {
        return developerId;
    }

    @JsonProperty("developerId")
    public void setDeveloperId(String developerId) {
        this.developerId = developerId;
    }

    public Developer withDeveloperId(String developerId) {
        this.developerId = developerId;
        return this;
    }

    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Developer withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Developer withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @JsonProperty("userName")
    public String getUserName() {
        return userName;
    }

    @JsonProperty("userName")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Developer withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    @JsonProperty("organizationName")
    public String getOrganizationName() {
        return organizationName;
    }

    @JsonProperty("organizationName")
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Developer withOrganizationName(String organizationName) {
        this.organizationName = organizationName;
        return this;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public Developer withStatus(String status) {
        this.status = status;
        return this;
    }

    @JsonProperty("attributes")
    public List<Attribute> getAttributes() {
        return attributes;
    }

    @JsonProperty("attributes")
    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public Developer withAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
        return this;
    }

    @JsonProperty("createdAt")
    public Long getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("createdAt")
    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Developer withCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @JsonProperty("createdBy")
    public String getCreatedBy() {
        return createdBy;
    }

    @JsonProperty("createdBy")
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Developer withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    @JsonProperty("lastModifiedAt")
    public Long getLastModifiedAt() {
        return lastModifiedAt;
    }

    @JsonProperty("lastModifiedAt")
    public void setLastModifiedAt(Long lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public Developer withLastModifiedAt(Long lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
        return this;
    }

    @JsonProperty("lastModifiedBy")
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    @JsonProperty("lastModifiedBy")
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Developer withLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
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

    public Developer withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Developer.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("apps");
        sb.append('=');
        sb.append(((this.apps == null)?"<null>":this.apps));
        sb.append(',');
        sb.append("companies");
        sb.append('=');
        sb.append(((this.companies == null)?"<null>":this.companies));
        sb.append(',');
        sb.append("email");
        sb.append('=');
        sb.append(((this.email == null)?"<null>":this.email));
        sb.append(',');
        sb.append("developerId");
        sb.append('=');
        sb.append(((this.developerId == null)?"<null>":this.developerId));
        sb.append(',');
        sb.append("firstName");
        sb.append('=');
        sb.append(((this.firstName == null)?"<null>":this.firstName));
        sb.append(',');
        sb.append("lastName");
        sb.append('=');
        sb.append(((this.lastName == null)?"<null>":this.lastName));
        sb.append(',');
        sb.append("userName");
        sb.append('=');
        sb.append(((this.userName == null)?"<null>":this.userName));
        sb.append(',');
        sb.append("organizationName");
        sb.append('=');
        sb.append(((this.organizationName == null)?"<null>":this.organizationName));
        sb.append(',');
        sb.append("status");
        sb.append('=');
        sb.append(((this.status == null)?"<null>":this.status));
        sb.append(',');
        sb.append("attributes");
        sb.append('=');
        sb.append(((this.attributes == null)?"<null>":this.attributes));
        sb.append(',');
        sb.append("createdAt");
        sb.append('=');
        sb.append(((this.createdAt == null)?"<null>":this.createdAt));
        sb.append(',');
        sb.append("createdBy");
        sb.append('=');
        sb.append(((this.createdBy == null)?"<null>":this.createdBy));
        sb.append(',');
        sb.append("lastModifiedAt");
        sb.append('=');
        sb.append(((this.lastModifiedAt == null)?"<null>":this.lastModifiedAt));
        sb.append(',');
        sb.append("lastModifiedBy");
        sb.append('=');
        sb.append(((this.lastModifiedBy == null)?"<null>":this.lastModifiedBy));
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
        result = ((result* 31)+((this.lastName == null)? 0 :this.lastName.hashCode()));
        result = ((result* 31)+((this.lastModifiedAt == null)? 0 :this.lastModifiedAt.hashCode()));
        result = ((result* 31)+((this.organizationName == null)? 0 :this.organizationName.hashCode()));
        result = ((result* 31)+((this.lastModifiedBy == null)? 0 :this.lastModifiedBy.hashCode()));
        result = ((result* 31)+((this.userName == null)? 0 :this.userName.hashCode()));
        result = ((result* 31)+((this.firstName == null)? 0 :this.firstName.hashCode()));
        result = ((result* 31)+((this.createdAt == null)? 0 :this.createdAt.hashCode()));
        result = ((result* 31)+((this.companies == null)? 0 :this.companies.hashCode()));
        result = ((result* 31)+((this.developerId == null)? 0 :this.developerId.hashCode()));
        result = ((result* 31)+((this.createdBy == null)? 0 :this.createdBy.hashCode()));
        result = ((result* 31)+((this.attributes == null)? 0 :this.attributes.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.email == null)? 0 :this.email.hashCode()));
        result = ((result* 31)+((this.apps == null)? 0 :this.apps.hashCode()));
        result = ((result* 31)+((this.status == null)? 0 :this.status.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Developer) == false) {
            return false;
        }
        Developer rhs = ((Developer) other);
        return ((((((((((((((((this.lastName == rhs.lastName)||((this.lastName!= null)&&this.lastName.equals(rhs.lastName)))&&((this.lastModifiedAt == rhs.lastModifiedAt)||((this.lastModifiedAt!= null)&&this.lastModifiedAt.equals(rhs.lastModifiedAt))))&&((this.organizationName == rhs.organizationName)||((this.organizationName!= null)&&this.organizationName.equals(rhs.organizationName))))&&((this.lastModifiedBy == rhs.lastModifiedBy)||((this.lastModifiedBy!= null)&&this.lastModifiedBy.equals(rhs.lastModifiedBy))))&&((this.userName == rhs.userName)||((this.userName!= null)&&this.userName.equals(rhs.userName))))&&((this.firstName == rhs.firstName)||((this.firstName!= null)&&this.firstName.equals(rhs.firstName))))&&((this.createdAt == rhs.createdAt)||((this.createdAt!= null)&&this.createdAt.equals(rhs.createdAt))))&&((this.companies == rhs.companies)||((this.companies!= null)&&this.companies.equals(rhs.companies))))&&((this.developerId == rhs.developerId)||((this.developerId!= null)&&this.developerId.equals(rhs.developerId))))&&((this.createdBy == rhs.createdBy)||((this.createdBy!= null)&&this.createdBy.equals(rhs.createdBy))))&&((this.attributes == rhs.attributes)||((this.attributes!= null)&&this.attributes.equals(rhs.attributes))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.email == rhs.email)||((this.email!= null)&&this.email.equals(rhs.email))))&&((this.apps == rhs.apps)||((this.apps!= null)&&this.apps.equals(rhs.apps))))&&((this.status == rhs.status)||((this.status!= null)&&this.status.equals(rhs.status))));
    }

}
