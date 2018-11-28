package com.micropole.sxwine.module.personal.Bean;

/**
 * Created by JohnnyH on 2018/6/11.
 */

public class UserInfoEntity {

    /**
     * username : 15975605510
     * nickname : 猴赛利
     * mobile : 15975605510
     * user_wallet : 0.00
     * commission_total : 0
     * freeze_amount : 0.00
     * invite_code : 10020
     * vip_id : 2
     * subordinate_quota : 0
     * sex : 1
     * avatar : /uploads/user_imgs/20180524/2018-05-24-09-44-35-5b0689830c9d0.jpg
     * vip_name : 二级会员
     */

    private String username;
    private String nickname;
    private String mobile;
    private String user_wallet;
    private String commission_total;
    private String freeze_amount;
    private String invite_code;
    private String vip_id;
    private String subordinate_quota;
    private String sex;
    private String avatar;
    private String vip_name;

    public String getMessage_code() {
        return message_code;
    }

    public void setMessage_code(String message_code) {
        this.message_code = message_code;
    }

    private String message_code;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUser_wallet() {
        return user_wallet;
    }

    public void setUser_wallet(String user_wallet) {
        this.user_wallet = user_wallet;
    }

    public String getCommission_total() {
        return commission_total;
    }

    public void setCommission_total(String commission_total) {
        this.commission_total = commission_total;
    }

    public String getFreeze_amount() {
        return freeze_amount;
    }

    public void setFreeze_amount(String freeze_amount) {
        this.freeze_amount = freeze_amount;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getVip_id() {
        return vip_id;
    }

    public void setVip_id(String vip_id) {
        this.vip_id = vip_id;
    }

    public String getSubordinate_quota() {
        return subordinate_quota;
    }

    public void setSubordinate_quota(String subordinate_quota) {
        this.subordinate_quota = subordinate_quota;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getVip_name() {
        return vip_name;
    }

    public void setVip_name(String vip_name) {
        this.vip_name = vip_name;
    }
}
