package com.mobilephone.foodpai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.adapter.HomeSearchAdapter;
import com.mobilephone.foodpai.adapter.SearchResultAdapter;
import com.mobilephone.foodpai.adapter.SearchResultListAdapter;
import com.mobilephone.foodpai.bean.HomeSearchBean;
import com.mobilephone.foodpai.bean.SearchFoodBean;
import com.mobilephone.foodpai.myview.MyGridView;
import com.mobilephone.foodpai.util.HttpUtil;
import com.mobilephone.foodpai.util.JsonUtil;
import com.mobilephone.foodpai.util.ThreadUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class SearchActivity extends AppCompatActivity {

    private static final int GET_SEARCH_DATA = 10;
    private static final int GET_SEARCH_RESULT_DATA = 20;
    private static final String TAG = "SearchActivity-test";
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.lvSeach)
    ImageView lvSeach;
    @Bind(R.id.etSearch)
    EditText etSearch;
    @Bind(R.id.gvSearch)
    MyGridView gvSearch;
    @Bind(R.id.lvSeachResult)
    ListView lvSeachResult;
    @Bind(R.id.llRemoveHistory)
    LinearLayout llRemoveHistory;
    @Bind(R.id.llResult)
    LinearLayout llResult;
    @Bind(R.id.llNoResultLayout)
    LinearLayout llNoResultLayout;

    @Bind(R.id.llOrder)
    LinearLayout llOrder;
    @Bind(R.id.llRecomment)
    LinearLayout llRecomment;
    @Bind(R.id.lvSearchResultFood)
    ListView lvSearchResultFood;
    @Bind(R.id.llResultLayout)
    LinearLayout llResultLayout;
    Map<Object, String> map;
    List<SearchFoodBean.ItemsBean> searchFoodBeanItems;
    String compelet;
    String st;
    List<Map<Object, String>> mapList = new ArrayList<>();

    Set<String> foodSet = new HashSet<>();
    List<String> keywords;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_SEARCH_DATA:
                    String json = (String) msg.obj;
                    if (json != null) {
                        HomeSearchBean searchBean = JsonUtil.parseHomeSearch(json);
                        if (searchBean != null) {
                            keywords = searchBean.getKeywords();
                            HomeSearchAdapter adapter = new HomeSearchAdapter(SearchActivity.this, keywords);
                            gvSearch.setAdapter(adapter);
                        }

                    }
                    break;
                case GET_SEARCH_RESULT_DATA:
                    String searchJson = (String) msg.obj;
                    if (searchJson != null) {
                        SearchFoodBean searchFoodBean = JsonUtil.parseSearchFood(searchJson);
                        Log.e(TAG, "handleMessage: searchFoodBean===" + searchFoodBean);
                        if (searchFoodBean != null) {
                            searchFoodBeanItems = searchFoodBean.getItems();
                            if (searchFoodBeanItems != null) {
//                                SearchResultAdapter searchResultAdapter = new SearchResultAdapter(SearchActivity.this, searchResult);
                                SearchResultListAdapter resultListAdapter = new SearchResultListAdapter(SearchActivity.this, searchFoodBeanItems,compelet,aBoolean);
                                lvSearchResultFood.setAdapter(resultListAdapter);
                                Log.e(TAG, "handleMessage: aBoolean==" + aBoolean);
                            }
                        }

                    }

                    break;

            }
        }
    };
    boolean aBoolean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        aBoolean = intent.getBooleanExtra("aBoolean", false);

//        st = intent.getStringExtra("st");
        compelet = intent.getStringExtra("compelet");
        initSearch();
    }

    @OnItemClick(R.id.lvSearchResultFood)
    public void onLvSearchClick(AdapterView<?> parent, View view, int position, long id) {
        SearchFoodBean.ItemsBean itemsBean = searchFoodBeanItems.get(position);
        String code = itemsBean.getCode();
        Intent intent = new Intent(this, FoodMainDetailsActivity.class);
        if (compelet!=null){
            intent.putExtra("compelet",compelet);
        }
        intent.putExtra("code", code);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!foodSet.isEmpty()) {
            Log.e(TAG, "onResume: foodset" + foodSet);
            llNoResultLayout.setVisibility(View.VISIBLE);
            llResult.setVisibility(View.VISIBLE);
            llResultLayout.setVisibility(View.GONE);

            SearchResultAdapter searchResultAdapter = new SearchResultAdapter(this, mapList);
            lvSeachResult.setAdapter(searchResultAdapter);
            searchResultAdapter.notifyDataSetChanged();


        } else {
            llResult.setVisibility(View.GONE);
        }

    }


    @OnClick(R.id.llRemoveHistory)
    public void onLlRemoveHistory(View view) {
        foodSet.clear();
        llResultLayout.setVisibility(View.GONE);
        llResult.setVisibility(View.GONE);
        llNoResultLayout.setVisibility(View.VISIBLE);

    }

    @OnItemClick(R.id.gvSearch)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        llResultLayout.setVisibility(View.VISIBLE);
        llNoResultLayout.setVisibility(View.GONE);
        final String s = keywords.get(position);
        if (s != null) {
            map = new HashMap<Object, String>();
            map.put(s,s);
            etSearch.setText(s);
            ThreadUtil.execute(new Runnable() {
                @Override
                public void run() {
                    String searchFood = HttpUtil.getSearchFood(SearchActivity.this, s, 1);
                    if (searchFood != null) {
                        handerSendMsg(searchFood);
                    }
                }
            });

        }

    }


    @OnClick(R.id.lvSeach)
    public void onIvSearch() {
        llResultLayout.setVisibility(View.VISIBLE);
        llNoResultLayout.setVisibility(View.GONE);
        Log.e(TAG, "onIvSearch: 1111111111111111");
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String foodName = etSearch.getText().toString();
                foodSet.add(foodName);
                map = new HashMap<Object, String>();
                map.put(foodName, foodName);
                Log.e(TAG, "run:foodName=== " + foodName);
                if (foodName != null) {
                    String searchFood = HttpUtil.getSearchFood(SearchActivity.this, foodName, 1);
                    handerSendMsg(searchFood);
                }
            }
        });

    }

    private void handerSendMsg(String searchFood) {
        if (searchFood != null) {
            Message msg = handler.obtainMessage();
            msg.what = GET_SEARCH_RESULT_DATA;
            msg.obj = searchFood;
            handler.sendMessage(msg);
        }
    }

    private void initSearch() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String json = HttpUtil.getHomeSearch(SearchActivity.this);
                Message msg = handler.obtainMessage();
                msg.what = GET_SEARCH_DATA;
                msg.obj = json;
                handler.sendMessage(msg);

            }
        });


    }

    @OnClick(R.id.ivBack)
    public void onIvBack() {
        finish();
    }

}
