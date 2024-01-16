# ResourceManager
Project Scope: 
Apigee on perimis API management gateway 

Project Objective 
This Project is intended to build a set of 0Google Apigee related utilities : 
1- Apigee Migration Tool especially from Apigee onpremise to Apigee Cloud 

•	Install gcloud Google Command Line Tool 
	Download the Installer:
	Go to the Google Cloud SDK download page, and click on the "Download SDK" button.
	
	Run the Installer:
	Run the downloaded installer (e.g., google-cloud-sdk-installer.exe).
	
	Follow Installation Wizard:
	Follow the installation wizard instructions. You can choose the installation location and whether to add the Cloud SDK tools to your system PATH. Adding to the PATH allows you to use gcloud from any command prompt window.

•	Authenticate:
	Run gcloud auth login to authenticate with your Google Cloud account. This will open a browser window for you to sign in.
•	Create a Service Account Json Key :	
	gcloud iam service-accounts keys create my-key-file.json \
  	--iam-account my-service-account@my-project.iam.gserviceaccount.com
    
•	Use the Service account key in config.json file .

•	Clone the reposatory 
•	cd <AppHome>
•	Maven Build 
   mvn clean compile assembly:single



2- As a requirement to implement System Reliability Engineering SRE, Build an automatic on demand capacity control and resource failures handling system. 
This will implemented by continuously monitoring all Apigee message processors and routers and make the appropriate decision to allocate these type of resources to different Apigee Environment based on availability and workload to grantee a High Availability and ensure SRE 


2- Perform specific Apigee queries based on apigee management api like 
•	Find All Products without Proxies 
•	Find all Target Server Usage in all Proxies 
•	Find all un-deployed proxies
•	Find All Products without Proxies 	 

   and others 
   

