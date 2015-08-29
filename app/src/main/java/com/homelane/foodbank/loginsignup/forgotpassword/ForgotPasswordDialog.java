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
package com.homelane.foodbank.loginsignup.forgotpassword;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.hl.hlcorelib.mvp.events.HLCoreEvent;
import com.hl.hlcorelib.mvp.events.HLEventDispatcher;
import com.hl.hlcorelib.mvp.presenters.HLCoreFragmentDialogPresenter;
import com.homelane.foodbank.Constants;
import com.homelane.foodbank.R;

/**
 * Created by hl0395 on 29/8/15.
 */
public class ForgotPasswordDialog extends HLCoreFragmentDialogPresenter<ForgotPasswordDialogView> {


    /**
     * Function which return the enclosing view class, this will be used to
     * create the respective view bind it to the Context
     *
     * @return return the enclosed view class
     */
    @Override
    protected Class<ForgotPasswordDialogView> getVuClass() {
        return ForgotPasswordDialogView.class;
    }

    /**
     * Function which will be called when the view is bind to the presenter,
     * This is useful to set the user interaction listeners to the View
     */
    @Override
    protected void onBindView() {
        super.onBindView();

             mView.mResetPwdBtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     String valid = mView.validateFields();
                     if (valid.length() > 0) {
                         Toast.makeText(getActivity(), valid, Toast.LENGTH_SHORT).show();
                     } else {
                         String email = mView.getEmail();
                         String mobile = mView.getValidPhone();

                         Bundle bundle = new Bundle();
                         bundle.putString(Constants.User.EMAIL, email);
                         bundle.putString(Constants.User.MOBILE, mobile);

                         HLCoreEvent event = new HLCoreEvent(Constants.ON_FORGOT_PWD_EVENT, bundle);
                         HLEventDispatcher.acquire().dispatchEvent(event);

                     }
                 }
             });

        mView.mEmailAddress.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }


    /**
     * Override to build your own custom Dialog container.  This is typically
     * used to show an AlertDialog instead of a generic Dialog; when doing so,
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} does not need
     * to be implemented since the AlertDialog takes care of its own content.
     * <p/>
     * <p>This method will be called after {@link #onCreate(Bundle)} and
     * before {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.  The
     * default implementation simply instantiates and returns a {@link Dialog}
     * class.
     * <p/>
     * <p><em>Note: DialogFragment own the {@link Dialog#setOnCancelListener
     * Dialog.setOnCancelListener} and {@link Dialog#setOnDismissListener
     * Dialog.setOnDismissListener} callbacks.  You must not set them yourself.</em>
     * To find out about these events, override {@link #onCancel(DialogInterface)}
     * and {@link #onDismiss(DialogInterface)}.</p>
     *
     * @param savedInstanceState The last saved instance state of the Fragment,
     *                           or null if this is a freshly created Fragment.
     * @return Return a new Dialog instance to be displayed by the Fragment.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dlg = super.onCreateDialog(savedInstanceState);

        dlg.setTitle(getString(R.string.forgot_password));
        TextView titleTextView = (TextView) dlg.findViewById(android.R.id.title);
        titleTextView.setGravity(Gravity.CENTER);

        titleTextView.setTextColor(getResources().getColor(R.color.black));

        return dlg;

    }

    /**
     * Called on when onDestroy fired for the presenter. Prior to destroy the enclosing view
     * and resources can be released
     */
    @Override
    protected void onDestroyHLView() {
        super.onDestroyHLView();
    }
}
