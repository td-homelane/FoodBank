package com.homelane.foodbank.loginsignup;

import android.util.Log;
import android.view.View;

import com.hl.hlcorelib.mvp.events.HLCoreEvent;
import com.hl.hlcorelib.mvp.presenters.HLCoreFragment;
import com.hl.hlcorelib.orm.HLConstants;
import com.hl.hlcorelib.orm.HLUser;
import com.hl.hlcorelib.utils.HLPreferenceUtils;
import com.homelane.foodbank.Constants;
import com.homelane.foodbank.R;

/**
 * Created by hl0395 on 29/8/15.
 */
public class SignupPresenter extends HLCoreFragment<SignupView> {

    /**
     * Function which return the enclosing view class, this will be used to
     * create the respective view bind it to the Context
     *
     * @return return the enclosed view class
     */
    @Override
    protected Class<SignupView> getVuClass() {
        return SignupView.class;
    }

    /**
     * Function which will be called when the view is bind to the presenter,
     * This is useful to set the user interaction listeners to the View
     */
    @Override
    protected void onBindView() {
        super.onBindView();

        mView.mVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mView.validateFields()){

                    HLUser user = new HLUser();
                    user.setmPhone(mView.mMobile.getText().toString());
                    user.setmName(mView.mFirstName.getText().toString()+" "+mView.mLastName.getText().toString());
                    try {
                        boolean result = user.signUp(mView.mEmail.getText().toString(),
                                mView.mPassword.getText().toString());
                        if(result){
                            HLPreferenceUtils.obtain().put(HLConstants._ID,user.getmObjectId());
                            pop();
                            HLCoreEvent event = new HLCoreEvent(Constants.ON_UPDATE_USER_EVENT,null);
                            dispatchEvent(event);
                        }else{
                            showToast(getString(R.string.signup_failed_msg));
                        }
                    }catch(HLUser.HLUserExistException e){
                        showToast(e.getMessage());
                        Log.d(getClass().getName(), e.getMessage());
                    }

                }
            }
        });
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
