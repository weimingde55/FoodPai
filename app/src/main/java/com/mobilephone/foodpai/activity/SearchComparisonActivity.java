package com.mobilephone.foodpai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.adapter.CompareAdapter;
import com.mobilephone.foodpai.adapter.SearchCompeletFoodAdapter;
import com.mobilephone.foodpai.bean.CompeletFoodBean;
import com.mobilephone.foodpai.myview.MylistView;
import com.mobilephone.foodpai.util.HttpUtil;
import com.mobilephone.foodpai.util.JsonUtil;
import com.mobilephone.foodpai.util.SharedPreferencesUtils;
import com.mobilephone.foodpai.util.ThreadUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchComparisonActivity extends AppCompatActivity {

    private static final int GET_COMPELET_DATA = 20;
    private static final String TAG = "test";
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.ivVs)
    ImageView ivVs;
    @Bind(R.id.llRightAdd)
    LinearLayout llRightAdd;
    @Bind(R.id.llLeftAdd)
    LinearLayout llLeftAdd;
    @Bind(R.id.mlvCompelet)
    MylistView mlvCompelet;
    @Bind(R.id.ivRight)
    ImageView ivRight;
    @Bind(R.id.ivLeft)
    ImageView ivLeft;
    boolean aBoolean = false, RL = false;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_COMPELET_DATA:
                    String json = (String) msg.obj;
                    getSharePreferencesData();
                    Log.e(TAG, "handleMessage: left==" + leftBeanNutrition);
                    Log.e(TAG, "handleMessage: left==" + rightNutrition);
                    if (json != null) {
                        CompeletFoodBean compeletFoodBean = JsonUtil.parseCompeletFoodBean(json);
                        if (compeletFoodBean != null) {
                            if (RL && leftBeanNutrition == null) {
                                List<CompeletFoodBean.NutritionBean> nutritionRight = compeletFoodBean.getNutrition();
                                if (nutritionRight != null) {
                                    setAdapterData(compeletFoodBean, nutritionRight);
                                    Glide.with(SearchComparisonActivity.this).load(compeletFoodBean.getThumb_image_url()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL).into(ivRight);
                                }

                            } else if (RL == false && rightNutrition == null) {
                                List<CompeletFoodBean.NutritionBean> nutritionLeft = compeletFoodBean.getNutrition();
                                if (nutritionLeft != null) {
                                    setAdapterData(compeletFoodBean, nutritionLeft);
                                    Glide.with(SearchComparisonActivity.this).load(compeletFoodBean.getThumb_image_url()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL).into(ivLeft);
                                }

                            } else if (RL && leftBeanNutrition != null) {
                                List<CompeletFoodBean.NutritionBean> right = compeletFoodBean.getNutrition();
                                CompareAdapter compareAdapter = new CompareAdapter(SearchComparisonActivity.this, leftBeanNutrition, right);
                                mlvCompelet.setAdapter(compareAdapter);

                            } else if (RL == false && rightNutrition != null) {
                                List<CompeletFoodBean.NutritionBean> left = compeletFoodBean.getNutrition();
                                CompareAdapter compareAdapter = new CompareAdapter(SearchComparisonActivity.this, left, rightNutrition);
                                mlvCompelet.setAdapter(compareAdapter);

                            }

                        }
                    }
                    break;
            }
        }
    };
    String getJson;
    private List<CompeletFoodBean.NutritionBean> leftBeanNutrition = null;
    private List<CompeletFoodBean.NutritionBean> rightNutrition = null;
    private CompeletFoodBean rightBean = null;
    private CompeletFoodBean leftBean = null;

    private void setAdapterData(CompeletFoodBean compeletFoodBean, List<CompeletFoodBean.NutritionBean> nutritionRight) {
        SearchCompeletFoodAdapter compeletFoodAdapter = new SearchCompeletFoodAdapter(SearchComparisonActivity.this, nutritionRight, RL);
        mlvCompelet.setAdapter(compeletFoodAdapter);
        getSharePreferencesData();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_comparison);
        ButterKnife.bind(this);
        getIntentData();

    }

    private void getSharePreferencesData() {

        String rightCompareData = SharedPreferencesUtils.getSPData("saveRightCompareData", this);
        Log.d(TAG, "rightCompareData = [" + rightCompareData + "]");
        if (rightCompareData != null) {
            rightBean = JsonUtil.parseCompeletFoodBean(rightCompareData);
            if (rightBean!=null){
                rightNutrition = rightBean.getNutrition();
            }
            try {
                Glide.with(SearchComparisonActivity.this).load(rightBean.getLarge_image_url()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL).into(ivRight);
            } catch (Exception e) {
                e.printStackTrace();
            }

//            setAdapterData(rightBean, rightNutrition);
//            Glide.with(this).load(rightBean.getLarge_image_url()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL).into(ivRight);
        }
        String leftCompareData = SharedPreferencesUtils.getSPData("saveLeftCompareData", this);
        Log.d(TAG, "leftCompareData = [" + leftCompareData + "]");
        if (leftCompareData != null) {
            leftBean = JsonUtil.parseCompeletFoodBean(leftCompareData);
            if (leftBean!=null){
                leftBeanNutrition = leftBean.getNutrition();
            }
            try {
                Glide.with(SearchComparisonActivity.this).load(leftBean.getLarge_image_url()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL).into(ivLeft);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            setAdapterData(leftBean, leftBeanNutrition);
//            Glide.with(this).load(leftBean.getLarge_image_url()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL).into(ivLeft);
        }

    }

    private void getIntentData() {
        Intent intent = getIntent();
        RL = intent.getBooleanExtra("RL", false);
        String code = intent.getStringExtra("code");
        if (code != null) {
            initCompeletData(code);
        }
    }

    private void initCompeletData(final String code) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                getJson = HttpUtil.getCompeletJson(SearchComparisonActivity.this, code);
                Log.e(TAG, "run: json==" + getJson);
                if (getJson != null) {
                    Message msg = handler.obtainMessage();
                    msg.what = GET_COMPELET_DATA;
                    msg.obj = getJson;
                    handler.sendMessage(msg);
                    if (RL) {
                        SharedPreferencesUtils.saveSPData("saveRightCompareData", getJson, SearchComparisonActivity.this);
                    } else {
                        SharedPreferencesUtils.saveSPData("saveLeftCompareData", getJson, SearchComparisonActivity.this);
                    }
                }

            }
        });

    }


    @OnClick(R.id.ivBack)
    public void onIvBackClick(View view) {
        finish();
    }

    @OnClick(R.id.llLeftAdd)
    public void onllLeftAddClick() {
        aBoolean = false;
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("compelet", "加入对比");
        intent.putExtra("aBoolean", aBoolean);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.llRightAdd)
    public void onllRightAddClick() {
        aBoolean = true;
        Toast.makeText(this, "您点击了添加按钮", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("compelet", "加入对比");
        intent.putExtra("aBoolean", aBoolean);
        startActivity(intent);
        finish();
    }

}
