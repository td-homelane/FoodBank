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

package com.hl.hlcorelib;

import android.content.Context;

import com.hl.hlcorelib.orm.HLUser;
import com.hl.hlcorelib.utils.HLAppPropertyReader;

/**
 * Created by hl0204 on 27/7/15.
 */
public class HLCoreLib {

    /**
     * Holds the application context, this will accessible from any where in the app
     */
    private static Context mApplicationContext;

    /**
     * Holds the property loader instance
     */
    private static HLAppPropertyReader mPropertyReader;

    /**
     * Getter function for mApplicationContext
     * @return of type Context, returns the mApplicationContext instance
     */
    public static final Context getAppContext(){
        return mApplicationContext;
    }

    /**
     * Starting point of the cor-lib if this is not called this will leads to exceptions
     *
     * @param mApplicationContext not null of type Context, this will be saved in mApplicationContext
     */
    public static final void init(Context mApplicationContext){
        HLCoreLib.mApplicationContext = mApplicationContext;
    }

    /**
     * function initialize the app configuration
     *
     * @param isDebug which decides from where the property should be read
     */
    public static final void initAppConfig(boolean isDebug){
        mPropertyReader = HLAppPropertyReader.acquire(mApplicationContext, isDebug);
    }

    /**
     * Holds the instance of currently logged in user
     */
    private static HLUser mUser;

    /**
     * Setter function for mUser
     * @param mUser the user object which needs to be saved in mUser
     */
    public static void setmUser(HLUser mUser) {
        HLCoreLib.mUser = mUser;
    }

    /**
     * getter function for user object
     * @return returns the current logged in in user object
     */
    public static HLUser getCurrentUser(){
        return mUser;
    }

    /**
     * Function read the property from the mProperties object
     *
     * @param key the key against which the property should be obtained
     * @return the value obtained aginst the key
     */
    public static final String readProperty(final String key){
        return mPropertyReader.readProperty(key);
    }

}
