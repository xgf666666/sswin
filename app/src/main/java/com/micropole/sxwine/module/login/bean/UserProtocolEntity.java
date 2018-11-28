package com.micropole.sxwine.module.login.bean;

/**
 * Created by JohnnyH on 2018/9/17.
 */

public class UserProtocolEntity {

    /**
     * protocol_title : 注册协议
     * protocol_value : <p>你必须同意aaaaa</p>
     * en_protocol_title : Register Protocol
     * en_protocol_value : <p>You must agree ddddd</p>
     */

    private String protocol_title;
    private String protocol_value;
    private String en_protocol_title;
    private String en_protocol_value;

    public String getProtocol_title() {
        return protocol_title;
    }

    public void setProtocol_title(String protocol_title) {
        this.protocol_title = protocol_title;
    }

    public String getProtocol_value() {
        return protocol_value;
    }

    public void setProtocol_value(String protocol_value) {
        this.protocol_value = protocol_value;
    }

    public String getEn_protocol_title() {
        return en_protocol_title;
    }

    public void setEn_protocol_title(String en_protocol_title) {
        this.en_protocol_title = en_protocol_title;
    }

    public String getEn_protocol_value() {
        return en_protocol_value;
    }

    public void setEn_protocol_value(String en_protocol_value) {
        this.en_protocol_value = en_protocol_value;
    }
}
