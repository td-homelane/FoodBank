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

package com.hl.hlcorelib.orm;

import java.util.List;

/**
 * Created by hl0204 on 14/8/15.
 */
public interface HLQueryInterface {


    /**
     * function to set the not equals selection
     *
     * @param key the key against which the selection to be done
     * @param value the value to be compared
     */
    public void whereNotEquals(String key, String value);

    /**
     * function to set equals selection
     *
     * @param key against which the selection to be done
     * @param value value to be compared
     */
    public void whereEquals(String key, String value);

    /**
     * this compare an array of values against key provided and return the appropriate
     *
     * @param key against which the selection to be done
     * @param values the values to be compared against a key
     */
    public void whereEquals(String key, String[] values);

    /**
     * Funcltion compares a list of HLObject against key provided and return the proper values
     *
     * @param key of type String name of the column against which the selection should be performed
     * @param value of type List<HLObject> the list of objects to checked against the field
     */
    public void whereEquals(String key, List<HLObject> value);


}
