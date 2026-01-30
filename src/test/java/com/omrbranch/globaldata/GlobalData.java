package com.omrbranch.globaldata;

import lombok.Data;

/**
 * GlobalData is a Singleton class used to store and share runtime data across
 * API test executions.
 *
 * <p>
 * This class acts as an in-memory data holder for values such as authentication
 * tokens, response status codes, and dynamically fetched IDs (state, city,
 * etc.).
 * </p>
 *
 * <p>
 * It ensures that only one instance is created and shared throughout the
 * framework using the Singleton Design Pattern.
 * </p>
 *
 * <b>Used in:</b>
 * <ul>
 * <li>Step Definitions</li>
 * <li>Hooks</li>
 * <li>Payload Builders</li>
 * </ul>
 *
 * @author Vel
 */


@Data
public class GlobalData {

	/**
	 * Static reference to hold the single instance of GlobalData.
	 */
	private static GlobalData globalDataInstance;

	/**
	 * Private constructor to prevent object creation from outside the class.
	 */
	private GlobalData() {

	}

	/**
	 * Stores the HTTP response status code of the last API call.
	 * <p>
	 * Example: 200, 201, 400, 401
	 * </p>
	 */
	private int statusCode;

	/**
	 * Stores the authentication or login token.
	 * <p>
	 * Used for Authorization header in secured APIs
	 * </p>
	 */
	private String logToken;

	/**
	 * Stores the selected state ID fetched from the State List API.
	 */
	private int stateId;

	/**
	 * Stores the state name corresponding to the state ID.
	 * <p>
	 * Example: "Tamil Nadu"
	 * </p>
	 */
	private String stateIdText;

	/**
	 * Stores the selected city ID fetched from the City List API.
	 */
	private int cityId;
	
	
	
	
	
	
	
	
	private int cartId;


	private int addressId;

	private String cityIdText;

	private String countryIdText;

	
	private String addressIdText;
	
	private int childCatagoryId;
	
	private String childCatagoryIdText;
	
	private int variantID;
	
	private String variantIDText;
	
	private String specifications;
	
	private int productId;
	
	private String productIdText;
	
	private int category_id;
	
	private String categoryIdText;
	
	private String setCartIdTxt;

	private String productType;

	
	
	
	
	/**
	 * Returns the singleton instance of GlobalData.
	 *
	 * <p>
	 * This method is synchronized to ensure thread safety during parallel test
	 * execution.
	 * </p>
	 *
	 * @return GlobalData singleton instance
	 */
	public synchronized static GlobalData getGlobalDataInstance() {
		if (globalDataInstance == null) {
			globalDataInstance = new GlobalData();
		}
		return globalDataInstance;
	}







	
	
	
}