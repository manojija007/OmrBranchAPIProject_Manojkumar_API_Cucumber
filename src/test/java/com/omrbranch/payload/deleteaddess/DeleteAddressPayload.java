package com.omrbranch.payload.deleteaddess;

import com.omrbranch.pojo.deleteaddress.DeleteAddress_Input_Pojo;

public class DeleteAddressPayload {

	public DeleteAddress_Input_Pojo deleteAddressList(String address_id) {

		DeleteAddress_Input_Pojo deleteAddress_Input_Pojo = new DeleteAddress_Input_Pojo(address_id);

		return deleteAddress_Input_Pojo;

	}

}
