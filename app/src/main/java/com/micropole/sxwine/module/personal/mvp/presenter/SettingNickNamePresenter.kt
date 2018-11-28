package com.micropole.sxwine.module.personal.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.module.personal.mvp.contract.SettingNicknameContract
import com.micropole.sxwine.module.personal.mvp.model.SettingNicknameModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/15.
 */
class SettingNickNamePresenter : BaseMvpPresenter<SettingNicknameContract.Model,SettingNicknameContract.View>(),SettingNicknameContract.Presenter {

    override fun alterNickname(nickname: String?) {
        mModel.alterNickname(nickname,object : HttpObserver<Any>(){
            override fun onSuccess(bean: Any, msg: String) {
                mView?.onAlterNicknameSuccess(msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onAlterNicknameFailure(msg)
            }

        })
    }

    override fun createModel(): SettingNicknameContract.Model = SettingNicknameModel()
}