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

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;

import com.hl.hlcorelib.db.HLCoreDatabase;
import com.hl.hlcorelib.utils.HLPreferenceUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hl0204 on 28/7/15.
 * class which is responsible for getting the content from the database
 */
public class HLQuery implements HLQueryInterface{

    /**
     * To hold the instance of the queries which should be chained on load of the contents
     */
    private HashMap<String, HLQuery> mChainedQueries;


    /**
     * Keep track of the not equals keys and entries for it
     */
    private HashMap<String, String> mWhereNotEqualsMap;

    /**
     * Keeps the reference list of string
     */
    private List<String> mReferenceList;

    /**
     * The function which chain the query with the existing query
     *
     * @param key the foreign key in the chaining query
     * @param query query to be chained on load of the actual query
     * @param isReference the value decide the key if foreign key or just a reference
     * @return returns the combined query
     */
    public HLQuery chain(String key, HLQuery query, boolean isReference){
        mChainedQueries = mChainedQueries == null ? new HashMap<String, HLQuery>() :
                mChainedQueries;
        mChainedQueries.put(key, query);
        if(isReference){
            mReferenceList = (mReferenceList==null) ? new ArrayList<String>() : mReferenceList;
            if(!mReferenceList.contains(query.mClassName))
                mReferenceList.add(query.mClassName);
        }
        return this;
    }


    /**
     * Holds the result set which is fetched from the database
     */
    private ArrayList<HLObject> mResultSet;



    /**
     * Holds the table name from which the data needs to be fetched
     */
    private String mClassName;

    /**
     * Holds the where clause map this can be set using whereEquals method
     */
    private HashMap<String, String> mWhereClauseMap;

    /**
     * The map holds the where clause for a list of HLObjects
     */
    private HashMap<String, List<HLObject>> mWhereClauseObjectMap;


    /**
     * Holds teh array of column names to be included in the result set
     */
    private String[] mColumns;





    /**
     * Constructor function for the query
     * @param className the name of  the table from which the content should be fetched
     */
    public HLQuery(String className){
        mClassName = className;
    }


    /**
     * function to set the not equals selection
     *
     * @param key   the key against which the selection to be done
     * @param value the value to be compared
     */
    @Override
    public void whereNotEquals(String key, String value) {
        mWhereNotEqualsMap = (mWhereNotEqualsMap == null) ? new HashMap<String, String>()
                : mWhereNotEqualsMap;
        mWhereNotEqualsMap.put(key, value);
    }

    /**
     * function to set equals selection
     *
     * @param key   against which the selection to be done
     * @param value value to be compared
     */
    @Override
    public void whereEquals(String key, String value) {
        mWhereClauseMap = (mWhereClauseMap == null) ? new HashMap<String, String>() :
                mWhereClauseMap;
        mWhereClauseMap.put(key, value);
    }

    HashMap<String, String[]> mArraySelectionMap;

    /**
     * this compare an array of values against key provided and return the appropriate
     *
     * @param key    against which the selection to be done
     * @param values the values to be compared against a key
     */
    @Override
    public void whereEquals(String key, String[] values) {
        mArraySelectionMap = (mArraySelectionMap == null) ? new HashMap<String, String[]>() :
                mArraySelectionMap;
        mArraySelectionMap.put(key, values);
    }

    /**
     * Funcltion compares a list of HLObject against key provided and return the proper values
     *
     * @param key   of type String name of the column against which the selection should be performed
     * @param value of type List<HLObject> the list of objects to checked against the field
     */
    @Override
    public void whereEquals(String key, List<HLObject> value) {
        mWhereClauseObjectMap = (mWhereClauseObjectMap == null) ? new HashMap<String, List<HLObject>>() :
                mWhereClauseObjectMap;
        mWhereClauseObjectMap.put(key, value);
    }

    /**
     * Set the columns to be reflected in the result set
     * @param columns of type String[] extra columns to be reflected
     */
    public void setMselect(String[] columns){
        mColumns = concat(getDefaultColumns(), columns);
    }

    /**
     * based on the class name columns will be returned
     * @return the default column array will be returned
     */
    protected String[] getDefaultColumns(){
        if(mClassName.equals(HLConstants.USER)){
            return new String[]{HLConstants._ID, HLConstants._createdAt, HLConstants._updatedAt,
                    HLConstants._user_phone, HLConstants._user_password, HLConstants._user_email,
                    HLConstants._user_name
            };
        }else{
            return new String[]{HLConstants._ID, HLConstants._createdAt, HLConstants._updatedAt};
        }
    }

    /**
     * Exception raced on querying the database
     */
    HLQueryException mException;

    /**
     * Function call the db to get the reccords matching the query, The load happens on the main thread
     * @return mResultSet of type List
     */
    public ArrayList<HLObject> query(){
        SQLiteDatabase db = HLCoreDatabase.obtain().getReadableDatabase();

        if(HLPreferenceUtils.obtain().getString(mClassName).isEmpty()){
            mException = new HLQueryException("No items present make sure you call save once against the object to get the result");
            return null;
        }
        String where = null;
        String[] whereArgs = null;
        if(mColumns == null){
            mColumns = getDefaultColumns();
        }
        if(mWhereClauseMap != null && mWhereClauseMap.size() > 0){
            where = "";
            whereArgs = new String[mWhereClauseMap.size()];
            int count = 0;
            for(String key : mWhereClauseMap.keySet()){
                where += ((count == 0) ? "" : " AND ") + key + " = ?";
                whereArgs[count] = mWhereClauseMap.get(key);
                count++;
            }
        }

        if(mWhereNotEqualsMap != null && mWhereNotEqualsMap.size() > 0){
            where = (where == null) ? "" : where;
            whereArgs = concat(whereArgs, new String[mWhereNotEqualsMap.size()]);
            int count = 0;
            for(String key : mWhereNotEqualsMap.keySet()){
                where += ((count == 0) ? "" : " AND ") + key + " != ?";
                whereArgs[count] = mWhereNotEqualsMap.get(key);
                count++;
            }
        }

        if(mWhereClauseObjectMap != null && !mWhereClauseObjectMap.isEmpty()){
            where = (where == null) ? "" : where;
            String oWhere = "";
            String[] oWhereArgs = null;
            for(String key : mWhereClauseObjectMap.keySet()){
                mColumns = concat(mColumns, new String[]{key});
                List<HLObject> list = mWhereClauseObjectMap.get(key);
                if(list != null && !list.isEmpty()){
                    int count = 0;
                    String[] oLocalArgs =new String[list.size()];
                    for(HLObject object : list){
                        oWhere += ((oWhere.isEmpty()) ? "" : " OR ") + key + " = ?";
                        oLocalArgs[count] = object.getmObjectId();
                        count++;
                    }
                    oWhereArgs = concat(oWhereArgs, oLocalArgs);
                }
            }
            if(!oWhere.isEmpty()){
                //String x = "email = ? AND( name = ? OR name = ? )";
                whereArgs = concat(whereArgs, oWhereArgs);
                if(oWhereArgs.length > 1 && !where.isEmpty()){
                    where += " AND( " + oWhere + " )";
                }else{
                    where += (!where.isEmpty() ? " AND " : "") + oWhere;
                }
            }
        }

        if(mArraySelectionMap != null && !mArraySelectionMap.isEmpty()){
            where = (where == null) ? "" : where;
            String aWhere = "";
            String[] aWhereArgs = null;
            for(String key : mArraySelectionMap.keySet()){
                mColumns = concat(mColumns, new String[]{key});
                String[] list = mArraySelectionMap.get(key);
                if(list != null && list.length > 0){
                    int count = 0;
                    String[] oLocalArgs =new String[list.length];
                    for(String id : list){
                        aWhere += ((aWhere.isEmpty()) ? "" : " OR ") + key + " = ?";
                        oLocalArgs[count] = id;
                        count++;
                    }
                    aWhereArgs = concat(aWhereArgs, oLocalArgs);
                }
            }
            if(!aWhere.isEmpty()){
                //String x = "email = ? AND( name = ? OR name = ? )";
                whereArgs = concat(whereArgs, aWhereArgs);
                if(aWhereArgs.length > 1 && !where.isEmpty()){
                    where += " AND( " + aWhere + " )";
                }else{
                    where += (!where.isEmpty() ? " AND " : "") + aWhere;
                }
            }
        }


        Cursor cursor = null;
        try {
            cursor = db.query(mClassName, mColumns, where, whereArgs, null, null, null, null);
            if(null != cursor && cursor.moveToFirst()){
                mResultSet = new ArrayList<HLObject>(cursor.getColumnCount());
                do{
                    mResultSet.add(HLObject.obtain(cursor, mClassName));
                }while (cursor.moveToNext());
                cursor.close();
            }
        }catch(Exception e){
            mException = new HLQueryException(e.getLocalizedMessage());
        }

        if(mResultSet != null && mChainedQueries != null && !mChainedQueries.isEmpty()){
            String[] ids = null;
            if(mReferenceList != null)
                ids = obtainArrayIds(mResultSet);
            for(String key : mChainedQueries.keySet()){
                HLQuery chained = mChainedQueries.get(key);
                key = key.split("_")[0];
                if(mReferenceList == null || !mReferenceList.contains(chained.mClassName)) {
                    chained.whereEquals(key, mResultSet);
                }else{
                    chained.whereEquals(key, ids);
                }
                List<HLObject> result = chained.query();
                if(result != null && !result.isEmpty())
                    insertMatchingItems(mResultSet, result, key);
            }
        }

        return mResultSet;
    }

    /**
     * function creates the array of ids from the provided list
     *
     * @param list from which the id to be extracted
     * @return the array of string containing ids
     */
    private String[] obtainArrayIds(List<HLObject> list){
        if(list == null)
            return null;
        String[] results = new String[list.size()];
        int count = 0;
        for (HLObject object : list){
            results[count] = object.getmObjectId();
            count++;
        }
        return results;
    }

    /**
     * function loop through source and target list and add the source objects to
     * the matching objects in the target list
     *
     * @param targets the target list targets to which the objects to be added
     * @param sources source list from which the object to be populated/home/hl0204/Downloads/DisplayingBitmaps
     * @param key the key against which matched objects will be appended
     */
    private void insertMatchingItems(List<HLObject> targets, List<HLObject> sources, String key){
        for(HLObject target : targets){
            List<HLObject> objects = new ArrayList<>();
            for(HLObject source : sources){
                if(target.getmObjectId().equals(source.getString(key))){
                    objects.add(source);
                }
            }
            target.put(key, objects);
        }
    }

    /**
     * This loads the query in the background and delegate callback should be passed to
     * listen for the load status
     *
     * @param callback to which the call should be delegated on load
     */
    public void query(HLQueryCallback callback){
        QueryLoader loader = new QueryLoader(callback);
        if (Build.VERSION.SDK_INT >= 11) {
            loader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new HLQuery[]{this});
        }else{
            loader.execute(new HLQuery[]{this});
        }

    }

    /**
     * Function combine two arrays
     * @param first
     * @param second
     * @param <T>
     * @return
     */
    public static <T> T[] concat(T[] first, T[] second) {
        if(first == null)
            return second;
        if(second == null)
            return first;
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    /**
     * Callback to listen for the query load
     * #onLoad method will be called on load of the content
     */
    public static interface HLQueryCallback{
        /**
         * Delegate method to be called on completion of the query
         *
         * @param list the lsit of object fetched from database
         * @param error the error raised against the query
         */
        public void onLoad(List<HLObject> list, HLQueryException error);
    }

    /**
     * Exception raised against an error happened while qurying the
     * database
     o */
    public static class HLQueryException extends Exception{
        /**
         * Constructs a new {@code Exception} with the current stack trace and the
         * specified detail message.
         *
         * @param detailMessage the detail message for this exception.
         */
        public HLQueryException(String detailMessage) {
            super(detailMessage);
        }
    }

    /**
     * Class takes care of loading the contents
     *
     */
    private class QueryLoader extends AsyncTask<HLQuery, Void, List<HLObject>>{



        /**
         * Delegation callback on load
         */
        private HLQueryCallback mCallback;

        /**
         * Constructor function which set the deligation callback and return the instance of
         * QueryLoader
         *
         * @param mCallback to where the call should be deligated on load
         */
        private QueryLoader(HLQueryCallback mCallback){
            this.mCallback = mCallback;
        }

        /**
         * Runs on the UI thread before {@link #doInBackground}.
         *
         * @see #onPostExecute
         * @see #doInBackground
         */
        @Override
        protected void onPreExecute() {
            HLQuery.this.mException = null;
            super.onPreExecute();
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected List<HLObject> doInBackground(HLQuery... params) {
            HLQuery query = params[0];
            List<HLObject> list = query.query();
            return list;
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
            if(mCallback != null){
                mCallback.onLoad(hlObjects, HLQuery.this.mException);
            }
        }
    }


}
