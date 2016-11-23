package com.mobilephone.foodpai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.adapter.FoodClassDetailsAdapter;
import com.mobilephone.foodpai.bean.FoodDetailsBean;
import com.mobilephone.foodpai.util.HttpUtil;
import com.mobilephone.foodpai.util.JsonUtil;
import com.mobilephone.foodpai.util.ThreadUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class FoodDetailsActivity extends AppCompatActivity {

    private static final int GET_FOOD_DETAILS_DATA = 10;
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.tvFoodClassName)
    TextView tvFoodClassName;
    @Bind(R.id.lvFood)
    ListView lvFood;
    String foodClassName;
    String kind;
    int position;
    List<FoodDetailsBean.FoodsBean> foods;
    @Bind(R.id.tvAll)
    TextView tvAll;
    @Bind(R.id.cbOrder)
    CheckBox cbOrder;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_FOOD_DETAILS_DATA:
                    String json = (String) msg.obj;
                    if (json != null) {
                        try {
                            FoodDetailsBean foodDetailsBean = JsonUtil.parseFoodDetails(json);
                            foods = foodDetailsBean.getFoods();
                            if (foods != null) {
                                FoodClassDetailsAdapter foodClassDetailsAdapter = new FoodClassDetailsAdapter(FoodDetailsActivity.this, foods);
                                lvFood.setAdapter(foodClassDetailsAdapter);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        foodClassName = intent.getStringExtra("name");
        position = intent.getIntExtra("position", -1);
        kind = intent.getStringExtra("kind");
        tvFoodClassName.setText(foodClassName);
        initFoodData();

    }

    @OnItemClick(R.id.lvFood)
    public void onLvFoodItemClick(AdapterView<?> parent, View view, int position, long id) {
        FoodDetailsBean.FoodsBean foodsBean = foods.get(position);
        String code = foodsBean.getCode();
        Intent intent = new Intent(this, FoodMainDetailsActivity.class);
        intent.putExtra("code",code);
        startActivity(intent);


    }

    @OnClick(R.id.ivBack)
    public void onIvBackClick(View view) {
        finish();
    }

    private void initFoodData() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String json = HttpUtil.getFoodDetailsJson(FoodDetailsActivity.this, kind, position + 1, 1);
                if (json != null) {
                    Message msg = handler.obtainMessage();
                    msg.what = GET_FOOD_DETAILS_DATA;
                    msg.obj = json;
                    handler.sendMessage(msg);

                }

            }
        });
    }
}
