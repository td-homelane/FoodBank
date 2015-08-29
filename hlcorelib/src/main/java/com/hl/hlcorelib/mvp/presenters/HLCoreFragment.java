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

package com.hl.hlcorelib.mvp.presenters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.hl.hlcorelib.HLBackstackInterface;
import com.hl.hlcorelib.mvp.HLView;
import com.hl.hlcorelib.mvp.events.HLDispatcher;
import com.hl.hlcorelib.mvp.events.HLEvent;
import com.hl.hlcorelib.mvp.events.HLEventDispatcher;
import com.hl.hlcorelib.mvp.events.HLEventListener;
import com.hl.hlcorelib.utils.HLFragmentUtils;

/**
 * Class which can be used as the base class for all the Fragments in the system
 * The extending classes can make use most of the in built functionality
 * Created by HL0204 on 09-07-2015.
 */
public abstract class HLCoreFragment<V extends HLView> extends Fragment implements
        HLBackstackInterface, HLDispatcher {


    /**
     * Decides the view is bound or not
     */
    protected boolean mViewBound;

    /**
     * Holds the HLView instance
     */
    protected V mView;


    /**
     * Holds the enclosing activity
     */
    protected HLCoreActivityPresenter mActivity;

    /**
     * Constructor function
     */
    public HLCoreFragment(){

    }

    /**
     * Called when the Fragment is no longer resumed.  This is generally
     * tied to {@link Activity#onPause() Activity.onPause} of the containing
     * Activity's lifecycle.
     */
    @Override
    public final void onPause() {
        beforePause();
        super.onPause();
    }

    /**
     * Function which will be called before the execution of {@link Fragment#onPause()}
     * generally this hides the soft keyboard id there is any
     */
    protected void beforePause(){
        hideSoftKeyBoard();
    }

    /**
     * function shows the toast message
     * @param text the message to be shown in the Toast
     */
    protected void showToast(String text){
        if(getActivity() != null && isResumed()){
            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p/>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            restoreInstanceState(savedInstanceState);
        }
        try {
            mView = getVuClass().newInstance();
            mView.init(inflater, container);
            onBindView();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return mView.getView();
    }



    /**
     * Called when the view previously created by {@link #onCreateView} has
     * been detached from the fragment.  The next time the fragment needs
     * to be displayed, a new view will be created.  This is called
     * after {@link #onStop()} and before {@link #onDestroy()}.  It is called
     * <em>regardless</em> of whether {@link #onCreateView} returned a
     * non-null view.  Internally it is called after the view's state has
     * been saved but before it has been removed from its parent.
     */
    @Override
    public final void onDestroyView() {
        onDestroyHLView();
        mView = null;
        super.onDestroyView();
    }

    /**
     * Called to ask the fragment to save its current dynamic state, so it
     * can later be reconstructed in a new instance of its process is
     * restarted.  If a new instance of the fragment later needs to be
     * created, the data you place in the Bundle here will be available
     * in the Bundle given to {@link #onCreate(Bundle)},
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}, and
     * {@link #onActivityCreated(Bundle)}.
     * <p/>
     * <p>This corresponds to {@link Activity#onSaveInstanceState(Bundle)
     * Activity.onSaveInstanceState(Bundle)} and most of the discussion there
     * applies here as well.  Note however: <em>this method may be called
     * at any time before {@link #onDestroy()}</em>.  There are many situations
     * where a fragment may be mostly torn down (such as when placed on the
     * back stack with no UI showing), but its state will not be saved until
     * its owning activity actually needs to save its state.
     *
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    public final void onSaveInstanceState(Bundle outState) {
        saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    /**
     * Called when all saved state has been restored into the view hierarchy
     * of the fragment.  This can be used to do initialization based on saved
     * state that you are letting the view hierarchy track itself, such as
     * whether check box widgets are currently checked.  This is called
     * after {@link #onActivityCreated(Bundle)} and before
     * {@link #onStart()}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public final void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        restoreInstanceState(savedInstanceState);
    }

    /**
     * Function which will be called {@link android.app.Fragment#onSaveInstanceState(Bundle)}
     *
     * @param savedInstanceState the state to save the contents
     */
    protected void saveInstanceState(Bundle savedInstanceState){}

    /**
     * Function which will be triggered when {@link android.app.Fragment#onViewStateRestored(Bundle)}
     *
     * @param data the state which saved on {Fragment#onSaveInstanceState}
     */
    protected void restoreInstanceState(@Nullable Bundle data){}


    /**
     * Function which return the enclosing view class, this will be used to
     * create the respective view bind it to the Context
     * @return return the enclosed view class
     */
    protected abstract Class<V> getVuClass();

    /**
     * Function which will be called when the view is bind to the presenter,
     * This is useful to set the user interaction listeners to the View
     */
    protected void onBindView(){
        mViewBound = true;
    }

    /**
     * Called on when onDestroy fired for the presenter. Prior to destroy the enclosing view
     * and resources can be released
     */
    protected void onDestroyHLView(){

    }

    /**
     * Called when a fragment loads an animation.
     *
     * @param transit
     * @param enter
     * @param nextAnim
     */
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation animation = super.onCreateAnimation(transit, enter, nextAnim);
        if(animation != null)
            animation.setDuration((HLFragmentUtils.ALLOW_FRAGMENT_ANIMATION) ? 0 :
                    animation.getDuration());
        return animation;
    }

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * {@link #setRetainInstance(boolean)} to retain their instance,
     * as this callback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after {@link #onCreateView}
     * and before {@link #onViewStateRestored(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (HLCoreActivityPresenter)getActivity();
    }

    /**
     * Called when a fragment is first attached to its activity.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (HLCoreActivityPresenter)getActivity();
    }

    /**
     * function notifies the activity if the back press handled inside fragment or not
     * @return true if handled else false
     */
    public boolean onBackPressed(){
        return false;
    }

    /**
     * Function which will be used to pop a Fragment from the backstack
     *
     * @return true success otherwise false
     */
    @Override
    public boolean pop() {
        return mActivity.pop();
    }

    /**
     * Function which push a new Fragment to the backstack
     *
     * @param transaction of type HLFragmentTransaction
     */
    @Override
    public void push(HLFragmentUtils.HLFragmentTransaction transaction) {
        mActivity.push(transaction);
    }

    /**
     * Function which will pop all the fragments till the first occurence of the class name
     *
     * @param fragmentName name of the Fragment
     */
    @Override
    public void popUntilFirstOccurrence(String fragmentName) {
        mActivity.popUntilFirstOccurrence(fragmentName);
    }

    /**
     * Function which will pop all the fragments till the first occurence of the class name
     *
     * @param fragmentName name of the Fragment
     */
    @Override
    public void popUntilLastOccurrence(String fragmentName) {
        mActivity.popUntilLastOccurrence(fragmentName);
    }

    /**
     * function which will clear the backstack of fragments, This also prevent the fragment animations
     *
     * @return boolean return true success else flase
     */
    @Override
    public boolean clearBackstack() {
        return mActivity.clearBackstack();
    }

    /**
     * Function hides the soft keyboard if there is any
     */
    public void hideSoftKeyBoard(){
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Add the listener to the listener map
     *
     * @param type     the type of the event against which the listener to be added
     * @param listener the listener to be invoked on event dispatch {@link HLEventListener}
     */
    @Override
    public void addEventListener(String type, HLEventListener listener) {
        HLEventDispatcher.acquire().addEventListener(type, listener);
    }

    /**
     * Remove the listener for the listener map
     *
     * @param type     the type of the event against which the listener to be removed
     * @param listener the listener to be removed
     */
    @Override
    public void removeEventListener(String type, HLEventListener listener) {
        HLEventDispatcher.acquire().removeEventListener(type, listener);
    }

    /**
     * Check the listener already registered or not
     *
     * @param type     type of the event against the existence to be checked
     * @param listener the listener to be mapped
     * @return returns true if mapped else false
     */
    @Override
    public boolean hasEventListener(String type, HLEventListener listener) {
        return HLEventDispatcher.acquire().hasEventListener(type, listener);
    }

    /**
     * The method notify the propper event listeners {@link HLEventListener#onEvent(HLEvent)}
     *
     * @param event to be propagated to the listener
     */
    @Override
    public void dispatchEvent(HLEvent event) {
        HLEventDispatcher.acquire().dispatchEvent(event);
    }

    /**
     * Function destroy the dispatcher
     */
    @Override
    public void dumb() {

    }

    /**
     * Function to get the menu layout
     * @return the id of the layout
     */
    protected abstract int getMenuLayout();

    /**
     * Fuction to get the menu items that are disabled in that screen
     * @return the ids of the items to be disabled
     */
    protected abstract int[] getDisabledMenuItems();

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.  For this method
     * to be called, you must have first called {@link #setHasOptionsMenu}.  See
     * {@link Activity#onCreateOptionsMenu(Menu) Activity.onCreateOptionsMenu}
     * for more information.
     *
     * @param menu     The options menu in which you place your items.
     * @param inflater
     * @see #setHasOptionsMenu
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(getMenuLayout(), menu);
    }

    /**
     * Prepare the Screen's standard options menu to be displayed.  This is
     * called right before the menu is shown, every time it is shown.  You can
     * use this method to efficiently enable/disable items or otherwise
     * dynamically modify the contents.  See
     * {@link Activity#onPrepareOptionsMenu(Menu) Activity.onPrepareOptionsMenu}
     * for more information.
     *
     * @param menu The options menu as last shown or first initialized by
     *             onCreateOptionsMenu().
     * @see #setHasOptionsMenu
     * @see #onCreateOptionsMenu
     */
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        getActivity().supportInvalidateOptionsMenu();
        for(int i:getDisabledMenuItems()){
            MenuItem item=menu.findItem(i);
            item.setVisible(false);
        }
    }

    /**
     * function returns the fragment at the position provided
     *
     * @param position index at which the fragment to be returned
     * @return {@link HLCoreFragment}
     */
    @Override
    public HLCoreFragment getFragmentAt(int position) {
        return mActivity.getFragmentAt(position);
    }
}
