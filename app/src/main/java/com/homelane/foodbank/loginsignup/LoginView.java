package com.homelane.foodbank.loginsignup;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hl.hlcorelib.mvp.HLView;
import com.homelane.foodbank.R;

/**
 * Created by hl0395 on 29/8/15.
 */
public class LoginView implements HLView {

    private View mView;
    TextView mSignUp, mForgotPwd;

    /**
     * Create the view from the id provided
     *
     * @param inflater inflater using which the view shold be inflated
     * @param parent   to which the view to be attached
     */
    @Override
    public void init(LayoutInflater inflater, ViewGroup parent) {
        mView = inflater.inflate(R.layout.login_layout, parent, false);
        mSignUp = (TextView) mView.findViewById(R.id.signup_textView);
        mForgotPwd = (TextView) mView.findViewById(R.id.forgot_password_text_view);
    }


    /**
     * Return the enclosing view
     *
     * @return return the enclosing view
     */
    @Override
    public View getView() {
        return mView;
    }

    /**
     * To handle the back press
     *
     * @return false if not handled true if handled
     */
    @Override
    public boolean onBackPreseed() {
        return false;
    }



    /**
     * Function which will be called {@link Activity#onSaveInstanceState(Bundle)}
     * or {@link Fragment#onSaveInstanceState(Bundle)}
     *
     * @param savedInstanceState the state to save the contents
     */
    @Override
    public void onSavedInstanceState(Bundle savedInstanceState) {
    }


    /**
     * Function which will be triggered when {@link Activity#onRestoreInstanceState(Bundle)}
     * or {@link Fragment#onViewStateRestored(Bundle)}
     *
     * @param savedInstanceState the state which saved on {HLView#onSavedInstanceState}
     */
    @Override
    public void onRecreateInstanceState(Bundle savedInstanceState) {
    }

}
