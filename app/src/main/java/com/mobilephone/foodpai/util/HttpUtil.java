package com.mobilephone.foodpai.util;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/10/31.
 */
public class HttpUtil {

    private static final String TAG = "HttpUtil-test";
    private static OkHttpClient okHttpClient;

    /**
     * 通过API
     * 获得食物百科数据JSON数据，Kind:group(食物分类)、brand(热门品牌)、restaurant（连锁餐饮）
     *
     * @return
     */
    public static String getFoodEncyclopedia(Context context) {
        String json = getStringCanCache(context, "http://food.boohee.com/fb/v1/categories/list");
        return json;
    }

    /**
     * eatHome的api
     *
     * @param pager
     * @return
     */
    public static String getEatHomeJson(Context context, int pager) {
        String json = getStringCanCache(context, "http://food.boohee.com/fb/v1/feeds/category_feed?page=" + pager + "&category=1&per=10");
//        Log.e(TAG, "getEatHomeJson: " + json);
        return json;
    }

    /**
     * 获取“逛吃 -- 知识”的Json数据
     *
     * @param context
     * @param pager
     * @return
     */
    public static String getEatKnowJson(Context context, int pager) {
        String json = getStringCanCache(context,
                "http://food.boohee.com/fb/v1/feeds/category_feed?page=" + pager + "&category=3&per=10&app_device=Android&app_version=2.6&channel=lenovo&user_key=&token=&phone_model=VPhone&os_version=4.4.2");
        return json;
    }


    public static String getFoodDetailsJson(Context context, String kind, int value, int page) {
        String url = "http://food.boohee.com/fb/v1/foods?kind=" + kind + "&value=" + value + "&order_by=1&page=" + page + "&order_asc=0";
        String json = getStringCanCache(context, url);
        return json;

    }

    /**
     * 比较食物
     *
     * @param context
     * @return
     */
    public static String getCompeletJson(Context context, String code) {
        String url = "http://food.boohee.com/fb/v1/foods/" + code + "/brief?";
        String json = getStringCanCache(context, url);
        return json;

    }


    /**
     * 获取“逛吃 -- 美食”的Json数据
     *
     * @param context
     * @param pager
     * @return
     */
    public static String getEatFoodJson(Context context, int pager) {
        String json = getStringCanCache(context,
                "http://food.boohee.com/fb/v1/feeds/category_feed?page=" + pager + "&category=4&per=10&app_device=Android&app_version=2.6&channel=lenovo&user_key=&token=&phone_model=VPhone&os_version=4.4.2");
        return json;
    }

    /**
     * 获取物品营养详情页的Json数据
     *
     * @param context
     * @param code
     * @return
     */
    public static String getFoodMainDetailJson(Context context, String code) {
        String json = getStringCanCache(context, "http://food.boohee.com/fb/v1/foods/" + code + "/mode_show?app_device=Android&app_version=2.6&channel=lenovo&user_key=&token=&phone_model=VPhone&os_version=4.4.2");
        return json;
    }

    /**
     * @param page
     * @return
     */
    public static String getEatTest(Context context, int page) {
        String json = null;
        try {
            String url = "http://food.boohee.com/fb/v1/feeds/category_feed?page=" + page + "&category=2&per=10&token=&user_key=";
            json = getStringCanCache(context, url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;
    }

    public static String getHomeSearch(Context context) {
        String url = "http://food.boohee.com/fb/v1/keywords?";
        String json = getStringCanCache(context, url);
        return json;
    }

    public static String getSearchFood(Context context, String foodName, int page) {
        String url = "http://food.boohee.com/fb/v1/search?page=" + page + "&order_asc=desc&q=" + foodName;
        String json = getStringCanCache(context, url);
        return json;
    }

    private static String getStringByOkHttp(String url) {
        String json = "";
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build();
        }
        Request request = new Request.Builder()
                .url(url)
                .tag("tag")
                .build();
        Log.e(TAG, "getStringByOkHttp: " + url);

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                json = response.body().string();
                Log.e(TAG, "getStringByOkHttp: " + json);
            } else {
                json = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getStringByOkHttp: " + e.getMessage());
        }
        return json;
    }

    /**
     * 自动缓存数据到缓存文件中的OKHttp下载
     *
     * @param context 上下文
     * @param url     网址
     * @return Json数据
     */
    private static String getStringCanCache(Context context, String url) {
        String json = null;
        //做了修改，让okhttp单例
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new CacheInterceptor())
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .cache(new Cache(context.getExternalCacheDir(), 10 * 1024 * 1024))   //配置缓存路径及缓存空间
                    .build();
        }
        Request request = new Request.Builder()
                .url(url)
                .tag("Tag")
                .build();

        try {
            if (!NetworkUtil.isNetWorkAvailable(context)) {    //没有网络，从缓存中拿取数据
                Response response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
//                    Log.e(TAG, "getStringCanCache: 有没有？" + response.code());
                }

            } else {              //在有网络的情况下，联网更新数据
                request.newBuilder()
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
                Response response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    json = response.body().string();
//                    Log.e(TAG, "getStringCanCache: 有网络？" + response.code());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static class CacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            Response response1 = response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "max-age=" + 3600 * 24 * 30)       //缓存24*30小时
                    .build();
            return response1;
        }
    }

}
