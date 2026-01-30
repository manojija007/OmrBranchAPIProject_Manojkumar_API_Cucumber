package com.omrbranch.payload.createorder;

import com.omrbranch.pojo.createorder.CreateOrder_Input_Pojo;

public class CreateOrderPayload {

	public CreateOrder_Input_Pojo createOrder(String payment_method, String card_no, String card_type, String year,
			String month, String cvv) {

		CreateOrder_Input_Pojo createOrder_Input_Pojo = new CreateOrder_Input_Pojo(payment_method, card_no, card_type,
				year, month, cvv);

		return createOrder_Input_Pojo;

	}
}