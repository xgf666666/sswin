package com.micropole.sxwine.module.personal.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.module.personal.Bean.CommentGoodsListEntity
import com.micropole.sxwine.module.personal.mvp.contract.CommentGoodsListContract
import com.micropole.sxwine.module.personal.mvp.model.CommentGoodsListModel
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/9/7.
 */
class CommentGoodsListPresenter : BaseMvpPresenter<CommentGoodsListContract.Model,CommentGoodsListContract.View>(),CommentGoodsListContract.Presenter {
    override fun loadData(order_id: String?) {
        mModel.loadData(order_id,object : HttpObserver<List<CommentGoodsListEntity>>(){
            override fun onSuccess(bean: List<CommentGoodsListEntity>, msg: String) {
                mView?.setData(bean)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onDataFailure(msg)
            }

        })
    }

    override fun createModel(): CommentGoodsListContract.Model = CommentGoodsListModel()
}