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
package com.hl.hlcorelib.mvp;


public class HLCorePresenter<T> {

    /**
     * The enclosing Activity or Fragment of the presenter
     */
    private T t;

    /**
     * Constructor function
     * @return return an instance of HLCorePresenter
     */
    public HLCorePresenter(){
    }

    /**
     * This will set the view for the presenter further calls the publish method
     * @param t
     */
    public void onTakeView(T t){
        this.t = t;
        publish();
    }

    /**
     * Responsible for providing the content to the view, the child classes should override these methods to
     * listen for the data changes
     */
    protected void publish(){
    }

    /**
     * Return the enclosing view(Activity/Fragment) associated with the Presenter
     * @return return the view associated with the presenter
     */
    public T getView(){
        return t;
    }

}
