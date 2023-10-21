
package com.smartvalue.moj.najiz.mapping.appointments.auto;

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
    "siteName",
    "endTime",
    "serviceName",
    "categoryId",
    "id",
    "startTime",
    "departmentId",
    "protectedId",
    "externalId",
    "departmentName",
    "date",
    "siteId",
    "regionName",
    "reservationDateTime",
    "regionId",
    "slotId"
})
@Generated("jsonschema2pojo")
public class Appointment {

    @JsonProperty("siteName")
    private String siteName;
    @JsonProperty("endTime")
    private String endTime;
    @JsonProperty("serviceName")
    private String serviceName;
    @JsonProperty("categoryId")
    private Integer categoryId;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("startTime")
    private String startTime;
    @JsonProperty("departmentId")
    private Object departmentId;
    @JsonProperty("protectedId")
    private String protectedId;
    @JsonProperty("externalId")
    private String externalId;
    @JsonProperty("departmentName")
    private String departmentName;
    @JsonProperty("date")
    private String date;
    @JsonProperty("siteId")
    private Object siteId;
    @JsonProperty("regionName")
    private String regionName;
    @JsonProperty("reservationDateTime")
    private String reservationDateTime;
    @JsonProperty("regionId")
    private Object regionId;
    @JsonProperty("slotId")
    private Integer slotId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("siteName")
    public String getSiteName() {
        return siteName;
    }

    @JsonProperty("siteName")
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Appointment withSiteName(String siteName) {
        this.siteName = siteName;
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

    public Appointment withEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    @JsonProperty("serviceName")
    public String getServiceName() {
        return serviceName;
    }

    @JsonProperty("serviceName")
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Appointment withServiceName(String serviceName) {
        this.serviceName = serviceName;
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

    public Appointment withCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    public Appointment withId(Integer id) {
        this.id = id;
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

    public Appointment withStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    @JsonProperty("departmentId")
    public Object getDepartmentId() {
        return departmentId;
    }

    @JsonProperty("departmentId")
    public void setDepartmentId(Object departmentId) {
        this.departmentId = departmentId;
    }

    public Appointment withDepartmentId(Object departmentId) {
        this.departmentId = departmentId;
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

    public Appointment withProtectedId(String protectedId) {
        this.protectedId = protectedId;
        return this;
    }

    @JsonProperty("externalId")
    public String getExternalId() {
        return externalId;
    }

    @JsonProperty("externalId")
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Appointment withExternalId(String externalId) {
        this.externalId = externalId;
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

    public Appointment withDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public Appointment withDate(String date) {
        this.date = date;
        return this;
    }

    @JsonProperty("siteId")
    public Object getSiteId() {
        return siteId;
    }

    @JsonProperty("siteId")
    public void setSiteId(Object siteId) {
        this.siteId = siteId;
    }

    public Appointment withSiteId(Object siteId) {
        this.siteId = siteId;
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

    public Appointment withRegionName(String regionName) {
        this.regionName = regionName;
        return this;
    }

    @JsonProperty("reservationDateTime")
    public String getReservationDateTime() {
        return reservationDateTime;
    }

    @JsonProperty("reservationDateTime")
    public void setReservationDateTime(String reservationDateTime) {
        this.reservationDateTime = reservationDateTime;
    }

    public Appointment withReservationDateTime(String reservationDateTime) {
        this.reservationDateTime = reservationDateTime;
        return this;
    }

    @JsonProperty("regionId")
    public Object getRegionId() {
        return regionId;
    }

    @JsonProperty("regionId")
    public void setRegionId(Object regionId) {
        this.regionId = regionId;
    }

    public Appointment withRegionId(Object regionId) {
        this.regionId = regionId;
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

    public Appointment withSlotId(Integer slotId) {
        this.slotId = slotId;
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

    public Appointment withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Appointment.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("siteName");
        sb.append('=');
        sb.append(((this.siteName == null)?"<null>":this.siteName));
        sb.append(',');
        sb.append("endTime");
        sb.append('=');
        sb.append(((this.endTime == null)?"<null>":this.endTime));
        sb.append(',');
        sb.append("serviceName");
        sb.append('=');
        sb.append(((this.serviceName == null)?"<null>":this.serviceName));
        sb.append(',');
        sb.append("categoryId");
        sb.append('=');
        sb.append(((this.categoryId == null)?"<null>":this.categoryId));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("startTime");
        sb.append('=');
        sb.append(((this.startTime == null)?"<null>":this.startTime));
        sb.append(',');
        sb.append("departmentId");
        sb.append('=');
        sb.append(((this.departmentId == null)?"<null>":this.departmentId));
        sb.append(',');
        sb.append("protectedId");
        sb.append('=');
        sb.append(((this.protectedId == null)?"<null>":this.protectedId));
        sb.append(',');
        sb.append("externalId");
        sb.append('=');
        sb.append(((this.externalId == null)?"<null>":this.externalId));
        sb.append(',');
        sb.append("departmentName");
        sb.append('=');
        sb.append(((this.departmentName == null)?"<null>":this.departmentName));
        sb.append(',');
        sb.append("date");
        sb.append('=');
        sb.append(((this.date == null)?"<null>":this.date));
        sb.append(',');
        sb.append("siteId");
        sb.append('=');
        sb.append(((this.siteId == null)?"<null>":this.siteId));
        sb.append(',');
        sb.append("regionName");
        sb.append('=');
        sb.append(((this.regionName == null)?"<null>":this.regionName));
        sb.append(',');
        sb.append("reservationDateTime");
        sb.append('=');
        sb.append(((this.reservationDateTime == null)?"<null>":this.reservationDateTime));
        sb.append(',');
        sb.append("regionId");
        sb.append('=');
        sb.append(((this.regionId == null)?"<null>":this.regionId));
        sb.append(',');
        sb.append("slotId");
        sb.append('=');
        sb.append(((this.slotId == null)?"<null>":this.slotId));
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
        result = ((result* 31)+((this.departmentId == null)? 0 :this.departmentId.hashCode()));
        result = ((result* 31)+((this.protectedId == null)? 0 :this.protectedId.hashCode()));
        result = ((result* 31)+((this.regionName == null)? 0 :this.regionName.hashCode()));
        result = ((result* 31)+((this.reservationDateTime == null)? 0 :this.reservationDateTime.hashCode()));
        result = ((result* 31)+((this.siteName == null)? 0 :this.siteName.hashCode()));
        result = ((result* 31)+((this.externalId == null)? 0 :this.externalId.hashCode()));
        result = ((result* 31)+((this.serviceName == null)? 0 :this.serviceName.hashCode()));
        result = ((result* 31)+((this.regionId == null)? 0 :this.regionId.hashCode()));
        result = ((result* 31)+((this.siteId == null)? 0 :this.siteId.hashCode()));
        result = ((result* 31)+((this.startTime == null)? 0 :this.startTime.hashCode()));
        result = ((result* 31)+((this.slotId == null)? 0 :this.slotId.hashCode()));
        result = ((result* 31)+((this.endTime == null)? 0 :this.endTime.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.categoryId == null)? 0 :this.categoryId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Appointment) == false) {
            return false;
        }
        Appointment rhs = ((Appointment) other);
        return ((((((((((((((((((this.departmentName == rhs.departmentName)||((this.departmentName!= null)&&this.departmentName.equals(rhs.departmentName)))&&((this.date == rhs.date)||((this.date!= null)&&this.date.equals(rhs.date))))&&((this.departmentId == rhs.departmentId)||((this.departmentId!= null)&&this.departmentId.equals(rhs.departmentId))))&&((this.protectedId == rhs.protectedId)||((this.protectedId!= null)&&this.protectedId.equals(rhs.protectedId))))&&((this.regionName == rhs.regionName)||((this.regionName!= null)&&this.regionName.equals(rhs.regionName))))&&((this.reservationDateTime == rhs.reservationDateTime)||((this.reservationDateTime!= null)&&this.reservationDateTime.equals(rhs.reservationDateTime))))&&((this.siteName == rhs.siteName)||((this.siteName!= null)&&this.siteName.equals(rhs.siteName))))&&((this.externalId == rhs.externalId)||((this.externalId!= null)&&this.externalId.equals(rhs.externalId))))&&((this.serviceName == rhs.serviceName)||((this.serviceName!= null)&&this.serviceName.equals(rhs.serviceName))))&&((this.regionId == rhs.regionId)||((this.regionId!= null)&&this.regionId.equals(rhs.regionId))))&&((this.siteId == rhs.siteId)||((this.siteId!= null)&&this.siteId.equals(rhs.siteId))))&&((this.startTime == rhs.startTime)||((this.startTime!= null)&&this.startTime.equals(rhs.startTime))))&&((this.slotId == rhs.slotId)||((this.slotId!= null)&&this.slotId.equals(rhs.slotId))))&&((this.endTime == rhs.endTime)||((this.endTime!= null)&&this.endTime.equals(rhs.endTime))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.categoryId == rhs.categoryId)||((this.categoryId!= null)&&this.categoryId.equals(rhs.categoryId))));
    }

}
