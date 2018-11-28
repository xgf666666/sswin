package com.micropole.sxwine.module.personal.Bean;

/**
 * Created by JohnnyH on 2018/6/13.
 */

public class AccumulateEarningsEntity {

    /**
     * directly_amount_total : +3333.33
     * consume_amount_total : +77778.55
     * organization_amount_total : +44444.55
     */

    private String directly_amount_total;
    private String consume_amount_total;
    private String organization_amount_total;

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

    public String getOrganization_amount_total() {
        return organization_amount_total;
    }

    public void setOrganization_amount_total(String organization_amount_total) {
        this.organization_amount_total = organization_amount_total;
    }
}
