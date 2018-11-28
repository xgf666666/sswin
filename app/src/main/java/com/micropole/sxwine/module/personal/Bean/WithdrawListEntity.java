package com.micropole.sxwine.module.personal.Bean;

import java.util.List;

/**
 * Created by JohnnyH on 2018/6/14.
 */

public class WithdrawListEntity {


    /**
     * date : 2018-08-13
     * record : [{"withdraw_id":"32","user_id":"20","amount":"-1000.00","reject_reason":"","status":"1","account":"62256821212124526623","card_holder":"杜小帅","bank_name":"马来西亚国家银行","created_at":"2018-08-13","updated_at":"2018-08-13"}]
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
         * withdraw_id : 32
         * user_id : 20
         * amount : -1000.00
         * reject_reason :
         * status : 1
         * account : 62256821212124526623
         * card_holder : 杜小帅
         * bank_name : 马来西亚国家银行
         * created_at : 2018-08-13
         * updated_at : 2018-08-13
         */

        private String withdraw_id;
        private String user_id;
        private String amount;
        private String reject_reason;
        private String status;
        private String account;
        private String card_holder;
        private String bank_name;
        private String created_at;
        private String updated_at;

        public String getWithdraw_id() {
            return withdraw_id;
        }

        public void setWithdraw_id(String withdraw_id) {
            this.withdraw_id = withdraw_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getReject_reason() {
            return reject_reason;
        }

        public void setReject_reason(String reject_reason) {
            this.reject_reason = reject_reason;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getCard_holder() {
            return card_holder;
        }

        public void setCard_holder(String card_holder) {
            this.card_holder = card_holder;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
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
