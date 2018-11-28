package com.micropole.sxwine.module.personal.Bean;

/**
 * Created by JohnnyH on 2018/6/13.
 */

public class MyWalletEntity {


    /**
     * today_commission : 0.00
     * yesterday_commission : 0.00
     * organization_amount_total : 1.00
     * directly_amount_total : 6.00
     * consume_amount_total : 0.90
     * commission_total : 7.90
     * user_wallet : 7.90
     */

    private String today_commission;
    private String yesterday_commission;
    private String organization_amount_total;
    private String directly_amount_total;
    private String consume_amount_total;
    private String commission_total;
    private String user_wallet;

    public String getToday_commission() {
        return today_commission;
    }

    public void setToday_commission(String today_commission) {
        this.today_commission = today_commission;
    }

    public String getYesterday_commission() {
        return yesterday_commission;
    }

    public void setYesterday_commission(String yesterday_commission) {
        this.yesterday_commission = yesterday_commission;
    }

    public String getOrganization_amount_total() {
        return organization_amount_total;
    }

    public void setOrganization_amount_total(String organization_amount_total) {
        this.organization_amount_total = organization_amount_total;
    }

    public String getDirectly_amount_total() {
        return directly_amount_total;
    }

    public void setDirectly_amount_total(String directly_amount_total) {
        this.directly_amount_total = directly_amount_total;
    }

    public String getConsume_amount_total() {
        return consume_amount_total;
    }

    public void setConsume_amount_total(String consume_amount_total) {
        this.consume_amount_total = consume_amount_total;
    }

    public String getCommission_total() {
        return commission_total;
    }

    public void setCommission_total(String commission_total) {
        this.commission_total = commission_total;
    }

    public String getUser_wallet() {
        return user_wallet;
    }

    public void setUser_wallet(String user_wallet) {
        this.user_wallet = user_wallet;
    }
}
