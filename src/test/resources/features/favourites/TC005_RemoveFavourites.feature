@feature5 @regression
Feature: Remove User Favourites via API
 This feature handles removing a product from the user's favourites list
  and verifying it no longer appears in the favourites.

  @Login @smoke
  Scenario: Get User logtoken from login endpoint
    Given User adds required login headers
    When User adds basic authentication for login
    And User sends "POST" request to the login endpoint
    Then User should verify the status code is 200
    And User should verify the login response body contains firstName "Manojkumar" and save the logtoken

  @favourites @remove @smoke
  Scenario: from user  favourites list
    Given User sets bearer authorization for managing favourites
    And User sets request body with saved variant ID to remove from favourites
    When User sends "POST" request to the ManageFavourites endpoint
    Then Verify the response status code is 200
    And Verify the Remove product response message is "Product removed from your favorites"

  @favourites @empty @smoke
  Scenario: Verify removed product does not appear in favourites list
    Given User sets bearer authorization for retrieving favourites
    When User sends "GET" request to the GetFavourites endpoint
    Then Verify the response status code is 400
    And Verify the favourites list response message is "No favorites found"
