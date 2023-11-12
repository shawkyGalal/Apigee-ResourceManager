package com.smartvalue.apigee.configuration;

import java.util.HashMap;

public interface FilterMap<K, V> {
	
	 HashMap<K, V> filter(MapFilter<K, V> filter);
}
