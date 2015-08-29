/*
 * Copyright (c) 2015 HomeLane.com
 *  All Rights Reserved.
 *  All information contained herein is, and remains the property of HomeLane.com.
 *  The intellectual and technical concepts contained herein are proprietary to
 *  HomeLane.com Inc and may be covered by U.S. and Foreign Patents, patents in process,
 *  and are protected by trade secret or copyright law. This product can not be
 *  redistributed in full or parts without permission from HomeLane.com. Dissemination
 *  of this information or reproduction of this material is strictly forbidden unless
 *  prior written permission is obtained from HomeLane.com.
 *  <p/>
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 *
 */
package com.homelane.foodbank.loginsignup.forgotpassword;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hl.hlcorelib.mvp.HLView;
import com.homelane.foodbank.R;

/**
 * Created by hl0395 on 29/8/15.
 */
public class ForgotPasswordDialogView implements HLView {

    /*
     * Holds the inflated layout
     */
    private View mView;

    /*
     * Holds the view for email
     */
    EditText mEmailAddress;

    /*
     * Holds the view for the reset button
     */

    Button mResetPwdBtn;

    /**
     * Create the view from the id provided
     *
     * @param inflater inflater using which the view shold be inflated
     * @param parent   to which the view to be attached
     */
    @Override
    public void init(LayoutInflater inflater, ViewGroup parent) {
        mView  = inflater.inflate(R.layout.forgot_pwd_layout, parent, false);
        mResetPwdBtn=(Button)mView.findViewById(R.id.reset_pin);
        mEmailAddress=(EditText)mView.findViewById(R.id.customer_email);
    }


    /**
     * @return the email entered by user
     */
    public String getEmail(){
        EditText text = (EditText)mView.findViewById(R.id.customer_email);

        if(text.getText() != null)
            return text.getText().toString();
        return null;
    }

    /**
     * @return the phone entered by user
     */
    public String getPhone(){
        EditText text = (EditText)mView.findViewById(R.id.mobile_number);
        if(text.getText() != null)
            return text.getText().toString();
        return null;
    }


    /**
     * @return the phone number append with prefix number +91
     */
    public String getValidPhone(){
        EditText text = (EditText)mView.findViewById(R.id.mobile_number);
        if(text.getText() != null) {
            String s = "+91-";
            return s.concat(text.getText().toString());
        }
        return null;
    }


    /**
     * function validate the name,phone and email that user entered
     * @return the combined message against the invalid fields
     */
    public String validateFields(){
        String result = "";
        String input = getPhone();
        if(!Patterns.PHONE.matcher(input).matches()){
            result = "Phone";
        }
        input = getEmail();
        if(!Patterns.EMAIL_ADDRESS.matcher(input).matches()){
            result += (result.length() > 0) ? ", Email" : "Email";
        }
        return result.length() > 0 ? "Invalid " + result : result;
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
     * Function which will be triggered when {@link Activity#onRestoreInstanceState(Bundle)}
     * or {@link Fragment#onViewStateRestored(Bundle)}
     *
     * @param savedInstanceState the state which saved on {HLView#onSavedInstanceState}
     */
    @Override
    public void onRecreateInstanceState(Bundle savedInstanceState) {

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
}
