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

package com.hl.hlcorelib.orm;

import android.content.ContentValues;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.hl.hlcorelib.HLCoreLib;

import java.util.List;

/**
 * Created by hl0204 on 28/7/15.
 */
public class HLUser extends HLObject{



    String mPassword;


    /**
     *
     */
    public static final Parcelable.Creator<HLUser> CREATOR
            = new Parcelable.Creator<HLUser>() {
        public HLUser createFromParcel(Parcel in) {
            return new HLUser(in);
        }

        public HLUser[] newArray(int size) {
            return new HLUser[size];
        }
    };

    /**
     * function which recreate the object from the Parcel
     *
     * @param in of type Parcel
     */
    HLUser(Parcel in) {
        super(in);
        mEmail = in.readString();
        mName  = in.readString();
        mPassword = in.readString();
        mPhone    = in.readString();
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString((mEmail != null) ? mEmail : "");
        dest.writeString((mName != null) ? mName : "");
        dest.writeString((mPassword != null) ? mPassword : "");
        dest.writeString((mPhone != null) ? mPhone : "");
    }


    /**
     * getter function for mEmail
     * @return the Email associated with the user
     */
    public String getmEmail() {
        return mEmail;
    }

    /**
     * getter function for mName
     * @return the Name associated with the user
     */
    public String getmName() {
        return mName;
    }

    /**
     * getter function for mPhone
     * @return the phone number associated with the user
     */
    public String getmPhone() {
        return mPhone;
    }

    /**
     * Setter function for mEmail
     * @param mEmail the email to be saved against the user
     */
    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    /**
     * Setter function for mName
     * @param mName the name to be saved against the user
     */
    public void setmName(String mName) {
        this.mName = mName;
    }

    /**
     * Setter function for mPhone
     * @param mPhone the phone number to be saved against the user
     */
    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    /**
     * Holds the email id for the user
     */
    private String mEmail;

    /**
     * Holds the Name of the user
     */
    private String mName;

    /**
     * Holds the mobile number for the user
     */
    private String mPhone;

    /**
     * Constructor function, The name will be always set using a default value.
     * This further calls HLObject constructor with the name
     */
    public HLUser() {
        super(HLConstants.USER);
    }

    /**
     * Function add the default columns to the table creation query
     *
     * @param tableQuery the query to which the default columns to be appended
     * @return return the cobined string with default columns
     */
    @Override
    protected String addDefaultColumns(String tableQuery) {
        tableQuery +=  " , " + HLConstants._user_email + " varchar not null unique, " +
                HLConstants._user_name + " varchar not null, " +
                HLConstants._user_password + " varchar not null, "
                + HLConstants._user_phone + " varchar not null unique";
        return super.addDefaultColumns(tableQuery);
    }

    /**
     * Check if the column is a default column or not
     *
     * @param column of type String against which the check should happen
     * @return of type boolean
     */
    @Override
    protected boolean isDefaultColumns(String column) {
        boolean result = super.isDefaultColumns(column);
        return result || (column.equals(HLConstants._user_email) || column.equals(HLConstants._user_name)
                || column.equals(HLConstants._user_password) || column.equals(HLConstants._user_phone));
    }

    /**
     *
     * @return returned HLUser object on successful login else an exeption will be thrown
     * @throws HLUserExistException
     */
    public static HLUser signIn(String email, String passWord) throws HLUserExistException{
        passWord = passWord;
        HLQuery query = new HLQuery(HLConstants.USER);
        query.whereEquals(HLConstants._user_email, email);
        query.whereEquals(HLConstants._user_password, passWord);
        List<HLObject> list = query.query();
        if(list != null && list.size() > 0){
            HLObject object = list.get(0);
            HLUser user = new HLUser();
            user.mObjectId    = object.mObjectId;
            user.mUpdatedTime = object.mUpdatedTime;
            user.mCreatedTime = object.mCreatedTime;
            user.mIsSynced = true;
            HLCoreLib.setmUser(user);
            return user;
        }else{
            throw new HLUserExistException(HLUserExistException.ErrorCode.EMAIL_PASSWORD_ERROR);
        }
    }

    /**
     * function to send the passcode to the user mobile
     * @param HLUser is the user, message is the string to be sent in the message
     */
    public void notifyUser(Context context,HLUser hlUser,String message) {

        String phoneNo = hlUser.getmPhone();
        String password = hlUser.mPassword;
        try {
            message += password;

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(context, "Your passcode is sent to your registered mobile number", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Failed to send message",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    /**
     * Function which creates a new user in the system
     *
     * @return return true if sign-up succeed
     * @throws HLUserExistException if sign up fail
     */
    public boolean signUp(String email, String pasword) throws HLUserExistException{
        mEmail = email;
        mPassword = pasword;
        HLQuery userQuery = new HLQuery(HLConstants.USER);
        userQuery.whereEquals(HLConstants._user_email, mEmail);
        List<HLObject> list = userQuery.query();
        if(list != null && list.size() > 0){
            throw new HLUserExistException(HLUserExistException.ErrorCode.EMAIL_EXIST);
        }
        userQuery = new HLQuery(HLConstants.USER);
        userQuery.whereEquals(HLConstants._user_phone, mPhone);
        list = userQuery.query();
        if(list != null && list.size() > 0){
            throw new HLUserExistException(HLUserExistException.ErrorCode.PHONE_EXIST);
        }
        mIsDirty = true;
        try {
            mIsSynced = save();
        }catch (HLQuery.HLQueryException e){
            throw new HLUserExistException(HLUserExistException.ErrorCode.INSERT_ERROR);
        }
        mFetched  = mIsSynced;
        HLCoreLib.setmUser(this);
        return mIsSynced;
    }

    /**
     * function which used by child classes to add default column values
     *
     * @param cv in which the values to be saved
     * @return return the modified ContentValues
     */
    @Override
    protected ContentValues overrideColumValues(ContentValues cv) {
        cv = super.overrideColumValues(cv);
        if(mEmail != null){
            cv.put(HLConstants._user_email, mEmail);
        }
        if(mPassword != null){
            cv.put(HLConstants._user_password, mPassword);
        }
        if(mPhone != null){
            cv.put(HLConstants._user_phone, mPhone);
        }

        if(mName != null){
            cv.put(HLConstants._user_name, mName);
        }
        return cv;
    }

    /**
     * Save the content to the DB, check the existence of the table and create if not created
     * if the object is not dirty this would not initiate the save
     *
     * @return true on save success otherwise flase
     */
//    @Override
//    public boolean save() {
//        throw new Error("Save can't be directly performed on user object");
//    }

    /**
     * Exception which will be raised on sign up or signing in
     */
    public static class HLUserExistException extends Exception{

        /**
         * Enum to denote the type of possible login errors
         */
        public static enum ErrorCode{
            EMAIL_EXIST,
            PHONE_EXIST,
            EMAIL_PASSWORD_ERROR,
            INSERT_ERROR
        };

        public ErrorCode mErrorCode;

        /**
         * Constructs a new {@code Exception} with the current stack trace and the
         * specified detail message.
         *
         * @param errorCode the error code for this exception. @see ErrorCode for more
         */
        public HLUserExistException(ErrorCode errorCode) {
            super(defineError(errorCode));
            this.mErrorCode = errorCode;
        }

        /**
         *
         * @param error the error against which the message to be obtained
         * @return of type String
         */
        private static String defineError(ErrorCode error){
            switch(error){
                case EMAIL_EXIST:{
                    return "Email id already taken, Try adding another email";
                }
                case PHONE_EXIST:{
                    return "Mobile number already taken, Try entering another";
                }
                case EMAIL_PASSWORD_ERROR:{
                    return "Failed to locate user against the email and password provided";
                }
                default:{
                    return "Error while saving the object";
                }
            }
        }
    }



}