package com.micropole.sxwine.module.login.mvp.contract;

import com.example.mvpframe.BaseMvpView;
import com.micropole.sxwine.module.login.bean.UserProtocolEntity;
import com.micropole.sxwine.utils.network.HttpObserver;

/**
 * Created by JohnnyH on 2018/9/17.
 */

public class UserProtocolContract {

    public interface View extends BaseMvpView{
        void onGetProtocolSuccess(UserProtocolEntity userProtocolEntity);
        void onGetProtocolFailure();

    }

    public interface Presenter{
        void getProtocol();
    }

    public interface Model {
        void getProtocol(HttpObserver<UserProtocolEntity> httpObserver);
    }
}
