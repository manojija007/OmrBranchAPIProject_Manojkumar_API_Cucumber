package com.omrbranch.pojo.getuserfavorites;

import java.util.ArrayList;

import lombok.Data;

@Data
public class GetUserFavorites_Output_Pojo {
	
    private int status;
    private String message;
    private ArrayList<Datum> data;
    private int cart_count;
    private String currency;
	

}
