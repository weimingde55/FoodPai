package com.mobilephone.foodpai.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2016/11/3.
 */
public class UserBean extends BmobUser {

    private String userCover;
    private String nikename;
    private String gender;
    private String age;
    private String tall;
    
    public String getUserCover() {
        return userCover;
    }

    public void setUserCover(String userCover) {
        this.userCover = userCover;
    }

    public String getNikename() {
        return nikename;
    }

    public void setNikename(String nikename) {
        this.nikename = nikename;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTall() {
        return tall;
    }

    public void setTall(String tall) {
        this.tall = tall;
    }
}
