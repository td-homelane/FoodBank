package com.homelane.foodbank;

/**
 * Created by hl0395 on 29/8/15.
 */
public class Constants {
    /**
     * Class keeps all configuration item key
     */
    public static final class AppConfig{
        public static final String GOOGLE_API_KEY = "google_api_key";
        public static final String CLIENT_ID = "client_id";
        public static final String CLIENT_SECRET = "client_secret";
        public static final String SERVER_TOKEN = "server_token";
        public static final String ACCESS_TOKEN = "access_token";
        public static final String UBER_API_URL = "uber_api_url";
        public static final String PRODUCT_ID = "product_id";
    }


    public static final String BACK_TO_DROP_FOOD = "BACK_TO_DROP_FOOD";

    public static final String URL = "url";
    public static final String NULL = "null";


    public static final class User{
        public static final String  EMAIL = "email";
        public static final String  MOBILE = "mobile";
    }


    public static final class Food{
        public static final String  FOOD = "food";
        public static final String  FOOD_ID = "food_id";
        public static final String  CATEGORY = "category";
    }

    public static final class CollectionCenters {
        public static final String CENTER_NAME = "name";
        public static final String ADDRESS = "address";
        public static final String CONTACT_NO = "contactNo";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String ACCEPTS_RAW_MATERIALS = "acceptsRawMaterials";
        public static final String ACCEPTS_PACKED_MATERIALS = "acceptsPackedMaterials";
        public static final String ACCEPTS_MEDICINES = "acceptsMedicines";

    }

    public static final class Trip {
        public static final String TRIP = "trip";
        public static final String TRIP_ID = "tripId";
        public static final String PICKUP_LOCATION = "pickup_location";
        public static final String DISPATCH_LOCATION = "dispatch_location";
        public static final String STATUS = "status";
        public static final String FARE = "fare";
        public static final String START_TIME = "start_time";
        public static final String END_TIME = "end_time";
        public static final String MAP_LINK = "href";

    }

    public static final class TripStatus{
        public static final String PROCESSING = "processing";
        public static final String COMPLETED = "completed";
        public static final String ACCEPTED = "accepted";
        public static final String FAILED   = "failed";

    }

    public static final class APIParams {
        public static final String START_LATITUDE = "start_latitude";
        public static final String START_LONGITUDE = "start_longitude";
        public static final String END_LATITUDE = "end_latitude";
        public static final String END_LONGITUDE = "end_longitude";
        public static final String PRODUCT_ID = "product_id";
    }
    /////////////////////////////////////////////////
    //////////////////Events////////////////////////
    ///////////////////////////////////////////////

    public static final String ON_UPDATE_USER_EVENT ="on_update_user";
    public static final String ON_FORGOT_PWD_EVENT ="on_forgot_password";
    public static final String ON_LOGOUT_EVENT ="on_logout_event";
    public static final String SHOW_DROP_IN_FOOD_EVENT = "SHOW_DROP_IN_FOOD_EVENT";


}
