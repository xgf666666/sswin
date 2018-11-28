package com.micropole.sxwine.module.home.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.bean.ClassifyBean
import com.micropole.sxwine.module.home.mvp.contract.MoreClassifyContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/5/31.
 */
class MoreClassifyModel : MoreClassifyContract.Model {
    override fun getClassify(page: String, httpObserver: HttpObserver<ArrayList<ClassifyBean>>) {
        API.service.getMoreClassify(page)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }

}
