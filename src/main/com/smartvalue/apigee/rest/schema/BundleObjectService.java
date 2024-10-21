package com.smartvalue.apigee.rest.schema;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.migration.ProcessResult;
import com.smartvalue.apigee.migration.deploy.DeployResult;
import com.smartvalue.apigee.migration.deploy.DeployResults;
import com.smartvalue.apigee.migration.export.ExportResult;
import com.smartvalue.apigee.migration.export.ExportResults;
import com.smartvalue.apigee.migration.load.LoadResult;
import com.smartvalue.apigee.migration.load.LoadResults;
import com.smartvalue.apigee.migration.transformers.ApigeeObjectTransformer;
import com.smartvalue.apigee.migration.transformers.TransformResult;
import com.smartvalue.apigee.migration.transformers.TransformationResults;
import com.smartvalue.apigee.resourceManager.helpers.Helper;
import com.smartvalue.apigee.rest.schema.proxy.Proxy;
import com.smartvalue.apigee.rest.schema.proxy.ProxyServices;
import com.smartvalue.apigee.rest.schema.proxyDeployment.ProxyDeployment;
import com.smartvalue.apigee.rest.schema.proxyDeployment.auto.Environment;
import com.smartvalue.apigee.rest.schema.proxyDeployment.auto.Revision;
import com.smartvalue.apigee.rest.schema.proxyUploadResponse.ProxyUploadResponse;
import com.smartvalue.apigee.rest.schema.sharedFlow.SharedFlowServices;
import com.smartvalue.apigee.rest.schema.sharedFlow.auto.RevisionedObject;

public abstract class BundleObjectService extends ApigeeService {

	
	protected boolean deployUponUpload = false ; 
	
	public BundleObjectService(ManagementServer ms, String m_orgName) {
		super(ms, m_orgName);
	}

	public boolean isDeployUponUpload() {
		return deployUponUpload;
	}

	public BundleObjectService withDeployUponUpload(boolean m_deployUponUpload) {
		this.deployUponUpload = m_deployUponUpload;
		return this;
	}
	
	public void setDeployUponUpload(boolean deployUponUpload) {
		this.deployUponUpload = deployUponUpload;
	}
	
	public TransformationResults transformProxy(String pundleZipFileName, String newBundleFolderPath) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, FileNotFoundException, IOException {
		TransformationResults transformResults  = new TransformationResults ();
		ArrayList<ApigeeObjectTransformer>  transformers = this.buildTransformers();

		File pundleZipFile = new File (pundleZipFileName) ; 
		String zipFileName = pundleZipFile.getName();

		 
		String proxyName = zipFileName.substring(0, zipFileName.indexOf(".")); 
		int transformerCount = 1 ; 
		String tempTramsformedFilePath = newBundleFolderPath + File.separatorChar +"temp"+ File.separatorChar + "tranformer_"+transformerCount ; 
		Path sourcePath ; 
		Path destPath ; 
		if (transformers.size() > 0 )
		{
			TransformResult  tr = null ; 
			for (ApigeeObjectTransformer trasnformer : transformers)
			{
				boolean transform = trasnformer.filter(pundleZipFileName) ;
				if (transform)
				{	
					tr = trasnformer.trasform( pundleZipFileName , tempTramsformedFilePath);
					if (tr.isFailed())	
					{	System.out.println("Error  : Proxy Bundle "+ pundleZipFileName +" Transformed Failed Using "+ trasnformer.getClass().getName() + " With error : " + tr.getError());
						transformResults.add(tr); break; 	}
					else 
					{ 
						System.out.println("Success : Proxy Bundle "+ pundleZipFileName +" Transformed Successully Using "+ trasnformer.getClass().getName()+" and saved to " + tempTramsformedFilePath );
					}
					transformResults.add(tr);
				
					//System.out.println("=======Proxy "+ pundleZipFile + " Is Tranformed To : "+tempTramsformedFilePath+" ==========") ;
					// in the next loop transform the transformed file
					if (transformerCount != transformers.size())
					{
						pundleZipFileName = tempTramsformedFilePath + File.separatorChar + proxyName + ".zip" ;
						transformerCount++;
						tempTramsformedFilePath = newBundleFolderPath + File.separatorChar +"temp"+ File.separatorChar + "tranformer_"+transformerCount ;
					}
				}
				 
			}
			//--Upon success transformation, Copy Last Transformed file to the outputFolderPath 
			if (tr!= null && !tr.isFailed())
			{
				sourcePath = Path.of(tempTramsformedFilePath + File.separatorChar + proxyName + ".zip");
				destPath = Path.of(newBundleFolderPath + File.separatorChar + proxyName + ".zip");
				Files.copy(sourcePath, destPath , StandardCopyOption.REPLACE_EXISTING);
			}
		}
		else
		{
			// if No TRansformers found, simply copy the file to destination 
			sourcePath =  Path.of(pundleZipFileName);
			destPath =  Path.of(newBundleFolderPath + File.separatorChar + zipFileName );
			Files.copy(sourcePath, destPath , StandardCopyOption.REPLACE_EXISTING);
		}
		
		return transformResults ; 
	}

	
	public TransformationResults  transformAll(String inputFolderPath , String outputFolderPath) throws Exception
	{
		
		TransformationResults transformResults  = new TransformationResults (); 
		String envName ;
		File folder = new File(inputFolderPath);
		File[] listFiles = folder.listFiles() ; 
		if (listFiles == null) { throw new Exception("Transformation source Folder " + folder + "  Not Found Oe Empty ") ; }
		for (File envFolder : folder.listFiles() )
		{
			int envProxiesCount = 0 ; 
			envName = envFolder.getName();
			System.out.println("================Tranforming Proxies Deplyed TO Environment  " + envName + " ==============");
			for (File proxyFolder : envFolder.listFiles() )
			{
				envProxiesCount++; 
				for (File revisionFolder : proxyFolder.listFiles() )
				{
					String revision = revisionFolder.getName();
					String zipFileName = null ;
					String proxyName ; 
					for (File pundleZipFile : revisionFolder.listFiles())
					{
						try {
						zipFileName= pundleZipFile.getName(); 
						proxyName = zipFileName.substring(0, zipFileName.indexOf("."));
						}
						catch (Exception e) {
							System.err.println("File : " + pundleZipFile + "is Not a zip File ");
							continue; 
						}
						String newBundleFolderPath = outputFolderPath+ File.separatorChar + envName + File.separatorChar + proxyName + File.separatorChar + revision +File.separatorChar ;
						transformResults.addAll(this.transformProxy(pundleZipFile.getAbsolutePath(), newBundleFolderPath)) ; 
						
						/*
						String pundleZipFileName = pundleZipFile.getAbsolutePath() ; 
						int transformerCount = 1 ; 
						String tempTramsformedFilePath = newBundleFolderPath + File.separatorChar +"temp"+ File.separatorChar + "tranformer_"+transformerCount ; 
						Path sourcePath ; 
						Path destPath ; 
						if (transformers.size() > 0 )
						{
							TransformResult  tr = null ; 
							for (ApigeeObjectTransformer trasnformer : transformers)
							{
								//System.out.println("\t\tTransformer : " + trasnformer.getClass()) ; 
								boolean transform = trasnformer.filter(pundleZipFileName) ;
								
								if (transform)
								{	
									 
									tr = trasnformer.trasform( pundleZipFileName , tempTramsformedFilePath);
									if (tr.isFailed())	
									{	
										transformResults.add(tr);
										break; 
									}
									else { System.out.println("Proxy Bundle "+ pundleZipFileName +" Transformed Successully Using "+ trasnformer.getClass().getName()+" and saved to " + tempTramsformedFilePath );}
								
									//System.out.println("=======Proxy "+ pundleZipFile + " Is Tranformed To : "+tempTramsformedFilePath+" ==========") ;
									// in the next loop transform the transformed file
									if (transformerCount != transformers.size())
									{
										pundleZipFileName = tempTramsformedFilePath + File.separatorChar + proxyName + ".zip" ;
										transformerCount++;
										tempTramsformedFilePath = newBundleFolderPath + File.separatorChar +"temp"+ File.separatorChar + "tranformer_"+transformerCount ;
									}
								}
								 
							}
							//--Upon success transformation, Copy Last Transformed file to the outputFolderPath 
							if (tr!= null && !tr.isFailed())
							{
								sourcePath = Path.of(tempTramsformedFilePath + File.separatorChar + proxyName + ".zip");
								destPath = Path.of(newBundleFolderPath + File.separatorChar + proxyName + ".zip");
								Files.copy(sourcePath, destPath , StandardCopyOption.REPLACE_EXISTING);
							}
						}
						else
						{
							 sourcePath =  Path.of(pundleZipFileName);
							 destPath =  Path.of(newBundleFolderPath + File.separatorChar + proxyName + ".zip");
							 Files.createDirectories(destPath.getParent());
							 Files.copy(sourcePath, destPath , StandardCopyOption.REPLACE_EXISTING);
						}
						*/

						
					}
				}
			}

			System.out.println("==== End of Tranforming Proxies Deplyed to Environment " + envName +"==("+envProxiesCount+") Proxies =====\n\n\n");
			
		}
		return transformResults ; 

	}
	
	public LoadResults importAll(String folderPath) throws UnirestException, IOException 
	{
		LoadResults allResults = new LoadResults(); 
		String envName ;
		File folder = new File(folderPath); 
		if (folder.listFiles() == null)
		{throw new IllegalArgumentException ("Folder Not Found or Empty: " + folder.getAbsolutePath()) ; }
		
		for (File envFolder : folder.listFiles() )
		{
			int envProxiesCount = 0 ; 
			envName = envFolder.getName(); 
			System.out.println("================Importing "+this.getApigeeObjectType()+" Deplyed TO Environment  " + envName +"==============");
			for (File objectFolder : envFolder.listFiles() )
			{
				envProxiesCount++; 
				for (File revisionFolder : objectFolder.listFiles() )
				{
				
					for (File zipfile : revisionFolder.listFiles())
					{
						int dotIndex = zipfile.getName().indexOf(".zip");
						if (dotIndex<0) break ; // ignore not zip files & folders 
						
						String objectName= zipfile.getName().substring(0, dotIndex ) ; 
						System.out.println( objectName + ":" +zipfile.getAbsolutePath()  );
						LoadResult lr = importObject(zipfile.getAbsolutePath() , objectName); 
						
						allResults.add(lr) ;
						
						if (this.isDeployUponUpload() && !lr.isFailed())
						{
							
							Gson json = new Gson(); 
							ProxyUploadResponse pur = json.fromJson(lr.getHttpResponse().getBody(), ProxyUploadResponse.class); 
							//--- Started Deploying the proxy revision to environment 
							int newRevesion = Integer.parseInt(pur.getRevision());
							DeployResult dr = this.deployRevision(objectName, envName , newRevesion) ;
							allResults.add(dr ) ;
						}
					}
			
				}
				
			}
			System.out.println("==== End of Importing Proxies Deplyed to Environment " + envName +"==("+envProxiesCount+") Proxies =====\n\n\n");
	
		}
		
		//System.out.println("Errors:  \n" + failedResult.toString()); 
		return allResults;
	}

	public LoadResult importObject(String pundleZipFileName , String objectName) 
	{
		LoadResult loadResult = new LoadResult();
		loadResult.setSource(pundleZipFileName);
		
		String apiPath = this.getResourcePath()+"?action=import&name="+objectName+"&validate=true" ; 
		ManagementServer ms = this.getMs() ;
		HttpResponse<String> httpResponse = null ; 
		try
		{ 
			httpResponse = ms.getPostFileHttpResponse(apiPath , pundleZipFileName ) ;
			loadResult.setFailed(! Helper.isConsideredSuccess(httpResponse.getStatus()));
		}
		catch (Exception e) {
			loadResult.setFailed(true);
			loadResult.setExceptionClassName(e.getClass().getName());
			loadResult.setError(e.getMessage());
		}
		loadResult.setHttpResponse(httpResponse);
		return loadResult ; 
	}	
	
	
	public DeployResult deployRevision(String m_objectName , String m_envName , int revision ) throws UnirestException, IOException
	{
		DeployResult deployResult = new DeployResult() ;
		deployResult.setSource(m_objectName + "." + revision);
		deployResult.setDestination(m_envName);
		String apiPath = "/v1/organizations/"+orgName+"/environments/"+m_envName+"/"+getApigeeObjectType()+"/"+m_objectName +"/revisions/"+revision+"/deployments" ; 
		ManagementServer ms = this.getMs() ;
		HttpResponse<String> response = null ; 
		try {
			response = ms.getPostHttpResponse(apiPath, null, null ) ;
			deployResult.setFailed(! Helper.isConsideredSuccess(response.getStatus()));
			
		}
		catch (Exception e) {
			deployResult.setFailed(true) ; 
			deployResult.setError(e.getMessage());
			deployResult.setExceptionClassName(e.getClass().getName());
		}
		deployResult.setHttpResponse(response);
		return deployResult ; 
	}
	
	public void serializeDeployStatus(String destFile) throws UnirestException, Exception
	{
		File file = new File(destFile);
        file.getParentFile().mkdirs();
		FileOutputStream fos = new FileOutputStream(destFile);
        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(getDeploymentStatus());
		}  
	}
	
	public DeploymentsStatus deSerializeDeployStatus(String sourceFile) throws IOException, ClassNotFoundException 
	{
		FileInputStream fis = new FileInputStream(sourceFile);
        try (ObjectInputStream ois = new ObjectInputStream(fis)) {
        	DeploymentsStatus result  = (DeploymentsStatus) ois.readObject(); 
			return result ;
		} 
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String>  getAllBundledObjectNameList() throws Exception
	{
		ArrayList<String> BundledObjectNameList = this.getAllResources(ArrayList.class); 
		return BundledObjectNameList ;  
	}
	
	public DeploymentsStatus getDeploymentStatus() throws UnirestException, IOException, Exception
	{
		DeploymentsStatus result = new DeploymentsStatus () ;  
		ArrayList<String>  allBundledObjectsNameList = this.getAllBundledObjectNameList() ; 
		for ( String BundledObjectsName : allBundledObjectsNameList )
		{
			String apiPath = getResourcePath()+"/"+BundledObjectsName + "/deployments" ; 
			ProxyDeployment proxyDeployment =  this.getMs().executeGetMgmntAPI(apiPath , ProxyDeployment.class ) ;
			List<Environment> envs = proxyDeployment.getEnvironment() ; 
			HashMap<String, ArrayList<String>> proxyDeployMentREvisions = new HashMap<String, ArrayList<String>> (); 
			for (Environment env : envs )
			{
				String envname = env.getName(); 
				ArrayList<String> revionsList = new ArrayList<String>(); 
				for (Revision rev : env.getRevision() )
				{revionsList.add(rev.getName());}
				proxyDeployMentREvisions.put(envname, revionsList); 				
			}
			result.put(BundledObjectsName, proxyDeployMentREvisions) ; 
		}
		return result ; 
	}	
	

	public RevisionedObject getRevisionedObject( String ObjectName) throws UnirestException, IOException
	{
		RevisionedObject revisionObject = null ; 
		if (this instanceof ProxyServices)  revisionObject =  this.getOrganization().getProxy(ObjectName); 
		else if ( this instanceof SharedFlowServices) revisionObject = this.getOrganization().getShardFlow(ObjectName);
		else {throw new IllegalArgumentException("Revisioned Object Type " + this.getClass() + " is not Supported "); }
		return revisionObject ; 
	}
		
	public DeployResults rollBackToLastSerializedDeployStatus(String serlizeFileName) throws ClassNotFoundException, IOException, UnirestException
	{
		HashMap<String, HashMap<String, ArrayList<String>>> pds = this.deSerializeDeployStatus(serlizeFileName) ;
		DeployResults deployResults = new DeployResults(); 
		for (Entry<String, HashMap<String, ArrayList<String>>> entry : pds.entrySet())
		{
			String revisionedObjectName = entry.getKey(); 
			
			HashMap<String, ArrayList<String>> ds = entry.getValue();
			for (Entry<String, ArrayList<String>> entry01 : ds.entrySet())
			{
				String envName = entry01.getKey(); 
				ArrayList<String> revisions = entry01.getValue() ;
				for (String revision : revisions)
				{
					String processSource = revisionedObjectName +"." + envName + "." + revision ; 
					ProcessResult pr = new ProcessResult(); 
					pr.setSource(processSource);
					
					try {
						
						RevisionedObject revisionedObject = this.getRevisionedObject(revisionedObjectName);
						revisionedObject.getRevision(revision).deploy(envName); 
						pr.withFailed(false); 
					} catch (UnirestException | IOException e) {
						pr.withFailed(true)
						  .withExceptionClassName(e.getClass().getName())
						  .withError(e.getMessage());
					}
					deployResults.add(pr) ; 
				}
			}
		}
		return deployResults ; 
	}
	
	public  ExportResults exportAll(String folderDest , String serlizeFileName) throws Exception
	{
		// Keep a copy of the current proxies deployment statuses in a file in case a roll back of a Load Process is required  
		serializeDeployStatus(serlizeFileName);
		return exportAll(getAllBundledObjectNameList() , folderDest ); 
	} 
	public  ExportResults exportAll(String folderDest) throws Exception
	{
		return exportAll(getAllBundledObjectNameList() , folderDest ); 
	}
	
	private  ExportResults exportAll( ArrayList<String> proxiesList , String folderDest) 
	{
		
		ExportResults exportResults = new ExportResults();  
		{
			for (String proxyName : proxiesList)
			{
				System.out.println( "Start Exporting "+this.getApigeeObjectType()+" :" + proxyName );
				RevisionedObject revisionedObject;
				try {
					revisionedObject = this.getOrganization().getRevisionedObject(this.getApigeeObjectType() ,  proxyName);
					exportResults.addAll( revisionedObject.exportAllDeployedRevisions(folderDest)) ;
				} catch (UnirestException | IOException e) {
					ExportResult er = new ExportResult() ;
					er.setFailed(true);
					er.setSource(proxyName);
					er.setExceptionClassName(e.getClass().getName());
					er.setError(e.getMessage());
					
				} 
				
			}
		}
		return exportResults;
	}
	

}
