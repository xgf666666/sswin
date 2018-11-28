package com.micropole.sxwine.adapter

import android.content.Context
import android.widget.ImageView
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.sxwine.R
import com.micropole.sxwine.R.id.mBanner
import com.micropole.sxwine.base.loadImg
import com.micropole.sxwine.bean.MessageBean
import com.micropole.sxwine.utils.network.API
import java.text.DateFormat

/**
 * Description:
 * Created by DarkHorse on 2018/9/6.
 */
class MessageAdapter(private val context: Context, layoutId: Int) : BaseQuickAdapter<MessageBean, BaseViewHolder>(layoutId) {
    override fun convert(helper: BaseViewHolder, item: MessageBean) {
        helper.setText(R.id.tv_time, item.updatedAt)
        val imageView = helper.getView<ImageView>(R.id.iv_message)
        val dm = context.resources.displayMetrics
        val width = dm.widthPixels
        val param = imageView.layoutParams
        param.height = width / 16 * 9
        imageView.layoutParams = param

        imageView.loadImg(context, API.HOST + item.img)
        helper.setText(R.id.tv_title, item.title)
        helper.setText(R.id.tv_content, item.content)
    }

}
