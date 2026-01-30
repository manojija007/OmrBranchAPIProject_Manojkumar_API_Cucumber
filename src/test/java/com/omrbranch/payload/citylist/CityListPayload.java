package com.omrbranch.payload.citylist;

import com.omrbranch.pojo.citylist.CityList_Input_Pojo;




public class CityListPayload {
	public CityList_Input_Pojo createCityList(String stateIdText) {
		CityList_Input_Pojo cityList_Input_Pojo = new CityList_Input_Pojo(stateIdText);
		return cityList_Input_Pojo;

	}

}
