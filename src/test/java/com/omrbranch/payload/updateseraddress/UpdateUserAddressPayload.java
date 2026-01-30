package com.omrbranch.payload.updateseraddress;

import com.omrbranch.pojo.updateuseraddress.UpdateUserAddress_Input_Pojo;

public class UpdateUserAddressPayload {

	public UpdateUserAddress_Input_Pojo createUpdateUserAddress(String address_id, String first_name, String last_name,
			String mobile, String apartment, int state, int city, int country, String zipcode, String address,
			String address_type) {

		UpdateUserAddress_Input_Pojo updateUserAddress_Input_Pojo = new UpdateUserAddress_Input_Pojo(address_id,
				first_name, last_name, mobile, apartment, state, city, country, zipcode, address, address_type);

		return updateUserAddress_Input_Pojo;

	}

}
