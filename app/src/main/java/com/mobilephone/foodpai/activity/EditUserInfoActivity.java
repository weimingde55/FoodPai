package com.mobilephone.foodpai.activity;

import android.app.ProgressDialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.bean.UserBean;
import com.mobilephone.foodpai.util.DaoBmobUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class EditUserInfoActivity extends AppCompatActivity {

    private static final String TAG = "EditUserInfoActivity-test";
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.tvCheckUserInfo)
    TextView tvCheckUserInfo;
    @Bind(R.id.tvSavekUserInfo)
    TextView tvSavekUserInfo;

    @Bind(R.id.etNikeName)
    EditText etNikeName;
    @Bind(R.id.etGender)
    EditText etGender;
    @Bind(R.id.etAge)
    EditText etAge;
    @Bind(R.id.etTall)
    EditText etTall;
    @Bind(R.id.etEmail)
    EditText etEmail;
    @Bind(R.id.etPhoneNumber)
    EditText etPhoneNumber;
    @Bind(R.id.rlBar)
    RelativeLayout rlBar;
    @Bind(R.id.tvCheckEmail)
    TextView tvCheckEmail;
    @Bind(R.id.tvUpdateEmail)
    TextView tvUpdateEmail;
    @Bind(R.id.tvCheckPhoneNumber)
    TextView tvCheckPhoneNumber;
    @Bind(R.id.tvUpdatePhoneNumber)
    TextView tvUpdatePhoneNumber;
    private int screenWidth;
    private PopupWindow popupWindow;
    private EditText popupUsername;
    private EditText popupPassword;
    private EditText popupAgainPassword;
    private Button btnCheck;
    private TextInputLayout popupTilAgianPassword;
    private UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        ButterKnife.bind(this);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        initClick();
        getCurrentUserInfo();

    }


    private void getCurrentUserInfo() {
        //获取当前用户信息
        userBean = BmobUser.getCurrentUser(UserBean.class);

        String nikename = userBean.getNikename();
        String gender = userBean.getGender();
        String age = userBean.getAge();
        String tall = userBean.getTall();
        String email = userBean.getEmail();
        String mobilePhoneNumber = userBean.getMobilePhoneNumber();

        Boolean phoneNumberVerified = userBean.getMobilePhoneNumberVerified();
        Boolean emailVerified = userBean.getEmailVerified();

        Log.e(TAG, "getCurrentUserInfo: " + emailVerified + " / " + phoneNumberVerified);

        if (nikename != null) {
            etNikeName.setText(nikename);
        }
        if (gender != null) {
            etGender.setText(gender);
        }
        if (age != null) {
            etAge.setText(age);
        }
        if (tall != null) {
            etTall.setText(tall);
        }
        if (email != null) {
            etEmail.setText(email);
        }
        if (mobilePhoneNumber != null) {
            etPhoneNumber.setText(mobilePhoneNumber);

        }
        if (phoneNumberVerified != null && !phoneNumberVerified) {
            tvCheckPhoneNumber.setVisibility(View.VISIBLE);
            tvUpdatePhoneNumber.setVisibility(View.GONE);
        } else {
            tvCheckPhoneNumber.setVisibility(View.GONE);
            tvUpdatePhoneNumber.setVisibility(View.VISIBLE);
        }
        if (emailVerified != null && !emailVerified) {
            tvCheckEmail.setVisibility(View.VISIBLE);
            tvUpdateEmail.setVisibility(View.GONE);
        } else {
            tvCheckEmail.setVisibility(View.GONE);
            tvUpdateEmail.setVisibility(View.VISIBLE);
        }
    }

    private void initClick() {

        tvCheckUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserBean currentUser = BmobUser.getCurrentUser(UserBean.class);
                String username = currentUser.getUsername();
                showCheckPopup(username);
                Log.e(TAG, "onClick: " + username);

            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //验证邮箱
        tvCheckEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = userBean.getEmail();
                userBean.requestEmailVerify(email, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            tvCheckEmail.setText("发送成功");
                            Log.e(TAG, "done: " + email);
                        } else {
                            tvCheckEmail.setText("发送失败");
                            Log.e(TAG, "done: " + e.getMessage());
                        }
                    }
                });
            }
        });
        //修改邮箱
        tvUpdateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //验证手机号码
        tvCheckPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //修改手机号码
        tvUpdatePhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tvSavekUserInfo.setOnClickListener(new View.OnClickListener() {
            private ProgressDialog updateProgressDialog;

            @Override
            public void onClick(View v) {
                if (etNikeName.getText().toString().equals("")) {
                    etNikeName.setEnabled(true);
                    etNikeName.setError("请填写您的昵称");
                } else if (etGender.getText().toString().equals("")) {
                    etGender.setEnabled(true);
                    etGender.setError("请填写您的性别");
                } else if (etAge.getText().toString().equals("")) {
                    etAge.setEnabled(true);
                    etAge.setError("请填写您的年龄");
                } else if (etTall.getText().toString().equals("")) {
                    etTall.setEnabled(true);
                    etTall.setError("请填写您的身高");
                } else if (etPhoneNumber.getText().toString().equals("") || etPhoneNumber.getText().toString().length() != 11) {
                    etPhoneNumber.setEnabled(true);
                    etPhoneNumber.setError("请填写正确的电话号码");
                } else {
                    updateProgressDialog = ProgressDialog.show(EditUserInfoActivity.this, null, "修改中");
                    final UserBean userBean = new UserBean();
                    userBean.setNikename(etNikeName.getText().toString());
                    userBean.setGender(etGender.getText().toString());
                    userBean.setAge(etAge.getText().toString());
                    userBean.setTall(etTall.getText().toString());
                    userBean.setMobilePhoneNumber(etPhoneNumber.getText().toString());
                    DaoBmobUtil.getInstance().onUpdate(userBean, new DaoBmobUtil.OnUpdate() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(EditUserInfoActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                                updateProgressDialog.dismiss();
                                userBean.logOut();
                                finish();
                            } else {
                                Toast.makeText(EditUserInfoActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "done: " + e.getMessage());
                                updateProgressDialog.dismiss();
                            }

                        }
                    });
                }
            }
        });
    }

    private void showCheckPopup(String username) {

        if (popupWindow == null) {
            final View contentView = LayoutInflater.from(this).inflate(R.layout.check_user_info_popup, null, false);
            popupUsername = (EditText) contentView.findViewById(R.id.etUsername);
            popupPassword = (EditText) contentView.findViewById(R.id.etPassword);
            popupAgainPassword = (EditText) contentView.findViewById(R.id.etAgainPassword);
            popupTilAgianPassword = ((TextInputLayout) contentView.findViewById(R.id.tilAgainPassword));
            btnCheck = ((Button) contentView.findViewById(R.id.btnLogin));
            popupAgainPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!popupPassword.getText().toString().equals(s.toString())) {
                        popupTilAgianPassword.setErrorEnabled(true);
                        popupTilAgianPassword.setError("两次输入的密码不一致");
                        btnCheck.setClickable(false);
                    } else {
                        popupTilAgianPassword.setErrorEnabled(false);
                        btnCheck.setClickable(true);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            btnCheck.setOnClickListener(new View.OnClickListener() {
                private ProgressDialog checkProgressDialog;

                @Override
                public void onClick(View v) {
                    UserBean userBean = new UserBean();
                    userBean.setUsername(popupUsername.getText().toString());
                    userBean.setPassword(popupPassword.getText().toString());
                    checkProgressDialog = ProgressDialog.show(EditUserInfoActivity.this, null, "验证中");
                    DaoBmobUtil.getInstance().onLogin(userBean, new DaoBmobUtil.OnDaoLogin() {
                        @Override
                        public void done(UserBean userBean, BmobException e) {
                            if (e == null) {
                                Toast.makeText(EditUserInfoActivity.this, "验证成功!", Toast.LENGTH_SHORT).show();
                                if (popupWindow.isShowing())
                                    popupWindow.dismiss();
                                tvCheckUserInfo.setVisibility(View.GONE);
                                tvSavekUserInfo.setVisibility(View.VISIBLE);
                                checkProgressDialog.dismiss();
                                setEditTextToEnable();
                            } else {
                                checkProgressDialog.dismiss();
                                Toast.makeText(EditUserInfoActivity.this, "验证失败!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
            popupWindow = new PopupWindow(contentView, screenWidth, ViewGroup.LayoutParams.MATCH_PARENT, true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
        }
        popupUsername.setText(username);
        popupWindow.showAsDropDown(rlBar, 0, 0);

    }

    private void setEditTextToEnable() {

        etNikeName.setEnabled(true);
        etNikeName.setFocusable(true);
        etGender.setEnabled(true);
        etAge.setEnabled(true);
        etTall.setEnabled(true);
        tvCheckEmail.setClickable(true);
        tvCheckEmail.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_tv_check_update_enable));
        tvUpdateEmail.setClickable(true);
        tvUpdateEmail.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_tv_check_update_enable));

        tvCheckPhoneNumber.setClickable(true);
        tvCheckPhoneNumber.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_tv_check_update_enable));
        if (userBean.getEmail() == null)
            etEmail.setEnabled(true);
        if (userBean.getMobilePhoneNumber() == null)
            etPhoneNumber.setEnabled(true);

    }
}
