package com.micropole.sxwine.adapter

import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.baseframe.utils.AppManager
import com.micropole.sxwine.R
import com.micropole.sxwine.R.id.mBanner
import com.micropole.sxwine.base.loadImg
import com.micropole.sxwine.bean.VipGoodsBean
import com.micropole.sxwine.utils.RCImageView
import com.micropole.sxwine.utils.network.API

/**
 * Description:
 * Created by DarkHorse on 2018/5/31.
 */
class VipGoodsAdapter(layoutId: Int, data: ArrayList<VipGoodsBean>) : BaseQuickAdapter<VipGoodsBean, BaseViewHolder>(layoutId, data) {

    override fun convert(helper: BaseViewHolder, item: VipGoodsBean) {
        val imageView = helper.getView<ImageView>(R.id.iv_activity) as RCImageView;
        imageView.layoutParams.height
        val param = imageView.layoutParams
        val dm = AppManager.currentActivity().resources.displayMetrics
        val width = dm.widthPixels
        param.height = width / 3
        imageView.layoutParams = param
        imageView.degree = 12

        if (item.video_url.isEmpty()) {
            imageView.loadImg(mContext, API.HOST + item.cover_img, R.mipmap.zhaoshao, transform = CenterCrop())

        } else {
            imageView.loadImg(mContext, API.HOST + item.video_cover, R.mipmap.zhaoshao, transform = CenterCrop())
        }
        if (item.vip_level == "1") {
            helper.setImageResource(R.id.iv_top, R.mipmap.p_one)
        } else {
            helper.setImageResource(R.id.iv_top, R.mipmap.p_two)
        }
    }

}
