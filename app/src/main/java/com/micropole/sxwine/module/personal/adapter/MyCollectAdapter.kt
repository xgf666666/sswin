package com.micropole.sxwine.module.personal.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.sxwine.R
import com.micropole.sxwine.base.loadImg
import com.micropole.sxwine.module.personal.Bean.MyCollectEntity
import com.micropole.sxwine.utils.network.API

/**
 * Created by JohnnyH on 2018/6/12.
 */
class MyCollectAdapter(resLayoutId : Int,data : List<MyCollectEntity>?) : BaseQuickAdapter<MyCollectEntity,BaseViewHolder>(resLayoutId,data) {

    override fun convert(helper: BaseViewHolder?, item: MyCollectEntity?) {
        val iv_goods = helper!!.getView<ImageView>(R.id.iv_goods)
        iv_goods.loadImg(mContext,API.HOST+item!!.goods.cover_img)
        helper.setText(R.id.tv_name,item.goods.goods_name)
        helper.setText(R.id.tv_price,"RMB"+item.goods.mall_price)
        helper.setText(R.id.tv_old_price,mContext.getString(R.string.shoppe_price)+" RMB"+item.goods.shop_price)
        helper.setText(R.id.tv_buy,item.goods.sold_count+mContext.getString(R.string.hadSale))
    }
}