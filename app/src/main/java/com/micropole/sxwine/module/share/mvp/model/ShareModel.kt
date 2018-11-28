package com.micropole.sxwine.module.share.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.bean.ShareBean
import com.micropole.sxwine.module.share.mvp.contract.ShareContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Description:
 * Created by DarkHorse on 2018/6/22.
 */
class ShareModel : ShareContract.Model {
    override fun getShareData(httpObserver: HttpObserver<ShareBean>) {
        API.service.getShareData()
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }
}
