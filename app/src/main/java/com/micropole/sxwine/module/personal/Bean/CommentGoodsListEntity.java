package com.micropole.sxwine.module.personal.Bean;

import java.util.List;

/**
 * Created by JohnnyH on 2018/9/7.
 */

public class CommentGoodsListEntity {

    /**
     * order_id : 544
     * goods_id : 4
     * quantity : 1
     * goods_price : 1.00
     * is_commented : 0
     * goods : [{"goods_id":"4","goods_name":"一级会员美白霜","cover_img":"/uploads/goods/20180703/2018-07-03-09-18-53-5b3acefd696e1.jpg"}]
     */

    private String order_id;
    private String goods_id;
    private String quantity;
    private String goods_price;
    private String is_commented;
    private List<GoodsBean> goods;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getIs_commented() {
        return is_commented;
    }

    public void setIs_commented(String is_commented) {
        this.is_commented = is_commented;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class GoodsBean {
        /**
         * goods_id : 4
         * goods_name : 一级会员美白霜
         * cover_img : /uploads/goods/20180703/2018-07-03-09-18-53-5b3acefd696e1.jpg
         */

        private String goods_id;
        private String goods_name;
        private String cover_img;

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

        public String getCover_img() {
            return cover_img;
        }

        public void setCover_img(String cover_img) {
            this.cover_img = cover_img;
        }
    }
}
