package com.micropole.sxwine.module.personal.mvp.model

import android.text.TextUtils
import com.darkhorse.httphelper.transformer.RxTransformer
import com.micropole.sxwine.bean.BaseResponseBean
import com.micropole.sxwine.module.personal.Bean.UploadPriceEntity
import com.micropole.sxwine.module.personal.mvp.contract.OrderCommentContract
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.utils.network.HttpObserver
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * Created by JohnnyH on 2018/9/7.
 */
class OrderCommentModel :OrderCommentContract.Model {
    override fun submitComment(order_id: String?, goods_id: String?, score: String?, content: String?, img: List<String>?, httpObserver: HttpObserver<Any>?) {
        val  sb = StringBuffer()
        if (!img!!.isEmpty()){
            val type = RequestBody.create(MultipartBody.FORM, "app_goods_comment")
            val imgFile0 = File(img[0])
            val imageBody0 = RequestBody.create(MultipartBody.FORM, imgFile0)
            val image0 = MultipartBody.Part.createFormData("newfile", imgFile0.name, imageBody0)

            API.service.uploadPrice(image0,type)
                    .flatMap { pictureEntity ->

                        sb.append(pictureEntity.data!!.file_path)
                        if (img.size>1){
                            val imgFile1 = File(img[1])
                            val imageBody1 = RequestBody.create(MultipartBody.FORM, imgFile1)
                            val image1 = MultipartBody.Part.createFormData("newfile", imgFile1.name, imageBody1)
                            API.service.uploadPrice(image1,type)
                        }else{
                            //没有第二张,直接返回模拟数据
                            Observable.just(BaseResponseBean<UploadPriceEntity>("1", "1234","",UploadPriceEntity("")))
                        }

                    }
                    .flatMap { pictureEntity ->

                        sb.append(pictureEntity.data!!.file_path)
                        if (img.size>2){
                            val imgFile2 = File(img[2])
                            val imageBody2 = RequestBody.create(MultipartBody.FORM, imgFile2)
                            val image2 = MultipartBody.Part.createFormData("newfile", imgFile2.name, imageBody2)
                            API.service.uploadPrice(image2,type)
                        }else{
                            //没有第三张,直接返回模拟数据
                            Observable.just(BaseResponseBean<UploadPriceEntity>("1", "1234","",UploadPriceEntity("")))
                        }

                    }
                    .flatMap { pictureEntity ->

                        sb.append(pictureEntity.data!!.file_path)
                        if (img.size>3){
                            val imgFile3 = File(img[3])
                            val imageBody3 = RequestBody.create(MultipartBody.FORM, imgFile3)
                            val image3 = MultipartBody.Part.createFormData("newfile", imgFile3.name, imageBody3)
                            API.service.uploadPrice(image3,type)
                        }else{
                            //没有第四张,直接返回模拟数据
                            Observable.just(BaseResponseBean<UploadPriceEntity>("1", "1234","",UploadPriceEntity("")))
                        }

                    }
                    .flatMap { pictureEntity ->

                        sb.append(pictureEntity.data!!.file_path)
                        if (img.size>4){
                            val imgFile4 = File(img[4])
                            val imageBody4 = RequestBody.create(MultipartBody.FORM, imgFile4)
                            val image4 = MultipartBody.Part.createFormData("newfile", imgFile4.name, imageBody4)
                            API.service.uploadPrice(image4,type)
                        }else{
                            //没有第五张,直接返回模拟数据
                            Observable.just(BaseResponseBean<UploadPriceEntity>("1", "1234","",UploadPriceEntity("")))
                        }

                    }
                    .flatMap { pictureEntity ->

                        sb.append(pictureEntity.data!!.file_path)
                        if (img.size>5){
                            val imgFile5 = File(img[5])
                            val imageBody5 = RequestBody.create(MultipartBody.FORM, imgFile5)
                            val image5 = MultipartBody.Part.createFormData("newfile", imgFile5.name, imageBody5)
                            API.service.uploadPrice(image5,type)
                        }else{
                            //没有第六张,直接返回模拟数据
                            Observable.just(BaseResponseBean<UploadPriceEntity>("1", "1234","",UploadPriceEntity("")))
                        }

                    }
                    .flatMap { pictureEntity ->

                        sb.append(pictureEntity.data!!.file_path)
                        if (img.size>6){
                            val imgFile6 = File(img[6])
                            val imageBody6 = RequestBody.create(MultipartBody.FORM, imgFile6)
                            val image6 = MultipartBody.Part.createFormData("newfile", imgFile6.name, imageBody6)
                            API.service.uploadPrice(image6,type)
                        }else{
                            //没有第七张,直接返回模拟数据
                            Observable.just(BaseResponseBean<UploadPriceEntity>("1", "1234","",UploadPriceEntity("")))
                        }

                    }
                    .flatMap { pictureEntity ->

                        sb.append(pictureEntity.data!!.file_path)
                        if (img.size>7){
                            val imgFile7 = File(img[7])
                            val imageBody7 = RequestBody.create(MultipartBody.FORM, imgFile7)
                            val image7 = MultipartBody.Part.createFormData("newfile", imgFile7.name, imageBody7)
                            API.service.uploadPrice(image7,type)
                        }else{
                            //没有第八张,直接返回模拟数据
                            Observable.just(BaseResponseBean<UploadPriceEntity>("1", "1234","",UploadPriceEntity("")))
                        }

                    }
                    .flatMap { pictureEntity ->
                        if (!TextUtils.isEmpty(pictureEntity.data!!.file_path)){
                            sb.append(pictureEntity.data!!.file_path).append(",")
                            if (img.size>8){
                                val imgFile8 = File(img[8])
                                val imageBody8 = RequestBody.create(MultipartBody.FORM, imgFile8)
                                val image8 = MultipartBody.Part.createFormData("newfile", imgFile8.name, imageBody8)
                                API.service.uploadPrice(image8,type)
                            }else{
                                //没有第九张,直接返回模拟数据
                                Observable.just(BaseResponseBean<UploadPriceEntity>("1", "1234","",UploadPriceEntity("")))
                            }
                        }else{
                            //没有第三张,直接返回模拟数据
                            Observable.just(BaseResponseBean<UploadPriceEntity>("1", "1234","",UploadPriceEntity("")))
                        }
                    }
                    .flatMap { pictureEntity->
                        if (!TextUtils.isEmpty(pictureEntity.data!!.file_path)){
                            sb.append(pictureEntity.data!!.file_path)
                        }
                        API.service.orderGoodsComment(order_id,goods_id,score,content,sb.toString())
                    }
                    .compose(RxTransformer.io_main())
                    .subscribe(httpObserver!!)
        }else{
            API.service.orderGoodsComment(order_id,goods_id,score,content,null)
                    .compose(RxTransformer.io_main())
                    .subscribe(httpObserver!!)

        }
    }
}