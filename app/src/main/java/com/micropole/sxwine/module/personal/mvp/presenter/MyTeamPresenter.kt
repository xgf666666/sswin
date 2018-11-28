package com.micropole.sxwine.module.personal.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.bean.MyTeamResult
import com.micropole.sxwine.module.personal.mvp.contract.MyTeamContract
import com.micropole.sxwine.module.personal.mvp.model.MyTeamModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/12.
 */
class MyTeamPresenter : BaseMvpPresenter<MyTeamContract.Model, MyTeamContract.View>(), MyTeamContract.Presenter {
    override fun getData(type: Int,page:Int) {
        mModel.getData(type,page, object : HttpObserver<MyTeamResult>() {
            override fun onSuccess(bean: MyTeamResult, msg: String) {
                mView?.onSuccess(bean, msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onFailure(msg)
            }
        })
    }

    override fun createModel(): MyTeamContract.Model = MyTeamModel()
}