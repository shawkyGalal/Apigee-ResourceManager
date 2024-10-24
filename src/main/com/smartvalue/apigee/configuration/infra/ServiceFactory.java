package com.smartvalue.apigee.configuration.infra;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.smartvalue.apigee.rest.schema.ApigeeService;

public class ServiceFactory {

	 public static <T extends ApigeeService> T  createBundleServiceInstance(Class<? extends ApigeeService> bundledObjectClass , ManagementServer ms   ) 
	  {
	        try {
	            Constructor<?> constructor = bundledObjectClass.getConstructor(ms.getClass() );
	            return (T) constructor.newInstance(ms);
	        } catch ( NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
	            e.printStackTrace();
	            return null;
	        }
	  }
	 
}
