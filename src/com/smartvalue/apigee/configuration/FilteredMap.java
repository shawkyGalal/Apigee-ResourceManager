package com.smartvalue.apigee.configuration;

import java.util.HashMap;
import java.util.Map;

class FilteredMap<K, V> extends HashMap<K, V> implements FilterMap<K, V> {
    @Override
    public FilteredMap<K, V> filter(MapFilter<K, V> filter) {
    	FilteredMap<K, V> filteredMap = new FilteredMap<>();
        for (Map.Entry<K, V> entry : entrySet()) {
            if (filter.apply(entry)) {
                filteredMap.put(entry.getKey(), entry.getValue());
            }
        }
        return filteredMap;
    }
    
    public static void main(String[] args )
    {
    	FilteredMap<String, Integer> filterMap = new FilteredMap<>();
        filterMap.put("A", 10);
        filterMap.put("B", 20);
        filterMap.put("C", 30);
        filterMap.put("D", 40);
        // Define a custom filter implementation
        MapFilter<String, Integer> customFilter = new MapFilter<String, Integer>() {
			@Override
			public boolean apply(Entry<String, Integer> entry) {
				return entry.getValue() > 25;
			}
		};

        // Filter the map using the custom filter
        FilteredMap<String, Integer> filteredMap = filterMap.filter(customFilter);

        // Print the filtered map
        System.out.println("Filtered Map: " + filteredMap);
    }
    
}
