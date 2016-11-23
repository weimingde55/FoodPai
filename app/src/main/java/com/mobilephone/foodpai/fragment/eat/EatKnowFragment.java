package com.mobilephone.foodpai.fragment.eat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.adapter.EatKnowRVAdapter;
import com.mobilephone.foodpai.base.BaseFragment;
import com.mobilephone.foodpai.bean.EatKnowBean;
import com.mobilephone.foodpai.ui.WebActivity;
import com.mobilephone.foodpai.util.HttpUtil;
import com.mobilephone.foodpai.util.JsonUtil;
import com.mobilephone.foodpai.util.ThreadUtil;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/10/31.
 */
public class EatKnowFragment extends BaseFragment {

    //    @Bind(R.id.rvEatKnow)
//    RecyclerView rvEatKnow;
    @Bind(R.id.srflEatKnow)
    SwipeRefreshLayout srflEatKnow;

    private static final String TAG = "EatKnowFragment-test";
    //    @Bind(R.id.rvEatKnow)
    private PullLoadMoreRecyclerView rvEatKnow;

    private View view;
    private List<EatKnowBean.FeedsBean> feedsBeanList = new ArrayList<>();
    private int currentPager = 1;
    private final int MESSAGE_SEND_KNOW_JSON = 112;//成功获取数据
    private final int MESSAGE_NOTSEND_KNOW_JSON = 116;//获取数据失败
    private LinearLayoutManager linearLayoutManager;
    private EatKnowRVAdapter eatKnowRVAdapter;

    private boolean isPullToUpRefresh = false;//是否为上拉刷新，默认为false;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.eatknow_fragment, container, false);
            rvEatKnow = ((PullLoadMoreRecyclerView) view.findViewById(R.id.rvEatKnow));

        }

        //联网获取数据
        getDataFromHttp(1);

        //初始化下拉上拉刷新
        initPullRefresh();
        //初始化RecyclerView
//        initRecyclerView();


        return view;
    }


    /**
     * 处理Item的点击事件的方法
     */
    private void setOnItemClick() {
        eatKnowRVAdapter.setOnItemClickListener(new EatKnowRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                EatKnowBean.FeedsBean feedsBean = feedsBeanList.get(position);
                String link = feedsBean.getLink();
                String title = feedsBean.getTitle();
                intent.putExtra("link", link);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });
    }

    private void initPullRefresh() {
        rvEatKnow.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                getDataFromHttp(1);
                isPullToUpRefresh = false;
            }

            @Override
            public void onLoadMore() {
                getDataFromHttp(currentPager + 1);
                isPullToUpRefresh = true;
            }
        });
    }

    private void getDataFromHttp(final int pager) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String json = HttpUtil.getEatKnowJson(getContext(), pager);
                Log.e(TAG, "json : " + json);
                EatKnowBean eatKnowBean = JsonUtil.parseEatKnowBean(json);
                Message message = handler.obtainMessage();
                if (json != null) {
                    message.what = MESSAGE_SEND_KNOW_JSON;
                    message.obj = eatKnowBean;
//                    Message msg = handler.obtainMessage(MESSAGE_SEND_KNOW_JSON, eatKnowBean);
                    handler.sendMessage(message);
                } else {
                    message.what = MESSAGE_NOTSEND_KNOW_JSON;
                    handler.sendMessage(message);
                }
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SEND_KNOW_JSON:
                    EatKnowBean eatKnowBean = (EatKnowBean) msg.obj;
                    if (eatKnowBean != null) {
                        List<EatKnowBean.FeedsBean> feeds = eatKnowBean.getFeeds();
                        if (!isPullToUpRefresh) {
                            feedsBeanList.clear();
                            feedsBeanList.addAll(feeds);
                            initRecyclerView();
                            currentPager = 1;
                        } else {
                            feedsBeanList.addAll(feeds);
                            currentPager++;
                        }
                    }
                    rvEatKnow.setPullLoadMoreCompleted();
                    if (eatKnowRVAdapter != null) {
                        eatKnowRVAdapter.notifyDataSetChanged();
                        setOnItemClick();//处理Item的点击事件
                    }
                    break;
                case MESSAGE_NOTSEND_KNOW_JSON:
                    rvEatKnow.setPullLoadMoreCompleted();
                    break;
            }
        }
    };

    private void initRecyclerView() {

        if (eatKnowRVAdapter == null) {
            eatKnowRVAdapter = new EatKnowRVAdapter(getActivity(), feedsBeanList);
            rvEatKnow.setAdapter(eatKnowRVAdapter);
//            linearLayoutManager = new LinearLayoutManager(getContext());
//            rvEatKnow.setLayoutManager(linearLayoutManager);
            rvEatKnow.setLinearLayout();
        }
    }


    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
    }
}
