package com.mobilephone.foodpai.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mobilephone.foodpai.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class SetingActivity extends AppCompatActivity {

    private static final String TAG = "SetingActivity-test";
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivI)
    ImageView ivI;
    @BindView(R.id.tvCache)
    TextView tvCache;
    @BindView(R.id.rlCache)
    RelativeLayout rlCache;
    @BindView(R.id.rlSuggest)
    RelativeLayout rlSuggest;
    @BindView(R.id.rlScore)
    RelativeLayout rlScore;
    @BindView(R.id.rlShare)
    RelativeLayout rlShare;
    private Button btnClearUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seting);
        ButterKnife.bind(this);

        initClearUserClick();

//        getCurrentUser();
    }

    private void getCurrentUser() {
        BmobUser currentUser = BmobUser.getCurrentUser();
        if (currentUser != null) {
            btnClearUser.setVisibility(View.VISIBLE);
        } else {
            btnClearUser.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getCurrentUser();
    }

    private void initClearUserClick() {
        btnClearUser = ((Button) findViewById(R.id.btnClearUser));
        btnClearUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut();
                //撤销qq授权
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                if (qq.isAuthValid()) {
                    qq.removeAccount(true);
                }
                //撤销微信授权
                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                if (wechat.isAuthValid()) {
                    wechat.removeAccount(true);
                }
                //撤销新浪微博授权
                Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                if (weibo.isAuthValid()) {
                    weibo.removeAccount(true);
                }
                getCurrentUser();
                finish();
            }
        });
    }

    @OnClick(R.id.ivBack)
    public void onInBackClick(View view) {
        finish();
    }

}
