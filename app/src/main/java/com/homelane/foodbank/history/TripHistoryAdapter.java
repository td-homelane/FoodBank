package com.homelane.foodbank.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.hl.hlcorelib.orm.HLObject;
import com.homelane.foodbank.R;

import java.util.ArrayList;

/**
 * Created by hl0395 on 29/8/15.
 */
public class TripHistoryAdapter extends RecyclerView.Adapter<TripHistoryAdapter.ViewHolder>{

    /**
     * Holds the instance of customer list presenter, This is specifically to
     * make a delegate call to notify the item click
     */
    TripHistoryPresenter mPresenter;

    /**
     * The data provider for the list view items
     */
    private ArrayList<HLObject> mDataProvider;


    /**
     * Constructor to set the dataPorvider and the reference of the corresponding presenter class
     */
    public TripHistoryAdapter(ArrayList<HLObject> dataProvider, TripHistoryPresenter presenter) {
        super();
        this.mDataProvider = dataProvider;
        mPresenter = presenter;
    }

    /**
     * getter function for mDataProvider
     * @return the ArrayList containing the values
     */
    public ArrayList<HLObject> getmDataProvider() {
        return mDataProvider;
    }

    /**
     * Setter function for mDataProvider
     * @param mDataProvider the data provider to be set
     */
    public void setmDataProvider(ArrayList<HLObject> mDataProvider) {
        this.mDataProvider = mDataProvider;
        notifyDataSetChanged();
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method
     * should update the contents of the {@link ViewHolder#itemView} to reflect the item at
     * the given position.
     * <p/>
     * Note that unlike {@link ListView}, RecyclerView will not call this
     * method again if the position of the item changes in the data set unless the item itself
     * is invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside this
     * method and should not keep a copy of it. If you need the position of an item later on
     * (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will have
     * the updated adapter position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final int pos = position;
        HLObject history = mDataProvider.get(position);



    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p/>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p/>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int)}. Since it will be re-used to display different
     * items in the data set, it is a good idea to cache references to sub views of the View to
     * avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public TripHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_history_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return (mDataProvider != null) ? mDataProvider.size() : 0;
    }

    /**
     * ViewHolder class loads the views for the Recyler view item.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTripDate;
        public TextView mTripFare;
        public TextView mFoodType;
        public TextView mDeliveryStatus;
        /**
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
            mTripDate          = (TextView)itemView.findViewById(R.id.delivered_date);
            mTripFare          = (TextView)itemView.findViewById(R.id.rateTxt);
            mFoodType          = (TextView)itemView.findViewById(R.id.foodType);
            mDeliveryStatus    = (TextView)itemView.findViewById(R.id.delivered_status);
        }
    }

}
