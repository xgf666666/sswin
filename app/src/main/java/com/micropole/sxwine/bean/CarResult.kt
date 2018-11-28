package com.micropole.sxwine.bean

import com.google.gson.annotations.SerializedName


/**
 * Description:
 * Created by DarkHorse on 2018/9/6.
 */

data class CarResult(
        @SerializedName("cart") var cart: List<Cart>,
        @SerializedName("recommend_list") var recommendList: ArrayList<Recommend>
) {
    data class Recommend(
            @SerializedName("goods_id") var goodsId: String,
            @SerializedName("goods_name") var goodsName: String,
            @SerializedName("en_goods_name") var enGoodsName: String,
            @SerializedName("mall_price") var mallPrice: String,
            @SerializedName("shop_price") var shopPrice: String,
            @SerializedName("cover_img") var coverImg: String,
            @SerializedName("sold_count") var soldCount: String
    )

    data class Cart(
            @SerializedName("cart_id") var cartId: String,
            @SerializedName("goods_id") var goodsId: String,
            @SerializedName("quantity") var quantity: String,
            @SerializedName("goods") var goods: Goods
    ) {
        data class Goods(
                @SerializedName("goods_id") var goodsId: String,
                @SerializedName("goods_name") var goodsName: String,
                @SerializedName("mall_price") var mallPrice: String,
                @SerializedName("shop_price") var shopPrice: String,
                @SerializedName("introduce") var introduce: String,
                @SerializedName("cover_img") var coverImg: String,
                @SerializedName("sold_count") var soldCount: String,
                @SerializedName("en_goods_name") var enGoodsName: String,
                @SerializedName("en_introduce") var enIntroduce: String,
                @SerializedName("en_decp") var enDecp: String,
                @SerializedName("thumb_img") var thumb_img: String
        )
    }
}
