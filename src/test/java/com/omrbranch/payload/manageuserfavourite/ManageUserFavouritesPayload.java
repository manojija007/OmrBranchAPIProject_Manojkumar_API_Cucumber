package com.omrbranch.payload.manageuserfavourite;

import com.omrbranch.pojo.manageuserfavourites.ManageUserFavourites_Input_Pojo;

public class ManageUserFavouritesPayload {

	public ManageUserFavourites_Input_Pojo createUserFavouritesPayload(String product_id, String product_variation_id) {

		ManageUserFavourites_Input_Pojo manageUserFavourites_Input_Pojo = new ManageUserFavourites_Input_Pojo(
				product_id, product_variation_id);

		return manageUserFavourites_Input_Pojo;

	}


}
