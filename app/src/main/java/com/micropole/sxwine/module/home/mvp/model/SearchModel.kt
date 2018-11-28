package com.micropole.sxwine.module.home.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.bean.HomeResult
import com.micropole.sxwine.module.home.mvp.contract.SearchContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/6/8.
 */
class SearchModel : SearchContract.Model {
    override fun searchGoods(keyword: String, page: Int, sort: String, direction: String, httpObserver: HttpObserver<HomeResult>) {
        API.service.getGoods(keyword = keyword, page = page, sort = sort, direction = direction)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }
}
