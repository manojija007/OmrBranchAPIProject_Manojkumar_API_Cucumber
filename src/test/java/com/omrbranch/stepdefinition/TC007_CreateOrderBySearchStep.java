package com.omrbranch.stepdefinition;

import java.util.List;
import java.util.Map;

import com.omrbranch.endpoints.Endpoint;
import com.omrbranch.globaldata.GlobalData;
import com.omrbranch.objectmanager.PayloadObjectManager;
import com.omrbranch.pojo.createorder.CreateOrder_Input_Pojo;
import com.omrbranch.utility.BaseClass;

import io.cucumber.java.en.When;

public class TC007_CreateOrderBySearchStep extends BaseClass {

//	@When("User sets request body using saved variant ID")
//	public void userSetsRequestBodyUsingSavedVariantID() {
//
//	io.cucumber.junit.UndefinedStepException: The step 'User sets request body to create order with payment details "<payment_method >", "<CardNo>", "<CardName>", "<Month>", "<Year>", "<CVV>"' is undefined.
//	You can implement this step using the snippet(s) below:
////
//		@When("User sets request body to create order with payment details {string}, {string}, {string}, {string}, {string}, {string}")
//		public void userSetsRequestBodyToCreateOrderWithPaymentDetails( String paymentMethodPlaceholder,
//    String cardNoPlaceholder,
//    String cardNamePlaceholder,
//    String monthPlaceholder,
//    String yearPlaceholder,
//    String cvvPlaceholder,
//    io.cucumber.datatable.DataTable dataTable) {
//		    // Write code here that turns the phrase above into concrete actions
//		    // For automatic transformation, change DataTable to one of
//		    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
//		    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
//		    // Double, Byte, Short, Long, BigInteger or BigDecimal.
//		    //
//		    // For other transformations you can register a DataTableType.
//			List<Map<String, String>> asMaps = dataTable.asMaps();
//			Map<String, String> map = asMaps.get(0);
//			String cardType = map.get("Select card");
//			String cardNo = map.get("CardNo");
//			String paymentMethod = map.get("payment_method");
//			String month = map.get("Month");
//			String year = map.get("Year");
//			String cvv = map.get("CVV");
//
//			CreateOrder_Input_Pojo createOrder_Input_Pojo = new CreateOrder_Input_Pojo(paymentMethod, cardNo, cardType,
//					year, month, cvv);
//			addPayload(createOrder_Input_Pojo);
//		}

//	@When("User sets request body to create order with payment details")
//	public void userSetsRequestBodyToCreateOrderWithPaymentDetails(io.cucumber.datatable.DataTable dataTable) {
//
//		List<Map<String, String>> asMaps = dataTable.asMaps();
//		Map<String, String> map = asMaps.get(0);
//
//		String cardType = map.get("Select card");
//		String cardNo = map.get("CardNo");
//		String paymentMethod = map.get("payment_method");
//		String month = map.get("Month");
//		String year = map.get("Year");
//		String cvv = map.get("CVV");
//
//		CreateOrder_Input_Pojo createOrder_Input_Pojo = new CreateOrder_Input_Pojo(paymentMethod, cardNo, cardType,
//				year, month, cvv);
//		addPayload(createOrder_Input_Pojo);
//		
//
//	}
//
//	@When("User sets request body to create order with payment details {string}, {string}, {string}, {string}, {string}, {string}")
//	public void userSetsRequestBodyToCreateOrderWithPaymentDetails(
//	        String paymentMethodPlaceholder,
//	        String cardNoPlaceholder,
//	        String cardNamePlaceholder,
//	        String monthPlaceholder,
//	        String yearPlaceholder,
//	        String cvvPlaceholder,
//	        io.cucumber.datatable.DataTable dataTable) {
//
//	    // Convert DataTable to List of Maps
//	    List<Map<String, String>> asMaps = dataTable.asMaps(String.class, String.class);
//
//	    // Use first row (since only one order is created)
//	    Map<String, String> map = asMaps.get(0);
//
//	    // Read values from DataTable (NOT from placeholders)
//	    String paymentMethod = map.get("payment_method");
//	    String cardNo = map.get("CardNo");
//	    String cardType = map.get("CardName"); // or "Select card" if that is your column
//	    String month = map.get("Month");
//	    String year = map.get("Year");
//	    String cvv = map.get("CVV");
//
//	    // Create payload object
//	    CreateOrder_Input_Pojo createOrder_Input_Pojo =
//	            new CreateOrder_Input_Pojo(
//	                    paymentMethod,
//	                    cardNo,
//	                    cardType,
//	                    year,
//	                    month,
//	                    cvv
//	            );
//
//	    // Attach payload to request
//	    addPayload(createOrder_Input_Pojo);
//	}
//
//
	
	@When("User sets request body to create order with payment details")
	public void user_sets_request_body_to_create_order_with_payment_details(io.cucumber.datatable.DataTable dataTable) {

	    // Convert table to list of maps
	    List<Map<String, String>> mapList = dataTable.asMaps(String.class, String.class);
	    Map<String, String> map = mapList.get(0);

	    // Extract values from table
	    String paymentMethod = map.get("payment_method"); // matches table header exactly
	    String cardNo = map.get("CardNo");
	    String cardName = map.get("CardName");
	    String month = map.get("Month");
	    String year = map.get("Year");
	    String cvv = map.get("CVV");

	    // Create POJO
	    CreateOrder_Input_Pojo createOrder_Input_Pojo = PayloadObjectManager.getPayloadInstance()
	        .getCreateOrderPayload()
	        .createOrder(paymentMethod, cardNo, cardName, year, month, cvv);

	    // Add payload for request
	    addPayload(createOrder_Input_Pojo);
	}

	@When("User sends {string} request to the createorder endpoint")
	public void userSendsRequestToTheCreateorderEndpoint(String type) {
		 response = sendRequest(type, Endpoint.CREATE_ORDER);
		 int statusCode = getStatusCode(response);
		 GlobalData.getGlobalDataInstance().setStatusCode(statusCode);
	
	}



}
