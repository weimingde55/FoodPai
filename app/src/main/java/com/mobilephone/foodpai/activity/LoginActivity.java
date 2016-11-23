package com.mobilephone.foodpai.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.bean.UserBean;
import com.mobilephone.foodpai.util.DaoBmobUtil;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity-test";
    @Bind(R.id.etUsername)
    EditText etUsername;
    @Bind(R.id.tilUsername)
    TextInputLayout tilUsername;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.tilPassword)
    TextInputLayout tilPassword;
    @Bind(R.id.btnLogin)
    Button btnLogin;
    @Bind(R.id.rlLoginContent)
    RelativeLayout rlLoginContent;
    @Bind(R.id.tvUnknowPassword)
    TextView tvUnknowPassword;
    @Bind(R.id.tvRegister)
    TextView tvRegister;
    @Bind(R.id.ivQQLogin)
    ImageView ivQQLogin;
    @Bind(R.id.ivSinaLogin)
    ImageView ivSinaLogin;
    @Bind(R.id.ivWeXinLogin)
    ImageView ivWeXinLogin;
    @Bind(R.id.etAgainPassword)
    EditText etAgainPassword;
    @Bind(R.id.tilAgainPassword)
    TextInputLayout tilAgainPassword;
    @Bind(R.id.ivBack)
    ImageView ivBack;
    private ProgressDialog registerDialog;
    private String[] strings;
    private String snsTypeQQ = BmobUser.BmobThirdUserAuth.SNS_TYPE_QQ;
    private String snsTypeWeixin = BmobUser.BmobThirdUserAuth.SNS_TYPE_WEIXIN;
    private String snsTypeWeibo = BmobUser.BmobThirdUserAuth.SNS_TYPE_WEIBO;

    private static final int SSOLOGIN_QQ_OK = 11;
    private static final int SSOLOGIN_WEIXIN_OK = 22;
    private static final int SSOLOGIN_WEIBO_OK = 33;
    private static final int THRID_LOGIN_OK = 45;
    private static final int SET_DEFAULT_INFO_OK = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initClickListener();

        //
        List<String> stringList = new ArrayList<>();
        File dirFile = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "flut");
        if (dirFile.isDirectory()) {
            for (File file : dirFile.listFiles()) {
                String absolutePath = file.getAbsolutePath();
                if (absolutePath.endsWith(".jpg") || absolutePath.endsWith(".png"))
                    stringList.add(absolutePath);
            }
        }
        strings = new String[stringList.size()];
        for (int i = 0; i < stringList.size(); i++) {
            strings[i] = stringList.get(i);
        }

        //登录输入校验
        initCheck();
    }

    private void initCheck() {
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().matches("^[a-zA-z][a-zA-Z0-9_]{2,9}$")) {
                    tilUsername.setErrorEnabled(true);
                    tilUsername.setError("仅以英文字母开头长度不小于3位");
                    btnLogin.setClickable(false);
                    btnLogin.setTextColor(getResources().getColor(R.color.grayII));
                } else {
                    tilUsername.setErrorEnabled(false);
                    btnLogin.setClickable(true);
                    btnLogin.setTextColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //密码
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().matches("^[a-zA-z][a-zA-Z0-9_]{2,9}$")) {
                    tilPassword.setErrorEnabled(true);
                    tilPassword.setError("仅以英文字母开头长度不小于3位");
                    btnLogin.setClickable(false);
                    btnLogin.setTextColor(getResources().getColor(R.color.grayII));
                } else {
                    tilPassword.setErrorEnabled(false);
                    btnLogin.setClickable(true);
                    btnLogin.setTextColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //确认密码
        etAgainPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(etPassword.getText().toString())) {
                    tilAgainPassword.setErrorEnabled(true);
                    tilAgainPassword.setError("输入的密码不一致");
                    btnLogin.setClickable(false);
                    btnLogin.setTextColor(getResources().getColor(R.color.grayII));
                } else {
                    tilAgainPassword.setErrorEnabled(false);
                    btnLogin.setClickable(true);
                    btnLogin.setTextColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initClickListener() {
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUsername.getText().toString().equals("") || etPassword.getText().toString().equals("") || etAgainPassword.getText().toString().equals("")) {
                    if (etUsername.getText().toString().equals("")) {
                        tilUsername.setErrorEnabled(true);
                        tilUsername.setError("用户名不能为空");
                    }
                    if (etPassword.getText().toString().equals("")) {
                        tilPassword.setErrorEnabled(true);
                        tilPassword.setError("密码能为空");
                    }
                    if (etAgainPassword.getText().toString().equals("")) {
                        tilAgainPassword.setErrorEnabled(true);
                        etAgainPassword.setError("密码能为空");
                    }
                } else {
                    login();
                }
            }
        });
        ivQQLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ssoLogin(snsTypeQQ, ShareSDK.getPlatform(QQ.NAME));
            }
        });
        ivWeXinLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ssoLogin(snsTypeWeixin, ShareSDK.getPlatform(Wechat.NAME));
                Log.e(TAG, "onClick: 微信登录");
            }
        });
        ivSinaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ssoLogin(snsTypeWeibo, ShareSDK.getPlatform(SinaWeibo.NAME));
            }
        });
    }

    /**
     * @param snsType  Bmob中登录的类型 只能是三种取值中的一种：weibo、qq、weixin
     * @param platform
     */
    private void ssoLogin(final String snsType, Platform platform) {
        //若平台为空
        if (platform == null) {
//            Log.e(TAG, "ssoLogin: platform空？");
            return;
        }

        //使用SSO授权, //设置false表示使用SSO授权方式
        platform.SSOSetting(false);
        if (platform.isAuthValid()) { //如果用户已经授权使用该平台账号登陆，则拿取用户信息进行显示

            String userId = platform.getDb().getUserId();
            if (userId != null) {
                String userName = platform.getDb().getUserName();
                thridLogin(platform, snsType);
                return;
            }
        } else {
            //用户未授权，则引导用户进行授权
            platform.authorize();
            platform.showUser(null);//获取用户信息
            Log.e(TAG, "ssoLogin: 引导授权");
        }
        //用户授权是否成功回调
        platform.setPlatformActionListener(new PlatformActionListener() {
            /**
             * 授权完成时的回调
             * @param platform 进行授权的平台
             * @param i  代表“Action”的类型，8=平台授权 1=
             * @param hashMap 操作成功返回的具体数据
             */
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                if (i == 8) {
                    Message message = handler.obtainMessage();
                    message.obj = platform;
//                    Log.e(TAG, "onComplete: 引导授权成功");
                    if (snsType.equals(snsTypeQQ)) {
                        message.what = SSOLOGIN_QQ_OK;
                        handler.sendMessage(message);
                    }
                    if (snsType.equals(snsTypeWeixin)) {
                        message.what = SSOLOGIN_WEIXIN_OK;
                        handler.sendMessage(message);
                    }
                    if (snsType.equals(snsTypeWeibo)) {
                        message.what = SSOLOGIN_WEIBO_OK;
                        handler.sendMessage(message);
                    }

                } else {
                    Log.e(TAG, "onComplete: i ==" + i);
                }
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

                Log.e(TAG, "onError: " + i + " / " + throwable.getMessage());
            }

            @Override
            public void onCancel(Platform platform, int i) {

                Log.e(TAG, "onCancel: " + i);
            }
        });

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Platform platform = (Platform) msg.obj;
            switch (msg.what) {
                case SSOLOGIN_QQ_OK:
                    Log.e(TAG, "handleMessage: QQ" + platform);
                    thridLogin(platform, snsTypeQQ);
                    break;
                case SSOLOGIN_WEIXIN_OK:
                    Log.e(TAG, "handleMessage: WEIXIN" + platform);
                    thridLogin(platform, snsTypeWeixin);
                    break;
                case SSOLOGIN_WEIBO_OK:
                    Log.e(TAG, "handleMessage: WEIBO" + platform);
                    thridLogin(platform, snsTypeWeibo);
                    break;
                case THRID_LOGIN_OK:
                    setDefaultUserInfo(platform);

                    break;
                case SET_DEFAULT_INFO_OK:
                    Toast.makeText(LoginActivity.this, "默认密码123456", Toast.LENGTH_LONG).show();
                    finish();
                    break;
            }
        }
    };

    private void setDefaultUserInfo(Platform platform) {
        UserBean currentUser = BmobUser.getCurrentUser(UserBean.class);
        Boolean emailVerified = currentUser.getEmailVerified();
        Boolean phoneNumberVerified = currentUser.getMobilePhoneNumberVerified();

        if (emailVerified == null && phoneNumberVerified == null) {
            currentUser.setPassword("123456");
            currentUser.setUserCover("http://bmob-cdn-7311.b0.upaiyun.com/2016/11/05/09cea82b4c67469992646ff557c71b48.jpg");
            currentUser.setEmailVerified(false);
            currentUser.setMobilePhoneNumberVerified(false);
            currentUser.update(currentUser.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Message message = handler.obtainMessage();
                        message.what = SET_DEFAULT_INFO_OK;
                        handler.sendMessage(message);
                    } else {
                        Log.e(TAG, "done: " + e.getMessage());
                    }
                }
            });
        } else {
            finish();
        }
    }

    private void thridLogin(Platform platform, String snsType) {
        registerDialog = ProgressDialog.show(this, null, "登录中...");

        String accessToken = platform.getDb().getToken();
        String expiresIn = String.valueOf(platform.getDb().getExpiresIn());
        String userId = platform.getDb().getUserId();

        BmobUser.BmobThirdUserAuth thirdUserAuth = new BmobUser.BmobThirdUserAuth(snsType, accessToken, expiresIn, userId);
        BmobUser.loginWithAuthData(thirdUserAuth, new LogInListener<JSONObject>() {
            @Override
            public void done(JSONObject jsonObject, BmobException e) {
                if (e == null) {
                    Log.e(TAG, "done: 第三方登录成功");
                    registerDialog.dismiss();
                    Message message = handler.obtainMessage();
                    message.what = THRID_LOGIN_OK;
                    handler.sendMessage(message);
//                    finish();
                } else {
                    Log.e(TAG, "done: 第三方登录失败" + e.getMessage());
                    registerDialog.dismiss();
                }
            }
        });

    }

    private void login() {
        registerDialog = ProgressDialog.show(LoginActivity.this, null, "登录中");
        UserBean userBean = new UserBean();
        userBean.setUsername(etUsername.getText().toString());
        userBean.setPassword(etPassword.getText().toString());

        DaoBmobUtil.getInstance().onLogin(userBean, new DaoBmobUtil.OnDaoLogin() {
            @Override
            public void done(UserBean userBean, BmobException e) {
                if (e == null) {
                    registerDialog.dismiss();
                    finish();
                } else {
                    Log.e(TAG, "done: " + e.getMessage());
                    clearErrorInfo();
                    registerDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "错误" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void clearErrorInfo() {
        etPassword.setText("");
        etAgainPassword.setText("");
    }

}
