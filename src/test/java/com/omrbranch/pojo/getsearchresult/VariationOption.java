package com.omrbranch.pojo.getsearchresult;

import lombok.Data;

@Data
public class VariationOption {
	
    private int id;
    private int product_id;
    private int variation_id;
    private String attribute_id;
    private int attribute_value_id;
    private String status;
    private String created_at;
    private String updated_at;

}
