package com.mobilephone.foodpai.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.bean.EatHomeFoodBean;
import com.mobilephone.foodpai.widget.MyCircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class EatHomeImageActivity extends AppCompatActivity {
    private static final String TAG = "EatHomeImageActivity-test";
    @Bind(R.id.ivGoodCover)
    ImageView ivGoodCover;
    @Bind(R.id.rlGood)
    RelativeLayout rlGood;
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.ivEatFoodShare)
    ImageView ivEatFoodShare;
    @Bind(R.id.rlBar)
    RelativeLayout rlBar;
    @Bind(R.id.ivUserCover)
    MyCircleImageView ivUserCover;
    @Bind(R.id.tvUserName)
    TextView tvUserName;
    @Bind(R.id.upDataTime)
    TextView upDataTime;
    @Bind(R.id.rlTop)
    RelativeLayout rlTop;
    @Bind(R.id.ivFoodImage)
    ImageView ivFoodImage;
    @Bind(R.id.tvEatFoodContent)
    TextView tvEatFoodContent;
    @Bind(R.id.svMainContent)
    ScrollView svMainContent;
    private EatHomeFoodBean.FeedsBean feedsBean;
    private String url;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat_home_image);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        Log.e(TAG, "onCreate: " + feedsBean.getTitle());
        initClickListener();
        initData();

    }

    private void initData() {
        url = feedsBean.getCard_image();
        title = feedsBean.getTitle();
        String description = feedsBean.getDescription();
        String userCoverUrl = feedsBean.getPublisher_avatar();
        int item_id = feedsBean.getItem_id();
        Log.e(TAG, "initData: item_id = " + item_id);

        Glide.with(this)
                .load(url)
                .skipMemoryCache(true)
                .into(ivFoodImage);
        Glide.with(this)
                .load(userCoverUrl)
                .skipMemoryCache(true)
                .into(ivUserCover);
        tvEatFoodContent.setText(description);
        tvUserName.setText(title);


    }

    boolean isGood=false;
    private void initClickListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivEatFoodShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
        rlGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGood==false){
                    ivGoodCover.setImageResource(R.mipmap.good_yes);
                    isGood=true;
                }else {
                    ivGoodCover.setImageResource(R.mipmap.good_noyes);
                    isGood=false;
                }
            }
        });
    }


    //事件总线，
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 10, sticky = true)
    public void getEatHomeFoodBean(EatHomeFoodBean.FeedsBean feedsBean) {
        this.feedsBean = feedsBean;

        ///搜索历史测试..............
        List<String> stringList = new ArrayList<>();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public void showShare( ) {
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
}
