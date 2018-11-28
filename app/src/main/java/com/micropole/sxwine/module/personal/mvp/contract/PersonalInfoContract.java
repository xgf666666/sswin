package com.micropole.sxwine.module.personal.mvp.contract;

import com.example.mvpframe.BaseMvpView;
import com.micropole.sxwine.module.personal.Bean.UploadPriceEntity;
import com.micropole.sxwine.utils.network.HttpObserver;

import java.io.File;

/**
 * Created by JohnnyH on 2018/6/14.
 */

public class PersonalInfoContract {

    public interface View extends BaseMvpView {
        void onAlterSexSuccess(String msg);
        void onAlterSexFailure(String err);

        void onUploadAvatarSuccess(UploadPriceEntity data);
        void onUploadAvatarFailure(String err);
    }

    public interface Presenter {
        void alterSex(String sex);
        void uploadAvatar(File file);
    }

    public interface Model {
        void alterSex(String sex, HttpObserver<Object> httpObserver);
        void uploadAvatar(File file,HttpObserver<UploadPriceEntity> httpObserver);
        void alterAvatar(String new_avatar,HttpObserver<UploadPriceEntity> httpObserver);
    }
}
