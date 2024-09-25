# Apigee ResourceManager
## Project Scope: 
A Java Command Line tool for Google Apigee API management gateway for suitable for migartion purposed especially from on premise Apigee Edge (OPDK) to Google Apigee Cloud 


## Prerequizite 
Before start using this project you should have the following components/system installed on your machine 

1- Java 

Install jdk 21 or higher from -> [https://www.oracle.com/java/technologies/downloads/]( https://www.oracle.com/java/technologies/downloads/ ) 

2- git 

Install from -> [https://git-scm.com/book/en/v2/Getting-Started-Installing-Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)

3- maven : 

Install from -> [https://maven.apache.org/install.html](https://maven.apache.org/install.html)

4- Having a Google Cloud account and a Project with Apigee API's enabled, in Case you need to import/export from Your Apigee cloud account 

## Project Objective 
This Project is intended to build a set of Google Apigee related utilities : 


## 1- Apigee Migration Tool 
![ Migration Methodology Diagram ](./MigrationMethodology.png "a title") 

You could Use this tool especially for Apigee onpremise migration to Apigee Cloud - using Google Service Account Key as an authorization method

### 1.1 Clone this repository 
~~~
git clone https://github.com/shawkyGalal/Apigee-ResourceManager.git
~~~

### 1.2 Build Project With Maven 
~~~
 cd Apigee-ResourceManager
 mvn clean compile assembly:single
~~~
This Should build a jar file  /target/ResourceManager-jar-with-dependencies.jar


    
### 1.3 Create Your Service Account Json Key :

#### 1.3.1 Using Google Cloud Consle 
 
You Can Create Your Service Account Using Gcloud Console Service "API & Services" at https://console.cloud.google.com/apis/dashboard


#### 1.3.2 Using gcloud cli

### 1.3.2.1 Install gcloud Google Cloud Command Line Tool 
	
 	flow instruction at: 
	[https://cloud.google.com/sdk/docs/install#windows](https://cloud.google.com/sdk/docs/install#windows)
	
	Follow the installation wizard instructions. You can choose the installation location and whether to add the Cloud SDK tools to your system PATH. Adding to the PATH allows you to use gcloud from any command prompt window.

### 1.3.2.2  gcloud Authenticate:
~~~
gcloud auth login 
~~~
   	to authenticate with your Google Cloud account. This will open a browser window for you to sign in.

~~~
PROJECT_ID=<Your_Project_Name>
SERVICE_ACCOUNT_NAME=<Your_Service_Account_Name>
gcloud iam service-accounts create ${SERVICE_ACCOUNT_NAME} --description="[DESCRIPTION]" --display-name="[DISPLAY_NAME]"
SERVICE_ACCOUNT_EMAIL=${SERVICE_ACCOUNT_NAME}@${PROJECT_ID}.iam.gserviceaccount.com
~~~

Grant apigee.apiAdminV2 role (permissions) to the service account. Use the following command to grant a role to the service account:

~~~
gcloud projects add-iam-policy-binding ${PROJECT_ID} --member="serviceAccount:${SERVICE_ACCOUNT_EMAIL}" --role="roles/apigee.apiAdminV2"
~~~

#### 1.3.3 Create a Service Account Json Key
~~~
gcloud iam service-accounts keys create my-key-file.json  --iam-account ${SERVICE_ACCOUNT_EMAIL}
~~~
    
### 1.4 Add the Service account key to your config.json file.
Sample : 

~~~
{
	"Partners":[		
		{  "Name" : "YourCompanyName" , 
			"Customers" : [
				{	"Name" : "Customer01"  
					,"Infras" : [
						{	"Name" : "Stage" ,
							"regions" :
							 [
								{ "name" : "dc-1" ,  
								"MgmServerUrl" : "http://stage.apigee.yourcompany.com:9001/api-management" , 
								"OauthMgmServerUrl" : "http://stage.apigee.yourcompany.com/api-management" ,
								"tokenUrl" : "http://stage.apigee.yourcompany.com/v1/authorize/access-token" ,  
								"podGateWayName" : "gateway"
								}, 
								{ "name" : "dc-2" ,  
								"MgmServerUrl" : "http://stage-dr.apigee.yourcompany.com/api-management" , 
								"OauthMgmServerUrl" : "http://stage-dr.apigee.yourcompany.com/api-management" ,
								"tokenUrl" : "http://stage.apigee.yourcompany.com/v1/authorize/access-token" , 
								"podGateWayName" : "gateway-2"
								}
							],
							"AuthType" : "OAuth" , 
							"sysadminCred" : {
								"Username" : "******" , 
								"Password" : "*******" , 
								"clientId" : "*********************" , 
								"clientSecret" : "********************"
							}, 
							"connectionTimeout" : "0" ,
							"socketTimeout" : "20000" , 
							"Ansible_hosts_file" : "/etc/apigee/ansible/ansible_hosts/moj/stage/hosts"  , 
							"DevPortal" : {"basePath" : "/var/www/html/moj-int" , "dataBaseName" :"moj_int"}
						}, 
						{	 
							"Name" : "MyApigee" ,
							"connectionTimeout" : "0" ,
							"socketTimeout" : "20000" ,
							"AuthType" : "OAuth" , 
							"googleCloud" : "true",
							"googleServiceAccount" :  // --replace the below dummy service account with  Your Google Cloud Service Account, It should have access to your Apigee Services 
							{    
								  "type": "service_account",
								  "project_id": "********",
								  "private_key_id": "*************************",
								  "private_key": "-----BEGIN PRIVATE KEY-----****************************\n-----END PRIVATE KEY-----\n",
								  "client_email": "********@******.iam.gserviceaccount.com",
								  "client_id": "100161588649150357715",
								  "auth_uri": "https://accounts.google.com/o/oauth2/auth",
								  "token_uri": "https://oauth2.googleapis.com/token",
								  "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
								  "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/apigeeamin-java%40moj-apigee.iam.gserviceaccount.com",
								  "universe_domain": "googleapis.com"
							}
							 , 
							"regions" :
							[
								{ "name" : "region01" ,  
									"OauthMgmServerUrl" : "https://apigee.googleapis.com/" 
								}
							]
						}
					]
				} 			
			]
		}	
	]
}
~~~

•	Use Usage_Examples folder for Some Usage Examples (exportAllSource.bat , importAllToDest.bat , transformAllSource.bat ... ) 
For Example : 
### Export all Proxies : 
The following command will export all proxies from Organization <OrgName> and store the result to </path/to/Destination>

~~~
java -jar ./target/ResourceManager-jar-with-dependencies.jar  -configFile <path/to/your/config.json>  -partner YourCompanyName -customer Customer01  -infra  Stage -infra  <Infra> -org <OrgName> -operation migrate -exportAll proxies -destFolder "</path/to/Destination>"
~~~

### Transform All Proxies : 
Transformation can be done through 2 ways : 

##### 1- Java Transformer   
Transforming All Apigee Objects are fully customizable, You should be java faimilar to build your own Java Tranfomer Class that implements  java interface com.smartvalue.apigee.rest.schema.proxy.transformers.ApigeeObjectTransformer 
then configure your Transformer class in the project configuration file ( Config.json)  as the example below  : 

~~~
//================== Your config.json  ===========================
,"Infras" : [
{
	"Name" : "Prod", 
	"transformers" : 
	[
		{
		"implClass" : "com.smartvalue.apigee.rest.schema.proxy.transformers.TargetServerTransformer", 
		"attributes" : 
			[
				{ "name" : "fileName" , "value" :"apiproxy/targets/default.xml"	} ,
				{ "name" : "xpath" , "value" :"/TargetEndpoint/HTTPTargetConnection" }
			]
		}, 
		{
		"implClass" : "com.smartvalue.apigee.rest.schema.proxy.transformers.ZipFileEntryModifyTransformer", 
		"attributes" : 
			[
			{ "name" : "valueDelimiter" , "value" :";;"	},
			{ "name" : "filesPathInZip" , "value" :"apiproxy/policies/Regular-Expression-Protection.xml ;; apiproxy/policies/xxxxx.xml"}, 
			{ "name" : "searchFor" , "value" :"<Pattern/>  ;; oldvlaue"	}, 
			{ "name" : "replaceBy" , "value" :"<Pattern>aaaaaaa</Pattern> ;; newValue"}
			]
		}
	]
...

~~~

The above Transformers configuration will configure the tool to apply 2 transformers : 

1- TargetServerTransformer : This Transformer will replace the current  HTTPTargetConnection with a Target Server instead of hardcoded URL 

2- ZipFileEntryModifyTransformer :  This is a generic trasformer can be used to search for a specific text in a specific file in the proxy/sharedflow  pundle and replace this text with a new value. 

You Can build your own transformers to satisfy your specific needs and simply attach it with its attributes as the example above.

You Could Use the command line to perform the Transformation  
 
~~~
java -jar ./target/ResourceManager-jar-with-dependencies.jar  -configFile <path/to/your/config.json> -partner YourCompanyName -customer Customer01  -infra  Stage  -org <OrgName> -operation migrate -transformAll proxies -sourceFolder "</path/to/Source>" -destFolder "</path/to/Traformed>"
~~~

##### 2- Shell script Transformer   
You could build your own shell script transformer 
Example : [./scripts/modifyPrxoies.sh](./scripts/modifyPrxoies.sh)
 
 
### Import from the transformed proxies : 
~~~
java -jar ./target/ResourceManager-jar-with-dependencies.jar  -configFile <path/to/your/config.json>  -partner YourCompanyName -customer Customer01 -infra  <Infra>  -org <OrgName> -operation migrate -importAll proxies -sourceFolder "</path/to/Traformed>"
~~~
###  Delete all proxies : 
~~~
java -jar ./target/ResourceManager-jar-with-dependencies.jar  -configFile <path/to/your/config.json>  -partner YourCompanyName -customer Customer01 -infra  <Infra>  -org <OrgName> -operation migrate -deleteAll proxies
~~~

## Objects Valid for exportAll , transformAll , importAll , deleteAll 

You Could Perform any of the operation ( exportAll , transformAll , importAll , deleteAll ) with the following Apigee Objects Type : 

- apps
- developers
- kvms
- products
- proxies
- sharedflows
- targetservers



## 2- Build an automatic on demand capacity control and resource failures handling system. 
This will implemented by continuously monitoring all Apigee message processors and routers and make the appropriate decision to allocate these type of resources to different Apigee Environment based on availability and workload to grantee a High Availability and ensure SRE 
Under Construction ....


## 3- Perform specific Apigee queries like :  
### Find All Products without Proxies 
### Find all Target Server Usage in all Proxies 
### Find all un-deployed proxies
### Find All Products without Proxies 	 
      

