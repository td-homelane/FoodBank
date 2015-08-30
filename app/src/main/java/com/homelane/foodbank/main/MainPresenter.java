package com.homelane.foodbank.main;

import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.hl.hlcorelib.HLCoreLib;
import com.hl.hlcorelib.HLLoaderInterface;
import com.hl.hlcorelib.mvp.events.HLCoreEvent;
import com.hl.hlcorelib.mvp.events.HLEvent;
import com.hl.hlcorelib.mvp.events.HLEventListener;
import com.hl.hlcorelib.mvp.presenters.HLCoreActivityPresenter;
import com.hl.hlcorelib.orm.HLConstants;
import com.hl.hlcorelib.orm.HLObject;
import com.hl.hlcorelib.orm.HLQuery;
import com.hl.hlcorelib.orm.HLUser;
import com.hl.hlcorelib.utils.HLFragmentUtils;
import com.hl.hlcorelib.utils.HLPreferenceUtils;
import com.homelane.foodbank.Constants;
import com.homelane.foodbank.R;
import com.homelane.foodbank.loginsignup.LoginPresenter;
import com.homelane.foodbank.pickup.FoodPickupPresenter;

import java.util.List;

/**
 * Created by hl0395 on 29/8/15.
 */
public class MainPresenter extends HLCoreActivityPresenter<MainView> implements HLEventListener {

    /**
     * Function which return the enclosing view class, this will be used to
     * create the respective view bind it to the Context
     *
     * @return return the enclosed view class
     */
    @Override
    protected Class<MainView> getVuClass() {
        return MainView.class;
    }

    /**
     * Function which will be called when the view is bind to the presenter,
     * This is useful to set the user interaction listeners to the View
     */
    @Override
    protected void onBindView() {
        super.onBindView();
        if(!isRecreated()) {
            final boolean mode = getIntent().getExtras().getBoolean("isExist");
            if(mode){
                showDropinFood();
            }else{
                final HLFragmentUtils.HLFragmentTransaction transaction =
                        new HLFragmentUtils.HLFragmentTransaction();
                transaction.isRoot = true;
                transaction.mFrameId = R.id.fragment_frame;
                transaction.mFragmentClass = LoginPresenter.class;
                push(transaction);
            }
        }
        if(! hasEventListener(Constants.ON_LOGOUT_EVENT, this))
            addEventListener(Constants.ON_LOGOUT_EVENT, this);

        if(!hasEventListener(Constants.SHOW_DROP_IN_FOOD_EVENT, this)){
            addEventListener(Constants.SHOW_DROP_IN_FOOD_EVENT, this);
        }
    }

    /**
     * Called on when onDestroy fired for the presenter. Prior to destroy the enclosing view
     * and resources can be released
     */
    @Override
    protected void onDestroyHLView() {
        super.onDestroyHLView();
        removeEventListener(Constants.ON_LOGOUT_EVENT, this);
        removeEventListener(Constants.SHOW_DROP_IN_FOOD_EVENT, this);
    }

    /**
     * Delegate method which will be called against respective events
     *
     * @param event the event which is dispatched by the {@link HLDispatcher}
     */
    @Override
    public void onEvent(HLEvent event) {
        HLCoreEvent e = (HLCoreEvent)event;
        Bundle bundle = e.getmExtra();

        if(e.getType().equals(Constants.ON_LOGOUT_EVENT)) {

            HLPreferenceUtils.obtain().put(HLConstants._ID,null);

            HLFragmentUtils.HLFragmentTransaction transaction =
                    new HLFragmentUtils.HLFragmentTransaction();
            transaction.isRoot = true;
            transaction.mFrameId = R.id.fragment_frame;

            transaction.mFragmentClass = LoginPresenter.class;
            push(transaction);
        }else{
            showDropinFood();
        }
    }

    /**
     * function launch the {@link FoodPickupPresenter}
     */
    private void showDropinFood(){
        HLFragmentUtils.HLFragmentTransaction transaction =
                new HLFragmentUtils.HLFragmentTransaction();
        transaction.isRoot = true;
        transaction.mFrameId = R.id.fragment_frame;
        transaction.mFragmentClass = FoodPickupPresenter.class;
        push(transaction);
    }
}
