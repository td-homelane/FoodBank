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

import com.hl.hlcorelib.orm.HLConstants;
import com.hl.hlcorelib.orm.HLObject;

import java.util.List;

/**
 * Created by rajeshcp on 24/08/15.
 */
public final class HLObjectUtils {

    /**
     *
     * @param key the key against the value should be compared
     * @param value the value against the filtering to be done
     * @param list the list of values against the filtering to be done
     * @return the filtered object
     */
    public static final HLObject getObjectWithKey(String key, String value, List<HLObject> list){
        for(final HLObject object : list){
            if(doesObjectOwnsValue(key, value, object)){
                return object;
            }
        }
        return null;
    }

    /**
     *
     * @param key the key against the value should be compared
     * @param value the value against the filtering to be done
     * @param object the object on the check will be done
     * @return returns true if object holds the value against the key provided
     */
    public static final boolean doesObjectOwnsValue(String key, String value, HLObject object){
        final String oValue = (key.equals(HLConstants._ID)) ? object.getmObjectId() : object.getString(key);
        if(oValue != null && oValue.equals(value)){
            return true;
        }else {
            return false;
        }
    }





}
