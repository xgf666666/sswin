package com.micropole.sxwine.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.sxwine.R
import com.micropole.sxwine.base.loadImg
import com.micropole.sxwine.bean.CarGoodsBean2
import com.micropole.sxwine.utils.network.API

/**
 * Description:
 * Created by DarkHorse on 2018/6/6.
 */
class ConfirmGoodsAdapter(layoutId: Int, data: ArrayList<CarGoodsBean2>) : BaseQuickAdapter<CarGoodsBean2, BaseViewHolder>(layoutId, data) {

    override fun convert(helper: BaseViewHolder, item: CarGoodsBean2) {
        helper.getView<ImageView>(R.id.iv_icon).loadImg(mContext, API.HOST + item.thumbImg)
        helper.setText(R.id.tv_name, item.goodsName)
        helper.setText(R.id.tv_price, "RMB ${item.mallPrice}")
        helper.setText(R.id.tv_count, "x" + item.quantity)
    }


}
