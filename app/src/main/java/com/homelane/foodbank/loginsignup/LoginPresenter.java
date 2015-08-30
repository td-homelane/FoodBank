package com.homelane.foodbank.loginsignup;

import android.view.View;
import android.widget.Toast;

import com.hl.hlcorelib.HLLoaderInterface;
import com.hl.hlcorelib.mvp.events.HLCoreEvent;
import com.hl.hlcorelib.mvp.events.HLEvent;
import com.hl.hlcorelib.mvp.events.HLEventListener;
import com.hl.hlcorelib.mvp.presenters.HLCoreFragment;
import com.hl.hlcorelib.orm.HLConstants;
import com.hl.hlcorelib.orm.HLObject;
import com.hl.hlcorelib.orm.HLQuery;
import com.hl.hlcorelib.orm.HLUser;
import com.hl.hlcorelib.utils.HLFragmentUtils;
import com.hl.hlcorelib.utils.HLPreferenceUtils;
import com.homelane.foodbank.Constants;
import com.homelane.foodbank.R;
import com.homelane.foodbank.loginsignup.forgotpassword.ForgotPasswordDialog;
import com.homelane.foodbank.pickup.FoodPickupPresenter;

import java.util.List;

/**
 * Created by hl0395 on 29/8/15.
 */
public class LoginPresenter extends HLCoreFragment<LoginView> implements HLLoaderInterface<HLObject>,
        HLEventListener {

    HLUser mUser;

    /**
     * Function which return the enclosing view class, this will be used to
     * create the respective view bind it to the Context
     *
     * @return return the enclosed view class
     */
    @Override
    protected Class<LoginView> getVuClass() {
        return LoginView.class;
    }

    /**
     * Function which will be called when the view is bind to the presenter,
     * This is useful to set the user interaction listeners to the View
     */
    @Override
    protected void onBindView() {
        super.onBindView();

        load(HLConstants.USER);

        mView.mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HLFragmentUtils.HLFragmentTransaction transaction =
                        new HLFragmentUtils.HLFragmentTransaction();
                transaction.mFrameId = R.id.fragment_frame;
                transaction.mFragmentClass = SignupPresenter.class;
                push(transaction);

                addEventListener(Constants.ON_UPDATE_USER_EVENT, LoginPresenter.this);

            }
        });

        mView.mForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addEventListener(Constants.ON_FORGOT_PWD_EVENT,LoginPresenter.this);

                forgotPasswordDialog=new ForgotPasswordDialog();
                forgotPasswordDialog.show(getChildFragmentManager(), ForgotPasswordDialog.class.getName());
            }
        });

        mView.mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mView.validateFields()) {
                    try {
                        HLUser us = HLUser.signIn(mView.mEmail.getText().toString(), mView.mPassword.getText().toString());
                        if (us != null) {
                            HLPreferenceUtils.obtain().put(HLConstants._ID,us.getmObjectId());

                            HLFragmentUtils.HLFragmentTransaction transaction =
                                    new HLFragmentUtils.HLFragmentTransaction();
                            transaction.mFragmentClass = FoodPickupPresenter.class;
                            transaction.mFrameId = R.id.fragment_frame;
                            push(transaction);
                        }
                    } catch (HLUser.HLUserExistException e) {
                        showToast(e.getMessage());
                    }

                }

            }
        });

    }

    /**
     * function responsible for initialising the query
     *
     * @param name of the object against which the query to be loaded
     */
    @Override
    public void load(String name) {
        HLQuery query = new HLQuery(HLConstants.USER);
        query.query(new HLQuery.HLQueryCallback() {
            /**
             * @param list the list of object fetched from database
             * @param error the error raised against the query
             */
            @Override
            public void onLoad(List<HLObject> list, HLQuery.HLQueryException error) {
                LoginPresenter.this.onLoad(list);
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
        if (list != null && list.size() > 0) {
            mUser = (HLUser) list.get(0);
        }
    }



    /**
     * Reference for the ForgotPasswordDialog
     */
    ForgotPasswordDialog forgotPasswordDialog;

    /**
     * Delegate method which will be called against respective events
     *
     * @param event the event which is dispatched by the {@link HLDispatcher}
     */
    @Override
    public void onEvent(HLEvent event) {
        HLCoreEvent coreEvent = (HLCoreEvent) event;

        if(coreEvent.getType().equals(Constants.ON_UPDATE_USER_EVENT)){

            removeEventListener(Constants.ON_UPDATE_USER_EVENT, this);

            HLFragmentUtils.HLFragmentTransaction transaction =
                    new HLFragmentUtils.HLFragmentTransaction();
            transaction.mFrameId = R.id.fragment_frame;
            transaction.isRoot = true;
            transaction.mFragmentClass = FoodPickupPresenter.class;
            push(transaction);

        }else if(coreEvent.getType().equals(Constants.ON_FORGOT_PWD_EVENT)){


            String email=coreEvent.getmExtra().getString(Constants.User.EMAIL);
            String phone =coreEvent.getmExtra().getString(Constants.User.MOBILE);

            if(email.equals(mUser.getmEmail()) && phone.split("-")[1].equals(mUser.getmPhone())) {

                if(forgotPasswordDialog == null) {
                    forgotPasswordDialog = (ForgotPasswordDialog) getChildFragmentManager().
                            findFragmentByTag(ForgotPasswordDialog.class.getName());
                }
                if(forgotPasswordDialog != null) {
                    forgotPasswordDialog.dismiss();
                }

                mUser.notifyUser(getActivity(), mUser,
                        getString(R.string.pincode_msg_format) + " ");
            }else
                Toast.makeText(getActivity(), "Incorrect email or mobile number",
                        Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * Called on when onDestroy fired for the presenter. Prior to destroy the enclosing view
     * and resources can be released
     */
    @Override
    protected void onDestroyHLView() {
        super.onDestroyHLView();
        removeEventListener(Constants.ON_FORGOT_PWD_EVENT, this);
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
