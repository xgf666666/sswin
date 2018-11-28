package com.micropole.sxwine.module.personal.mvp.contract;

import com.example.mvpframe.BaseMvpView;
import com.micropole.sxwine.module.personal.Bean.MyCollectEntity;
import com.micropole.sxwine.utils.network.HttpObserver;

import java.util.List;

/**
 * Created by JohnnyH on 2018/6/12.
 */

public class MyCollectContract {

    public interface View extends BaseMvpView {

        void setData(List<MyCollectEntity> data);

        void addData(List<MyCollectEntity> data);

        void onDataFailure(String err,boolean isFirstLoading);
    }

    public interface Presenter{

        void loadData(boolean isRefresh,boolean isFirstLoading,String page,String per_page);
    }

    public interface  Model{
        void loadData(String page, String per_page, HttpObserver<List<MyCollectEntity>> httpObserver);
    }
}
