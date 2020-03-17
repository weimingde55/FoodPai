package com.mobilephone.foodpai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.mobilephone.foodpai.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EatFoodActivity extends AppCompatActivity {


    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.llSearchAnalyze)
    LinearLayout llSearchAnalyze;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat_food);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ivBack)
    public void onIvBackClick() {
        finish();
    }

    @OnClick(R.id.llSearchAnalyze)
    public void onIllSearchAnalyzeClick() {
        startActivity(new Intent(this, SearchActivity.class));
        this.finish();
    }


}
