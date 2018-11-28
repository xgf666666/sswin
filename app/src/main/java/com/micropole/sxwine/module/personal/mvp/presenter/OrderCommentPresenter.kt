package com.micropole.sxwine.module.personal.mvp.presenter

import com.example.mvpframe.BaseMvpPresenter
import com.micropole.sxwine.module.personal.mvp.contract.OrderCommentContract
import com.micropole.sxwine.module.personal.mvp.model.OrderCommentModel
import com.micropole.sxwine.utils.network.HttpObserver
import java.util.*

/**
 * Created by JohnnyH on 2018/9/7.
 */
class OrderCommentPresenter : BaseMvpPresenter<OrderCommentContract.Model,OrderCommentContract.View>(),OrderCommentContract.Presetner {
    override fun submitComment(order_id: String?, goods_id: String?, score: String?, content: String?, photos : List<String>?) {
        mModel.submitComment(order_id,goods_id,score,content,photos,object : HttpObserver<Any>(){
            override fun onSuccess(bean: Any, msg: String) {
                mView?.onCommentSuccess(msg)
            }

            override fun onFailure(code: String, msg: String) {
                mView?.onCommentFailure(msg)
            }

        })
    }


    private val mCurrentImageLists = ArrayList<String>()

    override fun imageSelect(newImageLists: MutableList<String>?) {
        if (null == newImageLists || newImageLists.size == 0) {
            return
        }

        mCurrentImageLists.addAll(newImageLists)

        //刷新UI

        mView?.setImageResult(mCurrentImageLists)

    }

    override fun imageDelete(newImageLists: MutableList<String>?) {
        if (null == newImageLists) {
            return
        }
        mCurrentImageLists.clear()
        mCurrentImageLists.addAll(newImageLists)


        mView?.setImageResult(newImageLists)

    }


    override fun createModel(): OrderCommentContract.Model = OrderCommentModel()
}