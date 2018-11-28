package com.micropole.sxwine.adapter

import android.support.v7.widget.CardView
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.sxwine.R
import com.micropole.sxwine.R.id.mBanner
import com.micropole.sxwine.base.loadImg
import com.micropole.sxwine.bean.GoodsBean
import com.micropole.sxwine.utils.network.API

/**
 * Description:
 * Created by DarkHorse on 2018/5/31.
 */
class GoodsAdapter(layoutId: Int, data: ArrayList<GoodsBean>) : BaseQuickAdapter<GoodsBean, BaseViewHolder>(layoutId, data) {

    override fun convert(helper: BaseViewHolder, item: GoodsBean) {
        val rootView = helper.getView<CardView>(R.id.item)
        val dm = mContext.resources.displayMetrics
        val width = dm.widthPixels
        val param = rootView.layoutParams
        param.height = (width * 0.4).toInt()
        rootView.layoutParams = param

        if ("" != item.video_url) {
            helper.getView<View>(R.id.rl_item).visibility = View.GONE
            helper.getView<View>(R.id.iv_play).visibility = View.VISIBLE
            helper.getView<View>(R.id.iv_cover).visibility = View.VISIBLE
            helper.getView<ImageView>(R.id.iv_bg).loadImg(mContext, API.HOST + item.video_cover, R.mipmap.zhaotwo, transform = CenterCrop())
        } else {
            helper.getView<View>(R.id.rl_item).visibility = View.VISIBLE
            helper.getView<View>(R.id.iv_play).visibility = View.GONE
            helper.getView<View>(R.id.iv_cover).visibility = View.GONE

            helper.getView<ImageView>(R.id.iv_bg).loadImg(mContext, API.HOST + item.cover_img, R.mipmap.zhaotwo, transform = CenterCrop())
            helper.setText(R.id.tv_detail, item.goods_name)
            helper.setText(R.id.tv_price_1, "RMB ${item.mall_price}")
            helper.setText(R.id.tv_price_2, mContext.resources.getString(R.string.shoppe_price)+" RMB ${item.shop_price}")
            helper.setText(R.id.tv_sale, item.sold_count + mContext.getString(R.string.hadSale))
        }
    }

}
