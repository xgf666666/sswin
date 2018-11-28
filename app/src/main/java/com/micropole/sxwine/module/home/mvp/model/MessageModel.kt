package com.micropole.sxwine.module.home.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.bean.MessageBean
import com.micropole.sxwine.module.home.mvp.contract.MessageContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

import java.util.ArrayList

/**
 * Description:
 * Created by DarkHorse on 2018/9/6.
 */
class MessageModel : MessageContract.Model {
    override fun getMessageList(page: Int, httpObserver: HttpObserver<ArrayList<MessageBean>>) {
        API.service.getMessageList()
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver)
    }
}
