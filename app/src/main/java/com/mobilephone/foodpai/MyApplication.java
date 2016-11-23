package com.mobilephone.foodpai;

import android.app.Application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cn.bmob.v3.Bmob;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Administrator on 2016-11-02.
 */
public class MyApplication extends Application {

    private static final String TAG = "MyApplication-test";
    private static MyApplication INSTANCE;

    public static MyApplication INSTANCE() {
        return INSTANCE;
    }

    private void setInstance(MyApplication app) {
        setMyApplication(app);
    }

    private static void setMyApplication(MyApplication a) {
        MyApplication.INSTANCE = a;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        //初始化
        //只有主进程运行的时候才需要初始化
        if (getApplicationInfo().packageName.equals(getMyProcessName())) {
            ShareSDK.initSDK(this);
            //提供以下两种方式进行初始化操作：
            //第一：默认初始化
            Bmob.initialize(this,"36a2aec9f718227ac50a5607e3e8fc74");

            //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
            //BmobConfig config =new BmobConfig.Builder(this)
            ////设置appkey
            //.setApplicationId("Your Application ID")
            ////请求超时时间（单位为秒）：默认15s
            //.setConnectTimeout(30)
            ////文件分片上传时每片的大小（单位字节），默认512*1024
            //.setUploadBlockSize(1024*1024)
            ////文件的过期时间(单位为秒)：默认1800s
            //.setFileExpiration(2500)
            //.build();
            //Bmob.initialize(config);
        }

    }

    /**
     * 获取当前运行的进程名
     *
     * @return
     */
    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


