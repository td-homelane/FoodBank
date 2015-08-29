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
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by hl0204 on 26/8/15.
 * The class used to read the configuration properties from the
 * properties file.
 */
public final class HLAppPropertyReader {


    /**
     * Holds the single ton object for the class
     */
    private static HLAppPropertyReader mInstance;

    /**
     * Holds the reference to the property object
     */
    private Properties mProperties;

    /**
     * Getter function for mInstance
     * @return the created HLAppPropertyReader instance
     */
    public static HLAppPropertyReader acquire(final Context mContext, boolean isDebug){
        mInstance = (mInstance == null) ? new HLAppPropertyReader(mContext, isDebug) :
                mInstance;
        return mInstance;
    }

    /**
     * Constructs a new instance of {@code Object}.
     *
     * @param mContext used to fetch the config file
     */
    private HLAppPropertyReader(final Context mContext, final boolean isDebug){
        mProperties = new Properties();
        try {
            final String file = (isDebug) ? "appconfig_debug.properties" : "appconfig_production.properties";
            InputStreamReader stream = new InputStreamReader(mContext.getResources().
                    getAssets().open(file));
            mProperties.load(stream);
        }catch (IOException e){
            throw new Error("Make sure you have both properties file present under assets " +
                    "folder[appconfig_debug.properties and appconfig_production.properties]");
        }
    }

    /**
     * Function read the property from the mProperties object
     *
     * @param key the key against which the property should be obtained
     * @return the value obtained aginst the key
     */
    public final String readProperty(final String key){
        return mProperties.getProperty(key);
    }
}
