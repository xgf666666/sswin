package com.micropole.sxwine.module.personal.Bean;

/**
 * Created by JohnnyH on 2018/6/20.
 */

public class EarningsWithdrawEntity {
    /**
     * account : 62256821212124526623
     * user_id : 38
     * type : MOL
     * amount : 500
     * updated_at : 1528185938
     * created_at : 1528185938
     * withdraw_id : 8
     */

    private String account;
    private String user_id;
    private String type;
    private String amount;
    private String updated_at;
    private String created_at;
    private String withdraw_id;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getWithdraw_id() {
        return withdraw_id;
    }

    public void setWithdraw_id(String withdraw_id) {
        this.withdraw_id = withdraw_id;
    }
}
