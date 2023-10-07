package com.smartvalue.apigee.environmentsMonitor;

import java.util.List;
import com.smartvalue.apigee.rest.schema.environment.Environment;
import com.smartvalue.apigee.rest.schema.server.MPServer;

public class MonitoringEnvThread extends Thread {
	
	private Environment env ; 
	private String region = "dc-1" ; 
	private int expectedMPCount ;
	final int STOP = 0 ; 
	private int status = 1 ;    
	
	 public void run(){
		 try { 
		 	
			
		     while( status ==1 )
		     {
		    	 System.out.println("=== Processing Org/Environment :  " + this.env.getOrgName() +"/"+ this.env.getName() + "========");
		    	 sleep(5000);
		    	 List<MPServer> currentEnvMPs= env.getMessageProcesors(region) ; 
		    	 List<MPServer> freeMps ;
		    	 for ( MPServer mp : currentEnvMPs )
		    	 {
		    		 boolean mpIsActive = mp.healthCheck() ;
		    		 
		    		 if (! mpIsActive)
		    		 {
		    			 sleep(5000);
		    			 mpIsActive = mp.healthCheck() ;
		    			 //=== Replace Failed MP if found =====
		    			 if (! mpIsActive)
		    			 {
		    				 System.out.println("MP " + mp.getInternalIP() + "Allocated To Env : " + this.env.getName() + " Currerntly is unreachable " ) ;
		    				 System.out.println("System Will Try To replace with any avaliable Free MP " ) ;
		    				 freeMps  = env.getMs().getFreeMps(region);
			    			 if ( freeMps.size() > 0 )
			    			 {
			    				 System.out.println("=== Remove the affected MP :  " + mp.getInternalIP() + "From Environmernt : " + this.env.getName() + "========");
			    				 mp.removeFromEnvironmnt( env) ;
			    				 System.out.println("=== Add MP :  " + freeMps.get(0).getInternalIP() + "From Environmernt : " + this.env.getName() + "========");
			    				 freeMps.get(0).addToEnvironmnt(env) ;
			    			 }
			    			 else 
			    			 {	 
			    				 System.out.println("No Free MPs available For Environment " + this.env.getName() ) ; 
			    			 }
		    			 }
		    			
		    			 
		    		 }
		    		
		    	 }
		    	 
		    	//=== Add Extra MP from freeMps to the environment if needed  =====
    			 if (currentEnvMPs.size() < this.getExpectedMPCount())
    			 {
    				 freeMps  = env.getMs().getFreeMps(region);
    				 if ( freeMps.size() > 0 )
	    			 {
    					 System.out.println("=== Add MP :  " + freeMps.get(0).getInternalIP() + "From Environmernt : " + this.env.getName() + "========");
	    				 freeMps.get(0).addToEnvironmnt(env) ;
	    			 }
    				 else 
	    			 {
    					 System.out.println("No Free MPs available For Environment " + this.env.getName()+ ". Expected Size = " + this.getExpectedMPCount() + ",  Current Size = " + currentEnvMPs.size() ) ; 
	    			 }
    			 }
		       
		     }
		 }
	     
	     catch ( Exception e) {
	    	System.out.println("Unable to Start Monitoring Environment due to " + e.getMessage()); 
	     }
	   }
	

	public int getExpectedMPCount() {
		return expectedMPCount;
	}

	public void setExpectedMPCount(int expectedMPCount) {
		this.expectedMPCount = expectedMPCount;
	}

	public void setEnv(Environment environment) {
		this.env = environment ;
		
	}
	public void stopThread() {
        status = STOP; 
    }
	
	public int getStatus()
	{
		return status; 
	}

	

}
