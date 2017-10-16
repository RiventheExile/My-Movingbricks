package com.my_movingbricks.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.my_movingbricks.R;
import com.my_movingbricks.base.BaseMvpActivity;
import com.my_movingbricks.base.BasePresenter;
import com.my_movingbricks.view.widgets.ProgressWebView;

import butterknife.BindView;


/**
 * @创建者 CSDN_LQR
 * @描述 内置浏览器界面
 */
public class WebViewActivity extends BaseMvpActivity {

    private Intent mIntent;
    private Bundle mExtras;
    private String mUrl;
    private String mTitle;

    private boolean isLoading = false;

    @BindView(R.id.webview)
    public ProgressWebView mWebView;

    @Override
    public void init() {
        //得到url
        try {
            mIntent = getIntent();
            mExtras = mIntent.getExtras();
            if (mExtras == null) {
                finish();
                return;
            }
            mUrl = mExtras.getString("url");
            if (TextUtils.isEmpty(mUrl)) {
                finish();
                return;
            }
            mTitle = mExtras.getString("title");
        } catch (Exception e) {
            e.printStackTrace();
            finish();
            return;
        }
    }

    @Override
    public void initView() {
        //设置webView
        WebSettings settings = mWebView.getSettings();
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptEnabled(true);
        settings.setSavePassword(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setMinimumFontSize(settings.getMinimumLogicalFontSize() + 8);
        settings.setAllowFileAccess(false);
        settings.setTextSize(WebSettings.TextSize.NORMAL);
        mWebView.setVerticalScrollbarOverlay(true);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.loadUrl(mUrl);
        setHeaderTitle(TextUtils.isEmpty(mTitle) ? mWebView.getTitle() : mTitle);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        isLoading = false;

        //如果当前浏览器可以后退，则后退上一个页面
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.removeAllViews();
            try {
                mWebView.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mWebView = null;
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //在自己浏览器中跳转
            mWebView.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            isLoading = true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            isLoading = false;
        }
    }
}
