package com.smartvalue.apigee.configuration;

import java.util.Map;

public interface MapFilter<K, V>  {
	boolean apply(Map.Entry<K, V> entry);
	
}
