package com.omrbranch.payload.productlist;

import com.omrbranch.pojo.productlist.ProductList_Input_Pojo;

public class ProductListPayload {
	
	public ProductList_Input_Pojo createProductList(String category_id, String page_no) {
		
		ProductList_Input_Pojo productList_Input_Pojo = new ProductList_Input_Pojo(category_id, page_no);
		
		return productList_Input_Pojo;
		

	}

}
