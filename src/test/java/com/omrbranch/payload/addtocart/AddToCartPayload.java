package com.omrbranch.payload.addtocart;

import com.omrbranch.pojo.addtocart.AddToCart_Input_Pojo;

public class AddToCartPayload {

	public AddToCart_Input_Pojo createAddtoCart(String product_id, String product_variation_id, String type) {

		AddToCart_Input_Pojo addToCart_Input_Pojo = new AddToCart_Input_Pojo(product_id, product_variation_id, type);
		return addToCart_Input_Pojo;
	}

}
