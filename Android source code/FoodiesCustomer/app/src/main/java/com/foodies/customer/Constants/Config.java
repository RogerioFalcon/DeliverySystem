package com.foodies.customer.Constants;

/**
 * Created by Dinosoftlabs on 10/18/2019.
 */
public class Config {



  public static String baseURL = "http://domain.com/foodies/mobileapp_api/api/";
 public static String imgBaseURL = "http://domain.com/foodies/mobileapp_api/";





    public static final String LOGIN_URL = baseURL+"login";

    public static final String showCountries = baseURL+"showCountries";
    public static final String Verify_URL = baseURL+"verifyPhoneNo";
    public static final String SignUp_URL = baseURL+"registerUser";


  public static final String forgotPassword = baseURL+"forgotPassword";
  public static final String verifyforgotPasswordCode = baseURL+"verifyforgotPasswordCode";
  public static final String changePasswordForgot = baseURL+"changePasswordForgot";


    public static final String SHOW_RESTAURANTS = baseURL+"showRestaurants";
    public static final String SHOW_RESTAURANT_MENU = baseURL+"showRestaurantsMenu";
    public static final String ADD_FAV_RESTAURANT = baseURL+"addFavouriteRestaurant";
    public static final String SHOW_FAV_RESTAURANT = baseURL+"showFavouriteRestaurants";

    public static final String SHOW_SLIDER = baseURL+"showAppSliderImages";
    public static final String SHOW_ORDERS = baseURL+"showOrders";
    public static final String SHOW_DEALS = baseURL+"showDeals";

    public static final String CHANGE_PASSWORD = baseURL+"changePassword";
    public static final String EDIT_PROFILE = baseURL+"editUserProfile";


    public static final String ADD_PAYMENT_METHOD = baseURL+"addPaymentMethod";
    public static final String GET_PAYMENT_METHID = baseURL+"getPaymentDetails";
    public static final String ADD_DELIVERY_ADDRESS = baseURL+"addDeliveryAddress";
    public static final String GET_DELIVERY_ADDRESES = baseURL+"showDeliveryAddresses";

    public static final String SHOW_COUNTRIES_LIST = baseURL+"showCountries";
    public static final String SHOW_ORDER_DETAIL = baseURL+"showOrderDetail";
    public static final String SHOW_RIDER_LOCATION_AGAINST_LATLONG = baseURL+"showRiderLocationAgainstOrder";

    public static final String SHOW_MENU_EXTRA_ITEM = baseURL+"showMenuExtraItems";
    public static final String SHOE_TOTAL_RATINGS = baseURL+"showRestaurantRatings";
    public static final String SHOW_RESTAURANT_DEALS = baseURL+"showRestaurantDeals";


    public static final String SHOW_REST_AGAINST_SPECIALITY = baseURL+"showRestaurantsAgainstSpeciality";
    public static final String SHOW_REST_SPECIALITY_LIST = baseURL+"showRestaurantsSpecialities";

    public static final String VERIFY_COUPAN = baseURL+"verifyCoupon";
    public static final String PLACE_ORDER = baseURL+"placeOrder";

    public static final String ORDER_DEAL = baseURL+"orderDeal";


    /// All Admin Apis

    public static final String ACCEPT_DECLINE_STATUS = baseURL+"updateRestaurantOrderStatus";


    // Reviews API

    public static final String AddRestaurantRating = baseURL+"addRestaurantRating";
    public static final String GiveRatingsToRider = baseURL+"giveRatingsToRider";



    // Api For GetPlaces

    public static final String GET_CITY_BOUNDRIES = "http://maps.google.com/maps/api/geocode/json?address=";




    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    public static final int NOTIFICATION_ID = 100;


}
