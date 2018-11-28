package com.micropole.sxwine.module.personal.Bean;

import java.util.List;

/**
 * Created by JohnnyH on 2018/6/13.
 */

public class EarningsDetails2Entity {

    /**
     * date : 2018年第二季度(4月-6月)
     * record : [{"bonus_id":"24","user_id":"48","title":"2018年第二季度(4月-6月)","bonus_amount":"512.43","type":"最高销售业绩奖","created_at":"1528450465","updated_at":"1528450465"},{"bonus_id":"21","user_id":"48","title":"2018年第二季度(4月-6月)","bonus_amount":"854.06","type":"最高招募新人奖","created_at":"1528450465","updated_at":"1528450465"}]
     */

    private String date;
    private List<RecordBean> record;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<RecordBean> getRecord() {
        return record;
    }

    public void setRecord(List<RecordBean> record) {
        this.record = record;
    }

    public static class RecordBean {
        /**
         * bonus_id : 24
         * user_id : 48
         * title : 2018年第二季度(4月-6月)
         * bonus_amount : 512.43
         * type : 最高销售业绩奖
         * created_at : 1528450465
         * updated_at : 1528450465
         */

        private String bonus_id;
        private String user_id;
        private String title;
        private String bonus_amount;
        private String type;
        private String created_at;
        private String updated_at;

        public String getBonus_id() {
            return bonus_id;
        }

        public void setBonus_id(String bonus_id) {
            this.bonus_id = bonus_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBonus_amount() {
            return bonus_amount;
        }

        public void setBonus_amount(String bonus_amount) {
            this.bonus_amount = bonus_amount;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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
}
