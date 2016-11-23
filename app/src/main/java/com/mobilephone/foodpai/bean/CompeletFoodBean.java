package com.mobilephone.foodpai.bean;

import java.util.List;

/**
 * Created by qf on 2016/11/4.
 */
public class CompeletFoodBean {
    /**
     * code : xigua_junzhi
     * name : 西瓜
     * thumb_image_url : http://s.boohee.cn/house/food_mid/mid_photo_201513115340626.jpg
     * large_image_url : http://s.boohee.cn/house/food_big/big_photo201513115340626.jpg
     * nutrition : [{"prop":"calory","name":"热量","value":"26.0","unit":"kcal","remark":"低热量"},{"prop":"protein","name":"蛋白质","value":"0.6","unit":"克","remark":""},{"prop":"fat","name":"脂肪","value":"0.1","unit":"克","remark":"低脂肪"},{"prop":"carbohydrate","name":"碳水化合物","value":"5.5","unit":"克","remark":""},{"prop":"fiber_dietary","name":"膳食纤维","value":"0.3","unit":"克","remark":""},{"prop":"gi","name":"GI","value":"72.0"},{"prop":"gl","name":"GL","value":"4.2"},{"prop":"vitamin_a","name":"维生素A","value":"75.0","unit":"μgRE"},{"prop":"vitamin_c","name":"维生素C(抗坏血酸)","value":"6.0","unit":"毫克"},{"prop":"vitamin_e","name":"维生素E","value":"0.1","unit":"mgRE"},{"prop":"carotene","name":"胡萝卜素","value":"450.0","unit":"微克"},{"prop":"thiamine","name":"维生素B1(硫胺素)","value":"0.0","unit":"毫克"},{"prop":"lactoflavin","name":"维生素B2(核黄素)","value":"0.0","unit":"毫克"},{"prop":"niacin","name":"烟酸(烟酰胺/尼克酸)","value":"0.2","unit":"毫克"},{"prop":"magnesium","name":"镁","value":"8.0","unit":"毫克"},{"prop":"calcium","name":"钙","value":"8.0","unit":"毫克"},{"prop":"iron","name":"铁","value":"0.3","unit":"毫克"},{"prop":"zinc","name":"锌","value":"0.1","unit":"毫克"},{"prop":"copper","name":"铜","value":"0.1","unit":"毫克"},{"prop":"manganese","name":"锰","value":"0.1","unit":"毫克"},{"prop":"kalium","name":"钾","value":"87.0","unit":"毫克"},{"prop":"phosphor","name":"磷","value":"9.0","unit":"毫克"},{"prop":"natrium","name":"钠","value":"3.2","unit":"毫克"},{"prop":"selenium","name":"硒","value":"0.2","unit":"微克"}]
     * type : food
     */

    private String code;
    private String name;
    private String thumb_image_url;
    private String large_image_url;
    private String type;
    /**
     * prop : calory
     * name : 热量
     * value : 26.0
     * unit : kcal
     * remark : 低热量
     */

    private List<NutritionBean> nutrition;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumb_image_url() {
        return thumb_image_url;
    }

    public void setThumb_image_url(String thumb_image_url) {
        this.thumb_image_url = thumb_image_url;
    }

    public String getLarge_image_url() {
        return large_image_url;
    }

    public void setLarge_image_url(String large_image_url) {
        this.large_image_url = large_image_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<NutritionBean> getNutrition() {
        return nutrition;
    }

    public void setNutrition(List<NutritionBean> nutrition) {
        this.nutrition = nutrition;
    }

    public static class NutritionBean {
        private String prop;
        private String name;
        private String value;
        private String unit;
        private String remark;

        public String getProp() {
            return prop;
        }

        public void setProp(String prop) {
            this.prop = prop;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
