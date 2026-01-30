package com.omrbranch.pojo.productlist;

import java.util.ArrayList;

import lombok.Data;

@Data
public class ProductList_Output_Pojo {
	
    private int status;
    private String message;
    private String currency;
    private ArrayList<Datum> data;
    private String banner;
    private int cart_count;

}
