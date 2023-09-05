package com.smartvalue.moj.clients.environments;

public class Environments {
 Environment[] Environments ; 
 private String fileContent ;  


	private void setEnvs(Environment[] m_envs) {
		this.Environments = m_envs; 
		
	}

	private Environment[] getEnvs() {

		return this.Environments;
	}
	
	public Environment getEnvByName(String m_name)
	{
		Environment result = null ; 
		for ( Environment x : this.getEnvs() ) 
		{
			if (x.getName().equalsIgnoreCase(m_name) )
			{	result = x ;break ;  }
		}
		return result ; 
	}
	
	public String getFileContent() {
		return fileContent;
	}

}
