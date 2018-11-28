package com.micropole.sxwine.module.login.mvp.contract;

import com.example.mvpframe.BaseMvpView;
import com.micropole.sxwine.module.login.bean.LoginEntity;
import com.micropole.sxwine.module.personal.Bean.UserInfoEntity;
import com.micropole.sxwine.utils.network.HttpObserver;

/**
 * Created by JohnnyH on 2018/6/6.
 */

public class RegisterContract {

    public interface View extends BaseMvpView {

        void onSendCodeSuccess(String msg);

        void onSendCodeFailure(String err);

        void onRegisterSuccess();

        void onRegisterFailure(String err);
    }

    public interface Presenter{

        void sendCode(String mobile,String type,String message_code);

        void register(String mobile,String password,String password_confirmation,String verify_code,
                      String recommend_code,String credentials_type,String credentials_no,String real_name,
                      String message_code);

        void getUserInfo();
    }

    public interface Model{
        void sendCode(String mobile, String type, String message_code, HttpObserver<Object> httpObserver);

        void register(String mobile, String password, String password_confirmation, String verify_code
                , String recommend_code,String credentials_type,String credentials_no,String real_name,
                      String message_code, HttpObserver<LoginEntity> httpObserver);

        void getUserInfo(HttpObserver<UserInfoEntity> httpObserver);
    }
}