package com.mobilephone.foodpai.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import android.widget.Toast;

import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.bean.UserBean;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity-test";
    @Bind(R.id.rlUserInfo)
    RelativeLayout rlUserInfo;
    @Bind(R.id.etUsername)
    EditText etUsername;
    @Bind(R.id.tilUsername)
    TextInputLayout tilUsername;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.tilPassword)
    TextInputLayout tilPassword;
    @Bind(R.id.etAgainPassword)
    EditText etAgainPassword;
    @Bind(R.id.tilAgainPassword)
    TextInputLayout tilAgainPassword;
    @Bind(R.id.etEmail)
    EditText etEmail;
    @Bind(R.id.tilEmail)
    TextInputLayout tilEmail;
    @Bind(R.id.btnClearInfo)
    Button btnClearInfo;
    @Bind(R.id.btnOkRegister)
    Button btnOkRegister;
    @Bind(R.id.ivBack)
    ImageView ivBack;
    private Toast registerToastOK, registerToastERROR;
    private ProgressDialog registerDialog;
    private UserBean userBean;
    private boolean ssoLoginSucceed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        //点击事件
        initClick();

        //以下是注册校验的业务逻辑，先这样写
        initCheck();


    }

    private void initCheck() {
        //用户名
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().matches("^[a-zA-z][a-zA-Z0-9_]{2,9}$")) {
                    tilUsername.setErrorEnabled(true);
                    tilUsername.setError("仅以英文字母开头长度不小于3位");
                    btnOkRegister.setClickable(false);
                    btnOkRegister.setTextColor(getResources().getColor(R.color.grayII));
                } else {
                    tilUsername.setErrorEnabled(false);
                    btnOkRegister.setClickable(true);
                    btnOkRegister.setTextColor(getResources().getColor(R.color.white));
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
                    btnOkRegister.setClickable(false);
                    btnOkRegister.setTextColor(getResources().getColor(R.color.grayII));
                } else {
                    tilPassword.setErrorEnabled(false);
                    btnOkRegister.setClickable(true);
                    btnOkRegister.setTextColor(getResources().getColor(R.color.white));
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
                    btnOkRegister.setClickable(false);
                    btnOkRegister.setTextColor(getResources().getColor(R.color.grayII));
                } else {
                    tilAgainPassword.setErrorEnabled(false);
                    btnOkRegister.setClickable(true);
                    btnOkRegister.setTextColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //校验邮箱
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!etEmail.getText().toString().matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$") || s.toString().length() < 0) {
                    tilEmail.setErrorEnabled(true);
                    tilEmail.setError("请输入正确的邮箱格式");
                    btnOkRegister.setClickable(false);
                    btnOkRegister.setTextColor(getResources().getColor(R.color.grayII));
                } else {
                    tilEmail.setErrorEnabled(false);
                    btnOkRegister.setClickable(true);
                    btnOkRegister.setTextColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initClick() {

        //回退
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //注册按钮
        btnOkRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (etUsername.getText().toString().equals("") || etPassword.getText().toString().equals("") || etEmail.getText().toString().equals("")) {
                    if (etUsername.getText().toString().equals("")) {
                        tilUsername.setErrorEnabled(true);
                        tilUsername.setError("用户名不能为空");
                    }
                    if (etPassword.getText().toString().equals("")) {
                        tilPassword.setErrorEnabled(true);
                        tilPassword.setError("密码不能为空");
                    }
                    if (etAgainPassword.getText().toString().equals("")) {
                        tilAgainPassword.setErrorEnabled(true);
                        etAgainPassword.setError("密码不能为空");
                    }
                    if (etEmail.getText().toString().equals("")) {
                        tilEmail.setErrorEnabled(true);
                        tilEmail.setError("邮箱不能为空");
                    }
                } else {
                    if (registerToastOK == null)
                        registerToastOK = Toast.makeText(RegisterActivity.this, "注册", Toast.LENGTH_SHORT);
                    registerToastOK.show();
                    register();
                }
                Log.e(TAG, "onClick: " + etUsername.getText().toString() + etPassword.getText().toString());

            }
        });

    }


    private void register() {
        registerDialog = ProgressDialog.show(this, null, "注册中");
        userBean = new UserBean();
        userBean.setUsername(etUsername.getText().toString());
        userBean.setPassword(etPassword.getText().toString());
        userBean.setUserCover("http://bmob-cdn-7311.b0.upaiyun.com/2016/11/05/c25200c646274ee7af7b8cc3fbb7d913.jpg");
        userBean.setEmail(etEmail.getText().toString());
        userBean.setEmailVerified(false);
        userBean.setMobilePhoneNumberVerified(false);
        userBean.signUp(new SaveListener<UserBean>() {
            @Override
            public void done(UserBean userBean, BmobException e) {
                if (e == null) {
                    Message message = handler.obtainMessage();
                    message.what = REGISTEROK;
                    handler.sendMessage(message);
                    registerDialog.dismiss();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "done: " + e.getMessage());
                    registerDialog.dismiss();
                }
            }
        });

    }

    private static final int REGISTEROK = 33;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REGISTEROK:
                    userBean.login(new SaveListener<UserBean>() {
                        @Override
                        public void done(UserBean userBean, BmobException e) {
                            finish();
                            Log.e(TAG, "done: " + userBean.getObjectId());
                        }
                    });
                    break;
            }
        }
    };

    public void updateUserCover(String url) {
        UserBean currentUser = BmobUser.getCurrentUser(UserBean.class);

    }

}
