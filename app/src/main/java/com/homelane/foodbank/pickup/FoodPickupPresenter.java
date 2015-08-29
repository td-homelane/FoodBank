package com.homelane.foodbank.pickup;

import android.app.Fragment;
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

import com.hl.hlcorelib.mvp.events.HLCoreEvent;
import com.hl.hlcorelib.mvp.presenters.HLCoreFragment;
import com.hl.hlcorelib.orm.HLObject;
import com.homelane.foodbank.Constants;
import com.homelane.foodbank.R;
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

        mView.mPackedFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.mTypeLayout.setVisibility(View.GONE);
                mView.mConfirmLayout.setVisibility(View.VISIBLE);

                mView.mSelectedFoodType.setText(getString(R.string.packed_txt));

                for(HLObject object: collectionCenters) {
                    if (object.getString("processedFood").equals("true")) {
                        mView.mDestinationLocation.setText(object.getString("name"));
                        break;
                    }
                }
            }
        });

        mView.mRawFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.mTypeLayout.setVisibility(View.GONE);
                mView.mConfirmLayout.setVisibility(View.VISIBLE);

                mView.mSelectedFoodType.setText(getString(R.string.raw_materials));

                for (HLObject object : collectionCenters) {
                    if (object.getString("rawMaterials").equals("true")) {
                        mView.mDestinationLocation.setText(object.getString("name"));
                        break;
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

            locHandler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if(mGPSTracker.canGetLocation()) {
                        loc = mGPSTracker.getLocation();
                        mView.mCuurentLocation.setText(getAddressByGpsCoordinates(loc.getLatitude() + "",
                                loc.getLongitude() + ""));
                        locHandler.removeCallbacks(this);
                    }else
                        locHandler.postDelayed(this,2000);
                }
            },2000);
        }

        setHasOptionsMenu(true);
    }

    Handler locHandler = new Handler();

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
                if(mView.mCuurentLocation != null)
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
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
