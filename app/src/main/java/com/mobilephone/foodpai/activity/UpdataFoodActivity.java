package com.mobilephone.foodpai.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mobilephone.foodpai.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdataFoodActivity extends AppCompatActivity {

    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.ivI)
    ImageView ivI;
    @Bind(R.id.rlUpload)
    RelativeLayout rlUpload;
    @Bind(R.id.ivII)
    ImageView ivII;
    @Bind(R.id.rlWrite)
    RelativeLayout rlWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_food);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ivBack)
    public void onIvBackClick(View view){
        finish();
    }
}
