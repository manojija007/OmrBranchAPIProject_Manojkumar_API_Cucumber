package com.omrbranch.payload.getsearchresult;

import com.omrbranch.pojo.getsearchresult.GetSearchResult_Input_Pojo;

public class GetSearchResult {

	public GetSearchResult_Input_Pojo createGetSearchResult(String category_id, String id, String type) {

		GetSearchResult_Input_Pojo getSearchResult_Input_Pojo = new GetSearchResult_Input_Pojo(category_id, id, type);
		return getSearchResult_Input_Pojo;
	}

}
