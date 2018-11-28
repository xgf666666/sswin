package com.micropole.sxwine.bean

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import com.google.gson.annotations.SerializedName


/**
 * Description:
 * Created by DarkHorse on 2018/6/6.
 */


data class CarGoodsBean2(
        @SerializedName("cart_id") val cartId: String,
        @SerializedName("goods_id") val goodsId: String,
        @SerializedName("quantity") val quantity: String,
        @SerializedName("user_id") val userId: String,
        @SerializedName("goods_name") val goodsName: String,
        @SerializedName("mall_price") val mallPrice: String,
        @SerializedName("cover_img") val coverImg: String,
        @SerializedName("thumb_img") val thumbImg: String
) : Serializable