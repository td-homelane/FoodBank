package com.homelane.foodbank.splash;

import android.content.Intent;
import android.os.Handler;

import com.hl.hlcorelib.mvp.presenters.HLCoreActivityPresenter;
import com.homelane.foodbank.main.MainPresenter;

/**
 * Created by hl0395 on 29/8/15.
 */
public class SplashPresenter extends HLCoreActivityPresenter<SplashView> {



    /**
     * Function which return the enclosing view class, this will be used to
     * create the respective view bind it to the Context
     *
     * @return return the enclosed view class
     */
    @Override
    protected Class<SplashView> getVuClass() {
        return SplashView.class;
    }


    /**
     * Function which will be called when the view is bind to the presenter,
     * This is useful to set the user interaction listeners to the View
     */
    @Override
    protected void onBindView() {
        super.onBindView();
        final Handler launcherHandler = new Handler();
        launcherHandler.postDelayed(new Runnable() {
            /**
             * Starts executing the active part of the class' code. This method is
             * called when a thread is started that has been created with a class which
             * implements {@code Runnable}.
             */
            @Override
            public void run() {
                launcherHandler.removeCallbacks(this);
                Intent mainIntent = new Intent(SplashPresenter.this, MainPresenter.class);
                finish();
                startActivity(mainIntent);
            }
        }, 2000);
    }

}
