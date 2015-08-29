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

import android.os.Bundle;

/**
 * Created by hl0204 on 5/8/15.
 */
public class HLCoreEvent implements HLEvent {

    public Bundle getmExtra() {
        return mExtra;
    }

    /**
     * Holds the extra values about the event
     */
    private Bundle mExtra;

    /**
     * Constructs a new instance of {@code Object}.
     *
     * @param type the type of th event
     */
    public HLCoreEvent(String type, Bundle mExtra) {
        super();
        mType = type;
        this.mExtra = mExtra;
    }

    /**
     * Holds the type of the event
     */
    private String mType;

    /**
     * Event propagation source
     */
    private Object mSource;

    /**
     * Function return the type of event
     *
     * @return of type {@link String}
     */
    @Override
    public String getType() {
        return mType;
    }

    /**
     * To get the creator or source of the event
     *
     * @return of type {@link Object}
     */
    @Override
    public Object getSource() {
        return mSource;
    }

    /**
     * To set the source of the event propagation
     *
     * @param object the object from which the event is triggered
     */
    @Override
    public void setSource(Object object) {
        this.mSource = object;
    }
}
