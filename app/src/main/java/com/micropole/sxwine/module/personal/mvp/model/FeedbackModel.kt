package com.micropole.sxwine.module.personal.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.module.personal.mvp.contract.FeedbackContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver

/**
 * Created by JohnnyH on 2018/6/15.
 */
class FeedbackModel : FeedbackContract.Model {
    override fun feedback(content: String?, httpObserver: HttpObserver<Any>?) {
        API.service.feedback(content)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }
}