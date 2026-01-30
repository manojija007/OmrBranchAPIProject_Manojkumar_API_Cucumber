package com.omrbranch.utility;

import java.io.File;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * =============================================================== Class Name :
 * BaseClass Project : OMR Branch API Automation Framework
 *
 * Purpose: -> Loads Config.properties once (static init) -> Initializes
 * RestAssured.baseURI based on env (dev/qa/uat/prod) -> Provides reusable
 * utilities for: -> Request initialization -> Headers / Authentication ->
 * Payload handling -> Request execution -> Response utilities -> Logging
 *
 * Notes: -> Base URI is selected from Config.properties using key:
 * base.uri.<env> -> Environment can be overridden using JVM arg: -Denv=qa
 *
 * Author : Velmurugan
 * ===============================================================
 */
public class BaseClass {

	/** Logger for framework-level logs */
	private static final Logger logger = LogManager.getLogger(BaseClass.class);

	/** Properties loaded from Config.properties (loaded once) */
	private static final Properties properties = new Properties();

	/** Default environment if env key is missing */
	private static final String DEFAULT_ENV = "qa";

	/** Default Base URI if env-based base uri is missing */
	private static final String DEFAULT_BASE_URI = "https://www.omrbranch.com";

	/** Rest-Assured request object */
	protected RequestSpecification reqSpec;

	/** Rest-Assured response object */
	protected Response response;
	
	

	// ===========================================================
	// STATIC INIT (RUNS ONCE)
	// ===========================================================

	static {
		try {
			loadConfigOnce();
		} catch (Exception e) {
			logger.error("Config load failed during static init", e);
		}

		try {
			initBaseUriByEnv();
		} catch (Exception e) {
			logger.error("BaseURI init failed during static init", e);
			RestAssured.baseURI = DEFAULT_BASE_URI;
		}
	}

	// ===========================================================
	// PROJECT PATH
	// ===========================================================

	/**
	 * Returns the current project root directory path.
	 *
	 * @return project path
	 */
	public static String getProjectPath() {
		return System.getProperty("user.dir");
	}

	// ===========================================================
	// CONFIG LOADING
	// ===========================================================

	/**
	 * Loads Config.properties once when framework starts.
	 */
	private static void loadConfigOnce() {
		String configPath = getProjectPath() + File.separator + "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "config" + File.separator + "Config.properties";

		try (InputStream is = new FileInputStream(configPath)) {
			properties.load(is);
			logger.info("Config loaded successfully: {}", configPath);
		} catch (Exception e) {
			logger.error("Unable to load Config.properties from {} | Reason: {}", configPath, e.getMessage());
		}
	}

	/**
	 * Initializes RestAssured.baseURI based on selected environment.
	 */
	private static void initBaseUriByEnv() {

		String env = System.getProperty("env");
		if (env == null || env.trim().isEmpty())
			env = getProperty("env");
		if (env == null || env.trim().isEmpty())
			env = DEFAULT_ENV;

		env = env.trim().toLowerCase();

		String baseUriKey = "base.uri." + env;
		String baseUri = getProperty(baseUriKey);

		if (baseUri == null || baseUri.trim().isEmpty()) {
			baseUri = DEFAULT_BASE_URI;
			logger.warn("Base URI not found for key '{}' -> using default", baseUriKey);
		}

		RestAssured.baseURI = baseUri;
		logger.info("ENV Selected: {}", env);
		logger.info("RestAssured.baseURI initialized: {}", RestAssured.baseURI);
	}

	// ===========================================================
	// CONFIG GETTERS
	// ===========================================================

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	public static String getEnv() {
		String env = System.getProperty("env");
		if (env == null || env.trim().isEmpty())
			env = getProperty("env");
		if (env == null || env.trim().isEmpty())
			env = DEFAULT_ENV;
		return env.trim().toLowerCase();
	}

	public static String getUserName() {
		return getProperty("userName." + getEnv());
	}

	public static String getPassword() {
		return getProperty("password." + getEnv());
	}

	public static String getJsonOutputPath() {
		return getProperty("jsonpath");
	}

	public static String getJvmReportPath() {
		return getProperty("jvmPath");
	}

	// ===========================================================
	// REQUEST INITIALIZATION
	// ===========================================================

	public void initRequest() {
		reqSpec = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON);
	}

	public void initRequest(ContentType contentType, ContentType acceptType) {
		reqSpec = RestAssured.given();
		if (contentType != null)
			reqSpec.contentType(contentType);
		if (acceptType != null)
			reqSpec.accept(acceptType);
	}

	private void ensureRequestInitialized() {
		if (reqSpec == null) {
			initRequest();
		}
	}

	public void clear() {
		reqSpec = null;
		response = null;
	}

	// ===========================================================
	// HEADERS & AUTH
	// ===========================================================

	public void addHeader(String key, String value) {
		ensureRequestInitialized();
		reqSpec.header(key, value);
	}

	public RequestSpecification addBasicAuth(String userName, String password) {
		ensureRequestInitialized();

		if (userName == null || userName.trim().isEmpty()) {
			throw new IllegalArgumentException("Username cannot be null or empty");
		}
		if (password == null || password.trim().isEmpty()) {
			throw new IllegalArgumentException("Password cannot be null or empty");
		}

		reqSpec.auth().preemptive().basic(userName, password);
		return reqSpec;
	}

	public void addHeaders(Headers headers) {
		ensureRequestInitialized();
		reqSpec.headers(headers);
	}

	// ===========================================================
	// PAYLOAD
	// ===========================================================

	public void addPayload(String body) {
		ensureRequestInitialized();
		reqSpec.body(body);
	}

	public void addPayload(Object body) {
		ensureRequestInitialized();
		reqSpec.body(body);
	}

	// ===========================================================
	// MULTIPART
	// ===========================================================
	public void addMultipartFile(String fieldName, String filePath) {
		File file = new File(filePath);
		reqSpec = reqSpec.multiPart(fieldName, file);
	}

	// ===========================================================
	// REQUEST EXECUTION
	// ===========================================================

	public Response sendRequest(String type, String endpoint) {
		ensureRequestInitialized();

		if (type == null || type.trim().isEmpty())
			throw new IllegalArgumentException("HTTP method cannot be null or empty");

		if (endpoint == null || endpoint.trim().isEmpty())
			throw new IllegalArgumentException("Endpoint cannot be null or empty");

		switch (type.trim().toUpperCase()) {
		case "GET":
			response = reqSpec.get(endpoint);
			break;
		case "POST":
			response = reqSpec.post(endpoint);
			break;
		case "PUT":
			response = reqSpec.put(endpoint);
			break;
		case "PATCH":
			response = reqSpec.patch(endpoint);
			break;
		case "DELETE":
			response = reqSpec.delete(endpoint);
			break;
		default:
			throw new IllegalArgumentException("Invalid HTTP method: " + type);
		}
		return response;
	}

	// ===========================================================
	// RESPONSE UTILS
	// ===========================================================

	public int getStatusCode(Response response) {
		int code = response.getStatusCode();
		logger.info("Status Code: {}", code);
		return code;
	}

	public String getResBodyAsString(Response response) {
		return response.getBody().asString();
	}

	public String getResBodyAsPrettyString(Response response) {
		return response.getBody().asPrettyString();
	}

	public String getJsonPath(Response response, String exp) {
		Object val = response.jsonPath().get(exp);
		return val == null ? null : val.toString();
	}

	// ===========================================================
	// LOGGING
	// ===========================================================

	public void logRequest() {
		ensureRequestInitialized();
		reqSpec.log().all();
	}

	public void logResponse() {
		if (response != null) {
			response.then().log().all();
		} else {
			logger.warn("Response is null. Nothing to log.");
		}
	}
}
