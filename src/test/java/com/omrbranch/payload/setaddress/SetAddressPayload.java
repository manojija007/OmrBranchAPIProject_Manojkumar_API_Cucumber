package com.omrbranch.payload.setaddress;

import com.omrbranch.pojo.setaddress.SetAddress_Input_Pojo;

public class SetAddressPayload {

	public SetAddress_Input_Pojo createSetAddress(String address_id, String cart_id) {

		SetAddress_Input_Pojo setAddress_Input_Pojo = new SetAddress_Input_Pojo(address_id, cart_id);
		return setAddress_Input_Pojo;

	}

}
