package com.micropole.sxwine.module.personal.mvp.contract;

import com.example.mvpframe.BaseMvpView;
import com.micropole.sxwine.utils.network.HttpObserver;

/**
 * Created by JohnnyH on 2018/6/15.
 */

public class SettingNicknameContract {

    public interface View extends BaseMvpView {
        void onAlterNicknameSuccess(String msg);
        void onAlterNicknameFailure(String err);
    }

    public interface Presenter {
        void alterNickname(String nickname);
    }

    public interface Model {
        void alterNickname(String nickname, HttpObserver<Object> httpObserver);
    }
}
