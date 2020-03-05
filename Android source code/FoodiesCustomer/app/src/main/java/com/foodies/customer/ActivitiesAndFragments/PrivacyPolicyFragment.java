package com.foodies.customer.ActivitiesAndFragments;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.foodies.customer.Constants.AllConstants;
import com.foodies.customer.Constants.Functions;
import com.foodies.customer.R;
import com.foodies.customer.Utils.FontHelper;
import com.foodies.customer.Utils.RelateToFragment_OnBack.RootFragment;


/**
 * Created by Dinosoftlabs on 10/18/2019.
 */

public class PrivacyPolicyFragment extends RootFragment {


    WebView mWebview;
    ImageView close_icon;
    TextView rider_jobs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.rider_weekly_earning_fragment, container, false);
        FrameLayout frameLayout = v.findViewById(R.id.weekly_earning_main_container);
        FontHelper.applyFont(getContext(),frameLayout, AllConstants.verdana);

        init(v);

        return v;
    }

    public void init(View v){

        rider_jobs = v.findViewById(R.id.rider_jobs);
        rider_jobs.setText(R.string.privacy_policy);

         callWebView(v);

        close_icon= v.findViewById(R.id.close_btn);
        close_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getActivity().onBackPressed();

                  }
        });

    }


    public void callWebView(View v){

         mWebview = v.findViewById(R.id.web_view);
        mWebview.getSettings().setJavaScriptEnabled(true);

        mWebview.getSettings().setLoadWithOverviewMode(true);
        mWebview.getSettings().setUseWideViewPort(true);
        mWebview.getSettings().setBuiltInZoomControls(true);


        mWebview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Toast.makeText(getContext(), rerr.getDescription(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                Functions.Show_loader(getContext(),false,false);

            }


            @Override
            public void onPageFinished(WebView view, String url) {
              Functions.cancel_loader();

                String webUrl = mWebview.getUrl();

            }



    });
        mWebview.loadUrl("https://foodies.com/customer/privacy.php?device=app");}
}
