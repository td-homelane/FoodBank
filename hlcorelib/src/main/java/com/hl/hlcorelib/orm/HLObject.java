/*
 * Copyright (c) 2015 HomeLane.com
 * All Rights Reserved.
 * All information contained herein is, and remains the property of HomeLane.com.
 * The intellectual and technical concepts contained herein are proprietary to
 * HomeLane.com Inc and may be covered by U.S. and Foreign Patents, patents in process,
 * and are protected by trade secret or copyright law. This product can not be
 * redistributed in full or parts without permission from HomeLane.com. Dissemination
 * of this information or reproduction of this material is strictly forbidden unless
 * prior written permission is obtained from HomeLane.com.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.hl.hlcorelib.orm;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hl.hlcorelib.HLPrimitiveGetSetInterface;
import com.hl.hlcorelib.db.HLCoreDatabase;
import com.hl.hlcorelib.utils.HLPreferenceUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * The class Act as the common VO class for the project
 * This holds all basic items such as Integer, Float, String this also holds the array of HLObject
 * if there is any
 * All the fields fill be accessed against a key
 */
public class HLObject implements HLPrimitiveGetSetInterface, Parcelable{


    /**
     * Decides the object is synced with the db or not
     */
    boolean mIsSynced;

    /**
     * Map keep track of the unique key's
     */
    HashMap<String, Boolean> mUniqueKeyMap;

    /**
     * Decides if the content is fetched from the database or not,
     * if not you can call fetch method to obtain the whole content
     */
     boolean mFetched;


    public String getmClassName() {
        return mClassName;
    }

    /**
     * Constructor function, the name should be passed this indicates from which table the obejct to be
     * fetched or saved to
     * @param className of type String, Table to which the object should be saved
     */
    public HLObject(String className){
        this.mClassName = className;
    }


    /**
     * Getter function for mObjectId
     * @return returns the unique id of the object
     */
    public String getmObjectId() {
        return mObjectId;
    }

    public void setmObjectId(String mObjectId) {
        this.mObjectId = mObjectId;
    }

    /**
     * Holds the unique id of the object
     */
    protected String mObjectId;

    /*
     * Switch to indicate the object is backed or not, means if this is false that means this object has
     * changes which needs to be saved to the permenent storage(Database, File or SharedPreference)
     */
     protected boolean mIsDirty;

    /**
     * This will be the class name or name of the object, if the object is saved in SQLite this indicates the
     * table name of the object
     */
    private String mClassName;

    /**
     * Holds the string values, each values will be saved agaimst a key
     */
    private HashMap<String, String> mStringMap;

    /**
     * Holds the integer values, each values will be saved agaimst a key
     */
    private HashMap<String, Integer> mIntegerMap;

    /**
     * Holds the float values, each values will be saved agaimst a key
     */
    private HashMap<String, Float> mFloatMap;

    /**
     * Holds the collections of HLObject against a key
     */
    private HashMap<String, List<HLObject>> mArrayMap;


    /**
     * Holds the collection of HLObject against a key
     */
    private HashMap<String, HLObject> mObjectMap;

    /**
     * Reference to the time which the object created, this will be system time in milliseconds
     */
    protected long mCreatedTime;


    /**
     * The time when a change happened to any of the object properties this will be time is milli seconds
     */
    protected long mUpdatedTime;

    public Date getmCreatedTime() {
        return new Date(mCreatedTime);
    }

    /**
     * Function set the unique key map, this further calls {@link #put(String, String)}
     * @param isUnique decides the key is unique or not
     */
    public void put(String key, String value, boolean isUnique){
        if(mUniqueKeyMap == null){
            mUniqueKeyMap = new HashMap<>();
        }
        mUniqueKeyMap.put(key, isUnique);
        put(key, value);
    }

    /**
     * Function set the List<HLObject> to the #mArrayMap
     *
     * @param key the key against which the object will be put
     * @param value the objects to be saved against the key
     */
    public void put(String key, List<HLObject> value){
        if(mArrayMap == null){
            mArrayMap = new HashMap<String, List<HLObject>>();
        }
        mArrayMap.put(key, value);
    }

    /**
     * function returns the list of objects against the key
     *
     * @param key the key gainst which the list needs to be fetched
     * @return returns the list of objects saved against key
     */
    public List<HLObject> getList(String key){
        if(mArrayMap == null)
            return null;
        return mArrayMap.get(key);
    }



    /**
     * Function save the value against the key provided update the values only if the content is not matching with the
     * existing content otherwise returned false
     * @param key of type String, The key against which the value should be saved
     * @param value of type String, The value which needs to be saved against key
     */
    @Override
    public boolean put(String key, int value){
        mIntegerMap = (mIntegerMap == null) ? new HashMap<String, Integer>() :
                mIntegerMap;
        if(mIntegerMap.get(key) != null && mIntegerMap.get(key).equals(value)){
           return false;
        }
        mIntegerMap.put(key, value);
        mIsDirty = true;
        return true;
    }

    /**
     * Function save the value against the key provided update the values only if the content is not matching with the
     * existing content otherwise returned false
     * @param key of type String, The key against which the value should be saved
     * @param value of type Float, The value which needs to be saved against key
     */
    @Override
    public boolean put(String key, float value){
        mFloatMap = (mFloatMap == null) ? new HashMap<String, Float>() :
                mFloatMap;
        if(mFloatMap.get(key) != null && mFloatMap.get(key).equals(value)) {
            return false;
        }
        mFloatMap.put(key, value);
        mIsDirty = true;
        return true;
    }

    /**
     * Function save the value against the key provided, update the values only if the content is not matching with the
     * existing content otherwise returned false
     * @param key of type String, The key against which the value should be saved
     * @param value of type HLObject, The value which needs to be saved against key
     */
    @Override
    public boolean put(String key, String value){
        mStringMap = (mStringMap == null) ? new HashMap<String, String>() :
                mStringMap;
        if(mStringMap.get(key) != null && mStringMap.get(key).equals(value)) {
            return false;
        }
        mStringMap.put(key, value);
        mIsDirty = true;
        return true;
    }

    /**
     * Function save the value against the key provided
     * @param key of type String, The key against which the value should be saved
     * @param value of type String, The value which needs to be saved against key
     */
    public boolean put(String key, HLObject value){
        mObjectMap = (mObjectMap == null) ? new HashMap<String, HLObject>() :
                mObjectMap;
        mObjectMap.put(key, value);
        mIsDirty = true;
        return true;
    }

    /**
     * Function save the value against the key provided
     *
     * @param key   of type String, The key against which the value should be saved
     * @param value of type boolean, The value which needs to be saved against key
     */
    @Override
    public boolean put(String key, boolean value) {
        return false;
    }

    /**
     * Function return the value which is saved against the key from the
     * mStringMap
     * @param key of type String
     * @return of type String
     */
    @Override
    public String getString(final String key){
        return (mStringMap == null) ? null : mStringMap.get(key);
    }


    /**
     * Function return the value which is saved against the key from the
     * mStringMap
     * @param key of type String
     * @return of type String
     */
    @Override
    public float getFloat(final String key){
        return (mFloatMap == null) ? null : mFloatMap.get(key);
    }

    /**
     * Function return the value which is saved against the key
     *
     * @param key of type String
     * @return of type boolean
     */
    @Override
    public boolean getBoolean(String key) {
        return false;
    }

    /**
     * Function return the value which is saved against the key
     *
     * @param key of type String
     * @return of type int
     */
    @Override
    public int getInteger(String key) {
        return (mIntegerMap == null) ? null : mIntegerMap.get(key);
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
        dest.writeString(mClassName);
        dest.writeString(mObjectId);
        dest.writeLong(mUpdatedTime);
        dest.writeLong(mCreatedTime);
        dest.writeInt(mIsDirty ? 1 : 0);
        dest.writeInt(mIsSynced ? 1 : 0);
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * function which used by child classes to add default column values
     *
     * @param cv in which the values to be saved
     * @return return the modified ContentValues
     */
    protected ContentValues overrideColumValues(ContentValues cv){
        return cv;
    }


    /**
     *
     */
    public static final Parcelable.Creator<HLObject> CREATOR
            = new Parcelable.Creator<HLObject>() {
        /**
         *
         * @param in
         * @return
         */
        public HLObject createFromParcel(Parcel in) {
            return new HLObject(in);
        }

        /**
         * @param size
         * @return
         */
        public HLObject[] newArray(int size) {
            return new HLObject[size];
        }
    };

    /**
     * function which recreate the object from the Parcel
     * @param in of type Parcel
     */
    HLObject(Parcel in) {
        mClassName = in.readString();
        mObjectId  = in.readString();
        mUpdatedTime = in.readLong();
        mCreatedTime = in.readLong();
        mIsDirty     = (in.readInt() == 1);
        mIsSynced    = (in.readInt() == 1);
    }

    /**
     * function return all the columns to be inserted or updated
     *
     * @return the total number of column for the db transaction
     */
    int getAllColumnCount(){
        int count = 0;
        if(mObjectMap != null)
            count += mObjectMap.size();
        if(mStringMap != null)
            count += mStringMap.size();
        return count;
    }

    /**
     * function which decides to create the table for the object or not
     *
     * @param db the DataBase against which the table to be added
     * @throws HLQuery.HLQueryException
     */
    private void createOrAlterTable(SQLiteDatabase db) throws HLQuery.HLQueryException{
        String columns = HLPreferenceUtils.obtain().getString(mClassName);
        if(columns.isEmpty()){
            String table = "create table if not exists " + mClassName + " (_id varchar primary key not null unique";
            if(mFloatMap != null){
                for(String key : mFloatMap.keySet()){
                    table += " , " + key + " real";
                }
            }
            if(mStringMap != null){
                for(String key : mStringMap.keySet()){
                    boolean unique = (mUniqueKeyMap != null && mUniqueKeyMap.containsKey(key)
                            && mUniqueKeyMap.get(key));
                    table += " , " + key + " text" + (!unique ? "" : " unique");
                }
            }

            if(mIntegerMap != null){
                for(String key : mIntegerMap.keySet()){
                    table += " , " + key + " integer";
                }
            }



            table = addDefaultColumns(table);
            if(mObjectMap != null){
                String keyMap = "";
                String foreignKeyMap = "";
                for(String key : mObjectMap.keySet()){
                    HLObject object = mObjectMap.get(key);
                    keyMap += ", " + key + " varchar";
                    foreignKeyMap += " , FOREIGN KEY (" + key + ") REFERENCES " + object.getmClassName() + "("
                            + HLConstants._ID   + ") ON DELETE CASCADE";
                }
                table += keyMap + foreignKeyMap + ")";
            }else{
                table += ")";
            }
            try {
                db.execSQL(table);
                HLPreferenceUtils.obtain().put(mClassName, getObjectColumns());
            }catch (SQLException e){
                throw new HLQuery.HLQueryException(e.getMessage());
            }
        }else{
            String alterStatement = "";
            if(mIntegerMap != null) {
                for (String key : mIntegerMap.keySet()) {
                    columns = alterOrAppendColumn(columns, key, Cursor.FIELD_TYPE_INTEGER, db);
                }
            }
            if(mStringMap != null) {
                for (String key : mStringMap.keySet()) {
                    columns = alterOrAppendColumn(columns, key, Cursor.FIELD_TYPE_STRING, db);
                }
            }
            if(mFloatMap != null) {
                for (String key : mFloatMap.keySet()) {
                    columns = alterOrAppendColumn(columns, key, Cursor.FIELD_TYPE_FLOAT, db);
                }
            }
            if(mObjectMap != null) {
                for (String key : mObjectMap.keySet()) {
                    if(!columns.contains(key)) {
                        columns += (columns.isEmpty()) ? key : "," + key;
                        alterStatement = "ALTER TABLE " + mClassName + " ADD COLUMN ";
                        HLObject object = mObjectMap.get(key);
                        String keyMap = key + " varchar";
                        String foreignKeyMap = " FOREIGN KEY (" + key + ") REFERENCES " + object.getmClassName() + "("
                                + HLConstants._ID + ")";
                        alterStatement += keyMap + foreignKeyMap + ")";
                        alterTable(alterStatement, db, columns);
                    }
                }
            }
        }
    }

    /**
     * Function execute the db statement
     *
     * @param statement the statement to be executed
     * @param db the db instance against which statement to be executed
     * @param columns the columns to be mapped to {@link android.content.SharedPreferences}
     *                to compare latter
     */
    private void alterTable(String statement, SQLiteDatabase db, String columns) throws HLQuery.HLQueryException{
        try{
            db.execSQL(statement);
            HLPreferenceUtils.obtain().put(mClassName, columns);
        }catch (SQLException e){
            throw new HLQuery.HLQueryException(e.getMessage());
        }
    }

    /**
     * Function create a alter command or append columns to it
     * @param columns the existing columns
     * @param key the key to be checked against the existing columns
     * @param type type of the column
     */
    private String alterOrAppendColumn(String columns,
                                     String key, int type, SQLiteDatabase db) throws HLQuery.HLQueryException{
        if(!columns.contains(key)){
            columns += (columns.isEmpty()) ? key : "," + key;
            String statement = "ALTER TABLE " +
                    mClassName + " ADD COLUMN ";
            switch (type){
                case Cursor.FIELD_TYPE_INTEGER :{
                    statement += key + " integer";
                    break;
                }
                case Cursor.FIELD_TYPE_FLOAT :{
                    statement += key + " real";
                    break;
                }
                case Cursor.FIELD_TYPE_STRING :{
                    boolean unique = (mUniqueKeyMap != null && mUniqueKeyMap.containsKey(key)
                            && mUniqueKeyMap.get(key));
                    statement += key + " text" + (!unique ? "" : " unique");
                    break;
                }
            }
            alterTable(statement, db, columns);
        }
        return columns;
    }


    /**
     * function which delete the record from the Database
     *
     * @throws HLDeleteException
     */
    public void delete() throws HLDeleteException{
        delete(new String[]{mObjectId}, mClassName);
    }

    /**
     * function deletes all the records matches the ids passed
     *
     * @throws HLDeleteException
     * @param ids the ids of the object to be deleted
     * @return true if success else false
     */
    static void delete(String[] ids, String mClassName) throws HLDeleteException{
        SQLiteDatabase db = HLCoreDatabase.obtain().getWritableDatabase();
        String args = TextUtils.join(", ", ids);
        try {
            db.execSQL(String.format("DELETE FROM " + mClassName + " WHERE " + HLConstants._ID + " IN (%s);", args));
        }catch(SQLException e){
            throw new HLDeleteException(e.getLocalizedMessage());
        }
    }

    /**
     * function delete list of objects from the database
     * <b>NOTE provide the object with same class name, otherwise throws an exception <b/>
     *
     * @param list the list of objects to be deleted
     * @throws HLDeleteException
     */
    public static void deleteAll(@NonNull List<HLObject> list,
                                 HLDeleteCallBack callback){
        AsyncSaveDelete loader = new AsyncSaveDelete(callback, AsyncSaveDelete.DELETE);
        if (Build.VERSION.SDK_INT >= 11) {
            loader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, list);
        }else{
            loader.execute(list);
        }
    }



    /**
     * Save the content to the DB, check the existence of the table and create if not created
     * if the object is not dirty this would not initiate the save
     * @return true on save success otherwise false
     */
    public boolean save() throws HLQuery.HLQueryException{
        if(mIsDirty){
            SQLiteDatabase db = HLCoreDatabase.obtain().getWritableDatabase();
            createOrAlterTable(db);
            ContentValues cv = new ContentValues();
            try {
                if (!mIsSynced) {
                    mCreatedTime = new Date().getTime();
                    mUpdatedTime = new Date().getTime();
                    mObjectId = Long.toHexString(System.nanoTime());
                    cv.put(HLConstants._ID, mObjectId);
                    cv = setMapValues(cv);
                    cv.put(HLConstants._createdAt, mCreatedTime);
                    cv.put(HLConstants._updatedAt, mUpdatedTime);
                    cv = overrideColumValues(cv);
                    mIsSynced = (db.insertOrThrow(mClassName, null, cv) > 0);
                    mIsDirty = !mIsSynced;
                    return mIsSynced;
                } else {
                    mUpdatedTime = new Date().getTime();
                    cv = setMapValues(cv);
                    cv.put(HLConstants._updatedAt, mUpdatedTime);
                    cv = overrideColumValues(cv);
                    mIsSynced = (db.update(mClassName, cv, HLConstants._ID + "  = ? ", new String[]{mObjectId}) > 0);
                    mIsDirty = !mIsSynced;
                    return mIsSynced;
                }
            }catch(SQLiteConstraintException e){
                throw new HLQuery.HLQueryException(e.getMessage());
            }

        }
        return false;
    }

    /**
     * Function add the default columns to the table creation query
     *
     * @param tableQuery the query to which the default columns to be appended
     * @return return the cobined string with default columns
     */
    protected String addDefaultColumns(String tableQuery){
        return tableQuery + ", " + HLConstants._createdAt + " integer not null, " +
                HLConstants._updatedAt + " integer not null";
    }

    /**
     * Function set the values to the content values from the
     * primitive hashmaps
     * @param cv
     * @return
     */
    private ContentValues setMapValues(ContentValues cv){
        if(mFloatMap != null) {
            for (String key : mFloatMap.keySet()) {
                cv.put(key, mFloatMap.get(key));
            }
        }
        if(mStringMap != null) {
            for (String key : mStringMap.keySet()) {
                cv.put(key, mStringMap.get(key));
            }
        }
        if(mIntegerMap != null) {
            for (String key : mIntegerMap.keySet()) {
                cv.put(key, mIntegerMap.get(key));
            }
        }
        if(mObjectMap != null){
            for (String key : mObjectMap.keySet()) {
                cv.put(key, mObjectMap.get(key).mObjectId);
            }
        }
        return cv;
    }

    /**
     *
     * @return the all columns belongs to this object
     */
    protected String getObjectColumns(){
        String columns = "";
        if(mIntegerMap != null) {
            for (String key : mIntegerMap.keySet()) {
                columns += (columns.isEmpty()) ? key : "," + key;
            }
        }
        if(mStringMap != null) {
            for (String key : mStringMap.keySet()) {
                columns += (columns.isEmpty()) ? key : "," + key;
            }
        }
        if(mFloatMap != null) {
            for (String key : mFloatMap.keySet()) {
                columns += (columns.isEmpty()) ? key : "," + key;
            }
        }
        if(mObjectMap != null) {
            for (String key : mObjectMap.keySet()) {
                columns += (columns.isEmpty()) ? key : "," + key;
            }
        }
        columns += ((columns.isEmpty()) ? "" : ",") + HLConstants._ID + "," +
                HLConstants._updatedAt + "," + HLConstants._createdAt;
        return columns;
    }



    /**
     * return the set of key values for the object from all primitive types
     * this will be further used to create or later the table
     */
    private Set<String> getAllKeys(){

        Set<String> mKeySet = null;
//        if(mIntegerMap != null){
//            mKeySet = mIntegerMap.keySet();
//        }
//
//        if(mFloatMap != null){
//            if(mKeySet == null)
//                mKeySet = mFloatMap.keySet();
//            else
//                mKeySet.addAll(mFloatMap.keySet());
//        }
//
//        if(mStringMap != null){
//            if(mKeySet == null)
//                mKeySet = mStringMap.keySet();
//            else
//                mKeySet.addAll(mStringMap.keySet());
//        }

        return  mKeySet;
    }

    /**
     * Check if the column is a default column or not
     * @param column of type String against which the check should happen
     * @return of type boolean
     */
    protected boolean isDefaultColumns(String column){
        return (column.equals(HLConstants._ID) || column.equals(HLConstants._createdAt) ||
                column.equals(HLConstants._updatedAt));
    }

    /**
     * Function which will fetch the content from the database
     * if the fetch is performed the content would not be fetched also it checks the
     * existence of table in the preference and then initiate the fetch
     *
     * on success full fetch this set mIsDirty to false and mFetched to true
     * <b>This fetch happens on the main thread<b/>
     */
    public void fetch(){
        if(!mFetched && HLPreferenceUtils.obtain().getString(mClassName) != null){
            SQLiteDatabase db = HLCoreDatabase.obtain().getReadableDatabase();
            Cursor cursor = db.query(mClassName, null, HLConstants._ID + " = ? ",
                    new String[]{mObjectId}, null, null, null);
            if(null != cursor && cursor.moveToFirst()){
                for(int i = 0; i < cursor.getColumnCount(); i++){
                    if(!isDefaultColumns(cursor.getColumnNames()[i])) {
                        if (cursor.getType(i) == Cursor.FIELD_TYPE_FLOAT) {
                            put(cursor.getColumnName(i), (float)cursor.getFloat(i));
                        } else if (cursor.getType(i) == Cursor.FIELD_TYPE_INTEGER) {
                            put(cursor.getColumnName(i), (int)cursor.getInt(i));
                        } else {
                            put(cursor.getColumnName(i), cursor.getString(i));
                        }
                    }
                }
                cursor.close();
                mIsSynced = true;
                mFetched = true;
                mIsDirty = false;
            }
        }
    }

    /**
     * Create object from the cursor record
     * @param cursor from which the value should be fetched
     * @param className the name of the Object name
     * @return newly created object
     */
    static HLObject obtain(Cursor cursor, String className){
        boolean isUser = className.endsWith(HLConstants.USER);
        HLObject object = isUser ? new HLUser() :
                new HLObject(className);
        for(int i = 0; i < cursor.getColumnCount(); i++){
            if(!object.isDefaultColumns(cursor.getColumnNames()[i])) {
                if (cursor.getType(i) == Cursor.FIELD_TYPE_FLOAT) {
                    object.put(cursor.getColumnName(i), cursor.getFloat(i));
                } else if (cursor.getType(i) == Cursor.FIELD_TYPE_INTEGER) {
                    object.put(cursor.getColumnName(i), cursor.getInt(i));
                } else {
                    object.put(cursor.getColumnName(i), cursor.getString(i));
                }
            }
        }
        object.mObjectId = cursor.getString(cursor.getColumnIndex(HLConstants._ID));
        object.mUpdatedTime = cursor.getInt(cursor.getColumnIndex(HLConstants._updatedAt));
        object.mCreatedTime = cursor.getInt(cursor.getColumnIndex(HLConstants._createdAt));
        if(isUser){
            ((HLUser)object).setmEmail(cursor.getString(cursor.getColumnIndex(HLConstants._user_email)));
            ((HLUser)object).setmName(cursor.getString(cursor.getColumnIndex(HLConstants._user_name)));
            ((HLUser)object).setmPhone(cursor.getString(cursor.getColumnIndex(HLConstants._user_phone)));
            ((HLUser)object).mPassword = cursor.getString(cursor.getColumnIndex(HLConstants._user_password));
        }
        object.mIsSynced = true;
        object.mIsDirty  = false;
        return object;
    }

    /**
     * function save a list of HLObject to the database, this internally initiate one DB transaction
     * to make the save faster
     *
     * @param list the list object to be saved
     * @return the list of saved objects
     */
    public static List<HLObject> saveAll(List<HLObject> list){
        if(list == null)
            return null;
        List<HLObject> result = new ArrayList<>(list.size());
        for(HLObject object : list){
            try {
                if(object.save()){
                    result.add(object);
                }
            }catch (HLQuery.HLQueryException e){

            }
        }
        return result;
    }

    /**
     * function initiate a background thread to do the save operation
     *
     * @param list the list object to be saved
     * @param callback the callback to listen for the operation
     */
    public static void saveAll(List<HLObject> list, SaveAllCallBack callback){
        AsyncSaveDelete loader = new AsyncSaveDelete(callback, AsyncSaveDelete.SAVE);
        if (Build.VERSION.SDK_INT >= 11) {
            loader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, list);
        }else{
            loader.execute(list);
        }
    }

    /**
     * Interface holds the delegate methods to notify
     * the listener about the save status
     */
    public static interface SaveAllCallBack{
        /**
         * function which will be called on save of all the objects
         * @param result the reference to all saved objects
         */
        public void onSave(List<HLObject> result);
    }

    /**
     * Interface holds delegate method to notify the listeners about the
     * delete status
     */
    public static interface HLDeleteCallBack {
        /**
         *
         * @param exception exception raised against delete operation
         */
        public void onDelete(HLDeleteException exception);
    }


    /**
     * Implementation of AsyncTask which takes care of saving a list of obkects
     */
    private static class AsyncSaveDelete extends AsyncTask<List<HLObject>, Void, List<HLObject>>{

        private static final int SAVE = 1;
        private static final int DELETE = 2;

        private Object mCallBack;

        int mType;

        HLDeleteException mDeleteException;

        /**
         * Constructs a new instance of {@code Object}.
         *
         * @param mCallBack the callback to be called on finish
         * @param mType the type of the operation to be performed {#SAVE #DELETE}
         */
        public AsyncSaveDelete(Object mCallBack, int mType) {
            super();
            this.mType = mType;
            this.mCallBack = mCallBack;
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param lists The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected List<HLObject> doInBackground(List<HLObject>... lists) {
            List<HLObject> list = lists[0];
            if(mType == SAVE) {
                return saveAll(list);
            }else if(list != null){
                String className = null;
                String[] ids = new String[list.size()];
                int index = 0;
                for (HLObject object : list){
                    if(className == null){
                        className = object.mClassName;
                    }else if(!className.equals(object.mClassName)){
                        mDeleteException = new
                                HLDeleteException("The list should contains same object types");
                        break;
                    }
                    ids[index] = object.mObjectId;
                    index++;
                }
                try {
                    delete(ids, className);
                }catch (HLDeleteException e){
                    mDeleteException = e;
                }
            }else{
                mDeleteException = new HLDeleteException("The list cannot be null");
            }
            return null;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param hlObjects The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(List<HLObject> hlObjects) {
            super.onPostExecute(hlObjects);
            if(mCallBack != null){
                if(mType == SAVE) {
                    ((SaveAllCallBack) mCallBack).onSave(hlObjects);
                } else{
                    ((HLDeleteCallBack)mCallBack).onDelete(mDeleteException);
                }

            }
        }


    }

    /**
     * Exception will be raised against the deletion of content
     */
    public static class HLDeleteException extends Exception{
        public HLDeleteException(String detailMessage) {
            super(detailMessage);
        }
    }
}
