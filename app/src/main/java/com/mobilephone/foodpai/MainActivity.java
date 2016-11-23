package com.mobilephone.foodpai;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mobilephone.foodpai.fragment.EatFragment;
import com.mobilephone.foodpai.fragment.HomeFragment;
import com.mobilephone.foodpai.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity-test";
    private List<Fragment> fragmentList = new ArrayList<>();
    private HomeFragment homeFragment;
    private EatFragment eatFragment;
    private MineFragment mineFragment;
    private FragmentManager supportFragmentManager;
    private RadioGroup rgMainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();
        initMenuButton();
        addFragment(homeFragment);

    }

    private void initMenuButton() {
        rgMainMenu = ((RadioGroup) findViewById(R.id.rgMainMenu));
        rgMainMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbMenuHome:
                        addFragment(homeFragment);
                        break;
                    case R.id.rbMenuEat:
                        addFragment(eatFragment);
                        break;
                    case R.id.rbMenuMine:
                        addFragment(mineFragment);
                        break;
                }
            }
        });
    }

    private void initFragment() {
        supportFragmentManager = getSupportFragmentManager();
        homeFragment = new HomeFragment();
        eatFragment = new EatFragment();
        mineFragment = new MineFragment();
        fragmentList.add(homeFragment);
        fragmentList.add(eatFragment);
        fragmentList.add(mineFragment);
    }

    private void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.flMain, fragment);
        }
        for (Fragment f : fragmentList) {
            if (f == fragment) {
                fragmentTransaction.show(f);
            } else {
                fragmentTransaction.hide(f);
            }
        }
        fragmentTransaction.commit();
    }


    /**
     * back键退出程序
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitTowClick();//调用双击的方法
        }
        return false;
    }

    private boolean isExit = false;

    /**
     * 双击则退出程序
     */
    private void exitTowClick() {
        Timer exit = null;
        if (isExit == false) {
            isExit = true;//准备退出
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exit = new Timer();
            exit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;//取消退出
                }
            }, 3000);//如果两秒钟内没点击第二次 ，则启动定时器取消刚才的执行任务
        } else {
            finish();
            System.exit(0);
        }
    }

}
