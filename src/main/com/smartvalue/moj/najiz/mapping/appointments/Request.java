package com.smartvalue.moj.najiz.mapping.appointments;

public class Request extends com.smartvalue.moj.najiz.mapping.appointments.auto.Request {
	private String serviceId;
	private String date ; 

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	} 

}
