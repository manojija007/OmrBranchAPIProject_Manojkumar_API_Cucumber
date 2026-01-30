package com.omrbranch.pojo.getsearchresult;

import java.util.ArrayList;

import lombok.Data;

@Data
public class GetSearchResult_Output_Pojo {
	
    private int status;
    private String message;
    private String currency;
    private ArrayList<Datum> data;
    private int cart_count;

}
