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

package com.hl.hlcorelib.utils;

import android.os.Bundle;

import com.hl.hlcorelib.mvp.presenters.HLCoreFragment;

/**
 * Created by HL0204 on 09-07-2015.
 */
public class HLFragmentUtils {

    /**
     * Switch the Fragment enter and exit animation
     */
    public static boolean ALLOW_FRAGMENT_ANIMATION = true;

    public static final int FRAGMENT_NO_ANIMATION = -1;

    /**
     * Class which will be used to create a fragment transaction
     */
    public static class HLFragmentTransaction {
        /**
         * Holds the class for the fragment to be pushed
         */
        public Class mFragmentClass;
        /**
         * Prameter to be passed to the newly created Fragment
         */
        public Bundle mParameters;

        /**
         * Decides it is root or not
         */
        public boolean isRoot;

        /**
         * Holds the id of the FrameLayout
         */
        public int mFrameId;

        /**
         * Indicates the animation to be performed when the fragment attached to the Window
         */
        public int mInAnimation = FRAGMENT_NO_ANIMATION;

        /**
         * Indicates the animation to be performed when the fragment de-attached from the Window
         */
        public int mOutAnimation = FRAGMENT_NO_ANIMATION;

        /**
         * Function which create the Fragment and assign the arguments
         * @return HLCoreFragment return the created Fragment
         */
        public HLCoreFragment compile(){
            HLCoreFragment fragment = null;
            try {
                fragment = (HLCoreFragment)mFragmentClass.newInstance();
                fragment.setArguments(mParameters);
                return fragment;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
