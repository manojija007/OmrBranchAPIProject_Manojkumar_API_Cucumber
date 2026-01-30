package com.omrbranch.pojo.productlist;

import lombok.Data;

@Data
public class VariationOption {
	
    public int id;
    public int product_id;
    public int variation_id;
    public String attribute_id;
    public int attribute_value_id;
    public String status;
    public String created_at;
    public String updated_at;

}
