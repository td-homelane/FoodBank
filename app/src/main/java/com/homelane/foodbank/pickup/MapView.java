package com.homelane.foodbank.pickup;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hl.hlcorelib.mvp.HLView;
import com.homelane.foodbank.R;

/**
 * Created by hl0204 on 30/8/15.
 */
public class MapView implements HLView {

    private View mView;

    /**
     * Function which will be called {@link Activity#onSaveInstanceState(Bundle)}
     * or {@link Fragment#onSaveInstanceState(Bundle)}
     *
     * @param savedInstanceState the state to save the contents
     */
    @Override
    public void onSavedInstanceState(Bundle savedInstanceState) {

    }

    /**
     * Function which will be triggered when {@link Activity#onRestoreInstanceState(Bundle)}
     * or {@link Fragment#onViewStateRestored(Bundle)}
     *
     * @param savedInstanceState the state which saved on {HLView#onSavedInstanceState}
     */
    @Override
    public void onRecreateInstanceState(Bundle savedInstanceState) {

    }

    /**
     * To handle the back press
     *
     * @return false if not handled true if handled
     */
    @Override
    public boolean onBackPreseed() {
        return false;
    }

    /**
     * Create the view from the id provided
     *
     * @param inflater inflater using which the view shold be inflated
     * @param parent   to which the view to be attached
     */
    @Override
    public void init(LayoutInflater inflater, ViewGroup parent) {
        mView = inflater.inflate(R.layout.map_view_layout, parent, false);

    }

    /**
     * Return the enclosing view
     *
     * @return return the enclosing view
     */
    @Override
    public View getView() {
        return mView;
    }

    /**
     * function load the provided url content
     *
     * @param url the url to be loaded
     */
    public void loadContent(final String url){
        final WebView webView = (WebView)mView.findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            /**
             * Notify the host application that a page has finished loading. This method
             * is called only for main frame. When onPageFinished() is called, the
             * rendering picture may not be updated yet. To get the notification for the
             * new Picture, use {@link WebView.PictureListener#onNewPicture}.
             *
             * @param view The WebView that is initiating the callback.
             * @param url  The url of the page.
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mView.findViewById(R.id.progress).setVisibility(View.GONE);
                mView.findViewById(R.id.web_view).setVisibility(View.VISIBLE);
            }

            /**
             * Give the host application a chance to take over the control when a new
             * url is about to be loaded in the current WebView. If WebViewClient is not
             * provided, by default WebView will ask Activity Manager to choose the
             * proper handler for the url. If WebViewClient is provided, return true
             * means the host application handles the url, while return false means the
             * current WebView handles the url.
             * This method is not called for requests using the POST "method".
             *
             * @param view The WebView that is initiating the callback.
             * @param url  The url to be loaded.
             * @return True if the host application wants to leave the current WebView
             * and handle the url itself, otherwise return false.
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }


            /**
             * Notify the host application that a page has started loading. This method
             * is called once for each main frame load so a page with iframes or
             * framesets will call onPageStarted one time for the main frame. This also
             * means that onPageStarted will not be called when the contents of an
             * embedded frame changes, i.e. clicking a link whose target is an iframe.
             *
             * @param view    The WebView that is initiating the callback.
             * @param url     The url to be loaded.
             * @param favicon The favicon for this page if it already exists in the
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mView.findViewById(R.id.progress).setVisibility(View.VISIBLE);
                mView.findViewById(R.id.web_view).setVisibility(View.GONE);
                super.onPageStarted(view, url, favicon);
            }
        });
        webView.loadUrl(url);
    }
}
