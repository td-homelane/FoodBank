package com.homelane.foodbank.pickup;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hl.hlcorelib.mvp.HLView;
import com.homelane.foodbank.R;

/**
 * Created by hl0395 on 29/8/15.
 */
public class FoodPickupView implements HLView {

    private View mView;
    TextView mPackedFood, mRawFood;
    RelativeLayout mTypeLayout,mConfirmLayout;
    TextView mSelectedFoodType,mCuurentLocation,mDestinationLocation,mFareEstimate;
    Button mBookNtn;



    /**
     * Create the view from the id provided
     *
     * @param inflater inflater using which the view shold be inflated
     * @param parent   to which the view to be attached
     */
    @Override
    public void init(LayoutInflater inflater, ViewGroup parent) {
        mView = inflater.inflate(R.layout.pickup_layout, parent, false);
        mPackedFood = (TextView) mView.findViewById(R.id.packed_text);
        mRawFood = (TextView) mView.findViewById(R.id.raw_material);
        mTypeLayout = (RelativeLayout) mView.findViewById(R.id.typeLayout);
        mConfirmLayout = (RelativeLayout) mView.findViewById(R.id.confirmLayout);
        mSelectedFoodType = (TextView) mView.findViewById(R.id.selectedType);
        mCuurentLocation = (TextView) mView.findViewById(R.id.current_location);
        mDestinationLocation = (TextView) mView.findViewById(R.id.destination_name);
        mFareEstimate = (TextView) mView.findViewById(R.id.fare_estimate);
    }

    /**
     * Return the enclosing view
     *
     * @return return the enclosing view
     */
    @Override
    public View getView() {
        return mView;
    }

    /**
     * To handle the back press
     *
     * @return false if not handled true if handled
     */
    @Override
    public boolean onBackPreseed() {
        if(mConfirmLayout.getVisibility() == View.VISIBLE){
            mSelectedFoodType.setText("");
            mDestinationLocation.setText("");
            mConfirmLayout.setVisibility(View.GONE);
            mTypeLayout.setVisibility(View.VISIBLE);
            return true;
        }
        return false;
    }

    /**
     * Function which will be called {@link Activity#onSaveInstanceState(Bundle)}
     * or {@link Fragment#onSaveInstanceState(Bundle)}
     *
     * @param savedInstanceState the state to save the contents
     */
    @Override
    public void onSavedInstanceState(Bundle savedInstanceState) {

    }

    /**
     * Function which will be triggered when {@link Activity#onRestoreInstanceState(Bundle)}
     * or {@link Fragment#onViewStateRestored(Bundle)}
     *
     * @param savedInstanceState the state which saved on {HLView#onSavedInstanceState}
     */
    @Override
    public void onRecreateInstanceState(Bundle savedInstanceState) {

    }
}
