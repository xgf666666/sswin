package com.micropole.sxwine.module.personal.Bean;

/**
 * Created by JohnnyH on 2018/6/13.
 */

public class EarningsDetails1Entity {

    /**
     * id : 33
     * day_amount : 100.00
     * directly_amount : 50.00
     * form_amount : 20.00
     * repeat_amount : 30.00
     */

    private String id;
    private String day_amount;
    private String directly_amount;
    private String form_amount;
    private String repeat_amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDay_amount() {
        return day_amount;
    }

    public void setDay_amount(String day_amount) {
        this.day_amount = day_amount;
    }

    public String getDirectly_amount() {
        return directly_amount;
    }

    public void setDirectly_amount(String directly_amount) {
        this.directly_amount = directly_amount;
    }

    public String getForm_amount() {
        return form_amount;
    }

    public void setForm_amount(String form_amount) {
        this.form_amount = form_amount;
    }

    public String getRepeat_amount() {
        return repeat_amount;
    }

    public void setRepeat_amount(String repeat_amount) {
        this.repeat_amount = repeat_amount;
    }
}
