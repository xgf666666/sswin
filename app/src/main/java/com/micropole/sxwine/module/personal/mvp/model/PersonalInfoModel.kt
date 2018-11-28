package com.micropole.sxwine.module.personal.mvp.model

import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.module.personal.Bean.UploadPriceEntity
import com.micropole.sxwine.module.personal.mvp.contract.PersonalInfoContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * Created by JohnnyH on 2018/6/14.
 */
class PersonalInfoModel : PersonalInfoContract.Model {
    override fun alterAvatar(new_avatar: String?, httpObserver: HttpObserver<UploadPriceEntity>?) {
        API.service.alterAvatar(new_avatar)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }

    override fun uploadAvatar(file: File?, httpObserver: HttpObserver<UploadPriceEntity>?) {
        val type = RequestBody.create(MultipartBody.FORM, "app_user_avatar")
        val requestBody = RequestBody.create(MultipartBody.FORM, file)
        val newfile = MultipartBody.Part.createFormData("newfile", file!!.name, requestBody)
        API.service.uploadPrice(newfile,type)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }

    override fun alterSex(sex: String?, httpObserver: HttpObserver<Any>?) {
        API.service.alterSex(sex)
                .compose(RxTransformer.io_main())
                .subscribe(httpObserver!!)
    }
}