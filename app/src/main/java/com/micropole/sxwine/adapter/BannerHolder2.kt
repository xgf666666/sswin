package com.micropole.sxwine.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bigkoo.convenientbanner.holder.Holder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.micropole.sxwine.R
import com.micropole.sxwine.base.loadImg
import com.micropole.sxwine.utils.network.API

/**
 * Description:
 * Created by DarkHorse on 2018/5/30.
 */
class BannerHolder2 : Holder<String> {

    private var imageView: ImageView? = null

    override fun UpdateUI(context: Context, position: Int, bean: String) {
        imageView!!.loadImg(context, API.HOST + bean, R.mipmap.zhaoone, transform = CenterCrop())
    }


    override fun createView(context: Context?): View {
        imageView = ImageView(context)
        imageView!!.scaleType = ImageView.ScaleType.CENTER_CROP
        return imageView as ImageView
    }

}
