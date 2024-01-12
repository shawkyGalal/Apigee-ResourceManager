package com.smartvalue.apigee.rest.schema;


import java.io.File;
import java.util.ArrayList;

import com.smartvalue.apigee.configuration.infra.ManagementServer;
import com.smartvalue.apigee.rest.schema.proxy.transformers.ApigeeObjectTransformer;
import com.smartvalue.apigee.rest.schema.proxy.transformers.TransformResult;

public abstract class BundleObjectService extends ApigeeService {

	public BundleObjectService(ManagementServer ms, String m_orgName) {
		super(ms, m_orgName);
	}

	public ArrayList<TransformResult>  transformAll(String inputFolderPath , String outputFolderPath)
	{
		ArrayList<TransformResult> transformResults  = new ArrayList<TransformResult> (); 
		String envName ;
		File folder = new File(inputFolderPath);
		ArrayList<ApigeeObjectTransformer>  transformers = this.getTransformers(); 
	
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
					for (File pundleZipFile : revisionFolder.listFiles())
					{
						String zipFileName= pundleZipFile.getName(); 
						String proxyName = zipFileName.substring(0, zipFileName.indexOf(".")); 
						String newBundleFolderPath = outputFolderPath+ File.separatorChar + envName + File.separatorChar + proxyName + File.separatorChar + revision +File.separatorChar ;
						String pundleZipFileName = pundleZipFile.getAbsolutePath() ; 
						
						for (ApigeeObjectTransformer trasnformer : transformers)
						{
							boolean transform = trasnformer.filter(pundleZipFileName) ;
							if (transform)
							{	 
								transformResults.add(trasnformer.trasform(pundleZipFileName , newBundleFolderPath));
								System.out.println("=======Proxy "+ pundleZipFile + " Is Tranformed To : "+newBundleFolderPath+" ==========") ;
							}
						}
					}
				}
			}

			System.out.println("==== End of Tranforming Proxies Deplyed to Environment " + envName +"==("+envProxiesCount+") Proxies =====\n\n\n");
			
		}
		return transformResults ; 

	}


}
