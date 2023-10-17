
package com.smartvalue.moj.najiz.services.appointments.auto;

import java.util.HashMap;
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
    "id",
    "protectedId",
    "creationDateTime",
    "regionName",
    "siteName",
    "departmentName",
    "subject",
    "statusId",
    "processingDateTime",
    "rejectionReasons",
    "categoryId",
    "slotId",
    "date",
    "startTime",
    "endTime"
})
@Generated("jsonschema2pojo")
public class Request {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("protectedId")
    private String protectedId;
    @JsonProperty("creationDateTime")
    private String creationDateTime;
    @JsonProperty("regionName")
    private String regionName;
    @JsonProperty("siteName")
    private String siteName;
    @JsonProperty("departmentName")
    private String departmentName;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("statusId")
    private Integer statusId;
    @JsonProperty("processingDateTime")
    private String processingDateTime;
    @JsonProperty("rejectionReasons")
    private String rejectionReasons;
    @JsonProperty("categoryId")
    private Integer categoryId;
    @JsonProperty("slotId")
    private Integer slotId;
    @JsonProperty("date")
    private String date;
    @JsonProperty("startTime")
    private String startTime;
    @JsonProperty("endTime")
    private String endTime;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    public Request withId(Integer id) {
        this.id = id;
        return this;
    }

    @JsonProperty("protectedId")
    public String getProtectedId() {
        return protectedId;
    }

    @JsonProperty("protectedId")
    public void setProtectedId(String protectedId) {
        this.protectedId = protectedId;
    }

    public Request withProtectedId(String protectedId) {
        this.protectedId = protectedId;
        return this;
    }

    @JsonProperty("creationDateTime")
    public String getCreationDateTime() {
        return creationDateTime;
    }

    @JsonProperty("creationDateTime")
    public void setCreationDateTime(String creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public Request withCreationDateTime(String creationDateTime) {
        this.creationDateTime = creationDateTime;
        return this;
    }

    @JsonProperty("regionName")
    public String getRegionName() {
        return regionName;
    }

    @JsonProperty("regionName")
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Request withRegionName(String regionName) {
        this.regionName = regionName;
        return this;
    }

    @JsonProperty("siteName")
    public String getSiteName() {
        return siteName;
    }

    @JsonProperty("siteName")
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Request withSiteName(String siteName) {
        this.siteName = siteName;
        return this;
    }

    @JsonProperty("departmentName")
    public String getDepartmentName() {
        return departmentName;
    }

    @JsonProperty("departmentName")
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Request withDepartmentName(String departmentName) {
        this.departmentName = departmentName;
        return this;
    }

    @JsonProperty("subject")
    public String getSubject() {
        return subject;
    }

    @JsonProperty("subject")
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Request withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    @JsonProperty("statusId")
    public Integer getStatusId() {
        return statusId;
    }

    @JsonProperty("statusId")
    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Request withStatusId(Integer statusId) {
        this.statusId = statusId;
        return this;
    }

    @JsonProperty("processingDateTime")
    public String getProcessingDateTime() {
        return processingDateTime;
    }

    @JsonProperty("processingDateTime")
    public void setProcessingDateTime(String processingDateTime) {
        this.processingDateTime = processingDateTime;
    }

    public Request withProcessingDateTime(String processingDateTime) {
        this.processingDateTime = processingDateTime;
        return this;
    }

    @JsonProperty("rejectionReasons")
    public String getRejectionReasons() {
        return rejectionReasons;
    }

    @JsonProperty("rejectionReasons")
    public void setRejectionReasons(String rejectionReasons) {
        this.rejectionReasons = rejectionReasons;
    }

    public Request withRejectionReasons(String rejectionReasons) {
        this.rejectionReasons = rejectionReasons;
        return this;
    }

    @JsonProperty("categoryId")
    public Integer getCategoryId() {
        return categoryId;
    }

    @JsonProperty("categoryId")
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Request withCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    @JsonProperty("slotId")
    public Integer getSlotId() {
        return slotId;
    }

    @JsonProperty("slotId")
    public void setSlotId(Integer slotId) {
        this.slotId = slotId;
    }

    public Request withSlotId(Integer slotId) {
        this.slotId = slotId;
        return this;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    public Request withDate(String date) {
        this.date = date;
        return this;
    }

    @JsonProperty("startTime")
    public String getStartTime() {
        return startTime;
    }

    @JsonProperty("startTime")
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Request withStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    @JsonProperty("endTime")
    public String getEndTime() {
        return endTime;
    }

    @JsonProperty("endTime")
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Request withEndTime(String endTime) {
        this.endTime = endTime;
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

    public Request withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Request.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("protectedId");
        sb.append('=');
        sb.append(((this.protectedId == null)?"<null>":this.protectedId));
        sb.append(',');
        sb.append("creationDateTime");
        sb.append('=');
        sb.append(((this.creationDateTime == null)?"<null>":this.creationDateTime));
        sb.append(',');
        sb.append("regionName");
        sb.append('=');
        sb.append(((this.regionName == null)?"<null>":this.regionName));
        sb.append(',');
        sb.append("siteName");
        sb.append('=');
        sb.append(((this.siteName == null)?"<null>":this.siteName));
        sb.append(',');
        sb.append("departmentName");
        sb.append('=');
        sb.append(((this.departmentName == null)?"<null>":this.departmentName));
        sb.append(',');
        sb.append("subject");
        sb.append('=');
        sb.append(((this.subject == null)?"<null>":this.subject));
        sb.append(',');
        sb.append("statusId");
        sb.append('=');
        sb.append(((this.statusId == null)?"<null>":this.statusId));
        sb.append(',');
        sb.append("processingDateTime");
        sb.append('=');
        sb.append(((this.processingDateTime == null)?"<null>":this.processingDateTime));
        sb.append(',');
        sb.append("rejectionReasons");
        sb.append('=');
        sb.append(((this.rejectionReasons == null)?"<null>":this.rejectionReasons));
        sb.append(',');
        sb.append("categoryId");
        sb.append('=');
        sb.append(((this.categoryId == null)?"<null>":this.categoryId));
        sb.append(',');
        sb.append("slotId");
        sb.append('=');
        sb.append(((this.slotId == null)?"<null>":this.slotId));
        sb.append(',');
        sb.append("date");
        sb.append('=');
        sb.append(((this.date == null)?"<null>":this.date));
        sb.append(',');
        sb.append("startTime");
        sb.append('=');
        sb.append(((this.startTime == null)?"<null>":this.startTime));
        sb.append(',');
        sb.append("endTime");
        sb.append('=');
        sb.append(((this.endTime == null)?"<null>":this.endTime));
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
        result = ((result* 31)+((this.departmentName == null)? 0 :this.departmentName.hashCode()));
        result = ((result* 31)+((this.date == null)? 0 :this.date.hashCode()));
        result = ((result* 31)+((this.subject == null)? 0 :this.subject.hashCode()));
        result = ((result* 31)+((this.protectedId == null)? 0 :this.protectedId.hashCode()));
        result = ((result* 31)+((this.regionName == null)? 0 :this.regionName.hashCode()));
        result = ((result* 31)+((this.siteName == null)? 0 :this.siteName.hashCode()));
        result = ((result* 31)+((this.statusId == null)? 0 :this.statusId.hashCode()));
        result = ((result* 31)+((this.rejectionReasons == null)? 0 :this.rejectionReasons.hashCode()));
        result = ((result* 31)+((this.processingDateTime == null)? 0 :this.processingDateTime.hashCode()));
        result = ((result* 31)+((this.slotId == null)? 0 :this.slotId.hashCode()));
        result = ((result* 31)+((this.startTime == null)? 0 :this.startTime.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.endTime == null)? 0 :this.endTime.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.categoryId == null)? 0 :this.categoryId.hashCode()));
        result = ((result* 31)+((this.creationDateTime == null)? 0 :this.creationDateTime.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Request) == false) {
            return false;
        }
        Request rhs = ((Request) other);
        return (((((((((((((((((this.departmentName == rhs.departmentName)||((this.departmentName!= null)&&this.departmentName.equals(rhs.departmentName)))&&((this.date == rhs.date)||((this.date!= null)&&this.date.equals(rhs.date))))&&((this.subject == rhs.subject)||((this.subject!= null)&&this.subject.equals(rhs.subject))))&&((this.protectedId == rhs.protectedId)||((this.protectedId!= null)&&this.protectedId.equals(rhs.protectedId))))&&((this.regionName == rhs.regionName)||((this.regionName!= null)&&this.regionName.equals(rhs.regionName))))&&((this.siteName == rhs.siteName)||((this.siteName!= null)&&this.siteName.equals(rhs.siteName))))&&((this.statusId == rhs.statusId)||((this.statusId!= null)&&this.statusId.equals(rhs.statusId))))&&((this.rejectionReasons == rhs.rejectionReasons)||((this.rejectionReasons!= null)&&this.rejectionReasons.equals(rhs.rejectionReasons))))&&((this.processingDateTime == rhs.processingDateTime)||((this.processingDateTime!= null)&&this.processingDateTime.equals(rhs.processingDateTime))))&&((this.slotId == rhs.slotId)||((this.slotId!= null)&&this.slotId.equals(rhs.slotId))))&&((this.startTime == rhs.startTime)||((this.startTime!= null)&&this.startTime.equals(rhs.startTime))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.endTime == rhs.endTime)||((this.endTime!= null)&&this.endTime.equals(rhs.endTime))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.categoryId == rhs.categoryId)||((this.categoryId!= null)&&this.categoryId.equals(rhs.categoryId))))&&((this.creationDateTime == rhs.creationDateTime)||((this.creationDateTime!= null)&&this.creationDateTime.equals(rhs.creationDateTime))));
    }

}
