package com.omrbranch.pojo.getuserfavorites;

import lombok.Data;

@Data
public class AttributeOption {
	
    private int id;
    private int attribute_id;
    private String value;
    private String status;
    private String created_at;
    private String updated_at;

}
