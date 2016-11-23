package com.mobilephone.foodpai.bean;

import java.util.List;

/**
 * Created by qf on 2016/11/2.
 */
public class SearchFoodResultBean {
    /**
     * page : 1
     * total_pages : 7
     * tags : [{"type":"tags","name":"瓜果类水果"}]
     * items : [{"id":626,"code":"xigua_junzhi","name":"西瓜","thumb_image_url":"http://s.boohee.cn/house/food_mid/mid_photo_201513115340626.jpg","is_liquid":false,"health_light":1,"weight":"100","calory":"26","type":"food"},{"id":1630,"code":"xiaoxigua","name":"小西瓜","thumb_image_url":"http://s.boohee.cn/house/upload_food/2008/6/12/user_23474_1213284485.jpg","is_liquid":false,"health_light":1,"weight":"100","calory":"29","type":"food"},{"id":668,"code":"xiguazi_chao","name":"西瓜子(炒)","thumb_image_url":"http://s.boohee.cn/house/food_mid/m_1160658523375.jpg","is_liquid":false,"health_light":1,"weight":"100","calory":"582","type":"food"},{"id":1652,"code":"xiguazi_shu","name":"西瓜子（熟）","thumb_image_url":"http://s.boohee.cn/house/upload_food/2008/6/12/user_23474_1213285319.jpg","is_liquid":false,"health_light":3,"weight":"100","calory":"532","type":"food"},{"id":627,"code":"xigua_jingxinyihao","name":"西瓜(京欣一号)","thumb_image_url":"http://s.boohee.cn/house/food_mid/mid_photo_2010224627.jpg","is_liquid":false,"health_light":1,"weight":"100","calory":"34","type":"food"},{"id":670,"code":"xiguaziren","name":"西瓜子仁","thumb_image_url":"http://s.boohee.cn/house/food_mid/mid_photo_2010428161052670.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"566","type":"food"},{"id":669,"code":"xiguazi_huamei","name":"西瓜子(话梅)","thumb_image_url":"http://s.boohee.cn/house/food_mid/m_huameixiguazi34.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"567","type":"food"},{"id":628,"code":"xigua_zhengzhousanhao","name":"西瓜(郑州三号)","thumb_image_url":"http://s.boohee.cn/house/food_mid/m_1160658507609.jpg","is_liquid":false,"health_light":1,"weight":"100","calory":"26","type":"food"},{"id":1335,"code":"xiguafu","name":"西瓜脯","thumb_image_url":"http://s.boohee.cn/house/food_mid/m_guopu.jpg","is_liquid":false,"health_light":1,"weight":"100","calory":"311","type":"food"},{"id":8730,"code":"xiguaji","name":"西瓜鸡","thumb_image_url":"http://s.boohee.cn/house/upload_food/2008/6/21/user_mid_36507_1214027764.jpg","is_liquid":false,"health_light":1,"weight":"100","calory":"45","type":"food"},{"id":629,"code":"xigua_zhongyu6hao_heipi","name":"西瓜(忠于6号，黑皮)","thumb_image_url":"http://s.boohee.cn/house/food_mid/m_heimeiren.jpg","is_liquid":false,"health_light":1,"weight":"100","calory":"32","type":"food"},{"id":9160,"code":"xiguachaodan","name":"西瓜炒蛋","thumb_image_url":"http://s.boohee.cn/house/upload_food/2016/10/27/mid_photo_url_b1c5ad0365494a2c919df7ff71e86018.jpg","is_liquid":false,"health_light":2,"weight":"100","calory":"88","type":"food"},{"id":131280,"code":"fd3ed7ba","name":"大徐 话梅西瓜子","thumb_image_url":"http://s.boohee.cn/house/upload_food/2016/4/11/mid_photo_url_2672d42b7eb503d92b7ea5c7c28b1f18.png","is_liquid":false,"health_light":3,"weight":"100","calory":"574","type":"food"},{"id":79947,"code":"fuzhongxianghixiguadoujiang_wu","name":"府中香 西瓜豆酱（五香味）","thumb_image_url":"http://s.boohee.cn/house/upload_food/2014/5/11/452803_1399818827mid.jpg","is_liquid":false,"health_light":3,"weight":"100","calory":"222","type":"food"},{"id":11322,"code":"bingtangshuijingxigua","name":"冰糖水晶西瓜","thumb_image_url":"http://s.boohee.cn/house/upload_food/2016/9/12/mid_photo_url_4855650ec9fa4f2b87d428855b577850.jpg","is_liquid":false,"health_light":1,"weight":"100","calory":"67","type":"food"},{"id":22806,"code":"xianzhaxiguazhi","name":"鲜榨西瓜汁","thumb_image_url":"http://s.boohee.cn/house/upload_food/2010/6/4/419856_1275613309mid.jpg","is_liquid":false,"health_light":1,"weight":"100","calory":"29","type":"food"},{"id":44031,"code":"qiaqiaxiaoerxiangxiguazi_naiyo","name":"洽洽小而香西瓜子（奶油味）","thumb_image_url":"http://s.boohee.cn/house/upload_food/2013/3/1/627491_1362116495mid.jpg","is_liquid":false,"health_light":3,"weight":"100","calory":"575","type":"food"},{"id":77739,"code":"quanfuyuanhiheiguazi","name":"全福元 黑瓜子","thumb_image_url":"http://s.boohee.cn/house/upload_food/2014/4/18/452803_1397797456mid.jpg","is_liquid":false,"health_light":3,"weight":"100","calory":"343","type":"food"},{"id":42481,"code":"alinhicuiyouxianghixiguazi","name":"阿林 脆又香 西瓜子","thumb_image_url":"http://s.boohee.cn/house/upload_food/2013/2/2/452803_1359804175mid.jpg","is_liquid":false,"health_light":3,"weight":"100","calory":"536","type":"food"},{"id":44590,"code":"hongyinghigancaoguazi","name":"鸿鹰 甘草瓜子","thumb_image_url":"http://s.boohee.cn/house/upload_food/2013/3/9/452803_1362798638mid.jpg","is_liquid":false,"health_light":1,"weight":"100","calory":"375","type":"food"}]
     */

    private int page;
    private int total_pages;
    /**
     * type : tags
     * name : 瓜果类水果
     */

    private List<TagsBean> tags;
    /**
     * id : 626
     * code : xigua_junzhi
     * name : 西瓜
     * thumb_image_url : http://s.boohee.cn/house/food_mid/mid_photo_201513115340626.jpg
     * is_liquid : false
     * health_light : 1
     * weight : 100
     * calory : 26
     * type : food
     */

    private List<ItemsBean> items;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class TagsBean {
        private String type;
        private String name;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class ItemsBean {
        private int id;
        private String code;
        private String name;
        private String thumb_image_url;
        private boolean is_liquid;
        private int health_light;
        private String weight;
        private String calory;
        private String type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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

        public boolean isIs_liquid() {
            return is_liquid;
        }

        public void setIs_liquid(boolean is_liquid) {
            this.is_liquid = is_liquid;
        }

        public int getHealth_light() {
            return health_light;
        }

        public void setHealth_light(int health_light) {
            this.health_light = health_light;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getCalory() {
            return calory;
        }

        public void setCalory(String calory) {
            this.calory = calory;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
