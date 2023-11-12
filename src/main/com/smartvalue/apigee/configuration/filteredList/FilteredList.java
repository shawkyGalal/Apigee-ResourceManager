package com.smartvalue.apigee.configuration.filteredList;

import java.util.ArrayList;

public class FilteredList<E> extends ArrayList<E> implements FilterList<E> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public FilteredList<E> filter(ListFilter<E> filter) throws Exception 
    {
        FilteredList<E> filteredList = new FilteredList<>();
        for (E element : this) {
            if (filter.apply(element)) {
                filteredList.add(element);
            }
        }
        return filteredList;
    }
    
    public static void main(String[] args) throws Exception {
        // Create a FilteredList instance
        FilteredList<Integer> originalList = new FilteredList<>();
        originalList.add(10);
        originalList.add(20);
        originalList.add(30);
        originalList.add(40);

        // Define a custom filter implementation
        ListFilter<Integer> customFilter = entry -> entry > 30; 

        // Filter the list using the custom filter
        FilteredList<Integer> filteredList = originalList.filter( customFilter );

        // Print the filtered list
        System.out.println("Filtered List: " + filteredList);
    }
}

