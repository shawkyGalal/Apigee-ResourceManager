
package com.smartvalue.apigee.rest.cloud.OrganizationList.auto;

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
    "organization",
    "projectIds",
    "projectId"
})
@Generated("jsonschema2pojo")
public class Organization {

    @JsonProperty("organization")
    private String organization;
    @JsonProperty("projectIds")
    private List<String> projectIds = new ArrayList<String>();
    @JsonProperty("projectId")
    private String projectId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("organization")
    public String getOrganization() {
        return organization;
    }

    @JsonProperty("organization")
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Organization withOrganization(String organization) {
        this.organization = organization;
        return this;
    }

    @JsonProperty("projectIds")
    public List<String> getProjectIds() {
        return projectIds;
    }

    @JsonProperty("projectIds")
    public void setProjectIds(List<String> projectIds) {
        this.projectIds = projectIds;
    }

    public Organization withProjectIds(List<String> projectIds) {
        this.projectIds = projectIds;
        return this;
    }

    @JsonProperty("projectId")
    public String getProjectId() {
        return projectId;
    }

    @JsonProperty("projectId")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Organization withProjectId(String projectId) {
        this.projectId = projectId;
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

    public Organization withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Organization.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("organization");
        sb.append('=');
        sb.append(((this.organization == null)?"<null>":this.organization));
        sb.append(',');
        sb.append("projectIds");
        sb.append('=');
        sb.append(((this.projectIds == null)?"<null>":this.projectIds));
        sb.append(',');
        sb.append("projectId");
        sb.append('=');
        sb.append(((this.projectId == null)?"<null>":this.projectId));
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
        result = ((result* 31)+((this.projectIds == null)? 0 :this.projectIds.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.projectId == null)? 0 :this.projectId.hashCode()));
        result = ((result* 31)+((this.organization == null)? 0 :this.organization.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Organization) == false) {
            return false;
        }
        Organization rhs = ((Organization) other);
        return (((((this.projectIds == rhs.projectIds)||((this.projectIds!= null)&&this.projectIds.equals(rhs.projectIds)))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.projectId == rhs.projectId)||((this.projectId!= null)&&this.projectId.equals(rhs.projectId))))&&((this.organization == rhs.organization)||((this.organization!= null)&&this.organization.equals(rhs.organization))));
    }

}
