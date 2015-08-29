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

package com.hl.hlcorelib.mvp.events;

/**
 * Created by hl0204 on 5/8/15.
 */
public interface HLDispatcher {

    /**
     * Add the listener to the listener map
     *
     * @param type the type of the event against which the listener to be added
     * @param listener the listener to be invoked on event dispatch {@link HLEventListener}
     */
    public void addEventListener(String type, HLEventListener listener);

    /**
     * Remove the listener for the listener map
     *
     * @param type the type of the event against which the lstener to be removed
     * @param listener the listener to be removed
     */
    public void removeEventListener(String type, HLEventListener listener);

    /**
     * Check the listener already registered or not
     *
     * @param type type of the event against the existence to be checked
     * @param listener the listener to be mapped
     * @return returns true if mapped else false
     */
    public boolean hasEventListener(String type, HLEventListener listener);

    /**
     * The method notify the propper event listeners {@link HLEventListener#onEvent(HLEvent)}
     * @param event to be propagated to the listener
     */
    public void dispatchEvent(HLEvent event);

    /**
     * Function destroy the dispatcher
     */
    public void dumb();
}
