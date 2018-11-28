package com.micropole.sxwine.module.personal.mvp.contract;

import com.example.mvpframe.BaseMvpView;
import com.micropole.sxwine.utils.network.HttpObserver;

/**
 * Created by JohnnyH on 2018/6/15.
 */

public class SettingLoginPwdContract {
    public interface View extends BaseMvpView{
        void onSendCodeSuccess(String msg);
        void onSendCodeFailure(String err);
        void onAlterOrSetLoginPwdSuccess();
        void onAlterOrSetLoginPwdFailure(String err);
    }

    public interface Presenter{
        void sendCode(String mobile,String type);
        void alterOrSetLoginPwd(String mobile,String password,String password_confirmation,String verify_code);
    }

    public interface Model {
        void sendCode(String mobile, String type, HttpObserver<Object> httpObserver);
        void alterOrSetLoginPwd(String mobile, String password, String password_confirmation,
                                String verify_code, HttpObserver<Object> httpObserver);
    }
}
