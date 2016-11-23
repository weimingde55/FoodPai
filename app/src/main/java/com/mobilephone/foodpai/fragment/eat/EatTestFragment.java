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
import com.mobilephone.foodpai.adapter.EatTestRVAdapter;
import com.mobilephone.foodpai.base.BaseFragment;
import com.mobilephone.foodpai.bean.EatTestBean;
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
public class EatTestFragment extends BaseFragment {

    private static final int GET_TEST_DATA = 20;
    //    @Bind(R.id.lvTest)
//    ListView lvTest;
    private static final int NOTGET_TEST_DATA = 25;
    private static final String TAG = "EatTestFragment-test";
    private PullLoadMoreRecyclerView lvTest;
    private View view;
    private int currentPage = 1;
    private boolean isPullToUpRefresh = false;//是否为上拉刷新，默认为fals;
    private EatTestRVAdapter eatTestRVAdapter;
    private List<EatTestBean.FeedsBean> feeds = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.eattest_fragment, container, false);
            lvTest = ((PullLoadMoreRecyclerView) view.findViewById(R.id.lvTest));
            getDataFromHttp(1);
            initlvTestRefresh();
        }
        return view;
    }

    /**
     * 处理Item的点击事件的方法
     */
    private void setOnItemClick() {
        eatTestRVAdapter.setOnItemClickListener(new EatTestRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                EatTestBean.FeedsBean feedsBean = feeds.get(position);
                String link = feedsBean.getLink();
                String title = feedsBean.getTitle();
                intent.putExtra("link", link);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });
    }

    private void initlvTestRefresh() {
        lvTest.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                getDataFromHttp(1);
                isPullToUpRefresh = false;
            }

            @Override
            public void onLoadMore() {
                getDataFromHttp(currentPage + 1);
                isPullToUpRefresh = true;
            }
        });
    }

    private void getDataFromHttp(final int currentPage) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String eatTest = HttpUtil.getEatTest(getActivity(), currentPage);
//                Log.e(TAG, "run: json == " + eatTest);
                Message msg = handler.obtainMessage();
                if (eatTest != null) {
                    msg.what = GET_TEST_DATA;
                    msg.obj = eatTest;
                    handler.sendMessage(msg);
                } else {
                    msg.what = NOTGET_TEST_DATA;
                    handler.sendMessage(msg);
                }
            }
        });
    }

    private Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_TEST_DATA:
                    String json = (String) msg.obj;
                    EatTestBean testBean = JsonUtil.parseEatTest(json);
                    if (testBean != null) {
                        List<EatTestBean.FeedsBean> beanList = testBean.getFeeds();
                        //如果是下拉刷新
                        if (!isPullToUpRefresh) {
                            feeds.clear();
                            feeds.addAll(beanList);
                            currentPage = 1;
                            initAdapter();
                        } else {
                            //上拉加载更多
                            currentPage++;
                            feeds.addAll(beanList);
                        }
                    }
                    if (eatTestRVAdapter != null) {
                        eatTestRVAdapter.notifyDataSetChanged();
                        lvTest.setPullLoadMoreCompleted();

                        setOnItemClick();//处理Item的点击事件
                    }
                    break;
                case NOTGET_TEST_DATA:
                    lvTest.setPullLoadMoreCompleted();
                    break;

            }
        }

        private void initAdapter() {

            if (eatTestRVAdapter == null) {
                eatTestRVAdapter = new EatTestRVAdapter(getContext(), feeds);
                lvTest.setAdapter(eatTestRVAdapter);
                lvTest.setLinearLayout();
            }

//            ButterKnife.bind(this, view);
//            lvTest=((ListView) view.findViewById(R.id.lvTest));
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
