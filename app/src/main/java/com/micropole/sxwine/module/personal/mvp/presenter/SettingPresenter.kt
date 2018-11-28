package com.micropole.sxwine.module.personal.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.module.personal.mvp.contract.SettingContract
import com.micropole.sxwine.module.personal.mvp.model.SettingModel

/**
 * Created by JohnnyH on 2018/6/8.
 */
class SettingPresenter : BaseMvpPresenter<SettingContract.Model,SettingContract.View>(),SettingContract.Presenter {
    override fun createModel(): SettingContract.Model = SettingModel()
}