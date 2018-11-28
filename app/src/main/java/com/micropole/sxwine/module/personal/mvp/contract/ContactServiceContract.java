package com.micropole.sxwine.module.personal.mvp.contract;

import com.example.mvpframe.BaseMvpView;
import com.micropole.sxwine.module.personal.Bean.ContactServiceEntity;
import com.micropole.sxwine.utils.network.HttpObserver;

/**
 * Created by JohnnyH on 2018/6/12.
 */

public class ContactServiceContract {

    public interface View extends BaseMvpView {
        void onServiceOnlineSucess(ContactServiceEntity data);
        void onServiceOnlineFailure(String err);
        void onConsumeServiceSuccess(ContactServiceEntity data);
        void onConsumeServiceFailure(String err);
    }

    public interface Presenter {
        void serviceOnline();
        void consumeService();
    }

    public interface Model {
        void serviceOnline(HttpObserver<ContactServiceEntity> httpObserver);
        void consumeService(HttpObserver<ContactServiceEntity> httpObserver);
    }
}
