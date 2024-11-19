package com.smartvalue.apigee.rest.schema.product;


public class Product extends com.smartvalue.apigee.rest.schema.product.auto.Product
{

	@Override
	public String getUniqueId() {
		return this.getName();
	}
		
}
