package com.micropole.sxwine.module.personal.Bean;

/**
 * Created by JohnnyH on 2018/6/20.
 */

public class OrderStatusEntity {

    /**
     * unpay : 0
     * wait_send : 1
     * sending : 1
     */

    private String unpay;
    private String wait_send;
    private String sending;

    public String getUnpay() {
        return unpay;
    }

    public void setUnpay(String unpay) {
        this.unpay = unpay;
    }

    public String getWait_send() {
        return wait_send;
    }

    public void setWait_send(String wait_send) {
        this.wait_send = wait_send;
    }

    public String getSending() {
        return sending;
    }

    public void setSending(String sending) {
        this.sending = sending;
    }
}
