package com.mobilephone.foodpai.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mobilephone.foodpai.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyCollectActivity extends AppCompatActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tabCollect)
    TabLayout tabCollect;
    @BindView(R.id.vpCollect)
    ViewPager vpCollect;
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
            Fragment collectFragment = new Fragment();
            Bundle bundle = new Bundle();
            bundle.putInt("number", i + 1);
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
                switch (position) {
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
    public void onIvBack() {
        finish();
    }
}
