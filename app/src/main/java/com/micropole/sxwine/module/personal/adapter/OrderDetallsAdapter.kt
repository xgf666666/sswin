package com.micropole.sxwine.module.personal.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.sxwine.R
import com.micropole.sxwine.base.loadImg
import com.micropole.sxwine.module.personal.Bean.OrderDetailsEntity
import com.micropole.sxwine.utils.network.API

/**
 * Created by JohnnyH on 2018/6/19.
 */
class OrderDetallsAdapter(resLayoutId:Int, data:List<OrderDetailsEntity.ItemsBean>?) : BaseQuickAdapter<OrderDetailsEntity.ItemsBean, BaseViewHolder>(resLayoutId,data){

    override fun convert(helper: BaseViewHolder?, item: OrderDetailsEntity.ItemsBean?) {
        val iv_goods = helper!!.getView<ImageView>(R.id.iv_goods)
        iv_goods.loadImg(mContext, API.HOST +item!!.thumb_img)
        helper.setText(R.id.tv_goods_name,item.goods_name)
        helper.setText(R.id.tv_goods_price,"RMB"+item.goods_price)
        helper.setText(R.id.tv_goods_count,"x"+item.quantity)
    }
}