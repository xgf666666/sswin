package com.micropole.sxwine.module.personal.Bean;

/**
 * Created by JohnnyH on 2018/10/11.
 */

public class BankRecordEntity {

    /**
     * bank_id : 3
     * user_id : 20
     * account : 62256821212124526623
     * card_holder : 杜小帅
     * bank_name : 马来西亚国家银行
     * swift_code : 465789
     * bank_address : 马来西亚群岛火山顶
     */

    private String bank_id;
    private String user_id;
    private String account;
    private String card_holder;
    private String bank_name;
    private String swift_code;
    private String bank_address;

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getSwift_code() {
        return swift_code;
    }

    public void setSwift_code(String swift_code) {
        this.swift_code = swift_code;
    }

    public String getBank_address() {
        return bank_address;
    }

    public void setBank_address(String bank_address) {
        this.bank_address = bank_address;
    }
}
