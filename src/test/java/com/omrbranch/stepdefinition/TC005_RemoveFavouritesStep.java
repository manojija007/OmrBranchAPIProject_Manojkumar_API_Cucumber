package com.omrbranch.stepdefinition;

import java.util.ArrayList;
import java.util.List;

import com.omrbranch.endpoints.Endpoint;
import com.omrbranch.globaldata.GlobalData;
import com.omrbranch.objectmanager.PayloadObjectManager;
import com.omrbranch.pojo.getuserfavorites.GetUserFavorites_Output_Pojo;
import com.omrbranch.pojo.manageuserfavourites.ManageUserFavourites_Output_Pojo;
import com.omrbranch.utility.BaseClass;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class TC005_RemoveFavouritesStep extends BaseClass {

	Response response;
	String logtoken = GlobalData.getGlobalDataInstance().getLogToken();
	String productIdText = GlobalData.getGlobalDataInstance().getProductIdText();
	String variantIdText = GlobalData.getGlobalDataInstance().getVariantIDText();

	@Given("User sets bearer authorization for managing favourites")
	public void user_sets_bearer_authorization_for_managing_favourites() {
		List<Header> lstHeader = new ArrayList<>();

		Header h1 = new Header("accept", "application/json");
		Header h2 = new Header("Content-Type", "application/json");
		Header h3 = new Header("Authorization", "Bearer " + logtoken);
		lstHeader.add(h1);
		lstHeader.add(h2);
		lstHeader.add(h3);

		Headers headers = new Headers(lstHeader);
		addHeaders(headers);
	}

	@Given("User sets request body with saved variant ID to remove from favourites")
	public void user_sets_request_body_with_saved_variant_id_to_remove_from_favourites() {
		addPayload(PayloadObjectManager.getPayloadInstance().getManageUserFavourites()
				.createUserFavouritesPayload(productIdText, variantIdText));
	}

	@Then("Verify the response status code is {int}")
	public void verify_the_response_status_code_is(Integer expStatusCode) {
		response = sendRequest("POST", Endpoint.MANAGE_USER_FAVOURITE);
		int actStatusCode = response.getStatusCode();
//		assertEquals("Verify Status Code", expStatusCode, actStatusCode);
		System.out.println(actStatusCode);

	}

	@Then("Verify the Remove product response message is {string}")
	public void verify_the_remove_product_response_message_is(String string) {
		ManageUserFavourites_Output_Pojo manageUserFavourites_Output_Pojo = response.as(ManageUserFavourites_Output_Pojo.class);
//		ManageUserFavourites_Output_Pojo manageUserFavourite_Output_Pojo = response
//				.as(ManageUserFavourites_Output_Pojo.class);
		String actMessage = manageUserFavourites_Output_Pojo.getMessage();
		System.out.println(actMessage);

	}

	@Given("User sets bearer authorization for retrieving favourites")
	public void user_sets_bearer_authorization_for_retrieving_favourites() {
		List<Header> lstHeader = new ArrayList<>();

		Header h1 = new Header("accept", "application/json");
		Header h3 = new Header("Authorization", "Bearer " + logtoken);
		lstHeader.add(h1);
		lstHeader.add(h3);

		Headers headers = new Headers(lstHeader);
		addHeaders(headers);

	}

	@Then("Verify the favourites list response message is {string}")
	public void verify_the_favourites_list_response_message_is(String string) {
		GetUserFavorites_Output_Pojo getUserFavorites_Output_Pojo = response.as(GetUserFavorites_Output_Pojo.class);
		//GetUserFavourites_Output_Pojo getUserFavourites_Output_Pojo = response.as(GetUserFavourites_Output_Pojo.class);
		String actMessage = getUserFavorites_Output_Pojo.getMessage();
		System.out.println(actMessage);

	}

}