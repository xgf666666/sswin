package com.micropole.sxwine.module.home.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.bean.HomeResult
import com.micropole.sxwine.bean.BannerBean
import com.micropole.sxwine.bean.ClassifyBean
import com.micropole.sxwine.module.home.mvp.contract.HomeContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver


/**
 * Description:
 * Created by DarkHorse on 2018/6/1.
 */
class HomeModel : HomeContract.Model {
    override fun getBanner(httpObserver: HttpObserver<ArrayList<BannerBean>>) {
        API.service.getBanner()
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }

    override fun getClassify(httpObserver: HttpObserver<ArrayList<ClassifyBean>>) {
        API.service.getHomeClassify()
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }

    override fun getGoods(page: Int, httpObserver: HttpObserver<HomeResult>) {
        API.service.getGoods(page = page, type = "1", sort = "sold_count")
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }
}
