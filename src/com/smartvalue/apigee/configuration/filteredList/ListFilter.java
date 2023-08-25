package com.smartvalue.apigee.configuration.filteredList;

public interface ListFilter<Entry> {
	boolean apply(Entry   entry) throws Exception;
}
