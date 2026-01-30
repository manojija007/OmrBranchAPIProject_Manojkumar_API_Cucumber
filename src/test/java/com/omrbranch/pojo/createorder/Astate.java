package com.omrbranch.pojo.createorder;

import lombok.Data;

@Data
public class Astate {
	
    private int id;
    private String name;
    private int country_id;
    private String status;
    private Country country;

}
