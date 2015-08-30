package com.homelane.foodbank.pickup;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hl.hlcorelib.mvp.HLView;
import com.homelane.foodbank.R;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

/**
 * Created by hl0395 on 29/8/15.
 */
public class FoodPickupView implements HLView {

    private View mView;
    TextView mPackedFood, mRawFood;
    RelativeLayout mTypeLayout,mConfirmLayout;
    TextView mSelectedFoodType,mCuurentLocation,mDestinationLocation,mFareEstimate;
    Button mBookBtn;
    ProgressBar mFareStatusProgress;



    /**
     * Create the view from the id provided
     *
     * @param inflater inflater using which the view shold be inflated
     * @param parent   to which the view to be attached
     */
    @Override
    public void init(LayoutInflater inflater, ViewGroup parent) {
        mView = inflater.inflate(R.layout.pickup_layout, parent, false);
        mPackedFood = (TextView) mView.findViewById(R.id.packed_text);
        mRawFood = (TextView) mView.findViewById(R.id.raw_material);
        mTypeLayout = (RelativeLayout) mView.findViewById(R.id.typeLayout);
        mConfirmLayout = (RelativeLayout) mView.findViewById(R.id.confirmLayout);
        mSelectedFoodType = (TextView) mView.findViewById(R.id.selectedType);
        mCuurentLocation = (TextView) mView.findViewById(R.id.current_location);
        mDestinationLocation = (TextView) mView.findViewById(R.id.destination_name);
        mFareEstimate = (TextView) mView.findViewById(R.id.fare_estimate);
        mBookBtn = (Button) mView.findViewById(R.id.bookBtn);
        mFareStatusProgress = (ProgressBar) mView.findViewById(R.id.fareProgressBar);
        FloatingActionButton floatingActionButton = (FloatingActionButton) mView.findViewById(R.id.fab);

        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(mView.getContext())
                .setTheme(SubActionButton.THEME_DARK);

        ImageView rlIcon1 = new ImageView(mView.getContext());
        ImageView rlIcon2 = new ImageView(mView.getContext());
        ImageView rlIcon3 = new ImageView(mView.getContext());
        ImageView rlIcon4 = new ImageView(mView.getContext());

        rlIcon1.setImageDrawable(mView.getContext().getResources().getDrawable(R.mipmap.clothing));
        rlIcon2.setImageDrawable(mView.getContext().getResources().getDrawable(R.mipmap.medicalsupplies));
        rlIcon3.setImageDrawable(mView.getContext().getResources().getDrawable(R.mipmap.sharefood));
        rlIcon4.setImageDrawable(mView.getContext().getResources().getDrawable(R.mipmap.appicon));


        // Set 4 SubActionButtons
        final FloatingActionMenu centerBottomMenu = new FloatingActionMenu.Builder(mView.getContext())
                .setStartAngle(0)
                .setEndAngle(180)
                .setAnimationHandler(new SlideInAnimationHandler())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon1).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon2).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon3).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon4).build())
                .attachTo(floatingActionButton)
                .build();

        rlIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                centerBottomMenu.close(true);
                mSelectedFoodType.setText("Clothing");
            }
        });

        rlIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                centerBottomMenu.close(true);
                mSelectedFoodType.setText("Medicines");

            }
        });
        rlIcon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                centerBottomMenu.close(true);
                mSelectedFoodType.setText("Food");

            }
        });

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
//        if(mConfirmLayout.getVisibility() == View.VISIBLE){
//            mSelectedFoodType.setText("");
//            mDestinationLocation.setText("");
//            mConfirmLayout.setVisibility(View.GONE);
//            mTypeLayout.setVisibility(View.VISIBLE);
//            return true;
//        }
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
