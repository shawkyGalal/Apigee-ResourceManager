package com.smartvalue.spring;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadStatusManager {

	private static final ConcurrentHashMap<String, Thread> threadStatusMap = new ConcurrentHashMap<>();

    public static Thread getThread(String theadUUID) {
		return threadStatusMap.get(theadUUID);
	}

	public static String startThread(Thread task, UUID uuid ) {
        String threadId = uuid.toString();
        threadStatusMap.put(threadId, task );
        task.start();
        return threadId;
    }
    
}
