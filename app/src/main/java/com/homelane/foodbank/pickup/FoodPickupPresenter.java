package com.homelane.foodbank.pickup;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.hl.hlcorelib.mvp.presenters.HLCoreFragment;
import com.hl.hlcorelib.orm.HLObject;
import com.homelane.foodbank.R;
import com.homelane.foodbank.loginsignup.LoginView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hl0395 on 29/8/15.
 */
public class FoodPickupPresenter extends HLCoreFragment<FoodPickupView> {

    /**
     * Collection center list
     */
    List<HLObject> collectionCenters;

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

                for(HLObject object: collectionCenters) {
                    if (object.getString("rawMaterials").equals("true")) {
                        mView.mDestinationLocation.setText(object.getString("name"));
                        break;
                    }
                }
            }
        });

        CollectionCenterJSONLoader collectionCenterJSONLoader=new CollectionCenterJSONLoader();
        collectionCenterJSONLoader.execute();
    }

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
     * Function to get the menu layout
     *
     * @return the id of the layout
     */
    @Override
    protected int getMenuLayout() {
        return 0;
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
}
