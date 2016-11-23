package com.mobilephone.foodpai.bean;

import java.util.Map;

/**
 * Created by Administrator on 2016/11/2.
 */
public class SearchHistoryBean {

    public Map<String, String> stringMap;

    public SearchHistoryBean(Map<String, String> stringMap) {
        this.stringMap = stringMap;
    }

    public Map<String, String> getStringMap() {
        return stringMap;
    }

    public void setStringMap(Map<String, String> stringMap) {
        this.stringMap = stringMap;
    }

}
