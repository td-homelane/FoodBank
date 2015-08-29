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

package com.hl.hlcorelib;

import com.hl.hlcorelib.mvp.presenters.HLCoreFragment;
import com.hl.hlcorelib.utils.HLFragmentUtils;

/**
 * Created by HL0204 on 09-07-2015.
 */
public interface HLBackstackInterface {

    /**
     * Function which will be used to pop a Fragment from the backstack
     * @return true success otherwise false
     */
    public boolean pop();

    /**
     * Function which push a new Fragment to the backstac
     * @param transaction of type HLFragmentTransaction
     */
    public void push(HLFragmentUtils.HLFragmentTransaction transaction);

    /**
     * Function which will pop all the fragments till the first occurrence of the class name
     * @param fragmentName name of the Fragment
     */
    public void popUntilFirstOccurrence(String fragmentName);


    /**
     * Function which will pop all the fragments till the first occurrence of the class name
     * @param fragmentName name of the Fragment
     */
    public void popUntilLastOccurrence(String fragmentName);

    /**
     * function which will clear the backstack of fragments, This also prevent the fragment animations
     * @return boolean return true success else false
     */
    public boolean clearBackstack();

    /**
     * function returns the fragment at the position provided
     * @param position  index at which the fragment to be returned
     * @return {@link HLCoreFragment}
     */
    public HLCoreFragment getFragmentAt(int position);


}
