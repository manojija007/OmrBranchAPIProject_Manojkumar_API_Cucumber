package com.omrbranch.payload.adduseraddress;

import com.omrbranch.pojo.adduseraddress.AddUserAddress_Input_Pojo;

public class AddAddressPayload {
	
	public AddUserAddress_Input_Pojo createAddressList(String firstName, String lastName, String mobile, String apartment, int stateId,
			int cityId, int country, String zipcode, String address, String address_type) {
		
		AddUserAddress_Input_Pojo addUserAddress_Input_Pojo = new AddUserAddress_Input_Pojo(firstName, lastName, mobile, apartment, stateId, cityId, country, zipcode, address, address_type);
		return addUserAddress_Input_Pojo;
		
	}

}
