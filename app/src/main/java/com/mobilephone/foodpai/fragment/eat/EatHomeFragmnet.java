package com.mobilephone.foodpai.fragment.eat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.activity.EatHomeImageActivity;
import com.mobilephone.foodpai.adapter.EatHomeRVAdapter;
import com.mobilephone.foodpai.base.BaseFragment;
import com.mobilephone.foodpai.bean.EatHomeFoodBean;
import com.mobilephone.foodpai.bean.EatHomeWebBean;
import com.mobilephone.foodpai.ui.FoodHomeWebActivity;
import com.mobilephone.foodpai.util.HttpUtil;
import com.mobilephone.foodpai.util.JsonUtil;
import com.mobilephone.foodpai.util.ThreadUtil;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */
public class EatHomeFragmnet extends BaseFragment {
    private static final String TAG = "EatHomeFragmnet-test";
    private static final int MSG_WEBHTTP_OK = 50;
    private View view;
    private static final int GETHTTP_OK = 45;
    private static final int GETHTTP_NOTOK = 35;
    //    private GridView gridView;
    private List<EatHomeFoodBean.FeedsBean> eatHomeFoodBeanList = new ArrayList<>();
    //    private EatHomeAdapter eatHomeAdapter;
    private EatHomeRVAdapter eatHomeRVAdapter;
    private PullLoadMoreRecyclerView rvEatHome;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private SwipeRefreshLayout srlRefreshRoot;
    private boolean isPullToUpRefresh = false;//是否是上拉刷新默认为false
    private int currentPager = 1;
    private String link;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.eathome_fragment, container, false);

            //大改动，之前的RecyclerView直接改用下拉上拉刷新的PullLoadMoreRecyclerView~~~~
            rvEatHome = ((PullLoadMoreRecyclerView) view.findViewById(R.id.rvEatHome));
            //显示下拉刷新
//            rvEatHome.setRefreshing(true);

            getFromHttp(1);
            getFoodWebJSON();
            rvEatHome.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
                @Override
                public void onRefresh() {
                    getFromHttp(1);
                    isPullToUpRefresh = false;
                }

                @Override
                public void onLoadMore() {
                    getFromHttp(currentPager + 1);
                    isPullToUpRefresh = true;
                }
            });

        }
        return view;
    }


    private void getFromHttp(final int pager) {

        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String json = HttpUtil.getEatHomeJson(getContext(), pager);
                Message message = handler.obtainMessage();
                EatHomeFoodBean eatHomeFoodBean = JsonUtil.parseEatHomeFoodBean(json);
                if (json != null && eatHomeFoodBean != null) {
                    message.what = GETHTTP_OK;
                    message.obj = eatHomeFoodBean;
                    handler.sendMessage(message);
                } else {
                    message.what = GETHTTP_NOTOK;
                    handler.sendMessage(message);
                }

            }
        });
    }

    private void getFoodWebJSON() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String json = HttpUtil.getEatHomeJson(getContext(), 1);
                Message msg = handler.obtainMessage();
                if (json != null) {
                    msg.what = MSG_WEBHTTP_OK;
                    msg.obj = json;
                    handler.sendMessage(msg);
                }
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GETHTTP_OK:
                    //网络访问成功，初始化数据
                    EatHomeFoodBean eatHomeFoodBean = (EatHomeFoodBean) msg.obj;
                    List<EatHomeFoodBean.FeedsBean> feedsBeanList = eatHomeFoodBean.getFeeds();
                    //如果是下拉刷新
                    if (!isPullToUpRefresh) {
                        eatHomeFoodBeanList.clear();
                        eatHomeFoodBeanList.addAll(feedsBeanList);
//                    eatHomeAdapter.notifyDataSetChanged();
                        rvEatHome.setPullLoadMoreCompleted();
                        currentPager = 1;
                        if (eatHomeRVAdapter != null)
                            eatHomeRVAdapter.notifyDataSetChanged();
                    } else {
                        eatHomeFoodBeanList.addAll(feedsBeanList);
                        rvEatHome.setPullLoadMoreCompleted();
                        //下拉刷新成功的时候
                        currentPager++;
                        if (eatHomeRVAdapter != null)
                            eatHomeRVAdapter.notifyDataSetChanged();
                    }
                    initAdapter();
                    break;
                case MSG_WEBHTTP_OK:
                    String json = (String) msg.obj;
                    EatHomeWebBean eatHomeWebBean = JsonUtil.parseEatHomeWebBean(json);
                    if (eatHomeWebBean != null) {
                        link = eatHomeWebBean.getFeeds().get(0).getLink();
                    }
                    break;
                case GETHTTP_NOTOK:
                    rvEatHome.setPullLoadMoreCompleted();
                    break;
            }
        }
    };

    private void initAdapter() {
        if (eatHomeRVAdapter == null) {
            eatHomeRVAdapter = new EatHomeRVAdapter(eatHomeFoodBeanList, getContext());
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            rvEatHome.setAdapter(eatHomeRVAdapter);
            rvEatHome.setStaggeredGridLayout(2);
            eatHomeRVAdapter.setOnItemClickListener(new EatHomeRVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    EatHomeFoodBean.FeedsBean feedsBean = eatHomeFoodBeanList.get(position);
                    if (position == 0) {
                        Intent intent = new Intent(getActivity(), FoodHomeWebActivity.class);
                        intent.putExtra("link", link);
                        startActivity(intent);
                    } else {
                        EventBus.getDefault().postSticky(feedsBean);
                        Intent intent = new Intent(getActivity(), EatHomeImageActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }

    }
}
