package com.homelane.foodbank;

import com.hl.hlcorelib.HLApplication;
import com.hl.hlcorelib.HLCoreLib;

/**
 * Created by hl0204 on 29/8/15.
 */
public class FoodBank extends HLApplication{

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();
        HLCoreLib.initAppConfig(false);
    }
}
