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


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class HLCoreLoader extends AsyncTaskLoader<HLCoreLoaderResult>{

    /**
     * Constructor function
     * @param context of type Context
     */
    public HLCoreLoader(Context context) {
        super(context);
    }

    /**
     * The operation which will be performed on a separate thread
     * @return HLCoreLoaderResult result of the load
     */
    @Override
    public HLCoreLoaderResult loadInBackground() {
        return null;
    }

    /**
     * Subclasses must implement this to take care of loading their data,
     * as per {@link #startLoading()}.  This is not called by clients directly,
     * but as a result of a call to {@link #startLoading()}.
     */
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
    }

    /**
     * Sends the result of the load to the registered listener. Should only be called by
     * subclasses.
     * <p/>
     * Must be called from the process's main thread.
     *
     * @param data
     *         the result of the load
     */
    @Override
    public void deliverResult(HLCoreLoaderResult data) {
        super.deliverResult(data);
    }

    /**
     * function release the resource passed in the param
     * @param result of type HLCoreLoaderResult
     *               the resource to be released
     */
    private void releaseResource(HLCoreLoaderResult result){
        result.release();
    }
}
