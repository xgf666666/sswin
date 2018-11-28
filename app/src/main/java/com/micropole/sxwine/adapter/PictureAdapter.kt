package com.micropole.sxwine.adapter

import android.content.Context
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.sxwine.R
import com.micropole.sxwine.base.loadImg
import com.micropole.sxwine.utils.network.API
import kotlinx.android.synthetic.main.rcv_commend_pic.view.*

/**
 * Description:
 * Created by DarkHorse on 2018/9/7.
 */
class PictureAdapter(private val context: Context, layoutId: Int) : BaseQuickAdapter<String, BaseViewHolder>(layoutId) {
    override fun convert(helper: BaseViewHolder, item: String) {
        helper.getView<ImageView>(R.id.iv_pic).loadImg(context, "${API.HOST}${item}")
    }
}
