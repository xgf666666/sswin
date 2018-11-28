package com.micropole.sxwine.module.personal.Bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by JohnnyH on 2018/6/19.
 */

public class MyOrderItemEntity  implements MultiItemEntity {

    /**
     * order_id : 31
     * order_sn : 20180613113536318930685
     * user_id : 51
     * order_amount : 2208.88
     * pay_amount : 2208.88
     * delivery_amount : 8.88
     * express_no :
     * delivery_id : 32
     * status : 6
     * created_at : 1528860936
     * updated_at : 1528860936
     * items : [{"item_id":"36","order_id":"31","goods_id":"4","quantity":"10","goods_price":"220.00","goods_name":"一级会员美白霜","cover_img":"/uploads/goods_imgs/20180506/meibai.jpg"}]
     * limit_time :
     */

    private String order_id;
    private String order_sn;
    private String user_id;
    private String order_amount;
    private String pay_amount;
    private String delivery_amount;
    private String express_no;
    private String delivery_id;
    private String status;
    private String created_at;
    private String updated_at;
    private String limit_time;
    private List<ItemsBean> items;
    private String is_commented;
    private String self_pick;

    public String getSelf_pick() {
        return self_pick;
    }

    public void setSelf_pick(String self_pick) {
        this.self_pick = self_pick;
    }

    public String getIs_commented() {
        return is_commented;
    }

    public void setIs_commented(String is_commented) {
        this.is_commented = is_commented;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public String getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getDelivery_amount() {
        return delivery_amount;
    }

    public void setDelivery_amount(String delivery_amount) {
        this.delivery_amount = delivery_amount;
    }

    public String getExpress_no() {
        return express_no;
    }

    public void setExpress_no(String express_no) {
        this.express_no = express_no;
    }

    public String getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(String delivery_id) {
        this.delivery_id = delivery_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getLimit_time() {
        return limit_time;
    }

    public void setLimit_time(String limit_time) {
        this.limit_time = limit_time;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    @Override
    public int getItemType() {
        return Integer.valueOf(status);
    }

    public static class ItemsBean {
        /**
         * item_id : 36
         * order_id : 31
         * goods_id : 4
         * quantity : 10
         * goods_price : 220.00
         * goods_name : 一级会员美白霜
         * cover_img : /uploads/goods_imgs/20180506/meibai.jpg
         */

        private String item_id;
        private String order_id;
        private String goods_id;
        private String quantity;
        private String goods_price;
        private String goods_name;
        private String cover_img;

        public String getThumb_img() {
            return thumb_img;
        }

        public void setThumb_img(String thumb_img) {
            this.thumb_img = thumb_img;
        }

        private String thumb_img;

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

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
