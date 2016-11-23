package com.mobilephone.foodpai.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.base.BaseFragment;
import com.mobilephone.foodpai.fragment.eat.EatFoodFragment;
import com.mobilephone.foodpai.fragment.eat.EatHomeFragmnet;
import com.mobilephone.foodpai.fragment.eat.EatKnowFragment;
import com.mobilephone.foodpai.fragment.eat.EatTestFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/10/31.
 * 吃货街的四个fragment导航
 */
public class EatFragment extends BaseFragment {
    private View view;
    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private EatHomeFragmnet eatHomeFragmnet;
    private EatKnowFragment eatKnowFragment;
    private EatTestFragment eatTestFragment;
    private EatFoodFragment eatFoodFragment;
    private RadioGroup rgEatMenu;
    private ViewPager vpEat;
    private FragmentPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_eat, container, false);
            //初始化fragment
            initFragment();
            //初始化导航按钮
            initEatMenu();
            //初始化viewpager
            initViewPager();
        }
        return view;
    }

    private void initViewPager() {
        vpEat = ((ViewPager) view.findViewById(R.id.vpEat));

        if (adapter == null) {
            adapter = new FragmentPagerAdapter(getChildFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    return fragmentList.get(position);
                }

                @Override
                public int getCount() {
                    return fragmentList.size();
                }
            };
            vpEat.setAdapter(adapter);
            vpEat.setOffscreenPageLimit(4);

            vpEat.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    ((RadioButton) rgEatMenu.getChildAt(position)).setChecked(true);

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

    }

    private void initEatMenu() {
        if (rgEatMenu == null) {
            rgEatMenu = ((RadioGroup) view.findViewById(R.id.rgEatMenu));
            rgEatMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rbEatHome:
                            vpEat.setCurrentItem(0, true);
                            break;
                        case R.id.rbEatTest:
                            vpEat.setCurrentItem(1, true);
                            break;
                        case R.id.rbEatKnow:
                            vpEat.setCurrentItem(2, true);
                            break;
                        case R.id.rbEatFood:
                            vpEat.setCurrentItem(3, true);
                            break;
                    }
                }
            });
        }
    }

    private void initFragment() {
        if (adapter == null) {
            fragmentManager = getFragmentManager();
            eatHomeFragmnet = new EatHomeFragmnet();
            eatTestFragment = new EatTestFragment();
            eatKnowFragment = new EatKnowFragment();
            eatFoodFragment = new EatFoodFragment();
            fragmentList.add(eatHomeFragmnet);
            fragmentList.add(eatTestFragment);
            fragmentList.add(eatKnowFragment);
            fragmentList.add(eatFoodFragment);
        }

    }
}
