package com.micropole.sxwine.adapter

import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.sxwine.R
import com.micropole.sxwine.base.loadImg
import com.micropole.sxwine.bean.ClassifyBean
import com.micropole.sxwine.utils.network.API
import android.view.View

/**
 * Description:
 * Created by DarkHorse on 2018/5/31.
 */
class ClassifyAdapter(layoutId: Int, data: ArrayList<ClassifyBean>) : BaseQuickAdapter<ClassifyBean, BaseViewHolder>(layoutId, data) {
    override fun convert(helper: BaseViewHolder, item: ClassifyBean) {
        if (item.thumb_img == mContext.getString(R.string.moreClassify)) {
            helper.getView<ImageView>(R.id.iv_classify).setImageResource(item.category_id)
        } else {
            helper.getView<ImageView>(R.id.iv_classify).loadImg(mContext, API.HOST+item.thumb_img, place = R.mipmap.home_btn_more,transform = FitCenter())
        }
        helper.setText(R.id.tv_classify, item.name)

        if(helper.layoutPosition == data.size-1){
            val view = helper.getView<View>(R.id.line)
            if(view!=null){
                view.visibility = View.GONE
            }
        }
    }

}
