package com.omrbranch.stepdefinition;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import com.omrbranch.endpoints.Endpoint;
import com.omrbranch.globaldata.GlobalData;
import com.omrbranch.objectmanager.PayloadObjectManager;
import com.omrbranch.pojo.categorylist.CategoryList_Output_Pojo;
import com.omrbranch.pojo.categorylist.ChildCatList;
import com.omrbranch.pojo.categorylist.Datum;
import com.omrbranch.pojo.getuserfavorites.GetUserFavorites_Output_Pojo;
import com.omrbranch.pojo.manageuserfavourites.ManageUserFavourites_Input_Pojo;
import com.omrbranch.pojo.manageuserfavourites.ManageUserFavourites_Output_Pojo;
import com.omrbranch.pojo.productlist.ProductList_Input_Pojo;
import com.omrbranch.pojo.productlist.ProductList_Output_Pojo;
import com.omrbranch.pojo.productlist.Variation;
import com.omrbranch.utility.BaseClass;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class TC004_AddFavouritesStep extends BaseClass {
	Response response;
	String logToken = GlobalData.getGlobalDataInstance().getLogToken();
	String productIdText = GlobalData.getGlobalDataInstance().getProductIdText();
	String variantIdText = GlobalData.getGlobalDataInstance().getVariantIDText();
	// Catogary List
	@Given("User sets headers for category list")
	public void user_sets_headers_for_category_list() {

		addHeader("accept", "application/json");
	}

	@When("User sends {string} request to the CategoryList endpoint")
	public void user_sends_request_to_the_category_list_endpoint(String type) {

		response = sendRequest(type, Endpoint.CATEGORY_LIST);
		int responseCode = getStatusCode(response);
		GlobalData.getGlobalDataInstance().setStatusCode(responseCode);
	}

	@Then("User should verify the category response message is {string}")
	public void user_should_verify_the_category_response_message_is(String expMsg) {

		CategoryList_Output_Pojo categoryList_Output_Pojo = response.as(CategoryList_Output_Pojo.class);
		String actMsg = categoryList_Output_Pojo.getMessage();
		Assert.assertEquals("Verify categoryList message", expMsg, actMsg);

	}

	@Then("User saves the category ID for main category {string} and subcategory {string}")
	public void user_saves_the_category_id_for_main_category_and_subcategory(String mainCatagory, String subCatagory) {

		CategoryList_Output_Pojo categoryList_Output_Pojo = response.as(CategoryList_Output_Pojo.class);
		ArrayList<Datum> data = categoryList_Output_Pojo.getData();

		// Debug
		boolean ChildCatagoryId = false;

		// execution
		for (Datum datum : data) {
			String name = datum.getName();

			if (name.equals(mainCatagory)) {
				ArrayList<ChildCatList> child_cat_list = datum.getChild_cat_list();
				for (ChildCatList eachChildCatList : child_cat_list) {
					String name2 = eachChildCatList.getName();
					if (name2.equals(subCatagory)) {
						int childCatagoryId = eachChildCatList.getId();
						ChildCatagoryId = true;
						GlobalData.getGlobalDataInstance().setChildCatagoryId(childCatagoryId);
						String ChildCatagoryIdText = String
								.valueOf(GlobalData.getGlobalDataInstance().getChildCatagoryId());
						GlobalData.getGlobalDataInstance().setChildCatagoryIdText(ChildCatagoryIdText);
						break;
					}

				}
				break;
			}

		}

		Assert.assertTrue("verify childCatagoryId", ChildCatagoryId);

	}

	// ProductList

	@Given("User sets headers for product list")
	public void user_sets_headers_for_product_list() {

		List<Header> lstHeader = new ArrayList<>();
		Header h1 = new Header("accept", "application/json");
		Header h2 = new Header("Content-Type", "application/json");

		lstHeader.add(h1);
		lstHeader.add(h2);

		Headers headers = new Headers(lstHeader);
		addHeaders(headers);

	}

	@Given("User sets request body with saved category ID and page number {string}")
	public void user_sets_request_body_with_saved_category_id_and_page_number(String page_no) {

		String childCatagoryIdText = GlobalData.getGlobalDataInstance().getChildCatagoryIdText();

		ProductList_Input_Pojo productList_Input_Pojo = PayloadObjectManager.getPayloadInstance()
				.getProductListPayload().createProductList(childCatagoryIdText, page_no);
		addPayload(productList_Input_Pojo);
	}

	@When("User sends {string} request to the ProductList endpoint")
	public void user_sends_request_to_the_product_list_endpoint(String type) {
		response = sendRequest(type, Endpoint.PRODUCT_LIST);
		int statusCode = getStatusCode(response);
		GlobalData.getGlobalDataInstance().setStatusCode(statusCode);
	}

	@Then("User should verify the product response message is {string}")
	public void user_should_verify_the_product_response_message_is(String expMsg) {
		ProductList_Output_Pojo productList_Output_Pojo = response.as(ProductList_Output_Pojo.class);
		String actMsg = productList_Output_Pojo.getMessage();
		Assert.assertEquals("Verify Product List", expMsg, actMsg);

	}

	@Then("User saves the variant ID of product {string}")
	public void user_saves_the_variant_id_of_product(String productName) {

		ProductList_Output_Pojo productList_Output_Pojo = response.as(ProductList_Output_Pojo.class);
		ArrayList<com.omrbranch.pojo.productlist.Datum> data = productList_Output_Pojo.getData();

		for (com.omrbranch.pojo.productlist.Datum datum : data) {
			String name = datum.getName();

			if (name.equals(productName)) {
				int productId = datum.getId();
				GlobalData.getGlobalDataInstance().setProductId(productId);
				String productIdText = String.valueOf(GlobalData.getGlobalDataInstance().getProductId());
				GlobalData.getGlobalDataInstance().setProductIdText(productIdText);

				ArrayList<Variation> variations = datum.getVariations();
				for (Variation datum2 : variations) {

					int variantID = datum2.getId();
					//					System.out.println(variantID);
					GlobalData.getGlobalDataInstance().setVariantID(variantID);
					String variantIDText = String.valueOf(GlobalData.getGlobalDataInstance().getVariantID());
					GlobalData.getGlobalDataInstance().setVariantIDText(variantIDText);
					break;

				}

			}

		}

	}



	@Then("User should verify the specification is {string}")
	public void user_should_verify_the_specification_is(String value) {

		int variantID = GlobalData.getGlobalDataInstance().getVariantID();

		ProductList_Output_Pojo productList_Output_Pojo = response.as(ProductList_Output_Pojo.class);
		ArrayList<com.omrbranch.pojo.productlist.Datum> data = productList_Output_Pojo.getData();

		for (com.omrbranch.pojo.productlist.Datum datum : data) {
			int id = datum.getId();

			if (id == variantID) {
				ArrayList<Variation> variations = datum.getVariations();
				for (Variation eachVariations : variations) {
					if (eachVariations.getSpecifications().equals(value)) {
						String specifications = eachVariations.getSpecifications();
						Assert.assertEquals("Verify Specification Value", value, specifications);
					}
				}

			}

		}

	}

	// Add favourites

	@Given("User sets bearer authorization using the saved logtoken for favourites")
	public void user_sets_bearer_authorization_using_the_saved_logtoken_for_favourites() {

		List<Header> lstHeader = new ArrayList<>();
		Header h1 = new Header("accept", "application/json");
		Header h2 = new Header("Authorization", "Bearer " + logToken);
		Header h3 = new Header("Content-Type", "application/json");

		lstHeader.add(h1);
		lstHeader.add(h2);
		lstHeader.add(h3);

		Headers headers = new Headers(lstHeader);
		addHeaders(headers);
	}

	@Given("User sets request body with saved variant ID to add to favourites")
	public void user_sets_request_body_with_saved_variant_id_to_add_to_favourites() {
		String productItText = GlobalData.getGlobalDataInstance().getProductIdText();
		String variationIdText = GlobalData.getGlobalDataInstance().getVariantIDText();
		System.out.println(productItText);
		System.out.println(variationIdText);

		ManageUserFavourites_Input_Pojo manageUserFavouritesPayload = PayloadObjectManager.getPayloadInstance().
				getManageUserFavourites().createUserFavouritesPayload(GlobalData.getGlobalDataInstance().getProductIdText(),
						GlobalData.getGlobalDataInstance().getVariantIDText());
		addPayload(manageUserFavouritesPayload);
		}



	@When("User sends {string} request to the ManageFavourites endpoint")
	public void user_sends_request_to_the_manage_favourites_endpoint(String type) {
		response = sendRequest(type, Endpoint.MANAGE_USER_FAVOURITE);
		int statusCode = getStatusCode(response);
		GlobalData.getGlobalDataInstance().setStatusCode(statusCode);
	}

	@Then("User should verify the favourites response message is  {string}")
	public void user_should_verify_the_favourites_response_message_is(String expMessage) {
		ManageUserFavourites_Output_Pojo manageUserFavourites_Output_Pojo = response.as(ManageUserFavourites_Output_Pojo.class);
		String actMessage = manageUserFavourites_Output_Pojo.getMessage();
		Assert.assertEquals("Verify manage favourites", expMessage,actMessage);
	}



	@Given("User sets bearer authorization using the saved logtoken for get favourites")
	public void user_sets_bearer_authorization_using_the_saved_logtoken_for_get_favourites() {
		List<Header> lstHeader = new ArrayList<>();
		Header h1 = new Header("accept", "application/json");
		Header h3 = new Header("Authorization", "Bearer " + GlobalData.getGlobalDataInstance().getLogToken());

		lstHeader.add(h1);
		lstHeader.add(h3); 

		Headers headers = new Headers(lstHeader);
		addHeaders(headers);    

	}


	@When("User sends {string} request to the GetFavourites endpoint")
	public void user_sends_request_to_the_get_favourites_endpoint(String type) {
		response = sendRequest(type, Endpoint.GET_USER_FAVOURITES);
		int statusCode = getStatusCode(response);
		GlobalData.getGlobalDataInstance().setStatusCode(statusCode);
	}

	@Then("User should verify the favourites list response message is {string}")
	public void user_should_verify_the_favourites_list_response_message_is(String expMessage) {
		GetUserFavorites_Output_Pojo getUserFavorites_Output_Pojo = response.as(GetUserFavorites_Output_Pojo.class);
		//GetUserFavourites_Output_Pojo getUserFavourites_Output_Pojo = response.as(GetUserFavourites_Output_Pojo.class);
		String actMessage = getUserFavorites_Output_Pojo.getMessage();
		Assert.assertEquals("Verify get favourites", expMessage,actMessage);

	}



	@Then("verify the favourites response message is  {string}")
	public void verify_the_favourites_response_message_is(String expMessage) {
		GetUserFavorites_Output_Pojo getUserFavorites_Output_Pojo = response.as(GetUserFavorites_Output_Pojo.class);
		//GetUserFavourites_Output_Pojo getUserFavourites_Output_Pojo = response.as(GetUserFavourites_Output_Pojo.class);
		String actMessage = getUserFavorites_Output_Pojo.getMessage();
		Assert.assertEquals("Verify get favourites", expMessage,actMessage);
	}


}