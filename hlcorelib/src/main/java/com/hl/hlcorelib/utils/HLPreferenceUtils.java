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

package com.hl.hlcorelib.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.hl.hlcorelib.HLCoreLib;
import com.hl.hlcorelib.HLPrimitiveGetSetInterface;

import java.util.Set;

/**
 * Created by hl0204 on 27/7/15.
 */
public class HLPreferenceUtils implements HLPrimitiveGetSetInterface {

    /**
     * Names under which the preferences belongs to the corelib will be
     * saved
     */
    private static final String HL_CORELIB_PREFERENCE = "HL_CORELIB_PREFERENCE";

    /**
     * Holds the singleton instance of the class
     */
    private static HLPreferenceUtils mInstance;

    /**
     * Getter function for mInstance
     *
     * @return mInstance of type HLPreferenceUtils
     */
    public static HLPreferenceUtils obtain(){
        mInstance = (mInstance == null) ? new HLPreferenceUtils() : mInstance;
        return mInstance;
    }

    /**
     * return the Editor object against which editing will be done
     * @return SharedPreferences.Editor
     */
    public static SharedPreferences.Editor obtainEditor(){
        return  HLCoreLib.getAppContext().
                getSharedPreferences(HL_CORELIB_PREFERENCE, Context.MODE_PRIVATE).edit();
    }

    /**
     * Return the preferences with name
     * @return of type SharedPreferences
     */
    public static SharedPreferences obtainPreferences(){
        return HLCoreLib.getAppContext().getSharedPreferences(HL_CORELIB_PREFERENCE, Context.MODE_PRIVATE);
    }

    /**
     * Function save the set against the key provided
     *
     * @param key   of type String, The key against which the value should be saved
     * @param set of type Set<String>, The set which needs to be saved against key
     */
    private boolean put(String key, Set<String> set){
        return obtainEditor().putStringSet(key, set)
                .commit();
    }

    /**
     * function get the Set<String> from the preferences against the key provided
     *
     * @param key of type String, value to be obtained against the key
     * @return of type Set<String> return default value as null
     */
    private Set<String> getStringSet(String key){
        return obtainPreferences().getStringSet(key, null);
    }

    /**
     * Function save the value against the key provided
     *
     * @param key   of type String, The key against which the value should be saved
     * @param value of type String, The value which needs to be saved against key
     */
    @Override
    public boolean put(String key, String value) {
        
        return obtainEditor().putString(key, value)
                .commit();
    }

    /**
     * Function save the value against the key provided
     *
     * @param key   of type String, The key against which the value should be saved
     * @param value of type float, The value which needs to be saved against key
     */
    @Override
    public boolean put(String key, float value) {
        return obtainEditor().putFloat(key, value)
                .commit();
    }

    /**
     * Function save the value against the key provided
     *
     * @param key   of type String, The key against which the value should be saved
     * @param value of type int, The value which needs to be saved against key
     */
    @Override
    public boolean put(String key, int value) {
        return obtainEditor().putInt(key, value)
                .commit();
    }

    /**
     * Function save the value against the key provided
     *
     * @param key   of type String, The key against which the value should be saved
     * @param value of type boolean, The value which needs to be saved against key
     */
    @Override
    public boolean put(String key, boolean value) {
        return obtainEditor().putBoolean(key, value)
                .commit();
    }

    /**
     * Function return the value which is saved against the key
     *
     * @param key of type String
     * @return of type int
     */
    @Override
    public int getInteger(String key) {
        return obtainPreferences().getInt(key, Integer.MAX_VALUE);
    }

    /**
     * Function return the value which is saved against the key
     *
     * @param key of type String
     * @return of type String
     */
    @Override
    public String getString(String key) {
        return obtainPreferences().getString(key, "");
    }

    /**
     * Function return the value which is saved against the key
     *
     * @param key of type String
     * @return of type float
     */
    @Override
    public float getFloat(String key) {
        return obtainPreferences().getFloat(key, Float.MAX_VALUE);
    }

    /**
     * Function return the value which is saved against the key
     *
     * @param key of type String
     * @return of type boolean
     */
    @Override
    public boolean getBoolean(String key) {
        return obtainPreferences().getBoolean(key, Boolean.FALSE);
    }
}
