package com.smartvalue.apigee.rest.schema.server;

import java.io.IOException;
import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.resourceManager.helpers.Helper;

public  class Server extends com.smartvalue.apigee.rest.schema.server.auto.Server{

	public boolean healthCheck()
	{
		boolean result = false ; 
		HttpResponse<String> response ; 
		try {

			String propValue = Helper.getPropertyValueFromList(this.getTags().getProperty(), "name" , "http.management.port") ; 
			String healthCheckURL = "http://" + this.getExternalIP()+":" + propValue + "/v1/servers/self/up" ; 
			com.mashape.unirest.request.HttpRequest httpRequest = Unirest.get(healthCheckURL);
			response = httpRequest.header("Authorization", "No Need " ).asString();
		
			result =  Helper.isConsideredSuccess(response.getStatus()) ; 
		} 
		catch (UnirestException e) {
			e.printStackTrace();
		} 
		return result;
		
	}

	public  String getSimpleName() 
	{
		return "general purpose Server " ; 
	}
	
    public StringBuffer executeShell(String privateKeyPath , String[] commands) {
    	StringBuffer result = new StringBuffer(); 
        String host = this.getInternalIP();
        String user = "root";
        
       
        try {
            JSch jsch = new JSch();
            
            // Load your private key
            jsch.addIdentity(privateKeyPath);
            
            // Create an SSH session
            Session session = jsch.getSession(user, host);
            
            // Avoid asking for key confirmation
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            
            // Connect to the remote server
            session.connect();
            
            // Create an SSH channel
            Channel channel = session.openChannel("exec");
            // Concatenate the commands into a single string
            String joinedCommands = String.join(" && ", commands);
            
            ((ChannelExec) channel).setCommand(joinedCommands);
            
            // Set up input and output streams
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            
            InputStream in = channel.getInputStream();
            
            // Connect the channel
            channel.connect();
            
            // Read the command output
            byte[] buffer = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int bytesRead = in.read(buffer, 0, 1024);
                    if (bytesRead < 0) break;
                    result.append (new String(buffer, 0, bytesRead));
                }
                if (channel.isClosed()) {
                    if (in.available() > 0) continue;
                    result.append("Exit status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            // Disconnect the channel and session
            channel.disconnect();
            session.disconnect();
            
        } catch (JSchException | IOException e) {
            e.printStackTrace();
        }
        
        return result; 
    }

	  
}
