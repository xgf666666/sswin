package com.micropole.sxwine.bean;

import com.google.gson.annotations.SerializedName;

/**
 * author: xiaoguagnfei
 * date: 2018/11/30
 * describe:
 */
public class WxPayBean {

    /**
     * status : 1
     * data : {"appid":"wxe13c15b520e07f80","partnerid":"1501495561","prepayid":"wx30091557501478f267d1429a0515029800","package":"Sign=WXPay","noncestr":"961b339547740367c06b4df407cff6f6","timestamp":1543540557,"sign":"D4DE7FBBBD47FEF835FD8D9A51CE7556"}
     */

    private int status;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * appid : wxe13c15b520e07f80
         * partnerid : 1501495561
         * prepayid : wx30091557501478f267d1429a0515029800
         * package : Sign=WXPay
         * noncestr : 961b339547740367c06b4df407cff6f6
         * timestamp : 1543540557
         * sign : D4DE7FBBBD47FEF835FD8D9A51CE7556
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        @SerializedName("package")
        private String packageX;
        private String noncestr;
        private String timestamp;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
