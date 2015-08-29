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
import android.widget.Toast;

import com.hl.hlcorelib.mvp.HLView;
import com.homelane.foodbank.R;

import java.util.regex.Pattern;

/**
 * Created by hl0395 on 29/8/15.
 */
public class SignupView implements HLView {

    private View mView;
    EditText mFirstName, mLastName, mMobile, mPassword, mEmail;
    Button mVerify;

    /**
     * Create the view from the id provided
     *
     * @param inflater inflater using which the view shold be inflated
     * @param parent   to which the view to be attached
     */
    @Override
    public void init(LayoutInflater inflater, ViewGroup parent) {
        mView = inflater.inflate(R.layout.signup_layout, parent, false);
        mFirstName = (EditText) mView.findViewById(R.id.first_name_editText);
        mLastName = (EditText) mView.findViewById(R.id.last_name_editText);
        mMobile = (EditText) mView.findViewById(R.id.mobile_number_edittext);
        mEmail = (EditText) mView.findViewById(R.id.email_editText);
        mPassword = (EditText) mView.findViewById(R.id.password_editText);
        mVerify = (Button) mView.findViewById(R.id.verify_button);
    }

    /**
     * function validate the name,phone and email that user entered
     * @return the combined message against the invalid feelds
     */
    public boolean validateFields(){
        String input = mMobile.getText().toString();
        String regex = "^[0-9]{10}$";
        if(input.length()<10 || !Pattern.matches(regex, input)){
            Toast.makeText(mView.getContext(),"Invalid Mobile Number",Toast.LENGTH_SHORT).show();
            return false;
        }


        input = mEmail.getText().toString();

        if(!Patterns.EMAIL_ADDRESS.matcher(input).matches()){
            Toast.makeText(mView.getContext(),"Invalid Email",Toast.LENGTH_SHORT).show();
            return false;
        }
        input = mFirstName.getText().toString();

        if(!(input != null && input.length() > 0)){
            Toast.makeText(mView.getContext(),"Invalid First name",Toast.LENGTH_SHORT).show();
            return false;
        }

        input = mLastName.getText().toString();

        if(!(input != null && input.length() > 0)){
            Toast.makeText(mView.getContext(),"Invalid Last name",Toast.LENGTH_SHORT).show();
            return false;
        }

        input = mPassword.getText().toString();

        if(!(input != null && input.length() > 0)){
            Toast.makeText(mView.getContext(),"Invalid Password",Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
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
