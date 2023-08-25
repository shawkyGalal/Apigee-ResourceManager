package com.smartvalue.apigee.configuration.filteredList;

public interface FilterList<E> {
	FilterList<E> filter(ListFilter<E> filter) throws Exception;
}
