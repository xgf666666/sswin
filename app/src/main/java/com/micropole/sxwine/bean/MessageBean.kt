package com.micropole.sxwine.bean

import com.google.gson.annotations.SerializedName


/**
 * Description:
 * Created by DarkHorse on 2018/9/6.
 */
data class MessageBean(
        @SerializedName("message_id") var messageId: String,
        @SerializedName("title") var title: String,
        @SerializedName("img") var img: String,
        @SerializedName("content") var content: String,
        @SerializedName("message_type") var messageType: String,
        @SerializedName("link") var link: String,
        @SerializedName("goods_id") var goodsId: String,
        @SerializedName("order_id") var orderId: String,
        @SerializedName("type") var type: String,
        @SerializedName("updated_at") var updatedAt: String
)
