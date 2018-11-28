package com.micropole.sxwine.module.personal.mvp.contract;

import com.example.mvpframe.BaseMvpView;
import com.micropole.sxwine.module.personal.Bean.OrganizationEarningsEntity;
import com.micropole.sxwine.utils.network.HttpObserver;

import java.util.List;

/**
 * Created by JohnnyH on 2018/6/14.
 */

public class OrganizationEarningsContract {
    public interface View extends BaseMvpView{
        void setData(List<OrganizationEarningsEntity> data);
        void addData(List<OrganizationEarningsEntity> data);
        void onDataFailure(String err,boolean isFirstLoading);
    }

    public interface Presenter {
        void loadData(boolean isRefresh,boolean isFirstLoading,String page,String per_page,String type);
    }

    public interface Model {
        void loadData(String page, String per_page,String type, HttpObserver<List<OrganizationEarningsEntity>> httpObserver);
    }
}
