package com.mobilephone.foodpai.bean.bmobbean;

import com.mobilephone.foodpai.bean.UserBean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016-11-04.
 */
public class CollectBean extends BmobObject{

    private String Title;
    private String link;
    private String imgUrl;
    private String calory;
    private String code;
    private UserBean Collect;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCalory() {
        return calory;
    }

    public void setCalory(String calory) {
        this.calory = calory;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UserBean getCollect() {
        return Collect;
    }

    public void setCollect(UserBean collect) {
        Collect = collect;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
