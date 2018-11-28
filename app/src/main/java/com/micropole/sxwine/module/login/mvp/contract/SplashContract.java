package com.micropole.sxwine.module.login.mvp.contract;

import com.example.mvpframe.BaseMvpView;
import com.micropole.sxwine.module.login.bean.ADEntity;
import com.micropole.sxwine.utils.network.HttpObserver;

/**
 * Created by JohnnyH on 2018/8/28.
 */

public class SplashContract {

    public interface View extends BaseMvpView {
        void onGetAdSuccess(ADEntity entity);
        void onGetAdFailure(String err);
    }

    public interface Presenter {
        void loadData();
    }

    public interface Model {
        void loadData(HttpObserver<ADEntity> httpObserver);
    }
}
