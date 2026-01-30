package com.omrbranch.pojo.postmanbasicauthlogin;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class PostmanBasicAuthLogin_Output_Pojo {
	
    private int status;
    private String message;
    private Data data;
    private String refer_msg;
    private int cart_count;
    private String role;

}
