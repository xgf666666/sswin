package com.micropole.sxwine.module.personal.mvp.contract;

import com.example.mvpframe.BaseMvpView;
import com.micropole.sxwine.module.personal.Bean.WithdrawListEntity;
import com.micropole.sxwine.utils.network.HttpObserver;

import java.util.List;

/**
 * Created by JohnnyH on 2018/6/14.
 */

public class WithdrawListContract {

    public interface View extends BaseMvpView{
        void setData(List<WithdrawListEntity> data);
        void addData(List<WithdrawListEntity> data);
        void onDataFailure(String err,boolean isFirstLoading);
    }

    public interface Presenter {
        void loadData(boolean isRefresh,boolean isFirstLoading,String page);
    }

    public interface Model{
        void loadData(String page, HttpObserver<List<WithdrawListEntity>> httpObserver);
    }
}
