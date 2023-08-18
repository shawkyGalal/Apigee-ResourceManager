package com.smartvalue.apigee.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;

import com.google.gson.Gson;
import com.google.gson.internal.Primitives;
import com.smartvalue.apigee.configuration.infra.Infra;
import com.sun.codemodel.JCodeModel;

public class ApigeeConfig {
	
	private ArrayList<Partner> Partners ;
	
	private HashMap<String , HashMap<String , HashMap<String , Infra> > > partnersMap = new HashMap<String , HashMap<String , HashMap<String , Infra> > > () ; 

	public ApigeeConfig (String  m_apigeeConfigFilePath) throws FileNotFoundException , IOException
	{
		File configFile = new File(m_apigeeConfigFilePath) ; //("E:\\MasterWorks\\Eclipse-WS\\ResourceManager\\config.json") ;
		FileInputStream inputStream = new FileInputStream(configFile);
		String fileContent = readFromInputStream(inputStream) ; 
		Gson gson = new Gson();
		ApigeeConfig result = null; 
		result = gson.fromJson(fileContent, (Type) ApigeeConfig.class);
		
		//result = (ApigeeConfig) Primitives.wrap(ApigeeConfig.class).cast(result);
		this.setPartners(result.getPartners()) ; 
	
		//---using a HashMap instead of ArrayList 
		for ( Partner partner : this.Partners  )
		{
			this.partnersMap.put (partner.getName()  , partner.getCustomersMap()) ; 
		}
		
	}
	
	private ArrayList<Partner> getPartners() {
		return Partners;
	}
	public void setPartners(ArrayList<Partner> partners) {
		this.Partners = partners;
	}

		
	private HashMap<String, HashMap<String, Infra>> getPartner(String m_partnerName) throws Exception 
	{
		HashMap<String, HashMap<String, Infra>> partner =  this.getPartnersMap().get(m_partnerName);
		if (partner == null)
		{
			throw new Exception ("Partner " + m_partnerName + " Not Found") ; 
		}
		return partner ; 
	}
	
	private HashMap<String, Infra> getCustomer(String m_partnerName , String m_customerName) throws Exception {
		HashMap<String, HashMap<String, Infra>> Partner =  this.getPartner( m_partnerName);
		HashMap<String, Infra> customer =  Partner.get(m_customerName);
		if (customer == null)
		{
			throw new Exception ("Customer " + m_customerName + " Not Found For Partner " + m_partnerName ) ;
		}
		return customer ; 
	}

	public Infra getInfra( String m_partnerName , String m_customerName , String m_infraName) throws Exception {
		HashMap<String, Infra> customer =  this.getCustomer( m_partnerName ,  m_customerName);
		Infra infra = customer.get(m_infraName) ; 
		if (infra == null )
		{
			throw new Exception ("Infra " + m_infraName  + " Not Found For Customer " + m_customerName + " and Partner " + m_partnerName ) ;

		}
		return infra ; 
	}

	private static String readFromInputStream(InputStream inputStream)
			  throws IOException {
			    StringBuilder resultStringBuilder = new StringBuilder();
			    try (BufferedReader br
			      = new BufferedReader(new InputStreamReader(inputStream))) {
			        String line;
			        while ((line = br.readLine()) != null) {
			            resultStringBuilder.append(line).append("\n");
			        }
			    }
			  return resultStringBuilder.toString();
			}

	public HashMap<String , HashMap<String , HashMap<String , Infra> > > getPartnersMap() {
		return partnersMap;
	}
	
	public void generateJavaClassFromJson(String jsonObject , String m_ClassName , String m_packageName) throws IOException {
		//=========Generate Java Classes From Json Object 
		File f = new File ("src") ;
		this.convertJsonToJavaClass(jsonObject ,  f, m_packageName, m_ClassName  )  ;
		//=======
		
	}
	
	private  void convertJsonToJavaClass(String jsonObject , File outputJavaClassDirectory, String packageName, String javaClassName)			  throws IOException 
	{
				
	    JCodeModel jcodeModel = new JCodeModel();

	    GenerationConfig config = new DefaultGenerationConfig() {
			    @Override
			    public boolean isGenerateBuilders() 
			    {
				     return true;
				}
		
				@Override
				public SourceType getSourceType() 
				{
				   return SourceType.JSON;
				}
		 };

		 	SchemaGenerator schemaGenerator = new SchemaGenerator();
	        Jackson2Annotator annotator = new Jackson2Annotator(config);
	        SchemaStore schemaStore = new SchemaStore() ; 
	        RuleFactory ruleFactory = new RuleFactory(config, annotator, schemaStore) ;
	                
	        SchemaMapper mapper01 = new SchemaMapper( ruleFactory , schemaGenerator );
			mapper01.generate(jcodeModel, javaClassName, packageName, jsonObject) ; 
	 		jcodeModel.build(outputJavaClassDirectory);
	}
	

}
