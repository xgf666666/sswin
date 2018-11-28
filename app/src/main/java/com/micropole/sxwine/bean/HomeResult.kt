package com.micropole.sxwine.bean

/**
 * Description:
 * Created by DarkHorse on 2018/6/1.
 */
data class HomeResult(
        val activity: ArrayList<ActivityBean>,
        val goods: ArrayList<GoodsBean>,
        val vip_goods: ArrayList<VipGoodsBean>
)
