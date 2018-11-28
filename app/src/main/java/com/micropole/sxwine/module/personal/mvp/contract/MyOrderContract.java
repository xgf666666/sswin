package com.micropole.sxwine.module.personal.mvp.contract;

import com.example.mvpframe.BaseMvpView;
import com.micropole.sxwine.module.personal.Bean.MyOrderItemEntity;
import com.micropole.sxwine.utils.network.HttpObserver;

import java.util.List;

/**
 * Created by JohnnyH on 2018/6/15.
 */

public class MyOrderContract {
    public interface View extends BaseMvpView {
        void setData(List<MyOrderItemEntity> data);
        void addData(List<MyOrderItemEntity> data);
        void onDataFailure(String err, boolean isFirstLoading);

        void onDeleteOrderSuccess();
        void onDeleteOrderFailure(String err);

        void onConfirmReceiveSuccess();
        void onConfirmReceiveFailure(String err);

        void onCancelOrderSuccess();
        void onCancelOrderFailure(String err);
    }

    public interface Presenter {
        void loadData(boolean isRefresh, boolean isFirstLoading, String page, String per_page, String order_status);

        void deleteOrder(String order_id);

        void confirmReceive(String order_id);

        void cancelOrder(String order_id);
    }

    public interface Model {
        void loadData(String page, String per_page, String order_status, HttpObserver<List<MyOrderItemEntity>> httpObserver);

        void deleteOrder(String order_id,HttpObserver<Object> httpObserver);

        void confirmReceive(String order_id,HttpObserver<Object> httpObserver);

        void cancelOrder(String order_id,HttpObserver<Object> httpObserver);
    }
}
