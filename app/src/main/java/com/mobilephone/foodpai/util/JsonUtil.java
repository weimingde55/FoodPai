package com.mobilephone.foodpai.util;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mobilephone.foodpai.bean.CompeletFoodBean;
import com.mobilephone.foodpai.bean.EatFoodBean;
import com.mobilephone.foodpai.bean.EatHomeFoodBean;
import com.mobilephone.foodpai.bean.EatHomeWebBean;
import com.mobilephone.foodpai.bean.EatKnowBean;
import com.mobilephone.foodpai.bean.EatTestBean;
import com.mobilephone.foodpai.bean.FoodDetailsBean;
import com.mobilephone.foodpai.bean.FoodEncyclopediaBean;
import com.mobilephone.foodpai.bean.FoodMainDetailBean;
import com.mobilephone.foodpai.bean.HomeSearchBean;
import com.mobilephone.foodpai.bean.SearchFoodBean;

/**
 * Created by Administrator on 2016-10-31.
 */
public class JsonUtil {

    /**
     * 使用Gson解析食物百科（首页）的Json数据。
     * 返回FoodEncyclopediaBean
     *
     * @param json
     * @return foodEncyclopediaBean
     */
    public static FoodEncyclopediaBean parseFoodEncyclopedia(String json) {

        FoodEncyclopediaBean foodEncyclopediaBean = null;
        try {
            foodEncyclopediaBean = new Gson().fromJson(json, FoodEncyclopediaBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return foodEncyclopediaBean;
    }

    public static EatTestBean parseEatTest(String json){

        EatTestBean eatTestBean = null;
        try {
            eatTestBean = new Gson().fromJson(json, EatTestBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return eatTestBean;
    }

    public static FoodDetailsBean parseFoodDetails(String json){

        FoodDetailsBean foodDetailsBean = null;
        try {
            foodDetailsBean = new Gson().fromJson(json, FoodDetailsBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return foodDetailsBean;

    }

    public static SearchFoodBean parseSearchFood(String json){
        SearchFoodBean foodBean = null;
        try {
            foodBean = new Gson().fromJson(json, SearchFoodBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return foodBean;
    }

    public static HomeSearchBean parseHomeSearch(String json){
        HomeSearchBean searchBean = null;
        try {
            searchBean = new Gson().fromJson(json, HomeSearchBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return searchBean;
    }

    /**
     * 比较食物的json解析
     * @param json
     * @return
     */
    public static CompeletFoodBean parseCompeletFoodBean(String json){
        CompeletFoodBean compeletFoodBean = null;
        try {
            compeletFoodBean = new Gson().fromJson(json, CompeletFoodBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return compeletFoodBean;
    }

    public static EatHomeFoodBean parseEatHomeFoodBean(String json) {

        EatHomeFoodBean eatHomeFoodBean = null;
        try {
            eatHomeFoodBean = JSON.parseObject(json, EatHomeFoodBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        EatHomeFoodBean eatHomeFoodBean1 = new Gson().fromJson(json, EatHomeFoodBean.class);
        return eatHomeFoodBean;

    }

    public static EatHomeWebBean parseEatHomeWebBean(String json){
        EatHomeWebBean eatHomeWebBean = null;
        try {
            eatHomeWebBean =JSON.parseObject(json,EatHomeWebBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return eatHomeWebBean;
    }

    /**
     * 对“逛吃 -- 知识”数据进行解析
     * @param json
     * @return
     */
    public static EatKnowBean parseEatKnowBean(String json){
        EatKnowBean eatKnowBean = null;
        if (json != null){
            eatKnowBean = new Gson().fromJson(json,EatKnowBean.class);
        }
        return eatKnowBean;
    }

    /**
     * 对“逛吃 --- 美食” 的数据进行解析
     * @param json
     * @return
     */
    public static EatFoodBean parseEatFoodBean(String json){
        EatFoodBean eatFoodBean = null;
        if (json != null){
            eatFoodBean = new Gson().fromJson(json,EatFoodBean.class);
        }
        return eatFoodBean;
    }

    /**
     * 解析 营养详情页的 Json数据
     * @param json
     * @return
     */
    public static FoodMainDetailBean parseFoodMainDetailBean(String json){
        FoodMainDetailBean foodMainDetailBean = null;
        if (json != null){
            foodMainDetailBean = new Gson().fromJson(json,FoodMainDetailBean.class);
        }
        return foodMainDetailBean;
    }
}
