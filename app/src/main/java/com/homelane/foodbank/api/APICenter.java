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
import com.hl.hlcorelib.orm.HLQuery;
import com.homelane.foodbank.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
         * @param e the exception thrown
         */
        public void onError(Exception e);

        /**
         * delegate method which will be called on completion of the request
         *
         * @param results the result obtained from the server
         */
        public void onResult(List<HLObject> results);

        /**
         * delegate method which will be called on completion of the request
         *
         * @param object the result obtained from the server
         */
        public void onResult(HLObject object);
    }

    /**
     * function which get the collection center arounwhich is close to the user
     *
     * @param radius the limit in which the collection centers to be picked
     * @param location the localtion of the user
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
        final String uberAPIUrl = HLCoreLib.readProperty(Constants.AppConfig.UBER_API_URL) +
                "/v1/requests/" + mTrip.getString(Constants.Trip.TRIP_ID);
        


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
        final String requestPickupUrl = HLCoreLib.readProperty(Constants.AppConfig.UBER_API_URL)
                + "/v1/requests";

        try {
            JSONObject params = new JSONObject();

            String productId = HLCoreLib.readProperty(Constants.AppConfig.PRODUCT_ID);
            params.put(Constants.APIParams.START_LATITUDE, "12.967061");
            params.put(Constants.APIParams.START_LONGITUDE, "77.595482");
            params.put(Constants.APIParams.END_LATITUDE, "12.908376");
            params.put(Constants.APIParams.END_LONGITUDE, "77.647506");
            params.put(Constants.APIParams.PRODUCT_ID, productId);

            JsonObjectRequest request = new JsonObjectRequest(requestPickupUrl, params, new Response.Listener<JSONObject>() {
                /**
                 * Called when a response is received.
                 *
                 * @param response
                 */
                @Override
                public void onResponse(JSONObject response) {
                    mRequestQueue.cancelAll(requestPickupUrl);
                    try {
                        if (response != null && response.optString("request_id") != null) {
                            mTrip.put(Constants.Trip.TRIP_ID, response.getString("request_id"));
                            if(response.optString("status") != null){
                                mTrip.put(Constants.Trip.STATUS, response.getString("status"));
                            }
                            mTrip.save();
                            callback.onResult(mTrip);
                        }
                    }catch (JSONException e){
                        callback.onError(e);
                    }catch (HLQuery.HLQueryException e){
                        callback.onError(e);
                    }
                }
            }, new Response.ErrorListener() {
                /**
                 * Callback method that an error has been occurred with the
                 * provided error code and optional user-readable message.
                 *
                 * @param error
                 */
                @Override
                public void onErrorResponse(VolleyError error) {
                    mRequestQueue.cancelAll(requestPickupUrl);
                    callback.onError(error);
                }
            }){
                /**
                 * Returns a list of extra HTTP headers to go along with this request. Can
                 * throw {@link AuthFailureError} as authentication may be required to
                 * provide these values.
                 *
                 * @throws AuthFailureError In the event of auth failure
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    String accessToken = HLCoreLib.readProperty(Constants.AppConfig.ACCESS_TOKEN);
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer " + accessToken);
                    return headers;
                }
            };
            request.setTag(requestPickupUrl);
            mRequestQueue.add(request);
        }catch (JSONException e){
            callback.onError(e);
        }
    }


}
