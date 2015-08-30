package com.homelane.foodbank.history;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.hl.hlcorelib.HLLoaderInterface;
import com.hl.hlcorelib.mvp.events.HLCoreEvent;
import com.hl.hlcorelib.mvp.presenters.HLCoreFragment;
import com.hl.hlcorelib.orm.HLObject;
import com.hl.hlcorelib.orm.HLQuery;
import com.homelane.foodbank.Constants;
import com.homelane.foodbank.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hl0395 on 29/8/15.
 */
public class TripHistoryPresenter extends HLCoreFragment<TripHistoryView> implements HLLoaderInterface<HLObject> {

    /**
     * Adapter for the recyler view
     */

    private TripHistoryAdapter mAdapter;

    /**
     * Constructor function
     */
    public TripHistoryPresenter() {
        super();
    }

    /**
     * Function which return the enclosing view class, this will be used to
     * create the respective view bind it to the Context
     *
     * @return return the enclosed view class
     */
    @Override
    protected Class<TripHistoryView> getVuClass() {
        return TripHistoryView.class;
    }

    /**
     * Function which will be called when the view is bind to the presenter,
     * This is useful to set the user interaction listeners to the View
     */
    @Override
    protected void onBindView() {
        super.onBindView();

        mAdapter = new TripHistoryAdapter(new ArrayList<HLObject>(), this);
        mView.mTripHistoryList.setAdapter(mAdapter);
        load(Constants.Trip.TRIP);
        setHasOptionsMenu(true);
    }


    /**
     * function responsible for initialising the query
     *
     * @param name of the object against which the query to be loaded
     */
    @Override
    public void load(String name) {
        HLQuery query = new HLQuery(name);

        HLQuery foodQuery = new HLQuery(Constants.Food.FOOD);
        foodQuery.setMselect(new String[]{Constants.Food.CATEGORY});
        query.chain(Constants.Trip.TRIP_ID, foodQuery, false);

        query.setMselect(new String[]{Constants.Trip.DISPATCH_LOCATION, Constants.Trip.STATUS,Constants.Trip.FARE,
                Constants.Trip.START_TIME});
        query.query(new HLQuery.HLQueryCallback() {
            /**
             * Delegate method to be called on completion of the query
             *
             * @param list  the lsit of object fetched from database
             * @param error the error raised against the query
             */
            @Override
            public void onLoad(List<HLObject> list, HLQuery.HLQueryException error) {
                if (mView != null)
                    TripHistoryPresenter.this.onLoad(list);
            }
        });
    }

    /**
     * function decides what to do with the loaded content
     *
     * @param list list of values obtained from query
     */
    @Override
    public void onLoad(List<HLObject> list) {
        if(list != null && list.size() > 0){
            mAdapter.setmDataProvider((ArrayList<HLObject>)list);
            mView.mTripHistoryList.swapAdapter(mAdapter, true);
        }
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
     * Called on when onDestroy fired for the presenter. Prior to destroy the enclosing view
     * and resources can be released
     */
    @Override
    protected void onDestroyHLView() {
        super.onDestroyHLView();
    }

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
        return new int[]{ R.id.menu_history };
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

                HLCoreEvent hlEventDispatcher = new HLCoreEvent(Constants.ON_LOGOUT_EVENT, null);
                dispatchEvent(hlEventDispatcher);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
