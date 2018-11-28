package com.micropole.sxwine.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.sxwine.R
import com.micropole.sxwine.bean.RcvConfirmAddressBean
import kotlinx.android.synthetic.main.item_fqa.view.*

/**
 * Description:
 * Created by DarkHorse on 2018/9/14.
 */
class ConfirmAddressAdapter(layoutId: Int) : BaseQuickAdapter<RcvConfirmAddressBean, BaseViewHolder>(layoutId) {
    override fun convert(helper: BaseViewHolder, item: RcvConfirmAddressBean) {
        helper.setText(R.id.tv_title, item.title)
        helper.setText(R.id.tv_content, item.content)
        helper.setChecked(R.id.cb_choice, item.isCheck)
    }

}
