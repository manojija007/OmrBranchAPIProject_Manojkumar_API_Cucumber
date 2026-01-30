package com.omrbranch.stepdefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;

import com.omrbranch.endpoints.Endpoint;
import com.omrbranch.globaldata.GlobalData;
import com.omrbranch.objectmanager.PayloadObjectManager;
import com.omrbranch.pojo.addtocart.AddToCart_Input_Pojo;
import com.omrbranch.pojo.addtocart.AddToCart_Output_Pojo;
import com.omrbranch.pojo.clearcart.ClearCart_Output_Pojo;
import com.omrbranch.pojo.createorder.CreateOrder_Input_Pojo;
import com.omrbranch.pojo.createorder.CreateOrder_Output_Pojo;
import com.omrbranch.pojo.getcartitems.GetCartItems_Output_Pojo;
import com.omrbranch.pojo.getsearchresult.GetSearchResult_Input_Pojo;
import com.omrbranch.pojo.getsearchresult.GetSearchResult_Output_Pojo;
import com.omrbranch.pojo.getsearchresult.Variation;
import com.omrbranch.pojo.searchproduct.Datum;
import com.omrbranch.pojo.searchproduct.SearchProduct_Input_Pojo;
import com.omrbranch.pojo.searchproduct.SearchProduct_Output_Pojo;
import com.omrbranch.pojo.setaddress.SetAddress_Input_Pojo;
import com.omrbranch.pojo.setaddress.SetAddress_Ouput_Pojo;
import com.omrbranch.utility.BaseClass;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class TC006_CreateOrderByCategoryStep extends BaseClass {

	Response response;
	String logToken = GlobalData.getGlobalDataInstance().getLogToken();

	// Clear Cart
	@Given("User sets bearer authorization for ClearCart endpoint")
	public void user_sets_bearer_authorization_for_clear_cart_endpoint() {
		List<Header> lstHeader = new ArrayList<>();
		Header h1 = new Header("accept", "application/json");
		Header h2 = new Header("Authorization", "Bearer " + logToken);
		lstHeader.add(h1);
		lstHeader.add(h2);

		Headers headers = new Headers(lstHeader);
		addHeaders(headers);
	}

	@When("User sends {string} request to the ClearCart endpoint")
	public void user_sends_request_to_the_clear_cart_endpoint(String type) {
		response = sendRequest(type, Endpoint.CLEAR_CART_LIST);
		int statusCode = getStatusCode(response);
		GlobalData.getGlobalDataInstance().setStatusCode(statusCode);

	}

//	@Then("Verify the ClearCart response message is {string}")
//	public void verify_the_clear_cart_response_message_is(String expMsg) {
//		ClearCart_Output_Pojo clearCart_Output_Pojo = response.as(ClearCart_Output_Pojo.class);
//		String actMsg = clearCart_Output_Pojo.getMessage();
//		Assert.assertEquals("Verify ClearCart response message", expMsg, actMsg);
//	}

	@Then("Verify the ClearCart response message is {string}")
	public void verify_the_clear_cart_response_message_is(String expMsg) {

		SoftAssertions softAssert = new SoftAssertions();

		ClearCart_Output_Pojo clearCart_Output_Pojo = response.as(ClearCart_Output_Pojo.class);
		String actMsg = clearCart_Output_Pojo.getMessage();

		softAssert.assertThat(actMsg).as("Verify ClearCart response message").isEqualTo(expMsg);
	}

	// Search product

	@Given("User sets headers for SearchProduct")
	public void user_sets_headers_for_search_product() {
		List<Header> lstHeader = new ArrayList<>();
		Header h1 = new Header("accept", "application/json");
		Header h2 = new Header("Content-Type", "application/json");

		lstHeader.add(h1);
		lstHeader.add(h2);

		Headers headers = new Headers(lstHeader);
		addHeaders(headers);
	}

	@When("User sets request body to search for product {string}")
	public void user_sets_request_body_to_search_for_product(String productName) {

		SearchProduct_Input_Pojo searchProduct_Input_Pojo = PayloadObjectManager.getPayloadInstance()
				.getSearchProductPayload().createSearchProduct(productName);

		addPayload(searchProduct_Input_Pojo);

	}

	@When("User sends {string} request to the SearchProduct endpoint")
	public void user_sends_request_to_the_search_product_endpoint(String type) {
		response = sendRequest(type, Endpoint.SEARCH_PRODUCT);
		int statusCode = getStatusCode(response);
		GlobalData.getGlobalDataInstance().setStatusCode(statusCode);
	}

	@Then("Verify the search result includes product name {string} and Save the category ID and product ID from response")
	public void verify_the_search_result_includes_product_name_and_save_the_category_id_and_product_id_from_response(
			String expMsg) {

		SearchProduct_Output_Pojo searchProduct_Output_Pojo = response.as(SearchProduct_Output_Pojo.class);
		ArrayList<Datum> data = searchProduct_Output_Pojo.getData();
		for (Datum datum : data) {
			String getProductName = datum.getText();
			if (getProductName.equals(expMsg)) {
				int productId = datum.getId();
				int category_id = datum.getCategory_id();
				String productType = datum.getType();

//				System.out.println(productId);
//				System.out.println(category_id);
//				System.out.println(productType);

				GlobalData.getGlobalDataInstance().setProductId(productId);
				GlobalData.getGlobalDataInstance().setCategory_id(category_id);
				GlobalData.getGlobalDataInstance().setProductType(productType);
				Assert.assertEquals("Verify searched product name", expMsg, getProductName);
			}

		}

	}

	// Get Product

	@Given("User sets bearer authorization for GetSearchProductList endpoint")
	public void user_sets_bearer_authorization_for_get_search_product_list_endpoint() {

		List<Header> lstHeader = new ArrayList<>();
		lstHeader.add(new Header("accept", "application/json"));
		lstHeader.add(new Header("Authorization", "Bearer " + logToken));
		lstHeader.add(new Header("Content-Type", "application/json"));

		Headers headers = new Headers(lstHeader);
		addHeaders(headers);
	}

	@When("User sets request body with saved product ID")
	public void user_sets_request_body_with_saved_product_id() {

		String productIdText = String.valueOf(GlobalData.getGlobalDataInstance().getProductId());
		String categoryIdText = String.valueOf(GlobalData.getGlobalDataInstance().getCategory_id());
		String productType = GlobalData.getGlobalDataInstance().getProductType();

//		System.out.println(productIdText);
//		System.out.println(categoryIdText);
//		System.out.println(productType);

		GetSearchResult_Input_Pojo getSearchResult_Input_Pojo = PayloadObjectManager.getPayloadInstance()
				.getSearchResultPayload().createGetSearchResult(categoryIdText, productIdText, productType);
		addPayload(getSearchResult_Input_Pojo);

	}

	@When("User sends {string} request to the GetSearchProductList endpoint")
	public void user_sends_request_to_the_get_search_product_list_endpoint(String type) {

		response = sendRequest(type, Endpoint.GET_PRODUCT_SEARCH_RESULT);
		int statusCode = getStatusCode(response);
		GlobalData.getGlobalDataInstance().setStatusCode(statusCode);
	}

	@Then("Verify the response includes product with specification {string} and save the variant ID")
	public void verify_the_response_includes_product_with_specification_and_save_the_variant_id(String expMsg) {

		GetSearchResult_Output_Pojo getSearchResult_Output_Pojo = response.as(GetSearchResult_Output_Pojo.class);
		ArrayList<com.omrbranch.pojo.getsearchresult.Datum> data = getSearchResult_Output_Pojo.getData();

		for (com.omrbranch.pojo.getsearchresult.Datum datum : data) {
			ArrayList<Variation> variations = datum.getVariations();
			for (Variation variation : variations) {
				String specifications = variation.getSpecifications();
				if (specifications.equals(expMsg)) {
					int variantID = variation.getId();

					GlobalData.getGlobalDataInstance().setVariantID(variantID);
					GlobalData.getGlobalDataInstance().setSpecifications(specifications);

					Assert.assertEquals("Verify product specifications", expMsg, specifications);
				}
			}

		}

	}

	// Add to Cart

	@Given("User sets bearer authorization for AddToCart endpoint")
	public void user_sets_bearer_authorization_for_add_to_cart_endpoint() {

		List<Header> lstHeader = new ArrayList<>();
		lstHeader.add(new Header("accept", "application/json"));
		lstHeader.add(new Header("Authorization", "Bearer " + logToken));
		lstHeader.add(new Header("Content-Type", "application/json"));

		Headers headers = new Headers(lstHeader);
		addHeaders(headers);

	}

	@When("User sets request body using saved variant ID and pass the type {string} to add product to cart")
	public void user_sets_request_body_using_saved_variant_id_and_pass_the_type_to_add_product_to_cart(String addType) {

		String productIdText = String.valueOf(GlobalData.getGlobalDataInstance().getProductId());
		String variantIDText = String.valueOf(GlobalData.getGlobalDataInstance().getVariantID());

		AddToCart_Input_Pojo addToCart_Input_Pojo = PayloadObjectManager.getPayloadInstance().getAddToCartPayload()
				.createAddtoCart(productIdText, variantIDText, addType);

		addPayload(addToCart_Input_Pojo);
	}

	@When("User sends {string} request to the AddToCart endpoint")
	public void user_sends_request_to_the_add_to_cart_endpoint(String string) {
		response = sendRequest(string, Endpoint.ADD_TO_CART);
		int statusCode = getStatusCode(response);
		GlobalData.getGlobalDataInstance().setStatusCode(statusCode);
	}

	@Then("Verify the AddToCart response message is {string}")
	public void verify_the_add_to_cart_response_message_is(String expMsg) {
		AddToCart_Output_Pojo addToCart_Input_Pojo = response.as(AddToCart_Output_Pojo.class);
		String actMsg = addToCart_Input_Pojo.getMessage();
		Assert.assertEquals("Verify AddToCart response message", expMsg, actMsg);

	}

	// View Cart Items
	@Given("User sets bearer authorization for GetCart endpoint")
	public void user_sets_bearer_authorization_for_get_cart_endpoint() {

		List<Header> lstHeader = new ArrayList<>();
		lstHeader.add(new Header("accept", "application/json"));
		lstHeader.add(new Header("Authorization", "Bearer " + logToken));

		Headers headers = new Headers(lstHeader);
		addHeaders(headers);

	}

	@When("User sends {string} request to the GetCart endpoint")
	public void user_sends_request_to_the_get_cart_endpoint(String type) {

		response = sendRequest(type, Endpoint.GET_CART_ITEMS);
		int statusCode = getStatusCode(response);
		GlobalData.getGlobalDataInstance().setStatusCode(statusCode);

	}

	@Then("Verify the GetCart response message is {string} Save the cart ID")
	public void verify_the_get_cart_response_message_is_save_the_cart_id(String expMsg) {

		GetCartItems_Output_Pojo getCartItems_Output_Pojo = response.as(GetCartItems_Output_Pojo.class);
		String actMsg = getCartItems_Output_Pojo.getMessage();
		Assert.assertEquals("Verify GetCart response message", expMsg, actMsg);

		ArrayList<com.omrbranch.pojo.getcartitems.Datum> data = getCartItems_Output_Pojo.getData();
		for (com.omrbranch.pojo.getcartitems.Datum datum : data) {
			
		
		
	//			ArrayList<com.omrbranch.pojo.getcartItems.Datum> data = getCartItems_Output_Pojo.getData();
		//for (com.omrbranch.pojo.getCartItems.Datum datum : data) {

			int cartId = datum.getCart_id();
			System.out.println(cartId);

			Assert.assertTrue("Verify cart ID is generated", cartId > 0);
			GlobalData.getGlobalDataInstance().setCartId(cartId);
			break;

		}

	}

	// Set address for checkout

	@Given("User sets bearer authorization for SetAddress endpoint")
	public void user_sets_bearer_authorization_for_set_address_endpoint() {
		List<Header> lstHeader = new ArrayList<>();
		lstHeader.add(new Header("accept", "application/json"));
		lstHeader.add(new Header("Authorization", "Bearer " + logToken));
		lstHeader.add(new Header("Content-Type", "application/json"));

		Headers headers = new Headers(lstHeader);
		addHeaders(headers);

	}

	@When("User sets request body with saved address ID and cart ID")
	public void user_sets_request_body_with_saved_address_id_and_cart_id() {

		String addressIdText = String.valueOf(GlobalData.getGlobalDataInstance().getAddressId());
		String cartIdText = String.valueOf(GlobalData.getGlobalDataInstance().getCartId());

	System.out.println(addressIdText);
		System.out.println(cartIdText);

		SetAddress_Input_Pojo setAddress_Input_Pojo = PayloadObjectManager.getPayloadInstance().getSetAddressPayload()
				.createSetAddress(addressIdText, cartIdText);

		addPayload(setAddress_Input_Pojo);
	}

	@When("User sends {string} request to the SetAddress endpoint")
	public void user_sends_request_to_the_set_address_endpoint(String type) {
		response = sendRequest(type, Endpoint.SET_ADDRESS);
		int statusCode = getStatusCode(response);
		GlobalData.getGlobalDataInstance().setStatusCode(statusCode);

	}

	@Then("Verify the SetAddress response message is {string}")
	public void verify_the_set_address_response_message_is(String expMsg) {
		SetAddress_Ouput_Pojo setAddress_Ouput_Pojo = response.as(SetAddress_Ouput_Pojo.class);
		String actMsg = setAddress_Ouput_Pojo.getMessage();
		Assert.assertEquals("Verify SetAddress response message", expMsg, actMsg);
	}

	// Create an order with payment

	@Given("User sets bearer authorization for CreateOrder endpoint")
	public void user_sets_bearer_authorization_for_create_order_endpoint() {
		List<Header> lstHeader = new ArrayList<>();
		lstHeader.add(new Header("accept", "application/json"));
		lstHeader.add(new Header("Authorization", "Bearer " + logToken));
		lstHeader.add(new Header("Content-Type", "application/json"));

		Headers headers = new Headers(lstHeader);
		addHeaders(headers);
	}

	@When("User sets request body to create order with payment details {string},{string},{string},{string},{string},{string}")
	public void user_sets_request_body_to_create_order_with_payment_details(String payment_method, String card_no,
			String card_type, String month, String year, String cvv, io.cucumber.datatable.DataTable dataTable) {

		List<Map<String, String>> map = dataTable.asMaps(String.class, String.class);

		CreateOrder_Input_Pojo createOrder_Input_Pojo = PayloadObjectManager.getPayloadInstance()
				.getCreateOrderPayload().createOrder(map.get(0).get("payment_method"), map.get(0).get("card_no"),
						map.get(0).get("card_type"), map.get(0).get("year"), map.get(0).get("month"),
						map.get(0).get("cvv"));

		System.out.println(createOrder_Input_Pojo);
		addPayload(createOrder_Input_Pojo);
	}


	@When("User sends {string} request to the CreateOrder endpoint")
	public void user_sends_request_to_the_create_order_endpoint(String type) {
		response = sendRequest(type, Endpoint.CREATE_ORDER);
		int statusCode = getStatusCode(response);
		GlobalData.getGlobalDataInstance().setStatusCode(statusCode);
	}

	@Then("Verify the CreateOrder response message is {string}")
	public void verify_the_create_order_response_message_is(String expMsg) {
		CreateOrder_Output_Pojo createOrder_Output_Pojo = response.as(CreateOrder_Output_Pojo.class);
		String actMsg = createOrder_Output_Pojo.getMessage();
		Assert.assertEquals("Verify CreateOrder response message", expMsg, actMsg);

	}

}