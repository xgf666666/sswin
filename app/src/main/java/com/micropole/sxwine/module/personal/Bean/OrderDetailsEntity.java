package com.micropole.sxwine.module.personal.Bean;

import java.util.List;

/**
 * Created by JohnnyH on 2018/6/19.
 */

public class OrderDetailsEntity {

    /**
     * order_id : 10
     * order_sn : 20180528091755881390742
     * user_id : 20
     * order_amount : 238884.40
     * pay_amount : 238884.40
     * goods_total_amount : 0.00
     * delivery_amount : 8.88
     * express_no :
     * delivery_id : 11
     * status : 1
     * created_at : 2018-05-28 17:17:55
     * updated_at : 2018-05-28 17:17:55
     * items : [{"item_id":"5","order_id":"10","goods_id":"3","quantity":"5","goods_price":"28888.00","created_at":"1527499076","updated_at":"1527499076","goods_name":"美白霜","cover_img":"/uploads/goods/20180613/2018-06-13-15-30-50-5b20c82ad183a.png"},{"item_id":"6","order_id":"10","goods_id":"1","quantity":"5","goods_price":"18888.88","created_at":"1527499076","updated_at":"1527499076","goods_name":"24K美白乳液","cover_img":"/uploads/goods/20180613/2018-06-13-15-28-40-5b20c7a856c04.png"}]
     * address : {"delivery_id":"11","user_id":"20","receiver":"JackMa","mobile":"18620144793","delivery_address":"中国杭州","created_at":"1527499075","updated_at":"1527499075"}
     */

    private String order_id;
    private String order_sn;
    private String user_id;
    private String order_amount;
    private String pay_amount;
    private String goods_total_amount;
    private String delivery_amount;
    private String express_no;
    private String delivery_id;
    private String status;
    private String created_at;
    private String updated_at;
    private AddressBean address;
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

    public String getGoods_total_amount() {
        return goods_total_amount;
    }

    public void setGoods_total_amount(String goods_total_amount) {
        this.goods_total_amount = goods_total_amount;
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

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class AddressBean {
        /**
         * delivery_id : 11
         * user_id : 20
         * receiver : JackMa
         * mobile : 18620144793
         * delivery_address : 中国杭州
         * created_at : 1527499075
         * updated_at : 1527499075
         */

        private String delivery_id;
        private String user_id;
        private String receiver;
        private String mobile;
        private String delivery_address;
        private String created_at;
        private String updated_at;

        public String getDelivery_id() {
            return delivery_id;
        }

        public void setDelivery_id(String delivery_id) {
            this.delivery_id = delivery_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getDelivery_address() {
            return delivery_address;
        }

        public void setDelivery_address(String delivery_address) {
            this.delivery_address = delivery_address;
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
    }

    public static class ItemsBean {
        /**
         * item_id : 5
         * order_id : 10
         * goods_id : 3
         * quantity : 5
         * goods_price : 28888.00
         * created_at : 1527499076
         * updated_at : 1527499076
         * goods_name : 美白霜
         * cover_img : /uploads/goods/20180613/2018-06-13-15-30-50-5b20c82ad183a.png
         */

        private String item_id;
        private String order_id;
        private String goods_id;
        private String quantity;
        private String goods_price;
        private String created_at;
        private String updated_at;
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
