package com.micropole.sxwine.adapter

import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.sxwine.R
import com.micropole.sxwine.base.loadImg
import com.micropole.sxwine.bean.CarGoodsBean
import com.micropole.sxwine.utils.network.API
import com.micropole.sxwine.widgets.AmountView
import com.micropole.sxwine.widgets.GlideRoundTransform

/**
 * Description:
 * Created by DarkHorse on 2018/6/6.
 */
class CarGoodsAdapter(layoutId: Int, data: ArrayList<CarGoodsBean>, val isChoseAll: (isChoseAll: Boolean) -> Unit, val updateGoods: (id: Int, quantity: Int) -> Unit) : BaseQuickAdapter<CarGoodsBean, BaseViewHolder>(layoutId, data) {

    override fun convert(helper: BaseViewHolder, item: CarGoodsBean) {
        helper.getView<ImageView>(R.id.iv_icon).loadImg(mContext, API.HOST + item.goods.thumb_img,transform = GlideRoundTransform())
        helper.setText(R.id.tv_name, item.goods.goods_name)
        helper.setText(R.id.tv_price, "RMB ${item.goods.mall_price}")

        val mAmountView = helper.getView<AmountView>(R.id.mAmountView)
        mAmountView.setGoods_storage(Int.MAX_VALUE)
        val editText = mAmountView.findViewById<EditText>(R.id.etAmount)
        editText.setText(item.quantity)

        mAmountView.setOnAmountChangeListener { view, amount ->
            updateGoods(item.cartId.toInt(), amount)
            item.quantity = amount.toString()
        }

        mAmountView.afterTextChanged(editText.text)

        val checkBox = helper.getView<CheckBox>(R.id.cb_choice)
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            item.isChecked = isChecked
            var isAll = true
            for (i in data) {
                if (!i.isChecked) {
                    isAll = false
                    break
                }
            }
            isChoseAll(isAll)
        }
        checkBox.isChecked = item.isChecked
    }


}
