package com.micropole.sxwine.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Description:
 * Created by DarkHorse on 2018/6/6.
 */
data class CarGoodsBean(
        @SerializedName("cart_id") val cartId: String,
        @SerializedName("goods_id") val goodsId: String,
        @SerializedName("quantity") var quantity: String,
        @SerializedName("goods") val goods: GoodsBean,
        var isChecked: Boolean
)
