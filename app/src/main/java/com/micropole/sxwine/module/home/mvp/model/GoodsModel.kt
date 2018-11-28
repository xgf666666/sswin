package com.micropole.sxwine.module.home.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.bean.HomeResult
import com.micropole.sxwine.module.home.mvp.contract.GoodsContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/6/4.
 */
class GoodsModel : GoodsContract.Model {
    override fun getGoods(category_id: String, page: Int, sort: String, direction: String, httpObserver: HttpObserver<HomeResult>) {
        API.service.getGoods(category_id = category_id, page = page, sort = sort, direction = direction)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }
}
