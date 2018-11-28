package com.micropole.sxwine.module.personal.mvp.contract;

import com.example.mvpframe.BaseMvpView;
import com.micropole.sxwine.module.personal.Bean.MyWalletEntity;
import com.micropole.sxwine.utils.network.HttpObserver;

/**
 * Created by JohnnyH on 2018/6/13.
 */

public class MyWalletContract {

    public interface View extends BaseMvpView {
        void setData(MyWalletEntity data);
        void onDataFailure(String err);
    }

    public interface Presenter {
        void loadData();
    }

    public interface Model {
        void loadData(HttpObserver<MyWalletEntity> httpObserver);
    }
}
