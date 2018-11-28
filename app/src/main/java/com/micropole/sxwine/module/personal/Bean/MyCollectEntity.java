package com.micropole.sxwine.module.personal.Bean;

import java.util.List;

/**
 * Created by JohnnyH on 2018/6/12.
 */

public class MyCollectEntity {

    /**
     * collect_id : 3
     * user_id : 20
     * goods_id : 3
     * created_at : 1527130353
     * updated_at : 1527130353
     * goods : {"goods_id":"3","goods_name":"美白霜","mall_price":"28888.00","shop_price":"48888.00","introduce":"用完后绝对不变黑","cate_id":"3","cover_img":"/public/uploads/meibai.jpg","goods_img":[],"decp":"美白美白霜霜霜","sold_count":"400","comment_count":"300","is_hot":"1"}
     */

    private String collect_id;
    private String user_id;
    private int goods_id;
    private String created_at;
    private String updated_at;
    private GoodsBean goods;

    public String getCollect_id() {
        return collect_id;
    }

    public void setCollect_id(String collect_id) {
        this.collect_id = collect_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public GoodsBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsBean goods) {
        this.goods = goods;
    }

    public static class GoodsBean {
        /**
         * goods_id : 3
         * goods_name : 美白霜
         * mall_price : 28888.00
         * shop_price : 48888.00
         * introduce : 用完后绝对不变黑
         * cate_id : 3
         * cover_img : /public/uploads/meibai.jpg
         * goods_img : []
         * decp : 美白美白霜霜霜
         * sold_count : 400
         * comment_count : 300
         * is_hot : 1
         */

        private String goods_id;
        private String goods_name;
        private String mall_price;
        private String shop_price;
        private String introduce;
        private String cate_id;
        private String cover_img;
        private String decp;
        private String sold_count;
        private String comment_count;
        private String is_hot;
        private List<?> goods_img;

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getMall_price() {
            return mall_price;
        }

        public void setMall_price(String mall_price) {
            this.mall_price = mall_price;
        }

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getCate_id() {
            return cate_id;
        }

        public void setCate_id(String cate_id) {
            this.cate_id = cate_id;
        }

        public String getCover_img() {
            return cover_img;
        }

        public void setCover_img(String cover_img) {
            this.cover_img = cover_img;
        }

        public String getDecp() {
            return decp;
        }

        public void setDecp(String decp) {
            this.decp = decp;
        }

        public String getSold_count() {
            return sold_count;
        }

        public void setSold_count(String sold_count) {
            this.sold_count = sold_count;
        }

        public String getComment_count() {
            return comment_count;
        }

        public void setComment_count(String comment_count) {
            this.comment_count = comment_count;
        }

        public String getIs_hot() {
            return is_hot;
        }

        public void setIs_hot(String is_hot) {
            this.is_hot = is_hot;
        }

        public List<?> getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(List<?> goods_img) {
            this.goods_img = goods_img;
        }
    }
}
