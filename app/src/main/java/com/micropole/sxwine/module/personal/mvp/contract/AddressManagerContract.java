package com.micropole.sxwine.module.personal.mvp.contract;

import com.example.mvpframe.BaseMvpView;
import com.micropole.sxwine.module.personal.Bean.AddressManagerEntity;
import com.micropole.sxwine.utils.network.HttpObserver;

import java.util.List;

/**
 * Created by JohnnyH on 2018/6/8.
 */

public class AddressManagerContract {

    public interface View extends BaseMvpView{

        void setData(List<AddressManagerEntity> data);

        void addData(List<AddressManagerEntity> data);

        void onDataFailure(boolean isRefresh,String err);

        void onDeleteAddressSuccess();

        void onDeleteAddressFailure(String err);

    }

    public interface Presenter {

        void loadData(boolean isRefresh,String page,String per_page);

        void deleteAddress(String address_id);
    }

    public interface Model {

        void loadData(String page, String per_page, HttpObserver<List<AddressManagerEntity>> httpObserver);

        void deleteAddress(String address_id,HttpObserver<Object> httpObserver);
    }
}
