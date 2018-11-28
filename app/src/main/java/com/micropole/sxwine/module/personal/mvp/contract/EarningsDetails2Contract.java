package com.micropole.sxwine.module.personal.mvp.contract;

import com.example.mvpframe.BaseMvpView;
import com.micropole.sxwine.module.personal.Bean.EarningsDetails2Entity;
import com.micropole.sxwine.utils.network.HttpObserver;

import java.util.List;

/**
 * Created by JohnnyH on 2018/6/13.
 */

public class EarningsDetails2Contract {
    public interface View extends BaseMvpView{
        void setData(List<EarningsDetails2Entity> data);
        void addData(List<EarningsDetails2Entity> data);
        void onDataFailure(String err,boolean isFirstLoading);
    }

    public interface Presenter{
        void loadData(boolean isRefresh,boolean isFirstLoading,String type,String page,String per_page);
    }

    public interface Model{
        void loadData(String type, String page, String per_page, HttpObserver<List<EarningsDetails2Entity>>httpObserver);
    }
}
