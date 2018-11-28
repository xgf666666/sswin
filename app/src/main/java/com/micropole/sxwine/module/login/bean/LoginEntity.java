package com.micropole.sxwine.module.login.bean;

/**
 * Created by JohnnyH on 2018/6/6.
 */

public class LoginEntity {

    /**
     * token : {"sign_login":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxNywibG9naW5fdGltZSI6MTUyNzA2NDE1N30.GjpqdkXMYUq0g0NMU2POYTkKn7lHdQQrMpS665wbtYE","sign_api":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxNywic2hvcnRfdGltZSI6MTUyNzA2NDE1N30.wTvZCRJbSku0cGWW8QGufJ2GQTSIwpVq0qP4LXj2sz4"}
     */

    private TokenBean token;

    public TokenBean getToken() {
        return token;
    }

    public void setToken(TokenBean token) {
        this.token = token;
    }

    public static class TokenBean {
        /**
         * sign_login : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxNywibG9naW5fdGltZSI6MTUyNzA2NDE1N30.GjpqdkXMYUq0g0NMU2POYTkKn7lHdQQrMpS665wbtYE
         * sign_api : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxNywic2hvcnRfdGltZSI6MTUyNzA2NDE1N30.wTvZCRJbSku0cGWW8QGufJ2GQTSIwpVq0qP4LXj2sz4
         */

        private String sign_login;
        private String sign_api;

        public String getSign_login() {
            return sign_login;
        }

        public void setSign_login(String sign_login) {
            this.sign_login = sign_login;
        }

        public String getSign_api() {
            return sign_api;
        }

        public void setSign_api(String sign_api) {
            this.sign_api = sign_api;
        }
    }
}
