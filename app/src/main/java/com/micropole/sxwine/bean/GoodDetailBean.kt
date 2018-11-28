package com.micropole.sxwine.bean
import com.google.gson.annotations.SerializedName


/**
 * Description:
 * Created by DarkHorse on 2018/6/4.
 */

data class GoodDetailBean(
    @SerializedName("goods_id") var goodsId: String,
    @SerializedName("goods_name") var goodsName: String,
    @SerializedName("mall_price") var mallPrice: String,
    @SerializedName("shop_price") var shopPrice: String,
    @SerializedName("is_hot") var isHot: String,
    @SerializedName("cover_img") var coverImg: String,
    @SerializedName("thumb_img") var thumb_img: String,
    @SerializedName("goods_img") var goodsImg: List<String>,
    @SerializedName("sold_count") var soldCount: String,
    @SerializedName("comment_count") var commentCount: String,
    @SerializedName("decp") var decp: String,
    @SerializedName("introduce") var introduce: String,
    @SerializedName("en_goods_name") var enGoodsName: String,
    @SerializedName("en_introduce") var enIntroduce: String,
    @SerializedName("en_decp") var enDecp: String,
    @SerializedName("vip_id") var vipId: String,
    @SerializedName("stock") var stock: String,
    @SerializedName("recommend_goods") var recommendGoods: ArrayList<RecommendGood>,
    @SerializedName("comments") var comments: List<Comment>,
    @SerializedName("collect") var collect: String
){
    data class RecommendGood(
            @SerializedName("goods_id") var goodsId: String,
            @SerializedName("goods_name") var goodsName: String,
            @SerializedName("mall_price") var mallPrice: String,
            @SerializedName("shop_price") var shopPrice: String,
            @SerializedName("is_hot") var isHot: String,
            @SerializedName("cover_img") var coverImg: String,
            @SerializedName("goods_img") var goodsImg: ArrayList<String>,
            @SerializedName("sold_count") var soldCount: String,
            @SerializedName("comment_count") var commentCount: String,
            @SerializedName("decp") var decp: String,
            @SerializedName("introduce") var introduce: String,
            @SerializedName("en_goods_name") var enGoodsName: String,
            @SerializedName("en_introduce") var enIntroduce: String,
            @SerializedName("en_decp") var enDecp: String,
            @SerializedName("vip_id") var vipId: String,
            @SerializedName("stock") var stock: String
    )

    data class Comment(
            @SerializedName("comment_id") var commentId: String,
            @SerializedName("photos") var photos: List<String>,
            @SerializedName("score") var score: Int,
            @SerializedName("content") var content: String,
            @SerializedName("goods_id") var goodsId: String,
            @SerializedName("user_id") var userId: String,
            @SerializedName("nickname") var nickname: String,
            @SerializedName("avatar") var avatar: String
    )
}

