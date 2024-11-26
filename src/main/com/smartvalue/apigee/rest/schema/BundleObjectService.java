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
import java.util.UUID;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.smartvalue.apigee.configuration.AppConfig;
import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.migration.ProcessResult;
import com.smartvalue.apigee.migration.ProcessResults;
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
import com.smartvalue.apigee.rest.schema.proxy.ProxyServices;
import com.smartvalue.apigee.rest.schema.proxyDeployment.ProxyDeployment;
import com.smartvalue.apigee.rest.schema.proxyDeployment.auto.Environment;
import com.smartvalue.apigee.rest.schema.proxyDeployment.auto.Revision;
import com.smartvalue.apigee.rest.schema.proxyRevision.ProxyRevision;
import com.smartvalue.apigee.rest.schema.proxyUploadResponse.ProxyUploadResponse;
import com.smartvalue.apigee.rest.schema.sharedFlow.SharedFlowServices;
import com.smartvalue.apigee.rest.schema.sharedFlow.auto.RevisionedObject;

public abstract class BundleObjectService extends ApigeeService implements RollBackable{

	protected boolean deployUponUpload = false ; 
	
	
	public BundleObjectService(ManagementServer ms, String m_orgName) {
		super(ms, m_orgName);
	}
	public BundleObjectService(ManagementServer ms) {
		super(ms, ms.getOrgName());
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
	
	public static TransformationResults transformBundleObject(String pundleZipFileName, String newBundleFolderPath , ArrayList<ApigeeObjectTransformer>  transformers ) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, FileNotFoundException, IOException 
	{
		UUID uuid = UUID.randomUUID(); 
		TransformationResults transformResults  = new TransformationResults ("Transform " + pundleZipFileName , uuid );
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
			destPath =  Path.of(newBundleFolderPath  ); // + File.separatorChar + zipFileName 
			Files.createDirectories(destPath) ; 
			Files.copy(sourcePath, destPath , StandardCopyOption.REPLACE_EXISTING);
		}
		
		return transformResults ; 
	}
	
	/**
	 * Transform the provided Bundle Using the transformers associated with the current infra 
	 */
	public TransformationResults transformBundleObject(String pundleZipFileName, String newBundleFolderPath) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, FileNotFoundException, IOException {
		ArrayList<ApigeeObjectTransformer>  transformers = this.buildTransformers();
		return BundleObjectService.transformBundleObject( pundleZipFileName,  newBundleFolderPath , transformers) ; 
	}

	
	public TransformationResults  transformAll(String inputFolderPath , String outputFolderPath , UUID uuid ) throws Exception
	{
		if (uuid == null) uuid = UUID.randomUUID(); 
		TransformationResults transformResults  = new TransformationResults ("Transform All " + this.getApigeeObjectType() , uuid); 
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
						transformResults.addAll(this.transformBundleObject(pundleZipFile.getAbsolutePath(), newBundleFolderPath)) ; 
						
					}
				}
			}

			System.out.println("==== End of Tranforming Proxies Deplyed to Environment " + envName +"==("+envProxiesCount+") Proxies =====\n\n\n");
			
		}
		return transformResults ; 

	}
	public LoadResults importAll(String folderPath) throws Exception 
	{
		return importAll( folderPath , null ) ; 
	}
	public LoadResults importAll(String folderPath, UUID uuid) throws Exception 
	{
		// <MigrationBasePath>/<UniqueProcessId>/<Infra>/<Org>/<ObjectType>
		// Keep a copy of all proxies deployment status before importing to be able to rollback this import action
		if(uuid== null) uuid = UUID.randomUUID(); 
		serializeAllDeployStatus(uuid.toString() );
		LoadResults allResults = new LoadResults("Import All " + this.getApigeeObjectType() , uuid); 
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
							ProxyUploadResponse pur = json.fromJson(lr.getResponseBody(), ProxyUploadResponse.class); 
							//--- Started Deploying the proxy revision to environment 
							int newRevesion = Integer.parseInt(pur.getRevision());
							DeployResults dr = this.deployRevision(objectName, envName , newRevesion , true) ;
							allResults.addAll(dr ) ;
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
	/**
	 * 
	 * @param m_objectName Proxy/SharedFlow to be deployed
	 * @param m_envName  Apigee Environment Name to Deply to  
	 * @param revision Apigee Proxy/sharedFlow Revision to be deployed
	 * @param force : Undeploy any other revision deployed to the provided m_envName
	 * @return
	 * @throws UnirestException
	 * @throws IOException
	 */
	public DeployResults deployRevision(String m_objectName , String m_envName , int revision , boolean force ) throws UnirestException, IOException 
	{
		UUID uuid = UUID.randomUUID() ; 
		DeployResults deployResults = new DeployResults("Deploying " + this.getApigeeObjectType() +" : " +  m_objectName +" ,Revision : " + revision , uuid ) ;
		if(force)// Undeploy all revisions deployed to m_envName  
		{	deployResults.addAll(this.UnDeployFromEnv(m_objectName, m_envName));	}
		
		DeployResult deployResult = new DeployResult() ;
		deployResult.setSource("Deploying : " + m_objectName + "." + revision + "To Env: " + m_envName);
		deployResult.setDestination(m_envName);
		String apiPath = AppConfig.BASE_BATH+this.getMs().getOrgName()+"/environments/"+m_envName+"/"+getApigeeObjectType()+"/"+m_objectName +"/revisions/"+revision+"/deployments" ; 
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
		deployResults.add(deployResult); 
		return deployResults ; 
	}
	
	public DeployResults UnDeployFromEnv(String m_objectName , String m_envName ) throws UnirestException, IOException 
	{
		List<Revision> deployedRevisions = this.getEnvDeployedRevisions( m_objectName , m_envName) ; 
		UUID uuid = UUID.randomUUID() ; 
		DeployResults deployResults = new DeployResults("Undeploying " + m_objectName +"From Env: "+ m_envName  , uuid) ;
		for (Revision revision : deployedRevisions )
		{
			DeployResult deployResult = new DeployResult() ;
			deployResult.setSource("Undeplolying " + m_objectName + "." + revision.getName() + " From Env. " + m_envName);
			String apiPath = AppConfig.BASE_BATH+this.getMs().getOrgName()+"/environments/"+m_envName+"/"+getApigeeObjectType()+"/"+m_objectName +"/revisions/"+revision.getName()+"/deployments" ; 
			ManagementServer ms = this.getMs() ;
			HttpResponse<String> response = null ; 
			try {
				response = ms.getDeleteHttpResponse(apiPath) ;
				deployResult.setFailed(! Helper.isConsideredSuccess(response.getStatus()));
			}
			catch (Exception e) {
				deployResult.setFailed(true) ; 
				deployResult.setError(e.getMessage());
				deployResult.setExceptionClassName(e.getClass().getName());
			}
			deployResult.setHttpResponse(response);
			deployResults.add(deployResult); 
		}
		
		return deployResults ; 
	}
	
	public void serializeAllDeployStatus(String processID) throws UnirestException, Exception
	{ 	String destFile = this.getMs().getSerlizeDeplyStateFileName(processID); 
		File file = new File(destFile);
        file.getParentFile().mkdirs();
        System.out.println("Start Serializing All "+this.getApigeeObjectType()+" Deployment status to : " + destFile );
		FileOutputStream fos = new FileOutputStream(destFile);
        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(this.getDeploymentStatus());
		}  
	}
	
	public void serializeDeployStatus(String processID , String bundleObjectname) throws UnirestException, Exception
	{ 	String destFile = this.getMs().getSerlizeDeplyStateFileName(processID); 
		File file = new File(destFile);
        file.getParentFile().mkdirs();
        System.out.println("Start Serializing All "+this.getApigeeObjectType()+" Deployment status to : " + destFile );
		FileOutputStream fos = new FileOutputStream(destFile);
        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(this.getDeploymentStatus(bundleObjectname));
		}  
	}
	
	public DeploymentsStatus deSerializeDeployStatus(String sourceFile) throws IOException, ClassNotFoundException 
	{
		//String sourceFile = this.getSerlizeDeplyStateFileName(sourceFile); 
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
	
	public DeploymentsStatus getDeploymentStatus(String bundledObjectsName) throws UnirestException, IOException, Exception
	{
		DeploymentsStatus result = new DeploymentsStatus () ;  
		String apiPath = getResourcePath()+"/"+bundledObjectsName + "/deployments" ; 
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
		result.put(bundledObjectsName, proxyDeployMentREvisions) ; 
		return result ; 
	}	
	
	public DeploymentsStatus getDeploymentStatus() throws UnirestException, IOException, Exception
	{
		DeploymentsStatus result = new DeploymentsStatus () ;  
		ArrayList<String>  allBundledObjectsNameList = this.getAllBundledObjectNameList() ; 
		for ( String bundledObjectsName : allBundledObjectsNameList )
		{
			String apiPath = getResourcePath()+"/"+bundledObjectsName + "/deployments" ; 
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
			result.put(bundledObjectsName, proxyDeployMentREvisions) ; 
		}
		return result ; 
	}	
	
	public List<Revision> getEnvDeployedRevisions(String bundledObjectsName, String m_envName) throws UnirestException, IOException
	{
		List<Revision> result = new ArrayList<Revision>(); 
		String apiPath = getResourcePath()+"/"+bundledObjectsName + "/deployments" ; 
		ProxyDeployment proxyDeployment =  this.getMs().executeGetMgmntAPI(apiPath , ProxyDeployment.class ) ;
		List<Environment> envs = proxyDeployment.getEnvironment() ; 
		for (Environment env : envs )
		{
			if (env.getName().equalsIgnoreCase(m_envName))
			{
				result = env.getRevision(); 
				break ; 
			}
		}
		return result ; 
	}

	public ProxyDeployment getBundleObjectDeployments(String bundledObjectName) throws UnirestException, IOException
	{
		String apiPath = getResourcePath()+"/"+bundledObjectName + "/deployments" ; 
		ProxyDeployment proxyDeployment =  this.getMs().executeGetMgmntAPI(apiPath , ProxyDeployment.class ) ;
		return proxyDeployment ; 
	}
	
	public ProxyRevision getBundleObjectRevision(String bundledObjectName , String revision ) throws UnirestException, IOException
	{
		String apiPath = getResourcePath()+"/"+bundledObjectName + "/revisions/" + revision ; 
		ProxyRevision proxyRevision =  this.getMs().executeGetMgmntAPI(apiPath , ProxyRevision.class ) ;
		return proxyRevision ; 
	}


	public RevisionedObject getRevisionedObject( String ObjectName) throws UnirestException, IOException
	{
		RevisionedObject revisionObject = null ; 
		if (this instanceof ProxyServices)  revisionObject =  this.getOrganization().getProxy(ObjectName); 
		else if ( this instanceof SharedFlowServices) revisionObject = this.getOrganization().getShardFlow(ObjectName);
		else {throw new IllegalArgumentException("Revisioned Object Type " + this.getClass() + " is not Supported "); }
		return revisionObject ; 
	}
	
	/**
	 * To apply the Complete ETL ( Extract, Transform and Load and Deploy )  Process on proxyName  using the same organization for both source & destination 
	 * @param proxyName
	 * @return ProcessResults including the complete process (ETL ) results  
	 * @throws Exception
	 */
	public ProcessResults  performETL( String proxyName , UUID uuid ) throws Exception {
		
		if (uuid == null) { uuid = UUID.randomUUID() ; 	}
		 serializeDeployStatus( uuid.toString() , proxyName) ; 
		//==================Export One Proxy ===========================
		 
		 ProcessResults overallResults = new ProcessResults("Perform Complete ETL on " + proxyName  , uuid); 
		 RevisionedObject ro =  this.getRevisionedObject(proxyName) ; 
		 String exportDest = this.getMs().getMigPathUpToOrgName(uuid.toString())  + File.separator+ this.getMigationSubFoler() ; 
		 ExportResults ers =  ro.exportAllDeployedRevisions(exportDest);
		 overallResults.addAll(ers); 
		 
		 //==================Transform the Exported Proxy ===========================
		 TransformationResults trnsformResults = new TransformationResults("Transform Object " , uuid) ; 
		 for (ProcessResult er : ers )
		 {
			String transformSource = er.getDestination() ;
			String dest = transformSource.replaceAll(this.getMigationSubFoler() ,  TransformedFoldername +File.separatorChar+File.separatorChar + AppConfig.ProxiesSubFolder) ;  
			trnsformResults.addAll( this.transformBundleObject(transformSource +proxyName+".zip" , dest  ) ) ;
		 }
		 overallResults.addAll(trnsformResults) ; 
			 
		//==================Load the Transformed Proxy ===========================
		 ProcessResults uploadResults = new ProcessResults("Load Object " , uuid) ;
		 LoadResult  loadResult ; 
		 
		 for (ProcessResult transformResult : trnsformResults )
		 {
			 String source = transformResult.getDestination() +File.separator+ proxyName+".zip" ;
			 if (! transformResult.isFailed())
			 {
				 loadResult = this.importObject(source, proxyName) ; 
				 uploadResults.add(loadResult) ;
					 if ( ! loadResult.isFailed())
					 {
						 Gson json = new Gson(); 
						 ProxyUploadResponse pur = json.fromJson(loadResult.getResponseBody(), ProxyUploadResponse.class);
						 int newRevesion = Integer.parseInt(pur.getRevision());
						 AppConfig appConfig = this.getMs().getAppConfig() ; 
						 String envName = loadResult.extractEnvNameFromsource(appConfig) ; 
						 DeployResults deployResults = this.deployRevision(proxyName, envName , newRevesion, true) ;
						 uploadResults.addAll(deployResults) ; 
					 }
			 }
		 }
		 overallResults.addAll(uploadResults) ; 
		 return overallResults ; 
	 }
	
	public DeployResults rollBackAllToSerializedDeployStatus( UUID uuid ) 
	{
		if (uuid == null) uuid = UUID.randomUUID(); 
		DeployResults deployResults = new DeployResults("RollBack Process" , uuid); 
		
		HashMap<String, HashMap<String, ArrayList<String>>> pds;
		try {
			pds = this.deSerializeDeployStatus(this.getMs().getSerlizeDeplyStateFileName(uuid.toString()));
		} catch (ClassNotFoundException | IOException e) {
			deployResults.add(new ProcessResult(e)) ; 
			e.printStackTrace();
			return deployResults ; 
		}
		 
		for (Entry<String, HashMap<String, ArrayList<String>>> entry : pds.entrySet())
		{
			String revisionedObjectName = entry.getKey(); 
			deployResults.addAll( rollBackObjectToSerializedDeployStatus( revisionedObjectName , uuid) )  ;
		}
		return deployResults ; 
	}
	
	public DeployResults rollBackObjectToSerializedDeployStatus(String revisionedObjectName ,  UUID uuid) 
	{
		if (uuid == null) uuid = UUID.randomUUID(); 
		DeployResults deployResults = new DeployResults("rollBackObjectToLastSerializedDeployStatus" , uuid); 
		HashMap<String, ArrayList<String>> pds = null;
		try {
			String deployStatusFileName = this.getMs().getSerlizeDeplyStateFileName(uuid.toString()) ; 
			pds = this.deSerializeDeployStatus(deployStatusFileName).get(revisionedObjectName);
		} catch (ClassNotFoundException | IOException e) {
			ProcessResult error = new ProcessResult(e) ;
			deployResults.add(error) ; 
			return deployResults ; 
		}
		for (Entry<String, ArrayList<String>> entry : pds.entrySet())
		{
			String envName = entry.getKey(); 
			ArrayList<String> revisions = entry.getValue() ;
			for (String revision : revisions)
			{
				DeployResults xx = new DeployResults("deployRevision" , uuid);
				try {
					xx = this.deployRevision(revisionedObjectName, envName, Integer.parseInt(revision), true);
				} catch (NumberFormatException | UnirestException | IOException e) {
					ProcessResult error = new ProcessResult(e) ;
					deployResults.add(error) ; 
					e.printStackTrace();
				} 
				deployResults.addAll(xx) ; 
			}
		}
		return deployResults ;
	}

	public  ExportResults exportAll(String folderDest) throws Exception
	{
		return exportAll(folderDest , null);
	}
	public  ExportResults exportAll(String folderDest, UUID uuid) throws Exception
	{
		return exportAll(getAllBundledObjectNameList() , folderDest , uuid); 
	}
	
	private  ExportResults exportAll( ArrayList<String> proxiesList , String folderDest , UUID uuid ) 
	{
		if(uuid == null)  uuid = UUID.randomUUID(); 
		ExportResults exportResults = new ExportResults("exportAll" , uuid);  
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
					exportResults.add(er); 
				} 
				
			}
		}
		return exportResults;
	}

}
