package com.omrbranch.stepdefinition;

import org.junit.Assert;

import com.omrbranch.endpoints.Endpoint;
import com.omrbranch.globaldata.GlobalData;
import com.omrbranch.pojo.postmanbasicauthlogin.PostmanBasicAuthLogin_Output_Pojo;
import com.omrbranch.utility.BaseClass;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class TC001_LoginStep extends BaseClass {

	Response response;

	@Given("User adds required login headers")
	public void user_adds_required_login_headers() {
		addHeader("accept", "application/json");
	}

	@When("User adds basic authentication for login")
	public void user_adds_basic_authentication_for_login() {
		addBasicAuth(getUserName(), getPassword());
	}

	@When("User sends {string} request to the login endpoint")
	public void user_sends_request_to_the_login_endpoint(String type) {
		response = sendRequest(type, Endpoint.LOGIN_BASIC_AUTH);
		int statusCode = getStatusCode(response);
		GlobalData.getGlobalDataInstance().setStatusCode(statusCode);
	}

	@Then("User should verify the login response body contains firstName {string} and save the logtoken")
	public void user_should_verify_the_login_response_body_contains_first_name_and_save_the_logtoken(
			String expFirstName) {
		PostmanBasicAuthLogin_Output_Pojo postmanBasicAuthLogin_Output_Pojo = response
				.as(PostmanBasicAuthLogin_Output_Pojo.class);
		String actFirstName = postmanBasicAuthLogin_Output_Pojo.getData().getFirst_name();
		Assert.assertEquals("Verify first name login response", expFirstName, actFirstName);

		// Save token to GlobalData
		String logToken = postmanBasicAuthLogin_Output_Pojo.getData().getLogtoken();

		Assert.assertNotNull("Login token is null", logToken);

		GlobalData.getGlobalDataInstance().setLogToken(logToken);

		System.out.println("Saved logToken in GlobalData: " + logToken);
	}

}
