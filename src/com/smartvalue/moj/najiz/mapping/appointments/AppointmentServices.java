package com.smartvalue.moj.najiz.mapping.appointments;

import java.util.List;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.moj.clients.environments.AccessTokenNotFound;
import com.smartvalue.moj.clients.environments.Environment;

public class AppointmentServices extends com.smartvalue.moj.najiz.NajizService{

	private String serviceBasePath = "/v1/self-services/appointment-mobile" ;   
	
	public AppointmentServices(Environment environment) {
		super(environment);
	}
	
	private Appointments  getMyAppintments() throws UnirestException, AccessTokenNotFound
	{
		Appointments result ; 
		Gson gson = new Gson();
		HttpResponse<String> serviceResponse = this.getMyAppintmentsResponse(); 
		result = gson.fromJson(serviceResponse.getBody() , Appointments.class) ;
		return result ; 
	}
	
	public List<com.smartvalue.moj.najiz.mapping.appointments.auto.Request> getMyRequests() throws UnirestException, AccessTokenNotFound
	{
		return this.getMyAppintments().getRequests() ; 
	}

	public List<com.smartvalue.moj.najiz.mapping.appointments.auto.Appointment> getMyAppontments() throws UnirestException, AccessTokenNotFound
	{
		return this.getMyAppintments().getAppointments() ; 
	}

	
	public HttpResponse<String>  getMyAppintmentsResponse() throws UnirestException, AccessTokenNotFound
	{
		
		String serviceSuffix = "/api/people/$userId/appointments?includeRequests=True" ; 
		String serviceUrl = this.getEnvironment().getMojServicesBaseUrl() + serviceBasePath + serviceSuffix ; 
		HttpResponse<String> serviceResponse = this.getEnvironment().executeRequest( serviceUrl , null, "GET", "") ; 
	
		return serviceResponse ; 
	}
	
	public HttpResponse<String>  getMyAppintmentsCount() throws UnirestException, AccessTokenNotFound
	{
		String serviceSuffix = "/api/people/xxxxx/appointments-count" ; //  service will automatically replace xxxx with the logged in user id from the accesstoken  
		String serviceUrl = this.getEnvironment().getMojServicesBaseUrl() + serviceBasePath + serviceSuffix ; 
		HttpResponse<String> serviceResponse = this.getEnvironment().executeRequest( serviceUrl , null, "GET", "") ; 
	
		return serviceResponse ; 
	}
	
	public HttpResponse<String> deleteRequest(String protectedId) throws UnirestException, AccessTokenNotFound
	{
		HttpResponse<String> result ; 
		
		
		String serviceSuffix = "/api/people/xxxxxxx/appointments/requests/"+protectedId ; //  service will automatically replace xxxx with the logged in user id from the accesstoken  
		String serviceUrl = this.getEnvironment().getMojServicesBaseUrl() + serviceBasePath + serviceSuffix ; 
		result = this.getEnvironment().executeRequest( serviceUrl , null, "POST", "") ; 
		return result ; 
	}
	
	public Request createRequest() throws UnirestException, AccessTokenNotFound
	{
		Request result = null ; 
		//-- TODO --
		return  result; 
	}
	
	public Request updateRequest() throws UnirestException, AccessTokenNotFound
	{
		Request result = null ; 
		//-- TODO --
		return  result;
	}
	
	public HttpResponse<String> getRegions() throws UnirestException, AccessTokenNotFound
	{
		HttpResponse<String> result ; 
		//String serviceBasePath = "/v1/appointment-mobile" ;  
		String serviceSuffix = "/regions" ; 
		String serviceUrl = this.getEnvironment().getMojServicesBaseUrl() + lookupBasePath + serviceSuffix ; 
		result = this.getEnvironment().executeRequest( serviceUrl , null, "GET", "") ; 
		return result ; 
	}
	
	private final String lookupBasePath = "/v1/appointment-mobile/api/lookups" ; 
	public HttpResponse<String> getSites(int regionId) throws UnirestException, AccessTokenNotFound
	{
		HttpResponse<String> result ; 
		String serviceSuffix = "/regions/"+regionId+"/sites" ;  
		String serviceUrl = this.getEnvironment().getMojServicesBaseUrl() + lookupBasePath + serviceSuffix ; 
		result = this.getEnvironment().executeRequest( serviceUrl , null, "GET", "") ; 
		return result ; 
	}
	
	public HttpResponse<String> getDepartments(int siteId) throws UnirestException, AccessTokenNotFound
	{
		HttpResponse<String> result ; 
		
		String serviceSuffix = "/sites/"+siteId+"/departments" ;  
		String serviceUrl = this.getEnvironment().getMojServicesBaseUrl() + lookupBasePath + serviceSuffix ; 
		result = this.getEnvironment().executeRequest( serviceUrl , null, "GET", "") ; 
		return result ; 
	}
	
	public HttpResponse<String> getServices(int deptId) throws UnirestException, AccessTokenNotFound
	{
		HttpResponse<String> result ; 
		String serviceSuffix = "/departments/"+deptId+"/services" ;
		String serviceUrl = this.getEnvironment().getMojServicesBaseUrl() + lookupBasePath + serviceSuffix ; 
		result = this.getEnvironment().executeRequest( serviceUrl , null, "GET", "") ; 
		return result ; 
	}

	public HttpResponse<String> getAvailableAppointments(int deptId , int serviceId ) throws UnirestException, AccessTokenNotFound
	{
		HttpResponse<String> result ; 
		String serviceSuffix = "/api/calendar/"+deptId+"/"+serviceId+"/xxxxxx" ;  //  service will automatically replace xxxx with the logged in user id from the accesstoken 
		String serviceUrl = this.getEnvironment().getMojServicesBaseUrl() + serviceBasePath + serviceSuffix ; 
		result = this.getEnvironment().executeRequest( serviceUrl , null, "GET", "") ; 
		return result ; 
	}
	
	

}
