package com.omrbranch.pojo.createorder;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserAddress {
	
    private int id;
    private String address_code;
    private int user_id;
    private String first_name;
    private String last_name;
    private String mobile;
    private String apartment;
    private String address;
    private int country_id;
    private int state_id;
    private int city_id;
    private String zipcode;
    private Object lat;
    private Object lng;
    @JsonProperty("default") 
    private String mydefault;
    private String status;
    private String address_type;
    private String created_at;
    private String updated_at;
    private Acity acity;
    private Astate astate;
    private Acountry acountry;

}
