package com.micropole.sxwine.module.personal.Bean;

/**
 * Created by JohnnyH on 2018/6/14.
 */

public class OrganizationEarningsEntity {


    /**
     * commission_amount : +12.50
     * created_at : 2018-06-04
     * updated_at : 2018-06-04
     */

    private String commission_amount;
    private String created_at;
    private String updated_at;

    public String getCommission_amount() {
        return commission_amount;
    }

    public void setCommission_amount(String commission_amount) {
        this.commission_amount = commission_amount;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
