package com.mobilephone.foodpai.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.bean.bmobbean.CollectBean;
import com.mobilephone.foodpai.fragment.collect.CollectFragment;
import com.mobilephone.foodpai.util.DaoBmobUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;

public class MyCollectActivity extends AppCompatActivity {

    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.tabCollect)
    TabLayout tabCollect;
    @Bind(R.id.vpCollect)
    ViewPager vpCollect;
    private CollectFragment collectFragment;
    private List<Fragment> fragments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        ButterKnife.bind(this);
        initFragment();
    }

    private void initFragment() {
        for (int i = 0; i < 2; i++) {
            collectFragment = new CollectFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("number",i+1);
            collectFragment.setArguments(bundle);
            fragments.add(collectFragment);
        }
        vpCollect.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position){
                    case 0:
                        return "文章";
                    case 1:
                        return "食物";
                }
                return null;
            }
        });
        tabCollect.setupWithViewPager(vpCollect);
    }

    @OnClick(R.id.ivBack)
    public void onIvBack(){
        finish();
    }
}
