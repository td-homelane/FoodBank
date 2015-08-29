package com.homelane.foodbank.main;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.hl.hlcorelib.mvp.presenters.HLCoreActivityPresenter;
import com.hl.hlcorelib.utils.HLFragmentUtils;
import com.homelane.foodbank.R;
import com.homelane.foodbank.loginsignup.LoginPresenter;

/**
 * Created by hl0395 on 29/8/15.
 */
public class MainPresenter extends HLCoreActivityPresenter<MainView> {

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
    }

    /**
     * Called on when onDestroy fired for the presenter. Prior to destroy the enclosing view
     * and resources can be released
     */
    @Override
    protected void onDestroyHLView() {
        super.onDestroyHLView();
    }



    /*
     * Function to check for internet connectivity of the mobile
     * @return the status of internet connection
    */
    public boolean isConnectedToNetwork() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }



}
