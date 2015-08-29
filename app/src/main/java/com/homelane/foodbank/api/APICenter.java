package com.homelane.foodbank.api;

import com.hl.hlcorelib.orm.HLObject;

import java.util.List;

/**aon 29/8/15.
 */
public final class APICenter {

    /**
     * The interface talk to the app components
     */
    public static interface  APIInterface{
        /**
         * function which will be called on error
         */
        public void onError();

        /**
         * delegate method which will be called on completion of the request
         *
         * @param results the result obtained from the server
         */
        public void onResult(List<HLObject> results);
    }

    /**
     * function which get the collection center arounwhich is close to the user
     *
     * @param radius the limit in which the collection centers to be picked
     * @param location the localtion of the user
     * @param callback the delegate call back to be called on success or error
     *                 of the request
     */
    public static final void getCollectionCenters(final int radius,
                                            HLObject location, final APIInterface callback){

    }


    /**
     * function which will returns the list of category objects
     *
     * @return returns the list of {@link HLObject} which has the category
     * info
     */
    public static final List<HLObject> getFoodCategories(){
        return null;
    }

    /**
     * function talk to server and updates the status
     *
     * @param mTrip the object against which the trip status to be obtained
     * @param callback the delegate call back to be called on success or error
     *                 of the request
     */
    public static final void getTripStatus(final HLObject mTrip,
                                           final APIInterface callback){

    }

    /**
     * function which request for a pick up
     * 
     * @param mTrip the trip object contains all the values
     * @param callback the delegate call back to be called on success or error
     *                 of the request
     */
    public static final void requestPickUp(final HLObject mTrip,
                                           final APIInterface callback){

    }


}
