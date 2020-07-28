package com.example.dell.firstcry.View.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.dell.firstcry.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CommerceFragment extends Fragment {

    WebView mWebViewCommerce;
    public CommerceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_commerce, container, false);

        mWebViewCommerce = (WebView) v.findViewById(R.id.webViewCommerce);
        mWebViewCommerce.loadUrl("file:///android_asset/commerce.html");

        // Enable Javascript
        WebSettings webSettings = mWebViewCommerce.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        mWebViewCommerce.setWebViewClient(new WebViewClient());

        mWebViewCommerce.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == MotionEvent.ACTION_UP
                        && mWebViewCommerce.canGoBack()) {
                    mWebViewCommerce.goBack();
                    return true;
                }
                return false;
            }

        });
        return  v;
    }
}
