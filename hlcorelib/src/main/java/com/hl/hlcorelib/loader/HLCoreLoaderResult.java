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

package com.hl.hlcorelib.loader;

import com.hl.hlcorelib.orm.HLObject;

import java.util.ArrayList;

/**
 * Created by rajeshcp on 10/07/15.
 */
public class HLCoreLoaderResult {

    /**
     * getter function for mError
     * @return mError of type HLException
     */
    public HLException getmError() {
        return mError;
    }

    /**
     * getter function for mResult
     * @return mResult of type ArrayList<HLObject>
     */
    public ArrayList<HLObject> getmResult() {
        return mResult;
    }

    /**
     * Holds the instance of error, this will be set if the back ground operation
     * fail
     */
    private HLException mError;

    /**
     * Holds the list of items which needs to be delivered to the HLPresenter
     */
    private ArrayList<HLObject> mResult;

    /**
     * Function which release all the resources all the objects
     *
     */
    public void release(){
        if(mResult != null){
            mResult.clear();
            mResult = null;
        }
        mError = null;
    }


    /**
     * Constructor function
     * @param mResult list obtained from loading
     * @param mError null if no error else holds the error info
     */
    public HLCoreLoaderResult(final ArrayList<HLObject> mResult, final HLException mError) {
    }
}
