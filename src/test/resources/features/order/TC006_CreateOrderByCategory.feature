@feature6 @regression
Feature: Search Product and Create Order via API
 This feature validates the flow of searching a product, adding it to the cart,
  setting up address, and successfully creating an order.

  @Login5 @added
  Scenario: Get User logtoken from login endpoint
    Given User adds required login headers
    When User adds basic authentication for login
    And User sends "POST" request to the login endpoint
    Then User should verify the status code is 200
    And User should verify the login response body contains firstName "Manojkumar" and save the logtoken

  @cart @added
  Scenario: Verify cart is already empty
    Given User sets bearer authorization for ClearCart endpoint
    When User sends "GET" request to the ClearCart endpoint
    Then User should verify the status code is 200
    And Verify the ClearCart response message is "Currently Your Cart is empty"

  @search @added
  Scenario: Search for product and save category/product IDs
    Given User sets headers for SearchProduct
    When User sets request body to search for product "Nuts"
    And User sends "POST" request to the SearchProduct endpoint
    Then User should verify the status code is 200
    And Verify the search result includes product name "Happilo 100% Natural Premium California Almonds | Premium Badam Giri in Fruit & Nuts" and Save the category ID and product ID from response

  @SearchResult @added
  Scenario: Retrieve product variant from search resultt
    Given User sets bearer authorization for GetSearchProductList endpoint
    When User sets request body with saved product ID
    And User sends "POST" request to the GetSearchProductList endpoint
    Then User should verify the status code is 200
    And Verify the response includes product with specification "500 g" and save the variant ID

  @cart @add @added
  Scenario: Add searched product to cart
    Given User sets bearer authorization for AddToCart endpoint
    When User sets request body using saved variant ID and pass the type "plus" to add product to cart
    And User sends "POST" request to the AddToCart endpoint
    Then User should verify the status code is 200
    And Verify the AddToCart response message is "Product added in cart"

  @cart @view @added
  Scenario: Get user cart and save cart IdD
    Given User sets bearer authorization for GetCart endpoint
    When User sends "GET" request to the GetCart endpoint
    Then User should verify the status code is 200
    And Verify the GetCart response message is "OK" Save the cart ID

  @StateList @added
  Scenario: Verify User Get StateList through API
    Given User adds headers for StateList
    When User sends "GET" request to StateList endpoint
    Then User should verify the status code is 200
    And User should verify the stateList response message matches "Tamil Nadu" and save the state id

  @CityList @added
  Scenario: Verify User Get City list through API
    Given User adds headers for CityList
    When User adds request body with state id for city list
    And User sends "POST" request to CityList endpoint
    Then User should verify the status code is 200
    And User should verify the cityList response message matches "Yercaud" and save the city id

  @AddAddress @added
  Scenario Outline: Verify add user address through API
    Given User adds headers and bearer authorization for accessing address endpoints
    When User adds request body for add new address "<first_name>","<last_name>","<mobile>","<apartment>","<state>","<city>","<country>","<zipcode>","<address>","<address_type>"
    Then User should verify the status code is 200
    And User sends "POST" request to addUserAddress endpoint
    And User should verify the addUserAddress response message matches "Address added successfully" and save the address id

    Examples:
      | first_name | last_name | mobile     | apartment | state | city | country | zipcode | address         | address_type |
      | Manoj    | Kumar     | 9080060477 | Apt-123   | 35    | 4440 | 101     | 600100  | 123, OMR Street | Home         |

  @address @set @added
  Scenario: Set address for checkout
    Given User sets bearer authorization for SetAddress endpoint
    When User sets request body with saved address ID and cart ID
    And User sends "POST" request to the SetAddress endpoint
    Then User should verify the status code is 200
    And Verify the SetAddress response message is "Cart updated successfully"

  @order @create @added
  Scenario: Create an order with payment
    Given User sets bearer authorization for CreateOrder endpoint
    When User sets request body to create order with payment details "<payment_method>","<card_no>","<card_type>","<month>","<year>","<cvv>"
      | payment_method | card_no          | card_type | month | year | cvv |
      | debit_card     | 5555555555552222 | visa      | March | 2024 | 123 |
    And User sends "POST" request to the CreateOrder endpoint
    Then User should verify the status code is 200
    And Verify the CreateOrder response message is "Order created successfully"