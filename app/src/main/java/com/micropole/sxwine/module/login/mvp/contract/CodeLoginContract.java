package com.micropole.sxwine.module.login.mvp.contract;


import com.example.mvpframe.BaseMvpView;
import com.micropole.sxwine.module.login.bean.LoginEntity;
import com.micropole.sxwine.module.personal.Bean.UserInfoEntity;
import com.micropole.sxwine.utils.network.HttpObserver;

/**
 * Created by JohnnyH on 2018/6/7.
 */

public class CodeLoginContract {

    public interface View extends BaseMvpView {

        void onSendCodeSuccess();

        void onSendCodeFailure(String err);

        void onLoginSuccess();

        void onLoginFailure(String err);

    }

    public interface Presenter {

        void sendCode(String mobile,String type,String message_code);

        void codeLogin(String mobile,String verify_code,String message_code);

        void getUserInfo();
    }

    public interface Model {

        void sendCode(String mobile,String type,String message_code,HttpObserver<Object> httpObserver);

        void codeLogin(String mobile, String verify_code, String message_code, HttpObserver<LoginEntity> httpObserver);

        void getUserInfo(HttpObserver<UserInfoEntity> httpObserver);
    }
}
