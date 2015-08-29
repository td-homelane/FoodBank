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


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.hl.hlcorelib.HLBackstackInterface;
import com.hl.hlcorelib.HLCoreLib;
import com.hl.hlcorelib.R;
import com.hl.hlcorelib.mvp.HLView;
import com.hl.hlcorelib.mvp.events.HLDispatcher;
import com.hl.hlcorelib.mvp.events.HLEvent;
import com.hl.hlcorelib.mvp.events.HLEventDispatcher;
import com.hl.hlcorelib.mvp.events.HLEventListener;
import com.hl.hlcorelib.orm.HLConstants;
import com.hl.hlcorelib.orm.HLUser;
import com.hl.hlcorelib.utils.HLFragmentUtils;

/**
 * Created by HL0204 on 09-07-2015.
 */
public abstract class HLCoreActivityPresenter<V extends HLView> extends AppCompatActivity implements
        HLBackstackInterface, HLDispatcher {


    Bundle savedInstanceState;

    /**
     * Holds the HLView instance
     */
    protected V mView;

    /**
     * Constructor function
     * @since 9/7/2015
     */
    public HLCoreActivityPresenter(){
    }

    /**
     * Called when the activity is starting.  This is where most initialization
     * should go: calling {@link #setContentView(int)} to inflate the
     * activity's UI, using {@link #findViewById} to programmatically interact
     * with widgets in the UI, calling
     * cursors for data being displayed, etc.
     * <p/>
     * <p>You can call {@link #finish} from within this function, in
     * which case onDestroy() will be immediately called without any of the rest
     * of the activity lifecycle ({@link #onStart}, {@link #onResume},
     * {@link #onPause}, etc) executing.
     * <p/>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     * @see #onStart
     * @see #onSaveInstanceState
     * @see #onRestoreInstanceState
     * @see #onPostCreate
     */
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        restoreInstanceState(savedInstanceState);
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.color.white);
        this.savedInstanceState = savedInstanceState;
        try {
            mView = getVuClass().newInstance();
            mView.init(getLayoutInflater(), null);
            setContentView(mView.getView());
            onBindView();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Perform any final cleanup before an activity is destroyed.  This can
     * happen either because the activity is finishing (someone called
     * {@link #finish} on it, or because the system is temporarily destroying
     * this instance of the activity to save space.  You can distinguish
     * between these two scenarios with the {@link #isFinishing} method.
     * <p/>
     * <p><em>Note: do not count on this method being called as a place for
     * saving data! For example, if an activity is editing data in a content
     * provider, those edits should be committed in either {@link #onPause} or
     * {@link #onSaveInstanceState}, not here.</em> This method is usually implemented to
     * free resources like threads that are associated with an activity, so
     * that a destroyed activity does not leave such things around while the
     * rest of its application is still running.  There are situations where
     * the system will simply kill the activity's hosting process without
     * calling this method (or any others) in it, so it should not be used to
     * do things that are intended to remain around after the process goes
     * away.
     * <p/>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @see #onPause
     * @see #onStop
     * @see #finish
     * @see #isFinishing
     */
    @Override
    protected final void onDestroy() {
        onDestroyHLView();
        mView = null;
        super.onDestroy();
    }

    /**
     * Save all appropriate fragment state.
     *
     * @param outState
     */
    @Override
    protected final void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveInstanceState(outState);
    }

    /**
     * This method is called after {@link #onStart} when the activity is
     * being re-initialized from a previously saved state, given here in
     * <var>savedInstanceState</var>.  Most implementations will simply use {@link #onCreate}
     * to restore their state, but it is sometimes convenient to do it here
     * after all of the initialization has been done or to allow subclasses to
     * decide whether to use your default implementation.  The default
     * implementation of this method performs a restore of any view state that
     * had previously been frozen by {@link #onSaveInstanceState}.
     * <p/>
     * <p>This method is called between {@link #onStart} and
     * {@link #onPostCreate}.
     *
     * @param savedInstanceState the data most recently supplied in {@link #onSaveInstanceState}.
     * @see #onCreate
     * @see #onPostCreate
     * @see #onResume
     * @see #onSaveInstanceState
     */
    @Override
    protected final void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restoreInstanceState(savedInstanceState);
    }

    /**
     * Function which will be called {@link android.app.Activity#onSaveInstanceState(Bundle)}
     *
     * @param savedInstanceState the state to save the contents
     */
    protected void saveInstanceState(Bundle savedInstanceState){
        if(HLCoreLib.getCurrentUser() != null){
            savedInstanceState.putParcelable(HLConstants.USER, HLCoreLib.getCurrentUser());
        }
    }

    /**
     * Function which will be triggered when {@link android.app.Activity#onRestoreInstanceState(Bundle)}
     *
     * @param data the state which saved on {Activity#onSavedInstanceState}
     */
    protected void restoreInstanceState(@Nullable Bundle data){
        if(data != null){
            if(data.containsKey(HLConstants.USER)){
                HLCoreLib.setmUser((HLUser)data.getParcelable(HLConstants.USER));
            }
        }
    }

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
    }

    /**
     * Called on when onDestroy fired for the presenter. Prior to destroy the enclosing view
     * and resources can be released
     */
    protected void onDestroyHLView(){
    }

    /**
     * Function which will be used to pop a Fragment from the backstack
     *
     * @return true success otherwise false
     */
    @Override
    public boolean pop() {
        return getSupportFragmentManager().popBackStackImmediate();
    }

    /**
     * Function which push a new Fragment to the backstack
     *
     * @param transaction       HLFragmentTransaction which defines the Fragment
     */
    @Override
    public void push(HLFragmentUtils.HLFragmentTransaction transaction) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(transaction.mInAnimation == HLFragmentUtils.FRAGMENT_NO_ANIMATION ||
                transaction.mOutAnimation == HLFragmentUtils.FRAGMENT_NO_ANIMATION) {
            ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        }else{
            ft.setCustomAnimations(transaction.mInAnimation, transaction.mOutAnimation);
        }
        String tag = "";
        if(transaction.isRoot){
            clearBackstack();
            tag = "0";
        }else {
            tag = getFragmentCount() + "";
        }
        Fragment fragment = transaction.compile();
        ft.replace(transaction.mFrameId, fragment, tag);
        ft.addToBackStack(tag);
        ft.commit();
    }

    /**
     * Function which will pop all the fragments till the first occurrence of the class name
     *
     * @param fragmentName name of the Fragment
     */
    @Override
    public void popUntilFirstOccurrence(String fragmentName) {
        if(fragmentName == null)
            return;
        int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
        String name = null;
        for(index = index; index >= 0; index--){
            HLCoreFragment fragment = getFragmentAt(index);
            final String clsName = fragment.getClass().getName();
            if(fragmentName.equals(clsName)){
                name = index+"";
                break;
            }
        }
        if(name != null) {
            getSupportFragmentManager().popBackStack(name, 0);
        }
    }

    /**
     * Function which will pop all the fragments till the first occurrence of the class name
     *
     * @param fragmentName name of the Fragment
     */
    @Override
    public void popUntilLastOccurrence(String fragmentName) {
        if(fragmentName == null)
            return;
        int length = getSupportFragmentManager().getBackStackEntryCount();
        String name = null;
        for(int index = 0; index < length; index++){
            HLCoreFragment fragment = getFragmentAt(index);
            final String clsName = fragment.getClass().getName();
            if(fragmentName.equals(clsName)){
                name = index+"";
                break;
            }
        }
        if(name != null) {
            getSupportFragmentManager().popBackStack(name, 0);
        }
    }

    /**
     * function which will clear the backstack of fragments, This also prevent the fragment animations
     *
     * @return boolean return true success else flase
     */
    @Override
    public boolean clearBackstack() {
        boolean result = true;
        HLFragmentUtils.ALLOW_FRAGMENT_ANIMATION = false;
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        HLFragmentUtils.ALLOW_FRAGMENT_ANIMATION = true;
        return result;
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        int count = getFragmentCount();
        if(count > 0) {
            HLCoreFragment fragment = getFragmentAt(count - 1);
            if (fragment != null && !fragment.onBackPressed()) {
                if (!pop()) {
                    super.onBackPressed();
                }
                if(count == 1){
                    super.onBackPressed();
                }
            }
        }else{
            super.onBackPressed();
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
        FragmentManager.BackStackEntry backEntry=getSupportFragmentManager().
                getBackStackEntryAt(position);
        String str=backEntry.getName();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(str);
        return (HLCoreFragment)fragment;
    }

    /**
     * Function decides the activity is recreated or not
     * @return true if the activity is recreated
     */
    public boolean isRecreated(){
        return (savedInstanceState != null);
    }

    /**
     * Function return the number of fragments in the back stack
     * @return number of fragments in backstack
     */
    public int getFragmentCount(){
        return getSupportFragmentManager().getBackStackEntryCount();
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
     * @param type     the type of the event against which the lstener to be removed
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
}
