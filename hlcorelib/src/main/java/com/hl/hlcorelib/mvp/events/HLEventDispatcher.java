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

import android.util.Log;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by hl0204 on 5/8/15.
 */
public class HLEventDispatcher implements HLDispatcher {


    private static final String TAG = HLEventDispatcher.class.getSimpleName();

    /**
     * getter method for mInstance
     *
     * @return Constructs a new instance of {@code HLEventDispatcher}
     */
    public static HLEventDispatcher acquire() {
        mInstance = (mInstance == null) ? new HLEventDispatcher() :
                mInstance;
        return mInstance;
    }



    /**
     * Singleton instance Global to the app
     */
    private static HLEventDispatcher mInstance;

    /**
     * The map in which all the listeners will be saved
     */
    private HashMap<String, CopyOnWriteArrayList<HLEventListener>> mListenersMap;

    /**
     * Target to which the events should be associated
     */
    private HLDispatcher target;


    /**
     * Constructs a new instance of {@code Object}.
     */
    public HLEventDispatcher() {
        super();
        mListenersMap = new HashMap<String, CopyOnWriteArrayList<HLEventListener>>();
    }

    /**
     * Add the listener to the listener map
     *
     * @param type     the type of the event against which the listener to be added
     * @param listener the listener to be invoked on event dispatch {@link HLEventListener}
     */
    @Override
    public void addEventListener(String type, HLEventListener listener) {
        synchronized (mListenersMap) {
            CopyOnWriteArrayList<HLEventListener> listenerList = mListenersMap.get(type);
            if (listenerList == null) {
                listenerList = new CopyOnWriteArrayList<HLEventListener>();
                mListenersMap.put(type, listenerList);
            }
            listenerList.add(listener);
        }
    }

    /**
     * Remove the listener for the listener map
     *
     * @param type     the type of the event against which the lstener to be removed
     * @param listener the listener to be removed
     */
    @Override
    public void removeEventListener(String type, HLEventListener listener) {
        synchronized (mListenersMap) {
            CopyOnWriteArrayList<HLEventListener> listeners = mListenersMap.get(type);
            if (listeners == null)
                return;
            listeners.remove(listener);
            if (listeners.size() == 0) {
                mListenersMap.remove(type);
            }
        }
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
        synchronized (mListenersMap) {
            CopyOnWriteArrayList<HLEventListener> listeners = mListenersMap.get(type);
            if (listeners == null)
                return false;
            return listeners.contains(listener);
        }
    }

    /**
     * The method notify the propper event listeners {@link HLEventListener#onEvent(HLEvent)}
     *
     * @param event to be propagated to the listener
     */
    @Override
    public void dispatchEvent(HLEvent event) {
        if (event == null) {
            Log.e(TAG, "Event object can not be null");
            return;
        }
        String type = event.getType();
        event.setSource(this);
        CopyOnWriteArrayList<HLEventListener> listeners;
        synchronized (mListenersMap) {
            listeners = mListenersMap.get(type);
        }
        if (listeners == null)
            return;
        for (HLEventListener listener : listeners) {
            listener.onEvent(event);
        }
    }

    /**
     * Function destroy the dispatcher
     */
    @Override
    public void dumb() {
        synchronized (mListenersMap) {
            mListenersMap.clear();
        }
        target = null;
    }
}
