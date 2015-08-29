package com.homelane.foodbank.api;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hl.hlcorelib.HLCoreLib;
import com.hl.hlcorelib.orm.HLObject;
import com.homelane.foodbank.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**aon 29/8/15.
 */
public final class APICenter {


    private static RequestQueue mRequestQueue = Volley.newRequestQueue(HLCoreLib.getAppContext());

    /**
     * The interface talk to the app components
     */
    public static interface  APIInterface{
        /**
         * function which will be called on error
         */
        public void onError();

        /**
         * delegate method which will be called on completion of the request
         *
         * @param results the result obtained from the server
         */
        public void onResult(List<HLObject> results);
    }

    /**
     * function which get the collection center arounwhich is close to the user
     *
     * @param radius the limit in which the collection centers to be picked
     * @param location the location of the user
     * @param callback the delegate call back to be called on success or error
     *                 of the request
     */
    public static final void getCollectionCenters(final int radius,
                                            HLObject location, final APIInterface callback){

    }


    /**
     * function which will returns the list of category objects
     *
     * @return returns the list of {@link HLObject} which has the category
     * info
     */
    public static final List<HLObject> getFoodCategories(){
        return null;
    }

    /**
     * function talk to server and updates the status
     *
     * @param mTrip the object against which the trip status to be obtained
     * @param callback the delegate call back to be called on success or error
     *                 of the request
     */
    public static final void getTripStatus(final HLObject mTrip,
                                           final APIInterface callback){
        String uberAPIUrl = HLCoreLib.readProperty(Constants.AppConfig.UBER_API_URL);

    }

    /**
     * function which request for a pick up
     *
     * @param mTrip the trip object contains all the values
     * @param callback the delegate call back to be called on success or error
     *                 of the request
     */
    public static final void requestPickUp(final HLObject mTrip,
                                           final APIInterface callback) {
        String url = HLCoreLib.readProperty(Constants.AppConfig.UBER_API_URL);
        String requestPickupUrl = url + "/v1/requests";
        JsonObjectRequest requestPickUpObject = new JsonObjectRequest(Request.Method.POST,
                    requestPickupUrl,
            new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", response.toString());
            }
        }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error", error.getMessage());
                }
        }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    String productId = HLCoreLib.readProperty(Constants.AppConfig.PRODUCT_ID);
                    params.put(Constants.APIParams.START_LATITUDE, "12.967061");
                    params.put(Constants.APIParams.START_LONGITUDE, "77.595482");
                    params.put(Constants.APIParams.END_LATITUDE, "12.908376");
                    params.put(Constants.APIParams.END_LONGITUDE, "77.647506");
                    params.put(Constants.APIParams.PRODUCT_ID, productId);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    String accessToken = HLCoreLib.readProperty(Constants.AppConfig.ACCESS_TOKEN);
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer " + accessToken);
                    return headers;
                }
            };
            mRequestQueue.add(requestPickUpObject);
    }

}
