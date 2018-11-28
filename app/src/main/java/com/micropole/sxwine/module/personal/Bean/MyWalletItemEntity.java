package com.micropole.sxwine.module.personal.Bean;

import android.app.Activity;

/**
 * Created by JohnnyH on 2018/6/13.
 */

public class MyWalletItemEntity {
    private String type;
    private String title;
    private String price;
    private Class<? extends Activity> clazz;

    public MyWalletItemEntity(String type, String title, String price, Class<? extends Activity> clazz) {
        this.type = type;
        this.title = title;
        this.price = price;
        this.clazz = clazz;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Class<? extends Activity> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends Activity> clazz) {
        this.clazz = clazz;
    }
}
