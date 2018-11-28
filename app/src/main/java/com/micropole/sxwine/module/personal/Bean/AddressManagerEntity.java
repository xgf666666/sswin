package com.micropole.sxwine.module.personal.Bean;

import java.io.Serializable;

/**
 * Created by JohnnyH on 2018/6/8.
 */

public class AddressManagerEntity implements Serializable {

    /**
     * address_id : 17
     * mobile : 18620144793
     * receiver : 乔布斯
     * address_detail : 美国纽约曼哈顿
     * is_default : 1
     */

    private String address_id;
    private String mobile;
    private String receiver;
    private String address_detail;
    private String is_default;

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAddress_detail() {
        return address_detail;
    }

    public void setAddress_detail(String address_detail) {
        this.address_detail = address_detail;
    }

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }
}
