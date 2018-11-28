package com.micropole.sxwine.bean

import java.io.Serializable

/**
 * Description:
 * Created by DarkHorse on 2018/6/1.
 */
data class GoodsBean(
        var goods_id: String,
        var goods_name: String,
        var cover_img: String,
        var goods_img: ArrayList<String>?,
        var mall_price: String,
        var shop_price: String,
        var introduce: String?,
        var decp: String?,
        var sold_count: String,
        var comment_count: String?,
        var is_hot: String?,
        var activity_id: String,
        var video_url: String,
        var video_cover: String,
        var video_id: String,
        var thumb_img: String
) : Serializable