package com.omrbranch.pojo.createorder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrder_Input_Pojo {

	private String payment_method;
	private String card_no;
	private String card_type;
	private String year;
	private String month;
	private String cvv;

}
