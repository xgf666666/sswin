package com.micropole.sxwine.bean;

/**
 * author: xiaoguagnfei
 * date: 2018/11/30
 * describe:
 */
public class AwPayBean {

    /**
     * status : 1
     * data : alipay_sdk=lokielse%2Fomnipay-alipay&app_id=2018032602450457&biz_content=%7B%22subject%22%3A%22%5Cu4e09%5Cu9999%5Cu4e5d%22%2C%22out_trade_no%22%3A%22201811291626448908%22%2C%22total_amount%22%3A1%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=JSON&method=alipay.trade.app.pay¬ify_url=http%3A%2F%2Fsxj.goodbooy.cn%2Fapi%2Falipay%2ForderReturn&sign_type=RSA2×tamp=2018-11-29+17%3A44%3A03&version=1.0&sign=EZDmUbm%2FK3NyTF%2F6my4pviyZn7%2Fv7RsGCSB9CnLrVYkpXBmfgIkA5S82QYC1nmi8KALjRpNzvBlfqvJBvcFuYSWkSspDRShmpANfahyGxAZ4VNOJDblFXmJvvWi86oY4ASBxrgu27N0xQsRvzCZyKWsh%2BQQMismRyp1Wf6p5A8%2FIDYZ8l3fEH6haW2yKdZuOtw%2BVfpDRhpmKr1x6nj1UCdgrITJFBNUJu9pNvZ5eMI8akZPA2sqV5nSpwb3e820e6eR9bmvpgD20kXWyjSy8616AAvib8cM%2FYPlTssJ8ytCiOCGmz61%2B%2FWKc83iNIjBANNNlo7PeZd1L9qrkumtaDw%3D%3D
     */

    private int status;
    private String data;
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
