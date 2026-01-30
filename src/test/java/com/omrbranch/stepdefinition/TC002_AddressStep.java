package com.omrbranch.stepdefinition;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import com.omrbranch.endpoints.Endpoint;
import com.omrbranch.globaldata.GlobalData;
import com.omrbranch.objectmanager.PayloadObjectManager;
import com.omrbranch.pojo.adduseraddress.AddUserAddress_Output_Pojo;
import com.omrbranch.pojo.citylist.CityList_Output_Pojo;
import com.omrbranch.pojo.deleteaddress.DeleteAddress_Input_Pojo;
import com.omrbranch.pojo.deleteaddress.DeleteAddress_Output_Pojo;
import com.omrbranch.pojo.getuseraddress.GetUserAddress_Output_Pojo;
import com.omrbranch.pojo.statelist.Datum;
import com.omrbranch.pojo.statelist.StateList_Output_Pojo;
import com.omrbranch.pojo.updateuseraddress.UpdateUserAddress_Input_Pojo;
import com.omrbranch.pojo.updateuseraddress.UpdateUserAdress_Output_Pojo;
import com.omrbranch.utility.BaseClass;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class TC002_AddressStep extends BaseClass {

	Response response;
	String logToken = GlobalData.getGlobalDataInstance().getLogToken();

	@Given("User adds headers for StateList")
	public void user_adds_headers_for_state_list() {

		addHeader("accept", "application/json");

	}

	@When("User sends {string} request to StateList endpoint")
	public void user_sends_request_to_state_list_endpoint(String type) {
		response = sendRequest(type, Endpoint.STATE_LIST);
		int statusCode = getStatusCode(response);
		GlobalData.getGlobalDataInstance().setStatusCode(statusCode);
	}

	@Then("User should verify the stateList response message matches {string} and save the state id")
	public void user_should_verify_the_state_list_response_message_matches_and_save_the_state_id(String expStateName) {

		StateList_Output_Pojo stateList_Output_Pojo = response.as(StateList_Output_Pojo.class);
		ArrayList<Datum> stateList = stateList_Output_Pojo.getData();

		String actStateName = null;
		String actStateId = null;

		for (com.omrbranch.pojo.statelist.Datum eachStateList : stateList) {
			if (eachStateList.getName().equals(expStateName)) {

				actStateName = eachStateList.getName();
				actStateId = String.valueOf(eachStateList.getId());

				// Save to GlobalData
				GlobalData.getGlobalDataInstance().setStateId(eachStateList.getId());
				GlobalData.getGlobalDataInstance().setStateIdText(actStateId);

				break;
			}
		}

		Assert.assertEquals("State name mismatch", expStateName, actStateName);
		Assert.assertNotNull("State ID should not be null", actStateId);

	}

	// City List

	@Given("User adds headers for CityList")
	public void userAddsHeadersForCityList() {

//		2.Add Headers
		List<Header> lstHeader = new ArrayList<>();
		Header h1 = new Header("accept", "application/json");
		Header h2 = new Header("Content-Type", "application/json");
		lstHeader.add(h1);
		lstHeader.add(h2);

		Headers headers = new Headers(lstHeader);
		addHeaders(headers);
	}

	@When("User adds request body with state id for city list")
	public void userAddsRequestBodyWithStateIdForCityList() {

		String stateId = GlobalData.getGlobalDataInstance().getStateIdText();

		System.out.println("CityList request stateId: " + stateId);

		Assert.assertNotNull("StateId is NULL. StateList scenario did not run.", stateId);

		addPayload(PayloadObjectManager.getPayloadInstance().getCityListPayload().createCityList(stateId));
	}

	@When("User sends {string} request to CityList endpoint")
	public void userSendsRequestToCityListEndpoint(String type) {
		response = sendRequest(type, Endpoint.CITY_LIST);
		int statusCode = getStatusCode(response);

		GlobalData.getGlobalDataInstance().setStatusCode(statusCode);
	}

	@Then("User should verify the cityList response message matches {string} and save the city id")
	public void userShouldVerifyTheCityListResponseMessageMatchesAndSaveTheCityId(String expCityName) {
		CityList_Output_Pojo cityList_Output_Pojo = response.as(CityList_Output_Pojo.class);
		ArrayList<com.omrbranch.pojo.citylist.Datum> cityList = cityList_Output_Pojo.getData();
		for (com.omrbranch.pojo.citylist.Datum eachCityList : cityList) {
			String cityName = eachCityList.getName();
			if (cityName.equals(expCityName)) {
				int cityId = eachCityList.getId();
				System.out.println(cityId);
				GlobalData.getGlobalDataInstance().getCityId();
				GlobalData.getGlobalDataInstance().setCityId(cityId);
				Assert.assertEquals("Verify city name", expCityName, cityName);
				break;
			}
		}

	}

	// AddAddress

	@Given("User adds headers and bearer authorization for accessing address endpoints")
	public void user_adds_headers_and_bearer_authorization_for_accessing_address_endpoints() {

		// Ensure token is not null
		Assert.assertNotNull("LogToken is NULL. Login scenario did not run or failed.", logToken);

		List<Header> lstHeader = new ArrayList<>();
		lstHeader.add(new Header("accept", "application/json"));
		lstHeader.add(new Header("Authorization", "Bearer " + logToken));
		lstHeader.add(new Header("Content-Type", "application/json"));

		Headers headers = new Headers(lstHeader);
		addHeaders(headers);
	}

	@When("User adds request body for add new address {string},{string},{string},{string},{string},{string},{string},{string},{string},{string}")
	public void user_adds_request_body_for_add_new_address(String first_name, String last_name, String mobile,
			String apartment, String state, String city, String country, String zipcode, String address,
			String address_type) {

		// Save String values in GlobalData
		GlobalData.getGlobalDataInstance().setStateIdText(state.trim());
		GlobalData.getGlobalDataInstance().setCityIdText(city.trim());
		GlobalData.getGlobalDataInstance().setCountryIdText(country.trim());

		// Convert String → int (for payload usage)
		int stateId = Integer.parseInt(GlobalData.getGlobalDataInstance().getStateIdText());
		int cityId = Integer.parseInt(GlobalData.getGlobalDataInstance().getCityIdText());
		int countryId = Integer.parseInt(GlobalData.getGlobalDataInstance().getCountryIdText());

		Object addAddressPayload = PayloadObjectManager.getPayloadInstance().getAddAddressPayload().createAddressList(
				first_name, last_name, mobile, apartment, stateId, cityId, countryId, zipcode, address, address_type);

		addPayload(addAddressPayload);
	}

	@Then("User sends {string} request to addUserAddress endpoint")
	public void user_sends_request_to_add_user_address_endpoint(String type) {

		response = sendRequest(type, Endpoint.ADD_USER_ADDRESS);

		int statusCode = getStatusCode(response);
		GlobalData.getGlobalDataInstance().setStatusCode(statusCode);
	}

	@Then("User should verify the addUserAddress response message matches {string} and save the address id")
	public void user_should_verify_the_add_user_address_response_message_matches_and_save_the_address_id(
			String expMessage) {

		// Convert response to POJO
		AddUserAddress_Output_Pojo addUserAddress_Output_Pojo = response.as(AddUserAddress_Output_Pojo.class);

		// Verify message
		String actMessage = addUserAddress_Output_Pojo.getMessage();
		Assert.assertEquals("Verify add address message", expMessage, actMessage);

		// Save address id
		int addressId = addUserAddress_Output_Pojo.getAddress_id();
		GlobalData.getGlobalDataInstance().setAddressId(addressId);
		GlobalData.getGlobalDataInstance().setAddressIdText(String.valueOf(addressId));
		Assert.assertTrue("Address ID should be greater than 0", addressId > 0);

	}

	// Update Address

	@When("User adds request body to update address {string},{string},{string},{string},{string},{string},{string},{string},{string},{string}")
	public void user_adds_request_body_to_update_address(String first_name, String last_name, String mobile,
			String apartment, String state, String city, String country, String zipcode, String address,
			String address_type) {

		int stateId = GlobalData.getGlobalDataInstance().getStateId();
		int cityId = GlobalData.getGlobalDataInstance().getCityId();
		int countryId = Integer.parseInt(GlobalData.getGlobalDataInstance().getCountryIdText());
		String addressIdText = GlobalData.getGlobalDataInstance().getAddressIdText();

		UpdateUserAddress_Input_Pojo payload = PayloadObjectManager.getPayloadInstance().getUpdateUserAddressPayload()
				.createUpdateUserAddress(addressIdText, first_name, last_name, mobile, apartment, stateId, cityId,
						countryId, zipcode, address, address_type);

		addPayload(payload);
	}

	@When("User sends {string} request to updateUserAddress endpoint")
	public void user_sends_request_to_update_user_address_endpoint(String type) {

		response = sendRequest(type, Endpoint.UPDATE_USER_ADDRESS);
		int statusCode = getStatusCode(response);
		GlobalData.getGlobalDataInstance().setStatusCode(statusCode);
	}

	@Then("User should verify the updateUserAddress response message matches {string}")
	public void user_should_verify_the_update_user_address_response_message_matches(String expMessage) {

		UpdateUserAdress_Output_Pojo updateUserAddress_Output_Pojo = response.as(UpdateUserAdress_Output_Pojo.class);
		String actualMessage = updateUserAddress_Output_Pojo.getMessage();
		Assert.assertEquals("Verify update address message", expMessage, actualMessage);

	}

	// Get Address

	@Given("User adds headers and bearer authorization for accessing get address endpoints")
	public void user_adds_headers_and_bearer_authorization_for_accessing_get_address_endpoints() {

		List<Header> lstHeaders = new ArrayList<>();
		Header h1 = new Header("accept", "application/json");
		Header h2 = new Header("Authorization", "Bearer " + logToken);

		lstHeaders.add(h1);
		lstHeaders.add(h2);

		Headers headers = new Headers(lstHeaders);
		addHeaders(headers);

	}

	@When("User sends {string} request to GetUserAddress endpoint")
	public void user_sends_request_to_get_user_address_endpoint(String type) {

		response = sendRequest(type, Endpoint.GET_USER_ADDRESS);
		int statusCode = response.getStatusCode();
		GlobalData.getGlobalDataInstance().setStatusCode(statusCode);

	}

	@Then("User should verify the GetUserAddress response message matches {string}")
	public void user_should_verify_the_get_user_address_response_message_matches(String expMessage) {

		GetUserAddress_Output_Pojo getUserAddress_Output_Pojo = response.as(GetUserAddress_Output_Pojo.class);
		String actualMessage = getUserAddress_Output_Pojo.getMessage();
		Assert.assertEquals("Verify the get address message", expMessage, actualMessage);
	}

	// Delete Address

	@When("User adds request body with address id")
	public void user_adds_request_body_with_address_id() {

//		List<Header> lstHeaders = new ArrayList<>();
//		Header h1 = new Header("accept", "application/json");
//		Header h2 = new Header("Authorization", "Bearer " + logToken);
//		Header h3 = new Header("Content-Type", "application/json");
//
//		lstHeaders.add(h1);
//		lstHeaders.add(h2);
//		lstHeaders.add(h3);
//
//		Headers headers = new Headers(lstHeaders);
//		addPayload(headers);
		
		String addressIdText = GlobalData.getGlobalDataInstance().getAddressIdText();

		DeleteAddress_Input_Pojo payload = PayloadObjectManager.getPayloadInstance().getDeleteAddressPayload()
				.deleteAddressList(addressIdText);
		
		addPayload(payload);
		
		

	}

	@When("User sends {string} request to DeleteAddress endpoint")
	public void user_sends_request_to_delete_address_endpoint(String type) {
		
		response = sendRequest(type,Endpoint.DELETE_USER_ADDRESS);
		response.getStatusCode();
		
	}

	@Then("User should verify the DeleteAddress response message matches {string}")
	public void user_should_verify_the_delete_address_response_message_matches(String expMessage) {
		DeleteAddress_Output_Pojo deleteAddress_Output_Pojo = response.as(DeleteAddress_Output_Pojo.class);
		String actMsg = deleteAddress_Output_Pojo.getMessage();
		Assert.assertEquals("Verify delete address Message",expMessage, actMsg);
	}

}