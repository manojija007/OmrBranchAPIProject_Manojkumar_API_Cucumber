package com.omrbranch.pojo.addtocart;

import lombok.Data;

@Data
public class AttributeOption {
	public int id;
    public int attribute_id;
    public String value;
    public String status;
    public String created_at;
    public String updated_at;

}
