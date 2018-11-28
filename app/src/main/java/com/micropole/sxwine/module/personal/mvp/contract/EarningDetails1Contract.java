package com.micropole.sxwine.module.personal.mvp.contract;

import com.example.mvpframe.BaseMvpView;
import com.micropole.sxwine.module.personal.Bean.AccumulateEarningsEntity;
import com.micropole.sxwine.module.personal.Bean.EarningsDetails1Entity;
import com.micropole.sxwine.utils.network.HttpObserver;

/**
 * Created by JohnnyH on 2018/6/13.
 */

public class EarningDetails1Contract {

    public interface View extends BaseMvpView{
        void setData(EarningsDetails1Entity data);
        void setAccumulateEarnings(AccumulateEarningsEntity data);
        void onDataFailure(String err);
    }

    public interface Presenter {
        void loadData(String type);
        void getAccumulateEarnings();
    }

    public interface Model {
        void loadData(String type, HttpObserver<EarningsDetails1Entity> httpObserver);
        void getAccumulateEarnings(HttpObserver<AccumulateEarningsEntity> httpObserver);
    }
}
