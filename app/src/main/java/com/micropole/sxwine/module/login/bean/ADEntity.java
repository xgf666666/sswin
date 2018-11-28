package com.micropole.sxwine.module.login.bean;

import java.io.Serializable;

/**
 * Created by JohnnyH on 2018/8/28.
 */

public class ADEntity implements Serializable{

    /**
     * ad_id : 5
     * title : 美白
     * en_title : meibai
     * img : /uploads/goods/20180827/2018-08-27-17-40-07-5b83c6f7084b1.png
     * link : #
     * goods_id : 1
     * type_id : 1
     * introduction :
     * en_introduction :
     */

    private String ad_id;
    private String title;
    private String en_title;
    private String img;
    private String link;
    private String goods_id;
    private String type_id;
    private String introduction;
    private String en_introduction;

    public String getAd_id() {
        return ad_id;
    }

    public void setAd_id(String ad_id) {
        this.ad_id = ad_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEn_title() {
        return en_title;
    }

    public void setEn_title(String en_title) {
        this.en_title = en_title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getEn_introduction() {
        return en_introduction;
    }

    public void setEn_introduction(String en_introduction) {
        this.en_introduction = en_introduction;
    }
}
