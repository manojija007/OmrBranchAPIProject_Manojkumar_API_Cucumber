package com.omrbranch.pojo.categorylist;

import java.util.ArrayList;

import lombok.Data;

@Data
public class CategoryList_Output_Pojo {

	public int status;
	public String message;
	public ArrayList<Datum> data;

}
