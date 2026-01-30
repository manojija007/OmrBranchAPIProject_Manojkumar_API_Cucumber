package com.omrbranch.payload.searchproduct;

import com.omrbranch.pojo.searchproduct.SearchProduct_Input_Pojo;

public class SearchProductPayload {
	
	public SearchProduct_Input_Pojo createSearchProduct(String text) {
		
		SearchProduct_Input_Pojo searchProduct_Input_Pojo = new SearchProduct_Input_Pojo(text);
		
		return searchProduct_Input_Pojo;	
		
		

	}

}
