package com.omrbranch.pojo.createorder;

import lombok.Data;

@Data
public class Country {
	
    private int id;
    private String code;
    private String name;
    private int phonecode;
    private String currency_name;
    private String currency_symbol;
    private String currency_code;
    private String status;

}
