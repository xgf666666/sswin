package com.micropole.sxwine.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.sxwine.R
import com.micropole.sxwine.base.loadImg
import com.micropole.sxwine.bean.MemberBean
import com.micropole.sxwine.utils.network.API
import android.view.View

/**
 * Description:
 * Created by DarkHorse on 2018/6/13.
 */
class MyTeamAdapter(layoutId: Int, data: ArrayList<MemberBean>) : BaseQuickAdapter<MemberBean, BaseViewHolder>(layoutId, data) {
    override fun convert(helper: BaseViewHolder, item: MemberBean) {
        helper.getView<ImageView>(R.id.iv_icon).loadImg(mContext, API.HOST + item.avatar, R.mipmap.toubu)
        val iv_sex = helper.getView<ImageView>(R.id.iv_sex)
        when(item.sex){
            "1"->{
                iv_sex.visibility = View.VISIBLE
                iv_sex.isSelected = true
            }
            "2"->{
                iv_sex.visibility = View.VISIBLE
                iv_sex.isSelected = true
            }
            "3"->{
                iv_sex.visibility = View.GONE
            }
        }
        helper.setText(R.id.tv_vip,item.vip_name)
        helper.setText(R.id.tv_nicname,item.nickname)
        helper.setText(R.id.tv_phone,item.mobile)
    }
}
