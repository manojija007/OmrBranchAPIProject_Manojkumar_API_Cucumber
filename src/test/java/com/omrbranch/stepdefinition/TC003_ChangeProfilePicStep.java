package com.omrbranch.stepdefinition;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import com.omrbranch.endpoints.Endpoint;
import com.omrbranch.globaldata.GlobalData;
import com.omrbranch.pojo.changeprofilepicture.ChangeProfilePicture_Output_Pojo;
import com.omrbranch.utility.BaseClass;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class TC003_ChangeProfilePicStep extends BaseClass {
	
	Response response;
	String logToken = GlobalData.getGlobalDataInstance().getLogToken();
	
	
	@Given("User sets bearer authorization using the saved logtoken for profile picture endpoint")
	public void user_sets_bearer_authorization_using_the_saved_logtoken_for_profile_picture_endpoint() {
		
		List<Header> lstHeader = new ArrayList<>();
		Header h1 = new Header("accept", "application/json");
		Header h2 = new Header("Content-Type", "multipart/form-data");
		Header h3 = new Header("Authorization", " Bearer " + logToken);
		lstHeader.add(h1);
		lstHeader.add(h2);
		lstHeader.add(h3);

		Headers headers = new Headers(lstHeader);
		addHeaders(headers);
		
	}

	@Given("User sets multipart request body with valid image file for profile update")
	public void user_sets_multipart_request_body_with_valid_image_file_for_profile_update() {
		
		addMultipartFile("profile_picture", "src\\test\\resources\\Files\\Zoro.jpg");
		
	}

	@When("User sends {string} request to the ChangeProfile endpoint")
	public void user_sends_request_to_the_change_profile_endpoint(String type) {
		
		response = sendRequest(type, Endpoint.CHANGE_PROFILE_PICTURE);
		response.getStatusCode();
	}

	@Then("User should verify the response message is {string}")
	public void user_should_verify_the_response_message_is(String expMsg) {
		ChangeProfilePicture_Output_Pojo changeProfilePicture_Output_Pojo = response.as(ChangeProfilePicture_Output_Pojo.class);
		String actMessage = changeProfilePicture_Output_Pojo.getMessage();
		Assert.assertEquals("Verify Change Profile Picture",expMsg,actMessage);
		
	}


}
