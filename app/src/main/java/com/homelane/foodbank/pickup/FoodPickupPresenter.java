package com.homelane.foodbank.pickup;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hl.hlcorelib.mvp.presenters.HLCoreFragment;
import com.homelane.foodbank.loginsignup.LoginView;

/**
 * Created by hl0395 on 29/8/15.
 */
public class FoodPickupPresenter extends HLCoreFragment<FoodPickupView> {

    /**
     * Function which return the enclosing view class, this will be used to
     * create the respective view bind it to the Context
     *
     * @return return the enclosed view class
     */
    @Override
    protected Class<FoodPickupView> getVuClass() {
        return null;
    }

    /**
     * Function which will be called when the view is bind to the presenter,
     * This is useful to set the user interaction listeners to the View
     */
    @Override
    protected void onBindView() {
        super.onBindView();
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
