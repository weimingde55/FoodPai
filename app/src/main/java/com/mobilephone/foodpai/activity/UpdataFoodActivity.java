package com.mobilephone.foodpai.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.mobilephone.foodpai.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdataFoodActivity extends AppCompatActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivI)
    ImageView ivI;
    @BindView(R.id.rlUpload)
    RelativeLayout rlUpload;
    @BindView(R.id.ivII)
    ImageView ivII;
    @BindView(R.id.rlWrite)
    RelativeLayout rlWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_food);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ivBack)
    public void onIvBackClick(View view) {
        finish();
    }
}
