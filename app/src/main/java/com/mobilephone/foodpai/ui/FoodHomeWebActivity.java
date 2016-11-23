package com.mobilephone.foodpai.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobilephone.foodpai.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FoodHomeWebActivity extends AppCompatActivity {

    private static final String TAG = "test";
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.pbProgressBar)
    ProgressBar pbProgressBar;
    @Bind(R.id.webView)
    WebView webView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodhomeweb);
        ButterKnife.bind(this);

        url = getIntent().getStringExtra("link");
        showHtml(url);
    }


    private void showHtml(String Url) {
        WebSettings settings = webView.getSettings();
        settings.setAppCacheEnabled(true);//告诉webview启用应用程序缓存api
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);//webview的缓存模式
        settings.setDomStorageEnabled(true);//开启DOM  DOM storage API 功能
        settings.setJavaScriptEnabled(true);//支持js
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//适应屏幕
        settings.setLoadWithOverviewMode(true);
        settings.setDefaultTextEncodingName("utf-8");//设置默认的字符编码
        webView.setWebViewClient(new WebViewClient() {

            /**
             * 网页开始加载
             * @param view
             * @param url
             * @param favicon
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                System.out.println("网页开始加载");
                pbProgressBar.setVisibility(View.VISIBLE);
            }

            /**
             * 网页加载结束
             * @param view
             * @param url
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                System.out.println("网页加载结束");
                pbProgressBar.setVisibility(View.GONE);
            }

            /**
             * WebViewClient很多方法可覆盖，大多数用不到，必须要覆盖shouldOverrideUrlLoading（WebView, String）默认方法。
             * 当有新的URL加载到WebView时，譬如说点击某个链接，该方法会决定下一步行动。如返回true，表示WebView不要处理URL，
             * 如返回false，表示叫WebView，去加载这个URL。
             * @param view
             * @return
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

            /**
             * 网页加载进度改变
             * @param view
             * @param newProgress
             */
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                System.out.println("加载进度：" + newProgress);
                super.onProgressChanged(view, newProgress);
                pbProgressBar.setProgress(newProgress);
            }

            /**
             * 网页title
             * @param view
             * @param title
             */
            @Override
            public void onReceivedTitle(WebView view, String title) {
                System.out.println("网页Title" + title);
                super.onReceivedTitle(view, title);
            }
        });
        webView.loadUrl(Url);
    }

    /**
     * 回退键监听
     */
    @OnClick(R.id.ivBack)
    public void onBackClick() {
        finish();
    }

    /**
     * 设置此方法可以退出时让视频播放声音停止
     */
    @Override
    protected void onPause() {
        super.onPause();
        webView.reload();
    }

}
