package com.micropole.sxwine.module.personal.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.base.toast
import com.micropole.sxwine.module.personal.Bean.UploadPriceEntity
import com.micropole.sxwine.module.personal.mvp.contract.PersonalInfoContract
import com.micropole.sxwine.module.personal.mvp.model.PersonalInfoModel
import com.micropole.sxwine.utils.network.HttpObserver
import java.io.File

/**
 * Created by JohnnyH on 2018/6/14.
 */
class PersonalInfoPresenter : BaseMvpPresenter<PersonalInfoContract.Model,PersonalInfoContract.View>(),PersonalInfoContract.Presenter {

    override fun uploadAvatar(file: File?) {
        mModel.uploadAvatar(file,object : HttpObserver<UploadPriceEntity>(){
            override fun onSuccess(bean: UploadPriceEntity, msg: String) {
                mModel.alterAvatar(bean.file_path,object : HttpObserver<UploadPriceEntity>(){
                    override fun onSuccess(bean: UploadPriceEntity, msg: String) {
                        toast(msg)
                        mView?.onUploadAvatarSuccess(bean)
                    }

                    override fun onFailure(code: String, msg: String) {
                        mView?.onUploadAvatarFailure(msg)
                    }

                })

            }

            override fun onFailure(code: String, msg: String) {
                mView?.onUploadAvatarFailure(msg)
            }

        })
    }

    override fun alterSex(sex: String?) {
        mModel.alterSex(sex,object : HttpObserver<Any>(){
            override fun onSuccess(bean: Any, msg: String) {
                mView?.onAlterSexSuccess(msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onAlterSexFailure(msg)
            }

        })
    }

    override fun createModel(): PersonalInfoContract.Model = PersonalInfoModel()
}