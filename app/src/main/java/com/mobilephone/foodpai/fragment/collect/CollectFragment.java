package com.mobilephone.foodpai.fragment.collect;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.activity.FoodMainDetailsActivity;
import com.mobilephone.foodpai.adapter.CollectAdapter;
import com.mobilephone.foodpai.adapter.FoodCollectAdapter;
import com.mobilephone.foodpai.base.BaseFragment;
import com.mobilephone.foodpai.bean.bmobbean.CollectBean;
import com.mobilephone.foodpai.ui.WebActivity;
import com.mobilephone.foodpai.util.DaoBmobUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2016-11-04.
 */
public class CollectFragment extends BaseFragment {
    private static final String TAG = "test";
    @Bind(R.id.lvCollect)
    ListView lvCollect;
    private View view;

    private int number;
    private CollectAdapter collectAdapter;
    private FoodCollectAdapter CollectFoodAdapter;
    boolean isRefresh = false;
    List<CollectBean> listArticle = new ArrayList<>();
    List<CollectBean> listFood = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.collect_fragment, container, false);
            ButterKnife.bind(this, view);

            number = getArguments().getInt("number");
            onQuery();
        }
        lvCollect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (number == 1) {
                    CollectBean collectBean = listArticle.get(position);
                    String link = collectBean.getLink();
                    String title = collectBean.getTitle();
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    intent.putExtra("link", link);
                    intent.putExtra("title", title);
                    startActivity(intent);
                    isRefresh = true;
                }else {
                    CollectBean collectBean = listFood.get(position);
                    String code = collectBean.getCode();
                    Intent intent = new Intent(getActivity(), FoodMainDetailsActivity.class);
                    intent.putExtra("code",code);
                    startActivity(intent);
                    isRefresh=true;
                }
            }
        });
        return view;
    }


    private void onQuery() {
        DaoBmobUtil.getInstance().onQuery(new DaoBmobUtil.OnDaoQuery() {
            @Override
            public void onQuery(List<CollectBean> list, BmobException e) {
                try {
                    if (number == 1) {
                        listArticle.clear();
                    } else {
                        listFood.clear();
                    }

                    for (int i = 0; i < list.size(); i++) {
                        String link = list.get(i).getLink();
                        if (link != null) {
                            CollectBean collectBean = list.get(i);
                            listArticle.add(collectBean);
                        } else {
                            CollectBean collectBean = list.get(i);
                            listFood.add(collectBean);
                        }
                    }
                    if (number == 1) {
                        collectAdapter = new CollectAdapter(getActivity(), listArticle);
                        lvCollect.setAdapter(collectAdapter);
                    } else {
                        CollectFoodAdapter = new FoodCollectAdapter(getActivity(), listFood);
                        lvCollect.setAdapter(CollectFoodAdapter);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        if (isRefresh == true) {
            onQuery();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
