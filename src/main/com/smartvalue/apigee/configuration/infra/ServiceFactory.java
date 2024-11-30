package com.smartvalue.apigee.configuration.infra;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.smartvalue.apigee.rest.schema.ApigeeService;
import com.smartvalue.apigee.rest.schema.EnvironmentScopeService;

public class ServiceFactory {

	 public static <T extends ApigeeService> T  createBundleServiceInstance(Class<? extends ApigeeService> bundledObjectClass , ManagementServer ms , String envName  ) 
	  {
	        try {
	            Constructor<?> constructor = bundledObjectClass.getConstructor(ms.getClass() );
	            ApigeeService result =  (ApigeeService) constructor.newInstance(ms);  
	              
	            if (result instanceof EnvironmentScopeService)
	    		{
	    			((EnvironmentScopeService)result).setEnvName(envName);
	    		}
	            return (T) result ;
	        } catch ( NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
	            e.printStackTrace();
	            return null;
	        }
	  }
	 
}
