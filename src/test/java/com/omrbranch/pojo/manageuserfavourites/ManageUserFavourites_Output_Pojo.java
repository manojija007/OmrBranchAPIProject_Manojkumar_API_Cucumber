package com.omrbranch.pojo.manageuserfavourites;

import java.util.ArrayList;


import lombok.Data;

@Data
public class ManageUserFavourites_Output_Pojo {
	
    private int status;
    private String message;
    private ArrayList<Datum> data;
    private int cart_count;
    private String currency;

}
