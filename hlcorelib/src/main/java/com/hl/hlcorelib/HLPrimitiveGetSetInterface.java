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

package com.hl.hlcorelib;

/**
 * Created by hl0204 on 27/7/15.
 */
public interface HLPrimitiveGetSetInterface {


    /**
     * Function save the value against the key provided
     * @param key of type String, The key against which the value should be saved
     * @param value of type String, The value which needs to be saved against key
     */
    public boolean put(String key, String value);

    /**
     * Function save the value against the key provided
     * @param key of type String, The key against which the value should be saved
     * @param value of type float, The value which needs to be saved against key
     */
    public boolean put(String key, float value);
    /**
     * Function save the value against the key provided
     * @param key of type String, The key against which the value should be saved
     * @param value of type int, The value which needs to be saved against key
     */
    public boolean put(String key, int value);

    /**
     * Function save the value against the key provided
     * @param key of type String, The key against which the value should be saved
     * @param value of type boolean, The value which needs to be saved against key
     */
    public boolean put(String key, boolean value);


    /**
     * Function return the value which is saved against the key
     * @param key of type String
     * @return of type int
     */
    public int getInteger(String key);

    /**
     * Function return the value which is saved against the key
     * @param key of type String
     * @return of type String
     */
    public String getString(String key);

    /**
     * Function return the value which is saved against the key
     * @param key of type String
     * @return of type float
     */
    public float getFloat(String key);

    /**
     * Function return the value which is saved against the key
     * @param key of type String
     * @return of type boolean
     */
    public boolean getBoolean(String key);

}
