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

package com.hl.hlcorelib.mvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hl0204 on 28/7/15.
 */
public interface HLView {



    /**
     * Create the view from the id provided
     * @param inflater inflater using which the view shold be inflated
     * @param parent to which the view to be attached
     */
    public void init(LayoutInflater inflater, ViewGroup parent);

    /**
     * Return the enclosing view
     * @return return the enclosing view
     */
    public View getView();

    /**
     * To handle the back press
     * @return false if not handled true if handled
     */
    public boolean onBackPreseed();

    /**
     * Function which will be called {@link android.app.Activity#onSaveInstanceState(Bundle)}
     * or {@link android.support.v4.app.Fragment#onSaveInstanceState(Bundle)}
     *
     * @param savedInstanceState the state to save the contents
     */
    public void onSavedInstanceState(Bundle savedInstanceState);

    /**
     * Function which will be triggered when {@link android.app.Activity#onRestoreInstanceState(Bundle)}
     * or {@link android.support.v4.app.Fragment#onViewStateRestored(Bundle)}
     * @param savedInstanceState the state which saved on {HLView#onSavedInstanceState}
     */
    public void onRecreateInstanceState(Bundle savedInstanceState);
}
