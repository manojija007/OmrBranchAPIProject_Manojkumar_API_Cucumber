package com.omrbranch.objectmanager;

import com.omrbranch.payload.addtocart.AddToCartPayload;
import com.omrbranch.payload.adduseraddress.AddAddressPayload;
import com.omrbranch.payload.citylist.CityListPayload;
import com.omrbranch.payload.createorder.CreateOrderPayload;
import com.omrbranch.payload.deleteaddess.DeleteAddressPayload;
import com.omrbranch.payload.getsearchresult.GetSearchResult;
import com.omrbranch.payload.manageuserfavourite.ManageUserFavouritesPayload;
import com.omrbranch.payload.productlist.ProductListPayload;
import com.omrbranch.payload.searchproduct.SearchProductPayload;
import com.omrbranch.payload.setaddress.SetAddressPayload;
import com.omrbranch.payload.updateseraddress.UpdateUserAddressPayload;

public class PayloadObjectManager {

  private static PayloadObjectManager pom;

  private CityListPayload cityListPayload;
  private AddAddressPayload addAddressPayload;
  private UpdateUserAddressPayload updateUserAddressPayload;
  private DeleteAddressPayload deleteAddressPayload;
  private ProductListPayload productListPayload;
  private ManageUserFavouritesPayload manageUserFavourites;
  private AddToCartPayload addToCartPayload;
  private SetAddressPayload setAddressPayload;
  private CreateOrderPayload createOrderPayload;
  private SearchProductPayload searchProductPayload;
  private GetSearchResult getSearchResult;


  private PayloadObjectManager() {
  }

  public static PayloadObjectManager getPayloadInstance() {
    if (pom == null) {
      pom = new PayloadObjectManager();
    }
    return pom;
  }

  public CityListPayload getCityListPayload() {
    return (cityListPayload == null) ? cityListPayload = new CityListPayload() : cityListPayload;
  }

  public AddAddressPayload getAddAddressPayload() {
    return (addAddressPayload == null) ? addAddressPayload = new AddAddressPayload() : addAddressPayload;
  }

  public UpdateUserAddressPayload getUpdateUserAddressPayload() {
    return (updateUserAddressPayload == null) ? updateUserAddressPayload = new UpdateUserAddressPayload()
        : updateUserAddressPayload;
  }

  public DeleteAddressPayload getDeleteAddressPayload() {
    return (deleteAddressPayload == null) ? deleteAddressPayload = new DeleteAddressPayload() : deleteAddressPayload;
  }

  public ProductListPayload getProductListPayload() {
    return (productListPayload == null) ? productListPayload = new ProductListPayload() : productListPayload;
  }

  public ManageUserFavouritesPayload getManageUserFavourites() {
    return (manageUserFavourites == null) ? manageUserFavourites = new ManageUserFavouritesPayload() : manageUserFavourites;
  }

  public AddToCartPayload getAddToCartPayload() {
    return (addToCartPayload == null) ? addToCartPayload = new AddToCartPayload() : addToCartPayload;
  }

  public SetAddressPayload getSetAddressPayload() {
    return (setAddressPayload == null) ? setAddressPayload = new SetAddressPayload() : setAddressPayload;
  }

  public CreateOrderPayload getCreateOrderPayload() {
    return (createOrderPayload == null) ? createOrderPayload = new CreateOrderPayload() : createOrderPayload;
  }
  
  public SearchProductPayload getSearchProductPayload() {
	  return (searchProductPayload == null) ? searchProductPayload = new SearchProductPayload() : searchProductPayload;
  }

  public GetSearchResult getSearchResultPayload() {
	  return (getSearchResult == null) ? getSearchResult = new GetSearchResult() : getSearchResult;
  }

}
