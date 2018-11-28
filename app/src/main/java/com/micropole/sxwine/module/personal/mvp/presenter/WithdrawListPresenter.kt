package com.micropole.sxwine.module.personal.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.module.personal.Bean.WithdrawListEntity
import com.micropole.sxwine.module.personal.mvp.contract.WithdrawListContract
import com.micropole.sxwine.module.personal.mvp.model.WithdrawListModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/14.
 */
class WithdrawListPresenter : BaseMvpPresenter<WithdrawListContract.Model,WithdrawListContract.View>(),WithdrawListContract.Presenter {

    override fun createModel(): WithdrawListContract.Model = WithdrawListModel()

    override fun loadData(isRefresh: Boolean, isFirstLoading: Boolean, page: String?) {
        mModel.loadData(page,object : HttpObserver<List<WithdrawListEntity>>(){
            override fun onSuccess(bean: List<WithdrawListEntity>, msg: String) {
                if (isRefresh){
                    mView?.setData(bean)
                }else{
                    mView?.addData(bean)
                }
            }

            override fun onFailure(code: String, msg: String) {
               mView?.onDataFailure(msg,isFirstLoading)
            }

        })
    }
}