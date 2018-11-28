package com.micropole.sxwine.module.personal.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.module.personal.Bean.CommentGoodsListEntity
import com.micropole.sxwine.module.personal.mvp.contract.CommentGoodsListContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/9/7.
 */
class CommentGoodsListModel : CommentGoodsListContract.Model {
    override fun loadData(order_id: String?, httpObserver: HttpObserver<List<CommentGoodsListEntity>>?) {
        API.service.getCommentGoodsList(order_id)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }
}