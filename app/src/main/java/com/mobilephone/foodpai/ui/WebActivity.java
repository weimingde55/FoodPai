package com.mobilephone.foodpai.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.activity.LoginActivity;
import com.mobilephone.foodpai.bean.UserBean;
import com.mobilephone.foodpai.bean.bmobbean.CollectBean;
import com.mobilephone.foodpai.util.DaoBmobUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class WebActivity extends AppCompatActivity {

    private static final String TAG = "test";
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.llShare)
    LinearLayout llShare;
    @Bind(R.id.tvCollect)
    TextView tvCollect;
    @Bind(R.id.pbProgressBar)
    ProgressBar pbProgressBar;
    @Bind(R.id.llCollect)
    LinearLayout llCollect;
    @Bind(R.id.ivCollect)
    ImageView ivCollect;
    private String url;
    private String title;
    boolean isCollect = false;//判断是否已经收藏
    //存储objectId
    private Map<String, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);

        url = getIntent().getStringExtra("link");
        title = getIntent().getStringExtra("title");
        showHtml(url);
        onQuery();
    }


    private void showHtml(String Url) {
        WebSettings settings = webView.getSettings();
        settings.setAppCacheEnabled(true);//告诉webview启用应用程序缓存api
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);//webview的缓存模式
        settings.setDomStorageEnabled(true);//开启DOM  DOM storage API 功能
        settings.setJavaScriptEnabled(true);//支持js
        settings.setSupportZoom(true);//自动缩放
        settings.setBuiltInZoomControls(true);//显示放大缩小按钮
        settings.setUseWideViewPort(true);//支持双击放大缩小
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

    @OnClick(R.id.llShare)
    public void onShareClick() {
        showShare();
    }


    public void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);
        // 启动分享GUI
        oks.show(this);
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

    /**
     * 点击收藏或删除收藏
     */
    @OnClick(R.id.llCollect)
    public void onCollectClick() {
        UserBean user = UserBean.getCurrentUser(UserBean.class);
        if (user!=null) {
            //增加
            if (isCollect == false) {

                DaoBmobUtil.getInstance().onAdd(title, url,null,null,null, new DaoBmobUtil.OnDaoAdd() {
                    @Override
                    public void onAdd(String s, BmobException e) {
                        if (e == null) {
                            onQuery();
                            isCollect = true;
                        }else {
                            Toast.makeText(WebActivity.this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                //删除
            } else {

                DaoBmobUtil.getInstance().onDelete(map, title, new DaoBmobUtil.OnDelete() {
                    @Override
                    public void onDelete(BmobException e) {
                        if (e == null) {
                            ivCollect.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_news_keep_default));
                            tvCollect.setText("收藏");
                            isCollect = false;
                        }else {
                            Toast.makeText(WebActivity.this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 查询数据库
     * 获得objectId；
     */
    public void onQuery() {
        DaoBmobUtil.getInstance().onQuery(new DaoBmobUtil.OnDaoQuery() {
            @Override
            public void onQuery(List<CollectBean> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        String titlename = list.get(i).getTitle();
                        String objectId = list.get(i).getObjectId();
                        map.put(titlename, objectId); //获得objectId并存储在map中
                        if (titlename.equals(title)) {
                            //设置收藏图标和文字
                            ivCollect.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_news_keep_heighlight));
                            tvCollect.setText("已收藏");
                            isCollect = true;
                        }
                    }
                }else {
                    Toast.makeText(WebActivity.this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
