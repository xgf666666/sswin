package com.micropole.sxwine.module.personal.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.module.personal.Bean.ContactServiceEntity
import com.micropole.sxwine.module.personal.mvp.contract.ContactServiceContract
import com.micropole.sxwine.module.personal.mvp.model.ContactServiceModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/12.
 */
class ContactServicePresenter : BaseMvpPresenter<ContactServiceContract.Model,ContactServiceContract.View>(),ContactServiceContract.Presenter {

    override fun consumeService() {
        mModel.consumeService(object : HttpObserver<ContactServiceEntity>(){
            override fun onSuccess(bean: ContactServiceEntity, msg: String) {
                mView?.onConsumeServiceSuccess(bean)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onConsumeServiceFailure(msg)
            }

        })
    }

    override fun serviceOnline() {
        mModel.serviceOnline(object : HttpObserver<ContactServiceEntity>(){
            override fun onSuccess(bean: ContactServiceEntity, msg: String) {
                mView?.onServiceOnlineSucess(bean)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onServiceOnlineFailure(msg)
            }
        })
    }

    override fun createModel(): ContactServiceContract.Model = ContactServiceModel()
}