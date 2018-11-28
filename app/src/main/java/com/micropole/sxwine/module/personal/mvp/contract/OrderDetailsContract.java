package com.micropole.sxwine.module.personal.mvp.contract;

import com.example.mvpframe.BaseMvpView;
import com.micropole.sxwine.module.personal.Bean.OrderDetailsEntity;
import com.micropole.sxwine.utils.network.HttpObserver;

/**
 * Created by JohnnyH on 2018/6/19.
 */

public class OrderDetailsContract {

    public interface View extends BaseMvpView {
        void setData(OrderDetailsEntity data);
        void onDataFailure(String err,boolean isFirstLoading);

        void onConfirmReceiveSuccess();
        void onConfirmReceiveFailure(String err);

        void onCancelOrderSuccess();
        void onCancelOrderFailure(String err);

        void onDeleteOrderSuccess();
        void onDeleteOrderFailure(String err);
    }

    public interface Presenter{
        void loadData(boolean isFirstLoading,String order_id);

        void confirmReceive(String order_id);

        void cancelOrder(String order_id);

        void deleteOrder(String order_id);
    }

    public interface Model{
        void loadData(String order_id, HttpObserver<OrderDetailsEntity> httpObserver);

        void confirmReceive(String order_id,HttpObserver<Object> httpObserver);

        void cancelOrder(String order_id,HttpObserver<Object> httpObserver);

        void deleteOrder(String order_id,HttpObserver<Object> httpObserver);
    }
}
