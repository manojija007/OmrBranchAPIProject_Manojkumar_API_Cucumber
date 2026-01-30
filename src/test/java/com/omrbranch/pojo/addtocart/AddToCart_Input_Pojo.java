package com.omrbranch.pojo.addtocart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToCart_Input_Pojo {
private String product_id;
private String product_variation_id;
private String type;
}
