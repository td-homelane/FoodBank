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

package com.hl.hlcorelib.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.hl.hlcorelib.orm.HLObject;

import java.util.List;

/**
 * Created by hl0204 on 30/7/15.
 */
public abstract class GridAdapterBase<VH extends ViewHolder> extends ArrayAdapter<HLObject>{

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     */
    public GridAdapterBase(Context context, int resource) {
        super(context, resource);
    }

    /**
     * Constructor
     *
     * @param context            The current context.
     * @param resource           The resource ID for a layout file containing a layout to use when
     *                           instantiating views.
     * @param textViewResourceId The id of the TextView within the layout resource to be populated
     */
    public GridAdapterBase(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public GridAdapterBase(Context context, int resource, HLObject[] objects) {
        super(context, resource, objects);
    }

    /**
     * Constructor
     *
     * @param context            The current context.
     * @param resource           The resource ID for a layout file containing a layout to use when
     *                           instantiating views.
     * @param textViewResourceId The id of the TextView within the layout resource to be populated
     * @param objects            The objects to represent in the ListView.
     */
    public GridAdapterBase(Context context, int resource, int textViewResourceId, HLObject[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public GridAdapterBase(Context context, int resource, List<HLObject> objects) {
        super(context, resource, objects);
    }

    /**
     * Constructor
     *
     * @param context            The current context.
     * @param resource           The resource ID for a layout file containing a layout to use when
     *                           instantiating views.
     * @param textViewResourceId The id of the TextView within the layout resource to be populated
     * @param objects            The objects to represent in the ListView.
     */
    public GridAdapterBase(Context context, int resource, int textViewResourceId, List<HLObject> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    /**
     * {@inheritDoc}
     *
     * @param position
     * @param convertView
     * @param parent
     */
    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        VH holder = null;
        if(convertView == null){
            holder = onCreateViewHolder(parent, getItemViewType(position));
            holder.itemView.setTag(holder);
        }else{
            holder = (VH)convertView.getTag();
        }
        onBindViewHolder(holder, position);
        return holder.itemView;
    }

    /**
     * function creates the ViewHolder, this will be called against few entries since {@link android.widget.GridView} recycles the views
     * @param parent container gainst which the child will be added
     * @param viewType the type of the view this deppends on these two methods {@link #getViewTypeCount()} and
     *                 {@link #getItemViewType(int)}
     * @return returns the created VH
     */
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * function binds the items to the view
     * @param holder the holder holding the view
     * @param position the position in the parent view
     */
    public abstract void onBindViewHolder(VH holder, int position);


}
