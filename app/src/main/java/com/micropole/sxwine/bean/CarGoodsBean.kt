package com.micropole.sxwine.bean

import com.google.gson.annotations.SerializedName

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
