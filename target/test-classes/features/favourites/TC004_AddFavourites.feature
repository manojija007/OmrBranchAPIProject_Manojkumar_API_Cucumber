@feature4 @regression
Feature: Manage User Favourites via API

  This feature handles the flow of adding a product to the user's favourites list,
  including login, fetching category, subcategory, product, and verifying the addition.

  @Login @smoke
  Scenario: Get User logtoken from login endpoint
    Given User adds required login headers
    When User adds basic authentication for login
    And User sends "POST" request to the login endpoint
    Then User should verify the status code is 200
    And User should verify the login response body contains firstName "Manojkumar" and save the logtoken

  @favourites @category  @smoke
  Scenario: Get product category and subcategory
    Given User sets headers for category list
    When User sends "GET" request to the CategoryList endpoint
    Then User should verify the status code is 200
    And User should verify the category response message is "OK"
    And User saves the category ID for main category "Grocery" and subcategory "Fruit & Nuts"

  @favourites @products @cat @smoke 
  Scenario: Get product list from selected category
    Given User sets headers for product list
    And User sets request body with saved category ID and page number "0"
    When User sends "POST" request to the ProductList endpoint
    Then User should verify the status code is 200
    And User should verify the product response message is "OK"
    And User saves the variant ID of product "Happilo 100% Natural Premium California Almonds | Premium Badam Giri"
    And User should verify the specification is "500 g"


  @Login @smoke
  Scenario: Get User logtoken from login endpoint
    Given User adds required login headers
    When User adds basic authentication for login
    And User sends "POST" request to the login endpoint
    Then User should verify the status code is 200
    And User should verify the login response body contains firstName "Manojkumar" and save the logtoken

  @favourites @add @smoke 
  Scenario: Add product to favourites
    Given User sets bearer authorization using the saved logtoken for favourites
    And User sets request body with saved variant ID to add to favourites
    When User sends "POST" request to the ManageFavourites endpoint
    Then User should verify the status code is 200
    And verify the favourites response message is  "Product added in your favorites"

  @favourites @get @smoke 
  Scenario: Get user favourites list
    Given User sets bearer authorization using the saved logtoken for get favourites
    When User sends "GET" request to the GetFavourites endpoint
    Then User should verify the status code is 200
    And User should verify the favourites list response message is "Favorites list fetched successfully."
