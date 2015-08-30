package com.homelane.foodbank.loginsignup;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hl.hlcorelib.mvp.HLView;
import com.homelane.foodbank.R;

/**
 * Created by hl0395 on 29/8/15.
 */
public class LoginView implements HLView {

    private View mView;
    TextView mSignUp, mForgotPwd;
    Button mLoginBtn;
    EditText mEmail, mPassword;

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
        mLoginBtn = (Button) mView.findViewById(R.id.login_button);
        mEmail = (EditText) mView.findViewById(R.id.email_editText);
        mPassword = (EditText) mView.findViewById(R.id.password_editText);
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

    /**
     * function validate the name,phone and email that user entered
     * @return the combined message against the invalid feelds
     */
    public boolean validateFields(){
        String input = mEmail.getText().toString();

        if(!Patterns.EMAIL_ADDRESS.matcher(input).matches()){
            Toast.makeText(mView.getContext(),"Invalid Email",Toast.LENGTH_SHORT).show();
            return false;
        }

        input = mPassword.getText().toString();

        if(!(input != null && input.length() > 0)){
            Toast.makeText(mView.getContext(),"Invalid Password",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


}
