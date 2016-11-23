package com.mobilephone.foodpai.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */
public class EatHomeFoodBean {

    /**
     * feeds : [{"card_image":"http://one.boohee.cn/food/2016/10/16/C3B777A0-91AF-4623-84BC-F68CB4515541.jpg?imageView2/2/w/640","content_type":5,"description":"","item_id":7314,"like_ct":65,"publisher":"言言言言西早","publisher_avatar":"http://wx.qlogo.cn/mmopen/6KA0QCGAlFyyuUrfHc706G1E3WEqzPELYfib14IBq2PibW8UFGiaGFAdNAAwSKBhRd1MBnficMD4KvYsNsGTRZuy9W0Ss0PDnxQ8/0","title":"休息日早餐","type":"food_card"},{"card_image":"http://one.boohee.cn/food/2016/10/15/287A0CE4-7422-43F7-81EA-AF144CC6D6B3.jpg?imageView2/2/w/640","content_type":5,"description":"","item_id":7240,"like_ct":168,"publisher":"I.P","publisher_avatar":"http://one.boohee.cn/t/2016/9/27/E671FEE9-685A-4709-A0E4-A6E7E27C4AD2.jpg","title":"烤鸡胸","type":"food_card"},{"card_image":"http://one.boohee.cn/food/2016/10/15/F5DC2BF0-A19E-48DC-B0E8-6A512E9798A0.jpg?imageView2/2/w/640","content_type":5,"description":"","item_id":7242,"like_ct":195,"publisher":"limono","publisher_avatar":"http://one.boohee.cn/t/2016/9/27/FE3EF091-0033-4618-B058-CC22740A7C86.jpg","title":"蓝莓奶昔","type":"food_card"},{"card_image":"http://one.boohee.cn/food/2016/10/15/9AA37BC6-1D46-4515-92D1-7B9CCB4A9E3C.jpg?imageView2/2/w/640","content_type":5,"description":"","item_id":7238,"like_ct":66,"publisher":"团子菇凉","publisher_avatar":"http://one.boohee.cn/t/2016/9/21/6151F6F2-268F-4B52-88CD-871D59D7F00A.jpg","title":"荞麦面","type":"food_card"},{"card_image":"http://one.boohee.cn/food/2016/10/15/5FED8227-7A8E-470A-BC2F-76EBC00B9C60.jpg?imageView2/2/w/640","content_type":5,"description":"","item_id":7239,"like_ct":168,"publisher":"Maeil","publisher_avatar":"http://one.boohee.cn/t/2016/9/27/AA617932-A3D6-4143-8333-EA9315CDC793.jpg","title":"焗饭","type":"food_card"},{"card_image":"http://one.boohee.cn/food/2016/10/15/DA780FD6-C0E0-498E-B13C-CE0E9223ADAE.jpg?imageView2/2/w/640","content_type":5,"description":"无油煎鸡蛋 一根香蕉 一个猕猴桃 一瓣红柚😋🤗","item_id":7236,"like_ct":101,"publisher":"不减到100斤以下不改名字","publisher_avatar":"http://tva1.sinaimg.cn/crop.0.0.750.750.180/0064faNnjw8f3tfb4gki1j30ku0kuq55.jpg","title":"炒鸡蛋","type":"food_card"},{"card_image":"http://one.boohee.cn/food/2016/10/14/F9D4B549-A1D9-4DC2-9DD0-F87594F38670.jpg?imageView2/2/w/640","content_type":5,"description":"不喜欢人工糖，所以一直都用甜味果蔬当甜味剂，个人最爱用南瓜，煎出来的松饼又甜又软，酸奶是自制的希腊酸奶，没加糖，一点点酸提味\u2026\u2026","item_id":7185,"like_ct":120,"publisher":"炸鸡块","publisher_avatar":"http://one.boohee.cn/t/2016/10/22/8987BC9C-0DD8-4D02-A9AB-0723DFC9FE31.jpg","title":"南瓜全麦松饼","type":"food_card"},{"card_image":"http://one.boohee.cn/food/2016/10/13/A29577A1-089D-433B-B8C8-075574DCE7B0.jpg?imageView2/2/w/640","content_type":5,"description":"享受自己做的每一份早餐，第一次在这里传图，以后多更新还有很多往期早餐😋","item_id":7133,"like_ct":182,"publisher":"我是林春春","publisher_avatar":"http://one.boohee.cn/t/2016/9/26/B6EA0FE8-B494-4F88-BCB2-893152016A28.jpg","title":"减脂早餐","type":"food_card"},{"card_image":"http://one.boohee.cn/food/2016/10/13/D11B4AA6-36B5-43DD-AC5A-7D2A5FD63AA2.jpg?imageView2/2/w/640","content_type":5,"description":"自己带减脂午餐便当的日子","item_id":7134,"like_ct":183,"publisher":"我是林春春","publisher_avatar":"http://one.boohee.cn/t/2016/9/26/B6EA0FE8-B494-4F88-BCB2-893152016A28.jpg","title":"减脂午餐","type":"food_card"},{"card_image":"http://one.boohee.cn/food/2016/10/11/C1156700-FD97-4E59-B666-236CDF70005F.jpg?imageView2/2/w/640","content_type":5,"description":"","item_id":7041,"like_ct":55,"publisher":"小情绪..","publisher_avatar":"http://wx.qlogo.cn/mmopen/PiajxSqBRaEIibwzVXXnTox28hFWNaoQnaia6CGrDhr7NwsoRiadBgGwDeicicODY6wThrcoKWicVaTTt4AlCJ6BxGsVQ/0","title":"自制牛排","type":"food_card"}]
     * page : 2
     * total_pages : 195
     */

    private String page;
    private int total_pages;
    /**
     * card_image : http://one.boohee.cn/food/2016/10/16/C3B777A0-91AF-4623-84BC-F68CB4515541.jpg?imageView2/2/w/640
     * content_type : 5
     * description :
     * item_id : 7314
     * like_ct : 65
     * publisher : 言言言言西早
     * publisher_avatar : http://wx.qlogo.cn/mmopen/6KA0QCGAlFyyuUrfHc706G1E3WEqzPELYfib14IBq2PibW8UFGiaGFAdNAAwSKBhRd1MBnficMD4KvYsNsGTRZuy9W0Ss0PDnxQ8/0
     * title : 休息日早餐
     * type : food_card
     */

    private List<FeedsBean> feeds;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<FeedsBean> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<FeedsBean> feeds) {
        this.feeds = feeds;
    }

    public static class FeedsBean {
        private String card_image;
        private int content_type;
        private String description;
        private int item_id;
        private int like_ct;
        private String publisher;
        private String publisher_avatar;
        private String title;
        private String type;

        public String getCard_image() {
            return card_image;
        }

        public void setCard_image(String card_image) {
            this.card_image = card_image;
        }

        public int getContent_type() {
            return content_type;
        }

        public void setContent_type(int content_type) {
            this.content_type = content_type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getItem_id() {
            return item_id;
        }

        public void setItem_id(int item_id) {
            this.item_id = item_id;
        }

        public int getLike_ct() {
            return like_ct;
        }

        public void setLike_ct(int like_ct) {
            this.like_ct = like_ct;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getPublisher_avatar() {
            return publisher_avatar;
        }

        public void setPublisher_avatar(String publisher_avatar) {
            this.publisher_avatar = publisher_avatar;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
