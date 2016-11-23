package com.mobilephone.foodpai.fragment.eat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.adapter.EatFoodRVAdapter;
import com.mobilephone.foodpai.base.BaseFragment;
import com.mobilephone.foodpai.bean.EatFoodBean;
import com.mobilephone.foodpai.ui.WebActivity;
import com.mobilephone.foodpai.util.HttpUtil;
import com.mobilephone.foodpai.util.JsonUtil;
import com.mobilephone.foodpai.util.ThreadUtil;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */
public class EatFoodFragment extends BaseFragment {
    private View view;
    private PullLoadMoreRecyclerView rvEatFood;
    private int pager;
    private int currentPager = 1;
    private final int MESSAGE_SEND_FOOD_JSON = 119;
    private final int MESSAGE_NOTSEND_FOOD_JSON = 114;
    private EatFoodRVAdapter adapter;
    private boolean isRefresh;
    private List<EatFoodBean.FeedsBean> feedList = new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_SEND_FOOD_JSON:
                    List<EatFoodBean.FeedsBean> feeds = (List<EatFoodBean.FeedsBean>) msg.obj;
                    if (!isRefresh){
                        feedList.clear();
                        feedList.addAll(feeds);
                        initRecyclerView();
                    }else {
                        feedList.addAll(feeds);
                        currentPager++;
                    }
                    rvEatFood.setPullLoadMoreCompleted();
                    setOnItemClick();//处理Item的点击事件
                    adapter.notifyDataSetChanged();
                    break;
                case MESSAGE_NOTSEND_FOOD_JSON:
                    rvEatFood.setPullLoadMoreCompleted();
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.eatfood_fragment, container, false);
            rvEatFood = ((PullLoadMoreRecyclerView) view.findViewById(R.id.rvEatFood));
        }

        //联网获取数据
        getDataFromHttp(1);

        //初始化刷新
        initPullRefresh();

        return view;
    }

    private void getDataFromHttp(final int pager) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String json = HttpUtil.getEatFoodJson(getActivity(), pager);
                EatFoodBean eatFoodBean = JsonUtil.parseEatFoodBean(json);
                if (json != null){
                    if (eatFoodBean != null && eatFoodBean.getFeeds() != null){
                        List<EatFoodBean.FeedsBean> feeds = eatFoodBean.getFeeds();
                        Message msg = handler.obtainMessage(MESSAGE_SEND_FOOD_JSON,feeds);
                        handler.sendMessage(msg);
                    }
                }else {
                    Message msg = handler.obtainMessage(MESSAGE_NOTSEND_FOOD_JSON);
                    handler.sendMessage(msg);
                }
            }
        });
    }

    public void initRecyclerView(){
        if (adapter == null){
            adapter = new EatFoodRVAdapter(getContext(),feedList);
            rvEatFood.setAdapter(adapter);
            rvEatFood.setLinearLayout();
        }
    }

    private void initPullRefresh() {
        rvEatFood.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                getDataFromHttp(1);
                isRefresh = false;
            }

            @Override
            public void onLoadMore() {
                getDataFromHttp(currentPager + 1);
                isRefresh = true;
            }
        });
    }


    /**
     * 处理Item的点击事件的方法
     */
    private void setOnItemClick() {
        adapter.setOnItemClickListener(new EatFoodRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                EatFoodBean.FeedsBean feedsBean = feedList.get(position);
                String link = feedsBean.getLink();
                String title = feedsBean.getTitle();
                intent.putExtra("link",link);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });
    }

}
