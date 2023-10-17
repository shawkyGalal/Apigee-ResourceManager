
package com.smartvalue.moj.najiz.services.appointments.auto;

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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "appointments",
    "requests"
})
@Generated("jsonschema2pojo")
public class Appointments {

    @JsonProperty("appointments")
    private List<Appointment> appointments = new ArrayList<Appointment>();
    @JsonProperty("requests")
    private List<Request> requests = new ArrayList<Request>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("appointments")
    public List<Appointment> getAppointments() {
        return appointments;
    }

    @JsonProperty("appointments")
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Appointments withAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
        return this;
    }

    @JsonProperty("requests")
    public List<Request> getRequests() {
        return requests;
    }

    @JsonProperty("requests")
    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public Appointments withRequests(List<Request> requests) {
        this.requests = requests;
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

    public Appointments withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Appointments.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("appointments");
        sb.append('=');
        sb.append(((this.appointments == null)?"<null>":this.appointments));
        sb.append(',');
        sb.append("requests");
        sb.append('=');
        sb.append(((this.requests == null)?"<null>":this.requests));
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
        result = ((result* 31)+((this.appointments == null)? 0 :this.appointments.hashCode()));
        result = ((result* 31)+((this.requests == null)? 0 :this.requests.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Appointments) == false) {
            return false;
        }
        Appointments rhs = ((Appointments) other);
        return ((((this.appointments == rhs.appointments)||((this.appointments!= null)&&this.appointments.equals(rhs.appointments)))&&((this.requests == rhs.requests)||((this.requests!= null)&&this.requests.equals(rhs.requests))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))));
    }

}
