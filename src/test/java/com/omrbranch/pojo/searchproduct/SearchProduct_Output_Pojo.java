package com.omrbranch.pojo.searchproduct;

import java.util.ArrayList;


import lombok.Data;

@Data
public class SearchProduct_Output_Pojo {
	
    private int status;
    private String message;
    private ArrayList<Datum> data;
    private String currency;

}
