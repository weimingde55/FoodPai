package com.mobilephone.foodpai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.activity.EatFoodActivity;
import com.mobilephone.foodpai.activity.FoodDetailsActivity;
import com.mobilephone.foodpai.activity.FoodMainDetailsActivity;
import com.mobilephone.foodpai.activity.SearchActivity;
import com.mobilephone.foodpai.activity.SearchComparisonActivity;
import com.mobilephone.foodpai.adapter.HomeAdapter;
import com.mobilephone.foodpai.base.BaseFragment;
import com.mobilephone.foodpai.bean.FoodEncyclopediaBean;
import com.mobilephone.foodpai.myview.MyGridView;
import com.mobilephone.foodpai.ui.FoodHomeWebActivity;
import com.mobilephone.foodpai.util.HttpUtil;
import com.mobilephone.foodpai.util.JsonUtil;
import com.mobilephone.foodpai.util.ThreadUtil;
import com.mobilephone.foodpai.zxing.activity.CaptureActivity;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/10/31.
 */

public class HomeFragment extends BaseFragment {

    private static final int MSG_JSON_FOODENCYCLOPEDIA = 10;
    private static final String TAG = "hometest";
    @Bind(R.id.llSearch)
    LinearLayout llSearch;
    @Bind(R.id.ivAnalyze)
    ImageButton ivAnalyze;
    @Bind(R.id.ivSearch)
    ImageButton ivSearch;
    @Bind(R.id.ivQRCode)
    ImageButton ivQRCode;
    @Bind(R.id.gvFoods)
    MyGridView gvFoods;
    @Bind(R.id.gvHot)
    MyGridView gvHot;
    @Bind(R.id.gvChain)
    MyGridView gvChain;


    private List<FoodEncyclopediaBean.GroupBean.CategoriesBean> FoodsCategoriesList = new ArrayList<>();
    private List<FoodEncyclopediaBean.GroupBean.CategoriesBean> HotCategoriesList = new ArrayList<>();
    private List<FoodEncyclopediaBean.GroupBean.CategoriesBean> ChainCategoriesList = new ArrayList<>();

    private HomeAdapter foodsAdapter;
    private HomeAdapter hotAdapter;
    private HomeAdapter chainAdapter;

    private View view;


    /**
     * 通过Handler获得JSON
     * 并用JsonUtil获得bean集
     * 初始化构造器
     */
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_JSON_FOODENCYCLOPEDIA:
                    String json = (String) msg.obj;
                    FoodEncyclopediaBean foodEncyclopediaBean = JsonUtil.parseFoodEncyclopedia(json);
                    if (foodEncyclopediaBean != null) {

                        //获得数据的集合
                        List<FoodEncyclopediaBean.GroupBean> group = foodEncyclopediaBean.getGroup();
                        FoodsCategoriesList.addAll(group.get(0).getCategories());
                        HotCategoriesList.addAll(group.get(1).getCategories());
                        ChainCategoriesList.addAll(group.get(2).getCategories());

                        //设置适配器
                        foodsAdapter = new HomeAdapter(getActivity(), FoodsCategoriesList, HomeAdapter.LAYOUT_MANAGER_BIG);
                        hotAdapter = new HomeAdapter(getActivity(), HotCategoriesList, HomeAdapter.LAYOUT_MANAGER_SMALL);
                        chainAdapter = new HomeAdapter(getActivity(), ChainCategoriesList, HomeAdapter.LAYOUT_MANAGER_SMALL);
                        gvFoods.setAdapter(foodsAdapter);
                        gvHot.setAdapter(hotAdapter);
                        gvChain.setAdapter(chainAdapter);
                    } else {
                        Toast.makeText(getActivity(), "网络异常,请检查网络", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @OnClick(R.id.ivAnalyze)
    public void onIvAnalyse(View view) {
        startActivity(new Intent(getContext(), EatFoodActivity.class));
    }
    @OnClick(R.id.ivSearch)
    public void onIvSearchClick(View view) {
        startActivity(new Intent(getContext(),SearchComparisonActivity.class));
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, container, false);
            ButterKnife.bind(this, view);
            getJson();
        }

        TextView tvTest = (TextView) view.findViewById(R.id.tvTest);
        tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FoodMainDetailsActivity.class);
                startActivity(intent);
            }
        });

        gvFoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getContext(), FoodDetailsActivity.class);
                String name = FoodsCategoriesList.get(position).getName();
                intent.putExtra("name", name);
                intent.putExtra("kind", "group");
                intent.putExtra("position", position);
                getActivity().startActivity(intent);
            }
        });
        gvChain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getContext(), FoodDetailsActivity.class);
                String name = ChainCategoriesList.get(position).getName();
                intent.putExtra("name", name);
                intent.putExtra("kind", "restaurant");
                intent.putExtra("position", position);
                getActivity().startActivity(intent);
            }
        });
        gvHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getContext(), FoodDetailsActivity.class);
                String name = HotCategoriesList.get(position).getName();
                intent.putExtra("name", name);
                intent.putExtra("kind", "brand");
                intent.putExtra("position", position);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }

    /**
     * 获得JSON
     */
    public void getJson() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {

                String json = HttpUtil.getFoodEncyclopedia(getContext());

                Message msg = handler.obtainMessage();
                msg.what = MSG_JSON_FOODENCYCLOPEDIA;
                msg.obj = json;
                handler.sendMessage(msg);
            }
        });
    }

    @OnClick(R.id.llSearch)
    public void onLlSearch(View view) {
        getActivity().startActivity(new Intent(getContext(), SearchActivity.class));
    }


    @OnClick(R.id.ivQRCode)
    public void onIvQRCodeClick(){
        readQRCode();
    }


    /**
     * 启动扫描二维码
     */
    public void readQRCode() {
        startActivityForResult(new Intent(getActivity(),CaptureActivity.class),100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode==getActivity().RESULT_OK) {
            String result = intent.getExtras().getString("result");
            try {
                URL url = new URL(result);
                Intent webIntent = new Intent(getActivity(), FoodHomeWebActivity.class);
                webIntent.putExtra("link",result);
                startActivity(webIntent);
            } catch (Exception e) {
                onDialog(result);
            }

        }
    }



    public void onDialog(String result){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("扫码结果")
                .setMessage(result)
                .create().show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
