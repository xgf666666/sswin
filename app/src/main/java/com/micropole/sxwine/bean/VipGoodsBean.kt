package com.micropole.sxwine.bean

/**
 * Description:
 * Created by DarkHorse on 2018/6/5.
 */
data class VipGoodsBean(

        var goods_id: String,
        var goods_name: String,
        var cover_img: String,
        var mall_price: String,
        var shop_price: String,
        var cate_id: String,
        var introduce: String,
        var decp: String,
        var sold_count: String,
        var comment_count: String,
        var is_hot: String,
        var video_url: String,
        var video_cover: String,
        var video_id: String,
        var goods_img: List<String>,
        var vip_id: String,
        var vip_level: String
)
