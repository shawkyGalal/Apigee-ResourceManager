package com.smartvalue.swagger.v3.parser.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry ; 

public class FifoMap<K, V> extends LinkedHashMap<K, V> {

    private final List<K> queue = new ArrayList<>();

   
    @Override
    public V put(K key, V value) {
        if (containsKey(key)) {
            queue.remove(key);
        }
        queue.add(key);
        return super.put(key, value);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        List<Entry<K, V>> entries = new ArrayList<>(super.entrySet());
        entries.sort((e1, e2) -> queue.indexOf(e1.getKey()) - queue.indexOf(e2.getKey()));
        return new LinkedHashSet<>(entries);

    }
    
    
    public  static void main (String[] args )
    {
    	FifoMap<String, Object> fifoMap = new FifoMap<>();
    	fifoMap.put("key1", "value1");
    	fifoMap.put("key3", "value3");
    	fifoMap.put("key2", "value2");
    	
    	
    	int size = fifoMap.entrySet().size() ; 
    	// Iterate over elements in FIFO order
    	for (Map.Entry<String, Object> entry : fifoMap.entrySet()) {
    	    System.out.println(entry.getKey() + ": " + entry.getValue());
    	}
    }
}
