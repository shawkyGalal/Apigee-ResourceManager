package com.smartvalue.apigee.rest.schema.sharedFlow.auto;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.rest.schema.ApigeeComman;

public abstract class RevisionedObject extends ApigeeComman {
	
	public abstract String getResourcePath(); 
	
	public void export(int revision , String folderDest) throws UnirestException, IOException
	{
		HttpResponse<InputStream> result = null; 
		String apiPath = getResourcePath()+"/revisions/"+revision+"?format=bundle" ; 
		ManagementServer ms = this.getManagmentServer() ; 
		result = ms.getGetHttpBinResponse(apiPath ) ;
		Files.copy(result.getBody(), Paths.get(folderDest + this.getName()+".zip") , java.nio.file.StandardCopyOption.REPLACE_EXISTING );
	}
	
	public abstract String getName(); 

}
