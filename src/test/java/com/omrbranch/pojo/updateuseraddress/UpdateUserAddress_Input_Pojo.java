package com.omrbranch.pojo.updateuseraddress;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UpdateUserAddress_Input_Pojo {
	
    private String address_id;
    private String first_name;
    private String last_name;
    private String mobile;
    private String apartment;
    private int state;
    private int city;
    private int country;
    private String zipcode;
    private String address;
    private String address_type;

}
