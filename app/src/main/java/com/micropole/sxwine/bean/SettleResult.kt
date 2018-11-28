package com.micropole.sxwine.bean

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import com.google.gson.annotations.SerializedName


/**
 * Description:
 * Created by DarkHorse on 2018/6/7.
 */

data class SettleResult(
    @SerializedName("cart") val cart: List<CarGoodsBean2>,
    @SerializedName("goods_total_amount") val goodsTotalAmount: String,
    @SerializedName("express_fee") val expressFee: String,
    @SerializedName("order_amount") val orderAmount: String,
    @SerializedName("address") val address: AddressBean,
    @SerializedName("temp_id") val tempId: String
):Serializable
