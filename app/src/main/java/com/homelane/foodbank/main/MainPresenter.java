package com.homelane.foodbank.main;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.hl.hlcorelib.mvp.events.HLCoreEvent;
import com.hl.hlcorelib.mvp.events.HLEvent;
import com.hl.hlcorelib.mvp.events.HLEventListener;
import com.hl.hlcorelib.mvp.presenters.HLCoreActivityPresenter;
import com.hl.hlcorelib.utils.HLFragmentUtils;
import com.homelane.foodbank.Constants;
import com.homelane.foodbank.R;
import com.homelane.foodbank.loginsignup.LoginPresenter;

/**
 * Created by hl0395 on 29/8/15.
 */
public class MainPresenter extends HLCoreActivityPresenter<MainView> implements HLEventListener{

    /**
     * RequestQueue for volley
     */

    RequestQueue queue;

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

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(this);

        HLFragmentUtils.HLFragmentTransaction transaction =
                new HLFragmentUtils.HLFragmentTransaction();
        transaction.isRoot = true;
        transaction.mFrameId = R.id.fragment_frame;
        transaction.mFragmentClass = LoginPresenter.class;
        push(transaction);

        if(! hasEventListener(Constants.ON_LOGOUT_EVENT, this))
            addEventListener(Constants.ON_LOGOUT_EVENT, this);
    }

    /**
     * Called on when onDestroy fired for the presenter. Prior to destroy the enclosing view
     * and resources can be released
     */
    @Override
    protected void onDestroyHLView() {
        super.onDestroyHLView();
        removeEventListener(Constants.ON_LOGOUT_EVENT,this);
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
            HLFragmentUtils.HLFragmentTransaction transaction =
                    new HLFragmentUtils.HLFragmentTransaction();
            transaction.isRoot = true;
            transaction.mFrameId = R.id.fragment_frame;

            transaction.mFragmentClass = LoginPresenter.class;
            push(transaction);
        }
    }
}
