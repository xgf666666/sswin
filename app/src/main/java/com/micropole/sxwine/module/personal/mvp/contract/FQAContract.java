package com.micropole.sxwine.module.personal.mvp.contract;

import com.example.mvpframe.BaseMvpView;
import com.micropole.sxwine.module.personal.Bean.FQAEntity;
import com.micropole.sxwine.utils.network.HttpObserver;

import java.util.List;

/**
 * Created by JohnnyH on 2018/6/12.
 */

public class FQAContract {

    public interface View extends BaseMvpView {

        void setData(List<FQAEntity> data);

        void onDataFailure(String err,boolean isFirstLoading);
    }

    public interface Presenter {

        void loadData(boolean isRefresh,boolean isFirstLoading);
    }

    public interface Model {
        void loadData(HttpObserver<List<FQAEntity>> httpObserver);
    }
}
