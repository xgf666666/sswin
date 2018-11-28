package com.micropole.sxwine.module.home.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.bean.ConfirmAddressResult
import com.micropole.sxwine.bean.GoodDetailBean
import com.micropole.sxwine.module.home.mvp.contract.CommendListContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/9/7.
 */
class CommendListModel : CommendListContract.Model {
    override fun getAllCommend(goods_id: Int,page:Int, httpObserver: HttpObserver<ArrayList<GoodDetailBean.Comment>>) {
        API.service.getCommentList(goods_id,page)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }
}
