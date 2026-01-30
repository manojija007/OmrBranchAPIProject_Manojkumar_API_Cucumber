package com.omrbranch.endpoints;

/**
 * =============================================================== Interface
 * Name : Endpoint Project : OMR Branch API Automation Framework
 *
 * Purpose: - Central place to maintain all API endpoint paths - Uses relative
 * URLs (baseURI handled by BaseClass)
 *
 * Author : Velmurugan
 * ===============================================================
 */
//public interface Endpoint {
//	String LOGIN_BASIC_AUTH = "/api/postmanBasicAuthLogin";
//	String STATE_LIST = "/api/stateList";
//	public static final String CITY_LIST = "/api/cityList";
//	String ADD_USER_ADDRESS = "/api/addUserAddress";
//	String Set_Address = "/api/setAddress";
//	String Create_Order = "/api/createOrder";
//	String Get_Cart_Items = "/api/getCartItems";
//	String Add_To_Cart = "/api/addToCart";
//	String Clear_Cart_List = "api/clearCart";
//	String product_List = "api/productList";
//	String category_List = "api/categoryList";
//	String Update_User_Address = "api/updateUserAddress";
//	String Get_User_Address = "/api/getUserAddress";
//	String delete_User_Address = "/api/deleteAddress";
//	String manager_user_favourite = "/api/manageUserFavourite";
//	String get_user_favourites = "/api/getUserFavourites";
//
//}

public interface Endpoint {
    String LOGIN_BASIC_AUTH = "/api/postmanBasicAuthLogin";
    String STATE_LIST = "/api/stateList";
    String CITY_LIST = "/api/cityList";
    String ADD_USER_ADDRESS = "/api/addUserAddress";
    String SET_ADDRESS = "/api/setAddress";
    String CREATE_ORDER = "/api/createOrder";
    String GET_CART_ITEMS = "/api/getCartItems";
    String ADD_TO_CART = "/api/addToCart";
    String CLEAR_CART_LIST = "/api/clearCart";
    String PRODUCT_LIST = "/api/productList";
    String CATEGORY_LIST = "/api/categoryList";
    String UPDATE_USER_ADDRESS = "/api/updateUserAddress";
    String GET_USER_ADDRESS = "/api/getUserAddress";
    String DELETE_USER_ADDRESS = "/api/deleteAddress";
    String MANAGE_USER_FAVOURITE = "/api/manageUserFavourite";
    String GET_USER_FAVOURITES = "/api/getUserFavourites";
    String CHANGE_PROFILE_PICTURE = "/api/changeProfilePic";
    String SEARCH_PRODUCT = "/api/searchProduct";
    String GET_PRODUCT_SEARCH_RESULT = "/api/getSearchResult";
}
