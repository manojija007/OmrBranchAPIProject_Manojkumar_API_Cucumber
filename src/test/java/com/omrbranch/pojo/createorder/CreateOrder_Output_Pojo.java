package com.omrbranch.pojo.createorder;

@lombok.Data
public class CreateOrder_Output_Pojo {

	private int status;
	private String message;
	private String currency;
	private com.omrbranch.pojo.createorder.Data data;
	private int order_id;

}
