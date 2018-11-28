package com.micropole.sxwine.module.personal.Bean;

/**
 * Created by JohnnyH on 2018/6/13.
 */

public class EarningsDetails1ItemEnitity {
    private String title;

    public EarningsDetails1ItemEnitity(String title, String price) {
        this.title = title;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private String price;
}
