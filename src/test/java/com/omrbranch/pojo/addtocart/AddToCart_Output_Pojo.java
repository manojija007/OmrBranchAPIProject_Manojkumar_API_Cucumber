package com.omrbranch.pojo.addtocart;

@lombok.Data

public class AddToCart_Output_Pojo {
	 public int status;
	    public String message;
	    public String currency;
	    public int cart_count;
	    public com.omrbranch.pojo.addtocart.Data data;
}
  