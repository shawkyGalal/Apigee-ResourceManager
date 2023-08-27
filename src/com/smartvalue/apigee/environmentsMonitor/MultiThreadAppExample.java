package com.smartvalue.apigee.environmentsMonitor;

public class MultiThreadAppExample {
		    public static void main(String[] args) {
	        Thread thread1 = new Thread(() -> {
	            while (true) {
	                System.out.println("Thread 1 is running...");
	                try {
	                    Thread.sleep(1000); // Sleep for 1 second
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            }
	        });

	        Thread thread2 = new Thread(() -> {
	            while (true) {
	                System.out.println("Thread 2 is running...");
	                try {
	                    Thread.sleep(1500); // Sleep for 1.5 seconds
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            }
	        });

	        thread1.start();
	        thread2.start();
	    }
	
}
