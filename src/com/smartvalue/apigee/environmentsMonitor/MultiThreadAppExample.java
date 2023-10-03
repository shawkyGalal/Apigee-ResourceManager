package com.smartvalue.apigee.environmentsMonitor;



public class MultiThreadAppExample {
		    public static void main(String[] args) {
		    	MonitoringEnvThread met01 = new MonitoringEnvThread() ;
		    	met01.setEnvName("iam-protected");
		    	met01.setExpectedMPCount(2);
		    	met01.start() ; 
		    	
		    	MonitoringEnvThread met02 = new MonitoringEnvThread() ;
		    	met02.setEnvName("cert-protected");
		    	met02.setExpectedMPCount(2);
		    	met02.start() ;
	        
	    }
	
}
