package com.smartvalue.apigee.migration.export;

import java.util.UUID;

import com.smartvalue.apigee.migration.ProcessResults;

public class ExportResults extends ProcessResults{

	public ExportResults(String desc, UUID m_uuid) {
		super(desc, m_uuid);
	}

	public ExportResults(String desc) {
		super(desc );
	}


}
