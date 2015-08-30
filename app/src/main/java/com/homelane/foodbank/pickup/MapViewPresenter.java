package com.homelane.foodbank.pickup;

import com.hl.hlcorelib.mvp.events.HLCoreEvent;
import com.hl.hlcorelib.mvp.presenters.HLCoreFragment;
import com.homelane.foodbank.Constants;

/**
 * Created by hl0204 on 30/8/15.
 */
public class MapViewPresenter extends HLCoreFragment<MapView> {

    /**
     * Function which return the enclosing view class, this will be used to
     * create the respective view bind it to the Context
     *
     * @return return the enclosed view class
     */
    @Override
    protected Class<MapView> getVuClass() {
        return MapView.class;
    }

    /**
     * Function which will be called when the view is bind to the presenter,
     * This is useful to set the user interaction listeners to the View
     */
    @Override
    protected void onBindView() {
        super.onBindView();
        mView.loadContent(getArguments().getString(Constants.URL));
    }

    /**
     * function notifies the activity if the back press handled inside fragment or not
     *
     * @return true if handled else false
     */
    @Override
    public boolean onBackPressed() {
        final boolean backToFoodDrop = getArguments().getBoolean(Constants.BACK_TO_DROP_FOOD);
        if(backToFoodDrop){
            final HLCoreEvent event = new HLCoreEvent(Constants.SHOW_DROP_IN_FOOD_EVENT, null);
            dispatchEvent(event);
            return true;
        }
        return super.onBackPressed();
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
     * Function to get the menu layout
     *
     * @return the id of the layout
     */
    @Override
    protected int getMenuLayout() {
        return 0;
    }
}
