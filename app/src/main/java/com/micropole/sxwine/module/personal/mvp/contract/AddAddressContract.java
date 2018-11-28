package com.micropole.sxwine.module.personal.mvp.contract;

import com.example.mvpframe.BaseMvpView;
import com.micropole.sxwine.module.personal.Bean.AddAddressEntity;
import com.micropole.sxwine.utils.network.HttpObserver;

/**
 * Created by JohnnyH on 2018/6/8.
 */

public class AddAddressContract {

    public interface View extends BaseMvpView{

        void onAddAddressSuccess();

        void onAddAddressFailure(String err);

        void onCompileAddressSuccess();

        void onCompileAddressFailure(String err);
    }

    public interface Presenter{

        void addAddress(String mobile,String receiver,String address_detail,String is_default);

        void compileAddress(String mobile,String receiver,String address_detail,String is_default,String address_id);
    }

    public interface Model{
        void addAddress(String mobile, String receiver, String address_detail, String is_default,
                        HttpObserver<AddAddressEntity> httpObserver);

        void compileAddress(String mobile,String receiver,String address_detail,String is_default,String address_id,
                            HttpObserver<AddAddressEntity> httpObserver);
    }
}
