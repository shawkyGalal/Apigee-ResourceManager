# ResourceManager
Project Scope: 
Apigee Google API management gateway

Project Objective 
This Project is intended to build a set of Google Apigee related utilities : 

## 1- Apigee Migration Tool 
You could Use this tool especially from Apigee onpremise migration to Apigee Cloud - using Google Service Account Key as an authorization method

•	Clone this reposatory 

	git clone https://github.com/shawkyGalal/ResourceManager.git

•	
•	Build Project With Maven 

 	cd <AppHome>
 	mvn clean compile assembly:single

•	Install gcloud Google Command Line Tool 
	
 	flow instruction at: 
	https://cloud.google.com/sdk/docs/install#windows 
	
	Follow the installation wizard instructions. You can choose the installation location and whether to add the Cloud SDK tools to your system PATH. Adding to the PATH allows you to use gcloud from any command prompt window.

•	gcloud Authenticate:
	 
  	gcloud auth login 
   
   	to authenticate with your Google Cloud account. This will open a browser window for you to sign in.
    
•	Create Your Service Account Json Key :

	gcloud iam service-accounts keys create my-key-file.json  --iam-account my-service-account@my-project.iam.gserviceaccount.com
    
•	Add the Service account key to your config.json file.

•	Use Usage_Examples folder for Some Usage Examples (exportAllSource.bat , importAllToDest.bat , transformAllSource.bat ... ) 
For Example : 
• To export all proxies : 

cd ./target

java -jar ./target/ResourceManager-1.0.0-jar-with-dependencies.jar  -configFile <config.json>  -infra  <Infra> -org <OrgName> -operation migrate -exportAll proxies -sourceFolder -destFolder "</path/to/Destination>"

•  To transform all proxies : 


java -jar ./target/ResourceManager-1.0.0-jar-with-dependencies.jar  -configFile <config.json>  -infra  <Infra>  -org <OrgName> -operation migrate -transformAll proxies -sourceFolder "</path/to/Source>" -destFolder "</path/to/Traformed>"

•  To Perform Import for the transformed proxies : 


java -jar ./target/ResourceManager-1.0.0-jar-with-dependencies.jar  -configFile <config.json>  -infra  <Infra>  -org <OrgName> -operation migrate -importAll proxies -sourceFolder "</path/to/Traformed>"

•  To Delete all proxies : 


java -jar ./target/ResourceManager-1.0.0-jar-with-dependencies.jar  -configFile <config.json>  -infra  <Infra>  -org <OrgName> -operation migrate -deleteAll proxies


## 2- Build an automatic on demand capacity control and resource failures handling system. 
This will implemented by continuously monitoring all Apigee message processors and routers and make the appropriate decision to allocate these type of resources to different Apigee Environment based on availability and workload to grantee a High Availability and ensure SRE 


## 3- Perform specific Apigee queries like :  
•	Find All Products without Proxies 
•	Find all Target Server Usage in all Proxies 
•	Find all un-deployed proxies
•	Find All Products without Proxies 	 

   and others 
   

