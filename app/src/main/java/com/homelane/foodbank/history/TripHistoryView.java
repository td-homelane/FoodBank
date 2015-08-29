package com.homelane.foodbank.history;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hl.hlcorelib.mvp.HLView;
import com.homelane.foodbank.R;

/**
 * Created by hl0395 on 29/8/15.
 */
public class TripHistoryView implements HLView {

    private View mView;

    RecyclerView mTripHistoryList;

    private RecyclerView.LayoutManager mLayoutManager;


    /**
     * Create the view from the id provided
     *
     * @param inflater inflater using which the view shold be inflated
     * @param parent   to which the view to be attached
     */
    @Override
    public void init(LayoutInflater inflater, ViewGroup parent) {
        mView = inflater.inflate(R.layout.trip_history_layout, parent, false);
        mTripHistoryList = (RecyclerView) mView.findViewById(R.id.history_list);
        // use a linear layout manager
        mTripHistoryList.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(inflater.getContext());
        mTripHistoryList.setLayoutManager(mLayoutManager);

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