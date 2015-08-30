package com.homelane.foodbank.pickup;

import android.app.Fragment;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.hl.hlcorelib.mvp.events.HLCoreEvent;
import com.hl.hlcorelib.mvp.presenters.HLCoreFragment;
import com.hl.hlcorelib.orm.HLObject;
import com.hl.hlcorelib.orm.HLQuery;
import com.hl.hlcorelib.utils.HLFragmentUtils;
import com.homelane.foodbank.Constants;
import com.homelane.foodbank.R;
import com.homelane.foodbank.api.APICenter;
import com.homelane.foodbank.history.TripHistoryPresenter;
import com.homelane.foodbank.utils.GPSUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by hl0395 on 29/8/15.
 */
public class FoodPickupPresenter extends HLCoreFragment<FoodPickupView> {

    /**
     * Collection center list
     */
    List<HLObject> collectionCenters;
    /**
     *  Holds the instance to the locaton tracker
     */
    private GPSUtils mGPSTracker;

    /**
     *  Holds the value of destination location
     */
    String destLocation;

    /**
     *  Holds the value of selectedFood category
     */
    String foodCategory;

    /**
     * Function which return the enclosing view class, this will be used to
     * create the respective view bind it to the Context
     *
     * @return return the enclosed view class
     */
    @Override
    protected Class<FoodPickupView> getVuClass() {
        return FoodPickupView.class;
    }

    /**
     * Function which will be called when the view is bind to the presenter,
     * This is useful to set the user interaction listeners to the View
     */
    @Override
    protected void onBindView() {
        super.onBindView();

/*
        mView.clothingIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.centerBottomMenu.close(true);
                mView.mSelectedFoodType.setText("Clothing");
                mView.mFareEstimate.setText("");
                mView.mDestinationLocation.setText("");
                mView.mContentsView.setVisibility(View.VISIBLE);

            }
        });

        mView.medicinelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.centerBottomMenu.close(true);
                mView.mSelectedFoodType.setText("Medicines");
                mView.mFareEstimate.setText("");
                mView.mDestinationLocation.setText("");
                mView.mContentsView.setVisibility(View.VISIBLE);

            }
        });
*/
        mView.foodIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.centerBottomMenu.close(true);
                mView.mSelectedFoodType.setText("Food");
                mView.mFareEstimate.setText("");
                mView.mDestinationLocation.setText("");
                foodCategory = getString(R.string.packed_txt);
                mView.mContentsView.setVisibility(View.VISIBLE);

                for (HLObject object : collectionCenters) {
                    if (object.getString("processedFood").equals("true")) {
                        mView.mDestinationLocation.setText(object.getString("name"));
                        destLocation = object.getString("latitude") + "," + object.getString("longitude");
                        break;
                    }
                }
                if(loc != null){
                    updateFare();
                }

            }
        });



         mView.mBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.mBookBtn.setText("Requesting.....");
                mView.mBookBtn.setEnabled(false);
                if(loc != null && destLocation != null) {
                    final HLObject trip = new HLObject(Constants.Trip.TRIP);
                    trip.put(Constants.Trip.PICKUP_LOCATION, loc.getLatitude() + "," + loc.getLongitude());
                    trip.put(Constants.Trip.DISPATCH_LOCATION, destLocation);
                    trip.put(Constants.Trip.STATUS, Constants.NULL);
                    trip.put(Constants.Trip.FARE, Constants.NULL);
                    trip.put(Constants.Trip.START_TIME, Constants.NULL);
                    trip.put(Constants.Trip.END_TIME, Constants.NULL);

                    try {
                        if (trip.save()) {

                            HLObject food = new HLObject(Constants.Food.FOOD);
                            food.put(Constants.Trip.TRIP_ID, trip);
                            food.put(Constants.Food.CATEGORY, foodCategory);
                            boolean status = food.save();
                            APICenter.requestPickUp(trip, new APICenter.APIInterface() {
                                /**
                                 * delegate method which will be called on completion of the request
                                 *
                                 * @param response the result obtained from the server
                                 */
                                @Override
                                public void onResult(HLObject response) {
                                    if (response.getString(Constants.Trip.STATUS).
                                            equals(Constants.TripStatus.COMPLETED)) {
                                        APICenter.getRequestMap(response, new APICenter.APIInterface() {
                                            /**
                                             * delegate method which will be called on completion of the request
                                             *
                                             * @param response the result obtained from the server
                                             */
                                            @Override
                                            public void onResult(HLObject response) {
                                                obtainReceipt(response);
                                                HLFragmentUtils.HLFragmentTransaction transaction =
                                                        new HLFragmentUtils.HLFragmentTransaction();
                                                final Bundle data = new Bundle();
                                                transaction.isRoot = true;
                                                data.putString(Constants.URL, response.getString(Constants.Trip.MAP_LINK));
                                                data.putBoolean(Constants.BACK_TO_DROP_FOOD, true);
                                                transaction.mFragmentClass = MapViewPresenter.class;
                                                transaction.mFrameId = R.id.fragment_frame;
                                                transaction.mParameters = data;
                                                push(transaction);
                                            }

                                            /**
                                             * function which will be called on error
                                             *
                                             * @param e the exception thrown
                                             */
                                            @Override
                                            public void onError(Exception e) {

                                            }
                                        });
                                    } else {

                                        showToast("Unable to find the drivers nearby");

                                        mView.mBookBtn.setText("Retry");
                                        mView.mBookBtn.setEnabled(true);
                                        try {
                                            response.delete();
                                        } catch (HLObject.HLDeleteException e) {

                                        }
                                    }
                                }

                                /**
                                 * function which will be called on error
                                 *
                                 * @param e the exception thrown
                                 */
                                @Override
                                public void onError(Exception e) {
                                    showToast("Failed to place the request, make sure you have a network connection");
                                    try {
                                        trip.delete();
                                        mView.mBookBtn.setText("Retry");
                                        mView.mBookBtn.setEnabled(true);
                                    } catch (HLObject.HLDeleteException ex) {
                                        Log.d("hello", "hello");
                                    }
                                }
                            });

                        }
                    } catch (HLQuery.HLQueryException e) {
                        e.printStackTrace();
                    }
                }

            }
        });



        CollectionCenterJSONLoader collectionCenterJSONLoader=new CollectionCenterJSONLoader();
        collectionCenterJSONLoader.execute();
        mGPSTracker = new GPSUtils(getActivity());
        if(mGPSTracker.canGetLocation()){
            loc = mGPSTracker.getLocation();
            mGPSTracker.stopUsingGPS();
            mGPSTracker = null;
            mView.mCuurentLocation.setText(getAddressByGpsCoordinates(loc.getLatitude()+"",loc.getLongitude()+""));

        }else{
            mGPSTracker.showSettingsAlert();

            locHandler.postDelayed(locRunnable, 2000);
        }


        setHasOptionsMenu(true);
    }

    Runnable locRunnable = new Runnable() {
        @Override
        public void run() {
            if(mGPSTracker.canGetLocation()) {
                loc = mGPSTracker.getLocation();
                mView.mCuurentLocation.setText(getAddressByGpsCoordinates(loc.getLatitude() + "",
                        loc.getLongitude() + ""));
                if(destLocation != null){
                    updateFare();
                }
                updateFare();
                locHandler.removeCallbacks(this);
            }else
                locHandler.postDelayed(this,2000);

        }
    };

    Handler locHandler = new Handler();

    /**
     * function get the receipt from server and update it
     *
     * @param trip the trip against which the receipt should be obtained
     */
    private void obtainReceipt(final HLObject trip){
        APICenter.getRequestReceipt(trip, null);
    }

    /**
     * function which updates the fare for the trip
     */
    private void updateFare(){
        HLObject trip = new HLObject(Constants.Trip.TRIP);
        trip.put(Constants.Trip.PICKUP_LOCATION, loc.getLatitude() + "," + loc.getLongitude());
        trip.put(Constants.Trip.DISPATCH_LOCATION, destLocation);
        APICenter.getRequestEstimate(trip, new APICenter.APIInterface() {
            /**
             * function which will be called on error
             *
             * @param e the exception thrown
             */
            @Override
            public void onError(Exception e) {
                showToast("Failed to populate the estimate!!!");
            }

            /**
             * delegate method which will be called on completion of the request
             *
             * @param response the result obtained from the server
             */
            @Override
            public void onResult(HLObject response) {
                if(isVisible()) {
                    mView.mFareEstimate.setText(response.getString(Constants.Trip.FARE));
                    mView.mFareStatusProgress.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {@link Activity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();
        if(mGPSTracker!=null && mGPSTracker.canGetLocation()) {
            loc = mGPSTracker.getLocation();
            mView.mCuurentLocation.setText(getAddressByGpsCoordinates(loc.getLatitude() + "", loc.getLongitude() + ""));
        }
    }

    /**
     * Current location reference
     */
    Location loc;

    /**
     * Function which will be called {@link Fragment#onSaveInstanceState(Bundle)}
     *
     * @param savedInstanceState the state to save the contents
     */
    @Override
    protected void saveInstanceState(Bundle savedInstanceState) {
        super.saveInstanceState(savedInstanceState);
    }

    /**
     * Function which will be triggered when {@link Fragment#onViewStateRestored(Bundle)}
     *
     * @param data the state which saved on {Fragment#onSaveInstanceState}
     */
    @Override
    protected void restoreInstanceState(@Nullable Bundle data) {
        super.restoreInstanceState(data);
    }

    /**
     * function notifies the activity if the back press handled inside fragment or not
     *
     * @return true if handled else false
     */
    @Override
    public boolean onBackPressed() {
        if (mView.onBackPreseed())
            return true;

        return super.onBackPressed();
    }

    /**
     * Called on when onDestroy fired for the presenter. Prior to destroy the enclosing view
     * and resources can be released
     */
    @Override
    protected void onDestroyHLView() {
        super.onDestroyHLView();
        if(mGPSTracker != null){
            mGPSTracker.stopUsingGPS();
            mGPSTracker = null;
            locHandler.removeCallbacks(locRunnable);
        }
    }

    private class CollectionCenterJSONLoader extends AsyncTask<Void, Void, ArrayList<HLObject>> {
        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p/>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param hlObjects The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(ArrayList<HLObject> hlObjects) {
            super.onPostExecute(hlObjects);
            collectionCenters = hlObjects;
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected ArrayList<HLObject> doInBackground(Void... params) {
            ArrayList<HLObject> result = null;
            BufferedReader fileReader = null;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                InputStreamReader stream = new InputStreamReader(getResources().
                        getAssets().open("CollectionCentres.json"));
                fileReader = new BufferedReader(stream);
                String line = "";
                while ((line = fileReader.readLine()) != null){
                    stringBuilder.append(line);
                }
                String jsonString = stringBuilder.toString();
                JSONArray objectArray = new JSONObject(jsonString).getJSONArray("centres");
                result = new ArrayList<>(objectArray.length());
                for(int i = 0; i < objectArray.length(); i++){
                    JSONObject object = objectArray.getJSONObject(i);
                    HLObject hlObject = new HLObject("centre");
                    for(int j = 0; j < object.names().length(); j++){
                        String name = object.names().getString(j);
                        hlObject.put(name, object.getString(name));
                    }
                    result.add(hlObject);
                }
            }catch (Exception e){
                Log.e("Error", e.getMessage());
            }
            return result;
        }
    }


    /**
     * Method to get the address of GPS latitude/longitude.
     */
    private String getAddressByGpsCoordinates(String Latitude, String longitude) {
        // TODO Auto-generated method stub
        new AsyncTask<String, String, String>(

        ) {
            @Override
            protected String doInBackground(String... params) {

                if (getActivity() != null) {

                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

                    List<Address> addresses = null;

                    try {

                        addresses = geocoder.getFromLocation(Double.parseDouble(params[0]),
                                Double.parseDouble(params[1]), 1);

                    } catch (MalformedURLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (NumberFormatException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block

                        e.printStackTrace();
                    }

                    if (addresses != null && addresses.size() > 0) {

                        // Get the first address
                        Address address = addresses.get(0);

                        // Format the first line of address
                        if (getActivity() != null) {
                            String addressText = getActivity().getString(
                                    R.string.address_output_string,

                                    // If there's a street address, add it
                                    address.getMaxAddressLineIndex() > 0 ? address
                                            .getAddressLine(0) : "",

                                    // Locality is usually a city
                                    address.getLocality(),

                                    // The country of the address
                                    address.getCountryName());


                            // Return the text
                            str = address.getAddressLine(0) + ", " + address.getSubLocality() + ", " + address.getLocality();
                        }

                        // If there aren't any addresses, post a message
                    } else {
                        if (getActivity() != null)
                            str = getActivity().getString(R.string.no_address_found);
                    }
                }
                return str;
            }

            @Override
            protected void onPostExecute(String s) {
                str = s;
                if(isVisible() && mView.mCuurentLocation != null)
                    mView.mCuurentLocation.setText(s);
            }
        }.execute(Latitude, longitude);
        return "Loading address";
    }

    String str;

    /**
     * Function to get the menu layout
     *
     * @return the id of the layout
     */
    @Override
    protected int getMenuLayout() {
        return R.menu.pickup_menu;
    }

    /**
     * Fuction to get the menu items that are disabled in that screen
     *
     * @return the ids of the items to be disabled
     */
    @Override
    protected int[] getDisabledMenuItems() {
        return new int[0];
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     * <p/>
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                HLCoreEvent hlEventDispatcher=new HLCoreEvent(Constants.ON_LOGOUT_EVENT,null);
                dispatchEvent(hlEventDispatcher);
                return true;
            case R.id.menu_history:

                HLFragmentUtils.HLFragmentTransaction transaction =
                        new HLFragmentUtils.HLFragmentTransaction();
                transaction.mFrameId = R.id.fragment_frame;
                transaction.mFragmentClass = TripHistoryPresenter.class;
                push(transaction);


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
